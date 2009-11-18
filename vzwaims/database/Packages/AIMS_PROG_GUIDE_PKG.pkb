CREATE OR REPLACE Package Body      AIMS_PROG_GUIDE_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_results_cursor
         (
            p_program_guide_name            IN varchar2,            -- full/partial name of program guide
            p_nstl_comp_start_date          IN varchar2,            -- nstl completion start date
            p_nstl_comp_end_date            IN varchar2,            -- nstl completion end date
            p_page_no_needed                IN number,              -- page number requested
            p_num_per_page                  IN number,              -- number of records per page
            p_filter_text                   IN varchar2,            -- filtering text
            p_filter_field                  IN varchar2,            -- filtering field
            p_sort_field                    IN varchar2,            -- filtering text
            p_out_total_records            OUT number,              -- total number of records in selection set
            p_out_result                   OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Gives the cursor for the BREW APPS which match the search criteria for program guides.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 08-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        sql_select         varchar2(4000);
        sql_selone         varchar2(4000);
        sql_seltwo         varchar2(4000);
        sql_countone       varchar2(50) := 'SELECT COUNT(*) FROM ( ';
        sql_counttwo       varchar2(50) := ') ';
        sql_order_by       varchar2(100);

        from_rownum        number;
        to_rownum          number;
        v_max_points       number;
        v_num_per_page     number := 10;
        v_total_records    number := p_num_per_page;


        v_nstl_comp_start_date date;
        v_nstl_comp_end_date   date;
        v_program_guide_name   varchar2(200);
        v_filter_text          varchar2(200) := trim(upper(p_filter_text));
        v_sort_field           varchar2(10);

        newLine      varchar2(10) := chr(13) || chr(10);
   BEGIN

        if (p_nstl_comp_start_date is not null) then
            v_nstl_comp_start_date := to_date(p_nstl_comp_start_date, 'MM/DD/YYYY');
        end if;

        IF (p_nstl_comp_end_date is not null) then
            v_nstl_comp_end_date := to_date(p_nstl_comp_end_date, 'MM/DD/YYYY');
        END IF;

        IF (length(trim(p_program_guide_name)) > 1 ) THEN
            v_program_guide_name := '%' || trim(p_program_guide_name) || '%';
        END IF;

        IF (v_total_records is null or v_total_records <= 0) then
            v_total_records := 10;
        END IF;

        IF (length(trim(p_sort_field)) > 1 ) THEN
            v_sort_field := trim(p_sort_field);
        ELSE
            v_sort_field := '2';
        END IF;

        from_rownum := ((p_page_no_needed - 1) * v_num_per_page) + 1;
        --to_rownum   := (from_rownum + v_num_per_page) - 1;
        to_rownum   := v_total_records;

        sql_selone :=   sql_selone || 'SELECT ' || newLine;
        sql_selone :=   sql_selone || '   b.* ' || newLine;
        sql_selone :=   sql_selone || 'FROM (  ' || newLine;
        sql_selone :=   sql_selone || '       SELECT ' || newLine;
        sql_selone :=   sql_selone || '           a.*, ' || newLine;
        sql_selone :=   sql_selone || '           ROWNUM rnum ' || newLine;
        sql_selone :=   sql_selone || '       FROM  (   ' || newLine;

        sql_select :=   sql_select || 'SELECT  DISTINCT ' || newLine;
        sql_select :=   sql_select || '   app.apps_id, ' || newLine;
        sql_select :=   sql_select || '   a.company_name, ' || newLine;
        sql_select :=   sql_select || '   app.title, ' || newLine;
        sql_select :=   sql_select || '   ''BREW'', ' || newLine;
        sql_select :=   sql_select || '   brew_app.planned_completion_by_nstl, ' || newLine;
        sql_select :=   sql_select || '   p.phase_name, ' || newLine;
        sql_select :=   sql_select || '   brew_app.prog_guide_file_name, ' || newLine;
        sql_select :=   sql_select || '   c.category_name, ' || newLine;
        sql_select :=   sql_select || '   sc.sub_category_name, ' || newLine;
        sql_select :=   sql_select || '   a.alliance_id ' || newLine;
        sql_select :=   sql_select || 'FROM ' || newLine;
        sql_select :=   sql_select || '   aims_alliances a, ' || newLine;
        sql_select :=   sql_select || '   aims_apps app, ' || newLine;
        sql_select :=   sql_select || '   aims_brew_apps brew_app, ' || newLine;
        sql_select :=   sql_select || '   aims_lifecycle_phases p, ' || newLine;
        sql_select :=   sql_select || '   aims_app_categories c, ' || newLine;
        sql_select :=   sql_select || '   aims_app_sub_categories sc ' || newLine;
        sql_select :=   sql_select || 'WHERE ' || newLine;
        sql_select :=   sql_select || '   brew_app.prog_guide_file_name is not null ' || newLine;

        IF (v_program_guide_name is not null) THEN
        dbms_session.set_context( 'aims_prog_guide', 'pg_file_name', v_program_guide_name);
        sql_select :=   sql_select || '   and brew_app.prog_guide_file_name like sys_context(''aims_prog_guide'', ''pg_file_name'') ' || newLine;
        END IF;


        IF (v_nstl_comp_start_date is not null) THEN
        dbms_session.set_context( 'aims_prog_guide', 'nstl_comp_start_date', v_nstl_comp_start_date);
        sql_select := sql_select || '    and brew_app.planned_completion_by_nstl  >= to_date(sys_context(''aims_prog_guide'', ''nstl_comp_start_date'')) ' || newLine;
        END IF;

        IF (v_nstl_comp_end_date is not null) THEN
        dbms_session.set_context( 'aims_prog_guide', 'nstl_comp_end_date', v_nstl_comp_end_date);
        sql_select := sql_select || '    and brew_app.planned_completion_by_nstl  <= to_date(sys_context(''aims_prog_guide'', ''nstl_comp_end_date'')) ' || newLine;
        END IF;

        IF (length(v_filter_text) > 0) THEN
            IF (p_filter_field = 'company_name') THEN
                dbms_session.set_context( 'aims_prog_guide', 'company_name', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(a.company_name)  like sys_context(''aims_prog_guide'', ''company_name'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_title') THEN
                dbms_session.set_context( 'aims_prog_guide', 'app_title', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(app.title)  like sys_context(''aims_prog_guide'', ''app_title'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_category') THEN
                dbms_session.set_context( 'aims_prog_guide', 'app_category', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(c.category_name)  like sys_context(''aims_prog_guide'', ''app_category'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_sub_category') THEN
                dbms_session.set_context( 'aims_prog_guide', 'app_sub_category', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(sc.sub_category_name)  like sys_context(''aims_prog_guide'', ''app_sub_category'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_status') THEN
                dbms_session.set_context( 'aims_prog_guide', 'app_status', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(p.phase_name)  like sys_context(''aims_prog_guide'', ''app_status'') ' || newLine;
            END IF;
        END IF;
        sql_select :=   sql_select || '   and app.alliance_id = a.alliance_id ' || newLine;
        sql_select :=   sql_select || '   and app.apps_id = brew_app.brew_apps_id ' || newLine;
        sql_select :=   sql_select || '   and app.phase_id = p.phase_id ' || newLine;
        sql_select :=   sql_select || '   and sc.sub_category_id = app.sub_category_id ' || newLine;
        sql_select :=   sql_select || '   and c.category_id = sc.category_id ' || newLine;

        sql_order_by := sql_order_by || ' ORDER BY ' || v_sort_field || newLine;

        sql_seltwo :=   sql_seltwo || '         ) a ' || newLine;
        sql_seltwo :=   sql_seltwo || '         WHERE ' || newLine;
        sql_seltwo :=   sql_seltwo || '         ROWNUM <=  :1) b ' || newLine; -- to_rownum
        sql_seltwo :=   sql_seltwo || '         WHERE ' || newLine;
        sql_seltwo :=   sql_seltwo || '                     rnum >= :2 ' || newLine; -- from_rownum


        --INS_SQL(sql_countone || sql_select || sql_counttwo);
        --INS_SQL('sys_context: company_name --> ' || sys_context('aims_prog_guide', 'company_name'));
        --INS_SQL('Filter Text: ' || p_filter_text);
        --INS_SQL('Filter Field: ' || p_filter_field);
        --INS_SQL(sql_selone || sql_select || sql_order_by || sql_seltwo);


        EXECUTE IMMEDIATE sql_countone || sql_select || sql_counttwo INTO p_out_total_records;

        if (v_total_records < p_out_total_records) then
            p_out_total_records := v_total_records;
        end if;

        OPEN p_out_result FOR sql_selone || sql_select || sql_order_by || sql_seltwo USING to_rownum, from_rownum;


   END get_results_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_PROG_GUIDE_PKG; -- Package Body AIMS_PROG_GUIDE_PKG
/

