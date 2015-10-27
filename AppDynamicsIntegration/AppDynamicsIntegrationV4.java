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

import java.util.Random;

	 
	/**
	 * Load test add-on module.
	 */
	public class AppDynamicsIntegrationV4 implements LoadtestPluginInterface
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
			return "AppDynamics Integration V4";
		}
		
		
		public String getPluginDescription()
		{
			return "Plugin to insert custom request headers that can be used with AppDynamics to correlate data with Apica.\n"+
					"Headers: ApicaGUID: (some GUID), ApicaPageName: (Page Break's name/Transaction name)\n"+
					"Set vTriggerSnapshot to true to enable plugin, otherwise false";
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
			vLoopRandomNumber = generateGUID();
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
			String pageName = pluginContext.getPerformanceData().getPageInfoTextOfUrl(pluginContext.getThreadStep());
			
			requestHeader.setHeaderField("ApicaGUID", "" + vLoopRandomNumber);
			
			if(vTriggerSnapshot){
				requestHeader.setHeaderField("AppDynamicsSnapshotEnabled","true");
				requestHeader.setHeaderField("ApicaPageName", pageName);
			}

			System.out.println(vLoopRandomNumber);
			logVector.log("ApicaGUID = " + vLoopRandomNumber);
			logVector.log("ApicaPageName = " + pageName);
			logVector.log("AppDynamicsSnapshotEnabled = " + vTriggerSnapshot);
		}
		
	    public String generateGUID(){
	    	
	     	String guid_output = "";
	     	String guid_format = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
	        for(char ch : guid_format.toCharArray()){
	       	 if(ch == 'x')
	       		 guid_output += Integer.toHexString((int) ((Math.random()*16)));
	       	 else
	       		 guid_output += ch;
	        }
			
			return guid_output;
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
				
				AppDynamicsIntegrationV4 plugin = new AppDynamicsIntegrationV4();
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
