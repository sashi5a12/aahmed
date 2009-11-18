package com.netpace.aims.ws;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import net.sf.hibernate.HibernateException;

import com.netpace.aims.ws.amdocs.SettlementServices;
import com.netpace.aims.ws.vds.AllianceServices;
import com.netpace.aims.ws.vds.Developer;

public class ServiceManager extends Thread {
	
	private static Logger log = Logger.getLogger(ServiceManager.class.getName());
	
	String method = null;
	Long allianceId = null;
	Long contractId = null;
	Long vendorId = null;
	String developerName = null;
	
	private ServiceManager(String method, Long allianceId){
		this.method = method;
		this.allianceId = allianceId;
	}
	private ServiceManager(String method, Long allianceId, Long contractId){
		this.method = method;
		this.allianceId = allianceId;
		this.contractId = contractId;
	}
	
	private ServiceManager(String method, Long allianceId, Long vendorId, String developerName){
		this.method = method;
		this.allianceId = allianceId;
		this.vendorId = vendorId;
		this.developerName = developerName;
	}
	
	public static void createDeveloper(Long allianceId){
		ServiceManager t = new ServiceManager(AllianceServices.CREATE_METHOD, allianceId); 
		t.start();
	}
	
	public static void updateDeveloper(Long allianceId){
		ServiceManager t = new ServiceManager(AllianceServices.UPDATE_METHOD, allianceId); 
		t.start();
	}
	
	public static void deleteDeveloper(Long allianceId, Long vendorId, String developerName){
		ServiceManager t = new ServiceManager(AllianceServices.DELETE_METHOD, allianceId, vendorId, developerName); 
		t.start();
	}
	
	
	public static void partnerOnboarding(Long allianceId){
		if ( log.isDebugEnabled() )
			log.debug("In method partnerOnboarding");
		ServiceManager t = new ServiceManager(SettlementServices.METHOD_PARTNER_ONBOARDING, allianceId); 
		t.start();
	}
	
	public static void offerCreation(Long allianceId, Long contractId){
		if ( log.isDebugEnabled() )
			log.debug("In method offerCreation");
		ServiceManager t = new ServiceManager(SettlementServices.METHOD_OFFERCREATION, allianceId, contractId); 
		t.start();
	}
	
	public static void partnerOnboardingAndofferCreation(Long allianceId, Long contractId){
		if ( log.isDebugEnabled() )
			log.debug("In combined call method partnerOnboardingAndofferCreation");
		ServiceManager t = new ServiceManager(SettlementServices.METHOD_PARTNER_ONBOARDING_AND_OFFER_CREATION, allianceId, contractId); 
		t.start();
	}
	

	@Override
	public void run() 
	{
		try
		{
			if(AllianceServices.CREATE_METHOD.equalsIgnoreCase(method))
			{
				AllianceServices as = new AllianceServices();
					Developer developer = as.getDeveloperForAllianceId(allianceId);
				as.createDeveloper(developer);
			}
			else if(AllianceServices.UPDATE_METHOD.equalsIgnoreCase(method))
			{
				AllianceServices as = new AllianceServices();
					Developer developer = as.getDeveloperForAllianceId(allianceId);
				as.updateDeveloper(developer);
			}
			else if(AllianceServices.DELETE_METHOD.equalsIgnoreCase(method))
			{
				AllianceServices as = new AllianceServices();
					Developer developer = new Developer();
					
						developer.setAllianceId(allianceId.toString());	
						developer.setLoginId(vendorId.toString());
						developer.setDeveloperName(developerName);
										
				as.deleteDeveloper(developer);
			}
			else if(SettlementServices.METHOD_PARTNER_ONBOARDING.equalsIgnoreCase(method))
			{
				SettlementServices ss = new SettlementServices(allianceId, contractId);
				ss.partnerOnboarding();
			}
			else if(SettlementServices.METHOD_OFFERCREATION.equalsIgnoreCase(method))
			{
				SettlementServices ss = new SettlementServices(allianceId, contractId);
				ss.offerCreation();
			}
			else if(SettlementServices.METHOD_PARTNER_ONBOARDING_AND_OFFER_CREATION.equalsIgnoreCase(method))
			{
				SettlementServices ss = new SettlementServices(allianceId, contractId);
					ss.setCombinedCall(true);
					ss.partnerOnboarding();
					ss.offerCreation();
			}
		}
		catch(ThirdpartyServiceException t)
		{
			if(log.isEnabledFor(Priority.ERROR))
				log.error("Web Service "+ method +"' call failed ", t);
			
			t.printStackTrace();
		}
		catch(HibernateException h)
		{
			if(log.isEnabledFor(Priority.ERROR))
				log.error("Web Service '"+ method +"' call failed ", h);
			
			h.printStackTrace();
		}
		catch(Exception e)
		{
			if(log.isEnabledFor(Priority.ERROR))
				log.error("Web Service '"+ method +"' call failed ", e);
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ServiceManager.partnerOnboardingAndofferCreation(new Long(6526),new Long(569));
		//ServiceManager.offerCreation(new Long(6526),new Long(569));
		//ServiceManager.partnerOnboarding(new Long(6526));
		/*
		if("create".equalsIgnoreCase(args[0]) )
				ServiceManager.createDeveloper(Long.valueOf(args[1]) );
		else if("update".equalsIgnoreCase(args[0]) ).
		
				ServiceManager.updateDeveloper(Long.valueOf(args[1]) );
		else if("update".equalsIgnoreCase(args[0]) )
				ServiceManager.deleteDeveloper(Long.valueOf(args[1]) );
		else 
			System.out.println("Usuage ServiceManager create/update/delete <alliance-id>");
		*/
	}
	
	
}
