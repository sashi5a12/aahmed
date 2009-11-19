insert  into `vzdn_events`(`event_id`,`event_name`,`event_desc`,`created_date`,`created_by`,`last_updated_date`,`last_updated_by`) values (1,'Test Event1','Test Event1 Description',NULL,NULL,NULL,NULL),(2,'Test Event2','Test Event2 Description',NULL,NULL,NULL,NULL),(3,'Test Event3','Test Event3 Description',NULL,NULL,NULL,NULL);

insert  into `vzdn_event_handlers`(`event_handler_id`,`event_id`,`class_name`) values (1,1,'com.netpace.aims.bo.events.ApplicationEventHandler'),(2,2,'com.netpace.aims.bo.events.ApplicationEventHandler');

insert  into `vzdn_place_holders`(`place_holder_id`,`place_holder_display_name`,`created_date`,`created_by`,`last_updated_date`,`last_updated_by`) values (1,'Place Holder1',NULL,NULL,NULL,NULL),(2,'Place Holder2',NULL,NULL,NULL,NULL),(3,'Place Holder3',NULL,NULL,NULL,NULL),(4,'Place Holder4',NULL,NULL,NULL,NULL),(5,'Place Holder5',NULL,NULL,NULL,NULL),(6,'Place Holder6',NULL,NULL,NULL,NULL);

insert  into `vzdn_event_place_holders`(`place_holder_id`,`event_id`) values (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(1,2),(2,2),(3,2),(4,2),(5,2);

update `vzdn_sub_menus` set `sub_menu_url` = 'notifications.action' where sub_menu_id = 9;