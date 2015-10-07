//package test1;

// Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer AG, Switzerland.
// Copyright for manual written code belongs to <your name>, <your company>, <your country>
import java.net.URLEncoder;

import dfischer.utils.GenericPluginInterface;
import dfischer.utils.LoadtestPluginInterface;
import dfischer.utils.LoadtestPluginFixedUserInputField;
import dfischer.utils.LogVector;
import dfischer.utils.LoadtestPluginContext;
import dfischer.utils.HttpLoadTest;
import dfischer.utils.PerformanceData;
import dfischer.utils.CookieHandler;
import dfischer.utils.HttpTestURL;

/**
* Load test add-on module.
*/
public class RemoveFromString implements LoadtestPluginInterface
{
    private String inputString = "";        // input parameter #1 - label "Input String"
    private String removeString = "";        // input parameter #2 - label "String to remove"
    private boolean urlEncode = false;        // input parameter #3 - label "Url Encode output string"
    
    private String outputString = "";        // output parameter #1 - label "Output String"
    
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
        return "Remove From String";
    }
    
    
    public String getPluginDescription()
    {
        return "Remove substring from passed in string. Optionally url encode output string.\n\n";
    }
    
    
    public int getAllowedConstructScope()
    {
        return LoadtestPluginInterface.EXEC_SCOPE_NOT_FIXED;
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
        String[] labels = new String[3];
        labels[0] = "Input String";
        labels[1] = "String to remove";
        labels[2] = "Url Encode output string";
        return labels;
    }
    
    
    public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
    {
        return null;
    }
    
    
    public int allowOptionalInputParameter()
    {
        return 1;  
    }
    
    
    public String[] getOutputParameterLabels()
    {
        String[] labels = new String[1];
        labels[0] = "Output String";
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
     * input parameter #1: (String) inputString / default value = '' / label "Input String"
     * input parameter #2: (String) removeString / default value = '' / label "String to remove"
     * input parameter #3: (boolean) urlEncode / default value = 'false' / label "Url Encode output string"
     *
     * Note: all input parameters are always converted from strings.
     */
    public void setInputParameter(int parameterNumber, Object parameterValue)
    {
        switch (parameterNumber)
        {
            case 0:
                inputString = (String) parameterValue;
                break;
            case 1:
                removeString = (String) parameterValue;
                break;
            case 2:
                urlEncode = (((String) parameterValue).equalsIgnoreCase("1") || ((String) parameterValue).equalsIgnoreCase("true"));
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
	 try{

		 logVector = new LogVector();
	     LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
	     
	     if(inputString == null || removeString == null)
	    	 return;
	     
	     if(inputString.isEmpty() || removeString.isEmpty())
	    	 return;
	    	 
	     outputString = inputString.replace(removeString, "");
	     
	     if(urlEncode)
	    	 outputString = URLEncoder.encode(outputString, "UTF8");
     
	 }catch(Exception e){
		e.printStackTrace(); 
	 }
 }
 
 
 /**
  * Return plug-in output parameter. 
  *
  * output parameter #1: (String) outputString / default value = '' / label "Output String"
  *
  * Note: all output parameters are always converted to strings.
  */
 public Object getOutputParameter(int parameterNumber)
 {
     switch (parameterNumber)
     {
         case 0:
             return outputString;
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
         
         RemoveFromString plugin = new RemoveFromString();
         plugin.construct(null);
         plugin.setInputParameter(0, "MxgBEvZVs+fX7BzxepZ/zybiD2/B4bvIOsg60cvmNZ53/hFfgs1CU+cLl0ysR8Pugrl9qwX6Eume\nrcBjKUww7qpKR1SWwKFJ54lBUnjGlusHfhK2YQQj7JOdVlcjkZ/kjqnf75kxsh4N2xk6FrThs8RJ\nk27dXBqa3wrQ/I+L7sH0Bj23oMZS0yd3g4wNFVjx95SGMB7uSOgWbUkMCLN/JLvEftGk8AXqjX0v\nQIibr4I4sICjVo4Zjaoh+CNIIIYkZnaS4o2L9pcgavqtQOFFmSKpM81Y5ZTslC7TOGBs0IWmHeS8\nVpxHNEHXIMwNYPJI7yFOK5BbnO2RMAJ6Ciigfq43o5KF+rv0R7EWfKb5QdOdBtYIq3fDaOw7k9RV\neYl8BnD68YhUv3C/P9zUQ87QSw==");
         plugin.setInputParameter(1, "\n");
         plugin.setInputParameter(2, "true");
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

