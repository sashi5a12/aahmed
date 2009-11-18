package com.netpace.aims.ws.amdocs;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.XmlFormatter;
import com.netpace.aims.ws.ThirdpartyServiceException;
import com.netpace.aims.ws.amdocs.offer.ProductOfferType;
import com.netpace.aims.ws.amdocs.partner.CreateContentProvider;

public class SettlementServices {
	
		
	private static final String PROP_PARTNERON_BOARDING_URL = "com.netpace.aims.ws.settlementService.partner.url";
	private static final String PROP_OFFER_CREATION_URL = "com.netpace.aims.ws.settlementService.offer.url";
	private static final String PROP_USERNAME = "com.netpace.aims.ws.settlementService.username";
	private static final String PROP_PASSWORD = "com.netpace.aims.ws.settlementService.password";
	private static final String PROP_SERVICE_ENABLE = "com.netpace.aims.ws.settlementServiceService.enable";

	private static final Header HEADER_AUTH = new Header("Authorization", "WSSE profile=\"UsernameToken\"");;
	
	private boolean serviceTurnOn = false;
	private String partnerOnboardingURL = null;
	private String offerCreationURL = null;
	private String username = null;
	private String password = null;	
	private boolean combinedCall = false;
	
	public static final String METHOD_PARTNER_ONBOARDING = "partnerOnboading";
	public static final String METHOD_OFFERCREATION = "offerCreation";
	public static final String METHOD_PARTNER_ONBOARDING_AND_OFFER_CREATION = "partnerandoffer";
	
	private static Logger log = Logger.getLogger(SettlementServices.class.getName());

	public static final String NO_RESUBMIT_URL = "none";
	
	private Long allianceId;
	private Long contractId;
	
	public SettlementServices(Long allianceId, Long contractId) {
		
		this(				
				ConfigEnvProperties.getInstance().getProperty(PROP_PARTNERON_BOARDING_URL),
				ConfigEnvProperties.getInstance().getProperty(PROP_OFFER_CREATION_URL),
				ConfigEnvProperties.getInstance().getProperty(PROP_USERNAME),
				ConfigEnvProperties.getInstance().getProperty(PROP_PASSWORD),
				"true".equalsIgnoreCase(ConfigEnvProperties.getInstance().getProperty(PROP_SERVICE_ENABLE)) ? true	: false
			);
		
		this.allianceId = allianceId;
		this.contractId = contractId;
	}

/*	
	public SettlementServices() {
		this(				
				"https://verizon-api-u2.dcstest-us.net/cponboarding-rest/v1/partner",
				"https://verizon-api-u2.dcstest-us.net/productoffer-rest/v1/offer",
				"verizonnoadmin",
				"password",
				true
				);
	}	
	*/	
		
	public SettlementServices(String partnerOnboardingUrl, String offerCreationUrl, String username, String password, boolean serviceTurnOn) {
		this.partnerOnboardingURL = partnerOnboardingUrl;
		this.offerCreationURL = offerCreationUrl;
		this.username = username;
		this.password = password;
		this.serviceTurnOn = serviceTurnOn;

		if (log.isDebugEnabled()) {
			log.debug(new StringBuffer()
					.append("Creating Settlement Service with  ").append("\n")
					.append(" username : ").append(this.username).append("\n")
					.append(" password : ").append(this.password).append("\n")
					.append(" partnerOnboardingUrl : ").append(this.partnerOnboardingURL).append("\n")
					.append(" offerCreationUrl : ").append(this.offerCreationURL).append("\n")					
					.append(" service truenOn : ").append(this.serviceTurnOn).append("\n").toString());
		}
	}	

	
	/**
	 * Make REST Web Service call for GET
	 * @param url
	 * @return Response XML document
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private String doSecurePost(String resource, String request) throws ThirdpartyServiceException 
	{
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = null;
		String response = null;
		try 
		{
			postMethod = new PostMethod(resource);
			//set wsse header
			String authString = new WSSEHandler().getAuthString(username, password);
			postMethod.setRequestHeader(HEADER_AUTH);
			postMethod.setRequestHeader("X-WSSE", authString);
			
			if (log.isDebugEnabled()){
				log.debug("Request Header:"+HEADER_AUTH);
				log.debug("Request Header: X-WSSE " + authString);
			}
			
			//write request
			postMethod.setRequestBody(request);
			
			//make call
			httpClient.executeMethod(postMethod);
			
			//read response
			response = postMethod.getResponseBodyAsString();

		} 
		catch (UnsupportedEncodingException e) 
		{
			log.error(e,e);
			e.printStackTrace();
			
			throw new ThirdpartyServiceException(e);			
		} 
		catch (NoSuchAlgorithmException e) 
		{
			log.error(e,e);
			e.printStackTrace();
			
			throw new ThirdpartyServiceException(e);
		} 
		catch (IOException e) 
		{
			log.error(e,e);
			e.printStackTrace();
			
			throw new ThirdpartyServiceException(e);
		}
		finally 
		{
			if (postMethod != null) 
			{
				postMethod.releaseConnection();
			}
		}
		
		return response;
	}
	
	/**
	 * send out notification
	 */
	private void raiseEvent(String methodName, String errorMessage)
	{	
		if (log.isInfoEnabled()) 
			log.info("Enter raiseEvent(), allianceId="+this.allianceId+  " contractId=" + this.contractId + " methodName="+methodName);
		
		String resubmitUrl = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
		
		if ( isCombinedCall() && methodName.equalsIgnoreCase(METHOD_PARTNER_ONBOARDING) )
			resubmitUrl += "?method=" + METHOD_PARTNER_ONBOARDING_AND_OFFER_CREATION;
		else if ( isCombinedCall() && methodName.equalsIgnoreCase(METHOD_OFFERCREATION) )
			resubmitUrl += "?method=" + METHOD_OFFERCREATION;
		else
			resubmitUrl += "?method=" + methodName;
		
		resubmitUrl += "&allianceId="+this.allianceId;			
		if ( isCombinedCall() || SettlementServices.METHOD_OFFERCREATION.equalsIgnoreCase(methodName) )
			resubmitUrl += "&contractId="+this.contractId;
		
		resubmitUrl += "&massagdata=false";
		
		AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SETTLEMENT_SERVICE_FOLLOWUP);

