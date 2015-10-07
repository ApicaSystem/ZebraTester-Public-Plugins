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

import java.util.Random;

	 
	/**
	 * Load test add-on module.
	 */
	public class AppDynamicsIntegrationV3 implements LoadtestPluginInterface
	{
		private boolean vTriggerSnapshot = false;		// input parameter #1 - label "Trigger Snapshot"
		private String vLoopRandomNumber = "";
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
			return "AppDynamics Integration V3";
		}
		
		
		public String getPluginDescription()
		{
			return "";
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
			return true;
		}
		
		
		public String[] getInputParameterLabels()
		{
			String[] labels = new String[1];
			labels[0] = "Trigger Snapshot";
			return labels;
		}
		
		
		public LoadtestPluginFixedUserInputField[] getFixedUserInputFields()
		{
			LoadtestPluginFixedUserInputField[] userInputFields = new LoadtestPluginFixedUserInputField[1];
			userInputFields[0] = new LoadtestPluginFixedUserInputField("vTriggerSnapshot", true, "false");
			return userInputFields;
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

			//long range = 9999999999999L;	// will give you a long between 0 (inclusive) and range (exclusive)
			//Random r = new Random();
			vLoopRandomNumber = generateRandomCharacters(13);
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
					vTriggerSnapshot = (((String) parameterValue).equalsIgnoreCase("1") || ((String) parameterValue).equalsIgnoreCase("true"));
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

			//int userNr = pluginContext.getUserNr();

			HttpTestURL httpTestURL = pluginContext.getHttpTestURL();
			dfischer.utils.HttpRequestHeader requestHeader = httpTestURL.getRequestHeaderObject();

			requestHeader.setHeaderField("ApicaGUID", "" + vLoopRandomNumber);
			
			if(vTriggerSnapshot)
				requestHeader.setHeaderField("AppDynamicsSnapshotEnabled","true");

			System.out.println(vLoopRandomNumber);
			logVector.log("ApicaGUID = " + vLoopRandomNumber);
			logVector.log("AppDynamicsSnapshotEnabled = " + vTriggerSnapshot);
		}
		
	    public String generateRandomCharacters(int length){
	    	
	     	String output = "";
			while (output.length() < length)
			{
			    int rnd = (int) (Math.random() * 62);
			    if(rnd >= 52){
			    	output += (rnd-52)+"";
			    	continue;
			    }
			    char base = (rnd < 26) ? 'A' : 'a';
			    output += (char)(base + rnd % 26);
			}	   
			
			return output;
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
				
				AppDynamicsIntegrationV3 plugin = new AppDynamicsIntegrationV3();
				plugin.construct(null);
				plugin.setInputParameter(0, "true");
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
