CREATE OR REPLACE PACKAGE BODY Aims_Apps_List_Pkg
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_results_cursor
         (
            p_app_title                     IN VARCHAR2,            -- full/partial title of application
            p_platform_id                   IN NUMBER,              -- platform id of the application
            p_phase_id                      IN NUMBER,              -- phase id of the application
            p_category_id                   IN NUMBER,              -- category id of the application
            p_sub_category_id               IN NUMBER,              -- sub category id of the application
            p_selected_devices              IN VARCHAR2,            -- comma seperated list of device ids
            p_brew_app_type                 IN VARCHAR2,            -- New VZW App, Concept, etc.
            p_permitted_platforms           IN VARCHAR2,            -- comma seperated list permitted platform ids
            p_alliance_id                   IN NUMBER,              -- alliance id of the application user
            p_user_id                       IN NUMBER,              -- user id of the application user
            p_contact_id                    IN NUMBER,              -- vzw contact id of the application
            p_query_type                    IN VARCHAR2,            -- for egs. NEW BREW APP, SAVED APPS, SUBMITTED APPS, etc
            p_page_no_needed                IN NUMBER,              -- page number requested
            p_num_per_page                  IN NUMBER,              -- number of records per page
            p_filter_text                   IN VARCHAR2,            -- filtering text
            p_filter_field                  IN VARCHAR2,            -- filtering field
            p_sort_field                    IN VARCHAR2,            -- sort field
            p_user_type                     IN VARCHAR2,            -- type of user
        p_filter_platforms              IN varchar2,            -- comma seperated list of selected platforms
            p_filter_statuses               IN varchar2,            -- comma seperated list of selected statuses
            p_sort_order                    IN varchar2,            -- sorting order either asc or desc
            p_out_total_records            OUT NUMBER,              -- total number of records in selection set
            p_out_result                   OUT Types.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Gives the cursor for the APPS which match the search criteria for applications.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 10-12-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        sql_select         VARCHAR2(30000);
        sql_selone         VARCHAR2(30000);
        sql_seltwo         VARCHAR2(30000);
        sql_countone       VARCHAR2(50) := 'SELECT COUNT(*) FROM ( ';
        sql_counttwo       VARCHAR2(50) := ') ';
        sql_order_by       VARCHAR2(100);

        from_rownum        NUMBER;
        to_rownum          NUMBER;
        v_page_no_needed   NUMBER := p_page_no_needed;


        v_app_title        aims_apps.title%TYPE := trim(p_app_title);
        v_platform_id      aims_apps.platform_id%TYPE := p_platform_id;
        v_category_id      aims_app_categories.category_id%TYPE := p_category_id;
        v_sub_category_id  aims_app_sub_categories.sub_category_id%TYPE := p_sub_category_id;
        v_phase_id         aims_apps.phase_id%TYPE := p_phase_id;
        v_brew_app_type    VARCHAR2(200) := trim(p_brew_app_type);
        v_filter_statuses  VARCHAR2(250);
        v_filter_text      VARCHAR2(200) := trim(UPPER(p_filter_text));
        v_sort_field       VARCHAR2(50);

        v_selected_devices_array     DBMS_UTILITY.UNCL_ARRAY;
        v_permitted_platforms_array  DBMS_UTILITY.UNCL_ARRAY;

        newLine      VARCHAR2(10) := CHR(13) || CHR(10);
   BEGIN

        Ins_Sql('p_filter_text --> ' || p_filter_text);
        Ins_Sql('p_filter_field --> ' || p_filter_field);
        Ins_Sql('p_alliance_id --> ' || p_alliance_id);
        Ins_Sql('p_platform_id --> ' || p_platform_id);
        Ins_Sql('p_phase_id --> ' || p_phase_id);
        Ins_Sql('p_user_type --> ' || p_user_type);
        Ins_Sql('p_sort_field --> ' || p_sort_field);
        Ins_Sql('p_brew_app_type --> ' || p_brew_app_type);
        Ins_Sql('p_page_no_needed --> ' || p_page_no_needed);
        Ins_Sql('p_num_per_page --> ' || p_num_per_page);




        IF (LENGTH(v_app_title) > 1 ) THEN
            v_app_title := '%' || String_Utils.SQL_ESCAPE(UPPER(v_app_title)) || '%';
        END IF;

        IF (LENGTH(v_brew_app_type) > 1 ) THEN
            v_brew_app_type := '%' || String_Utils.SQL_ESCAPE(UPPER(v_brew_app_type)) || '%';
        END IF;

        /* Start Edit by Adnan Ahmed */
        IF (p_sort_field is null) THEN
            v_sort_field := 'p.platform_name';
        ELSE
            v_sort_field := p_sort_field;
        END IF;

        /* End Edit by Adnan Ahmed */

        Ins_Sql('v_sort_field --> ' || v_sort_field);

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
        sql_select :=   sql_select || '   p.platform_name, ' || newLine;
        sql_select :=   sql_select || '   app.title, ' || newLine;
        sql_select :=   sql_select || '   app.version, ' || newLine;
        sql_select :=   sql_select || '   c.category_name, ' || newLine;
        sql_select :=   sql_select || '   app.submitted_date, ' || newLine;
        sql_select :=   sql_select || '   lp.phase_id, ' || newLine;


        IF (p_user_type = 'A') THEN
        sql_select :=   sql_select || '   decode(lp.phase_name,''INITIAL APPROVAL'', ''SUBMITTED'',     ''BUSINESS APPROVAL GRANTED'', ''SUBMITTED'', '     || newLine;
        sql_select :=   sql_select || '                        ''PENDING DCR'',      ''SUBMITTED'',     ''PENDING ARM'',               ''SUBMITTED'', '     || newLine;
        sql_select :=   sql_select || '                        ''TESTING PASSED'',   ''SUBMITTED DCR'', ' || newLine;
        sql_select :=   sql_select || '                        ''TESTING FAILED'',   ''SUBMITTED DCR'', ''PUBLICATION READY'',         ''SUBMITTED DCR'', ' || newLine;
        sql_select :=   sql_select || '          lp.phase_name), ' || newLine;
        ELSE
        sql_select :=   sql_select || '   lp.phase_name, ' || newLine;
        END IF;

        sql_select :=   sql_select || '   p.platform_id, ' || newLine;
        sql_select :=   sql_select || '   app.if_on_hold, ' || newLine;
        sql_select :=   sql_select || '   a.alliance_id, ' || newLine;
        sql_select :=   sql_select || '   decode(ba.app_type, ''N'', ''New VZW App'', ''E'', ''Existing VZW App'', ''C'', ''Concept'', ba.app_type), ' || newLine;
        sql_select :=   sql_select || '   ba.is_lbs ' || newLine;
        sql_select :=   sql_select || 'FROM ' || newLine;
        sql_select :=   sql_select || '   aims_alliances a, ' || newLine;
        sql_select :=   sql_select || '   aims_apps app, ' || newLine;
        sql_select :=   sql_select || '   aims_brew_apps ba, ' || newLine;
        sql_select :=   sql_select || '   aims_lifecycle_phases lp, ' || newLine;
        sql_select :=   sql_select || '   aims_app_categories c, ' || newLine;
        sql_select :=   sql_select || '   aims_app_sub_categories sc, ' || newLine;
        sql_select :=   sql_select || '   aims_platforms p, ' || newLine;
        sql_select :=   sql_select || '   aims_brew_apps_devices bad, ' || newLine;
        sql_select :=   sql_select || '   aims_ent_apps_sub_categories easc ' || newLine;
        sql_select :=   sql_select || 'WHERE ' || newLine;
        sql_select :=   sql_select || '   1 = 1 ' || newLine;


        --Special conditions for VZW
        IF (p_user_type = 'V') THEN
            sql_select :=   sql_select || '  and lp.phase_id != 8  ' || newLine; --8 is 'SAVED' Phase
            IF (LENGTH(p_query_type) > 0) THEN
                IF (p_query_type != 'archive_apps') THEN
                    sql_select :=   sql_select || '   and app.submitted_date >= ''1-Jan-2006'' '|| newLine;
                END IF;
            END IF;
        END IF;

        IF (v_app_title IS NOT NULL) THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_app_title', v_app_title);
            sql_select :=   sql_select || '   and UPPER(app.title) like sys_context(''apps_list_pkg'', ''p_app_title'') ' || newLine;
        END IF;

        IF (v_platform_id > 0) THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_platform_id', v_platform_id);
            sql_select := sql_select || '    and app.platform_id  = to_number(sys_context(''apps_list_pkg'', ''p_platform_id'')) ' || newLine;
        END IF;

        IF (v_category_id > 0) THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_category_id', v_category_id);
            IF (v_platform_id = 5) THEN --If Platform selected is Enterprise
                sql_select := sql_select || '    and c.category_id  = to_number(sys_context(''apps_list_pkg'', ''p_category_id'')) ' || newLine;
            ELSE
                sql_select := sql_select || '    and c.category_id  = to_number(sys_context(''apps_list_pkg'', ''p_category_id'')) ' || newLine;
            END IF;
        END IF;

        IF (v_sub_category_id > 0) THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_sub_category_id', v_sub_category_id);
            IF (v_platform_id = 5) THEN --If Platform selected is Enterprise
                sql_select := sql_select || '    and easc.sub_category_id  = to_number(sys_context(''apps_list_pkg'', ''p_sub_category_id'')) ' || newLine;
            ELSE
                sql_select := sql_select || '    and app.sub_category_id  = to_number(sys_context(''apps_list_pkg'', ''p_sub_category_id'')) ' || newLine;
            END IF;
        END IF;

        IF (p_alliance_id > 0) THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_alliance_id', p_alliance_id);
            sql_select := sql_select || '    and app.alliance_id  = to_number(sys_context(''apps_list_pkg'', ''p_alliance_id'')) ' || newLine;
        END IF;

        IF (LENGTH(p_selected_devices)> 0) THEN
            sql_select :=   sql_select || '   and bad.device_id IN ( ' || p_selected_devices || ' )  ' || newLine;
        END IF;

        IF (LENGTH(p_permitted_platforms)> 0) THEN
            sql_select :=   sql_select || '   and p.platform_id IN ( ' || p_permitted_platforms || ' )  ' || newLine;
        END IF;

        IF (p_phase_id > 0) THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_phase_id', v_phase_id);
            IF (p_user_type = 'A') THEN
                sql_select := sql_select || '    and decode(lp.phase_id, 14,1,16,1,18,1,19,1,21,20,22,20,23,20, lp.phase_id) = to_number(sys_context(''apps_list_pkg'', ''p_phase_id'')) ' || newLine;
            ELSIF (p_user_type = 'V') THEN
                sql_select := sql_select || '    and lp.phase_id = to_number(sys_context(''apps_list_pkg'', ''p_phase_id'')) ' || newLine;
            END IF;
        END IF;


        IF (LENGTH(p_query_type) > 0) THEN
            IF (p_query_type = 'my_apps') THEN
            dbms_session.set_context( 'apps_list_pkg', 'p_user_id', p_user_id);
            dbms_session.set_context( 'apps_list_pkg', 'p_contact_id', p_contact_id);
                IF (p_user_type = 'A') THEN
                sql_select :=   sql_select || '  and app.app_created_by = to_number(sys_context(''apps_list_pkg'', ''p_user_id'')) ' || newLine;
                ELSIF (p_user_type = 'V') THEN
                sql_select :=   sql_select || '  and app.vzw_apps_contact_id = to_number(sys_context(''apps_list_pkg'', ''p_contact_id'')) ' || newLine;
                END IF;
            ELSIF (p_query_type = 'saved_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 8  ' || newLine; --8 is 'SAVED' Phase
            ELSIF (p_query_type = 'new_apps') THEN
                IF (p_user_type = 'A') THEN
                sql_select :=   sql_select || '  and decode(app.phase_id, 14,1,16,1,18,1,19,1,21,20,22,20,23,20, app.phase_id) = 1  ' || newLine; --1 is 'SUBMITTED' Phase
                ELSIF (p_user_type = 'V') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine; --1 is 'SUBMITTED' Phase
                END IF;
            ELSIF (p_query_type = 'archive_apps') THEN
                sql_select :=   sql_select || '   and app.submitted_date between ''1-Jan-2002'' and ''31-Dec-2005'' '|| newLine;
            ELSIF (p_query_type = 'new_brew_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine;
                sql_select :=   sql_select || '  and p.platform_id = 1  ' || newLine; --1 is 'BREW' Platform
                sql_select :=   sql_select || '  and ba.app_type != ''C''  ' || newLine; --C is 'Concept' Brew App Type
            ELSIF (p_query_type = 'new_ent_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine;
                sql_select :=   sql_select || '  and p.platform_id = 5  ' || newLine; --5 is 'ENTERPRISE' Platform
            ELSIF (p_query_type = 'new_mms_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine;
                sql_select :=   sql_select || '  and p.platform_id = 3  ' || newLine; --3 is 'MMS' Platform
            ELSIF (p_query_type = 'new_sms_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine;
                sql_select :=   sql_select || '  and p.platform_id = 2  ' || newLine; --2 is 'SMS' Platform
            ELSIF (p_query_type = 'new_vcast_video_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine;
                sql_select :=   sql_select || '  and p.platform_id = 6  ' || newLine; --6 is 'VCAST Video' Platform
            ELSIF (p_query_type = 'new_wap_apps') THEN
                IF (p_user_type = 'A') THEN
                    sql_select :=   sql_select || '  and decode(app.phase_id, 14,1,16,1,18,1,19,1,21,20,22,20,23,20, app.phase_id) = 1  ' || newLine; --1 is 'SUBMITTED' Phase
                ELSIF (p_user_type = 'V') THEN
                    sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine; --1 is 'SUBMITTED' Phase
                END IF;
                sql_select :=   sql_select || '  and p.platform_id = 4  ' || newLine; --4 is 'WAP' Platform
            ELSIF (p_query_type = 'new_vzappzone_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 1  ' || newLine;
                sql_select :=   sql_select || '  and p.platform_id = 42  ' || newLine; --42 is 'VZAPPZONE' Platform
            ELSIF (p_query_type = 'test_apps') THEN
                sql_select :=   sql_select || '  and app.phase_id = 6  ' || newLine; --6 is 'UNDER TESTING' Phase
            END IF;
        END IF;


        IF (LENGTH(v_filter_text) > 0) THEN
            IF (p_filter_field = 'company_name') THEN
                dbms_session.set_context( 'apps_list_pkg', 'company_name', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(a.company_name)  like sys_context(''apps_list_pkg'', ''company_name'') ' || newLine;
            END IF;
            IF (p_filter_field = 'app_title') THEN
                dbms_session.set_context( 'apps_list_pkg', 'app_title', '%' || v_filter_text || '%');
                sql_select := sql_select || '    and upper(app.title)  like sys_context(''apps_list_pkg'', ''app_title'') ' || newLine;
            END IF;
        END IF;



        sql_select :=   sql_select || '   and app.alliance_id = a.alliance_id ' || newLine;
        sql_select :=   sql_select || '   and app.platform_id = p.platform_id ' || newLine;
        sql_select :=   sql_select || '   and app.phase_id = lp.phase_id ' || newLine;
        IF (v_platform_id = 5) THEN --If Platform selected is Enterprise
        sql_select :=   sql_select || '   and sc.sub_category_id = easc.sub_category_id ' || newLine;
        ELSE
        sql_select :=   sql_select || '   and app.sub_category_id = sc.sub_category_id (+) ' || newLine;
        END IF;
        sql_select :=   sql_select || '   and sc.category_id = c.category_id (+) ' || newLine;
        sql_select :=   sql_select || '   and app.apps_id = ba.brew_apps_id (+) ' || newLine;
        sql_select :=   sql_select || '   and app.apps_id = easc.enterprise_apps_id (+) ' || newLine;
        sql_select :=   sql_select || '   and app.apps_id = bad.brew_apps_id (+) ' || newLine;

        --Start changed by adnan ahmed
        IF (p_filter_platforms is not null) THEN
           sql_select :=   sql_select || '   and app.platform_id in ('|| p_filter_platforms || ')' || newLine;
        END IF;

        IF (p_filter_statuses is not null) THEN
           v_filter_statuses := p_filter_statuses;
           IF (p_user_type = 'A') THEN            
                v_filter_statuses := v_filter_statuses || ',';
                IF (instr(v_filter_statuses,'1,')>0) THEN
                   v_filter_statuses := v_filter_statuses || '14,16,18,19,';
                END IF;
                IF (instr(v_filter_statuses,'20,')>0) THEN
                   v_filter_statuses := v_filter_statuses || '21,22,23,';
                END IF;
                v_filter_statuses := substr(v_filter_statuses,1,length(v_filter_statuses)-1);                
            END IF;
           sql_select :=   sql_select || '   and app.phase_id in ('|| v_filter_statuses || ')' || newLine;
        END IF;

        IF (instr(lower(v_sort_field),'date') > 0 ) THEN
           sql_order_by := sql_order_by || ' ORDER BY ' || v_sort_field || ' ' || p_sort_order || newLine;
        ELSIF (instr(lower(v_sort_field),'lp.phase_name') > 0 ) THEN -- AM: Hack for Status Sort for Alliance
           sql_order_by := sql_order_by || ' ORDER BY 9 ' || ' ' || p_sort_order || newLine;
        ELSE
           sql_order_by := sql_order_by || ' ORDER BY lower(' || v_sort_field || ') ' || p_sort_order || newLine;
        END IF;

        --End changed by adnan ahmed

        sql_seltwo :=   sql_seltwo || '         ) a ' || newLine;
        sql_seltwo :=   sql_seltwo || '              ) b ' || newLine; 
        sql_seltwo :=   sql_seltwo || '                    WHERE ' || newLine;
        sql_seltwo :=   sql_seltwo || '                           rnum between :1 and :2 ' || newLine; -- between from_rownum and to_rownum


        Ins_Sql(sql_countone || sql_select || sql_counttwo);
        Ins_Sql('Filter Text: ' || p_filter_text);
        Ins_Sql('Filter Field: ' || p_filter_field);
        INS_SQL('Filter Statuses: ' || p_filter_statuses);
        INS_SQL('Filter Platforms: ' || p_filter_platforms);
        Ins_Sql(sql_selone || sql_select || sql_order_by || sql_seltwo);


        EXECUTE IMMEDIATE sql_countone || sql_select || sql_counttwo INTO p_out_total_records;

        IF (v_page_no_needed < 1) THEN
            v_page_no_needed := 1;
        ELSIF (v_page_no_needed > CEIL(p_out_total_records / p_num_per_page)) THEN
            v_page_no_needed := CEIL(p_out_total_records / p_num_per_page);
        END IF;

        from_rownum := ((v_page_no_needed - 1) * p_num_per_page) + 1;
        to_rownum   := from_rownum -1 + p_num_per_page;
                
        OPEN p_out_result FOR sql_selone || sql_select || sql_order_by || sql_seltwo USING from_rownum, to_rownum;


   END get_results_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Apps_List_Pkg; -- Package Body AIMS_APPS_LIST_PKG
/
