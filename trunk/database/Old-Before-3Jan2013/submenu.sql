/*
SQLyog Enterprise - MySQL GUI
MySQL - 5.0.92 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;



create table `vap_main_menu` (
	`id` double ,
	`name` varchar (45),
	`sort_order` double 
); 

CREATE TABLE `vap_sub_menu` (                                                                                    
                `id` int(5) NOT NULL auto_increment,                                                                           
                `item_name` varchar(25) NOT NULL,                                                                              
                `menu_id` int(2) NOT NULL,                                                                                     
                `system_role_id` int(10) unsigned NOT NULL,                                                                    
                `url` varchar(250) NOT NULL,                                                                                   
                `sort_order` int(2) default NULL,                                                                              
                PRIMARY KEY  (`id`),                                                                                           
                KEY `FK_vap_sub_menu` (`menu_id`),                                                                             
                KEY `FK_vap_sub_menu_sys_role` (`system_role_id`),                                                             
                CONSTRAINT `FK_vap_sub_menu` FOREIGN KEY (`menu_id`) REFERENCES `vap_main_menu` (`id`),                        
                CONSTRAINT `FK_vap_sub_menu_sys_role` FOREIGN KEY (`system_role_id`) REFERENCES `vap_system_role` (`role_id`)  
              ) ENGINE=InnoDB ;
			  
			  
insert into `vap_main_menu` (`id`, `name`, `sort_order`) values('1','Menu','1');

insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('1','My Profile','1','1','/secure/myProfile.do','1');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('2','Company Information','1','1','/secure/companyInformation.do','2');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('3','Manage Users','1','1','/secure/manageUsers.do','3');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('4','Options','1','1','/secure/options.do','4');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('5','Support','1','1','/secure/support.do','5');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('6','My Profile','1','3','/secure/myProfile.do','1');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('7','Worklist','1','3','/secure/worklist.do','2');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('8','Product Dashboard','1','3','/secure/dashboard.do','3');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('9','Manage Partners','1','3','/secure/managePartners.do','4');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('10','Manage Products','1','3','/secure/manageProducts.do','5');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('11','Reports','1','3','/secure/reports.do','6');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('12','Manage Users','1','3','/secure/manageUsers.do','7');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('13','Options','1','3','/secure/options.do','8');
insert into `vap_sub_menu` (`id`, `item_name`, `menu_id`, `system_role_id`, `url`, `sort_order`) values('14','Support','1','3','/secure/support.do','9');