		if (aimsEvent != null) {
			AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
			
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_RESUBMIT_ALLIANCE_SERVICE_URL,resubmitUrl);
			//aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_METHOD_NAME,methodName);
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT,new Date());
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CAUSE_MESSAGE,errorMessage);
			
			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID,this.allianceId.toString());
			
			aimsEvent.raiseEvent(aimsEventObject);

			if (log.isInfoEnabled()) 
				log.info("Exit raiseEvent(), resubmitURL="+resubmitUrl);

		}
	}

	
	
	/**
	 * Call REST based web services offer-creation (/offer)
	 * on error send out email notification with resubmit option. 
	 */
	public void offerCreation() throws Exception
	{
		this.offerCreation( true, false );
	}

	
	
	/**
	 * Call REST based web services offer-creation (/offer)
	 * on error send out email notification with resubmit option. 
	 * @param isNotificationEnabled
	 */
	public void offerCreation( boolean isNotificationEnabled, boolean massageData ) throws Exception
	{
		if (log.isInfoEnabled())
			log.info("Enter offerCreation: allianceId= " + allianceId + " contractId= " + contractId);
		
		if (serviceTurnOn == false) 
		{
			if (log.isDebugEnabled())
				log.info("Settlement Services are turn-off, to trun-on set flag 'com.netpace.aims.ws.settlementService.enable=true'");
			
			return;
		}
		
		String resource = offerCreationURL;
		
		try 
		{			
			ProductOfferType request = SettlementDAO.getOfferInfo(allianceId, contractId, massageData);
			StringWriter strWriter = new StringWriter();
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ProductOfferType.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(request, strWriter);
			
			if ( log.isDebugEnabled() )
			{
				log.debug("=================================== REQUEST XML ==================================");				
				log.debug(new XmlFormatter().format(strWriter.toString()));
				log.debug("==================================================================================");
			}
			
			String strResponse =  doSecurePost(resource, strWriter.toString());
			
			if ( log.isDebugEnabled())
			{				
				log.debug("=================================== RESPONSE XML =================================");
				log.debug(new XmlFormatter().format(strResponse));
				log.debug("==================================================================================");
			}
			
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ProductOfferType response = (ProductOfferType)unmarshaller.unmarshal(new StringReader(strResponse));
			
			if(response.getErrors() != null )
			{
				com.netpace.aims.ws.amdocs.offer.ErrorType error = response.getErrors().getError().get(0);
				if ( log.isDebugEnabled() )
				{
					log.debug("******************************* ERROR RESPONSE ***********************************");
					log.debug("ResponseCode: " + error.getResponseCode());
					log.debug("ResponseMessage: " + error.getResponseMessage());
					log.debug("**********************************************************************************");
				}
				throw new ThirdpartyServiceException("ResponseCode: " + error.getResponseCode() + " ResponseMessage: " + error.getResponseMessage());
			}
			
		} 
		catch (JAXBException e) 
		{
			log.error(e,e);
			
			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_OFFERCREATION, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_OFFERCREATION;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		} 
		catch (ThirdpartyServiceException e) 
		{
			log.error(e,e);
			
			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_OFFERCREATION, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_OFFERCREATION;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		catch (RecordNotFoundException e) 
		{
			log.error(e,e);
			
			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_OFFERCREATION, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_OFFERCREATION;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		catch (Exception e) 
		{
			log.error(e,e);
			
			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_OFFERCREATION, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_OFFERCREATION;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		finally 
		{
			
		}
	}
	

	
	/**
	 * Call REST based web services partner-on-boarding (/partner)
	 * if fails send out email notification (similar to raiseEvent in AllianceServices) 
	 * @throws Exception 
	 */
	public void partnerOnboarding() throws Exception
	{
		this.partnerOnboarding( true, false );
	}
	
	/**
	 * Call REST based web services partner-on-boarding (/partner)
	 * if fails send out email notification (similar to raiseEvent in AllianceServices) 
	 * @param isNotificationEnabled
	 * @throws Exception 
	 */
	public void partnerOnboarding(boolean isNotificationEnabled, boolean massageData) throws Exception
	{
		if (log.isInfoEnabled())
			log.info("Enter partnerOnboarding: allianceId= " + allianceId + " contractId= " + contractId);
		
		if (serviceTurnOn == false) 
		{
			if (log.isDebugEnabled())
				log.info("Settlement Services are turn-off, to trun-on set flag 'com.netpace.aims.ws.settlementService.enable=true'");
			
			return;
		}
		
		String resource = partnerOnboardingURL;
		
		String strResponse = null;		
		try 
		{			
			CreateContentProvider request = SettlementDAO.getPartnerInfo(allianceId, massageData);
			
			StringWriter strWriter = new StringWriter();
			
			JAXBContext jaxbContext = JAXBContext.newInstance(CreateContentProvider.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(request, strWriter);
			
			if ( log.isDebugEnabled() )
			{
				log.debug("=================================== REQUEST XML ==================================");
				log.debug(new XmlFormatter().format(strWriter.toString()));
				log.debug("==================================================================================");
			}
			
			strResponse =  doSecurePost(resource, strWriter.toString());
			
			if ( log.isDebugEnabled())
			{				
				log.debug("=================================== RESPONSE XML =================================");
				log.debug(new XmlFormatter().format(strResponse));
				log.debug("==================================================================================");				
			}
			
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			CreateContentProvider response = (CreateContentProvider)unmarshaller.unmarshal(new StringReader(strResponse));
			
			if(response.getErrors() != null )
			{
				com.netpace.aims.ws.amdocs.partner.ErrorType error = response.getErrors().getError().get(0);
				if ( log.isDebugEnabled() )
				{
					log.debug("******************************* ERROR RESPONSE ***********************************");
					log.debug("ResponseCode: " + error.getResponseCode());
					log.debug("ResponseMessage: " + error.getResponseMessage());
					log.debug("**********************************************************************************");
				}
				throw new ThirdpartyServiceException("ResponseCode: " + error.getResponseCode() + " ResponseMessage: " + error.getResponseMessage());			
			}
			
		} 
		catch (JAXBException e) {
			log.error(e,e);
			
			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_PARTNER_ONBOARDING, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_PARTNER_ONBOARDING;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		catch (ThirdpartyServiceException e) {
			log.error(e,e);
			
			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_PARTNER_ONBOARDING, e.getMessage());
				
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_PARTNER_ONBOARDING;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		catch (RecordNotFoundException e) {
			log.error(e,e);	
			
			if ( isNotificationEnabled )		
				raiseEvent(SettlementServices.METHOD_PARTNER_ONBOARDING, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_PARTNER_ONBOARDING;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		catch (Exception e) {
			log.error(e,e);	

			if ( isNotificationEnabled )
				raiseEvent(SettlementServices.METHOD_PARTNER_ONBOARDING, e.getMessage());
			
			e.printStackTrace();
			
			String resubmitURL = NO_RESUBMIT_URL;
			if ( this.allianceId != null && this.contractId != null )
			{
				resubmitURL = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.amdocs.settlementservices.resubmitUrl");
				resubmitURL += "?method="+SettlementServices.METHOD_PARTNER_ONBOARDING;
				resubmitURL += "&allianceId=" + this.allianceId;
				resubmitURL += "&contractId=" + this.contractId;
			}
			SettlementServiceException ase = new SettlementServiceException( e, resubmitURL );
			
			throw ase;
		}
		finally 
		{
			
		}
	}

	public boolean isCombinedCall() {
		return combinedCall;
	}

	public void setCombinedCall(boolean combinedCall) {
		this.combinedCall = combinedCall;
	}

	public Long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
}
