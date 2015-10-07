//package test1;
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
public class trimString implements LoadtestPluginInterface
{
	private String inputString = "";		// input parameter #1 - label "Input string"
	private int trimFront = 0;		// input parameter #2 - label "number of chars to trim front"
	private int trimBack = 0;		// input parameter #3 - label "number of chars to trim back"
	
	private String outputString = "";		// output parameter #1 - label "Output string"
	
	private LogVector logVector = null;		// internal log vector - use logVector.log(<String>) to write log data
	
	
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
		return "TrimString";
	}
	
	
	public String getPluginDescription()
	{
		return "plugin to trim a string from the front and back with user specified length as integer.\n\n";
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
		String[] labels = new String[3];
		labels[0] = "Input string";
		labels[1] = "number of chars to trim front";
		labels[2] = "number of chars to trim back";
		return labels;
	}
	
	
	public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
	{
		return null;
	}
	
	
	public int allowOptionalInputParameter()
	{
		return 1;		// optional input parameters starting from parameter #2
	}
	
	
	public String[] getOutputParameterLabels()
	{
		String[] labels = new String[1];
		labels[0] = "Output string";
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
		// LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
	}
	
	
	/**
	 * Transfer input parameter before execute() is called.
	 *
	 * input parameter #1: (String) inputString / default value = '' / label "Input string"
	 * input parameter #2: (int) trimFront / default value = '0' / label "number of chars to trim front" / [optional]
	 * input parameter #3: (int) trimBack / default value = '0' / label "number of chars to trim back" / [optional]
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
				trimFront = Integer.valueOf((String) parameterValue).intValue();
				break;
			case 2:
				trimBack = Integer.valueOf((String) parameterValue).intValue();
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
		
		// vvv --- sample code --- vvv
		/*
		PerformanceData performanceData = pluginContext.getPerformanceData();
		long passedLoops = performanceData.getPassedLoops();
		long failedLoops = performanceData.getFailedLoops();
		
		int scope = pluginContext.getContextScope();
		
		if (false)		// internal error ?
			throw new RuntimeException("internal error in plug-in occured");
		*/
		// ^^^ --- sample code --- ^^^
		
		int length = inputString.length();
		if((trimFront+trimBack) > length){
			logVector.log("Selected values for number of characters to trim front and back exceeds length of input string");
			throw new StringIndexOutOfBoundsException();
		}
			
		outputString = inputString.substring(trimFront, inputString.length() - trimBack);
	}
	
	
	/**
	 * Return plug-in output parameter. 
	 *
	 * output parameter #1: (String) outputString / default value = '' / label "Output string"
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
	
	/*
	public static void main(String[] args)
	{
		try
		{
			// vvv --- sample code --- vvv
			
			trimString plugin = new trimString();
			plugin.construct(null);
			plugin.setInputParameter(0, "123jnijdwsnpkj7");
			plugin.setInputParameter(1, "8");
			plugin.setInputParameter(2, "4");
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
 
 
}	// end of class


