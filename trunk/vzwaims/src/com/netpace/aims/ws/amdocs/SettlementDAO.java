package com.netpace.aims.ws.amdocs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.ws.amdocs.offer.ApproveOfferType;
import com.netpace.aims.ws.amdocs.offer.ChargesAggregatableType;
import com.netpace.aims.ws.amdocs.offer.CompositionDataType;
import com.netpace.aims.ws.amdocs.offer.ConcludableType;
import com.netpace.aims.ws.amdocs.offer.ConstraintDataType;
import com.netpace.aims.ws.amdocs.offer.OfferAccessPeriodOptionsEnumType;
import com.netpace.aims.ws.amdocs.offer.OfferCategoryEnumDataType;
import com.netpace.aims.ws.amdocs.offer.OfferChargeFrequencyEnumType;
import com.netpace.aims.ws.amdocs.offer.OfferExpirationEnumType;
import com.netpace.aims.ws.amdocs.offer.OfferPackagingOptionsDataType;
import com.netpace.aims.ws.amdocs.offer.OfferPackagingOptionsEnumType;
import com.netpace.aims.ws.amdocs.offer.OfferPricingMethodsEnumType;
import com.netpace.aims.ws.amdocs.offer.OfferReaccessUntilEnumType;
import com.netpace.aims.ws.amdocs.offer.OfferReaccessWithinEnumType;
import com.netpace.aims.ws.amdocs.offer.PaymentPlanDataType;
import com.netpace.aims.ws.amdocs.offer.PriceSchemeDataType;
import com.netpace.aims.ws.amdocs.offer.ProductOfferDataType;
import com.netpace.aims.ws.amdocs.offer.ProductOfferTermDataType;
import com.netpace.aims.ws.amdocs.offer.ProductOfferType;
import com.netpace.aims.ws.amdocs.offer.RefundableType;
import com.netpace.aims.ws.amdocs.offer.RequirementDataType;
import com.netpace.aims.ws.amdocs.offer.TaxNexusPreferenceEnumType;
import com.netpace.aims.ws.amdocs.offer.TaxSchemaDataType;
import com.netpace.aims.ws.amdocs.partner.CPOnboardingDataType;
import com.netpace.aims.ws.amdocs.partner.ContactDataType;
import com.netpace.aims.ws.amdocs.partner.ContentProviderDataType;
import com.netpace.aims.ws.amdocs.partner.CreateContentProvider;
import com.netpace.aims.ws.amdocs.partner.ExternalInformationData;
import com.netpace.aims.ws.amdocs.partner.ExternalInformationList;
import com.netpace.aims.ws.amdocs.partner.InitialProfileDataType;
import com.netpace.aims.ws.amdocs.partner.ProvisioningDataType;
import com.netpace.aims.ws.amdocs.partner.RemittanceInformationDataType;
import com.netpace.aims.ws.amdocs.partner.TaxInformationDataType;
import com.netpace.aims.ws.amdocs.partner.TermsAndConditionsType;

public class SettlementDAO {

	private static Logger log = Logger.getLogger(SettlementDAO.class.getName());
	
	private static final String PO_TERM_AGREED 				="com.netpace.aims.ws.amdocs.partnerOnboarding.TermAgreed";
	private static final String PO_TAX_NEXUS_EXEMPTED 		="com.netpace.aims.ws.amdocs.partnerOnboarding.TaxNexusExempted";
	private static final String PO_AUTO_APPROVE 			="com.netpace.aims.ws.amdocs.partnerOnboarding.AutoApprove";
	private static final String PO_TERMS_ACCEPTANCE_WAIVED 	="com.netpace.aims.ws.amdocs.partnerOnboarding.TermsAcceptanceWaived";
	private static final String PO_REV_DIST_CONTR_TEMP_DESC ="com.netpace.aims.ws.amdocs.partnerOnboarding.RevenueDistributionContractTemplateDescription";
	private static final String PO_PUR_AND_OWNER_NOTIF 		="com.netpace.aims.ws.amdocs.partnerOnboarding.PurchaseAndOwnershipNotifications";
	private static final String PO_TAX_NEXUS 				="com.netpace.aims.ws.amdocs.partnerOnboarding.TaxNexus";
	private static final String PO_TAX_NEXUS_WAIVED 		="com.netpace.aims.ws.amdocs.partnerOnboarding.TaxNexusWaived";
	private static final String PO_CONTACT_TYPE		 		="com.netpace.aims.ws.amdocs.partnerOnboarding.ContactType";
	private static final String PO_REV_DIST_CONTRACT_TEMP_ID="com.netpace.aims.ws.amdocs.partnerOnboarding.RevDistContractTempId";	
	private static final String PO_EXT_INFO_NAME			="com.netpace.aims.ws.amdocs.partnerOnboarding.extInformationData.name";
	private static final String PO_EXT_INFO_VALUE			="com.netpace.aims.ws.amdocs.partnerOnboarding.extInformationData.value";	

