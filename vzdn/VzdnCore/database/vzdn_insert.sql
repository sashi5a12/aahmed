insert  into `vzdn_menus`(`menu_id`,`menu_name`,`menu_url`,`image_name`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) values (1,'My Info',NULL,'icon_myinfo.gif',1,'ikram','0000-00-00 00:00:00',NULL,NULL),(2,'Community',NULL,'icon_community.gif',2,'ikram','0000-00-00 00:00:00',NULL,NULL),(3,'Go To Market',NULL,'icon_gotoMarket.gif',3,'ikram','0000-00-00 00:00:00',NULL,NULL),(4,'Verizon Developer Network News',NULL,'icon_rss2.gif',4,'ikram','0000-00-00 00:00:00',NULL,NULL),(5,'Management',NULL,'icon_management.gif',5,'ikram','0000-00-00 00:00:00',NULL,NULL);

#insert  into `vzdn_sys_roles`(`role_id`,`role_name`,`role_description`,`role_type`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) values (1,'Developer','Developer',NULL,'ikram',NULL,NULL,NULL),(2,'Administrator','Administrator',NULL,'ikram',NULL,NULL,NULL);

insert into vzdn.vzdn_type_defs (type_name,type_description) values ("Developer","description"); 
insert into vzdn.vzdn_type_defs (type_name,type_description) values ("Verizon","description"); 
insert into vzdn.vzdn_type_defs (type_name,type_description) values ("All","description"); 



insert into vzdn.vzdn_types (type_id,type_value,type_def_id,description,sort_order) values (1,"Developer",1,"some desc",1); 
insert into vzdn.vzdn_types (type_id,type_value,type_def_id,description,sort_order) values (2,"Verizon",2,"some desc",2); 
insert into vzdn.vzdn_types (type_id,type_value,type_def_id,description,sort_order) values (3,"Both",3,"some desc",3); 



insert into vzdn.vzdn_sys_roles(role_name,role_description,type_id,vzdn_role_mapping_id) values ("Forum User","forum admin",1,101); 
insert into vzdn.vzdn_sys_roles(role_name,role_description,type_id,vzdn_role_mapping_id) values ("Forum Admin","forum admin",3,201); 
insert into vzdn.vzdn_sys_roles(role_name,role_description,type_id,vzdn_role_mapping_id) values ("Blog User","blog user",1,102); 
insert into vzdn.vzdn_sys_roles(role_name,role_description,type_id,vzdn_role_mapping_id) values ("Blog Admin","blog admin",2,202); 


insert  into `vzdn_sub_menus`(`sub_menu_id`,`sub_menu_name`,`sub_menu_url`,`image_name`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`menu_id`) values (1,'Edit Profile',NULL,NULL,1,'ikram',NULL,NULL,NULL,1),(2,'Notifications',NULL,NULL,2,'ikram',NULL,NULL,NULL,1),(3,'Blog',NULL,NULL,1,'ikram',NULL,NULL,NULL,2),(4,'Forum',NULL,NULL,2,'ikram',NULL,NULL,NULL,2),(5,'Application',NULL,NULL,1,'ikram',NULL,NULL,NULL,3),(6,'Alliances',NULL,NULL,2,'ikram',NULL,NULL,NULL,3),(7,'New JDK Download Available',NULL,NULL,1,'ikram',NULL,NULL,NULL,4),(8,'Brew gets better',NULL,NULL,2,'ikram',NULL,NULL,NULL,4),(9,'Notifications',NULL,NULL,1,'ikram',NULL,NULL,NULL,5),(10,'Content Publishing',NULL,NULL,2,'ikram',NULL,NULL,NULL,5),(11,'Reports',NULL,NULL,3,'ikram',NULL,NULL,NULL,5),(12,'Ticket Management',NULL,NULL,4,'ikram',NULL,NULL,NULL,5);

insert  into `vzdn_sys_privileges`(`privilege_id`,`privilege_name`,`privilege_description`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`menu_id`,`sub_menu_id`,`privilege_key`,`available_to`) values (1,'edit_profile','Edit Profile','ikram',NULL,NULL,NULL,NULL,1,NULL,NULL),(2,'notifications','Notifications','ikram',NULL,NULL,NULL,NULL,2,NULL,NULL),(3,'blog','Blogs','ikram',NULL,NULL,NULL,NULL,3,NULL,NULL),(4,'forum','Forums','ikram',NULL,NULL,NULL,NULL,4,NULL,NULL),(5,'applications','Applications','ikram',NULL,NULL,NULL,NULL,5,NULL,NULL),(6,'alliances','Alliances','ikram',NULL,NULL,NULL,NULL,6,NULL,NULL),(7,'manage_notifications','Notifications Management','ikram',NULL,NULL,NULL,NULL,9,NULL,NULL),(8,'publish_content','Conent Publishing','ikram',NULL,NULL,NULL,NULL,10,NULL,NULL),(9,'reports','Reports','ikram',NULL,NULL,NULL,NULL,11,NULL,NULL),(10,'manage_tickets','Tickets Management','ikram',NULL,NULL,NULL,NULL,12,NULL,NULL);

insert  into `vzdn_users`(`user_id`,`user_name`,`password`,`title`,`last_name`,`first_name`,`phone_number`,`security_question`,`security_answer`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`fax_number`,`mobile_number`,`newsletter_opt_out`,`status_type_id`,`user_type_id`) values (1,'Administrator','ikram','Mr.','Khan','Ikram',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'Ikramullah','ikram','Mr.','Khan','Inam',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

#insert  into `vzdn_role_privileges`(`role_id`,`privilege_id`) values (1,1),(2,1),(1,2),(2,2),(1,3),(2,3),(1,4),(2,4),(1,5),(2,5),(1,6),(2,6),(2,7),(2,8),(2,9),(2,10);






insert  into `vzdn_users`(`user_name`,`password`,`title`,`last_name`,`first_name`,`phone_number`,`security_question`,`security_answer`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`fax_number`,`mobile_number`,`newsletter_opt_out`,`status_type_id`,`user_type_id`) values ('mudassir','mudassir','Mr.','Mudassir','Shahabuddin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('sajjad','sajjad','Mr.','Sajjad','Raza',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1)
,('amakda','amakda','Mr.','Adnan','Makda',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2);

insert  into `vzdn_user_roles`(`role_id`,`user_id`) values (1,3),(3,4),(2,5),(4,5);
insert  into `vzdn_user_roles`(`role_id`,`user_id`) values (1,1),(2,1),(4,2);
