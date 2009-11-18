package com.netpace.aims.ws.appinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jws.WebService;

import org.apache.log4j.Logger;

/**
 * @author Waseem Akram
 * @dateCreated Monday 11 May, 2009
 * @description ApplicationInfoSoapImpl is a webservice that takes keyword as input and return
 * 				details a java application. Keyword is unique and only one java application can 
 * 				belong to a kerword. Certain fault codes are returned by this service depending
 * 				on the input and java application. Fault codes are declared in class declaration
 * 				section and are self explanatory.
 */
@WebService(endpointInterface = "com.netpace.aims.ws.appinfo.ApplicationInfoSoap")
public class ApplicationInfoSoapImpl implements ApplicationInfoSoap {

	private static Logger log = Logger.getLogger(ApplicationInfoSoapImpl.class.getName());
	
	private final String KEYWORD_NOT_FOUND_CODE = "9011";
	private final String KEYWORD_NOT_FOUND_TEXT = "Java Application with keyword '<-keyword->' not found.";
	private final String APPLICATION_NOT_APPROVED_CODE = "9012";
	private final String APPLICATION_NOT_APPROVED_TEXT = "Java Application is not approved.";
	private final String NOT_JAVA_APPLICATION_CODE = "9013";
	private final String NOT_JAVA_APPLICATION_TEXT = "Application is not Java application.";
	private final String NO_CONTRACT_ASSOCIATED_CODE = "9014";
	private final String NO_CONTRACT_ASSOCIATED_TEXT = "Contract not associated with Java Application.";
	private final String UNAUTHORIZED_USER_CODE = "9015";
	private final String UNAUTHORIZED_USER_TEXT = "User not authorized to access Java Application.";
	private final String UNKNOWN_ERROR_CODE = "9000";
	private final String UNKNOWN_ERROR_TEXT = "Unknown system error.";
	private final Long ACCEPTANCE_ID = new Long(2009);

	private static Connection getConnection() throws SQLException {		
        return ConnectionFactory.getConnection();		
	}
	
