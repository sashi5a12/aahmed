USE vzdn;

CREATE TABLE `vzdn_newsletter_email_log` (
	`newsletter_email_log_id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`email_date_time` DATETIME NOT NULL,
	`email_subject` VARCHAR(100),
	`email_attachment` LONGBLOB,
	`email_content` TEXT,
	`created_by` VARCHAR(50) NOT NULL,
	`created_date` DATETIME NOT NULL,
	PRIMARY KEY (`newsletter_email_log_id`),
	KEY `nle_user_id` (`user_id`),
	CONSTRAINT `nle_userid_fk` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `vzdn_newsletter_reciever_log` (
	`newsletter_reciever_log_id` INT NOT NULL AUTO_INCREMENT,
	`newsletter_email_log_id` INT NOT NULL,
	`email_address` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`newsletter_reciever_log_id`),
	KEY `nlr_email_log_id` (`newsletter_email_log_id`),
	CONSTRAINT `nlr_emaillog_fk` FOREIGN KEY (`newsletter_email_log_id`) REFERENCES `vzdn_newsletter_email_log` (`newsletter_email_log_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `vzdn_newsletter_optout_users` (
	`opt_out_id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`last_updated_by` VARCHAR(1000),
	`last_updated_date` DATETIME,
	PRIMARY KEY (`opt_out_id`),
	CONSTRAINT `nou_userid_fk` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

USE vzdn_audit;

CREATE TABLE `vzdn_newsletter_email_log$aud` (
  `newsletter_email_log_id` INT(11) NOT NULL DEFAULT '0',
  `user_id` INT(11) NOT NULL,
  `email_date_time` DATETIME NOT NULL,
  `email_subject` VARCHAR(100) DEFAULT NULL,
  `email_content` TEXT,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `AUD_ACTION` VARCHAR(3) NOT NULL DEFAULT '',
  `AUD_TIMESTAMP` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
  `AUD_USER` VARCHAR(77) NOT NULL DEFAULT ''
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `vzdn_newsletter_reciever_log$aud` (
  `newsletter_reciever_log_id` INT(11) NOT NULL DEFAULT '0',
  `newsletter_email_log_id` INT(11) NOT NULL,
  `email_address` VARCHAR(50) NOT NULL,
  `AUD_ACTION` VARCHAR(3) NOT NULL DEFAULT '',
  `AUD_TIMESTAMP` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
  `AUD_USER` VARCHAR(77) NOT NULL DEFAULT ''
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `vzdn_newsletter_optout_users$aud` (
  `opt_out_id` INT(11) NOT NULL DEFAULT '0',
  `user_id` INT(11) NOT NULL,
  `last_updated_by` VARCHAR(1000) DEFAULT NULL,
  `last_updated_date` DATETIME DEFAULT NULL,
  `AUD_ACTION` VARCHAR(3) NOT NULL DEFAULT '',
  `AUD_TIMESTAMP` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
  `AUD_USER` VARCHAR(77) NOT NULL DEFAULT ''
) ENGINE=INNODB DEFAULT CHARSET=utf8;

USE vzdn;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_email_log$audINS AFTER INSERT ON vzdn_newsletter_email_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_email_log$aud (newsletter_email_log_id,user_id,email_date_time,email_subject,email_content,created_by,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.newsletter_email_log_id,NEW.user_id,NEW.email_date_time,NEW.email_subject,NEW.email_content,NEW.created_by,NEW.created_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_email_log$audUPD AFTER UPDATE ON vzdn_newsletter_email_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_email_log$aud (newsletter_email_log_id,user_id,email_date_time,email_subject,email_content,created_by,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.newsletter_email_log_id,NEW.user_id,NEW.email_date_time,NEW.email_subject,NEW.email_content,NEW.created_by,NEW.created_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_email_log$audDEL AFTER DELETE ON vzdn_newsletter_email_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_email_log$aud (newsletter_email_log_id,user_id,email_date_time,email_subject,email_content,created_by,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.newsletter_email_log_id,OLD.user_id,OLD.email_date_time,OLD.email_subject,OLD.email_content,OLD.created_by,OLD.created_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_reciever_log$audINS AFTER INSERT ON vzdn_newsletter_reciever_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_reciever_log$aud (newsletter_reciever_log_id,newsletter_email_log_id,email_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.newsletter_reciever_log_id,NEW.newsletter_email_log_id,NEW.email_address,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_reciever_log$audUPD AFTER UPDATE ON vzdn_newsletter_reciever_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_reciever_log$aud (newsletter_reciever_log_id,newsletter_email_log_id,email_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.newsletter_reciever_log_id,NEW.newsletter_email_log_id,NEW.email_address,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_reciever_log$audDEL AFTER DELETE ON vzdn_newsletter_reciever_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_reciever_log$aud (newsletter_reciever_log_id,newsletter_email_log_id,email_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.newsletter_reciever_log_id,OLD.newsletter_email_log_id,OLD.email_address,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_optout_users$audINS AFTER INSERT ON vzdn_newsletter_optout_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_optout_users$aud (opt_out_id,user_id,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.opt_out_id,NEW.user_id,NEW.last_updated_by,NEW.last_updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;


DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_optout_users$audUPD AFTER UPDATE ON vzdn_newsletter_optout_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_optout_users$aud (opt_out_id,user_id,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.opt_out_id,NEW.user_id,NEW.last_updated_by,NEW.last_updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;


DELIMITER $$ 
CREATE TRIGGER vzdn_newsletter_optout_users$audDEL AFTER DELETE ON vzdn_newsletter_optout_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletter_optout_users$aud (opt_out_id,user_id,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.opt_out_id,OLD.user_id,OLD.last_updated_by,OLD.last_updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;

INSERT INTO vzdn_sub_menus(sub_menu_id, sub_menu_name, sub_menu_url, sort_order,menu_id)
		VALUES(20,'Newsletters','newsLetter.action',6,5);

INSERT INTO vzdn_sys_privileges (privilege_id, privilege_name, privilege_description, created_by, sub_menu_id, privilege_key)
			VALUES(20,'manager_newsletters','Manage Newsletters','system',20,'MANAGER_NEWSLETTER');


INSERT INTO vzdn_role_privileges(role_id, privilege_id) VALUES(5,20);

