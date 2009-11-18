Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2101, 'JAVA_APP_DELETED', 'JAVA_APP_DELETED', sysdate, 'system', sysdate, 'system')
/

Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2101)
/

Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2101)
/

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   
	(2101,2101, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
