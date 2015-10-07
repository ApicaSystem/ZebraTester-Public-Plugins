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
public class GetPercentOfANumber implements LoadtestPluginInterface
{
	private float number = -1;		// input parameter #1 - label "Number"
	private float percent = -1;		// input parameter #2 - label "Percent"
	
	private int result = -1;		// output parameter #1 - label "Result"
	
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
		return "GetPercentOfANumber";
	}
	
	
	public String getPluginDescription()
	{
		return "plugin to return the percent of a number. First input is the number to get percent from and second number is the percent.\n\n";
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
		String[] labels = new String[2];
		labels[0] = "Number";
		labels[1] = "Percent";
		return labels;
	}
	
	
	public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
	{
		return null;
	}
	
	
	public int allowOptionalInputParameter()
	{
		return -1;		// all input parameters required
	}
	
	
	public String[] getOutputParameterLabels()
	{
		String[] labels = new String[1];
		labels[0] = "Result";
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
	 * input parameter #1: (float) number / default value = '-1' / label "Number"
	 * input parameter #2: (float) percent / default value = '-1' / label "Percent"
	 *
	 * Note: all input parameters are always converted from strings.
	 */
	public void setInputParameter(int parameterNumber, Object parameterValue)
	{
		switch (parameterNumber)
		{
			case 0:
				number = Float.valueOf((String) parameterValue).floatValue();
				break;
			case 1:
				percent = Float.valueOf((String) parameterValue).floatValue();
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
		
		result = Math.round((number/100)*percent);
	}
	
	
	/**
	 * Return plug-in output parameter. 
	 *
	 * output parameter #1: (int) result / default value = '-1' / label "Result"
	 *
	 * Note: all output parameters are always converted to strings.
	 */
	public Object getOutputParameter(int parameterNumber)
	{
		switch (parameterNumber)
		{
			case 0:
				return "" + result;		// convert to string
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
			
			GetPercentOfANumber plugin = new GetPercentOfANumber();
			plugin.construct(null);
			plugin.setInputParameter(0, "275");
			plugin.setInputParameter(1, "50");
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));
			plugin.deconstruct(null);
			plugin.setInputParameter(0, "137");
			plugin.setInputParameter(1, "50");
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));
			plugin.setInputParameter(0, "129");
			plugin.setInputParameter(1, "73");
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));
			plugin.setInputParameter(0, "10");
			plugin.setInputParameter(1, "25");
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));
			
			// ^^^ --- sample code --- ^^^
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
*/	
 
 
}	// end of class

