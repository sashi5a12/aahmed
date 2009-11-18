INSERT INTO vzdn_sub_menus 
	(sub_menu_id, sub_menu_name, sub_menu_url, sort_order, created_by, menu_id)
	VALUES
	(22, 'Important information on getting paid', 'http://developer.verizon.com/jsps/devCenters/Smart_Phone/Landing_Pages/sp_gtm_VCASTAppsVendorPaymentSetup.jsp', 9, 'system', 3)
/	
	
INSERT INTO vzdn_sys_privileges 
	(privilege_id, privilege_name, privilege_description, created_by, sub_menu_id, privilege_key)
	VALUES
	(25, 'vcastapps_vendor_payment_setup', 'Re-directed the Developer on V CAST Apps Vendor Payment Setup Instructions', 'system', 22, 'VCASTAPPS_VENDOR_PAYMENT_SETUP' )
/	
INSERT INTO vzdn_role_privileges (role_id, privilege_id) VALUES (6, 25)
/