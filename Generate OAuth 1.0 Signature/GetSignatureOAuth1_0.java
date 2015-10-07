// Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer AG, Switzerland.
// Copyright for manual written code belongs to <your name>, <your company>, <your country>
import dfischer.utils.Base64Encoder;
import dfischer.utils.GenericPluginInterface;
import dfischer.utils.LoadtestPluginInterface;
import dfischer.utils.LoadtestPluginFixedUserInputField;
import dfischer.utils.LogVector;
import dfischer.utils.LoadtestPluginContext;
import dfischer.utils.HttpLoadTest;
import dfischer.utils.PerformanceData;
import dfischer.utils.CookieHandler;
import dfischer.utils.HttpTestURL;

import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.net.URL;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

	/**
	* Load test add-on module.
	*/
	public class GetSignatureOAuth1_0 implements LoadtestPluginInterface
	{
	    private String key = "";        // input parameter #1 - label "client_key"
	    private String secret = "";        // input parameter #2 - label "client_secret"
	    private String url = "";        // input parameter #3 - label "realm_url"
	    private String timestamp = "";        // input parameter #4 - label "timestamp"
	    private String nonce = "";        // input parameter #5 - label "nonce"
	    private String method = "HmacSHA1";        // input parameter #6 - label "signature_method"
	    private String token = "";
	    private String token_secret = "";
	    
	    private String signature = "";        // output parameter #1 - label "signature"
	    private String outTimestamp = "";
	    private String outNonce = "";
	    
	    private LogVector logVector = null;        // internal log vector - use logVector.log(<String>) to write log data
	    final private String UTF8 = "UTF-8";
	    
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
	        return "Generate oauth 1.0 signature";
	    }
	    
	    
	    public String getPluginDescription()
	    {
	        return "Generates an oauth 1.0 signature, timestamp and nonce.\n\nUser can pass in values for oauth_key, "
	        		+ "oauth_secret, realm and optional values for oauth_timestamp, oauth_nonce,"
	        		+ "oauth_signature_method, oauth_token and oauth_token_secret. Plugin will generate values for "
	        		+ "oauth_signature, timestamp and nonce.";
	    }
	    
	    public int getAllowedConstructScope()
	    {
	        return LoadtestPluginInterface.CONSTRUCT_SCOPE_GLOBAL;
	    }
	    
	    
	    public int getAllowedExecScope()
	    {
	        return LoadtestPluginInterface.EXEC_SCOPE_NOT_FIXED;
	    }
	    
	    
	    public int getAllowedExecOrder()
	    {
	        return LoadtestPluginInterface.EXEC_ORDER_NOT_FIXED;
	    }
	    
	    
	    public boolean allowMultipleUsage()
	    {
	        return true;
	    }
	    
	    
	    public String[] getInputParameterLabels()
	    {
	        String[] labels = new String[8];
	        labels[0] = "oauth_consumer_key";
	        labels[1] = "oauth_signature (secret)";
	        labels[2] = "realm";
	        labels[3] = "oauth_timestamp";
	        labels[4] = "oauth_nonce";
	        labels[5] = "oauth_signature_method";
	        labels[6] = "oauth_token";
	        labels[7] = "oauth_token_secret";
	        return labels;
	    }
	    
	    
	    public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
	    {
	    /*
	        LoadtestPluginFixedUserInputField[] userInputFields = new LoadtestPluginFixedUserInputField[8];
	        userInputFields[0] = new LoadtestPluginFixedUserInputField("oauth_consumer_key", true, "");
	        userInputFields[1] = new LoadtestPluginFixedUserInputField("oauth_signature", true, "");
	        userInputFields[2] = new LoadtestPluginFixedUserInputField("realm", true, "");
	        userInputFields[3] = new LoadtestPluginFixedUserInputField("oauth_timestamp", true, "");
	        userInputFields[4] = new LoadtestPluginFixedUserInputField("oauth_nonce", true, "");
	        userInputFields[5] = new LoadtestPluginFixedUserInputField("oauth_signature_method", true, "HMAC_SHA1");
	        userInputFields[6] = new LoadtestPluginFixedUserInputField("oauth_token", true, "");
	        userInputFields[7] = new LoadtestPluginFixedUserInputField("oauth_token_secret", true, "");
	        return userInputFields;
	     */
	     	return null;   
	    }
	    
	    
	    public int allowOptionalInputParameter()
	    {
	        return 3;        // optional input parameters starting from parameter #4
	    }
	    
	    
	    public String[] getOutputParameterLabels()
	    {
	        String[] labels = new String[3];
	        labels[0] = "signature";
	        labels[1] = "timestamp";
	        labels[2] = "nonce";
	        return labels;
	    }
	    
	    
	    public int allowOptionalOutputParameter()
	    {
	        return 1;        // all output parameters required
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
	     * input parameter #1: (String) key / default value = '' / label "client_key"
	     * input parameter #2: (String) secret / default value = '' / label "client_secret"
	     * input parameter #3: (String) url / default value = '' / label "realm_url"
	     * input parameter #4: (String) timestamp / default value = '' / label "timestamp" / [optional]
	     * input parameter #5: (String) nonce / default value = '' / label "nonce" / [optional]
	     * input parameter #6: (String) method / default value = 'HMAC_SHA1' / label "signature_method" / [optional]
	     *
	     * Note: all input parameters are always converted from strings.
	     */
	    public void setInputParameter(int parameterNumber, Object parameterValue)
	    {
	        switch (parameterNumber)
	        {
	            case 0:
	                key = (String) parameterValue;
	                break;
	            case 1:
	                secret = (String) parameterValue;
	                break;
	            case 2:
	                url = (String) parameterValue;
	                break;
	            case 3:
	                timestamp = (String) parameterValue;
	                break;
	            case 4:
	                nonce = (String) parameterValue;
	                break;
	            case 5:
	                method = (String) parameterValue;
	                break;
	            case 6:
	                token = (String) parameterValue;
	                break;    
	            case 7:
	                token_secret = (String) parameterValue;
	                break;    
	            default:
	                break;
	        }
	    }
	    
	    
	    /**
	     * Execute plug-in .
	     *
	     * Intrinsic plug-in implementation.
	     */
	    public void execute(Object context)
	    {
	        logVector = new LogVector();
	        LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;	        
	        
	       
	        try{
	        	String[] urlParts = url.split("\\?");
	        	String hostpath = URLEncoder.encode(urlParts[0], UTF8);
	        	String[] params = urlParts[1].split("&");
	        	ArrayList<String> parameters = new ArrayList<String>(Arrays.asList(params));
	        	 
	        	if(urlParts[1].contains("oauth_timestamp=")){
		        	for(String param : params){
		        		if(param.contains("oauth_timestamp="))
		        			timestamp = param.split("=")[1];
		        	}	        		
	        	}
	        	// generate timestamp if it wasn't passed in
	        	else if(timestamp.isEmpty()){
	 	        	timestamp = String.format("%d", System.currentTimeMillis()/1000L);
	 	        	parameters.add("oauth_timestamp="+timestamp);
	        	}
	        	outTimestamp = timestamp;
	        	logVector.log("out_timestamp = "+outTimestamp);
 	        	System.out.println("timestamp: " + timestamp);
	 	        
	        	if(urlParts[1].contains("oauth_nonce=")){
		        	for(String param : params){
		        		if(param.contains("oauth_nonce="))
		        			nonce = param.split("=")[1];
		        	}	        		
	        	}
	        	// generate nonce if it wasn't passed in
	        	else if(nonce.isEmpty()){
	 	        	nonce = String.format("%d", (long) (Math.random() * 100000000000L));
	 	        	parameters.add("oauth_nonce="+nonce);
	 	        }
	        	System.out.println("nonce: "+nonce);
	        	outNonce = nonce;
	        	logVector.log("out_nonce = "+outNonce);
	 	        
	 	        if(!urlParts[1].contains("oauth_version="))
	 	        	parameters.add("oauth_version=1.0");
	 	        
	 	        Collections.sort(parameters);
	        	String sortedParams = "";
	        	for(String param : parameters){
	        		//add excludes here
	        		if(param.contains("oauth_signature="))
	        			continue;
	        			
	        		sortedParams+=sortedParams.isEmpty()?param:String.format("&%s", param);
	        	}

	        	String signatureBase = String.format("GET&%s&%s", hostpath, URLEncoder.encode(sortedParams, UTF8));
	        	System.out.println("signature base: "+signatureBase);

	        	// generate oauth signature
	    		byte[] keyBytes = (secret + "&" + token_secret).getBytes(UTF8);
	            SecretKey sk = new SecretKeySpec(keyBytes, method);
	            Mac mac = Mac.getInstance(method);
	            mac.init(sk);

	            signature = Base64Encoder.encode(mac.doFinal(signatureBase.getBytes(UTF8)));
	            signature = URLEncoder.encode(signature, UTF8);         
	            
			} catch (Exception e) {
				e.printStackTrace();
			}

	    }
	    
	    
	    /**
	     * Return plug-in output parameter.
	     *
	     * output parameter #1: (String) signature / default value = '' / label "signature"
	     *
	     * Note: all output parameters are always converted to strings.
	     */
	    public Object getOutputParameter(int parameterNumber)
	    {
	        switch (parameterNumber)
	        {
	            case 0:
	                return signature;
	            case 1:
	            	return outTimestamp;
	            case 2:
	            	return outNonce;
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
	            // vvv --- sample code --- vvv
	            
	            GetSignatureOAuth1_0 plugin = new GetSignatureOAuth1_0();
	            plugin.construct(null);
	            //plugin.setInputParameter(0, args[0]); //key
	            //plugin.setInputParameter(1, args[1]); //secret
	            //plugin.setInputParameter(2, args[2]); //url
	            
	            plugin.setInputParameter(0, "afa9b186e945e790805067eb37ec7de4bfda955b17cf898c16280bc532877af0"); //key
	            plugin.setInputParameter(1, "142f9e33e485633c5991c469889ff6ade8879afbada65b9ffb3661009722da9e"); //secret
	            plugin.setInputParameter(2, "http://beta-cloud.rovicorp.com/matador-ws/v2.b/0/browse/service;country=;postalCode=;msoId=341774605?page=1&size=20"); //url
	            plugin.setInputParameter(3, "1404460445"); //timestamp
	            plugin.setInputParameter(4, "1234567890"); //nonce

	            /*
	            plugin.setInputParameter(0, "dpf43f3p2l4k3l03"); //key
	            plugin.setInputParameter(1, "kd94hf93k423kf44"); //secret
	            plugin.setInputParameter(2, "http://photos.example.net/photos?file=vacation.jpg&size=original&oauth_consumer_key=dpf43f3p2l4k3l03&oauth_token=nnch734d00sl2jdk&oauth_signature_method=HMAC-SHA1&oauth_signature=tR3%2BTy81lMeYAr%2FFid0kMTYa%2FWM%3D&oauth_timestamp=1191242096&oauth_nonce=kllo9940pd9333jh&oauth_version=1.0"); //url
	            plugin.setInputParameter(7, "pfkkdhi9sl3r4s00"); //secret
				*/
	            plugin.execute(null);
	            System.out.println(plugin.getOutputParameter(0));
	            System.out.println(plugin.getOutputParameter(1));
	            System.out.println(plugin.getOutputParameter(2));
	            plugin.deconstruct(null);
	            
	            // ^^^ --- sample code --- ^^^
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	    


	}    // end of class

