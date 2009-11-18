package com.netpace.vzdn.service.opensso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import com.netpace.vzdn.exceptions.NoRolesFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnCredentials;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.util.PasswordUtil;
import com.netpace.vzdn.webapp.actions.UserMgmtAction;

public class OpenssoRestService {

	private static Logger log = Logger.getLogger(OpenssoRestService.class);
	public static void updateUserRoles(VzdnUsers user) throws Exception{
		
		
	String csvRoles = getUserRolesCSV(user.getRoles());	
    String username = user.getUserName();
	ResourceBundle rb = ResourceBundle.getBundle("envProps");
	String openssoAdmin = rb.getString("opensso.admin.user");
	String encryptedPassword = rb.getString("opensso.admin.password"); 
	String password = PasswordUtil.decrypt(encryptedPassword);
	
	String serverUrl = rb.getString("opensso.serverURL");
	String userType = user.getUserType().getTypeValue().replaceAll(" ", "%20");	
	String userStatusType = user.getStatusType().getTypeValue();
	
	String res = request(new URL(serverUrl +
	        "/identity/authenticate?" +
	        "username=" + URLEncoder.encode(openssoAdmin, "UTF-8") +
	        "&password=" +

	URLEncoder.encode(password, "UTF-8")));
	String  tokenId = res.substring(9);
	System.out.println("pre token : "+tokenId);
	tokenId = tokenId.substring(0, tokenId.length() -1);
	
	
	 System.out.println("Change email address");
	 
	 System.out.println("User Type:" + user.getUserType().getTypeValue());
	 System.out.println("Manager Organization:" + user.getManagerOrganization());
	 System.out.println("User Roles:" + csvRoles);
	
	 String userOrganization = "";
	 
	 if(user.getUserType().getTypeId().intValue() == VzdnConstants.VerizonUser)
	 {
		 if (null != user.getManagerOrganization()){
			 userOrganization = user.getManagerOrganization().replaceAll(" ", "%20");
		 }
		 
		 if(null == userOrganization || "".equals(userOrganization)){
			 userOrganization = "Verizon";
		 }
	 }
	 
	 request(new URL(serverUrl +
			 "/identity/update?" +
			 "identity_name="+username+"&"+"admin=" +
			 URLEncoder.encode(tokenId, "UTF-8") +
    
			 "&identity_attribute_names=" + VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES +
			 "&identity_attribute_values_"+ VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES + "=" + csvRoles +
    
			 "&identity_attribute_names=" + VzdnConstants.OPENSSO_ATTRIBUTE_USER_TYPE +
			 "&identity_attribute_values_"+ VzdnConstants.OPENSSO_ATTRIBUTE_USER_TYPE + "=" + userType + 
    
			 "&identity_attribute_names="+ VzdnConstants.OPENSSO_ATTRIBUTE_PARTNER_ORGANIZATION +
			 "&identity_attribute_values_" + VzdnConstants.OPENSSO_ATTRIBUTE_PARTNER_ORGANIZATION + "=" + userOrganization +
			 
			 "&identity_attribute_names=" + VzdnConstants.OPENSSO_ATTRIBUTE_USER_STATUS +
			 "&identity_attribute_values_"+ VzdnConstants.OPENSSO_ATTRIBUTE_USER_STATUS + "=" + userStatusType

    
    ));
	
	 
	
	}
	
	
	public static String request(URL url)
    throws Exception {
    System.out.println(url.toString());
    URLConnection conn = url.openConnection();
    BufferedReader dis = new BufferedReader(
        new InputStreamReader(conn.getInputStream()));
    StringBuffer buff = new StringBuffer();
    String inputLine;

    while ((inputLine = dis.readLine()) != null) {
        buff.append(inputLine).append("\n");
    }
    dis.close();
    return buff.toString();
}
	
