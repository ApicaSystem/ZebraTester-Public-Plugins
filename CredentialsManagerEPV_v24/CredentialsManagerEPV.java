
import dfischer.utils.*;

// cyberark epv imports
import javapasswordsdk.*;
import javapasswordsdk.exceptions.*;

/**
* Load test add-on module.
*/
public class CredentialsManagerEPV implements LoadtestPluginInterface
{

private String vUsername = "";		// output parameter #1 - label "Username"
private String vPassword = "";		// output parameter #2 - label "Password"

private String vAppID = "";
private String vSafe = "";
private String vFolder = "";
private String vObject = "";
private String vReason = "";

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
   return "Credentials Manager v24";
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
   String[] labels = new String[5];
   labels[0] = "App ID";
   labels[1] = "Safe";
   labels[2] = "Folder";
   labels[3] = "Object";
   labels[4] = "Reason";
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
       vAppID = (String) parameterValue;
       break;
   case 1:
       vSafe = (String) parameterValue;
       break;
   case 2:
	   vFolder = (String) parameterValue;
	   break;
   case 3:
	   vObject = (String) parameterValue;
	   break;
   case 4:
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
   processEPV(vAppID, vSafe, vFolder, vObject, vReason);
   
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

protected String processEPV(String sAppID, String sSafe, String sFolder, String sObject, String sReason){
	
	vUsername = "";
	vPassword = "";
	
	try {
		
		PSDKPasswordRequest psdkpr = new PSDKPasswordRequest();
		psdkpr.setAppID(sAppID);
		psdkpr.setSafe(sSafe);
		psdkpr.setFolder(sFolder);
		psdkpr.setObject(sObject);
		if(sReason != null && !sReason.isEmpty())
			psdkpr.setReason(sReason);
	
		PSDKPassword passwordInfo = PasswordSDK.getPassword(psdkpr);
		vUsername = passwordInfo.getUserName();
		vPassword = passwordInfo.getContent();

	} catch (PSDKException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return vPassword;
}

public static void main(String[] args){
	
	CredentialsManagerEPV cm = new CredentialsManagerEPV();
	cm.call(args);
}

// ----------------------------------------------------------------------------
// PART 3: stand-alone utility that creates the encrypted file.
//
// TO CHECK A RECORD
// -------------------
// Argument[0] = AppID
// Argument[1] = Safe
// Argument[2] = Folder
// Argument[3] = Object
// Argument[4] = Reason
//

protected void call(String[] args)
{
   try
   {
	   if(args.length == 0){
		    System.out.println("no arguments were passed\n");
	   
	   }else{
	   
	       if (args.length >= 4) {
	    	   
	    	   String args4 = "";
	    	   if(args.length > 4)
	    		   args4 = args[4];
	    	   //out(args5);
	    	   processEPV(args[0], args[1], args[2], args[3], args4);
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
		+ "\t[PATH_TO_JAVA]>java -jar CredentialsManagerEPV_vXX.jar AppID Safe Folder Object Reason(optional)\n");
  
}


private static void out(String msg){
	if(DEBUG)
		System.out.println(msg);
}


}	// end of class

