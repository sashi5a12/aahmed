CREATE OR REPLACE Package AIMS_JMA_APP_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Arsalan Ahmed Qureshi (akureshi)
||
|| File:
||
|| Overview:            Used by AIMS application for JMA application maintenance.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 30-05-2009       akureshi        Created
|| 26-05-2009       akureshi        Updated, added script for isFeatured
||
||
||
*/

    PROCEDURE publish_jma_app
         (
           p_solution_id            IN number,
           p_is_published           IN char,
           p_is_mobile_professional IN char,
           p_is_soho                IN char,
           p_is_sme                 IN char,
           p_is_enterprise          IN char,
           p_is_featured            IN char
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_JMA_APP_PKG; -- Package Specification AIMS_JMA_APP_PKG
/