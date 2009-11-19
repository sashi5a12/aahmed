USE vzdn;

INSERT INTO vzdn_sub_menus VALUES(21,'Reports','reports.action',NULL,11,'system',NULL,NULL,NULL,5);

INSERT INTO vzdn_sys_privileges VALUES(21,'report_viewer','Report Viewer','system',NULL,NULL,NULL,NULL,21,'REPORT_VIEWER',NULL);

INSERT INTO vzdn_role_privileges VALUES(5,21);

DROP TABLE vzdn_report_roles;

DROP TABLE vzdn_reports;

DROP TABLE vzdn_report_roles$aud;

DROP TABLE vzdn_reports$aud;

CREATE TABLE `vzdn_reports` (                   
`report_id` INT(11) NOT NULL AUTO_INCREMENT,  
`display_name` VARCHAR(100) DEFAULT NULL,             
`report_action` VARCHAR(100) DEFAULT NULL,                                                                    
`privlege_id` INT(11) DEFAULT NULL,       	
`pdf_export` CHAR(1) DEFAULT NULL,            
`cvs_export` CHAR(1) DEFAULT NULL,            
`rtf_export` CHAR(1) DEFAULT NULL,            
`html_export` CHAR(1) DEFAULT NULL,           
`created_by` VARCHAR(100) DEFAULT NULL,       
`created_date` DATETIME DEFAULT NULL,         
`last_updated_by` VARCHAR(100) DEFAULT NULL,  
`last_updated_date` DATETIME DEFAULT NULL,    
PRIMARY KEY  (`report_id`),
KEY `FK_vzdn_reports` (`privlege_id`),                                                                      
CONSTRAINT `FK_vzdn_reports` FOREIGN KEY (`privlege_id`) REFERENCES `vzdn_sys_privileges` (`privilege_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;              

INSERT INTO vzdn_sys_privileges VALUES(22,'role_report','Verizon Roles Privileges Report','system',NULL,NULL,NULL,NULL,NULL,'ROLE_REPORT',NULL);
INSERT INTO vzdn_sys_privileges VALUES(23,'user_report','Verizon Users By Role Report','system',NULL,NULL,NULL,NULL,NULL,'USER_REPORT',NULL);
INSERT INTO vzdn_role_privileges VALUES(5,22),(5,23);
			  	  
INSERT INTO vzdn_reports VALUES(1,'Verizon Roles Privileges Report','rolereport.action',22,'Y','Y','Y','Y','system',NULL,'system',NULL);
INSERT INTO vzdn_reports VALUES(2,'Verizon Users By Role Report','userreport.action',23,'Y','Y','Y','Y','system',NULL,'system',NULL);
			  
USE vzdn_audit;
			  
CREATE TABLE `vzdn_reports$aud` (
  `report_id` INT(11) ,  
  `display_name` VARCHAR(100) DEFAULT NULL,             
  `report_action` VARCHAR(100) DEFAULT NULL,                                                                    
  `privlege_id` INT(11) DEFAULT NULL,       	
  `pdf_export` CHAR(1) DEFAULT NULL,            
  `cvs_export` CHAR(1) DEFAULT NULL,            
  `rtf_export` CHAR(1) DEFAULT NULL,            
  `html_export` CHAR(1) DEFAULT NULL,           
  `created_by` VARCHAR(100) DEFAULT NULL,       
  `created_date` DATETIME DEFAULT NULL,         
  `last_updated_by` VARCHAR(100) DEFAULT NULL,  
  `last_updated_date` DATETIME DEFAULT NULL, 
  `AUD_ACTION` VARCHAR(3) DEFAULT NULL,
  `AUD_TIMESTAMP` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `AUD_USER` VARCHAR(30) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;


USE vzdn;

DELIMITER $$ 
CREATE TRIGGER vzdn_reports$audINS AFTER INSERT ON vzdn_reports
FOR EACH ROW BEGIN 
INSERT INTO vzdn_reports$aud (report_id,display_name,report_action,privlege_id,pdf_export,cvs_export,rtf_export,html_export,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.report_id,NEW.display_name,NEW.report_action,NEW.privlege_id,NEW.pdf_export,NEW.cvs_export,NEW.rtf_export,NEW.html_export,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;


DELIMITER $$ 
CREATE TRIGGER vzdn_reports$audUPD AFTER UPDATE ON vzdn_reports
FOR EACH ROW BEGIN 
INSERT INTO vzdn_reports$aud (report_id,display_name,report_action,privlege_id,pdf_export,cvs_export,rtf_export,html_export,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.report_id,NEW.display_name,NEW.report_action,NEW.privlege_id,NEW.pdf_export,NEW.cvs_export,NEW.rtf_export,NEW.html_export,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;


DELIMITER $$ 
CREATE TRIGGER vzdn_reports$audDEL AFTER DELETE ON vzdn_reports
FOR EACH ROW BEGIN 
INSERT INTO vzdn_reports$aud (report_id,display_name,report_action,privlege_id,pdf_export,cvs_export,rtf_export,html_export,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.report_id,OLD.display_name,OLD.report_action,OLD.privlege_id,OLD.pdf_export,OLD.cvs_export,OLD.rtf_export,OLD.html_export,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

			  