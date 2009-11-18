package com.netpace.aims.controller.webservices;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;

import org.apache.soap.util.xml.XMLParserUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.netpace.aims.controller.application.WapInfoSpaceSubmitDCRBean;
import com.netpace.aims.util.XMLUtils;

public class GenerateDeckChangePayLoad  
{

    public static void main(String[] args)
    {
        try
        {
        	WapInfoSpaceSubmitDCRBean wapInfoSpaceSubmitDCRBean = new WapInfoSpaceSubmitDCRBean();
        	String doc = generate(wapInfoSpaceSubmitDCRBean);
        	System.out.println("The xml document is: \n" + doc);
            
        }
       
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static String generate(WapInfoSpaceSubmitDCRBean dcrBean)
    {

		DocumentBuilder builder = XMLParserUtils.getXMLDocBuilder();		  
		  
		Document doc_action = builder.newDocument();    		
	
		Element elem_root = doc_action.createElement("DeckChangeRequest");
		elem_root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");		
		elem_root.setAttribute("xsi:noNamespaceSchemaLocation", "./DCR.xsd");
		doc_action.appendChild(elem_root); 
		
		Element elem_vendor_data = doc_action.createElement("VendorData");
		elem_root.appendChild(elem_vendor_data);
		
		//TODO: VendorLoginID
		//Element elem_vendor_login_id = XmlUtility.getElementWithText(doc_action, "VendorLoginID", dcrBean.getVendorProductCode());
		//elem_vendor_data.appendChild(elem_vendor_login_id);
		
		Element elem_vendor_name = XmlUtility.getElementWithText(doc_action, "VendorName", dcrBean.getVendorName());
		elem_vendor_data.appendChild(elem_vendor_name);
		
		Element elem_vendor_id = XmlUtility.getElementWithText(doc_action, "VendorId", dcrBean.getVendorId());
		elem_vendor_data.appendChild(elem_vendor_id);		
		
		
		
		if (dcrBean.getVendorLogoURL() != null) {
			Element elem_vendor_logo_url = XmlUtility.getElementWithText(doc_action, "VendorLogoURL", dcrBean.getVendorLogoURL());
			elem_vendor_data.appendChild(elem_vendor_logo_url);	
		}		
		

		
		if (dcrBean.getBusinessContactName() != null){
			Element elem_vendor_bus_contact = XmlUtility.getContactTypeWithText(
					doc_action, "VendorBusinessContact", 
					dcrBean.getBusinessContactName(),
					dcrBean.getBusinessContactTitle(),
					dcrBean.getBusinessContactAddressStreet(),
					dcrBean.getBusinessContactAddressCity(),
					dcrBean.getBusinessContactAddressState(),
					dcrBean.getBusinessContactAddressZip(),
					dcrBean.getBusinessContactOfficePhone(),
					dcrBean.getBusinessContactMobilePhone(),
					dcrBean.getBusinessContactEmail()
					);		
			elem_vendor_data.appendChild(elem_vendor_bus_contact);	
		}
		
		if (dcrBean.getEscalationContactName() != null){		
			Element elem_vendor_esc_contact = XmlUtility.getEscalationContactTypeWithText(
					doc_action, "VendorEscalationContact", 
					dcrBean.getEscalationContactName(),
					dcrBean.getEscalationContactTitle(),
					dcrBean.getEscalationContactAddressStreet(),
					dcrBean.getEscalationContactAddressCity(),
					dcrBean.getEscalationContactAddressState(),
					dcrBean.getEscalationContactAddressZip(),
					dcrBean.getEscalationContactOfficePhone(),
					dcrBean.getEscalationContactMobilePhone(),
					dcrBean.getEscalationContactEmail(),
					dcrBean.getEscalationPhone(),
					dcrBean.getEscalationInstructions()				
					);		
			elem_vendor_data.appendChild(elem_vendor_esc_contact);			
		}
		
		if (dcrBean.getCustomerContactName() != null){		
			Element elem_customer_contact = XmlUtility.getCustomerContactTypeWithText(
					doc_action, "CustomerContact", 
					dcrBean.getCustomerContactName(),
					dcrBean.getCustomerContactEmail()		
					);		
			elem_vendor_data.appendChild(elem_customer_contact);	
		}
		
			
		Element elem_product_data = doc_action.createElement("ProductData");	
		
		elem_product_data.setAttributeNode(XmlUtility.getActionTypeAttribute(doc_action,
													"Action", dcrBean.getAction()));
		
		Element elem_vendor_prod_code = XmlUtility.getElementWithText(doc_action, "VendorProductCode", dcrBean.getVendorProductCode());
		elem_product_data.appendChild(elem_vendor_prod_code);		
		
		Element elem_vendor_prod_version = XmlUtility.getElementWithText(doc_action, "VendorProductVersion", dcrBean.getVendorProductVersion());
		elem_product_data.appendChild(elem_vendor_prod_version);		

		if (dcrBean.getProductPresentationNameLargeScreens() != null){	
			Element elem_prod_pres_name_large_screens = XmlUtility.getElementWithText(doc_action, 
						"ProductPresentationNameLargeScreens", dcrBean.getProductPresentationNameLargeScreens());
			elem_product_data.appendChild(elem_prod_pres_name_large_screens);		
		}
		
		Element elem_prod_pres_name_small_screens = XmlUtility.getElementWithText(doc_action, 
				"ProductPresentationNameSmallScreens", dcrBean.getProductPresentationNameSmallScreens());
		elem_product_data.appendChild(elem_prod_pres_name_small_screens);	
		

		if (dcrBean.getProductIconURL() != null){			
			Element elem_prod_icon_url = XmlUtility.getElementWithText(doc_action, 
					"ProductIconURL", dcrBean.getProductIconURL());
			elem_product_data.appendChild(elem_prod_icon_url);		
		}
		

		if (dcrBean.getProductImageURL() != null){		
			Element elem_prod_image_url = XmlUtility.getElementWithText(doc_action, 
					"ProductImageURL", dcrBean.getProductImageURL());
			elem_product_data.appendChild(elem_prod_image_url);		
		}
		
		Element elem_long_desc = XmlUtility.getElementWithText(doc_action, 
				"LongDescription", dcrBean.getLongDescription());
		elem_product_data.appendChild(elem_long_desc);			
		
		Element elem_short_desc = XmlUtility.getElementWithText(doc_action, 
				"ShortDescription", dcrBean.getShortDescription());
		elem_product_data.appendChild(elem_short_desc);
		
		if (dcrBean.getDemoUrl() != null){			
			Element elem_demo_url = XmlUtility.getElementWithText(doc_action, 
					"DemoURL", dcrBean.getDemoUrl());
			elem_product_data.appendChild(elem_demo_url);
		}
		
		Element elem_test_url = XmlUtility.getElementWithText(doc_action, 
				"TestURL", dcrBean.getTestUrl());
		elem_product_data.appendChild(elem_test_url);
		

		if (dcrBean.getAvailabilityDateForTestURL() != null){		
			Element elem_avail_date_test_url = XmlUtility.getElementWithText(doc_action, 
					"AvailabilityDateForTestURL", dcrBean.getAvailabilityDateForTestURL());
			elem_product_data.appendChild(elem_avail_date_test_url);	
		}
		
		Element elem_prod_url = XmlUtility.getElementWithText(doc_action, 
				"ProductionURL", dcrBean.getProductionUrl());
		elem_product_data.appendChild(elem_prod_url);
		
		if (dcrBean.getAvailabilityDateForProductionURL() != null){		
			Element elem_avail_date_prod_url = XmlUtility.getElementWithText(doc_action, 
					"AvailabilityDateForProductionURL", dcrBean.getAvailabilityDateForProductionURL());
			elem_product_data.appendChild(elem_avail_date_prod_url);	
		}
		
		if (dcrBean.getDesktopUrl() != null){		
			Element elem_desktop_url = XmlUtility.getElementWithText(doc_action, 
					"DesktopURL", dcrBean.getDesktopUrl());
			elem_product_data.appendChild(elem_desktop_url);	
		}
		
		ArrayList userProfileArrayList = dcrBean.getUserProfile();
		if (userProfileArrayList != null){
			for (int i = 0; i < userProfileArrayList.size(); i++) {
				
				if (userProfileArrayList.get(i) != null) {
					Element elem_user_profile = XmlUtility.getElementWithText(doc_action, 
							"UserProfile", (String)userProfileArrayList.get(i));
					elem_product_data.appendChild(elem_user_profile);	
				}
				
			}
		}
			
		Element elem_premium_indicator = XmlUtility.getElementWithText(doc_action, 
				"PremiumIndicator", dcrBean.getPremiumIndicator());
		elem_product_data.appendChild(elem_premium_indicator);		
		
		ArrayList initPortalPlacementCategoryArrayList = dcrBean.getInitialPortalPlacementCategory();
		ArrayList initPortalPlacementSubCategoryArrayList = dcrBean.getInitialPortalPlacementSubCategory();
		ArrayList initPortalPlacementPositionArrayList = dcrBean.getInitialPortalPlacementPosition();
		
		if (initPortalPlacementCategoryArrayList != null && initPortalPlacementSubCategoryArrayList != null
					&& initPortalPlacementPositionArrayList != null){
			
			for (int i = 0; i < initPortalPlacementCategoryArrayList.size(); i++) {
				
				if (initPortalPlacementCategoryArrayList.get(i) != null) {
					Element elem_init_portal_placement = XmlUtility.getInitPortalPlacementTypeWithText(
							doc_action, "InitialPortalPlacement", 
							(String)initPortalPlacementCategoryArrayList.get(i),
							(String)initPortalPlacementSubCategoryArrayList.get(i),
							(String)initPortalPlacementPositionArrayList.get(i)
							);		
					elem_product_data.appendChild(elem_init_portal_placement);	
				}
			}
		}
		
		elem_root.appendChild(elem_product_data);

		return XMLUtils.docToString(doc_action);	
    }
}