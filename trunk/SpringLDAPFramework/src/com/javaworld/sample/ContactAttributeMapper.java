package com.javaworld.sample;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;;

public class ContactAttributeMapper implements AttributesMapper{

	public Object mapFromAttributes(Attributes attributes) throws NamingException {
		ContactDTO contactDTO = new ContactDTO();

		Attribute firstName = attributes.get("sn");
		if(firstName != null)
			contactDTO.setFirstName((String)firstName.get());

		Attribute lastName = attributes.get("givenname");
		if(lastName != null)
		contactDTO.setLastName((String)lastName.get());

		Attribute cn = attributes.get("cn");
		if(cn != null)
		contactDTO.setCn((String)cn.get());

		Attribute mail = attributes.get("mail");
		if(mail != null)
		contactDTO.setMail((String)mail.get());
		
		Attribute title = attributes.get("vzdn-title");
		if(title != null)
		contactDTO.setTitle((String)title.get());

		Attribute phoneNumber = attributes.get("vzdn-phone-number");
		if(phoneNumber != null)
		contactDTO.setPhoneNumber((String)phoneNumber.get());

		Attribute faxNumber = attributes.get("vzdn-fax-number");
		if(faxNumber != null)
		contactDTO.setFaxNumber((String)faxNumber.get());

		Attribute mobileNumber = attributes.get("vzdn-mobile-number");
		if(mobileNumber != null)
		contactDTO.setMobileNumber((String)mobileNumber.get());

		Attribute userType = attributes.get("vzdn-user-type");
		if(userType != null)
		contactDTO.setUserType((String)userType.get());

		Attribute gtmCompanyId = attributes.get("vzdn-dev-organization-id");
		if(gtmCompanyId != null)
		contactDTO.setGtmCompanyId((String)gtmCompanyId.get());

		Attribute country = attributes.get("vzdn-country");
		if(country != null)
			contactDTO.setCountry((String)country.get());
				
		return contactDTO;
	}

}
