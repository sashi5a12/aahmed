/* Formatted on 2009/08/24 13:18 (Formatter Plus v4.8.7) */
SET escape !

--------------------------------------------------------------------------------------------------------
-- Script to add Master date related to java platform. Run this script when java platform is on
--------------------------------------------------------------------------------------------------------

INSERT INTO aims_platforms
            (platform_id, platform_name, platform_desc, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (44, 'V CAST Apps', 'V CAST Applications', 'system',
             SYSDATE, 'system', SYSDATE
            )
/
UPDATE aims_platforms ap
   SET ap.platform_name = 'V CAST Video',
       ap.platform_desc = 'V CAST Video'
 WHERE ap.platform_id = 6
/
UPDATE aims_sub_menus asm
   SET asm.sub_menu_name = 'All Approved V CAST Apps'
 WHERE asm.sub_menu_id = 2000
/

UPDATE AIMS_TYPES
	SET TYPE_VALUE = 'VERIZON - C7+ (CHILDREN)'
	, DESCRIPTION = 'C7+'
WHERE TYPE_ID = 2000
/
UPDATE AIMS_TYPES
	SET TYPE_VALUE = 'VERIZON - T13+ (TEEN)'
	, DESCRIPTION = 'T13+'
WHERE TYPE_ID = 2001
/
UPDATE AIMS_TYPES
	SET TYPE_VALUE = 'VERIZON - YA17+ (YOUNG ADULT)'
	, DESCRIPTION = 'YA17+'
WHERE TYPE_ID = 2002
/

UPDATE AIMS_TYPES
	SET TYPE_VALUE = 'VERIZON - 18+'
	, DESCRIPTION = 'M18+'
WHERE TYPE_ID = 2003
/
------------------------------------------------------------------------------------------------
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_VIDEO_STATUS_SUBMITTED'
   , EVENT_DESC ='VCAST_VIDEO_STATUS_SUBMITTED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 34
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_VIDEO_STATUS_ACCEPTED'
   , EVENT_DESC ='VCAST_VIDEO_STATUS_ACCEPTED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 35
/   
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_VIDEO_STATUS_NOT_ACCEPTED'
   , EVENT_DESC ='VCAST_VIDEO_STATUS_NOT_ACCEPTED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 36
/
------------------------------------------------------------------------------------------------
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_WORKFLOW_STARTED'
   , EVENT_DESC ='VCAST_APPS_WORKFLOW_STARTED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2001
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_REJECTED'
   , EVENT_DESC ='VCAST_APPS_REJECTED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2019
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_APPROVED'
   , EVENT_DESC ='VCAST_APPS_APPROVED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2003
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_DELETED'
   , EVENT_DESC ='VCAST_APPS_DELETED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2101
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_CONTENT_STANDARD_APPROVED'
   , EVENT_DESC ='VCAST_APPS_CONTENT_STANDARD_APPROVAL'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2016
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_CONTENT_STANDARD_RESUBMIT'
   , EVENT_DESC ='VCAST_APPS_CONTENT_STANDARD_RESUBMIT'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2020
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_CONTENT_STANDARD_RFI'
   , EVENT_DESC ='VCAST_APPS_CONTENT_STANDARD_RFI'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2015
/

UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_CONTENT_APPROVED'
   , EVENT_DESC ='VCAST_APPS_CONTENT_APPROVED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2007
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_LEGAL_CONTENT_DENIED'
   , EVENT_DESC ='VCAST_APPS_LEGAL_CONTENT_DENIED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2017
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_CONTENT_STANDARD_DENIED'
   , EVENT_DESC ='VCAST_APPS_CONTENT_STANDARD_DENIED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2018
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_LEGAL_CONTENT_DENY'
   , EVENT_DESC ='VCAST_APPS_LEGAL_CONTENT_DENY'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2008
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_LEGAL_CONTENT_RESUBMIT'
   , EVENT_DESC ='VCAST_APPS_LEGAL_CONTENT_RESUBMIT'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2013
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_LEGAL_CONTENT_RFI'
   , EVENT_DESC ='VCAST_APPS_LEGAL_CONTENT_RFI'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2009
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_PROGRAMMING_CONTENT_APPROVED'
   , EVENT_DESC ='VCAST_APPS_PROGRAMMING_CONTENT_APPROVED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2004
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_PROGRAMMING_CONTENT_DENIED'
   , EVENT_DESC ='VCAST_APPS_PROGRAMMING_CONTENT_DENIED'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2006
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_PROGRAMMING_CONTENT_RESUBMIT'
   , EVENT_DESC ='VCAST_APPS_PROGRAMMING_CONTENT_RESUBMIT'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2012
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_PROGRAMMING_CONTENT_RFI'
   , EVENT_DESC ='VCAST_APPS_PROGRAMMING_CONTENT_RFI'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2005
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_TAX_REVIEW_PENDING'
   , EVENT_DESC ='VCAST_APPS_TAX_REVIEW_PENDING'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2010
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_TAX_REVIEW_RFI'
   , EVENT_DESC ='VCAST_APPS_TAX_REVIEW_RFI'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2011
/
UPDATE AIMS_EVENTS
   SET EVENT_NAME = 'VCAST_APPS_TAX_REVIEW_RESUBMIT'
   , EVENT_DESC ='VCAST_APPS_TAX_REVIEW_RESUBMIT'
   , LAST_UPDATED_DATE = sysdate
   , LAST_UPDATED_BY = 'system'
   WHERE EVENT_ID = 2021
/
--------------------------------------------------------------------------------------------------------
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name, phase_desc,
             created_by, created_date, last_updated_by, last_updated_date
            )
            
            
     VALUES (2001, 'SUBMITTED', 'Submission Phase for V CAST Application',
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name,
             phase_desc, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (2002, 'RFI-CONTENT PROG',
             'RFI-CONTENT PROG for V CAST Application', 'system', SYSDATE,
             'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name,
             phase_desc, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (2003, 'CONTENT PROG APPROVED',
             'CONTENT PROG APPROVED for V CAST Application', 'system', SYSDATE,
             'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name,
             phase_desc, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (2004, 'RFI-LEGAL/CONTENT',
             'RFI-LEGAL/CONTENT for V CAST Application', 'system', SYSDATE,
             'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name,
             phase_desc, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2005, 'LEGAL/CONTENT APPROVED',
             'LEGAL/CONTENT APPROVED for V CAST Application', 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name, phase_desc,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (2006, 'RFI-TAX REVIEW', 'RFI-TAX REVIEW for V CAST Application',
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name,
             phase_desc, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (2007, 'PENDING TAX APPROVAL',
             'PENDING TAX APPROVAL for V CAST Application', 'system', SYSDATE,
             'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name, phase_desc, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2008, 'REJECTED', 'Rejected for V CAST Application', 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_lifecycle_phases
            (phase_id, phase_name, phase_desc, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2009, 'APPROVED', 'APPROVED V CAST Application', 'system',
             SYSDATE, 'system', SYSDATE
            )
/
--------------------------------------------------------------------------------------------------------

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2004, 'Alcohol', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2031, 'Alcohol', 'Alcohol',
             2004, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2005, 'Blogging', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2032, 'Blogging', 'Blogging',
             2005, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2006, 'Books', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2033, 'Books', 'Books',
             2006, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2007, 'Browser', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2034, 'Browser', 'Browser',
             2007, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2008, 'Business', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2035, 'Business', 'Business',
             2008, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2009, 'Charities/Nonprofits', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2036, 'Charities/Nonprofits', 'Charities/Nonprofits',
             2009, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2010, 'COMMUNICATIONS', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2037, 'Email', 'Email',
             2010, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2038, 'VoIP', 'VoIP',
             2010, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2039, 'Other', 'Other',
             2010, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2040, 'Voice Mail', 'Voice Mail',
             2010, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2011, 'COMMUNITY', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2041, 'Chat/IM', 'Chat/IM',
             2011, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2042, 'Forums', 'Forums',
             2011, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2043, 'Message Boards', 'Message Boards',
             2011, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2044, 'Personal Ads', 'Personal Ads',
             2011, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2045, 'Social Networks', 'Social Networks',
             2011, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2046, 'Other', 'Other',
             2011, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2012, 'Content Storage', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2047, 'Content Storage', 'Content Storage',
             2012, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2013, 'Contests/Sweepstakes', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2048, 'Contests/Sweepstakes', 'Contests/Sweepstakes',
             2013, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2014, 'EDUCATION', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2049, 'General', 'General',
             2014, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2050, 'Sex Education', 'Sex Education',
             2014, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2015, 'Entertainment', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2051, 'Entertainment', 'Entertainment',
             2015, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2016, 'Finance', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2052, 'Finance', 'Finance',
             2016, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2017, 'Gambling', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2053, 'Gambling', 'Gambling',
             2017, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2018, 'GAMES/NOVELTY', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2054, 'Casino', 'Casino',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2055, 'Classic/Arcade', 'Classic/Arcade',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2056, 'Entertainment', 'Entertainment',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2057, 'Other', 'Other',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2058, 'Prizes', 'Prizes',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2059, 'Puzzles', 'Puzzles',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2060, 'Sports', 'Sports',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2061, 'Strategy', 'Strategy',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2062, 'TV/Movie/Music', 'TV/Movie/Music',
             2018, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2019, 'Affinity Groups', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2063, 'Affinity Groups', 'Affinity Groups',
             2019, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2020, 'HEALTH/FITNESS/MEDICAL', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2064, 'General', 'General',
             2020, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2065, 'Prescriptions', 'Prescriptions',
             2020, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2021, 'Information/Reference', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2066, 'Information/Reference', 'Information/Reference',
             2021, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2022, 'Lifestyle', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2067, 'Lifestyle', 'Lifestyle',
             2022, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2023, 'M-COMMERCE', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2068, 'Banking', 'Banking',
             2023, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2069, 'Business', 'Business',
             2023, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2070, 'Payment', 'Payment',
             2023, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2071, 'Shopping', 'Shopping',
             2023, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2072, 'Wallet', 'Wallet',
             2023, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2024, 'MODELING', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2073, 'Glamour', 'Glamour',
             2024, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2074, 'Bikini', 'Bikini',
             2024, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2025, 'MUSIC', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2075, 'Streaming', 'Streaming',
             2025, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2076, 'non-streaming', 'non-streaming',
             2025, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2026, 'NAVIGATION', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2077, 'General', 'General',
             2026, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name,
             sub_category_desc, category_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2078, 'LBS Consumer!:  Advertising - Couponing',
             'LBS Consumer!:  Advertising - Couponing', 2026, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name,
             sub_category_desc, category_id,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (2079, 'LBS Consumer!:  Friend !& Family, Child Finder',
             'LBS Consumer!:  Friend !& Family, Child Finder', 2026,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2080, 'LBS Consumer!:  Gaming', 'LBS Consumer!:  Gaming',
             2026, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name,
             sub_category_desc, category_id,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (2081, 'LBS Consumer!:  Navigation Mapping, POI, Turn by Turn',
             'LBS Consumer!:  Navigation Mapping, POI, Turn by Turn', 2026,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id,
             sub_category_name,
             sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2082,
             'LBS Consumer!:  Social Networking, IM, Blogging, Buddy List',
             'LBS Consumer!:  Social Networking, IM, Blogging, Buddy List',
             2026, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name,
             sub_category_desc, category_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2083, 'LBS!: Enterprise!:  Field Force Automation',
             'LBS!: Enterprise!:  Field Force Automation', 2026, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2084, 'LBS!: Enterprise!:  Other', 'LBS!: Enterprise!:  Other',
             2026, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name,
             sub_category_desc, category_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2085, 'LBS!: Enterprise!: Sales Force Automation',
             'LBS!: Enterprise!: Sales Force Automation', 2026, 'system',
             SYSDATE, 'system', SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2027, 'News/Current Events', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2086, 'News/Current Events', 'News/Current Events',
             2027, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2028, 'Other', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2087, 'Other', 'Other',
             2028, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2029, 'PHOTOGRAPHY/IMAGES', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2088, 'General', 'General',
             2029, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2089, 'Binary Content', 'Binary Content',
             2029, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2090, 'Adult', 'Adult',
             2029, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2030, 'Productivity/Utilities', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2091, 'Productivity/Utilities', 'Productivity/Utilities',
             2030, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2031, 'Ring Tones', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2092, 'Ring Tones', 'Ring Tones',
             2031, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2032, 'Search', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2093, 'Search', 'Search',
             2032, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2033, 'Sports', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2094, 'Sports', 'Sports',
             2033, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2034, 'Tobacco', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2095, 'Tobacco', 'Tobacco',
             2034, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2035, 'Travel', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2096, 'Travel', 'Travel',
             2035, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2036, 'UGC (User Generated Content)', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name,
             sub_category_desc, category_id, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (2097, 'UGC (User Generated Content)',
             'UGC (User Generated Content)', 2036, 'system', SYSDATE,
             'system', SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2037, 'VIDEO', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2098, 'non-streaming (binarY)', 'non-streaming (binarY)',
             2037, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2099, 'streaming', 'streaming',
             2037, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2100, 'Adult', 'Adult',
             2037, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2038, 'Wallpaper Applications', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2101, 'Wallpaper Applications', 'Wallpaper Applications',
             2038, 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_app_categories
            (category_id, category_name, platform_id, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (2039, 'Weather', 44, 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_app_sub_categories
            (sub_category_id, sub_category_name, sub_category_desc,
             category_id, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (2102, 'Weather', 'Weather',
             2039, 'system', SYSDATE, 'system',
             SYSDATE
            )
/

------------------------------------------------------------------------------------

INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2003
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2004
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2005
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2006
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2007
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2008
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2009
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2010
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2011
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2012
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2013
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2015
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2016
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2017
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2019
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2020
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (34, 2021
            )
/
-------------------------------------------------------------------------------------
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME ='Manage VCAST Video' 
, PRIVILEGE_DESCRIPTION = 'Manage VCAST Video'
where PRIVILEGE_ID = 279 
/
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME ='V CAST Apps - Programming Content Manager' 
, PRIVILEGE_DESCRIPTION = 'V CAST Apps - Programming Content Manager' 
where PRIVILEGE_ID = 801
/
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME ='V CAST Apps - Tax Manager' 
, PRIVILEGE_DESCRIPTION = 'V CAST Apps - Tax Manager' 
where PRIVILEGE_ID = 805
/
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME='V CAST Apps - Content Standard Manager'
, PRIVILEGE_DESCRIPTION = 'V CAST Apps - Content Standard Manager' 
where PRIVILEGE_ID = 804
/
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME='V CAST Apps - Legal Manager'
, PRIVILEGE_DESCRIPTION = 'V CAST Apps - Legal Manager'
where PRIVILEGE_ID = 803
/
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME='V CAST Apps - Manage V CAST Applications'
, PRIVILEGE_DESCRIPTION = 'V CAST Apps - Manage V CAST Applications'
where PRIVILEGE_ID = 2001
/
update AIMS_SYS_PRIVILEGES  
set PRIVILEGE_NAME='V CAST Apps - All Approved V CAST Applications'
, PRIVILEGE_DESCRIPTION = 'V CAST Apps - All Approved V CAST Applications'
where PRIVILEGE_ID = 2000
/
-------------------------------------------------------------------------------------		 
COMMIT
/
SET escape off