// Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer AG, Switzerland.
// Copyright for manual written code belongs to <your name>, <your company>, <your country>
//package test1;

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
public class AddRequestHeader implements LoadtestPluginInterface
{
	private String reqHeaderName = "";	// input parameter #1 - label "Request Header Name"
	private String reqHeaderValue = "";	// input parameter #2 - label "Request Header Value"
	
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
		return "Add a Request Header";
	}
	
	
	public String getPluginDescription()
	{
		return "";
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
		return false;
	}
	
	
	public String[] getInputParameterLabels()
	{
		String[] labels = new String[2];
		labels[0] = "Request Header Name";
		labels[1] = "Request Header Value";
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
		String[] labels = new String[0];
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
	 * Initialize plug-in at start of loop (new instance per loop).
	 */
	public void construct(Object context)
	{
		// LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
	}
	
	
	/**
	 * Transfer input parameter before execute() is called.
	 *
	 * input parameter #1: (boolean) vTriggerSnapshot / default value = 'false' / label "Trigger Snapshot"
	 *
	 * Note: all input parameters are always converted from strings.
	 */
	public void setInputParameter(int parameterNumber, Object parameterValue)
	{
		switch (parameterNumber)
		{
			case 0:
				reqHeaderName = (String) parameterValue;
				break;
			case 1:
				reqHeaderValue = (String) parameterValue;
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * Execute plug-in before every URL call.
	 *
	 * Intrinsic plug-in implementation.
	 */
	public void execute(Object context)
	{
		logVector = new LogVector();
		LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;

		HttpTestURL httpTestURL = pluginContext.getHttpTestURL();
		dfischer.utils.HttpRequestHeader requestHeader = httpTestURL.getRequestHeaderObject();
		
		if(reqHeaderName.isEmpty())
			logVector.log("Warning! empty request header name passed");

		requestHeader.setHeaderField(reqHeaderName, reqHeaderValue);
		
		logVector.log(String.format("Adding custom header: %s-%s", reqHeaderName, reqHeaderValue));
	}
	
	
	/**
	 * Return plug-in output parameter. 
	 */
	public Object getOutputParameter(int parameterNumber)
	{
		switch (parameterNumber)
		{
			default:
				return null;
		}
	}
	
	
	/**
	 * Finalize plug-in at end of loop.
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
			
			AddRequestHeader plugin = new AddRequestHeader();
			plugin.construct(null);
			plugin.setInputParameter(0, "");
			plugin.setInputParameter(1, args[1]);
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
