
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.json.JSONObject;

import dfischer.utils.*;
// cyberark epv imports
//import javapasswordsdk.*;
//import javapasswordsdk.exceptions.*;

/**
* Load test add-on module.
*/
public class CredentialsManagerEPVAPI implements LoadtestPluginInterface
{

private String vUsername = null;		// output parameter #1 - label "Username"
private String vPassword = null;		// output parameter #2 - label "Password"

private String vApiUrl = null;
private String vAppID = null;
private String vSafe = null;
private String vFolder = null;
private String vObject = null;
private String vReason = null;

private LogVector logVector = null;		// internal log vector - use logVector.log(<String>) to write log data

private static boolean DEBUG = true;


// ----------------------------------------------------------------------------
// PART 1: var Manager GUI definition and load test integration
//
// Note: automatically generated - no manual programming required for this part
// ----------------------------------------------------------------------------


public int getPluginType()
{
   return GenericPluginInterface.TYPE_LOADTEST_EXEC;
}


public String getPluginName()
{
   return "Credentials Manager EPV v25";
}


public String getPluginDescription()
{
   return "Retrieve encrypted username/password from EPV and plugin will retrieve data to be used in the ZebraTester script.";
}


public int getAllowedConstructScope()
{
   return LoadtestPluginInterface.CONSTRUCT_SCOPE_GLOBAL;
}


public int getAllowedExecScope()
{
   return LoadtestPluginInterface.EXEC_SCOPE_LOOP;     // new account per loop - can be changed to EXEC_SCOPE_USER if needed (new account per user)
}


public int getAllowedExecOrder()
{
   return LoadtestPluginInterface.EXEC_ORDER_BEFORE;
}


public boolean allowMultipleUsage()
{
   return false;
}


public String[] getInputParameterLabels()
{
   String[] labels = new String[6];
   labels[0] = "API URL";
   labels[1] = "App ID";
   labels[2] = "Safe";
   labels[3] = "Folder";
   labels[4] = "Object";
   labels[5] = "Reason";
   return labels;
}


public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
{
    return null;
}


public int allowOptionalInputParameter()
{
	return 4;		// fifth parameter optional
}


public String[] getOutputParameterLabels()
{
   String[] labels = new String[2];
   labels[0] = "Username";
   labels[1] = "Password";
   return labels;
}


public int allowOptionalOutputParameter()
{
   return -1;		// all output parameters required
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
}

/**
* Transfer input parameter before execute() is called.
*/
public void setInputParameter(int parameterNumber, Object parameterValue)
{
   switch (parameterNumber)
   {
   case 0:
       vApiUrl = (String) parameterValue;
       break;
   case 1:
       vAppID = (String) parameterValue;
       break;
   case 2:
       vSafe = (String) parameterValue;
       break;
   case 3:
	   vFolder = (String) parameterValue;
	   break;
   case 4:
	   vObject = (String) parameterValue;
	   break;
   case 5:
	   vReason = (String) parameterValue;
	   break;
       
   default:
       break;
   }
}


/**
* Execute plug-in at start of loop (per loop).
*
* Intrinsic plug-in implementation.
*/
public void execute(Object context)
{
   logVector = new LogVector();
   
   // try getting credentials from EPV
   processEPV(vApiUrl, vAppID, vSafe, vFolder, vObject, vReason);
   
}


/**
* Return plug-in output parameter.
*
* output parameter #1: (String) vUsername / default value = '' / label "Username"
* output parameter #2: (String) vPassword / default value = '' / label "Password"
*
* Note: all output parameters are always converted to strings.
*/
public Object getOutputParameter(int parameterNumber)
{
   switch (parameterNumber)
   {
       case 0:
           return vUsername;
       case 1:
           return vPassword;
       default:
           return null;
   }
}


/**
* Finalize plug-in at end of load test.
*/
public void deconstruct(Object context)
{
}

protected String processEPV(String sApiUrl, String sAppID, String sSafe, String sFolder, String sObject, String sReason){
	
	vUsername = null;
	vPassword = null;
	String apiUrl = String.format("%s/AIMWebService/api/Accounts?AppID=%s&Safe=%s&Folder=%s&Object=%s", sApiUrl, sAppID, sSafe, sFolder, sObject);
	if(sReason != null && !sReason.isEmpty())
		apiUrl += "&Reason="+sReason;
	
	try {
		
		HttpTestURL testUrl = makeHTTPcall("GET", apiUrl, null, null);
		byte[] response = testUrl.getResponseContentObject().getContent();
		out(new String(response));
		JSONObject json = new JSONObject(new String(response));
		vUsername = json.getString("UserName");
		vPassword = json.getString("Content");
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return vPassword;
}

private HttpTestURL makeHTTPcall(String method, String apiUrl, String requestPayload, LoadtestPluginContext pluginContext){
	
    HttpTestURL testUrl = null;

	try{
		out(apiUrl);
		URL url = new URL(apiUrl);

		String requestFile = url.getPath();
		if(url.getQuery() != null && !url.getQuery().isEmpty())
			requestFile += "?" + url.getQuery();
		String requestHeader = method.toUpperCase().trim() + " " + requestFile + " HTTP/1.1\r\n" + 
				"Host: "+ url.getHost() +"\r\n" + 
				"Accept: */*\r\n"; 
				//"Content-Type: *.*\r\n";
		
		if(requestPayload != null && !requestPayload.isEmpty())
			requestHeader += "Content-Length: " + requestPayload.getBytes("UTF-8").length + "\r\n";

		int port = url.getPort();
		if(port == -1 && url.getProtocol().equals("https"))
			port = 443;
		else if(port == -1 && url.getProtocol().equals("http"))
			port = 80;
		
		HttpSocketPool socketPool = null;
		CookieHandler cookieHandler = null;
		if(pluginContext != null){
			socketPool = pluginContext.getSocketPool();
			cookieHandler = pluginContext.getCookieHandler();
		}
		
		testUrl = new HttpTestURL(url.getProtocol(), url.getHost(), port, requestHeader, requestPayload == null ? null : requestPayload.getBytes(), 
										-1, socketPool, cookieHandler, null);
	    testUrl.execute(null); // omit passing performance data
	    
	    out(testUrl.getStatus()+"");


	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return testUrl;

}

public static void main(String[] args){
	
	CredentialsManagerEPV cm = new CredentialsManagerEPV();
	
	String[] params = {"http://services-uscentral.skytap.com:14483", "Apica_ASM", "Test", "root", "loadtest1", "test"};
	cm.call(params);
}

// ----------------------------------------------------------------------------
// PART 3: stand-alone utility that creates the encrypted file.
//
// TO CHECK A RECORD
// -------------------
// Argument[0] = epv
// Argument[1] = AppID
// Argument[2] = Safe
// Argument[3] = Folder
// Argument[4] = Object
// Argument[5] = Reason
//

protected void call(String[] args)
{
   try
   {
	   if(args.length == 0){
		    System.out.println("no arguments were passed\n");
	   
	   }else{
	   
	       if (args[0].contains("http")) {
	    	   
	    	   String args5 = "";
	    	   if(args.length > 5)
	    		   args5 = args[5];
	    	   out(args5);
	    	   processEPV(args[0], args[1], args[2], args[3], args[4], args5);
	    	   out("user: "+vUsername);
	    	   out("pwd: "+vPassword);
	    	   return;
	       }
	   }      
   }
   catch(Exception exc){
	   exc.printStackTrace();
   }

   System.out.println("USAGE (when using CredentialsManagerEPV_vXX.jar): \n"
		+ "To retrieve ID and Password:\n"
		+ "\t[PATH_TO_JAVA]>java -jar CredentialsManagerEPV_vXX.jar API_URL AppID Safe Folder Object Reason(optional)\n");
  
}


private static void out(String msg){
	if(DEBUG)
		System.out.println(msg);
}


}	// end of class

