import com.amazonaws.apica.Apica_Cognito_Handler;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;

import dfischer.utils.*;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Cognito_SRP_Handler implements LoadtestPluginInterface
{

    private String userPoolID = "";
    private String clientID = "";
    private String username = "";
    private String password = "";
    private String result = "";

    private LogVector logVector = null;		// internal log vector - use logVector.log(<String>) to write log data


    public int getPluginType()
    {
        return GenericPluginInterface.TYPE_LOADTEST_EXEC;
    }


    public String getPluginName()
    {
        return "Cognito_SRP_Handler";
    }


    public String getPluginDescription()
    {
        return "Plugin that performs a USER_SRP_AUTH authentication against AWS Cognito using the provided variables and returns an auth token.";
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
        String[] labels = new String[4];
        labels[0] = "UserPoolID";
        labels[1] = "ClientID";
        labels[2] = "Username";
        labels[3] = "Password";
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
        labels[0] = "AuthToken";
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
        LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;

        logVector = new LogVector();

    }


    /**
     * Transfer input parameter before execute() is called.
     *
     * input parameter #1: (String) vCookieName / default value = '' / label "Cookie Name"
     *
     * Note: all input parameters are always converted from strings.
     */
    public void setInputParameter(int parameterNumber, Object parameterValue)
    {
        switch (parameterNumber)
        {
            case 0:
                userPoolID = (String) parameterValue;
                break;
            case 1:
                clientID = (String) parameterValue;
                break;
            case 2:
                username = (String) parameterValue;
                break;
            case 3:
                password = (String) parameterValue;
                break;
            default:
                break;
        }
    }


    public void execute(Object context)
    {

        LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
        Apica_Cognito_Handler apica_cognito_handler = new Apica_Cognito_Handler(userPoolID,clientID);
        result = apica_cognito_handler.performAuthentication(pluginContext,username,password);
    }

    /**
     * Return plug-in output parameter.
     */
    public Object getOutputParameter(int parameterNumber)
    {
        switch (parameterNumber)
        {
            case 0:
                return result;
            default:
                return null;
        }
    }


    /**
     * Finalize plug-in at end of load test.
     */
    public void deconstruct(Object context)
    {
        LoadtestPluginContext pluginContext = (LoadtestPluginContext) context;
    }

    public static void main(String []args){

    }




}
