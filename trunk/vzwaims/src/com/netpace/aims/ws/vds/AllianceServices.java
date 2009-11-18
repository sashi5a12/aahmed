package com.netpace.aims.ws.vds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.ws.ThirdpartyServiceException;

public class AllianceServices {

	// method name, parameters and response keys
	private static final String AUTHENTICATION_HANDLER = "AuthenticationHandler";
	private static final String LOG_USER_IN_METHOD = "logUserIn";
	private static final String LOG_USER_OUT_METHOD = "logUserOut";

	private static final String PARAM_USERNAME = "username";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_AUTH_KEY = "authKey";

	private static final String DEVELOPER_HANDLER = "DeveloperHandler";

	public static final String CREATE_METHOD = "create";
	public static final String UPDATE_METHOD = "update";
	public static final String DELETE_METHOD = "delete";
	public static final String GET_METHOD = "get";

	private static final String PARAM_DEVELOPER = "developer";
	private static final String PARAM_DEVELOPERID = "developerLoginId";
	private static final String PARAM_LOGINID = "loginId";

	// Response code, success value, real cause and message
	private static final String RESPONSE_CODE = "responseCode";
	private static final String RESPONSE_CODE_SUCCESS = "success";
	private static final String RESPONSE_ERROR_CODE = "responseErrorCode";
	private static final String PARAM_DEVELOPER_LOGINID = "developerLoginId";
	private static final String RESPONSE_MESSAGE = "responseMessage";
	
	private static final String PROP_URL = "com.netpace.aims.ws.allianceService.url";
	private static final String PROP_USERNAME = "com.netpace.aims.ws.allianceService.username";
	private static final String PROP_PASSWORD = "com.netpace.aims.ws.allianceService.password";
	private static final String PROP_DEV_PASSWORD = "com.netpace.aims.ws.allianceService.developer.password";
	private static final String PROP_SERVICE_ENABLE = "com.netpace.aims.ws.allianceService.enable";
	
	public static final String NO_RESUBMIT_URL = "none";


	// Error codes returned by web service method call
	private enum wsErorCodes {
		CDS_EX_INVALID_PARAMETER, CDS_EX_INVALID_CREDENTIALS, CDS_EX_ACCOUNT_DISABLED, 
		CDS_EX_PASSWORD_CHANGE_REQUIRED, CDS_EX_INTERNAL_ERROR, CDS_EX_UNTRUSTED_HOST, 
		CDS_EX_INVALID_AUTHKEY, CDS_EX_USER_NOT_AUTHORIZED, CDS_EX_OBJECT_NOT_FOUND
	}

	private boolean serviceTurnOn = false;
	private String url = null;
	private String username = null;
	private String password = null;
	private XmlRpcClient client = null;
	private String developerPassword = null;

	private static Logger log = Logger.getLogger(AllianceServices.class
			.getName());

	public AllianceServices() throws ThirdpartyServiceException {
		this(				
				ConfigEnvProperties.getInstance().getProperty(PROP_URL),
				ConfigEnvProperties.getInstance().getProperty(PROP_USERNAME),
				ConfigEnvProperties.getInstance().getProperty(PROP_PASSWORD),
				ConfigEnvProperties.getInstance().getProperty(PROP_DEV_PASSWORD),
				"true".equalsIgnoreCase(ConfigEnvProperties.getInstance().getProperty(PROP_SERVICE_ENABLE)) ? true	: false);
		
		
	}

	public AllianceServices(String url, String username, String password,
			String developerPassword, boolean serviceTurnOn)
			throws ThirdpartyServiceException {
		this.url = url;
		this.username = username;
		this.password = password;
		this.developerPassword = developerPassword;
		this.serviceTurnOn = serviceTurnOn;

		if (log.isDebugEnabled()) {
			log.debug(new StringBuffer()
					.append("Creating Alliance Service with  ").append("\n")
					.append(" username : ").append(this.username).append("\n")
					.append(" password : ").append(this.password).append("\n")
					.append(" url : ").append(this.url).append("\n")
					.append(" service truenOn : ").append(this.serviceTurnOn).append("\n").toString());
		}

		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		// Get a handle to the xmlrpc client.
		// XmlRpcClient client = null;
		try {
			config.setServerURL(new URL(url));
			client = new XmlRpcClient();
			client.setConfig(config);
		} catch (MalformedURLException e) {
			// Something is wrong with the url.
			log.debug(e, e);
			throw new ThirdpartyServiceException(e);
		}
	}

