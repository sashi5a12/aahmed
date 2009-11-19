INSERT INTO vzdn_sys_privileges VALUES(24,'dev_report','Registered Developers Report','system',NULL,NULL,NULL,NULL,NULL,'DEV_REPORT',NULL);
INSERT INTO vzdn_role_privileges VALUES(5,24);
INSERT INTO vzdn_reports VALUES(3,'Registered Developers Report','devreport.action',24,'Y','Y','Y','Y','system',NULL,'system',NULL);



INSERT INTO vzdn_sys_roles(role_id, role_name, role_description, type_id,vzdn_role_mapping_id) VALUES(46,'Newsletter Admin','Newsletter Administration',2,1002);
INSERT INTO vzdn_role_privileges(role_id, privilege_id) VALUES(46,20);


INSERT INTO vzdn_sys_roles(role_id, role_name, role_description, type_id,vzdn_role_mapping_id) VALUES(47,'Core - Reports','Core Reports',2,1003);
INSERT INTO vzdn_sys_roles(role_id, role_name, role_description, type_id,vzdn_role_mapping_id) VALUES(48,'Core - Verizon Roles Privileges Report','Core - Verizon Roles Privileges Report',2,1004);
INSERT INTO vzdn_sys_roles(role_id, role_name, role_description, type_id,vzdn_role_mapping_id) VALUES(49,'Core - Verizon Users By Role Report','Core - Verizon Users By Role Report',2,1005);
INSERT INTO vzdn_sys_roles(role_id, role_name, role_description, type_id,vzdn_role_mapping_id) VALUES(50,'Core - Registered Developers Report','Core - Registered Developers Report',2,1006);
INSERT INTO vzdn_role_privileges(role_id, privilege_id) VALUES(47,21),(48,22),(49,23),(50,24);