	private static String getUserRolesCSV(Set<VzdnSysRoles> roles){
		String csvRoles="";
		for(VzdnSysRoles role : roles){
			csvRoles+=role.getVzdnRoleMappingId()+",";
		}
		if(csvRoles.length()>1)
		csvRoles=csvRoles.substring(0,csvRoles.length()-1);
		return csvRoles;
	}
	
	
	
	
	public static String getUserRoles(VzdnUsers user) throws Exception{
		
		
		//String csvRoles = getUserRolesCSV(user.getRoles());	
	    String username = user.getUserName();
		ResourceBundle rb = ResourceBundle.getBundle("envProps");
		String openssoAdmin = rb.getString("opensso.admin.user");
		String encryptedPassword = rb.getString("opensso.admin.password"); 
		String password = PasswordUtil.decrypt(encryptedPassword);
		
		String serverUrl = rb.getString("opensso.serverURL");

		
		
		String res = request(new URL(serverUrl +
		        "/identity/authenticate?" +
		        "username=" + URLEncoder.encode(openssoAdmin, "UTF-8") +
		        "&password=" +
		        URLEncoder.encode(password, "UTF-8")));
		    	String  tokenId = res.substring(9);
		    	System.out.println("pre token : "+tokenId);
		    	tokenId = tokenId.substring(0, tokenId.length() -1);
		
		
		
		   	 System.out.println("Change email address");
		   	String output =  request(new URL(serverUrl +
			    "/identity/read?" +
			    "name="+username+"&"+"admin=" +
			    URLEncoder.encode(tokenId, "UTF-8") +	    
			    "&attributes_names=" + VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES 
			     
			    
			    
			    ));
		   	
		   	String attributeName = VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES;
		   	int startIndex = output.indexOf(attributeName)+attributeName.length();		   	
		   	String tempString = output.substring(output.indexOf(attributeName)+attributeName.length()+1);
		   	System.out.println("TempStr=" + tempString);
		   	int endIndex = output.indexOf(attributeName)+attributeName.length() + tempString.indexOf('\n') + 1;
		   	
		   	System.out.println("\n\n" + startIndex + "\n\n" +endIndex);
		   	if(startIndex >= endIndex)
		   		return null;
		   	String pair = output.substring(startIndex,endIndex).trim();
			String value = pair.substring("identitydetails.attribute.value".length() + 1 ).trim();
			
			System.out.println("^^^" + output + "***");
			System.out.println("\n\n'" + pair+"'");
			System.out.println("\n\nRoles from Opensso='" + value+"'");
			
			
			log.info("Roles from Opensso=" + value);
			
			return value;
		
		}
	
	
	public static VzdnCredentials getUserAttributesFromSSO(VzdnUsers user){
		try		
		{
			VzdnCredentials attribs = new VzdnCredentials();
			attribs.setFirstName(readAttribute(user.getUserName(), VzdnConstants.OPENSSO_ATTRIBUTE_FIRST_NAME));
			attribs.setLastName(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_LAST_NAME));
			attribs.setTitle(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_USER_TITLE));
			attribs.setPhone(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_USER_PHONE));
			attribs.setFax(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_USER_FAX));
			attribs.setMobile(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_USER_MOBILE));
			attribs.setCountry(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_USER_COUNTRY));
			attribs.setGtmCompanyId(readAttribute(user.getUserName(),VzdnConstants.OPENSSO_ATTRIBUTE_COMP_ID));
			return attribs;
		}
		catch(Exception exp){
			log.error("Issue with getting user info from opensso: OpenSSORestService.getUserAttributesFromSSO():\n\n" + exp);
			return null;
		}
	}
	
	
		public static String readAttribute(String username, String attribute) throws Exception{
			
		ResourceBundle rb = ResourceBundle.getBundle("envProps");
		String openssoAdmin = rb.getString("opensso.admin.user");
		String encryptedPassword = rb.getString("opensso.admin.password"); 
		String password = PasswordUtil.decrypt(encryptedPassword);		
		String serverUrl = rb.getString("opensso.serverURL");
						

		String res = request(new URL(serverUrl +
		        "/identity/authenticate?" +
		        "username=" + URLEncoder.encode(openssoAdmin, "UTF-8") +
		        "&password=" +
		        URLEncoder.encode(password, "UTF-8")));
		    	String  tokenId = res.substring(9);
		    	//System.out.println("pre token : "+tokenId);
		    	tokenId = tokenId.substring(0, tokenId.length() -1);
		
		
		   	String output =  request(new URL(serverUrl +
			    "/identity/read?" +
			    "name="+username+"&"+"admin=" +
			    URLEncoder.encode(tokenId, "UTF-8") +	    
			    "&attributes_names=" + attribute 
	
		   	));
		   	
		   	String attributeName = attribute;
		   	int startIndex = output.indexOf(attributeName)+attributeName.length();		   	
		   	String tempString = output.substring(output.indexOf(attributeName)+attributeName.length()+1);
		   	//System.out.println("TempStr=" + tempString);
		   	int endIndex = output.indexOf(attributeName)+attributeName.length() + tempString.indexOf('\n') + 1;
		   	
		   	//System.out.println("\n\n" + startIndex + "\n\n" +endIndex);
		   	if(startIndex >= endIndex)
		   		return null;
		   	String pair = output.substring(startIndex,endIndex).trim();
			String value = pair.substring("identitydetails.attribute.value".length() + 1 ).trim();
			
			//System.out.println("^^^" + output + "***");
			//System.out.println("\n\n'" + pair+"'");
			//System.out.println("\n\nRoles from Opensso='" + value+"'");
			
			return value;
		
		
		
		}
	
	
