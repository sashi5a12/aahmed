CREATE OR REPLACE Package Body      AIMS_ALLIANCES_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance
         ( p_alliance_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_curr_user_name         IN  varchar2           -- user name of the person deleting the alliance
          )
    IS

    /*
    || Overview:        Deletes a given ALLIANCE from the database
    ||
    ||
    || Dependencies:
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

        v_alliance_user_ids     varchar2(32767);
        v_alliance_contact_ids  varchar2(32767);
        v_t_user_ids            dbms_utility.uncl_array;
        v_t_contact_ids         dbms_utility.uncl_array;

   BEGIN

        /* Delete alliance related tables */
        AIMS_ALLIANCES_PKG_UTILS.delete_alliance_relations(p_alliance_id, p_curr_user_name);

        /* Delete tables related to applications submitted by the given alliance */
        AIMS_ALLIANCES_PKG_UTILS.delete_alliance_app_relations(p_alliance_id, p_curr_user_name);

        /* Delete tables related to users belonging the given alliance */
        AIMS_ALLIANCES_PKG_UTILS.delete_alliance_user_relations(p_alliance_id, p_curr_user_name);

        /* Get the comma seperated user_id string of users for the current alliance */
        AIMS_ALLIANCES_PKG_UTILS.get_alliance_user_ids(p_alliance_id, v_t_user_ids);

        /* Get the comma seperated contact_id string of users for the current alliance */
        AIMS_ALLIANCES_PKG_UTILS.get_alliance_contact_ids(p_alliance_id, v_t_contact_ids);

        /* Delete the record from aims_apps corresponding to the current alliance  */
        delete
        from
          aims_apps
        where
          alliance_id = p_alliance_id;


        /* Update alliance_id to null in the aims_users table corresponding to the current alliance */
        update
            aims_users
        set
            alliance_id = null
        where
            alliance_id = p_alliance_id;


        /* Update the user and contact fields from aims_alliances corresponding to the current alliance */

        /* Delete the current alliance */
        delete
        from
          aims_alliances
        where
          alliance_id = p_alliance_id;

        dbms_output.put_line('Value of v_alliance_user_ids='||v_alliance_user_ids);
        dbms_output.put_line('Value of v_alliance_contact_ids='||v_alliance_contact_ids);


        /* Nov 30, 2004 rqazi -- Users and Contacts are deleted individually  */
        if (v_t_user_ids.count > 0) then
            for i IN 1..v_t_user_ids.count loop
                begin
                    delete from aims_users where user_id = v_t_user_ids(i);
                exception
                    when others then
                        null;
                end;
            end loop;
        end if;

        if (v_t_contact_ids.count > 0) then
            for i IN 1..v_t_contact_ids.count loop
                begin
                    delete from aims_contacts where contact_id = v_t_contact_ids(i);
                exception
                    when others then
                        null;
                end;
            end loop;
        end if;

        -- Log the user who deleted the alliance
        insert into aims_alliance_delete_log (alliance_id, aud_user, aud_timestamp)
        values(p_alliance_id, p_curr_user_name, sysdate);

        /* Delete all users corresponding to the user_ids string generated earlier */
       -- execute immediate 'delete from aims_users where user_id in (' || v_alliance_user_ids || ')';



        /* Delete all contacts corresponding to the contact_ids string generated earlier */
        --execute immediate 'delete from aims_contacts where contact_id in (' || v_alliance_contact_ids || ')';




   END delete_alliance;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_uploaded_files
         (
            p_alliance_id            IN  number,          -- alliance id
            p_out_result            OUT  varchar2         -- '' if no problems, comma seperated list of files if problem
          )

    IS

    /*
    || Overview:        Checks if words are from filter words table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 03-18-2004       rqazi           Created
    ||
    ||
    ||
    ||

COMPANY_LOGO
COMPANY_PRESENTATION

COMPANY_LOGO_FILE_NAME
COMPANY_PRESENT_FILE_NAME

    */


        v_out_result varchar2(1):= 'N';
        v_out_filter_words varchar2(32767);
        v_temp_filter_word varchar2(200);
        v_file_name varchar2(200);
        v_temp_result varchar2(1);
        v_cnt number;
        v_file_names_array DBMS_UTILITY.UNCL_ARRAY;

   BEGIN
        v_cnt := 0;
        p_out_result := 'N';

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('COMPANY_LOGO', 'COMPANY_LOGO_FILE_NAME', 'AIMS_ALLIANCES', 'ALLIANCE_ID', p_alliance_id, v_temp_result);
        if (v_temp_result = 'Y') then
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Company Logo';
        end if;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('COMPANY_PRESENT', 'COMPANY_PRESENT_FILE_NAME', 'AIMS_ALLIANCES', 'ALLIANCE_ID', p_alliance_id, v_temp_result);
        if (v_temp_result = 'Y') then
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Company Presentation';
        end if;

        if (v_file_names_array.COUNT > 0) then
            PARSE.table_to_delimstring(v_file_names_array, p_out_result, ', ');
        else
            p_out_result := '';
        end if;

   END check_uploaded_files;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE generate_vendor_id
         ( p_alliance_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_vendor_id             out  number           -- user name of the person deleting the alliance
          )
      IS


   BEGIN

        select SEQ_VENDOR_IDS.nextval into p_vendor_id from dual;

        update aims_alliances set vendor_id = p_vendor_id where alliance_id = p_alliance_id;

   END generate_vendor_id;


/* -------------------------------------------------------------------------------------------------------------  */
END AIMS_ALLIANCES_PKG; -- Package Body AIMS_ALLIANCES_PKG
/

