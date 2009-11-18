CREATE OR REPLACE Package BDS_MVNFORUM
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Adnan Makda (amakda)
||
|| File:
||
|| Overview:            Used by BDS application to keep the BDS_USERS and MVNFORUMMEMBER tables in sync.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 05-13-2004       amakda           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE sync_mvnforum_account
         ( p_bds_user_id                 IN  number,             -- bds user id whose account is to be updated
           p_trans_type                  IN  varchar2            -- 'I' add  'U' update 'D' delete           
          );

/* -------------------------------------------------------------------------------------------------------------  */    

END BDS_MVNFORUM; -- Package Specification BDS_MVNFORUM
/

