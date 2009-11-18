CREATE OR REPLACE PACKAGE aims_login_content_pkg
IS

/*
|| All contents are Copyright 2006 Verizon Wireless, Inc.
||
|| Author:              Muhammad Shiraz Q (MSQ).
||
|| File:
||
|| Overview:            Checks if the user has acknowledged login content, if not or acknowledged before given number of days then show login content
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 05-30-2008       MSQ          Created.
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

   PROCEDURE get_login_content_to_show (
      p_user_id                IN       NUMBER, -- userid.
      p_alliance_id            IN       NUMBER, -- Alliance ID.
      p_days                   IN       NUMBER, -- Days.
      p_out_login_content_id   OUT      VARCHAR2 -- Result Login Content ID.
   );
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_alliance_contact_ack
        ( 
          p_login_content_id       IN  NUMBER,  -- Content ID.
          p_alliance_id            IN  NUMBER,  -- Alliance ID.
          p_user_name              IN  VARCHAR2 -- userid name.
        );

/* -------------------------------------------------------------------------------------------------------------  */


END aims_login_content_pkg; -- Package Specification aims_login_content_pkg.
/