	private HashMap callMethod(String methodName, HashMap methodParams)
			throws ThirdpartyServiceException {
		HashMap response = null;

		try {
			// Create a vector to hold the request parameters
			Vector request = new Vector();
			// Add the method specific parameters to the request.
			request.addElement(methodParams);
			response = (HashMap) client.execute(methodName, request);
		} catch (XmlRpcException e) {
			log.debug(e, e);
			throw new ThirdpartyServiceException(e);
		}

		if (log.isDebugEnabled())
			log.debug("'" + methodName + "' response: " + response);

		// Process the results: check to see if there was an error.
		if (response == null) {
			throw new ThirdpartyServiceException("'" + methodName
					+ "' response is null.");
		}
		return response;
	}
	
	/**
	 * Raise the event for Resubmit
	 * 
	 * @param resubmitUrl
	 * @author mnauman
	 *
	 */
	private void raiseEvent(String methodName, String allianceId, String allianceName, String causeMessage) {
		if (log.isInfoEnabled()) 
			log.info("Enter raiseEvent(), loginId="+allianceId+"methodName"+methodName);
		 
		String resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.allianceService.resubmitUrl");
		resubmitURL += "?method="+methodName;
		resubmitURL += "&allianceId=" + allianceId;
		
		AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ALLIANCE_SERVICE_ACCOUNT_DISABLED);