	private static final String OC_ACCESS_PERIOD_OPTION		="com.netpace.aims.ws.amdocs.offerCreation.AccessPeriodOption";
	private static final String OC_RACCESS_UNTIL 			="com.netpace.aims.ws.amdocs.offerCreation.ReaccessUntil";
	private static final String OC_OFFER_PACKAGING_TYPE 	="com.netpace.aims.ws.amdocs.offerCreation.OfferPackagingType";
	private static final String OC_OFFER_CATEGORY			="com.netpace.aims.ws.amdocs.offerCreation.OfferCategory";
	
	private static final String OC_OFFER_EXPIRATIOM 		="com.netpace.aims.ws.amdocs.offerCreation.OfferExpiration";
	private static final String OC_CHARGE_FREQUENCY 		="com.netpace.aims.ws.amdocs.offerCreation.ChargeFrequency";
	
	private static final String OC_OFFER_PRICE				="com.netpace.aims.ws.amdocs.offerCreation.OfferPrice";
	private static final String OC_OFFER_PRICED_ADJUSTABLE 	="com.netpace.aims.ws.amdocs.offerCreation.OfferPriceAdjustable";
	private static final String OC_OFFER_PRICING_METHOD 	="com.netpace.aims.ws.amdocs.offerCreation.OfferPricingMethod";
	private static final String OC_PAYMENT_METHOD 			="com.netpace.aims.ws.amdocs.offerCreation.PaymentMethod";
	private static final String OC_TAX_CATEGORY 			="com.netpace.aims.ws.amdocs.offerCreation.TaxCategory";
	private static final String OFFER_DESCRIPTION 			="com.netpace.aims.ws.amdocs.offerCreation.OfferDescription";
	private static final String OFFER_APPROVEOFFER 			="com.netpace.aims.ws.amdocs.offerCreation.ApproveOffer";
	private static final String OFFER_REFUNDABLE 			="com.netpace.aims.ws.amdocs.offerCreation.Refundable";
	private static final String OFFER_CONCLUDABLE 			="com.netpace.aims.ws.amdocs.offerCreation.Concludable";
	private static final String OFFER_TAXNEXUSPREFERENCE	="com.netpace.aims.ws.amdocs.offerCreation.TaxNexusPreference";
	private static final String OFFER_CHARGESAGGREGATABLE	="com.netpace.aims.ws.amdocs.offerCreation.ChargesAggregatable";

	private static final String PO_MD_REMITTO  	 = "com.netpace.aims.ws.amdocs.partnerOnboarding.massageData.remitTo";
	private static final String PO_MD_ADDRESS1 	 = "com.netpace.aims.ws.amdocs.partnerOnboarding.massageData.address1";	
	private static final String PO_MD_CITY 		 = "com.netpace.aims.ws.amdocs.partnerOnboarding.massageData.city";
	private static final String PO_MD_COUNTRY 	 = "com.netpace.aims.ws.amdocs.partnerOnboarding.massageData.country";
	private static final String PO_MD_STATE 	 = "com.netpace.aims.ws.amdocs.partnerOnboarding.massageData.state";
	private static final String PO_MD_POSTALCODE = "com.netpace.aims.ws.amdocs.partnerOnboarding.massageData.postalCode";
	
