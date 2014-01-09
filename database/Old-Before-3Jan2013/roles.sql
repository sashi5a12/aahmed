
CREATE TABLE `deviceportal`.`vap_system_role`(     
                `role_id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,     
                `title` VARCHAR(50) NOT NULL ,     
                `description` VARCHAR(250) NOT NULL ,     
                `created_date` DATETIME DEFAULT NULL,   
                `created_by` VARCHAR(50) DEFAULT NULL,   
                `updated_date` DATETIME DEFAULT NULL,   
                `updated_by` VARCHAR(50) DEFAULT NULL,   
                `is_active` VARCHAR(1) DEFAULT '1',
                PRIMARY KEY (`role_id`)  
) ENGINE=INNODB  DEFAULT CHARSET=utf8;

CREATE TABLE `deviceportal`.`vap_user_role`( 
                `user_role_id` INT UNSIGNED NOT NULL AUTO_INCREMENT , 
                `role_id` INT UNSIGNED NOT NULL , 
                `user_id` INT UNSIGNED NOT NULL , 
                `created_date` DATETIME DEFAULT NULL,   
                `created_by` VARCHAR(50) DEFAULT NULL,   
                `updated_date` DATETIME DEFAULT NULL,   
                `updated_by` VARCHAR(50) DEFAULT NULL,   
                `is_active` VARCHAR(1) DEFAULT '1',
                PRIMARY KEY (`user_role_id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8;


ALTER TABLE `deviceportal`.`vap_user_role` ADD CONSTRAINT `FK_vap_user_role_vap_user_fk1` FOREIGN KEY (`user_id`) REFERENCES `vap_user` (`user_id`) ON DELETE CASCADE  ON UPDATE CASCADE ;
ALTER TABLE `deviceportal`.`vap_user_role` ADD CONSTRAINT `FK_vap_user_role_vap_system_role_fk2` FOREIGN KEY (`role_id`) REFERENCES `vap_system_role` (`role_id`) ON DELETE CASCADE  ON UPDATE CASCADE ;



INSERT INTO vap_system_role(`role_id`,`title`,`description`,`created_date`,`created_by`,`updated_date`,`updated_by`,`is_active`) VALUES ( NULL,'ROLE_USER','All vap users',CURDATE(),'system',CURDATE(),'system','1');
INSERT INTO vap_system_role(`role_id`,`title`,`description`,`created_date`,`created_by`,`updated_date`,`updated_by`,`is_active`) VALUES ( NULL,'ROLE_ACCOUNT','All vap users with no company',CURDATE(),'system',CURDATE(),'system','1');
INSERT INTO vap_system_role(`role_id`,`title`,`description`,`created_date`,`created_by`,`updated_date`,`updated_by`,`is_active`) VALUES ( NULL,'ROLE_ADMIN','All vap admin users',CURDATE(),NULL,CURDATE(),'system','1');


create table `vap_application_properties`(     
                `property_id` int UNSIGNED NOT NULL AUTO_INCREMENT ,
                `type` varchar(250) NOT NULL ,
                `name` varchar(250) NOT NULL ,
                `value` varchar(2000) ,
                `sort_order` int NOT NULL DEFAULT '0' ,
                `created_date` DATETIME DEFAULT NULL,   
                `created_by` VARCHAR(50) DEFAULT NULL,   
                `updated_date` DATETIME DEFAULT NULL,   
                `updated_by` VARCHAR(50) DEFAULT NULL,   
                `is_active` VARCHAR(1) DEFAULT '1',
                PRIMARY KEY (`property_id`)  
);




insert into `vap_application_properties` (`property_id`, `type`, `name`, `value`, `sort_order`, `is_active`, `created_date`, `created_by`, `updated_date`, `updated_by`) values('1','ACCESS_CONTROL_METADATA','/signin.do','IS_AUTHENTICATED_ANONYMOUSLY','0','1','2012-12-05 00:00:00','system','2012-12-05 00:00:00','system');
insert into `vap_application_properties` (`property_id`, `type`, `name`, `value`, `sort_order`, `is_active`, `created_date`, `created_by`, `updated_date`, `updated_by`) values('2','ACCESS_CONTROL_METADATA','/secure','IS_AUTHENTICATED_FULLY','0','1','2012-12-05 00:00:00','system','2012-12-05 00:00:00','system');
insert into `vap_application_properties` (`property_id`, `type`, `name`, `value`, `sort_order`, `is_active`, `created_date`, `created_by`, `updated_date`, `updated_by`) values('3','ACCESS_CONTROL_METADATA','/company/companyInformation.do','ROLE_ACCOUNT,ROLE_USER','0','1','2012-12-05 00:00:00','system','2012-12-05 00:00:00','system');
insert into `vap_application_properties` (`property_id`, `type`, `name`, `value`, `sort_order`, `is_active`, `created_date`, `created_by`, `updated_date`, `updated_by`) values('4','ACCESS_CONTROL_METADATA','default','IS_AUTHENTICATED_ANONYMOUSLY','0','1','2012-12-05 00:00:00','system','2012-12-05 00:00:00','system');
INSERT INTO `vap_application_properties`(`property_id`,`type`,`name`,`value`,`sort_order`,`created_date`,`created_by`,`updated_date`,`updated_by`,`is_active`) VALUES ('5','APPLICATION_PROPERTY','authentication.failure.redirect.url','/signin.do?error=true','0', '2012-12-05 00:00:00', 'system', '2012-12-05 00:00:00', 'system','1');


