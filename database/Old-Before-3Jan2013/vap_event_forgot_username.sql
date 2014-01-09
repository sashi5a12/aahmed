insert into vap_events(event_id,event_name,event_desc,created_date,created_by,updated_date,updated_by,is_active)
values(10,'FORGOT_USERNAME_EVENT','FORGOT_USERNAME_EVENT',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_notifications(notification_id,event_id,notification_title,notification_description,media,status,created_date,created_by,updated_date,updated_by,is_active)
values(10,10,'FORGOT_USERNAME_EVENT','FORGOT_USERNAME_EVENT','FORGOT_USERNAME_EVENT','ACTIVE',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_place_holders(place_holder_id,place_holder_display_name,created_date,created_by,updated_date,updated_by,is_active)
values(10,'USERNAME',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_place_holders(place_holder_id,place_holder_display_name,created_date,created_by,updated_date,updated_by,is_active)
values(11,'TO_ADDRESS',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_place_holders(place_holder_id,place_holder_display_name,created_date,created_by,updated_date,updated_by,is_active)
values(12,'PASSWORD_LINK',now(),'SYSTEM',now(),'SYSTEM','1');
insert into vap_event_place_holders(place_holder_id,event_id)values(10,10);
insert into vap_event_place_holders(place_holder_id,event_id)values(11,10);
insert into vap_event_place_holders(place_holder_id,event_id)values(12,10);
insert into vap_email_messages(notification_id,email_title,email_text,email_desc,email_category,from_address)
values(10,'Password Reset','Password for username ${USERNAME} reset. <br/>New Password is ${PASSWORD_LINK}.','Password Reset','USER','TO_ADDRESS');