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
import javax.script.*;
 
/**
 * Load test add-on module.
 */
public class EvaluateJavaScript implements LoadtestPluginInterface
{
	private String EvaluateJavaScript_expression = "";		// input parameter #1 - label "JavaScript Expression:"
	
	private String EvaluateJavaScript_outputParameter = "";		// output parameter #1 - label "Output Parameter:"
	
	private LogVector logVector = null;		// internal log vector - use logVector.log(<String>) to write log data
	
	private ScriptEngineManager manager = new ScriptEngineManager();
	private ScriptEngine engine = manager.getEngineByName("js");
	
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
		return "Evaluate JavaScript";
	}
	
	
	public String getPluginDescription()
	{
		return "This weird little plugin allows for the evaluation of arbitrary JavaScript expressions.\n\n";
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
		String[] labels = new String[1];
		labels[0] = "JavaScript Expression:";
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
		labels[0] = "Output Parameter:";
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
	 * input parameter #1: (String) EvaluateJavaScript_expression / default value = '' / label "JavaScript Expression:"
	 *
	 * Note: all input parameters are always converted from strings.
	 */
	public void setInputParameter(int parameterNumber, Object parameterValue)
	{
		switch (parameterNumber)
		{
			case 0:
				EvaluateJavaScript_expression = (String) parameterValue;
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
		Object result = null;
		logVector = new LogVector();
		LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
		
		try
		{
			result = engine.eval(EvaluateJavaScript_expression);
		}
		catch (ScriptException e)
		{
			logVector.log("Invalid JavaScript expression: " + EvaluateJavaScript_expression);
			pluginContext.getHttpLoadTest().triggerAbort();
		}
		logVector.log("result of JavaScript eval: " + result);
		EvaluateJavaScript_outputParameter = result.toString();
		
		PerformanceData performanceData = pluginContext.getPerformanceData();
		long passedLoops = performanceData.getPassedLoops();
		long failedLoops = performanceData.getFailedLoops();
		
		int scope = pluginContext.getContextScope();
		
		if (false)		// internal error ?
			throw new RuntimeException("internal error in plug-in occured");
		
		// ^^^ --- sample code --- ^^^
	}
	
	
	/**
	 * Return plug-in output parameter. 
	 *
	 * output parameter #1: (String) EvaluateJavaScript_outputParameter / default value = '' / label "Output Parameter:"
	 *
	 * Note: all output parameters are always converted to strings.
	 */
	public Object getOutputParameter(int parameterNumber)
	{
		switch (parameterNumber)
		{
			case 0:
				return EvaluateJavaScript_outputParameter;
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
			
			EvaluateJavaScript plugin = new EvaluateJavaScript();
			plugin.construct(null);
			plugin.setInputParameter(0, args[0]);
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


