CREATE OR REPLACE Package Body      AIMS_EMAIL_GROUPS_PKG_UTILS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION all_contact_types
    RETURN  aimsTableType

    IS


    /*
    || Overview:        Gets the to contact_id for a given user_id
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 02-02-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        l_data             aimsTableType := aimsTableType();
        l_cnt              number default 0;

    BEGIN

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'A', 'Administrator' );

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'B', 'Business' );

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'E', 'Executive' );

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'M', 'Marketing' );

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'T', 'Technical' );

         RETURN l_data;

    END all_contact_types;
/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION all_contract_status
    RETURN  aimsTableType

    IS


    /*
    || Overview:        Gets the to contact_id for a given user_id
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 02-02-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        l_data             aimsTableType := aimsTableType();
        l_cnt              number default 0;

    BEGIN

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'ACCEPTED', 'Accepted' );

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'OFFERED', 'Offered' );

         l_cnt := l_cnt + 1;
         l_data.extend;
         l_data(l_cnt) := aimsScalarType( 'REJECTED', 'Rejected' );

         RETURN l_data;

    END all_contract_status;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_GROUPS_PKG_UTILS; -- Package Body AIMS_EMAIL_GROUPS_PKG_UTILS
/