	private static String safeTruncate(String val, int length)
	{
		if ( StringUtils.isEmpty(val) )
			return val;
		
		if ( val.length() > length )
			return val.substring(0, length);
		
		return val;
	}
	
	/**
	 * Query AIMS DB to fetch partner data for Alliace_Id
	 * Return JAXB binding object CPOnboardingDataType
	 */
	public static CreateContentProvider getPartnerInfo(Long allianceId, boolean massageData) throws RecordNotFoundException
	{	
		if(log.isInfoEnabled())
    		log.info("Enter SettlementDAO.getOfferInfo() allianceId =" + allianceId);
    	
    	final String query = "select alliance.ALLIANCE_ID, adminUserContact.FIRST_NAME REGFIRSTNAME,adminUserContact.LAST_NAME " +
    			"REGLASTNAME, alliance.VENDOR_ID REGUSERNAME, adminUserContact.EMAIL_ADDRESS REGEMAIL, " +
    			"alliance.COMPANY_NAME COMPNAME,alliance.VENDOR_ID COMPREF,alliance.WEB_SITE_URL COMPURL," +
    			"adminUserContact.TITLE TITLE,adminUserContact.PHONE PHONE, " +
    			"adminUserContact.MOBILE MOBILEPHONE, alliance.REMIT_ADDRESS1, alliance.REMIT_ADDRESS2, " +
    			"alliance.REMIT_CITY, alliance.REMIT_COUNTRY, alliance.REMIT_POSTAL_CODE, alliance.REMIT_STATE, alliance.REMIT_TO " +
    			"from AIMS_ALLIANCES alliance, AIMS_USERS aimsuser, AIMS_CONTACTS adminUserContact " +
    			"where alliance.ADMIN_USER_ID=aimsuser.USER_ID " +
    			"and aimsuser.CONTACT_ID=adminUserContact.CONTACT_ID " +
    			"and alliance.ALLIANCE_ID=?";
    	
    	
    	if(log.isDebugEnabled())
    		log.debug(" query = " + query);
    	
		Connection connection = null;
		PreparedStatement preparedStatement = null;		
		CommonProperties commonProps = CommonProperties.getInstance();		

		CreateContentProvider createContentProvider = new CreateContentProvider();
		CPOnboardingDataType cpOnboarding = new CPOnboardingDataType();

		createContentProvider.setCpOnboardingData(cpOnboarding);
		
		try  
		{						
			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, allianceId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) 
			{	
				InitialProfileDataType initialProfileData = new InitialProfileDataType();
				initialProfileData.setRegistrantEmailId( safeTruncate(rs.getString("REGEMAIL"),255) );
			
				initialProfileData.setRegistrantUserName(String.valueOf(rs.getLong("REGUSERNAME"))); //this is vendor id
				initialProfileData.setSecurityAnswer(UUID.randomUUID().toString()); //random number
				initialProfileData.setSecurityQuestion(UUID.randomUUID().toString()); //random number
				initialProfileData.setPassword(safeTruncate(rs.getLong("ALLIANCE_ID")+UUID.randomUUID().toString(),20));
				initialProfileData.setCompanyName(safeTruncate(rs.getString("COMPNAME").replaceAll("[<>]", ""),50));
				initialProfileData.setCompanyReferenceName(String.valueOf(rs.getLong("COMPREF"))); // this is vendor id
				//initialProfileData.setProductsDescription(); //optional not sent
				
				TermsAndConditionsType termsAndConditions = new TermsAndConditionsType();				
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_TERM_AGREED)) )
						termsAndConditions.setIsTermAgreed(new Boolean(commonProps.getProperty(PO_TERM_AGREED))); // always set to true

				initialProfileData.setTermsAndConditions(termsAndConditions);
				
				if ( rs.getString("COMPURL").indexOf("http://") == -1 && rs.getString("COMPURL").indexOf("https://") == -1  )
					initialProfileData.setCompanyUrl(safeTruncate("http://" + rs.getString("COMPURL"),255));
				else
					initialProfileData.setCompanyUrl(safeTruncate(rs.getString("COMPURL"),255)); 
						
				
				
				ContentProviderDataType contentProvider = new ContentProviderDataType();
				ContactDataType contactData = new ContactDataType();
				
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_CONTACT_TYPE)) )
					contactData.setContactType(commonProps.getProperty(PO_CONTACT_TYPE));
				//contactData.setIsPrimary(); // not in notes
				//verizon ask not to send phone & mobilePhone
				//contactData.setPhone(safeTruncate(rs.getString("PHONE"),20));
				//contactData.setMobilePhone(safeTruncate(rs.getString("MOBILEPHONE"),20));
				contactData.setNotes("");//optional, nothing set	
								
				RemittanceInformationDataType remittanceInformation = new RemittanceInformationDataType();				
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_TAX_NEXUS_EXEMPTED)) )
				{
					TaxInformationDataType	taxInformation = new TaxInformationDataType();				
						taxInformation.setIsTaxNexusExempted(new Boolean(commonProps.getProperty(PO_TAX_NEXUS_EXEMPTED)));
						
					// Nexus is not required coz isTaxNexusExempted is always set to YES
					//taxInformation.getNexus().add();  
					contentProvider.setTaxInformationData(taxInformation);
				}										
				
				String name = rs.getString("REGFIRSTNAME") + " " + rs.getString("REGLASTNAME");
				String title = rs.getString("TITLE");
				String email = rs.getString("REGEMAIL");
				
				String regFirstName = rs.getString("REGFIRSTNAME");
				String regLastName = rs.getString("REGLASTNAME");
				
				String remitAddress1 = rs.getString("REMIT_ADDRESS1");
				String remitAddress2 = rs.getString("REMIT_ADDRESS2");
				String remitCity = rs.getString("REMIT_CITY");
				String remitCountry = rs.getString("REMIT_COUNTRY");
				String remitPostalCode = rs.getString("REMIT_POSTAL_CODE");
				String remitTo = rs.getString("REMIT_TO");
				String remitState = rs.getString("REMIT_STATE");

				//MASSAGE FIELDS (for both bulk load and regular call)
				// < and > characters are to be removed in case of regular call
				name = name.replaceAll("[<>]", "");
				title = title.replaceAll("[<>]", "");
				//email = email.replaceAll("[<>]", "");
				regFirstName = regFirstName.replaceAll("[<>]", "");
				regLastName = regLastName.replaceAll("[<>]", "");
				
				if ( massageData )
				{
					regFirstName = regFirstName.replaceAll("[^A-Za-z0-9\\s]", "");
					regLastName = regLastName.replaceAll("[^A-Za-z0-9\\s]", "");
					
					if ( StringUtils.isEmpty(remitAddress1) )
						remitAddress1 = commonProps.getProperty(PO_MD_ADDRESS1);
					if ( StringUtils.isEmpty(remitAddress2) )
						remitAddress2 = "";
					if ( StringUtils.isEmpty(remitCity) )
							remitCity = commonProps.getProperty(PO_MD_CITY);
					if ( StringUtils.isEmpty(remitCountry))
						remitCountry = commonProps.getProperty(PO_MD_COUNTRY);
					if ( StringUtils.isEmpty(remitPostalCode))
						remitPostalCode = commonProps.getProperty(PO_MD_POSTALCODE);
					if ( StringUtils.isEmpty(remitTo))
						remitTo = commonProps.getProperty(PO_MD_REMITTO);
					if ( StringUtils.isEmpty(remitState))
						remitState = commonProps.getProperty(PO_MD_STATE);
				}
				
				initialProfileData.setRegistrantFirstName( safeTruncate(regFirstName,50) );
				initialProfileData.setRegistrantLastName( safeTruncate(regLastName,50) );
				
				contactData.setName(safeTruncate(name,50));
				contactData.setTitle(title);
				contactData.setEmail(email);
				
				remittanceInformation.setAddress1(remitAddress1);
				remittanceInformation.setAddress2(remitAddress2);				
				remittanceInformation.setCity(remitCity);
				remittanceInformation.setCountry(remitCountry);
				remittanceInformation.setPostalCode(remitPostalCode);
				remittanceInformation.setRemitTo(remitTo);
				remittanceInformation.setState(remitState);
				
				contentProvider.setRemittanceInformationData(remittanceInformation);
				
				contentProvider.getContactData().add(contactData);
				cpOnboarding.setContentProviderData(contentProvider);
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_EXT_INFO_NAME)) && !StringUtils.isEmpty(commonProps.getProperty(PO_EXT_INFO_VALUE)) )
				{
					ExternalInformationList externalInformation = new ExternalInformationList();//For time being we don't have any externalInformation	
						ExternalInformationData extInformationData = new ExternalInformationData();  
							extInformationData.setName(commonProps.getProperty(PO_EXT_INFO_NAME));
							extInformationData.setValue(commonProps.getProperty(PO_EXT_INFO_VALUE));
						
						externalInformation.getExternalInformation().add(extInformationData);
				
					cpOnboarding.setExternalInformationList(externalInformation);				
				}
				
				ProvisioningDataType provisioningData = new ProvisioningDataType();
						
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_AUTO_APPROVE)) )
					provisioningData.setAutoApprove(new Boolean(commonProps.getProperty(PO_AUTO_APPROVE))); //always set to true
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_TERMS_ACCEPTANCE_WAIVED)) )
					provisioningData.setTermsAcceptanceWaived(new Boolean(commonProps.getProperty(PO_TERMS_ACCEPTANCE_WAIVED))); //always set to true
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_REV_DIST_CONTRACT_TEMP_ID)) )
					provisioningData.setRevenueDistributionContractTemplateId(commonProps.getProperty(PO_REV_DIST_CONTRACT_TEMP_ID)); //??
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_REV_DIST_CONTR_TEMP_DESC)) )
					provisioningData.setRevenueDistributionContractTemplateDescription(commonProps.getProperty(PO_REV_DIST_CONTR_TEMP_DESC));
								
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_PUR_AND_OWNER_NOTIF)) )
					provisioningData.setPurchaseAndOwnershipNotifications(new Boolean(commonProps.getProperty(PO_PUR_AND_OWNER_NOTIF))); //always set to false
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_TAX_NEXUS)) )
					provisioningData.setTaxNexus(commonProps.getProperty(PO_TAX_NEXUS)); //set to NO
				
				if ( !StringUtils.isEmpty(commonProps.getProperty(PO_TAX_NEXUS_WAIVED)) )
					provisioningData.setTaxNexusWaived(new Boolean(commonProps.getProperty(PO_TAX_NEXUS_WAIVED))); //always set to true
					
				cpOnboarding.setProvisingData(provisioningData);
				
				cpOnboarding.setInitialProfileData(initialProfileData);			
			}
			else
				throw new RecordNotFoundException();
		} 
		catch (SQLException e) 
		{
			log.error(e,e);
			throw new RuntimeException(e);
		} 
		finally 
		{
			try  
			{			
				if(preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if(connection != null) 
				{
					connection.close();
					connection = null;
				}
			}
			catch (SQLException e) 	
			{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}
		
		if(log.isInfoEnabled())
    		log.info("Exit SettlementDAO.getOfferInfo()");
		return createContentProvider;
	}
	
	
	/**
	 * Query AIMS DB to fetch contract/offer data for Alliace_Id 
	 * Return JAXB binding object ProductOfferType
	 */	
	public static ProductOfferType getOfferInfo(Long allianceId, Long contractId, boolean massageData) throws RecordNotFoundException
	{		
		if(log.isInfoEnabled())
    		log.info("Enter SettlementDAO.getOfferInfo() allianceId= " + allianceId + " contractId=" + contractId);
    	
    	    	
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		CommonProperties commonProps = CommonProperties.getInstance();		
		
		String contractRefId = null;
		String contractTitle = null;
		String vendorId = null;
		try  
		{
			connection = ConnectionFactory.getConnection();
			
			String query = "select contract.CONTRACT_REF_ID,contract.CONTRACT_TITLE " +
									"from AIMS_CONTRACTS contract where contract.CONTRACT_ID=?";
	
			if(log.isDebugEnabled())
				log.debug(" query = " + query);

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, contractId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) 
			{
				contractRefId = String.valueOf(rs.getLong("CONTRACT_REF_ID"));
				contractTitle = rs.getString("CONTRACT_TITLE");
			}
			
			query = "select alliance.VENDOR_ID from AIMS_ALLIANCES alliance where alliance.ALLIANCE_ID=?";

			if(log.isDebugEnabled())
				log.debug(" query = " + query);
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, allianceId);
			rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) 
				vendorId = String.valueOf(rs.getLong("VENDOR_ID"));
		}
		catch (SQLException e) 
		{
			log.error(e,e);
			throw new RuntimeException(e);
		} 
		finally 
		{
			try  
			{			
				if(preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if(connection != null) 
				{
					connection.close();
					connection = null;
				}
			}
			catch (SQLException e) 	
			{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}
		
		
		ProductOfferType productOffer = new ProductOfferType();
		ProductOfferDataType productOfferData = new ProductOfferDataType();
		productOffer.setProductOfferData(productOfferData);
		
		//Contract Id is not set
		//productOffer.setOfferId(); //contract id
		productOfferData.setOfferId(contractRefId);				
			
		productOfferData.setVendorWorkingName("v_" + vendorId);//VENDOR_ID		
			
		ProductOfferTermDataType productOfferTerm = new ProductOfferTermDataType();
		CompositionDataType compositionData = new CompositionDataType();
		ConstraintDataType constraintData = new ConstraintDataType();
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_ACCESS_PERIOD_OPTION)) )
			constraintData.setAccessPeriodOption(OfferAccessPeriodOptionsEnumType.fromValue(commonProps.getProperty(OC_ACCESS_PERIOD_OPTION)));//set to free
			
		// this is not present in uat document
		//
		//constraintData.setReaccessUntil(OfferReaccessUntilEnumType.fromValue(commonProps.getProperty(OC_RACCESS_UNTIL)));//set to eoy
							
		//constraintData.setReaccessWithinTimeCount(); //optional, not set
		// this is not present in uat document
		//
		//constraintData.setReaccessWithinTimeUnit(OfferReaccessWithinEnumType.MONTHS);						
						
		compositionData.setConstraintData(constraintData);
		productOfferTerm.setCompositionData(compositionData);
				
		OfferPackagingOptionsDataType offerPackagingOptions = new OfferPackagingOptionsDataType();
		
		//offerPackagingOptions.setMaxAccessCount();//optional not set
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_OFFER_PACKAGING_TYPE)) )
			offerPackagingOptions.setOfferPackagingType(OfferPackagingOptionsEnumType.fromValue(commonProps.getProperty(OC_OFFER_PACKAGING_TYPE))); //set to instance
		
		productOfferTerm.setCountedAccessOfferTermsData(offerPackagingOptions);
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_OFFER_CATEGORY)) )
		{
			OfferCategoryEnumDataType offerCategoryEnum = new OfferCategoryEnumDataType();
				offerCategoryEnum.setOfferCategory(commonProps.getProperty(OC_OFFER_CATEGORY));//set to all
			
			productOfferTerm.setOfferCategoryData(offerCategoryEnum);
		}
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OFFER_DESCRIPTION)) )
			productOfferTerm.setOfferDescription(commonProps.getProperty(OFFER_DESCRIPTION));				
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_OFFER_EXPIRATIOM)) )
			productOfferTerm.setOfferExpiration(OfferExpirationEnumType.fromValue(commonProps.getProperty(OC_OFFER_EXPIRATIOM))); //set to never retires
		
						
		//productOfferTerm.setOfferExpirationDate();//optional not sent
		//OfferName is not set
		//productOfferTerm.setOfferName(); // name of the contract "contract name"
		
		
		// < and > characters are now to be removed whether massage data is required or not.
		// There commenting follwoing if check
		//if ( massageData )
			contractTitle = contractTitle.replaceAll("[<>]", "");
		productOfferTerm.setOfferName(contractTitle);		
		
		PaymentPlanDataType paymentPlanData = new PaymentPlanDataType();
		//paymentPlanData.setBillingDelay(); //optional not sent
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_CHARGE_FREQUENCY)) )
			paymentPlanData.setChargeFrequency(OfferChargeFrequencyEnumType.fromValue(commonProps.getProperty(OC_CHARGE_FREQUENCY))); //set to oneTime
		
		
		//paymentPlanData.setPeriodicityInterval();//optional, not sent
		//paymentPlanData.setPeriodicityUnit(OfferChagePeriodicityUnits);//optional not sent
		//paymentPlanData.setRecurringChargePeriodInterval();//not in documents
		productOfferTerm.setPaymentPlanData(paymentPlanData);
				
		PriceSchemeDataType priceSchemeData = new PriceSchemeDataType();
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_OFFER_PRICE)) )
			priceSchemeData.setOfferPrice(commonProps.getProperty(OC_OFFER_PRICE));//set to 1 cent
		
		// this field is not in the xml document
		//
		//priceSchemeData.setOfferPriceAdjustable(new Boolean(commonProps.getProperty(OC_OFFER_PRICED_ADJUSTABLE)));// set to false
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_OFFER_PRICING_METHOD)) )
			priceSchemeData.setOfferPricingMethod(OfferPricingMethodsEnumType.fromValue(commonProps.getProperty(OC_OFFER_PRICING_METHOD))); //set to point of sale
						
		productOfferTerm.setPriceSchemeData(priceSchemeData);
				
		RequirementDataType requirementData = new RequirementDataType();
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_PAYMENT_METHOD)) )
		    requirementData.getPaymentMethod().add(commonProps.getProperty(OC_PAYMENT_METHOD));
		
		productOfferTerm.setRequirementData(requirementData);
		
		TaxSchemaDataType taxSchemaData = new TaxSchemaDataType();
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OC_TAX_CATEGORY)) )
			taxSchemaData.setTaxCategory(commonProps.getProperty(OC_TAX_CATEGORY)); // set to none				
		
		
		productOfferTerm.setTaxSchemaData(taxSchemaData);					
		productOfferData.setProductOfferTermData(productOfferTerm);
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OFFER_APPROVEOFFER)) )
		{
			ApproveOfferType approveOffer = ApproveOfferType.fromValue(commonProps.getProperty(OFFER_APPROVEOFFER));
				productOfferData.setApproveOffer(approveOffer);
		}	
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OFFER_REFUNDABLE)) )
		{
			RefundableType refundable = RefundableType.fromValue(commonProps.getProperty(OFFER_REFUNDABLE));
				productOfferData.setRefundable(refundable);
		}
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OFFER_CONCLUDABLE)) )
		{
			ConcludableType concludable = ConcludableType.fromValue(commonProps.getProperty(OFFER_CONCLUDABLE));
				productOfferData.setConcludable(concludable);
		}
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OFFER_TAXNEXUSPREFERENCE)) )
		{
			TaxNexusPreferenceEnumType taxNexusPreference = TaxNexusPreferenceEnumType.fromValue(commonProps.getProperty(OFFER_TAXNEXUSPREFERENCE));
				productOfferData.setTaxNexusPreference(taxNexusPreference);
		}
		
		if ( !StringUtils.isEmpty(commonProps.getProperty(OFFER_CHARGESAGGREGATABLE)) )
		{
			ChargesAggregatableType chargesAggregatable = ChargesAggregatableType.fromValue(commonProps.getProperty(OFFER_CHARGESAGGREGATABLE));
				productOfferData.setChargesAggregatable(chargesAggregatable);
		}
		
		if(log.isInfoEnabled())
    		log.info("Exit SettlementDAO.getOfferInfo()");
		return productOffer;
	}
		
	
	
}
