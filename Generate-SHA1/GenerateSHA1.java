package plugin;

//Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer AG, Switzerland.
//Copyright for manual written code belongs to <your name>, <your company>, <your country>
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import dfischer.prxutils.MD4;
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
public class GenerateSHA1 implements LoadtestPluginInterface
{
	private String string1 = "";			// input parameter #1
	
	private String string2 = "";			// input parameter #2

	private String vResult = "";		// output parameter #1 - label "SHA1 encoding of value1 + value2"

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
		return "Generate SHA1 from 2 strings";
	}


	public String getPluginDescription()
	{
		return "Generate a SHA1 encoded value from appending string1 with string 2";
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
		labels[0] = "1st string value";
		labels[1] = "2nd string value";
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
		labels[0] = "SHA1 encoded output";
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
				string1 = parameterValue.toString();
				break;
			case 1:
				string2 = parameterValue.toString();
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
		if(string1 == null || string2 == null)
			return;
		
		logVector = new LogVector();
		LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;

		vResult = "";
		MessageDigest md = null;
		try{
			md = MessageDigest.getInstance("SHA-1");
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		md.reset();
		
		String tempString = string1 + string2;
		vResult = new String(Hex.encodeHex(md.digest(tempString.getBytes())));

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

			GenerateSHA1 plugin = new GenerateSHA1();
			plugin.construct(null);

			plugin.setInputParameter(0, args[0]);
			plugin.setInputParameter(1, args[1]);
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

