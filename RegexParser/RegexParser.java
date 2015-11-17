// Some parts of this code have been automatically generated - copyright for generic plug-in procedure reserved by Ingenieurbuero David Fischer GmbH, Switzerland.
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 
/**
 * Load test add-on module.
 */
public class RegexParser implements LoadtestPluginInterface
{
	private String user_input = "";		// input parameter #1 - label "String"
	private String user_input_2 = "";
	
	private String output_result = "";		// output parameter #1 - label "Output String"
	
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
		return "Basic RegexParser";
	}
	
	
	public String getPluginDescription()
	{
		return "Basic RegexParser By WIIK, picks first match.";
	}
	
	
	public int getAllowedConstructScope()
	{
		return LoadtestPluginInterface.CONSTRUCT_SCOPE_GLOBAL;
	}
	
	
	public int getAllowedExecScope()
	{
		return LoadtestPluginInterface.EXEC_SCOPE_URL;
	}
	
	
	public int getAllowedExecOrder()
	{
		return LoadtestPluginInterface.EXEC_ORDER_AFTER;
	}
	
	
	public boolean allowMultipleUsage()
	{
		return true;
	}
	
	
	public String[] getInputParameterLabels()
	{
		String[] labels = new String[2];
		labels[0] = "RAWCONTENT";
		labels[1] = "Regular Expression , Example \' seatID=\"(.*?\") \'";
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
		labels[0] = "Output String";
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
	 * input parameter #1: (String) user_input / default value = '' / label "String"
	 *
	 * Note: all input parameters are always converted from strings.
	 */
	public void setInputParameter(int parameterNumber, Object parameterValue)
	{
		switch (parameterNumber)
		{
			case 0:
				user_input = (String) parameterValue;
				break;
			case 1:
				user_input_2 = (String) parameterValue;
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * Execute plug-in after URL call.
	 *
	 * Intrinsic plug-in implementation.
	 */
	public void execute(Object context)
	{
		logVector = new LogVector();
		LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
		
		// vvv --- sample code --- vvv
		
		PerformanceData performanceData = pluginContext.getPerformanceData();
		long passedLoops = performanceData.getPassedLoops();
		long failedLoops = performanceData.getFailedLoops();
		
		int userNr = pluginContext.getUserNr();
		CookieHandler cookieHandler = pluginContext.getCookieHandler();
		
	
		
		HttpTestURL httpTestURL = pluginContext.getHttpTestURL();
		if (pluginContext.urlPassed())
		{
			// Regex Code starts here
			
				
			logVector.log("******PLUGIN_DEBUG****** Content To Search IN" + user_input );
			logVector.log("******PLUGIN_DEBUG****** Regular Expression " + user_input_2 );
					
						Pattern pattern = Pattern.compile(user_input_2);

						Matcher matcher = pattern.matcher(user_input);
						if (matcher.find())
						{
  						
			logVector.log("******PLUGIN_DEBUG****** RegexMatch: " + matcher.group(1));
								String str = matcher.group(1);

			output_result = str;
						}
				
		}
		else
		{
			// URL call failed
			int httpStatus = httpTestURL.getStatus();
			byte[] responseContent = httpTestURL.getDecompressedContent();
		}
		
		if (false)		// abort load test ?
		{
			logVector.log("*** load test aborted by plug-in ***");
			pluginContext.getHttpLoadTest().triggerAbort();
		}
		
		if (false)		// internal error ?
			throw new RuntimeException("internal error in plug-in occured");
		
		// ^^^ --- sample code --- ^^^
	}
	
	
	/**
	 * Return plug-in output parameter. 
	 *
	 * output parameter #1: (String) output_result / default value = '' / label "Output String"
	 *
	 * Note: all output parameters are always converted to strings.
	 */
	public Object getOutputParameter(int parameterNumber)
	{
		switch (parameterNumber)
		{
			case 0:
				return output_result;
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
			
			RegexParser plugin = new RegexParser();
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

