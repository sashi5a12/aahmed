update AIMS_APP_CATEGORIES set CATEGORY_NAME='Music*' where CATEGORY_ID=2001;

/*
* Script to remove Master date related to java platform. Run this script when java platform is off
* This scipt cannot be executed once a java plaform offdeck/ondeck application is created beacuse of referential integrity constraints
* BEGIN
*/
delete from AIMS_APP_SUB_CATEGORIES aasc where aasc.CATEGORY_ID in 
	   (
	   		select aac.CATEGORY_ID from Aims_App_Categories aac where aac.PLATFORM_ID=44
	   ) 

delete from Aims_App_Categories aac where aac.PLATFORM_ID=44;

delete from AIMS_LIFECYCLE_PHASES alp where alp.PHASE_ID in (2001,2002,2003,2004,2005,2006,2007,2008,2009);

delete from AIMS_PLATFORMS ap where ap.PLATFORM_ID=44

/*
* END
*/



/*
* Script to add  Master date pertaining to java platform. Run this script when java platform is on
* BEGIN
*/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2000, 'Games', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2001, 'Music', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2002, 'Pictures', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2003, 'Other', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2000, 'Puzzle Games', 'Puzzle Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2001, 'Sports Games', 'Sports Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2002, 'Classic Games', 'Classic Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2003, 'Play and Win', 'Play and Win', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2004, 'TV/Movie/Music Games', 'TV/Movie/Music Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2005, 'Action Games', 'Action Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2006, 'Casino Games', 'Casino Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2007, 'Strategy Games', 'Strategy Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2008, 'True Tones', 'True Tones', 2001, 'system', sysdate, 'system', sysdate)
/
       

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2009, 'Ringtones', 'Ringtones', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2010, 'Fun Tones', 'Fun Tones', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2011, 'Music Magazines', 'Music Magazines', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2012, 'Music Recognition', 'Music Recognition', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2013, 'Eye Candy', 'Eye Candy', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2014, 'Sports', 'Sports', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2015, 'The Boy`s Club', 'The Boy`s Club', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2016, 'SlideShows', 'SlideShows', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2017, 'My Wallpaper, My Way', 'My Wallpaper, My Way', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2018, 'Something 4 Tha Ladies', 'Something 4 Tha Ladies', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2019, 'For Your Entertainment', 'For Your Entertainment', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2020, 'Entertainment', 'Entertainment', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2021, 'Travel and Navigation', 'Travel and Navigation', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2022, 'Sports', 'Sports', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2023, 'Weather', 'Weather', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2024, 'Community and Sharing', 'Community and Sharing', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2025, 'E-mail', 'E-mail', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2026, 'Health and Wellness', 'Health and Wellness', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2027, 'Family Life', 'Family Life', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2028, 'Shopping n Style', 'Shopping n Style', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2029, 'Government', 'Government', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2030, 'Business Tools/Information', 'Business Tools/Information', 2003, 'system', sysdate, 'system', sysdate)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2001,
            'SUBMITTED',
            'Submission Phase for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2002,
            'RFI-CONTENT PROG',
            'RFI-CONTENT PROG for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2003,
            'CONTENT PROG APPROVED',
            'CONTENT PROG APPROVED for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2004,
            'RFI-LEGAL/CONTENT',
            'RFI-LEGAL/CONTENT for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2005,
            'LEGAL/CONTENT APPROVED',
            'LEGAL/CONTENT APPROVED for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2006,
            'RFI-TAX REVIEW',
            'RFI-TAX REVIEW for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2007,
            'PENDING TAX APPROVAL',
            'PENDING TAX APPROVAL for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2008,
            'REJECTED',
            'Rejected for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2009,
            'APPROVED',
            'APPROVED java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_PLATFORMS (PLATFORM_ID,
                            PLATFORM_NAME,
                            PLATFORM_DESC,
                            CREATED_BY,
                            CREATED_DATE,
                            LAST_UPDATED_BY,
                            LAST_UPDATED_DATE)
  VALUES   (44,
            'Java',
            'Java',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

/*
* END
*/

