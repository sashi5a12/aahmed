CREATE OR REPLACE Package Body AIMS_APPLICATIONS_PKG
IS



/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_access_to_spotlight_page
         (
            p_apps_id                   IN  number,           -- application id
            p_out_result                OUT  varchar2         -- 'Y' indicates 'Access Permitted', 'N' otherwise
          )

    IS
        sql_select varchar2(1000);
    BEGIN
          p_out_result := 'N';

          sql_select :=   sql_select || 'select distinct ''Y'' ';
          sql_select :=   sql_select || 'FROM ';
          sql_select :=   sql_select || '   aims_apps a, ';
          sql_select :=   sql_select || '   aims_alliance_contracts ac, ';
          sql_select :=   sql_select || '   aims_contracts c ';
          sql_select :=   sql_select || 'WHERE ';
          sql_select :=   sql_select || '   a.apps_id = :1 ';
          sql_select :=   sql_select || '   and a.alliance_id = ac.alliance_id ';
          sql_select :=   sql_select || '   and ac.contract_id = c.contract_id  ';
          sql_select :=   sql_select || '   and a.phase_id in (1, 7) ';
          sql_select :=   sql_select || '   and ac.status = ''ACCEPTED'' ';
          sql_select :=   sql_select || '   and c.platform_id = 5 ';

          execute immediate sql_select
                                into p_out_result using p_apps_id;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_result := 'N';

    END check_access_to_spotlight_page;

/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_APPLICATIONS_PKG; -- Package Body AIMS_APPLICATIONS_PKG
/

