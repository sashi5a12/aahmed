CREATE OR REPLACE PACKAGE Aims_Bulletin_Pkg
   IS

/*
|| All contents are Copyright 2006 Verizon Wireless, Inc.
||
|| Author:              Adnan Makda (amakda).
||
|| File:
||
|| Overview:            Used by AIMS application for Bulletin module.
actions.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 03-24-2006       amakda          Created.
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_bulletins_to_show
        ( p_user_id               IN   NUMBER,  -- username.
          p_out_bulletin_id       OUT  VARCHAR2 -- Result Bulletin ID of active bulletin, 0(zero) otherwise.
        );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_if_bulletin_viewed
        ( p_user_id               IN   NUMBER,  -- username.
          p_bulletin_id           OUT  NUMBER   -- Bulletin ID of active bulletin, 0(zero) otherwise.
        );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_bulletin_viewed_count
        ( p_user_id                IN  NUMBER,  -- username.
          p_bulletin_id            IN  NUMBER   -- bulletin ID.
        );

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE update_bulletin_counter
        ( p_user_id                IN  NUMBER,  -- username.
          p_bulletin_id            IN  NUMBER   -- bulletin ID.
        );

/* -------------------------------------------------------------------------------------------------------------  */


END Aims_Bulletin_Pkg; -- Package Specification AIMS_BULLETIN_PKG.
/

