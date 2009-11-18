CREATE OR REPLACE Package AIMS_DB_BACKUP
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used to send email messages after db export and get the size of the db.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 17-08-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_db_size
         RETURN varchar2;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_db_size;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE send_email
         (
            p_status                 IN  varchar2         -- 'Y' success 'N' failure
          );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_DB_BACKUP; -- Package Specification AIMS_DB_BACKUP
/

