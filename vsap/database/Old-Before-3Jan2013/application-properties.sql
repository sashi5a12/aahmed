insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('APPLICATION_PROPERTY','vap.email.host','smtp.gmail.com',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('APPLICATION_PROPERTY','vap.email.username','vdc.netpace@gmail.com',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('APPLICATION_PROPERTY','vap.email.password','netpace123',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('APPLICATION_PROPERTY','vap.notification.email.from','vdc.netpace@gmail.com',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('APPLICATION_PROPERTY','vap.notification.email.to','vdc.netpace@gmail.com',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('APPLICATION_PROPERTY','password.reset.link','resetPassword.do',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('ACCESS_CONTROL_METADATA','/forgotUsername.do','IS_AUTHENTICATED_ANONYMOUSLY',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('ACCESS_CONTROL_METADATA','/forgotPassword.do','IS_AUTHENTICATED_ANONYMOUSLY',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('ACCESS_CONTROL_METADATA','/accountVerificationConfirmation.do','IS_AUTHENTICATED_ANONYMOUSLY',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('ACCESS_CONTROL_METADATA','/resetPassword.do','IS_AUTHENTICATED_ANONYMOUSLY',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('ACCESS_CONTROL_METADATA','/submitPasswordReset.do','IS_AUTHENTICATED_ANONYMOUSLY',1,now(),'SYSTEM',now(),'SYSTEM',1);
insert into vap_application_properties (`type`,`name`,`value`,sort_order,created_date,created_by,updated_date,updated_by,is_active)
values('ACCESS_CONTROL_METADATA','/secure/companyInformation.do','IS_AUTHENTICATED_ANONYMOUSLY',1,now(),'SYSTEM',now(),'SYSTEM',1)