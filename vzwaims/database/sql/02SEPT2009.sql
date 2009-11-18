delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID=2008
/
delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID=2008
/
delete from AIMS_EMAIL_MESSAGES e where e.EMAIL_MESSAGE_ID in (select MESSAGE_ID from  AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2008 )
/ 
delete from AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2008
/
delete from AIMS_EVENTS ae where ae.EVENT_ID=2008 
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID=2006
/
delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID=2006
/
delete from AIMS_EMAIL_MESSAGES e where e.EMAIL_MESSAGE_ID in (select MESSAGE_ID from  AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2006 )
/
delete from AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2006
/
delete from AIMS_EVENTS ae where ae.EVENT_ID=2006 
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID=2017
/
delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID=2017
/
delete from AIMS_EMAIL_MESSAGES e where e.EMAIL_MESSAGE_ID in (select MESSAGE_ID from  AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2017 )
/
delete from AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2017
/
delete from AIMS_EVENTS ae where ae.EVENT_ID=2017
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID=2018
/
delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID=2018
/
delete from AIMS_EMAIL_MESSAGES e where e.EMAIL_MESSAGE_ID in (select MESSAGE_ID from  AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2018 )
/
delete from AIMS_EVENT_NOTIFICATIONS where EVENT_ID = 2018
/
delete from AIMS_EVENTS ae where ae.EVENT_ID=2018
/ 
update aims_events
set event_name = 'VCAST_APPS_SUBMITTED'
, event_desc = 'VCAST_APPS_SUBMITTED'
where event_id = 2001
/
update aims_events
set event_name = 'VCAST_APPS_LEGAL_APPROVED'
, event_desc = 'VCAST_APPS_LEGAL_APPROVED'
where event_id = 2007
/
update aims_events
set event_name = 'VCAST_APPS_LEGAL_RFI'
, event_desc = 'VCAST_APPS_LEGAL_RFI'
where event_id = 2009
/
update aims_events
set event_name = 'VCAST_APPS_LEGAL_RESUBMIT'
, event_desc = 'VCAST_APPS_LEGAL_RESUBMIT'
where event_id = 2013
/