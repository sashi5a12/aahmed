CREATE OR REPLACE PACKAGE aims_lob_utils
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for lob utility procedures/functions.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 17-12-2003       rqazi           Created
||
||
||
||
*/
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_single_files
         (
            p_file_column               IN  VARCHAR2,           -- application id
            p_file_name_column          IN  VARCHAR2,           -- application id
            p_table_name                IN  VARCHAR2,           -- application id
            p_pk_column_name            IN  VARCHAR2,           -- application id
            p_pk_id                     IN  NUMBER,           -- application id
            p_out_result                OUT  VARCHAR2           -- 'Y' one of the filter words is used 'N' words are good
          );
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_uploaded_files
         (
            p_apps_id                IN  NUMBER,           -- application id
            p_out_result            OUT  VARCHAR2         -- 'Y' one of the filter words is used 'N' words are good
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE copy_lob_from_temp_table
         ( p_temp_table_id          IN  NUMBER,    -- primary key of the temp table
           p_pk_expr_to_table       IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_name          IN  VARCHAR2,  -- name of the table in which the record will be updated
           p_to_table_col_name      IN  VARCHAR2,  -- name of the column of the table in which the record will be updated
           p_user_id                IN  VARCHAR2   -- user id of the current user
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE clone_images
         ( p_clone_from_app_id      IN  NUMBER,    -- application id to clone from
           p_clone_to_app_id        IN  VARCHAR2,  -- application id to clone to
           p_platform_id            IN  VARCHAR2   -- platform id
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE copy_lob_from_to_table
         ( p_from_table_col_name     IN  varchar2,  -- name of the column of the table which will be selected for update. 
           p_from_table_name         IN  varchar2,  -- name of table which record will be selected for update.
           p_pk_expr_from_table      IN  varchar2,  -- expression in the form pkid = value
           p_to_table_col_name       IN  varchar2,  -- name of the column of the table in which will be updated. 
           p_to_table_name           IN  varchar2,  -- name of the table in which column will be updated
           p_pk_expr_to_table        IN  varchar2   -- expression in the to pkid = value
         );

/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_LOB_UTILS; -- Package Specification AIMS_LOB_UTILS
/