public static String getUserCredentialsFromSSO(VzdnUsers user) throws Exception{
		
		//String csvRoles = getUserRolesCSV(user.getRoles());	
	    String username = user.getUserName();
		ResourceBundle rb = ResourceBundle.getBundle("envProps");
		String openssoAdmin = rb.getString("opensso.admin.user");		
		String encryptedPassword = rb.getString("opensso.admin.password"); 
		String password = PasswordUtil.decrypt(encryptedPassword);
		String serverUrl = rb.getString("opensso.serverURL");

		
		
		String res = request(new URL(serverUrl +
		        "/identity/authenticate?" +
		        "username=" + URLEncoder.encode(openssoAdmin, "UTF-8") +
		        "&password=" +
		        URLEncoder.encode(password, "UTF-8")));
		    	String  tokenId = res.substring(9);
		    	System.out.println("pre token : "+tokenId);
		    	tokenId = tokenId.substring(0, tokenId.length() -1);
		
		
		   	String output =  request(new URL(serverUrl +
			    "/identity/read?" +
			    "name="+username+"&"+"admin=" +
			    URLEncoder.encode(tokenId, "UTF-8") +	    
			    "&attributes_names=" + VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES 
			     
			    
			    
			    ));
		   	
		   	String attributeName = VzdnConstants.OPENSSO_ATTRIBUTE_USER_ROLES;
		   	int startIndex = output.indexOf(attributeName)+attributeName.length();		   	
		   	String tempString = output.substring(output.indexOf(attributeName)+attributeName.length()+1);
		   	System.out.println("TempStr=" + tempString);
		   	int endIndex = output.indexOf(attributeName)+attributeName.length() + tempString.indexOf('\n') + 1;
		   	
		   	System.out.println("\n\n" + startIndex + "\n\n" +endIndex);
		   	if(startIndex >= endIndex)
		   		return null;
		   	String pair = output.substring(startIndex,endIndex).trim();
			String value = pair.substring("identitydetails.attribute.value".length() + 1 ).trim();
			
			System.out.println("^^^" + output + "***");
			System.out.println("\n\n'" + pair+"'");
			System.out.println("\n\nRoles from Opensso='" + value+"'");
			
			
			log.info("Roles from Opensso=" + value);
			
			return value;
		
		
		}
	
	
	//testing method
	public static void main(String args[]) throws Exception{
		VzdnUsers user = new VzdnUsers();
		user.setUserName("temp1user6@netpace.com");
		getUserRoles(user);
	}
	
	
}
