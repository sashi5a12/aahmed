CREATE OR REPLACE Package AIMS_UTILS
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for utility procedures/functions.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 11-12-2003       rqazi           Created
||
||
||
||
*/


/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION value_in_table
         (  p_value     varchar2,
            p_table     DBMS_UTILITY.UNCL_ARRAY
         )
    RETURN boolean;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_two_tables
         (  p_table_in      IN               DBMS_UTILITY.UNCL_ARRAY,
            p_table_in_out  IN OUT NOCOPY    DBMS_UTILITY.UNCL_ARRAY
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_journal_entry
         (  p_apps_id      IN   number,
            p_alliance_id  IN   number,
            p_journal_text IN   varchar2,
            p_journal_type IN   varchar2,
            p_created_by   IN   varchar2
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_app_with_relations
         (  p_apps_id                IN  number,            -- app_id of the application and its relations to be deleted
            p_curr_user_name         IN  varchar2           -- user name of the person deleting the application
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_content_with_relations
         (  p_content_id             IN  number,            -- content_id of the creative content and its relations to be deleted
            p_curr_user_name         IN  varchar2           -- user name of the person deleting the application
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_UTILS; -- Package Specification AIMS_UTILS
/