		if (aimsEvent != null) {
			AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
			
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_RESUBMIT_ALLIANCE_SERVICE_URL,resubmitURL);
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT,new Date());
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CAUSE_MESSAGE,causeMessage);
			
			aimsEvent.raiseEvent(aimsEventObject);

			if (log.isInfoEnabled()) 
				log.info("Exit raiseEvent(), resubmitURL="+resubmitURL);

		}
		
	}
	

	/**
	 * Returns the formatted string of error message from the responce
	 * 
	 * @param response
	 * @return
	 * @author mnauman
	 */
	private String getRealErrorCauseMessage(HashMap response) {
		return new StringBuffer().append("\n")
				.append("Responce Code : ")
				.append(((String) response.get(RESPONSE_CODE)))
				.append("\n")
				.append("Responce Error Code : ")
				.append(((String) response.get(RESPONSE_ERROR_CODE)))
				.append("\n")
				.append("Responce Message : ")
				.append(((String) response.get(RESPONSE_MESSAGE)))
				.append("\n")
				.toString();
	}

	/**
	 * Logon to VDS Server
	 * 
	 * @return authentication key
	 */
	private String login() throws ThirdpartyServiceException {

		if (log.isInfoEnabled())
			log.info("Entry Login");

		String authKey = null;
		// Generate the full method name. ( handlerName.methodName )
		String methodName = AUTHENTICATION_HANDLER + "." + LOG_USER_IN_METHOD;
		// Create a HashMap to hold the method specific parameters and add them.
		HashMap<String, Object> methodParams = new HashMap<String, Object>();
		methodParams.put(PARAM_USERNAME, username);
		methodParams.put(PARAM_PASSWORD, password);
		HashMap response = callMethod(methodName, methodParams);
		if( RESPONSE_CODE_SUCCESS.equalsIgnoreCase((String)response.get(RESPONSE_CODE)) == false )
			throw new ThirdpartyServiceException(getRealErrorCauseMessage(response));

		// Get the authKey from the request.
		authKey = (String) response.get(PARAM_AUTH_KEY);
		if (log.isInfoEnabled())
			log.info("Exit Login: Response= " + response);

		return authKey;
	}

	/**
	 * Logoff from VDS Server providing valid authorization key returned from
	 * the logUserIn method.
	 * 
	 * @author mnauman
	 */
	private void logout(String authKey) throws ThirdpartyServiceException {
		if (log.isInfoEnabled())
			log.info("Enter Logout: authKey= " + authKey);

		String methodName = AUTHENTICATION_HANDLER + "." + LOG_USER_OUT_METHOD;
		HashMap<String, Object> methodParams = new HashMap<String, Object>();
		methodParams.put(PARAM_AUTH_KEY, authKey);

		HashMap response = callMethod(methodName, methodParams);

		if (log.isInfoEnabled())
			log.info("Exit Logout: Response= " + response);
	}

	/**
	 * call create developer method on VDS System. 
	 * @param developer
	 * @return loginId
	 */
	public String createDeveloper(Developer developer) throws Exception
	{
		return this.createDeveloper(developer, true);
	}

	/**
	 * call create developer method on VDS System. 
	 * @param developer
	 * @param isNotificationEnabled
	 * @return loginId
	 */
	public String createDeveloper(Developer developer, boolean isNotificationEnabled) throws Exception
	{

		if (log.isInfoEnabled())
			log.info("Enter createDeveloper: developer= "
					+ developer.toString());

		if (serviceTurnOn == false) {
			if (log.isDebugEnabled())
				log.info("Allaince Services are turn-off, to trun-on set flag 'com.netpace.aims.ws.allianceService.enable=true'");
			return null;
		}
		
		HashMap response = null;
		try{
			String authKey = login();
			String methodName = DEVELOPER_HANDLER + "." + CREATE_METHOD;
			HashMap<String, Object> methodParams = new HashMap<String, Object>();
			//set fixed developer password
			developer.setPassword(this.developerPassword);
			methodParams.put(PARAM_AUTH_KEY, authKey);
			methodParams.put(PARAM_DEVELOPER, developer.getRequestParameters());
			response = callMethod(methodName, methodParams);
			if( RESPONSE_CODE_SUCCESS.equalsIgnoreCase((String)response.get(RESPONSE_CODE)) == false )
				throw new ThirdpartyServiceException(getRealErrorCauseMessage(response));
			logout(authKey);
			
		} catch (ThirdpartyServiceException e){
			if(log.isDebugEnabled())
				log.debug("Failed to call VDS webservice method :"+CREATE_METHOD 
						+" for developer :"+developer.getAllianceId()
						+" with error message :"+e.getMessage());
			
			if( isNotificationEnabled )
				raiseEvent(CREATE_METHOD, developer.getAllianceId(), developer.getDeveloperName(), e.getMessage() );
			
			String resubmitURL = null;
			if ( developer != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.allianceService.resubmitUrl");
				resubmitURL += "?method="+CREATE_METHOD;
				resubmitURL += "&allianceId=" + developer.getAllianceId();
			}
			
			AllianceServicesException ase = new AllianceServicesException( e, resubmitURL );
			throw ase;
		}
		
		if (log.isInfoEnabled())
			log.info("Exit createDeveloper: response= " + response);
		
		return developer.getLoginId();
	}

	/**
	 * call update developer method on VDS System.
	 * @param developer
	 * @return loginId
	 */
	public String updateDeveloper(Developer developer) throws ThirdpartyServiceException {
		developer.setPassword(this.developerPassword);

		if (log.isInfoEnabled())
			log.info("Enter updateDeveloper: developer= "+ developer.toString());

		if (serviceTurnOn == false) {
			if (log.isDebugEnabled())
				log.info("Allaince Services are turn-off, to trun-on set flag 'com.netpace.aims.ws.allianceService.enable=true'");
			return null;
		}
		
		HashMap response = null;
		try{
			String authKey = login();
			String methodName = DEVELOPER_HANDLER + "." + UPDATE_METHOD;
			HashMap<String, Object> methodParams = new HashMap<String, Object>();
			//set fixed developer password
			developer.setPassword(this.developerPassword);			
			methodParams.put(PARAM_AUTH_KEY, authKey);
			methodParams.put(PARAM_DEVELOPER, developer.getRequestParameters());
			response = callMethod(methodName, methodParams);
			if( RESPONSE_CODE_SUCCESS.equalsIgnoreCase((String)response.get(RESPONSE_CODE)) == false )
				throw new ThirdpartyServiceException(getRealErrorCauseMessage(response));
			logout(authKey);
			
		} catch (ThirdpartyServiceException e){
			if(log.isDebugEnabled())
				log.debug("Failed to call VDS webservice method :"+UPDATE_METHOD 
						+" for developer :"+developer.getAllianceId()
						+" with error message :"+e.getMessage());
			raiseEvent(UPDATE_METHOD, developer.getAllianceId(), developer.getDeveloperName(), e.getMessage() );
			throw e;
		}
		
		if (log.isInfoEnabled())
			log.info("Exit updateDeveloper: response= " + response);
		
		return developer.getLoginId();
	}
	
	/**
	 * call delete developer method on VDS System.
	 * @param loginId
	 */
	public void deleteDeveloper(Developer developer) throws ThirdpartyServiceException
	{ 
		if (log.isInfoEnabled())
			log.info("Enter deleteDeveloper: longinId="+developer.getLoginId());

		if (serviceTurnOn == false) 
		{
			if (log.isDebugEnabled())
				log.info("Allaince Services are turn-off, to trun-on set flag 'com.netpace.aims.ws.allianceService.enable=true'");
			return;
		}
		HashMap response = null;
		try
		{
			String authKey = login();
			String methodName = DEVELOPER_HANDLER + "." + DELETE_METHOD;
			HashMap<String, Object> methodParams = new HashMap<String, Object>();
	
			if ( log.isDebugEnabled() )
			{
				log.debug("Following are the VDS call request parameters");
				log.debug("		loginid" + developer.getLoginId());
			}
			methodParams.put(PARAM_AUTH_KEY, authKey);
			methodParams.put(PARAM_LOGINID, developer.getLoginId());
			response = callMethod(methodName, methodParams);
			
			if( RESPONSE_CODE_SUCCESS.equalsIgnoreCase((String)response.get(RESPONSE_CODE)) == false )
				throw new ThirdpartyServiceException(getRealErrorCauseMessage(response));
			
			logout(authKey);
		} 
		catch (ThirdpartyServiceException e)
		{
			if(log.isDebugEnabled())
				log.debug("Failed to call VDS webservice method :" + DELETE_METHOD +" for developer :" + developer.getAllianceId() + " with error message :" + e.getMessage());
			raiseEvent(DELETE_METHOD, developer.getAllianceId(), developer.getDeveloperName(), e.getMessage() );
			throw e;
		}
		if (log.isInfoEnabled())
			log.info("Exit deleteDeveloper: response= " + response);
	}

	public Developer getDeveloper(String loginId) throws ThirdpartyServiceException 
	{
		if (log.isInfoEnabled())
			log.info("Enter getDeveloper: loginId= " + loginId);
		String authKey = login();

		String methodName = DEVELOPER_HANDLER + "." + GET_METHOD;
		HashMap<String, Object> methodParams = new HashMap<String, Object>();
		methodParams.put(PARAM_AUTH_KEY, authKey);
		methodParams.put(PARAM_LOGINID, loginId);
		HashMap response = callMethod(methodName, methodParams);
		HashMap developer = (HashMap) response.get("developer");
		Developer devObj = new Developer(developer);

		logout(authKey);
		if (log.isInfoEnabled())
			log.info("Exit getDeveloper: response= " + response);
		return devObj;
	}
	
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DBHelper dbHelper = null;

		try {

			/* 
			ConfigEnvProperties props = ConfigEnvProperties.getInstance();
			Configuration conf = new Configuration();
			dbHelper = DBHelper.getInstance();
			conf.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@172.30.30.54:1521:v902");
			conf.setProperty("hibernate.connection.username", "aims9");
			conf.setProperty("hibernate.connection.password", "aims9");
			dbHelper.sessionFactory = conf.configure().buildSessionFactory();
			*/
			
			/*
			 * Primary server: http://192.18.47.126/admin/main/cm_xml_rpc Backup
			 * server: http://192.18.47.127/admin/main/cm_xml_rpc Test url =
			 * "http://localhost:9999/admin/main/cm_xml_rpc"; VDS Admin username
			 * = "admin"; VDS Admin password = "cdsadmin";
			 */

			AllianceServices allianceServices = new AllianceServices(args[0],
					args[1], args[2], args[3], true);
			/* AllianceServices allianceServices = new AllianceServices(
					"http://192.18.47.126/admin/main/cm_xml_rpc", "admin","dbpass", "cdsadmin", true);
			*/
			
			/*
			 * //required fields private String loginId, password statusEnabled,
			 * developerName,contactName, companyUrl, email //optinal fields
			 * phoneNumber, faxNumber, street1, street2, city, state,
			 * postalCode, countryCode
			 */
			//CREATE
			Developer dev = new Developer();
			dev.setLoginId("12345");
			dev.setPassword("12345");
			dev.setDeveloperName("Dev 1");
			dev.setContactName("James bond");
			dev.setCompanyUrl("http://netpace.com");
			dev.setEmail("dev1@netpace.com");
			dev.setPhoneNumber("510-556-0066");
			dev.setFaxNumber("650-556-0066");
			dev.setStreet1("123 main st");
			dev.setStreet2("suit# 101");
			dev.setCity("San Ramon");
			dev.setState("CA");
			dev.setPostalCode("94583");
			dev.setCountryCode("US");
			//UPDATE
			Developer dev2 = new Developer();
			dev2.setLoginId("12345");
			dev2.setPassword("12345-1");
			dev2.setDeveloperName("Dev 2");
			dev2.setContactName("James bond 2");
			dev2.setCompanyUrl("http://netpace2.com");
			dev2.setEmail("dev2@netpace.com");
			dev2.setPhoneNumber("800-000-9999");
			dev2.setFaxNumber("900-000-8888");
			dev2.setStreet1("123 wall street");
			dev2.setStreet2("suit# 999");
			dev2.setCity("Fremont");
			dev2.setState("CA");
			dev2.setPostalCode("94538");
			dev2.setCountryCode("US");
			
			//LOGIN & LOGOUT 
			String authKey = allianceServices.login();
			allianceServices.logout(authKey);
			
			//CREATE
			//Developer dev4 = allianceServices.getDeveloperForAllianceId(14559L);
			//allianceServices.createDeveloper(dev4);
			//UPDATE
			//allianceServices.updateDeveloper(dev2);
			//DELETE
			//allianceServices.deleteDeveloper(dev);
			
			//GET  
			//Developer dev3 = allianceServices.getDeveloper(dev.getLoginId());
			

		} 
		catch (ThirdpartyServiceException e) 
		{			
			System.out.println("Failed to invoke web service on VDS, " + e.getMessage());
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			System.out.println("Failed to invoke web service on VDS, " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static String safeTruncate(String val, int length)
	{
		if ( StringUtils.isEmpty(val) )
			return val;
		
		if ( val.length() > length )
			return val.substring(0, length);
		
		return val;
	}

	/**
	 * Gets Developer using the vendor Id
	 * @param allianceId
	 * @throws HibernateException
	 * @throws ThirdpartyServiceException
	 * @author mnauman
	 */
	public Developer getDeveloperForAllianceId(Long allianceId)
			throws HibernateException, ThirdpartyServiceException {

		Developer developer = new Developer();
			developer.setAllianceId(allianceId.toString());
		
		Object[] userValues = null;
		Collection AimsAlliance = AllianceManager
				.getAllianceFromAllianceId(new Long(allianceId));

		if ( AimsAlliance.isEmpty() )
			throw new ObjectNotFoundException("Record not Found",allianceId,this.getClass());
		
		for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();) {

			userValues = (Object[]) iter.next();

			if (log.isInfoEnabled())
				log.info("getDeveloperForAllianceId()  Alliance Details = " + userValues);
			
			developer.setCity(String.valueOf(userValues[4]));
			developer.setCompanyUrl(String.valueOf(userValues[24]));
			
			//As per VZW, truncate companyName to 50 chars
			developer.setContactName( safeTruncate(String.valueOf(userValues[20]) + " " + String.valueOf(userValues[21]), 50));			
			
			//As per VZW, truncate companyName to 50 chars
			developer.setDeveloperName(  safeTruncate(String.valueOf(userValues[0]), 50)  );
			
			developer.setEmail(String.valueOf(userValues[22]));
						
			if ( AimsConstants.COUNTRY_CODES.get(String.valueOf(userValues[7]))!=null )
				developer.setCountryCode( AimsConstants.COUNTRY_CODES.get(String.valueOf(userValues[7])).toString()  );			
			
			developer.setFaxNumber(String.valueOf(userValues[12]));
			developer.setLoginId( String.valueOf( userValues[26])) ; //vendorid
			// developer.setPassword( String.valueOf(userValues[4] ) );
			developer.setPhoneNumber(String.valueOf(userValues[11]));
			// developer.setPlanIds( String.valueOf(userValues[4] ) );
			developer.setPostalCode(String.valueOf(userValues[6]));
			// developer.setRoleIds( String.valueOf(userValues[4] ) );
			developer.setState(String.valueOf(userValues[5]));
			developer.setStatusEnabled(true);
			developer.setStreet1(String.valueOf(userValues[25]));
			// developer.setStreet2( String.valueOf(userValues[4] ) );

			//AllianceServices allianceService = new AllianceServices();
			//allianceService.updateDeveloper(developer);
		}
		
		return developer;
	}

}