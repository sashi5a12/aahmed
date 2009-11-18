package com.netpace.aims.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.netpace.aims.bo.vzdn.PasswordUtil;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;


public class OpenssoRestService {
	
	static Logger log = Logger.getLogger(OpenssoRestService.class.getName());

	private static String serverURL;
	
	private static  String authenticate() throws Exception{
		ResourceBundle rb = ResourceBundle.getBundle("envProps");
		String openssoAdmin = rb.getString("opensso.admin.user");
		String encryptedPassword = rb.getString("opensso.admin.password");
		String password = PasswordUtil.decrypt(encryptedPassword);
		String authenticationTokenID=new String();

		serverURL = rb.getString("opensso.serverURL");
		log.debug("--- OPENSSO REST SERVICE --- server URL : " + serverURL);

		String res = request(new URL(serverURL + "/identity/authenticate?" + 
									 "username=" + URLEncoder.encode(openssoAdmin, "UTF-8") + 
									 "&password=" + URLEncoder.encode(password, "UTF-8")),authenticationTokenID);
		log.debug("--- OPENSSO REST SERVICE --- connection open request string : "+ res);
		String rawTokenId = res.substring(9);
		log.debug("Raw Token ID : " + rawTokenId);
		authenticationTokenID = rawTokenId.substring(0, rawTokenId.length() - 1);
		log.debug("--- OPENSSO REST SERVICE --- authenticationTokenID : " + authenticationTokenID);
		return authenticationTokenID;
	}
	
	private static void logout(String authenticationTokenID) throws Exception {

		String res = request(new URL(serverURL + "/identity/logout?" + 
									 "subjectid=" + URLEncoder.encode(authenticationTokenID, "UTF-8")),authenticationTokenID);

		log.debug("--- OPENSSO REST SERVICE --- Token discarded : " + authenticationTokenID);
	}
	
	public static void updateUserCompany(AimsUser user, AimsAllianc alliance) throws Exception{
		
		ConfigEnvProperties props=ConfigEnvProperties.getInstance();
		String gtm_company_id = props.getProperty("dev.organization.id");

		String authenticationTokenID=authenticate();
		log.debug("Writing Vendor id : "+alliance.getVendorId()+" against user : "+ user.getUsername()+ " in LDAP");
		
		request(new URL(serverURL +"/identity/update?" +
				 "identity_name="+user.getUsername()+
				 "&admin=" +URLEncoder.encode(authenticationTokenID, "UTF-8") +	   
				 "&identity_attribute_names=" + gtm_company_id +
				 "&identity_attribute_values_"+ gtm_company_id + "=" + alliance.getVendorId()),authenticationTokenID);
		logout(authenticationTokenID);		
	}
		
		
	public static String request(URL url, String authenticationTokenID) throws IOException {

		StringBuffer buff = new StringBuffer();
		BufferedReader dis =null;
		try {
			URLConnection conn = url.openConnection();
			dis = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = dis.readLine()) != null) {
				buff.append(inputLine).append("\n");
			}
		} catch (IOException e) {			
			log.error(e,e);
			try {
				if (StringUtils.isNotEmpty(authenticationTokenID))
					logout(authenticationTokenID);
			} catch (Exception e1) {
				log.error(e1,e1);
			}
			throw e;
		} finally {
			if (dis != null){
				try {
					dis.close();
				} catch (IOException e) {					
					log.error(e,e);
					try {
						if (StringUtils.isNotEmpty(authenticationTokenID))
							logout(authenticationTokenID);
					} catch (Exception e1) {
						log.error(e1,e1);
					}
					throw e;
				}
			}
		}
		return buff.toString();
	}
	public static Map<String,String> read(String username, String[] attributeList) throws Exception {
		log.debug("OpenssoRestService.read: Start");
		log.debug("UserName Param: "+username);
		log.debug("Attributes Param: "+attributeList);
		StringBuffer attributeParams=new StringBuffer();
		Map<String,String> values=new HashMap<String, String>();
		for (int i=0; i<attributeList.length; i++){
			attributeParams.append("&attributes_names=").append(attributeList[i]);
		}
		String attribute=(attributeList==null || attributeList.length==0)?"":attributeParams.toString();
		
		String authenticationTokenID=authenticate();

		String output = request(new URL(serverURL + "/identity/read?" + 
										"name="+ username + 
										"&admin="+ URLEncoder.encode(authenticationTokenID, "UTF-8")+ 
										attribute),authenticationTokenID);
		log.debug("Opensso output string: "+output);
//		System.out.println("Opensso output string: "+output);
		
		for (int i=0; i<attributeList.length; i++){
			String attributeName="identitydetails.attribute.name="+attributeList[i];
			log.debug("Trying to get value of ...... "+attributeName);
			
			//Splitting output based of attribute name
			String valueLines[]=output.split(attributeName);
			
			//Splitting value end (RHS) based on new line. This will give the attribute value line.
			String newLinesBreakup[]=valueLines[1].split("\n");
			if (newLinesBreakup.length>0){
				String valueLine=newLinesBreakup[1];
				String[] value=valueLine.split("=");
				if (value.length==2){
					log.debug("Value found: "+value[1]);
					values.put(attributeList[i], value[1]);	
				}
				else {
					log.debug("Condition-1.....Not found: ");
					values.put(attributeList[i], "");
				}
			}
			else {
				log.debug("Condition-2.....Not found: ");
				values.put(attributeList[i], "");
			}
		}
		log.debug("Filtered Value: "+values);
		logout(authenticationTokenID);
		log.debug("OpenssoRestService.read: End");
		return values;

	}	
	
	public static String readAttribute(String username, String attribute) throws Exception{
		String authenticationTokenID=authenticate();
		String output =  request(new URL(serverURL + "/identity/read?" +
		   									 "name="+username+
		   									 "&admin=" +
		   									 URLEncoder.encode(authenticationTokenID, "UTF-8") +	    
		   									 "&attributes_names=" + attribute),authenticationTokenID);
		String attributeName = attribute;
		int startIndex = output.indexOf(attributeName)+attributeName.length();		   	
		String tempString = output.substring(output.indexOf(attributeName)+attributeName.length()+1);
		
//		System.out.println("TempStr=" + tempString);
		int endIndex = output.indexOf(attributeName)+attributeName.length() + tempString.indexOf('\n') + 1;
		   	
		//System.out.println("\n\n" + startIndex + "\n\n" +endIndex);
		if(startIndex >= endIndex)
			return null;
		
		String pair = output.substring(startIndex,endIndex).trim();
		String value = pair.substring("identitydetails.attribute.value".length() + 1 ).trim();
			
		//System.out.println("^^^" + output + "***");
		//System.out.println("\n\n'" + pair+"'");
		//System.out.println("\n\nRoles from Opensso='" + value+"'");
		logout(authenticationTokenID);
		return value;
	}
		
	public String retreiveAttributes(){
		return "";
	}
	
	public static void main(String args []){
		try {
//			System.out.println(OpenssoRestService.read("adeel2@mailinator.com", new String[]{"uid","vzdn-user-roles","vzdn-user-type","vzdn-dev-organization-id","givenname","sn","vzdn-title","vzdn-mobile-number","vzdn-phone-number","vzdn-fax-number"}));
			
			AimsUser user=new AimsUser();
			user.setUsername("adeel@mailinator2.com");
			
			AimsAllianc alliance=new AimsAllianc();
			alliance.setVendorId(new Long(1000));
			
			updateUserCompany(user, alliance);
			
			readAttribute("adeel@mailinator2.com", "vzdn-dev-organization-id");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
}
