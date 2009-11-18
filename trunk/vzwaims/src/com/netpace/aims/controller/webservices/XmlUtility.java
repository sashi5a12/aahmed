package com.netpace.aims.controller.webservices;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;


public class XmlUtility 
{

	 public static Element getElementWithText (Document doc, String elementName, String textValue)
    {    
		Element elem_text = doc.createElement(elementName);
		elem_text.appendChild(doc.createTextNode(textValue));
		
		return elem_text;
    }
	 
	 
	 public static Element getContactTypeWithText 
		(Document doc, String elementName, String name, String title,
					String street, String city, String state, String zip,
					String officePhone, String mobilePhone, String email)
	{    
		
		Element elem_contact_type = doc.createElement(elementName);
		
		Element elem_name = getElementWithText(doc, "Name", name);
		Element elem_title = getElementWithText(doc, "Title", title);
		Element elem_address = getAddressTypeWithText(doc, street, city, state, zip);
		Element elem_officePhone = getPhoneTypeWithText(doc, "OfficePhone", officePhone);
		Element elem_mobilePhone = getPhoneTypeWithText(doc, "MobilePhone", mobilePhone);
		Element elem_email = getElementWithText(doc, "Email", email);
						
		
		elem_contact_type.appendChild(elem_name);
		elem_contact_type.appendChild(elem_title);
		if (street != null) {
			elem_contact_type.appendChild(elem_address);
		}		
		
		if (officePhone != null){
			elem_contact_type.appendChild(elem_officePhone);
		}
		
		if (mobilePhone != null){
			elem_contact_type.appendChild(elem_mobilePhone);
		}		

		elem_contact_type.appendChild(elem_email);
		
		
		return elem_contact_type;
	}
	 
	 public static Element getEscalationContactTypeWithText 
		(Document doc, String elementName, String name, String title,
					String street, String city, String state, String zip,
					String officePhone, String mobilePhone, String email,
					String escalationPhone, String escalationInstructions)
	{    
		
		Element elem_escalation_contact_type = getContactTypeWithText(
							doc, elementName, name, title,	
							street, city, state, zip,
							officePhone, mobilePhone, email);
	
		Element elem_esc_phone = getPhoneTypeWithText(doc, "EscalationPhone", escalationPhone);
		Element elem_esc_instructions = getElementWithText(doc, "EscalationInstructions", escalationInstructions);								
		
		
		if (escalationPhone != null){
			elem_escalation_contact_type.appendChild(elem_esc_phone);
		}
		
		elem_escalation_contact_type.appendChild(elem_esc_instructions);
		
		return elem_escalation_contact_type;
	}
	 
	 public static Element getCustomerContactTypeWithText 
		(Document doc, String elementName, String name, String email)
	{    
		
		Element elem_customer_contact_type = doc.createElement(elementName);
		
		Element elem_name = getElementWithText(doc, "Name", name);
		Element elem_email = getElementWithText(doc, "Email", email);
									
		elem_customer_contact_type.appendChild(elem_name);		
        elem_customer_contact_type.appendChild(elem_email);
		
		return elem_customer_contact_type;
	}	 
	 
	 public static Element getInitPortalPlacementTypeWithText 
		(Document doc, String elementName, String category, String subCategory, String position)
	{    		
		Element elem_init_portal_placement_type = doc.createElement(elementName);
		
		Element elem_category = getElementWithText(doc, "Category", category);
		Element elem_subCategory = getElementWithText(doc, "Subcategory", subCategory);
		Element elem_position = getElementWithText(doc, "Position", position);
									
		elem_init_portal_placement_type.appendChild(elem_category);
		elem_init_portal_placement_type.appendChild(elem_subCategory);
		elem_init_portal_placement_type.appendChild(elem_position);
		
		return elem_init_portal_placement_type;
	}
	 
	 public static Attr getActionTypeAttribute 
		(Document doc, String attrName, String action)
	{    		
	 	Attr attr_action_type = doc.createAttribute(attrName);
	 	attr_action_type.setValue(action);
	 	
		return attr_action_type;
	}		 
	 
	 public static Element getAddressTypeWithText 
	 			(Document doc, String street, String city, String state, String zip)
	    {    
			
	 		Element elem_address = doc.createElement("Address");
	 		
	 		Element elem_street = getElementWithText(doc, "Street", street);
	 		Element elem_city = getElementWithText(doc, "City", city);
	 		Element elem_state = getElementWithText(doc, "State", state);
	 		Element elem_zip = getElementWithText(doc, "Zip", zip);	 				
			
			elem_address.appendChild(elem_street);
			elem_address.appendChild(elem_city);
			elem_address.appendChild(elem_state);
			elem_address.appendChild(elem_zip);				
			
			return elem_address;
	    }
	 
	 public static Element getPhoneTypeWithText 
					(Document doc, String elementName, String areaCodeAndPhone)
		{    			
	 		Element elem_phone_type = doc.createElement(elementName);	
	 		Element elem_phone = getElementWithText(doc, "Phone", areaCodeAndPhone);
	 		
	 		elem_phone_type.appendChild(elem_phone);
		
			return elem_phone_type;
		}	 	 
}
