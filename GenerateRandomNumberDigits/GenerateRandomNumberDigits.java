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
public class GenerateRandomNumberDigits implements LoadtestPluginInterface
{
	private int vDigits = -1;			// input parameter #1 - label "Number of Digits"

	private String vResult = "";		// output parameter #1 - label "Random Number"

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
		return "Generate Random Number Digits";
	}


	public String getPluginDescription()
	{
		return "Generate a (natural) random number with a specified length (number of digits).\n\nThe result is returned as a string of fixed length.";
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
		labels[0] = "Number of Digits";
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
		labels[0] = "Random Number";
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
	 * input parameter #1: (long) vDigits / default value = '-1' / label "Number of Digits"
	 *
	 * Note: all input parameters are always converted from strings.
	 */
	public void setInputParameter(int parameterNumber, Object parameterValue)
	{
		switch (parameterNumber)
		{
			case 0:
				vDigits = Integer.valueOf((String) parameterValue).intValue();
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

		vResult = "";
		while (vResult.length() < vDigits)
		{
			String randomStr = "" + Math.random();
			if ((randomStr.length() > 5) && randomStr.startsWith("0."))
				vResult = vResult + randomStr.substring(2);
		}

		vResult = vResult.substring(0, vDigits);
	}


	/**
	 * Return plug-in output parameter.
	 *
	 * output parameter #1: (String) vResult / default value = '' / label "Random Number"
	 *
	 * Note: all output parameters are always converted to strings.
	 */
	public Object getOutputParameter(int parameterNumber)
	{
		switch (parameterNumber)
		{
			case 0:
				return vResult;
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
			// vvv --- test code --- vvv

			GenerateRandomNumberDigits plugin = new GenerateRandomNumberDigits();
			plugin.construct(null);

			plugin.setInputParameter(0, args[0]);
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));


			plugin.setInputParameter(0, args[0]);
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));


			plugin.setInputParameter(0, args[0]);
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));


			plugin.setInputParameter(0, args[0]);
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));


			plugin.setInputParameter(0, args[0]);
			plugin.execute(null);
			System.out.println(plugin.getOutputParameter(0));

			plugin.deconstruct(null);

			// ^^^ --- test code --- ^^^
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



}	// end of class

