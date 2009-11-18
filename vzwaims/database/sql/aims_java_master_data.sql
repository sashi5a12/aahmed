------------------------------------------------------------------------
-- add Java platform
------------------------------------------------------------------------
INSERT INTO AIMS_PLATFORMS
   (PLATFORM_ID, PLATFORM_NAME, PLATFORM_DESC, CREATED_BY, CREATED_DATE, 
    LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (44, 'Java', 'Java', 'system', SYSDATE, 
    'system', SYSDATE);

------------------------------------------------------------------------
--Add TYPE DEF for JAVA APPS
------------------------------------------------------------------------
INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID, TYPE_NAME, TYPE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
VALUES (9, 'JAVA_CONTENT_RATING', 'Content Rating for Java Apps', 'system', SYSDATE, 'system', SYSDATE );

INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID, TYPE_NAME, TYPE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
VALUES (10, 'JAVA_CONTENT_TYPE', 'Content Type for Java Apps', 'system', SYSDATE, 'system', SYSDATE );

Insert into AIMS_TYPE_DEFS
   (TYPE_DEF_ID, TYPE_NAME, TYPE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (20, 'CONTRACT_RING_NUMBER', 'Contract Ring Number', 'system', sysdate, 'system', sysdate);

------------------------------------------------------------------------
--Add Java Ring 2 & 3
------------------------------------------------------------------------
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (172, 20, 'Ring-2', 'Ring-2', 20, 'system', sysdate, 'system', sysdate);
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (173, 20, 'Ring-3', 'Ring-3', 30, 'system', sysdate, 'system', sysdate);

------------------------------------------------------------------------
--Add Content Rating for JAVA APPS
------------------------------------------------------------------------
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (201, 9, 'Verizon - C7+ (Children)', 'Verizon - C7+ (Children)', 1, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (202, 9, 'Verizon - T13+ (Teen)', 'Verizon - T13+ (Teen)', 2, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (203, 9, 'Verizon - YA17+ (Young Adult)', 'Verizon - YA17+ (Young Adult)', 3, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (204, 9, 'ESRB - EC (Early Childhood)', 'ESRB - EC (Early Childhood)', 4, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (205, 9, 'ESRB - E (Everyone)', 'ESRB - E (Everyone)', 5, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (206, 9, 'ESRB - E10+ (Everyone 10+)', 'ESRB - E10+ (Everyone 10+)', 6, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (207, 9, 'ESRB - T (Teen 13+)', 'ESRB - T (Teen 13+)', 7, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (208, 9, 'ESRB - M (Mature 17+)', 'ESRB - M (Mature 17+)', 8, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (209, 9, 'MPAA - G', 'MPAA - G', 9, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (210, 9, 'MPAA - PG', 'MPAA - PG', 10, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (211, 9, 'MPAA - PG13', 'MPAA - PG13', 11, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (212, 9, 'MPAA - R', 'MPAA - R', 12, 'system', SYSDATE, 'system', SYSDATE);
   
------------------------------------------------------------------------
--Add Content Type for JAVA APPS  
------------------------------------------------------------------------
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (301, 10, 'APPLICATION', 'APPLICATION', 1, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (302, 10, 'GAME', 'GAME', 2, 'system', SYSDATE, 'system', SYSDATE);
    
------------------------------------------------------------------------
-- Add Java Categories
------------------------------------------------------------------------
INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (161, 'Games', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (162, 'Entertainment', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (163, 'News', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (164, 'Sports', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (165, 'Money / Trading', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (166, 'Directories', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (167, 'Shopping', '', 44 , 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (168, 'Travel', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (169, 'Other', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (170, 'Weather', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (171, 'local Info', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

------------------------------------------------------------------------
-- Add Java Sub-Categories
------------------------------------------------------------------------
INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (271,  'Music',  'Music', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (272,  'TV',  'TV', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (273,  'Travel',  'Travel', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (274,  'Fun',  'Fun', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (275,  'Games',  'Games', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (276,  'Astrology',  'Astrology', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (277,  'Movies',  'Movies', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (278,  'Health',  'Health', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (279,  'Fashion',  'Fashion', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (280,  'Gossip',  'Gossip', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (281,  'For Him',  'For Him', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (282,  'Home and Family',  'Home and Family', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (283,  'Teens and Tweens',  'Teens and Tweens', 161, 'system', SYSDATE, SYSDATE, SYSDATE);
   
--ADD MORE SUB-CAT ?????   

------------------------------------------------------------------------
-- ADD JAVA WORKFLOWS
------------------------------------------------------------------------
INSERT INTO AIMS_WORKFLOW_DETAILS (WORKFLOW_DETAIL_ID, WORKFLOW_TYPE, MODULE_TYPE, MODULE_TYPE_ID, WORKFLOW_NAME, WORKFLOW_XML_NAME, WORKFLOW_DISPLAY_NAME, DESCRIPTION, CREATED_BY, CREATED_DATE) VALUES (8, 40, 41, 8, 'workflow_jondapp', 'workflow_java_ondeck_app.xml', 'Java Ondeck Workflow', 'Java ondeck workflow', 'system', SYSDATE);
INSERT INTO AIMS_WORKFLOW_DETAILS (WORKFLOW_DETAIL_ID, WORKFLOW_TYPE, MODULE_TYPE, MODULE_TYPE_ID, WORKFLOW_NAME, WORKFLOW_XML_NAME, WORKFLOW_DISPLAY_NAME, DESCRIPTION, CREATED_BY, CREATED_DATE) VALUES (10, 40, 41, 10, 'workflow_jofdapp', 'workflow_java_offdeck_app.xml', 'Java Offdeck Workflow', 'Java offdeck workflow', 'system', SYSDATE);


------------------------------------------------------------------------
-- ADD JAVA SYS PRIVILEGES
------------------------------------------------------------------------
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id, sub_menu_id, privilege_key, available_to  )
     VALUES (801, 'Java App Programming Content Manager', 'Java App Programming Content Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_PROGRAMMING_CONTENT_MANAGER', 'V' )
;         
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id, sub_menu_id, privilege_key, available_to     )
     VALUES (803, 'Java App Legal Manager', 'Java App Legal Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_LEGAL_MANAGER', 'V'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id, sub_menu_id, privilege_key, available_to  )
     VALUES (804, 'Java App Content Standard Manager', 'Java App Content Standard Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_CONTENT_STANDARD_MANAGER', 'V'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (805, 'Java App Tax Manager', 'Java App Tax Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_TAX_MANAGER', 'V'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (2001, 'Manage App Javaondeck', 'Manage App Javaondeck', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'MANAGE_APP_JAVAONDECK', 'B'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (2002, 'Manage App Javaoffdeck', 'Manage App Javaoffdeck', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'MANAGE_APP_JAVAFFDECK', 'B'  )
;            
            

------------------------------------------------------------------------
--Add Java lifecycle phases
------------------------------------------------------------------------
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2001, 'SUBMITTED', 'Submission Phase for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2002, 'RFI-CONTENT PROG', 'RFI-CONTENT PROG  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2003, 'CONTENT APPROVED', 'CONTENT APPROVED  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2004, 'RFI-LEGAL/CONTENT', 'RFI-LEGAL/CONTENT  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2005, 'LEGAL APPROVED', 'LEGAL APPROVED  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2006, 'RFI-TAX REVIEW', 'RFI-TAX REVIEW  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2007, 'PENDING TAX APPROVAL', 'PENDING TAX APPROVAL  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2008, 'REJECTED', 'Rejected  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2009, 'APPROVED', 'APPROVED  java application', 'system', SYSDATE, 'system', SYSDATE);
   
 
------------------------------------------------------------------------
-- EVENT GENERATION
------------------------------------------------------------------------
INSERT INTO aims_events
	( event_id, event_name, event_desc, created_date, created_by, last_updated_date, last_updated_by )
	VALUES (2002, 'JAVA_APP_STATUS_CHANGE', 'JAVA_APP_STATUS_CHANGE', SYSDATE, 'system', SYSDATE, 'system'  )
;
INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name )
     VALUES (2002, 2002, 'com.netpace.aims.bo.events.ApplicationEventHandler'  )
;
INSERT INTO aims_event_place_holders (place_holder_id, event_id )
     VALUES (1, 2002 )
;
INSERT INTO aims_event_place_holders (place_holder_id, event_id )
     VALUES (2, 2002 )
;
INSERT INTO aims_event_place_holders (place_holder_id, event_id )
     VALUES (6, 2002 )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2003, 'JAVA_APP_APPROVAL', 'JAVA_APP_APPROVAL', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2003, 2003, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2003
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2003
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2003
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2004, 'JAVA_APP_PROGRAMMING_CONTENT_APPROVAL', 'JAVA_APP_PROGRAMMING_CONTENT_APPROVAL', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2004, 2004, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2004
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2004
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2004
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2005, 'JAVA_APP_PROGRAMMING_CONTENT_RFI', 'JAVA_APP_PROGRAMMING_CONTENT_RFI', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2005, 2005, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2005
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2005
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2005
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2006, 'JAVA_APP_PROGRAMMING_CONTENT_DENY', 'JAVA_APP_PROGRAMMING_CONTENT_DENY', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2006, 2006, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2006
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2006
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2006
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2007, 'JAVA_APP_LEGAL_CONTENT_APPROVAL', 'JAVA_APP_LEGAL_CONTENT_APPROVAL', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2007, 2007, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2007
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2007
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2007
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2008, 'JAVA_APP_LEGAL_CONTENT_DENY', 'JAVA_APP_LEGAL_CONTENT_DENY', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2008, 2008, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2008
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2008
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2008
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2009, 'JAVA_APP_LEGAL_CONTENT_RFI', 'JAVA_APP_LEGAL_CONTENT_RFI', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2009, 2009, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2009
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2009
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2009
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2010, 'JAVA_APP_TAX_APPROVAL_PENDING', 'JAVA_APP_TAX_APPROVAL_PENDING', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2010, 2010, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2010
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2010
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2010
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2011, 'JAVA_APP_TAX_APPROVAL_RFI', 'JAVA_APP_TAX_APPROVAL_RFI', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2011, 2011, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2011
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2011
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2011
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2012, 'JAVA_APP_PROGRAMMING_CONTENT_RESUBMIT', 'JAVA_APP_PROGRAMMING_CONTENT_RESUBMIT', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2012, 2012, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2012
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2012
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2012
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2013, 'JAVA_APP_LEGAL_CONTENT_RESUBMIT', 'JAVA_APP_LEGAL_CONTENT_RESUBMIT', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2013, 2013, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2013
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2013
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2013
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2014, 'JAVA_APP_TAX_RESUBMIT', 'JAVA_APP_TAX_RESUBMIT', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2014, 2014, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2014
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2014
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2014
            )
;


