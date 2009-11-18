CREATE OR REPLACE Package Body      AIMS_PROV_APPS_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE get_results_cursor
         (
            p_user_id                       IN number,              -- user id of the person searching the records  
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
        v_num_per_page     number := p_num_per_page;
        v_total_records    number;

        v_prov_app_name   varchar2(200);
        v_filter_text          varchar2(200) := trim(upper(p_filter_text));
        v_sort_field           varchar2(10);

        newLine      varchar2(10) := chr(13) || chr(10);
   BEGIN

        IF (v_total_records is null or v_total_records <= 0) then
            v_total_records := 10;
        END IF;

        IF (length(trim(p_sort_field)) > 0 ) THEN
            v_sort_field := trim(p_sort_field);
        ELSE
            v_sort_field := '2';
        END IF;

        from_rownum := ((p_page_no_needed - 1) * p_num_per_page) + 1;
        to_rownum   := from_rownum -1 + p_num_per_page;

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
        sql_select :=   sql_select || '   (select platform_name from aims_platforms where platform_id = app.platform_id) platform_name, ';
        sql_select :=   sql_select || '   app.submitted_date, ' || newLine; 
        sql_select :=   sql_select || '   c.category_name, ' || newLine;
        sql_select :=   sql_select || '   sc.sub_category_name ' || newLine;     
        sql_select :=   sql_select || 'FROM ' || newLine;
        sql_select :=   sql_select || '   aims_alliances a, ' || newLine;
        sql_select :=   sql_select || '   aims_apps app, ' || newLine;
        sql_select :=   sql_select || '   aims_app_categories c, ' || newLine;
        sql_select :=   sql_select || '   aims_app_sub_categories sc, ' || newLine;
        sql_select :=   sql_select || '   aims_brew_apps ba, ' || newLine;
        sql_select :=   sql_select || '   aims_sys_roles r, ' || newLine;
        sql_select :=   sql_select || '   aims_user_roles ur, ' || newLine; 
        sql_select :=   sql_select || '   aims_prov_apps_roles par ' || newLine;
        sql_select :=   sql_select || 'WHERE ' || newLine;
        sql_select :=   sql_select || '   ur.user_id = :1 ' || newLine;
        sql_select :=   sql_select || '   and ba.provision = ''Y'' ' || newLine;        
        sql_select :=   sql_select || '   and ba.prov_exp_date >= sysdate ' || newLine;
        sql_select :=   sql_select || '   and ba.brew_apps_id = app.apps_id ' || newLine;
        sql_select :=   sql_select || '   and par.apps_id = app.apps_id ' || newLine;
        sql_select :=   sql_select || '   and par.role_id = r.role_id ' || newLine;
        sql_select :=   sql_select || '   and ur.role_id = r.role_id ' || newLine;        
        sql_select :=   sql_select || '   and app.alliance_id = a.alliance_id ' || newLine;
        sql_select :=   sql_select || '   and sc.sub_category_id = app.sub_category_id ' || newLine;
        sql_select :=   sql_select || '   and c.category_id = sc.category_id ' || newLine;        


        
        
        IF (length(v_filter_text) > 0) THEN
            IF (p_filter_field = 'company_name') THEN
                dbms_session.set_context( 'aims_prov_app', 'company_name', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(a.company_name)  like sys_context(''aims_prov_app'', ''company_name'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_title') THEN
                dbms_session.set_context( 'aims_prov_app', 'app_title', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(app.title)  like sys_context(''aims_prov_app'', ''app_title'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_category') THEN
                dbms_session.set_context( 'aims_prov_app', 'app_category', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(c.category_name)  like sys_context(''aims_prov_app'', ''app_category'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_sub_category') THEN
                dbms_session.set_context( 'aims_prov_app', 'app_sub_category', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(sc.sub_category_name)  like sys_context(''aims_prov_app'', ''app_sub_category'') ' || newLine;
            END IF;

        END IF;

        
        --INS_SQL('sql_select Text: ' || sql_select);

        sql_order_by := sql_order_by || ' ORDER BY ' || v_sort_field || newLine;

        sql_seltwo :=   sql_seltwo || '         ) a ' || newLine;
        sql_seltwo :=   sql_seltwo || '         WHERE ' || newLine;
        sql_seltwo :=   sql_seltwo || '         ROWNUM <=  :2) b ' || newLine; -- to_rownum
        sql_seltwo :=   sql_seltwo || '         WHERE ' || newLine;
        sql_seltwo :=   sql_seltwo || '                     rnum >= :3 ' || newLine; -- from_rownum

        /*
        INS_SQL(sql_countone || sql_select || sql_counttwo);
        INS_SQL('sys_context: company_name --> ' || sys_context('aims_prog_guide', 'company_name'));
        INS_SQL('Sort Field: ' || p_sort_field);
        INS_SQL('Filter Text: ' || p_filter_text);
        INS_SQL('Filter Field: ' || p_filter_field);
        INS_SQL(sql_selone || sql_select || sql_order_by || sql_seltwo);
        */

        EXECUTE IMMEDIATE sql_countone || sql_select || sql_counttwo INTO p_out_total_records USING p_user_id;
        
        /*
        if (v_total_records < p_out_total_records) then
            p_out_total_records := v_total_records;
        end if;
        */

        OPEN p_out_result FOR sql_selone || sql_select || sql_order_by || sql_seltwo USING p_user_id,
                                                                                            to_rownum, from_rownum;


   END get_results_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_PROV_APPS_PKG; -- Package Body AIMS_PROV_APPS_PKG
/

