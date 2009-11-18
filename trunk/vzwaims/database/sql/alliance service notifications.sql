----------------------------------------------------------------------------------------------------------------------------------------------
----------------------        Place holder for Alliance resubmit url for the notifications
----------------------------------------------------------------------------------------------------------------------------------------------

INSERT 
INTO 
    AIMS_PLACE_HOLDERS 
    (
        PLACE_HOLDER_ID, 
        PLACE_HOLDER_DISPLAY_NAME, 
        CREATED_DATE, 
        CREATED_BY, 
        LAST_UPDATED_DATE, 
        LAST_UPDATED_BY
    ) 
    VALUES 
    (
        2001, 
        'RESUBMIT_ALLIANCE_SERVICE_URL', 
        SYSDATE, 
        'system', 
        sysdate, 
        sysdate
    )
/	
----------------------------------------------------------------------------------------------------------------------------------------------
----------------------         NOTIFICATION EVENT FOR ALLIANCE REGESTRATION WEB SERVICE	
----------------------------------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------------------------------
-- Notification if the aims account is disabled 

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2100, 'ALLIANCE_SERVICE_ACCOUNT_DISABLED', 'ALLIANCE_SERVICE_ACCOUNT_DISABLED', SYSDATE,
             'system', SYSDATE, 'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2100, 2100, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2100
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2100
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2100
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2001, 2100
            )
/
----------------------------------------------------------------------------------------------------------------------------------------------

