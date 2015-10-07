//Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer AG, Switzerland.
//Copyright for manual written code belongs to <your name>, <your company>, <your country>
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
public class StringJoiner implements LoadtestPluginInterface
{
 private String string1 = "";        // input parameter #1 - label "string1"
 private String string2 = "";        // input parameter #2 - label "string2"
 private String string3 = "";        // input parameter #3 - label "string3"
 private String string4 = "";        // input parameter #4 - label "string4"
 private String string5 = "";        // input parameter #5 - label "string5"
 private String string6 = "";        // input parameter #6 - label "string6"
 
 private String output = "";        // output parameter #1 - label "outputString"
 
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
     return "String Joiner";
 }
 
 
 public String getPluginDescription()
 {
     return "Combines two or more strings together (concatenate) into one.\n\n";
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
     String[] labels = new String[6];
     labels[0] = "string1";
     labels[1] = "string2";
     labels[2] = "string3";
     labels[3] = "string4";
     labels[4] = "string5";
     labels[5] = "string6";
     return labels;
 }
 
 
 public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
 {
     return null;
 }
 
 
 public int allowOptionalInputParameter()
 {
     return 2;        // optional input parameters starting from parameter #3
 }
 
 
 public String[] getOutputParameterLabels()
 {
     String[] labels = new String[1];
     labels[0] = "outputString";
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
  * input parameter #1: (String) string1 / default value = '' / label "string1"
  * input parameter #2: (String) string2 / default value = '' / label "string2"
  * input parameter #3: (String) string3 / default value = '' / label "string3" / [optional]
  * input parameter #4: (String) string4 / default value = '' / label "string4" / [optional]
  * input parameter #5: (String) string5 / default value = '' / label "string5" / [optional]
  * input parameter #6: (String) string6 / default value = '' / label "string6" / [optional]
  *
  * Note: all input parameters are always converted from strings.
  */
 public void setInputParameter(int parameterNumber, Object parameterValue)
 {
     switch (parameterNumber)
     {
         case 0:
             string1 = (String) parameterValue;
             break;
         case 1:
             string2 = (String) parameterValue;
             break;
         case 2:
             string3 = (String) parameterValue;
             break;
         case 3:
             string4 = (String) parameterValue;
             break;
         case 4:
             string5 = (String) parameterValue;
             break;
         case 5:
             string6 = (String) parameterValue;
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

     output = string1 + string2;
     
     if(!string3.isEmpty())
    	 output += string3;
     
     if(!string4.isEmpty())
    	 output += string4;

     if(!string5.isEmpty())
    	 output += string5;

     if(!string6.isEmpty())
    	 output += string6;

 }
 
 
 /**
  * Return plug-in output parameter. 
  *
  * output parameter #1: (String) output / default value = '' / label "outputString"
  *
  * Note: all output parameters are always converted to strings.
  */
 public Object getOutputParameter(int parameterNumber)
 {
     switch (parameterNumber)
     {
         case 0:
             return output;
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
 
 /*
 public static void main(String[] args)
 {
     try
     {
         // vvv --- sample code --- vvv
         
         StringJoiner plugin = new StringJoiner();
         plugin.construct(null);
         plugin.setInputParameter(0, "abc");
         plugin.setInputParameter(1, "def");
         //plugin.setInputParameter(2, "ghi");
         //plugin.setInputParameter(3, "jkl");
         plugin.setInputParameter(4, "mno");
         //plugin.setInputParameter(5, "pqr");
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
*/


}    // end of class
