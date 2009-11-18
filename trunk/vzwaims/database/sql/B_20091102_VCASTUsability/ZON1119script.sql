update AIMS_PLACE_HOLDERS aeph
set aeph.PLACE_HOLDER_DISPLAY_NAME='API_RESUBMIT_URL'
where aeph.PLACE_HOLDER_ID=2001
/
update AIMS_PLACE_HOLDERS aeph
set aeph.PLACE_HOLDER_DISPLAY_NAME='API_ERROR_MESSAGE'
where aeph.PLACE_HOLDER_ID=2002
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph
where aeph.PLACE_HOLDER_ID=2 and aeph.EVENT_ID=2100 
/
update AIMS_EVENTS ae
set ae.EVENT_NAME='VCAST_APPS_API_ERR_SETTLEMENT'
where ae.EVENT_ID=2102
/
update AIMS_EVENTS ae
set ae.EVENT_NAME='VCAST_APPS_API_ERR_CONTENTDELIVERY'
where ae.EVENT_ID=2100
/
DELETE FROM aims_event_place_holders
      WHERE event_id IN
               (54,
                55,
                56,
                57,
                58,
                59,
                60,
                61,
                62,
                63,
                64,
                65,
                66,
                67,
                68,
                69,
                70,
                71,
                72,
                73,
                110,
                111,
                125
               )
/
DELETE FROM aims_event_handlers
      WHERE event_id IN
               (54,
                55,
                56,
                57,
                58,
                59,
                60,
                61,
                62,
                63,
                64,
                65,
                66,
                67,
                68,
                69,
                70,
                71,
                72,
                73,
                110,
                111,
                125
               )
/
DELETE FROM aims_events
      WHERE event_id IN
               (54,
                55,
                56,
                57,
                58,
                59,
                60,
                61,
                62,
                63,
                64,
                65,
                66,
                67,
                68,
                69,
                70,
                71,
                72,
                73,
                110,
                111,
                125
               )
/