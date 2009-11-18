Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2015, 'JAVA_APP_CONTENT_STANDARD_RFI', 'JAVA_APP_CONTENT_STANDARD_RFI', sysdate, 'system', sysdate, 'system');

Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2015);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2015);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2015);


Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2016, 'JAVA_APP_CONTENT_STANDARD_APPROVAL', 'JAVA_APP_CONTENT_STANDARD_APPROVAL',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2016);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2016);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2016);
   
   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2001, 'JAVA_APP_WORKFLOW_STARTED', 'JAVA_APP_WORKFLOW_STARTED',  sysdate, 'system', sysdate, 'system');

   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2001);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2001);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2001);
   
   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2017, 'JAVA_APP_LEGAL_CONTENT_DENIED', 'JAVA_APP_LEGAL_CONTENT_DENIED',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2017);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2017);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2017);
   
   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2018, 'JAVA_APP_CONTENT_STANDARD_DENIED', 'JAVA_APP_CONTENT_STANDARD_DENIED',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2018);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2018);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2018);
   
   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2019, 'JAVA_APP_REJECTED', 'JAVA_APP_REJECTED', sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2019);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2019);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2019);
   
   
   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2020, 'JAVA_APP_CONTENT_STANDARD_RESUBMIT', 'JAVA_APP_CONTENT_STANDARD_RESUBMIT',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2020);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2020);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2020);   
   
   
   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2021, 'JAVA_APP_TAX_REVIEW_RESUBMIT', 'JAVA_APP_TAX_REVIEW_RESUBMIT',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2021);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2021);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2021);
   
   
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2015,2015, 'com.netpace.aims.bo.events.ApplicationEventHandler');

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2016, 2016, 'com.netpace.aims.bo.events.ApplicationEventHandler');

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2001, 2001, 'com.netpace.aims.bo.events.ApplicationEventHandler');

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2017, 2017, 'com.netpace.aims.bo.events.ApplicationEventHandler');    

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2018, 2018, 'com.netpace.aims.bo.events.ApplicationEventHandler');

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2019, 2019, 'com.netpace.aims.bo.events.ApplicationEventHandler');

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2020, 2020, 'com.netpace.aims.bo.events.ApplicationEventHandler');

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2021, 2021, 'com.netpace.aims.bo.events.ApplicationEventHandler');
 
 
 
 
 
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2022, 'JAVA_OFFDECK_WORKFLOW_STARTED', 'JAVA_OFFDECK_WORKFLOW_STARTED',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2022);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2022);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2022);

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2022,2022, 'com.netpace.aims.bo.events.ApplicationEventHandler');
 
 
 Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2023, 'JAVA_OFFDECK_TAX_APPROVAL', 'JAVA_OFFDECK_TAX_APPROVAL',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2023);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2023);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2023);

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2023,2023, 'com.netpace.aims.bo.events.ApplicationEventHandler');

 
 
 
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2024, 'JAVA_OFFDECK_TAX_RFI', 'JAVA_OFFDECK_TAX_RFI',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2024);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2024);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2024);

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2024,2024, 'com.netpace.aims.bo.events.ApplicationEventHandler');

 
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2025, 'JAVA_OFFDECK_TAX_REJECTED', 'JAVA_OFFDECK_TAX_REJECTED',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2025);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2025);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2025);

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2025,2025, 'com.netpace.aims.bo.events.ApplicationEventHandler');


Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2026, 'JAVA_OFFDECK_TAXRFI_RESUBMITTED', 'JAVA_OFFDECK_TAXRFI_RESUBMITTED',  sysdate, 'system', sysdate, 'system');

 
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2026);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2026);
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2026);

Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2026,2026, 'com.netpace.aims.bo.events.ApplicationEventHandler');   