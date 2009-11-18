CREATE OR REPLACE Package AIMS_IMAGE_ARCHIVE_PKG
  IS

/*
|| All contents are Copyright 2004 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for archiving of "old" images.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 12-01-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE backup_images
         (
            p_status              OUT  varchar2     -- status of backup process 'Y' success  'N' failure
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE archive_images
         (
            p_status              OUT  varchar2     -- status of archive process 'Y' success  'N' failure
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_IMAGE_ARCHIVE_PKG; -- Package Specification AIMS_IMAGE_ARCHIVE_PKG
/

