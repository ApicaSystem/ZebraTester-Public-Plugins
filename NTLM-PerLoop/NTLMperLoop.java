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

import dfischer.utils.NtlmConfig;


/**
 * Load test add-on module.
 */
public class NTLMperLoop implements LoadtestPluginInterface
{
	private String vDomain = "";		// input parameter #1 - label "Domain"
	private String vUsername = "";		// input parameter #2 - label "Username"
	private String vPassword = "";		// input parameter #3 - label "Password"

	private LogVector logVector = null;		// internal log vector - use logVector.log(<String>) to write log data

	private NtlmConfig ntlmConfig = null;


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
		return "NTLM per Loop";
	}


	public String getPluginDescription()
	{
		return "Supports NTLM Authentication per loop.\n\n";
	}


	public int getAllowedConstructScope()
	{
		return LoadtestPluginInterface.CONSTRUCT_SCOPE_LOOP;
	}


	public int getAllowedExecScope()
	{
		return LoadtestPluginInterface.EXEC_SCOPE_ALL_URLS;
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
		String[] labels = new String[3];
		labels[0] = "Domain";
		labels[1] = "Username";
		labels[2] = "Password";
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
	 * input parameter #1: (String) vDomain / default value = '' / label "Domain"
	 * input parameter #2: (String) vUsername / default value = '' / label "Username"
	 * input parameter #3: (String) vPassword / default value = '' / label "Password"
	 *
	 * Note: all input parameters are always converted from strings.
	 */
	public void setInputParameter(int parameterNumber, Object parameterValue)
	{
		switch (parameterNumber)
		{
			case 0:
				vDomain = (String) parameterValue;
				break;
			case 1:
				vUsername = (String) parameterValue;
				break;
			case 2:
				vPassword = (String) parameterValue;
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

		if (ntlmConfig == null)
			ntlmConfig = new NtlmConfig(NtlmConfig.NTLM_V2, vDomain, vUsername, vPassword, true);

		LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
		HttpTestURL httpTestURL = pluginContext.getHttpTestURL();
		httpTestURL.setNtlmAuthentication(ntlmConfig);
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

			NTLMperLoop plugin = new NTLMperLoop();
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

