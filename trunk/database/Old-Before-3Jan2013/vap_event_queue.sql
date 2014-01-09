create table vap_event_queue(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`event_id` INT(11) NOT NULL DEFAULT 1,
	`priority` INT(11) NOT NULL DEFAULT 1,
	`properties_map` BLOB DEFAULT NULL,
	`created_date` DATETIME DEFAULT NULL,
	`created_by` VARCHAR(50) DEFAULT NULL,
	`updated_date` DATETIME DEFAULT NULL,
	`updated_by` VARCHAR(50) DEFAULT NULL,
	`is_active` VARCHAR(1) DEFAULT 'Y',
	PRIMARY KEY  (`id`),
	CONSTRAINT `vap_events_id_fk_1` FOREIGN KEY (`event_id`) REFERENCES `vap_events` (`event_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into vap_events(event_name,event_desc,created_date,created_by,updated_date,updated_by,is_active)
values('ACCOUNT_REGISTRATION_EVENT','ACCOUNT_REGISTRATION_EVENT',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_notifications(event_id,notification_title,notification_description,media,status,created_date,created_by,updated_date,updated_by,is_active)
values(1,'ACCOUNT_REGISTRATION_EVENT','ACCOUNT_REGISTRATION_EVENT','TEST','ACTIVE',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_place_holders(place_holder_display_name,created_date,created_by,updated_date,updated_by,is_active)
values('VAR_1',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_event_place_holders(place_holder_id,event_id)values(1,1);
insert into vap_email_messages(notification_id,email_title,email_text,email_desc,email_category,from_address)
values(1,'test Message','test message text','test message','A','');