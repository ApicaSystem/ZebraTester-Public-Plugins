package com.amazonaws.apica;

import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import dfischer.utils.*;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Apica_Cognito_Handler {
    private static final String HEX_N =
            "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1"
                    + "29024E088A67CC74020BBEA63B139B22514A08798E3404DD"
                    + "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245"
                    + "E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED"
                    + "EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3D"
                    + "C2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F"
                    + "83655D23DCA3AD961C62F356208552BB9ED529077096966D"
                    + "670C354E4ABC9804F1746C08CA18217C32905E462E36CE3B"
                    + "E39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9"
                    + "DE2BCBF6955817183995497CEA956AE515D2261898FA0510"
                    + "15728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64"
                    + "ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7"
                    + "ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6B"
                    + "F12FFA06D98A0864D87602733EC86A64521F2B18177B200C"
                    + "BBE117577A615D6C770988C0BAD946E208E24FA074E5AB31"
                    + "43DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF";
    private static final BigInteger N = new BigInteger(HEX_N, 16);
    private static final BigInteger g = BigInteger.valueOf(2);
    private static final BigInteger k;
    private static final int EPHEMERAL_KEY_LENGTH = 1024;
    private static final int DERIVED_KEY_SIZE = 16;
    private static final String DERIVED_KEY_INFO = "Caldera Derived Key";
    private static final ThreadLocal<MessageDigest> THREAD_MESSAGE_DIGEST =
            new ThreadLocal<MessageDigest>() {
                @Override
                protected MessageDigest initialValue() {
                    try {
                        return MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        throw new SecurityException("Exception in authentication", e);
                    }
                }
            };
    private static final SecureRandom SECURE_RANDOM;

    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");

            MessageDigest messageDigest = THREAD_MESSAGE_DIGEST.get();
            messageDigest.reset();
            messageDigest.update(N.toByteArray());
            byte[] digest = messageDigest.digest(g.toByteArray());
            k = new BigInteger(1, digest);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    private BigInteger a;
    private BigInteger A;
    private String userPoolID;
    private String clientId;
    private String region;

    public String performAuthentication(LoadtestPluginContext pluginContext, String username, String password){
        try {
            LogVector logVector = new LogVector();

            JSONObject firstResponse = initiateUserSrpAuthRequest(username, 60, pluginContext.getSocketPool(), pluginContext.getCookieHandler(), logVector);

            String SRP_B = firstResponse.getJSONObject("ChallengeParameters").getString("SRP_B");
            String saltString = firstResponse.getJSONObject("ChallengeParameters").getString("SALT");
            String userIdForSRP = firstResponse.getJSONObject("ChallengeParameters").getString("USER_ID_FOR_SRP");
            String secretBlockString = firstResponse.getJSONObject("ChallengeParameters").getString("SECRET_BLOCK");

            BigInteger B = new BigInteger(SRP_B, 16);
            if (B.mod(N).equals(BigInteger.ZERO)) {
                throw new SecurityException("SRP error, B cannot be zero");
            }

            BigInteger salt = new BigInteger(saltString, 16);
            byte[] key = getPasswordAuthenticationKey(userIdForSRP, password, B, salt);

            Date timestamp = new Date();
            byte[] hmac = null;
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA256");
                mac.init(keySpec);
                mac.update(userPoolID.split("_", 2)[1].getBytes(StringUtils.UTF8));
                mac.update(userIdForSRP.getBytes(StringUtils.UTF8));
                byte[] secretBlock = Base64.decode(secretBlockString);
                mac.update(secretBlock);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
                simpleDateFormat.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
                String dateString = simpleDateFormat.format(timestamp);
                byte[] dateBytes = dateString.getBytes(StringUtils.UTF8);
                hmac = mac.doFinal(dateBytes);
            } catch (Exception e) {
                System.out.println(e);
            }

            SimpleDateFormat formatTimestamp = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
            formatTimestamp.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

            Map<String, String> srpAuthResponses = new HashMap<>();
            srpAuthResponses.put("PASSWORD_CLAIM_SECRET_BLOCK", secretBlockString);
            srpAuthResponses.put("PASSWORD_CLAIM_SIGNATURE", new String(Base64.encode(hmac), StringUtils.UTF8));
            srpAuthResponses.put("TIMESTAMP", formatTimestamp.format(timestamp));
            srpAuthResponses.put("USERNAME", userIdForSRP);

            JSONObject authenticationJSONObject = initiateUserSrpChallengeRequest(srpAuthResponses.get("PASSWORD_CLAIM_SECRET_BLOCK"), srpAuthResponses.get("PASSWORD_CLAIM_SIGNATURE"), srpAuthResponses.get("TIMESTAMP"), srpAuthResponses.get("USERNAME"), 60, pluginContext.getSocketPool(), pluginContext.getCookieHandler(), logVector);

            return authenticationJSONObject.getJSONObject("AuthenticationResult").getString("AccessToken");

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject initiateUserSrpAuthRequest(String username, int timeout, HttpSocketPool socketPool, CookieHandler cookieHandler, LogVector httpLogVectorObject) throws IOException {

        String body = "{\"AuthFlow\":\"USER_SRP_AUTH\",\"AuthParameters\":{\"USERNAME\":\"" + username + "\",\"SRP_A\":\"" + this.getA().toString(16) + "\"},\"ClientId\":\""+this.clientId+"\",\"ClientMetadata\":{}}\n";
        String response = runInitiate("cognito-idp."+this.region+".amazonaws.com",body, timeout, socketPool, cookieHandler, httpLogVectorObject);
        try{
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject initiateUserSrpChallengeRequest(String PASSWORD_CLAIM_SECRET_BLOCK, String PASSWORD_CLAIM_SIGNATURE, String TIMESTAMP, String USERNAME, int timeout, HttpSocketPool socketPool, CookieHandler cookieHandler, LogVector httpLogVectorObject) throws IOException {
        String body = "{\"ChallengeName\":\"PASSWORD_VERIFIER\",\"ClientId\":\""+this.clientId+"\",\"ChallengeResponses\":{\"USERNAME\":\"" + USERNAME + "\",\"PASSWORD_CLAIM_SECRET_BLOCK\":\"" + PASSWORD_CLAIM_SECRET_BLOCK + "\",\"TIMESTAMP\":\"" + TIMESTAMP + "\",\"PASSWORD_CLAIM_SIGNATURE\":\"" + PASSWORD_CLAIM_SIGNATURE + "\"}}\n";
        String response = runChallenge("cognito-idp."+this.region+".amazonaws.com",body, timeout, socketPool, cookieHandler, httpLogVectorObject);

        try{
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static String runInitiate(String host,String body, int timeout, HttpSocketPool socketPool, CookieHandler cookieHandler, LogVector httpLogVectorObject) throws IOException {

        String requestProt = "https";
        String requestHost = host;
        int    requestPort = 443;
        String requestFile = "/";
        String requestContent = body;
        String requestHeader = "POST " + requestFile + " HTTP/" + "1.1" + "\r\n" +
                "Host: "+host+"\r\n" +
                "User-Agent: " + "ZebraTester" + "\r\n" +
                "Accept: */*\r\n" +
                "Content-Type: application/x-amz-json-1.1\r\n" +
                "x-amz-target: AWSCognitoIdentityProviderService.InitiateAuth\r\n" +
                "Content-Length: " + requestContent.length() + "\r\n" +
                "Connection: Keep-Alive\r\n" +
                "\r\n";

        HttpTestURL testURL = new HttpTestURL(requestProt, requestHost, requestPort, requestHeader, requestContent.getBytes(), timeout, socketPool, cookieHandler, httpLogVectorObject);

        testURL.execute(new PerformanceData());

        return testURL.getContentString();
    }

    private static String runChallenge(String host,String body, int timeout, HttpSocketPool socketPool, CookieHandler cookieHandler, LogVector httpLogVectorObject) throws IOException {

        String requestProt = "https";
        String requestHost = host;
        int    requestPort = 443;
        String requestFile = "/";
        String requestContent = body;
        String requestHeader = "POST " + requestFile + " HTTP/" + "1.1" + "\r\n" +
                "Host: "+host+"\r\n" +
                "User-Agent: " + "ZebraTester" + "\r\n" +
                "Accept: */*\r\n" +
                "Content-Type: application/x-amz-json-1.1\r\n" +
                "x-amz-target: AWSCognitoIdentityProviderService.RespondToAuthChallenge\r\n" +
                "Content-Length: " + requestContent.length() + "\r\n" +
                "Connection: Keep-Alive\r\n" +
                "\r\n";

        HttpTestURL testURL = new HttpTestURL(requestProt, requestHost, requestPort, requestHeader, requestContent.getBytes(), timeout, socketPool, cookieHandler, httpLogVectorObject);

        testURL.execute(new PerformanceData());

        return testURL.getContentString();
    }

    public Apica_Cognito_Handler(String userPoolID, String clientid) {
        do {
            a = new BigInteger(EPHEMERAL_KEY_LENGTH, SECURE_RANDOM).mod(N);
            A = g.modPow(a, N);
        } while (A.mod(N).equals(BigInteger.ZERO));

        this.userPoolID = userPoolID;
        this.clientId = clientid;
        this.region = userPoolID.split("_")[0];

    }

    private BigInteger getA() {
        return A;
    }

    private byte[] getPasswordAuthenticationKey(String userId,
                                                String userPassword,
                                                BigInteger B,
                                                BigInteger salt) {
        // Authenticate the password
        // u = H(A, B)
        MessageDigest messageDigest = THREAD_MESSAGE_DIGEST.get();
        messageDigest.reset();
        messageDigest.update(A.toByteArray());
        BigInteger u = new BigInteger(1, messageDigest.digest(B.toByteArray()));
        if (u.equals(BigInteger.ZERO)) {
            throw new SecurityException("Hash of A and B cannot be zero");
        }

        // x = H(salt | H(poolName | userId | ":" | password))
        messageDigest.reset();
        messageDigest.update(this.userPoolID.split("_", 2)[1].getBytes(StringUtils.UTF8));
        messageDigest.update(userId.getBytes(StringUtils.UTF8));
        messageDigest.update(":".getBytes(StringUtils.UTF8));
        byte[] userIdHash = messageDigest.digest(userPassword.getBytes(StringUtils.UTF8));

        messageDigest.reset();
        messageDigest.update(salt.toByteArray());
        BigInteger x = new BigInteger(1, messageDigest.digest(userIdHash));
        BigInteger S = (B.subtract(k.multiply(g.modPow(x, N))).modPow(a.add(u.multiply(x)), N)).mod(N);

        Apica_Cognito_Handler.Hkdf hkdf;
        try {
            hkdf = Apica_Cognito_Handler.Hkdf.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e.getMessage(), e);
        }
        hkdf.init(S.toByteArray(), u.toByteArray());
        byte[] key = hkdf.deriveKey(DERIVED_KEY_INFO, DERIVED_KEY_SIZE);
        return key;
    }

    private String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));

            return new BASE64Encoder().encode(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating ");
        }
    }

    /**
     * Internal class for doing the Hkdf calculations.
     */
    final static class Hkdf {
        private static final int MAX_KEY_SIZE = 255;
        private final byte[] EMPTY_ARRAY = new byte[0];
        private final String algorithm;
        private SecretKey prk = null;


        /**
         * @param algorithm REQUIRED: The type of HMAC algorithm to be used.
         */
        private Hkdf(String algorithm) {
            if (!algorithm.startsWith("Hmac")) {
                throw new IllegalArgumentException("Invalid algorithm " + algorithm
                        + ". Hkdf may only be used with Hmac algorithms.");
            } else {
                this.algorithm = algorithm;
            }
        }

        private static Apica_Cognito_Handler.Hkdf getInstance(String algorithm) throws NoSuchAlgorithmException {

            return new Apica_Cognito_Handler.Hkdf(algorithm);
        }

        /**
         * @param ikm REQUIRED: The input key material.
         */
        public void init(byte[] ikm) {
            this.init(ikm, (byte[]) null);
        }

        /**
         * @param ikm  REQUIRED: The input key material.
         * @param salt REQUIRED: Random bytes for salt.
         */
        private void init(byte[] ikm, byte[] salt) {
            byte[] realSalt = salt == null ? EMPTY_ARRAY : (byte[]) salt.clone();
            byte[] rawKeyMaterial = EMPTY_ARRAY;

            try {
                final Mac e = Mac.getInstance(this.algorithm);
                if (realSalt.length == 0) {
                    realSalt = new byte[e.getMacLength()];
                    Arrays.fill(realSalt, (byte) 0);
                }

                e.init(new SecretKeySpec(realSalt, this.algorithm));
                rawKeyMaterial = e.doFinal(ikm);
                final SecretKeySpec key = new SecretKeySpec(rawKeyMaterial, this.algorithm);
                Arrays.fill(rawKeyMaterial, (byte) 0);
                this.unsafeInitWithoutKeyExtraction(key);
            } catch (final GeneralSecurityException var10) {
                throw new RuntimeException("Unexpected exception", var10);
            } finally {
                Arrays.fill(rawKeyMaterial, (byte) 0);
            }

        }

        /**
         * @param rawKey REQUIRED: Current secret key.
         * @throws InvalidKeyException
         */
        private void unsafeInitWithoutKeyExtraction(SecretKey rawKey) throws InvalidKeyException {
            if (!rawKey.getAlgorithm().equals(this.algorithm)) {
                throw new InvalidKeyException(
                        "Algorithm for the provided key must match the algorithm for this Hkdf. Expected "
                                + this.algorithm + " but found " + rawKey.getAlgorithm());
            } else {
                this.prk = rawKey;
            }
        }

        /**
         * @param info   REQUIRED
         * @param length REQUIRED
         * @return converted bytes.
         */
        private byte[] deriveKey(String info, int length) {
            return this.deriveKey(info != null ? info.getBytes(StringUtils.UTF8) : null, length);
        }

        /**
         * @param info   REQUIRED
         * @param length REQUIRED
         * @return converted bytes.
         */
        private byte[] deriveKey(byte[] info, int length) {
            final byte[] result = new byte[length];

            try {
                this.deriveKey(info, length, result, 0);
                return result;
            } catch (final ShortBufferException var5) {
                throw new RuntimeException(var5);
            }
        }

        /**
         * @param info   REQUIRED
         * @param length REQUIRED
         * @param output REQUIRED
         * @param offset REQUIRED
         * @throws ShortBufferException
         */
        private void deriveKey(byte[] info, int length, byte[] output, int offset)
                throws ShortBufferException {
            this.assertInitialized();
            if (length < 0) {
                throw new IllegalArgumentException("Length must be a non-negative value.");
            } else if (output.length < offset + length) {
                throw new ShortBufferException();
            } else {
                final Mac mac = this.createMac();
                if (length > MAX_KEY_SIZE * mac.getMacLength()) {
                    throw new IllegalArgumentException(
                            "Requested keys may not be longer than 255 times the underlying HMAC length.");
                } else {
                    byte[] t = EMPTY_ARRAY;

                    try {
                        int loc = 0;

                        for (byte i = 1; loc < length; ++i) {
                            mac.update(t);
                            mac.update(info);
                            mac.update(i);
                            t = mac.doFinal();

                            for (int x = 0; x < t.length && loc < length; ++loc) {
                                output[loc] = t[x];
                                ++x;
                            }
                        }
                    } finally {
                        Arrays.fill(t, (byte) 0);
                    }

                }
            }
        }

        /**
         * @return the generates message authentication code.
         */
        private Mac createMac() {
            try {
                final Mac ex = Mac.getInstance(this.algorithm);
                ex.init(this.prk);
                return ex;
            } catch (final NoSuchAlgorithmException var2) {
                throw new RuntimeException(var2);
            } catch (final InvalidKeyException var3) {
                throw new RuntimeException(var3);
            }
        }

        /**
         * Checks for a valid pseudo-random key.
         */
        private void assertInitialized() {
            if (this.prk == null) {
                throw new IllegalStateException("Hkdf has not been initialized");
            }
        }
    }
}
