CREATE TABLE vzdn_email_exception_log
(
  `email_exception_id`  INT(11) NOT NULL AUTO_INCREMENT,
  `email_to`            LONGBLOB,
  `email_from`          VARCHAR(200) DEFAULT NULL,
  `email_subject`       VARCHAR(200) DEFAULT NULL,
  `email_cc`            LONGBLOB,
  `email_bcc`           LONGBLOB,
  `email_body`          LONGBLOB,
  `email_attachments`   INT,
  `exception_message`   LONGBLOB,
  `exception_trace`     LONGBLOB,
  `created_date`        DATETIME DEFAULT NULL,
   PRIMARY KEY  (`email_exception_id`)
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE vzdn_email_exception_log$aud
(
  `email_exception_id`  INT(11),
  `email_from`          VARCHAR(200) DEFAULT NULL,
  `email_subject`       VARCHAR(200) DEFAULT NULL,
  `email_attachments`   INT,
  `created_date`        DATETIME DEFAULT NULL,
  `AUD_ACTION`          VARCHAR(3) DEFAULT NULL,
  `AUD_TIMESTAMP`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `AUD_USER`            VARCHAR(30) DEFAULT NULL
)
ENGINE=INNODB DEFAULT CHARSET=utf8;
  
UPDATE vzdn_place_holders SET place_holder_display_name = 'PLACE_HOLDER_TEST_NAME_1' WHERE place_holder_id=1;
UPDATE vzdn_place_holders SET place_holder_display_name = 'PLACE_HOLDER_TEST_NAME_2' WHERE place_holder_id=2;
UPDATE vzdn_place_holders SET place_holder_display_name = 'PLACE_HOLDER_TEST_NAME_3' WHERE place_holder_id=3;
UPDATE vzdn_place_holders SET place_holder_display_name = 'PLACE_HOLDER_TEST_NAME_4' WHERE place_holder_id=4;
UPDATE vzdn_place_holders SET place_holder_display_name = 'PLACE_HOLDER_TEST_NAME_5' WHERE place_holder_id=5;
UPDATE vzdn_place_holders SET place_holder_display_name = 'PLACE_HOLDER_TEST_NAME_6' WHERE place_holder_id=6;

UPDATE vzdn_event_handlers SET class_name = 'com.netpace.vzdn.service.impl.InMemoryEventHandler' WHERE event_id=1;
UPDATE vzdn_event_handlers SET class_name = 'com.netpace.vzdn.service.impl.InMemoryEventHandler' WHERE event_id=2;

INSERT INTO vzdn_event_handlers(event_id,class_name) VALUES(3,'com.netpace.vzdn.service.impl.InMemoryEventHandler');

INSERT INTO vzdn_event_place_holders (place_holder_id,event_id) VALUES(1,3);
INSERT INTO vzdn_event_place_holders (place_holder_id,event_id) VALUES(2,3);
INSERT INTO vzdn_event_place_holders (place_holder_id,event_id) VALUES(3,3);
INSERT INTO vzdn_event_place_holders (place_holder_id,event_id) VALUES(4,3);
INSERT INTO vzdn_event_place_holders (place_holder_id,event_id) VALUES(5,3);
INSERT INTO vzdn_event_place_holders (place_holder_id,event_id) VALUES(6,3);