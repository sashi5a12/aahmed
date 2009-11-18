CREATE OR REPLACE Package Body AIMS_REPORTS_UTILS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_report_date
    RETURN  varchar2
    IS

    /*
    || Overview:        Gives the date on which the report was run.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */

        todays_date     varchar2(20);

    BEGIN

        select
            to_char(sysdate, 'MM/DD/YYYY')
        into
            todays_date
        from
            dual;

        RETURN todays_date;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           RETURN to_char(sysdate, 'MM/DD/YYYY');

    END get_report_date;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_department_name
        (
            p_user_id                   IN      number
        )
    RETURN  varchar2
    IS

    /*
    || Overview:        Gives the department name of a given user
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */
        v_dept_name     varchar2(100);

    BEGIN

        select
            NVL(d.department_name, '')
        into
            v_dept_name
        from
            aims_vzw_departments d,
            aims_users u
        where
            u.user_id = p_user_id
            and d.department_id = u.department_id (+);

        RETURN v_dept_name;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           RETURN '';

    END get_department_name;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_employee_name
        (
            p_user_id                   IN      number
        )
    RETURN  varchar2
    IS

    /*
    || Overview:        Gives the first name || last name of a given user
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */
        v_user_name     varchar2(100);

    BEGIN

        select
            c.first_name || ' ' || c.last_name
        into
            v_user_name
        from
            aims_users u,
            aims_contacts c
        where
            u.user_id = p_user_id
            and u.contact_id = c.contact_id
            and u.user_type = 'V';

        RETURN v_user_name;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           RETURN '';

    END get_employee_name;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE vzw_employee_details
         (
           p_out_result                 OUT     TYPES.cursor_type
         )
    IS

    /*
    || Overview:        Gives the cursor for the VZW employee details.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */


    BEGIN

        OPEN p_out_result FOR
            select
                u.user_id,
                c.first_name || ' ' || c.last_name
            from
                aims_users u,
                aims_contacts c
            where
                u.contact_id = c.contact_id
                and u.user_type = 'V';

    EXCEPTION
      WHEN OTHERS THEN
           IF(p_out_result%isopen) THEN
               CLOSE p_out_result;
           END IF;
           RAISE;

    END vzw_employee_details;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE vzw_emp_alliance_details
         (
           p_user_id                     IN     number,
           p_out_result                 OUT     TYPES.cursor_type
         )
    IS

    /*
    || Overview:        Gives the cursor for the alliances associated with a VZW employee.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */

      sql_select   varchar2(4000);

    BEGIN

        sql_select := sql_select || 'select ';
        sql_select := sql_select || '   a.company_name company_name, ';
        sql_select := sql_select || '   to_char(a.created_date, ''MM/DD/YYYY'') created_date, ';
        sql_select := sql_select || '   decode(a.status, ''Y'', ''Active'', ''N'', ''Inactive'') alliance_status, ';
        sql_select := sql_select || '   (select count(*) from aims_apps where alliance_id = a.alliance_id) cnt_submitted_apps, ';
        sql_select := sql_select || '   (select count(*) from aims_apps where alliance_id = a.alliance_id) cnt_live_apps, ';
        sql_select := sql_select || '   p.platform_name, ';
        sql_select := sql_select || '   app.title, ';
        sql_select := sql_select || '   to_char(app.created_date, ''MM/DD/YYYY'') app_created_date, ';
        sql_select := sql_select || '   decode(app.if_on_hold, ''Y'', ''On Hold'', lp.phase_name) app_status ';
        sql_select := sql_select || 'from ';
        sql_select := sql_select || '   aims_alliances a, ';
        sql_select := sql_select || '   aims_apps app, ';
        sql_select := sql_select || '   aims_platforms p, ';
        sql_select := sql_select || '   aims_lifecycle_phases lp ';
        sql_select := sql_select || 'where ';
        sql_select := sql_select || '   ((app.vzw_apps_contact_id = :1) OR (a.vzw_account_manager = :2)) ';
        sql_select := sql_select || '   and app.platform_id = p.platform_id ';
        sql_select := sql_select || '   and a.alliance_id = app.alliance_id ';
        sql_select := sql_select || '   and app.phase_id = lp.phase_id ';
        sql_select := sql_select || 'order by ';
        sql_select := sql_select || '   1, 6 ';

        OPEN p_out_result FOR sql_select USING p_user_id, p_user_id;

    EXCEPTION
      WHEN OTHERS THEN
           IF(p_out_result%isopen) THEN
               CLOSE p_out_result;
           END IF;
           RAISE;

    END vzw_emp_alliance_details;

	/* -------------------------------------------------------------------------------------------------------------  */
/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION GET_MARKET_SHARE
         (
            p_alliance_id                   IN  NUMBER,
            p_phase_id                      IN  NUMBER,
			p_start_date                    IN  DATE,
			p_end_date						IN DATE
          )
          RETURN NUMBER

    IS
	total_apps_count NUMBER;
	alliance_apps_count NUMBER;
	apps_percentage NUMBER(6,2);
    BEGIN
	
	IF p_phase_id <> 3 THEN  

		SELECT COUNT(*) INTO total_apps_count FROM AIMS_APPS WHERE phase_id = p_phase_id 
		AND SUBMITTED_DATE BETWEEN p_start_date AND p_end_date;

		SELECT COUNT(*) INTO alliance_apps_count FROM AIMS_APPS WHERE phase_id = p_phase_id
			   AND alliance_id = p_alliance_id AND SUBMITTED_DATE BETWEEN p_start_date AND p_end_date
			   AND phase_id =p_phase_id;
	ELSE
		SELECT COUNT(*) INTO total_apps_count FROM AIMS_APPS WHERE
		SUBMITTED_DATE BETWEEN p_start_date AND p_end_date
		AND phase_id IN (6,7,10,11);
		
		SELECT COUNT(*) INTO alliance_apps_count FROM AIMS_APPS WHERE 
			   alliance_id = p_alliance_id AND SUBMITTED_DATE BETWEEN p_start_date AND p_end_date
			   AND phase_id IN (6,7,10,11);
	END IF;
	
	apps_percentage := (alliance_apps_count/total_apps_count)*100;
	RETURN apps_percentage;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;

    END GET_MARKET_SHARE;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_REPORTS_UTILS; -- Package Body FNJ_ADVANCED_SEARCH
/

