----------------------------------------------------------------------------------------------------------
-- Drop constraits
----------------------------------------------------------------------------------------------------------
ALTER TABLE AIMS_JAVA_APPS
 DROP CONSTRAINT FK_JAVA_CATEGORY_TYPE
/
ALTER TABLE AIMS_JAVA_APPS
 DROP CONSTRAINT FK_JAVA_CATEGORY_TYPE1
/
ALTER TABLE AIMS_JAVA_APPS
 DROP CONSTRAINT FK_JAVA_CATEGORY_TYPE2
/
ALTER TABLE AIMS_JAVA_APPS
 DROP CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE
/
ALTER TABLE AIMS_JAVA_APPS
 DROP CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE1
/
ALTER TABLE AIMS_JAVA_APPS
 DROP CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE2
/
ALTER TABLE AIMS_APP_SUB_CATEGORIES
 DROP CONSTRAINT FK_APP_SUB_CATEGORIES_1
/

----------------------------------------------------------------------------------------------------------
-- Delete cat & sub-cat
----------------------------------------------------------------------------------------------------------

DELETE from AIMS_APP_CATEGORIES
where CATEGORY_ID in 
	( select category_id  
	  	from AIMS_APP_CATEGORIES 
		where platform_id = 44
	)

DELETE AIMS_APP_CATEGORIES
where platform_id = 44

----------------------------------------------------------------------------------------------------------
-- Add new cat & sub-cat
----------------------------------------------------------------------------------------------------------

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

----------------------------------------------------------------------------------------------------------
-- Put back the constraints
----------------------------------------------------------------------------------------------------------
ALTER TABLE AIMS_APP_SUB_CATEGORIES
 ADD FOREIGN KEY (CATEGORY_ID) 
 REFERENCES AIMS_APP_CATEGORIES (CATEGORY_ID)
    DEFERRABLE INITIALLY DEFERRED 
 ENABLE NOVALIDATE
/
----------------------------------------------------------------------------------------------------------
-- Drop Contraints and Delete Old Content Ratings
----------------------------------------------------------------------------------------------------------
ALTER TABLE AIMS_JAVA_APPS
	DROP CONSTRAINT FK_JAVA_CONTENT_RATING
/
Delete from AIMS_TYPES
where TYPE_DEF_ID = 9
/
----------------------------------------------------------------------------------------------------------
-- Add New Content Ratings
----------------------------------------------------------------------------------------------------------
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2000, 9, 'C (7+)', 'C (7+)', 1, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2001, 9, 'T (13+)', 'T (13+)', 2, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2002, 9, 'YA (17+)', 'YA (17+)', 3, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2003, 9, 'M (18+)', 'M (18+)', 4, 'system', sysdate, 'system', sysdate)
/
----------------------------------------------------------------------------------------------------------
-- Put back Contraints
----------------------------------------------------------------------------------------------------------
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTENT_RATING
 FOREIGN KEY (CONTENT_RATING_TYPE_ID)
 REFERENCES AIMS_TYPES (TYPE_ID)
     DEFERRABLE INITIALLY DEFERRED 
 ENABLE NOVALIDATE
)
/
