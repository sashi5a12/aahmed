CREATE OR REPLACE Package BDS_OPEN_REPORTS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE sync_report_account
         ( p_bds_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_trans_type                  IN  varchar2            -- 'A' add  'U' update 'D' delete           
          );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_OPEN_REPORTS; -- Package BDS_OPEN_REPORTS
/

