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
public class ExtractSubstringInBetween implements LoadtestPluginInterface
{
	private String inputString = "";		// input parameter #1 - label "Input string"
	private String fromString = "";		// input parameter #2 - label "Extract from string"
	private String toString = "";		// input parameter #3 - label "Extract to string"
	private boolean includeExtractStrings = false;		// input parameter #4 - label "Include extract strings"
	
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
		return "Extract Substring In Between";
	}
	
	
	public String getPluginDescription()
	{
		return "extracts a substring in between two given strings\n\n";
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
		String[] labels = new String[4];
		labels[0] = "Input string";
		labels[1] = "Extract from string";
		labels[2] = "Extract to string";
		labels[3] = "Include extract strings";
		return labels;
	}
	
	
	public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
	{
	     LoadtestPluginFixedUserInputField[] userInputFields = new LoadtestPluginFixedUserInputField[4];
	     userInputFields[0] = null;
	     userInputFields[1] = new LoadtestPluginFixedUserInputField("fromString", false, "");
	     userInputFields[2] = new LoadtestPluginFixedUserInputField("toString", false, "");
	     userInputFields[3] = new LoadtestPluginFixedUserInputField("includeExtractStrings", false, "false");
	     return null;
	}
	
	
	public int allowOptionalInputParameter()
	{
		return 3;		// optional input parameters starting from parameter #4
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
	 * input parameter #2: (String) fromString / default value = '' / label "Extract from string"
	 * input parameter #3: (String) toString / default value = '' / label "Extract to string"
	 * input parameter #4: (boolean) includeExtractStrings / default value = 'false' / label "Include extract strings" / [optional]
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
				fromString = (String) parameterValue;
				break;
			case 2:
				toString = (String) parameterValue;
				break;
			case 3:
				includeExtractStrings = (((String) parameterValue).equalsIgnoreCase("1") || ((String) parameterValue).equalsIgnoreCase("true"));
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

		if(!inputString.contains(fromString) || !inputString.contains(toString)){
			logVector.log("input string doesn't contain one or both extract strings");
			//System.err.println("input string doesn't contain extract strings");
			throw new StringIndexOutOfBoundsException();
		}

		int fromIndex = fromString.length();
		//int toIndex = toString.length();
		
		String subString = inputString.substring(inputString.indexOf(fromString)+fromIndex);
				
		outputString = subString.substring(0, subString.indexOf(toString)); 
		
		if(includeExtractStrings)
			outputString = fromString.concat(outputString).concat(toString);
		
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
			
			ExtractSubstringInBetween plugin = new ExtractSubstringInBetween();
			plugin.construct(null);
			plugin.setInputParameter(0, "http://stage.now.video.nfl.com/i/2014/NFL_NOW/SHOW/NFL_NOW/OFF/1/140718_NFL_NOW_BOW_REDSKINS_49129_,180k,320k,500k,700k,1200k,2000k,3200k,5000k,.mp4.csmil/master.m3u8");
			plugin.setInputParameter(1, ".kcom");
			plugin.setInputParameter(2, "3200k");
			plugin.setInputParameter(3, "false");
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
