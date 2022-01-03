// Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Apica.
// Copyright for manual written code belongs to <your name>, <your company>, <your country>
import dfischer.utils.GenericPluginInterface;
import dfischer.utils.LoadtestPluginInterface;
import dfischer.utils.LoadtestPluginFixedUserInputField;
import dfischer.utils.LogVector;
import dfischer.utils.LoadtestPluginContext;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

/**
* Load test add-on module.
*/
public class OTP_Code_Generator implements LoadtestPluginInterface
{
    private String secretKey = "";        // input parameter #1 - label "Secret Key"
    private String timestamp = "";        // input parameter #2 - label "Timestamp"
    
    private String otpToken = "";        // output parameter #1 - label "OTP Token"
    
    private LogVector logVector = null;        // internal log vector - use logVector.log(<String>) to write log data
    
    
    // ----------------------------------------------------------------------------
    // PART 1: var handler GUI definition and load test integration
    //
    // Note: automatically generated - no manual programming required for this part
    // ----------------------------------------------------------------------------
    
    
    public int getPluginType()
    {
        return GenericPluginInterface.TYPE_LOADTEST_EXEC;
    }
    
    
    public String getPluginName()
    {
        return "OTP/MFA Code Generator";
    }
    
    
    public String getPluginDescription()
    {
        return "Plugin to generate OTP for apps using MFA\n\n";
    }
    
    
    public int getAllowedConstructScope()
    {
        return LoadtestPluginInterface.CONSTRUCT_SCOPE_GLOBAL;
    }
    
    
    public int getAllowedExecScope()
    {
        return LoadtestPluginInterface.EXEC_SCOPE_URL;
    }
    
    
    public int getAllowedExecOrder()
    {
        return LoadtestPluginInterface.EXEC_ORDER_BEFORE;
    }
    
    
    public boolean allowMultipleUsage()
    {
        return true;
    }
    
    
    public String[] getInputParameterLabels()
    {
        String[] labels = new String[2];
        labels[0] = "Secret Key";
        labels[1] = "Timestamp";
        return labels;
    }
    
    
    public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
    {
        return null;
    }
    
    
    public int allowOptionalInputParameter()
    {
        return 1;        // optional input parameters starting from parameter #2
    }
    
    
    public String[] getOutputParameterLabels()
    {
        String[] labels = new String[1];
        labels[0] = "OTP Token";
        return labels;
    }
    
    
    public int allowOptionalOutputParameter()
    {
        return -1;        // all output parameters required
    }
    
    
    public LogVector getLogVector()
    {
        return logVector;
    }
    
    
    
    // ----------------------------------------------------------------------------
    // PART 2: runtime behavior / plug-in functionality
    //
    // This part requires manual programming (see sample code section below)
    // ----------------------------------------------------------------------------
    
    
    /**
     * Initialize plug-in at start of load test.
     */
    public void construct(Object context)
    {
        // LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
    }
    
    
    /**
     * Transfer input parameter before execute() is called.
     *
     * input parameter #1: (String) secretKey / default value = '' / label "Secret Key"
     * input parameter #2: (String) timestamp / default value = '' / label "Timestamp" / [optional]
     *
     * Note: all input parameters are always converted from strings.
     */
    public void setInputParameter(int parameterNumber, Object parameterValue)
    {
        switch (parameterNumber)
        {
            case 0:
                secretKey = (String) parameterValue;
                break;
            case 1:
                timestamp = (String) parameterValue;
                break;
            default:
                break;
        }
    }
    
    
    /**
     * Execute plug-in before URL call.
     *
     * Intrinsic plug-in implementation.
     */
    public void execute(Object context)
    {
        logVector = new LogVector();
        LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
        
        long ts = System.currentTimeMillis();
        if(!timestamp.isEmpty()) {
        	if(timestamp.length() > 10)
        		ts = Long.valueOf(timestamp);
        	else
        		ts = Long.valueOf(timestamp) * 1000;
        }
    	Base32 b32 = new Base32();
    	byte[] key = b32.decode(secretKey); // convert base32 encoded secret to byte array
    	otpToken = calculateVerificationCode(key, ts/30000, "HmacSHA1", 6); // timestep = current_time/30 secs
    	//calculateVerificationCode(byte[] secret, long timeStep, Algorithm algorithm, int numberOfDigits)
    	//System.out.println(otpToken);
        
    }
    
    /**
     * Calculate a verification code based upon the provided time step, algorithm and desired number of digits.
     *
     * @param secret         The secret
     * @param timeStep       The windowed time step to calculate the code
     * @param algorithm      The SHA algorithm to utilize
     * @param numberOfDigits The desired length of the code in number of digits
     * @return The verification code
     */
    // other valid algorithms
    // HmacSHA256 & HmacSHA512
    public String calculateVerificationCode(byte[] secret, long timeStep, String algorithm, int numberOfDigits) {
      // Generate Hashed Message from the secret
      byte[] hash = generateShaHMAC(secret, ByteBuffer.allocate(8).putLong(timeStep).order(ByteOrder.BIG_ENDIAN).array(), algorithm);

      // Truncate the hash and return a left padded string representation
      int offset = hash[hash.length - 1] & 0xf;
      int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
      long otp = binary % DIGITS_POWER[numberOfDigits];
      return String.format("%0" + numberOfDigits + "d", otp);
    }
    
    /**
     * Return the HMAC SHA encoded byte array using provided secret and the data as defined by the HOTP algorithm
     * defined by <a href="https://tools.ietf.org/html/rfc4226">RFC 4226</a>.
     *
     * @param secret The secret
     * @param data   The data to add to the secret - assumed to be a time instant
     * @return A byte array of the HMAC SHA hash
     */
    public byte[] generateShaHMAC(byte[] secret, byte[] data, String algorithm) {
      try {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(secret, "RAW"));

        return mac.doFinal(data);
      } catch (NoSuchAlgorithmException | InvalidKeyException e) {
        throw new IllegalStateException(e);
      }
    }
    
    private final long[] DIGITS_POWER = {
    	    1L,                // 0
    	    10L,               // 1
    	    100L,              // 2
    	    1_000L,            // 3
    	    10_000L,           // 4
    	    100_000L,          // 5
    	    1_000_000L,        // 6
    	    10_000_000L,       // 7
    	    100_000_000L,      // 8
    	    1_000_000_000L,    // 9
    	    10_000_000_000L,   // 10
    	    100_000_000_000L,  // 11
    	    1_000_000_000_000L // 12
    	  };
        
    /**
     * Return plug-in output parameter.
     *
     * output parameter #1: (String) otpToken / default value = '' / label "OTP Token"
     *
     * Note: all output parameters are always converted to strings.
     */
    public Object getOutputParameter(int parameterNumber)
    {
        switch (parameterNumber)
        {
            case 0:
                return otpToken;
            default:
                return null;
        }
    }
    
    
    /**
     * Finalize plug-in at end of load test.
     */
    public void deconstruct(Object context)
    {
        // LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
    }
    
    
    // ----------------------------------------------------------------------------
    // PART 3: stand-alone test utility - optional - used for plug-in development
    // ----------------------------------------------------------------------------
    
    
    public static void main(String[] args)
    {
        try
        {
        	
        	OTP_Code_Generator plugin = new OTP_Code_Generator();
            plugin.construct(null);
            plugin.setInputParameter(0, "BIUCXHN77DHJNM3O");
            plugin.execute(null);
            System.out.println(plugin.getOutputParameter(0));
            plugin.deconstruct(null);
            
            // ^^^ --- sample code --- ^^^
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    


}    // end of class