	/**
	 * javaAppByKeyword is private method that takes keyword as input and returns a hashmap 
	 * collection of AimsApp, AimsJavaApp and AimsAlliances pojo objects. A RecordNotFoundException
	 * checked exception is thrown by the methos if no application pertaining to input
	 * keyword is found. Runtime exception is thrown if unknown exception occurs. 
	 */
	private HashMap getJavaAppByKeyword(String keyword) throws RecordNotFoundException
    {
    	if(log.isInfoEnabled())
    		log.info("Enter getJavaAppByKeyword() Keyword ="+keyword);
    	
    	final String query = "select apps.APPS_ID, apps.PHASE_ID, apps.LONG_DESC, apps.SHORT_DESC, apps.TITLE, jApps.APP_REF_ID " +
    			", jApps.CONTENT_RATING_TYPE_ID, jApps.CONTENT_TYPE_ID, jApps.TAX_CATEGORY_CODE_ID , jApps.ENTERPRISE_APP" +
    			", jApps.ENTERPRISE_ID, apps.RING_TYPE_ID , aln.ALLIANCE_ID, aln.VENDOR_ID , ctr.CONTRACT_REF_ID " +
    			"from AIMS_APPS apps, AIMS_JAVA_APPS jApps, AIMS_ALLIANCES aln ,AIMS_CONTRACTS ctr, AIMS_ALLIANCE_CONTRACTS allCtr " +
    			"where apps.APPS_ID=jApps.JAVA_APPS_ID  and apps.ALLIANCE_ID=aln.ALLIANCE_ID  " +
    			"and ctr.CONTRACT_ID = allCtr.CONTRACT_ID and allCtr.ALLIANCE_ID = apps.ALLIANCE_ID " +
    			"and jApps.APP_KEYWORD= ?";
    	
    	if(log.isDebugEnabled())
    		log.debug(" getJavaAppByKeyword() query = "+query);
    	
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		HashMap<String, Object> appJava = new HashMap<String, Object>();
		AimsApp aimsApp= new AimsApp();		
		AimsJavaApp  aimsJavaApp= new AimsJavaApp();		
		AimsAlliance aimsAlliance = new AimsAlliance();		
		try  {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, keyword);
			ResultSet rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) {
				aimsApp.setAppsId(rs.getLong("APPS_ID"));
				aimsApp.setLongDesc(rs.getString("LONG_DESC"));
				aimsApp.setShortDesc(rs.getString("SHORT_DESC"));
				aimsApp.setTitle(rs.getString("TITLE"));
				aimsApp.setAppRefId(rs.getLong("APP_REF_ID"));
				aimsApp.setAimsLifecyclePhaseId(rs.getLong("PHASE_ID"));		
				
				aimsJavaApp.setJavaAppsId(rs.getLong("APPS_ID"));
				aimsJavaApp.setContractId(rs.getLong("CONTRACT_REF_ID"));
				aimsJavaApp.setContentRatingTypeId(rs.getLong("CONTENT_RATING_TYPE_ID"));
				aimsJavaApp.setContentTypeId(rs.getLong("CONTENT_TYPE_ID"));
				aimsJavaApp.setTaxCategoryCodeId(rs.getLong("TAX_CATEGORY_CODE_ID"));
				aimsJavaApp.setEnterpriseApp(rs.getString("ENTERPRISE_APP"));
				aimsJavaApp.setEnterpriseId(rs.getString("ENTERPRISE_ID"));
				//aimsJavaApp.setRingNumber(rs.getLong("RING_TYPE_ID"));
				if(172 == rs.getLong("RING_TYPE_ID"))
					aimsJavaApp.setRingNumber(2L);
				else
					aimsJavaApp.setRingNumber(3L);
								
				aimsAlliance.setAllianceId(rs.getLong("ALLIANCE_ID"));
				aimsAlliance.setVendorId(rs.getLong("VENDOR_ID"));
				
				appJava.put("AimsApp", aimsApp); // AimsApp
				appJava.put("AimsJavaApp", aimsJavaApp); // AimsJavaApp				
				appJava.put("AimsAlliance", aimsAlliance); // AimsAllianc
			}
			else
				throw new RecordNotFoundException();
		} 
		catch (SQLException e) {
			log.error(e,e);
			throw new RuntimeException(e);
		} finally {
			try  {			
				if(preparedStatement != null) {
					preparedStatement.close();
					preparedStatement = null;
				}
				if(connection != null) {
					connection.close();
					connection = null;
				}
			}catch (SQLException e) 	{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}

		if(log.isInfoEnabled())
    		log.info("Exit getJavaAppByKeyword() appJava ="+appJava);
    	
		return appJava;
	}

	/** 
	 * @param typeId aims_type_id
	 * makes a query to database to fetch aimstype record pertaining to typeid 
	 */
    private AimsType getAimsTypeByTypeId(Long typeId) {
    	
    	if(log.isInfoEnabled())
    		log.info("Enter getAimsTypeByTypeId() typeId ="+typeId);
    	
    	final String query = "select aimsTypes.TYPE_ID, aimsTypes.TYPE_VALUE, aimsTypes.DESCRIPTION " +
    			" from Aims_Types aimsTypes " +
    			" where aimsTypes.TYPE_ID=? ";
    	
    	if(log.isDebugEnabled())
    		log.debug(" getAimsTypeByTypeId() query = "+query);
    	
		Connection connection = null;
		PreparedStatement ps = null;
		AimsType aimsType = new AimsType();
		try {
			connection = getConnection();
			ps = connection.prepareStatement(query);
			ps.setLong(1, typeId);
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				aimsType.setTypeId(rs.getLong("TYPE_ID"));
				aimsType.setTypeValue(rs.getString("TYPE_VALUE"));
				aimsType.setDescription(rs.getString("DESCRIPTION"));
			}
		} catch (SQLException e) {
			log.error(e,e);
			throw new RuntimeException(e);
		}
		finally {
			try {
				if(ps != null) {
					ps.close();
					ps = null;
				}
				if(connection != null) {
					connection.close();
					connection = null;
				}
			} 
			catch (SQLException e) 	{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}
		if(log.isInfoEnabled())
    		log.info("Exit getAimsTypeByTypeId() aimsType ="+aimsType.getTypeValue());
    	
		return aimsType;
	}
    
    /**
     * @param codeId TAXCATEGORYCODEBYID
     * makes a query to database to fetch AIMS_TAX_CATEGORY_CODE record pertaining to codeId
     */
    private AimsTaxCategoryCode getAimsTaxCategoryCodeById(Long codeId)  {
    	
		if(log.isInfoEnabled())
			log.info("Enter getAimsTaxCategoryCodeById() codeId ="+codeId);
    	
    	final String sqlQuery = "select taxCategoryCode.TAX_CATEGORY_CODE_ID, taxCategoryCode.TAX_CATEGORY_CODE" +
    			", taxCategoryCode.TAX_CATEGORY_CODE_DESC " +
    			"from Aims_TAX_CATEGORY_CODES taxCategoryCode " +
    			"where taxCategoryCode.TAX_CATEGORY_CODE_ID=? ";
    	
    	if(log.isDebugEnabled())
    		log.debug(" getAimsTaxCategoryCodeById() sqlQuery = "+sqlQuery);
    	
		Connection connection = null;
		PreparedStatement ps = null;
		AimsTaxCategoryCode aimsTaxCategoryCode = new AimsTaxCategoryCode();
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sqlQuery);
			ps.setLong(1, codeId);
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				aimsTaxCategoryCode.setTaxCategoryCodeId(rs.getLong("TAX_CATEGORY_CODE_ID"));
				aimsTaxCategoryCode.setTaxCategoryCode(rs.getString("TAX_CATEGORY_CODE"));				
				aimsTaxCategoryCode.setTaxCategoryCodeDesc(rs.getString("TAX_CATEGORY_CODE_DESC"));
			}
		} 
		catch (SQLException e) {
			log.error(e,e);
			throw new RuntimeException(e);
		} 
		finally {
			try {
				if(ps != null) {
					ps.close();
					ps = null;
				}
				if(connection != null) 	{
					connection.close();
					connection = null;
				}
			} 
			catch (SQLException e)	{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}

		if(log.isInfoEnabled())
			log.info("Exit getAimsTaxCategoryCodeById() aimsTaxCategoryCode ="+aimsTaxCategoryCode.getTaxCategoryCode());
		
		return aimsTaxCategoryCode;
    }
    
    /**
     * @param codeId AIMS_CONTRACT_ID
     * makes a query to database to fetch AIMS_CONTRACT record pertaining to contractId
     */
    private AimsContract getAimsContractById(Long contractId)   {
    	
		if(log.isInfoEnabled())
			log.info("Enter getAimsContractById() contractId ="+contractId);
    	
    	final String sqlQuery = "select contract.CONTRACT_ID, contract.CONTRACT_REF_ID " +
    			"from AIMS_CONTRACTS contract " +
    			"where contract.CONTRACT_ID=? ";
    	
    	if(log.isDebugEnabled())
    		log.debug(" getAimsContractById() sqlQuery = "+sqlQuery);
    	
		Connection connection = null;
		PreparedStatement ps = null;
		AimsContract aimsContract = new AimsContract(); 
		try	{
			connection = getConnection();
			ps = connection.prepareStatement(sqlQuery);
			ps.setLong(1, contractId);
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				aimsContract.setContractId(rs.getLong("CONTRACT_ID"));
				aimsContract.setContractRefId(rs.getLong("CONTRACT_REF_ID"));
			}
		} 
		catch (SQLException e) 	{
			log.error(e,e);
			throw new RuntimeException(e);
		}
		finally {
			try  {
				if(ps != null) {
					ps.close();
					ps = null;
				}
				if(connection != null)	{
					connection.close();
					connection = null;
				}
			} 
			catch (SQLException e) {
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}

		if(log.isInfoEnabled())
			log.info("Exit getAimsContractById() aimsContract ="+aimsContract.getContractRefId());
		
		return aimsContract;
    }
    
    /**
     * takes fault code and message as input and returns 
     * and error respons object
     */
    private ErrorResponse createErrorResponse(String eField, String eText)  {

		if(log.isInfoEnabled())
			log.info("Enter createErrorResponse() eField ="+eField+", eText="+eText);

    	ErrorResponse errorResponse = new ErrorResponse();
    	ArrayList<ErrorItemType> errorList = new ArrayList<ErrorItemType>();
		ErrorItemType errorItem = new ErrorItemType();	
		errorItem.errorField = eField;
		errorItem.errorText = eText;
		errorList.add(errorItem);
		errorResponse.errorItem = errorList;
		
		if(log.isInfoEnabled())
			log.info("Exit createErrorResponse() errorResponse ="+errorResponse);
		
		return errorResponse;
    }
    
    /**
     * getApplicationInfo is the only method that is exposed by webservice
     * @input parameters contains keyword 
     */
	public ApplicationInfoResponse getApplicationInfo(ApplicationInfoRequest parameters) throws ErrorSoapOut 
	{		
		if(log.isInfoEnabled())
			log.info("Enter getApplicationInfo() applicationInfoRequest ="+parameters);
		
		ApplicationInfoSoapImpl appInfoSoapImpl = new ApplicationInfoSoapImpl();
		String keyword = parameters.getApplicationKeyword();
		if ( keyword==null || keyword.trim()=="" ) 	{
			throw new ErrorSoapOut(KEYWORD_NOT_FOUND_CODE, 
					createErrorResponse(KEYWORD_NOT_FOUND_CODE, KEYWORD_NOT_FOUND_TEXT.replaceAll("<-keyword->", keyword)) );
		}
		keyword = keyword.trim();
		HashMap javaApp=null;
		try {
			javaApp = appInfoSoapImpl.getJavaAppByKeyword(keyword);
		} 
		catch (RuntimeException e) 
		{
			log.error(e, e);
			throw new ErrorSoapOut(UNKNOWN_ERROR_CODE, createErrorResponse(UNKNOWN_ERROR_CODE, UNKNOWN_ERROR_TEXT) );
		} 
		catch (RecordNotFoundException e) 
		{
			log.error(e, e);
			throw new ErrorSoapOut(KEYWORD_NOT_FOUND_CODE, 
					createErrorResponse(KEYWORD_NOT_FOUND_CODE, KEYWORD_NOT_FOUND_TEXT.replaceAll("<-keyword->", keyword)) );			
		}

		AimsApp aimsApp = (AimsApp) javaApp.get("AimsApp");
		AimsJavaApp aimsJavaApps = (AimsJavaApp) javaApp.get("AimsJavaApp");
		AimsAlliance aimsAllianc = (AimsAlliance) javaApp.get("AimsAlliance");
		if ( ! ACCEPTANCE_ID.equals( aimsApp.getAimsLifecyclePhaseId() ) )
			throw new ErrorSoapOut(APPLICATION_NOT_APPROVED_CODE, 
					createErrorResponse(APPLICATION_NOT_APPROVED_CODE, APPLICATION_NOT_APPROVED_TEXT) );
				
		if ( aimsJavaApps.getContractId() == null || aimsJavaApps.getContractId()==0 )
			throw new ErrorSoapOut(NO_CONTRACT_ASSOCIATED_CODE, 
					createErrorResponse(NO_CONTRACT_ASSOCIATED_CODE, NO_CONTRACT_ASSOCIATED_TEXT) );
			
		ApplicationInfoResponse response = new ApplicationInfoResponse();
		response.setApplicationLongDescription(aimsApp.getLongDesc());
		response.setApplicationName(aimsApp.getTitle());			
		response.setApplicationRefId(aimsApp.getAppRefId()); // what about this field			
		response.setApplicationShortDescription(aimsApp.getShortDesc());
		
		try	{
			if ( aimsJavaApps.getContentRatingTypeId()!=null )	{
				AimsType contentRatingType = appInfoSoapImpl.getAimsTypeByTypeId(aimsJavaApps.getContentRatingTypeId());
				if ( contentRatingType!=null )
					response.setContentRating(contentRatingType.getDescription());
			}
				
			if ( aimsJavaApps.getContentTypeId()!=null ) {
				AimsType contentType = appInfoSoapImpl.getAimsTypeByTypeId(aimsJavaApps.getContentTypeId());
				//CR: VZW request to send "APPLICATN" instead of "APPLICATION"
				if ( contentType!=null )
					response.setContentType(  ContentTypeType.fromValue(contentType.getDescription()) );
			}
			
			if ( aimsJavaApps.getTaxCategoryCodeId()!=null ){
				AimsTaxCategoryCode aimsTaxCategoryCode = appInfoSoapImpl.getAimsTaxCategoryCodeById(aimsJavaApps.getTaxCategoryCodeId());
				//response.setTaxCategory(aimsTaxCategoryCode.getTaxCategoryCode()+ " - "+aimsTaxCategoryCode.getTaxCategoryCodeDesc());
				// Changed by Waseem Akram. Now tax code is passed only 
				response.setTaxCategory(aimsTaxCategoryCode.getTaxCategoryCode());
			}
				
			//AimsContract aimsContract = appInfoSoapImpl.getAimsContractById(aimsJavaApps.getContractId());
			//response.setContractRefId(aimsContract.getContractRefId());
			response.setContractRefId(aimsJavaApps.getContractId());
			
		}
		catch (RuntimeException e)	{
			log.error(e, e);
			throw new ErrorSoapOut(UNKNOWN_ERROR_CODE, createErrorResponse(UNKNOWN_ERROR_CODE, UNKNOWN_ERROR_TEXT) );
		}
			
		response.setEnterpriseFlag(aimsJavaApps.getEnterpriseApp().equals("Y")?true:false);
		response.setEnterpriseId(aimsJavaApps.getEnterpriseId());
		//convert ring number	
		if ( aimsJavaApps.getRingNumber()!=null )
			response.setRingNumber(aimsJavaApps.getRingNumber().intValue());
		//set vendor id
		response.setVendorID(aimsAllianc.getVendorId());
		
		if(log.isInfoEnabled())
			log.info("Exit getApplicationInfo() applicationInfoResponse ="+response);
		
		return response;
	}
}