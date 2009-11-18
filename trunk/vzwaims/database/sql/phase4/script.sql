------------------------------------------------------------------------------------------------------------------
-- script for refactoring settlement and alliance service notification display names
------------------------------------------------------------------------------------------------------------------
update AIMS_PLACE_HOLDERS aeph
set aeph.PLACE_HOLDER_DISPLAY_NAME='API_RESUBMIT_URL'
where aeph.PLACE_HOLDER_ID=2001;

update AIMS_PLACE_HOLDERS aeph
set aeph.PLACE_HOLDER_DISPLAY_NAME='API_ERROR_MESSAGE'
where aeph.PLACE_HOLDER_ID=2002;

delete from AIMS_EVENT_PLACE_HOLDERS aeph
where aeph.PLACE_HOLDER_ID=2 and aeph.EVENT_ID=2100; 

update AIMS_EVENTS ae
set ae.EVENT_NAME='VCAST_APPS_API_ERR_SETTLEMENT'
where ae.EVENT_ID=2102;

update AIMS_EVENTS ae
set ae.EVENT_NAME='VCAST_APPS_API_ERR_CONTENTDELIVERY'
where ae.EVENT_ID=2100;
