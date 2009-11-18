CREATE OR REPLACE Package AIMS_ALLIANCES_PKG_UTILS
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for alliance maintenance.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 02-03-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance_relations
         ( p_alliance_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_curr_user_name         IN  varchar2           -- user name of the person deleting the alliance
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance_app_relations
         ( p_alliance_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_curr_user_name         IN  varchar2           -- user name of the person deleting the alliance
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance_user_relations
         ( p_alliance_id            IN  number,            -- alliance_id of the alliance relations to be deleted
           p_curr_user_name         IN  varchar2           -- user name of the person deleting the alliance
          );


/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE get_alliance_user_ids
         ( p_alliance_id            IN  number,           -- alliance_id
           p_user_ids              OUT  varchar2          -- comma seperated list of user ids
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_alliance_contact_ids
         ( p_alliance_id            IN  number,           -- alliance_id
           p_contact_ids           OUT  varchar2          -- comma seperated list of contact ids
         );

/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE get_alliance_user_ids
         ( p_alliance_id            IN  number,                     -- alliance_id
           p_t_user_ids            OUT  dbms_utility.uncl_array     -- table of user ids
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_alliance_contact_ids
         ( p_alliance_id            IN  number,                     -- alliance_id
           p_t_contact_ids         OUT  dbms_utility.uncl_array     -- table of contact ids
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_ALLIANCES_PKG_UTILS; -- Package Specification AIMS_ALLIANCES_PKG_UTILS
/

