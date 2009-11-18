------------------------------------------------
------------------------------------------------
------------------------------------------------
-- Deleted Menus not present on PROD
------------------------------------------------
------------------------------------------------
------------------------------------------------



Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (6, 'Manage White Papers', '/aims/WPAdminSetup.do', 'manage_whitepapers', 700, 'system', TO_DATE('10/17/2003 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'system', TO_DATE('10/17/2003 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (13, 'Submit White Paper', '/aims/whitePaperSetup.do ', 'submit_white_paper', 1400, 'system', TO_DATE('01/12/2004 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'system', TO_DATE('01/12/2004 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, SORT_ORDER, CREATED_BY)
 Values
   (17, 'Content Requests', '/aims/mktAdmContentRequest.do', 1800, 'system');
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, SORT_ORDER, CREATED_BY)
 Values
   (18, 'Content Requests', '/aims/mktAllianceContentRequest.do', 1800, 'system');
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, SORT_ORDER, CREATED_BY)
 Values
   (19, 'Marketing', '#', 1700, 'system');
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (20, 'Marketing Content', '#', 'mkt_content', 1500, 'system', TO_DATE('04/05/2005 18:21:28', 'MM/DD/YYYY HH24:MI:SS'), 'system', TO_DATE('04/05/2005 18:21:28', 'MM/DD/YYYY HH24:MI:SS'));
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, IMAGE_NAME, SORT_ORDER)
 Values
   (21, 'Marketing Material', 'mkt_material', 1500);


------------------------------------------------
------------------------------------------------
------------------------------------------------
-- Deleted Menus for new Phase 2
------------------------------------------------
------------------------------------------------
------------------------------------------------



Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (4, 'Message Center', '#', 'mesg_center', 500, 'system', TO_DATE('10/17/2003 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'system', TO_DATE('10/17/2003 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (8, 'Manage Content', '#', 'manage_content', 900, 'system', TO_DATE('10/17/2003 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'system', TO_DATE('10/17/2003 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into AIMS_MENUS
   (MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (16, 'Provisioned Apps', '/aims/applicationSearchProvisioned.do?task=search', 'provisioned_apps', 410, 'system', TO_DATE('09/03/2004 14:45:20', 'MM/DD/YYYY HH24:MI:SS'), 'system', TO_DATE('09/03/2004 14:45:20', 'MM/DD/YYYY HH24:MI:SS'));

