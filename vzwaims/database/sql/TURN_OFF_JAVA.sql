--------------------------------------------------------------------------------------------------------
-- Script to remove Master date related to java platform. Run this script when java platform is off
-- This scipt cannot be executed once a java plaform offdeck/ondeck application is created beacuse of referential integrity constraints
--------------------------------------------------------------------------------------------------------


DELETE FROM AIMS_JOURNAL_ENTRIES
WHERE APPS_ID IN (SELECT APPS_ID 
                    FROM AIMS_APPS
                   WHERE SUB_CATEGORY_ID IN
                                    (SELECT SUB_CATEGORY_ID
                                       FROM AIMS_APP_SUB_CATEGORIES
                                      WHERE CATEGORY_ID IN
                                                (SELECT aac.CATEGORY_ID
                                                   FROM Aims_App_Categories aac
                                                  WHERE aac.PLATFORM_ID = 44)))
/ 
                                                                                                   
DELETE FROM AIMS_JAVA_APPS
 WHERE JAVA_APPS_ID IN  (SELECT APPS_ID 
                           FROM AIMS_APPS
                          WHERE SUB_CATEGORY_ID IN
                                       (SELECT SUB_CATEGORY_ID
                                          FROM AIMS_APP_SUB_CATEGORIES
                                         WHERE CATEGORY_ID IN
                                                   (SELECT aac.CATEGORY_ID
                                                      FROM Aims_App_Categories aac
                                                     WHERE aac.PLATFORM_ID = 44)))
/

DELETE FROM AIMS_APPS
      WHERE SUB_CATEGORY_ID IN
                    (SELECT SUB_CATEGORY_ID
                       FROM AIMS_APP_SUB_CATEGORIES
                      WHERE CATEGORY_ID IN
                                    (SELECT aac.CATEGORY_ID
                                       FROM Aims_App_Categories aac
                                      WHERE aac.PLATFORM_ID = 44))
/
 

DELETE FROM   AIMS_APP_SUB_CATEGORIES aasc
      WHERE   aasc.CATEGORY_ID IN (SELECT   aac.CATEGORY_ID
                                     FROM   Aims_App_Categories aac
                                    WHERE   aac.PLATFORM_ID = 44)
/
    
DELETE FROM   Aims_App_Categories aac
      WHERE   aac.PLATFORM_ID = 44
/


DELETE FROM   AIMS_LIFECYCLE_PHASES alp
      WHERE   alp.PHASE_ID IN
                    (2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009)
/


DELETE FROM AIMS_ALLIANCE_CONTRACT_AMENDS
WHERE ALLIANCE_CONTRACT_ID IN (SELECT ALLIANCE_CONTRACT_ID
                                 FROM AIMS_ALLIANCE_CONTRACTS
                                WHERE CONTRACT_ID IN (SELECT CONTRACT_ID 
                                                        FROM AIMS_CONTRACTS
                                                       WHERE PLATFORM_ID IN (SELECT PLATFORM_ID 
                                                                               FROM AIMS_PLATFORMS ap 
                                                                              WHERE ap.PLATFORM_ID=44)))
/

DELETE FROM AIMS_ALLIANCE_CONTRACTS
 WHERE CONTRACT_ID IN (SELECT CONTRACT_ID 
                         FROM AIMS_CONTRACTS
                        WHERE PLATFORM_ID IN (SELECT PLATFORM_ID 
                                                FROM AIMS_PLATFORMS ap 
                                               WHERE ap.PLATFORM_ID=44))
/ 
                                                                                             
DELETE FROM AIMS_CONTRACT_AMENDMENTS
 WHERE CONTRACT_ID IN ( SELECT CONTRACT_ID
                          FROM AIMS_CONTRACTS
                         WHERE PLATFORM_ID IN (SELECT PLATFORM_ID 
                                                 FROM AIMS_PLATFORMS ap 
                                                WHERE ap.PLATFORM_ID=44))
/

DELETE FROM AIMS_CONTRACTS
WHERE PLATFORM_ID IN ( SELECT PLATFORM_ID 
                         from AIMS_PLATFORMS ap 
                        where ap.PLATFORM_ID=44)
/ 

DELETE FROM AIMS_PLATFORMS ap
      WHERE ap.PLATFORM_ID = 44
/

COMMIT
/
--------------------------------------------------------------------------------------------------------
