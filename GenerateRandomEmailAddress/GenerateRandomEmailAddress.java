
// Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer AG, Switzerland.
// Copyright for manual written code belongs to <your name>, <your company>, <your country>
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
public class GenerateRandomEmailAddress implements LoadtestPluginInterface
{
    private int userLength = 8;        // input parameter #1 - label "Length of email username"
    private String domain = "@apicatest.com";        // input parameter #2 - label "Domain name (@domain.com)"
    private String prefix = "";
    private String suffix = "";
    
    private String emailAddress = "";        // output parameter #1 - label "Generated email address"
    
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
        return "Generate Random Email Address";
    }
    
    
    public String getPluginDescription()
    {
        return "Plugin to generate random email addresses\n\n";
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
        String[] labels = new String[4];
        labels[0] = "Domain name (optional/default: @apicatest.com)";
        labels[1] = "Length of email username (optional/default 8 characters long)";
        labels[2] = "Email prefix (optional)";
        labels[3] = "Email suffix (optional)";
        return labels;
    }
    
    
    public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
    {
 		LoadtestPluginFixedUserInputField[] userInputFields = new LoadtestPluginFixedUserInputField[4];
	    userInputFields[0] = new LoadtestPluginFixedUserInputField("domain", false, "");
	    userInputFields[1] = new LoadtestPluginFixedUserInputField("userLength", false, "");
	    userInputFields[2] = new LoadtestPluginFixedUserInputField("prefix", false, "");
	    userInputFields[3] = new LoadtestPluginFixedUserInputField("suffix", false, "");
     	return userInputFields;
    }
    
    
    public int allowOptionalInputParameter()
    {
        return 0;
    }
    
    
    public String[] getOutputParameterLabels()
    {
        String[] labels = new String[1];
        labels[0] = "Generated email address";
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
     * input parameter #1: (String) domain / default value = '@apicatest.com' / label "Domain name (@domain.com)"
     * input parameter #2: (int) userLength / default value = '6' / label "Length of email username"
     *
     * Note: all input parameters are always converted from strings.
     */
    public void setInputParameter(int parameterNumber, Object parameterValue)
    {
		//System.out.println((String)parameterValue);
		String inputField = getFixedUserInputFields()[parameterNumber].getDefaultValue();
		
        switch (parameterNumber)
        {
            case 0:
                domain = inputField.isEmpty() ? (String) parameterValue : inputField;
                break;
            case 1:
                userLength = inputField.isEmpty() ? Integer.valueOf((String) parameterValue).intValue() : Integer.valueOf((String) inputField).intValue();
                break;
            case 2:
            	prefix = inputField.isEmpty() ? (String) parameterValue : inputField;
            	break;
            case 3:
            	suffix = inputField.isEmpty() ? (String) parameterValue : inputField;
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

		String temp = "";
		while (temp.length() < userLength)
		{
		    int rnd = (int) (Math.random() * 52);
		    char base = (rnd < 26) ? 'A' : 'a';
		    temp += (char)(base + rnd % 26);
		}

		emailAddress = String.format("%s%s%s%s", prefix, temp.substring(0, userLength), suffix, domain);
		logVector.log("generated email: "+emailAddress);
    }
    
    
    /**
     * Return plug-in output parameter.
     *
     * output parameter #1: (String) emailAddress / default value = '' / label "Generated email address"
     *
     * Note: all output parameters are always converted to strings.
     */
    public Object getOutputParameter(int parameterNumber)
    {
        switch (parameterNumber)
        {
            case 0:
                return emailAddress;
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
            
            GenerateRandomEmailAddress plugin = new GenerateRandomEmailAddress();
            plugin.construct(null);
            plugin.setInputParameter(0, "@gmail.com");
            plugin.setInputParameter(1, "6");
            plugin.setInputParameter(2, "spoploadtest+apica_");
            //plugin.setInputParameter(3, "-apica");
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
