CREATE OR REPLACE PACKAGE BODY aims_notifications_pkg
IS
/* -------------------------------------------------------------------------------------------------------------  */
    FUNCTION get_replaced_message_body
         (
            p_message_body                  varchar2,
            p_properties_string             varchar2
         )
    RETURN varchar2
    IS
    /*
    || Overview:        Used as a helper package to replace the properties string with their values
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 06-08-2004       rqazi           Created
    || 03-23-2007       shiraz          Modified
    ||
    ||
    ||
    */
        v_properties_string     varchar2(4000);
        v_properties_array      DBMS_UTILITY.LNAME_ARRAY;
        v_cnt_properties        number;
        v_temp_string           varchar2(4000);
        v_temp_array            DBMS_UTILITY.LNAME_ARRAY;
        v_cnt_temp              number;
        v_empty_array           DBMS_UTILITY.LNAME_ARRAY;
        v_replaced_message_body varchar2(4000) := replace(p_message_body, '\n', chr(13) || chr(10));
    BEGIN
        v_properties_string := substr(p_properties_string, 2, LENGTH(p_properties_string)-2 );
        if (INSTR(v_properties_string,',') > 0) then
--            PARSE.delimstring_to_table(v_properties_string, v_properties_array, v_cnt_properties, ',');
/*  
            || If v_properties_string is greater than 227 characters, 
            || so delimlongstring_to_table procedure will be used 
            || it supports 4000 characters. 
            || delimstring_to_table is supports 227 characters,
            || so we cant use it in case of greater than 227 characters.
*/    
              PARSE.delimlongstring_to_table(v_properties_string, v_properties_array, v_cnt_properties, ',');
        elsif (LENGTH(v_properties_string) > 0) then
            v_properties_array(1) := v_properties_string;
            v_cnt_properties := 1;
        end if;
        if (v_cnt_properties > 0) then
            for i IN 1..v_properties_array.count loop
                if(length(trim(v_properties_array(i))) > 0) then
--                   PARSE.delimstring_to_table(trim(v_properties_array(i)), v_temp_array, v_cnt_temp, '=');
                   PARSE.delimlongstring_to_table(trim(v_properties_array(i)), v_temp_array, v_cnt_temp, '=');
                   v_replaced_message_body := replace(v_replaced_message_body, v_temp_array(1), v_temp_array(2));
                   v_replaced_message_body := replace(v_replaced_message_body, 'ASC#044', ',');
                   v_replaced_message_body := replace(v_replaced_message_body, 'ASC#061', '=');
                   --dbms_output.put_line('Value of v_temp_array(1)='||v_temp_array(1));
                   --dbms_output.put_line('Value of v_temp_array(2)='||v_temp_array(2));
                end if;
                v_temp_array := v_empty_array;
            end loop;
        end if;
        return v_replaced_message_body;
    END get_replaced_message_body;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE get_apps_notification_cursor
         (
            p_alliance_id               number,
            p_apps_id                   number,
            p_event_id                  number,
            p_properties_string         varchar2,
            p_user_ids_string           varchar2,               -- comma separated user ids string
            p_out_apps_notification OUT TYPES.cursor_type       -- Result cursor
         )
    IS
    /*
    || Overview:        Used by AIMS application for getting notification, users, message cursor.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 06-03-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */
        sql_select   varchar2(4000);
        newLine      varchar2(10) := chr(13) || chr(10);
        v_user_ids_string varchar2(2000) := p_user_ids_string;
        v_apps_id    number := p_apps_id;
        v_alliance_id    number := p_alliance_id;
        v_event_id   number := p_event_id;
   BEGIN
        if (v_user_ids_string is null) then
            v_user_ids_string := '0';
        else
            v_user_ids_string := '0, ' || v_user_ids_string;
        end if;
        if (v_alliance_id is null) then
            v_alliance_id := 0;
        end if;
        if (v_apps_id is null) then
            v_apps_id := 0;
        end if;
        if (v_event_id is null) then
            v_event_id := 0;
        end if;
        sql_select :=   sql_select || 'select '                                         || newLine;
        sql_select :=   sql_select || '    e.notification_id, '                         || newLine;
        sql_select :=   sql_select || '    m.email_title, '                             || newLine;
        sql_select :=   sql_select || '    AIMS_NOTIFICATIONS_PKG.get_replaced_message_body(m.email_text, :1), ' || newLine;
        sql_select :=   sql_select || '    m.email_message_id, '                             || newLine;
        sql_select :=   sql_select || '    cursor( '                                    || newLine;
        sql_select :=   sql_select || '            select  '                            || newLine;
        sql_select :=   sql_select || '                u.username email_addres, '       || newLine;
        sql_select :=   sql_select || '                nvl(u.notification_type, ''E'') notification_type ' || newLine;
        sql_select :=   sql_select || '            from '                               || newLine;
        sql_select :=   sql_select || '                aims_users u, '                  || newLine;
        sql_select :=   sql_select || '                aims_notification_roles nr, '    || newLine;
        sql_select :=   sql_select || '                aims_user_roles ur '             || newLine;
        sql_select :=   sql_select || '            where  '                             || newLine;
        sql_select :=   sql_select || '                u.alliance_id = :2 '               || newLine;
        sql_select :=   sql_select || '                and u.user_account_status = ''ACTIVE'' '        || newLine;
        sql_select :=   sql_select || '                and u.user_type = ''A'' '        || newLine;
        sql_select :=   sql_select || '                and nr.notification_id = e.notification_id ' || newLine;
        sql_select :=   sql_select || '                and nvl(u.notification_type, ''E'') = nvl(e.media, ''E'') ' || newLine;
        sql_select :=   sql_select || '                and u.user_id = ur.user_id '     || newLine;
        sql_select :=   sql_select || '                and ur.role_id = nr.role_id '    || newLine;
        sql_select :=   sql_select || '                and u.user_id not in ( '         || newLine;
        sql_select :=   sql_select || '                select user_id from aims_notif_opt_in_recipients '  || newLine;
        sql_select :=   sql_select || '                where notification_id = e.notification_id ) '  || newLine;
        sql_select :=   sql_select || '            union '                              || newLine;
        sql_select :=   sql_select || '            select  '                            || newLine;
        sql_select :=   sql_select || '                u.username email_addres, '       || newLine;
        sql_select :=   sql_select || '                nvl(u.notification_type, ''E'') notification_type ' || newLine;
        sql_select :=   sql_select || '            from '                               || newLine;
        sql_select :=   sql_select || '                aims_users u, '                  || newLine;
        sql_select :=   sql_select || '                aims_notification_roles nr, '    || newLine;
        sql_select :=   sql_select || '                aims_user_roles ur '             || newLine;
        sql_select :=   sql_select || '            where  '                             || newLine;
        sql_select :=   sql_select || '                u.user_account_status = ''ACTIVE'' '        || newLine;
        sql_select :=   sql_select || '                and u.user_type = ''V'' '        || newLine;
        sql_select :=   sql_select || '                and nr.notification_id = e.notification_id ' || newLine;
        sql_select :=   sql_select || '                and nvl(u.notification_type, ''E'') = nvl(e.media, ''E'') ' || newLine;
        sql_select :=   sql_select || '                and u.user_id = ur.user_id '     || newLine;
        sql_select :=   sql_select || '                and ur.role_id = nr.role_id '    || newLine;
        sql_select :=   sql_select || '                and u.user_id not in ( '         || newLine;
        sql_select :=   sql_select || '                select user_id from aims_notif_opt_in_recipients '  || newLine;
        sql_select :=   sql_select || '                where notification_id = e.notification_id ) '  || newLine;
           sql_select :=   sql_select || '            union '                              || newLine;
           sql_select :=   sql_select || '            select '                             || newLine;
           sql_select :=   sql_select || '                u.username email_address, '      || newLine;
           sql_select :=   sql_select || '                u.notification_type notification_type ' || newLine;
           sql_select :=   sql_select || '            from '                               || newLine;
           sql_select :=   sql_select || '                aims_users u, '                  || newLine;
           sql_select :=   sql_select || '                aims_apps a, '                   || newLine;
           sql_select :=   sql_select || '                aims_contacts c '               || newLine;
           sql_select :=   sql_select || '            where '                              || newLine;
           sql_select :=   sql_select || '                a.apps_id = :3 '                 || newLine;
           sql_select :=   sql_select || '                and u.user_account_status = ''ACTIVE'' '        || newLine;
           sql_select :=   sql_select || '                and u.user_type = ''V'' '        || newLine;
           sql_select :=   sql_select || '                and a.vzw_apps_contact_id = c.contact_id ' || newLine;
           sql_select :=   sql_select || '                and u.contact_id = c.contact_id '|| newLine;
           sql_select :=   sql_select || '                and e.include_vzw_apps_contact = ''Y'' ' || newLine;
           sql_select :=   sql_select || '                and u.user_id not in ( '         || newLine;
           sql_select :=   sql_select || '                select user_id from aims_notif_opt_in_recipients '  || newLine;
           sql_select :=   sql_select || '                where notification_id = e.notification_id ) '  || newLine;
        sql_select :=   sql_select || '            union '                              || newLine;
        sql_select :=   sql_select || '            select '                             || newLine;
        sql_select :=   sql_select || '                u.username email_address, '      || newLine;
        sql_select :=   sql_select || '                u.notification_type notification_type ' || newLine;
        sql_select :=   sql_select || '            from '                               || newLine;
        sql_select :=   sql_select || '                aims_users u, '                  || newLine;
        sql_select :=   sql_select || '                aims_alliances alnc '           || newLine;
        sql_select :=   sql_select || '            where '                              || newLine;
        sql_select :=   sql_select || '                alnc.alliance_id = :4 '                 || newLine;
        sql_select :=   sql_select || '                and u.user_account_status = ''ACTIVE'' '        || newLine;
        sql_select :=   sql_select || '                and u.user_type = ''V'' '        || newLine;
        sql_select :=   sql_select || '                and alnc.vzw_account_manager = u.user_id ' || newLine;
        sql_select :=   sql_select || '                and e.include_vzw_account_manager = ''Y'' ' || newLine;
        sql_select :=   sql_select || '                and u.user_id not in ( '         || newLine;
        sql_select :=   sql_select || '                select user_id from aims_notif_opt_in_recipients '  || newLine;
        sql_select :=   sql_select || '                where notification_id = e.notification_id ) '  || newLine;
        sql_select :=   sql_select || '            union '                              || newLine;
        sql_select :=   sql_select || '            select '                             || newLine;
        sql_select :=   sql_select || '                u.username email_address, '      || newLine;
        sql_select :=   sql_select || '                u.notification_type notification_type ' || newLine;
        sql_select :=   sql_select || '            from '                               || newLine;
        sql_select :=   sql_select || '                aims_users u '                   || newLine;
        sql_select :=   sql_select || '            where '                              || newLine;
        sql_select :=   sql_select || '                u.user_account_status = ''ACTIVE'' '        || newLine;
        sql_select :=   sql_select || '                and u.user_id IN  '                 || newLine;
        sql_select :=   sql_select || '                (select * from TABLE(cast(in_list(:5) as inListTableType))  ) ' || newLine;
        sql_select :=   sql_select || '            union '                              || newLine;
        sql_select :=   sql_select || '            select '                             || newLine;
        sql_select :=   sql_select || '                a.email_address email_address, ' || newLine;
        sql_select :=   sql_select || '                ''E'' notification_type '    || newLine;
        sql_select :=   sql_select || '            from '                               || newLine;
        sql_select :=   sql_select || '                aims_notif_ad_hoc_recipients a ' || newLine;
        sql_select :=   sql_select || '            where '                              || newLine;
        sql_select :=   sql_select || '                a.notification_id = e.notification_id ' || newLine;
        sql_select :=   sql_select || '          ) '                                    || newLine;
        sql_select :=   sql_select || 'from '                                           || newLine;
        sql_select :=   sql_select || '    aims_event_notifications e, '                || newLine;
        sql_select :=   sql_select || '    aims_email_messages m '                      || newLine;
        sql_select :=   sql_select || 'where '                                          || newLine;
        sql_select :=   sql_select || '    e.event_id = :6 '                            || newLine;
        sql_select :=   sql_select || '    and e.message_id = m.email_message_id '      || newLine;
        --ins_sql(sql_select);
        OPEN p_out_apps_notification FOR sql_select USING   p_properties_string, v_alliance_id,
                                                            v_apps_id, v_alliance_id,
                                                            v_user_ids_string, v_event_id;
   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_apps_notification%ISOPEN) THEN
            CLOSE p_out_apps_notification;
          END IF;
          RAISE;
   END get_apps_notification_cursor;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE insert_messages
         (
            p_email_address  varchar2,
            p_message_title  varchar2,
            p_message_body   varchar2,
            p_from_id        varchar2,
            p_email_message_id varchar2
         )
    IS
    /*
    || Overview:        Used by AIMS application for getting notification, users, message cursor.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 06-03-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */
        v_message_id   number;
   BEGIN
          insert into aims_messages
             (
                message_id,
                subject,
                message_text,
                from_id,
                status,
                created_date
             )
           values
            (
                seq_pk_messages.nextval,
                p_message_title,
                p_message_body,
                p_from_id,
                'N',
                sysdate
            )
            returning message_id into v_message_id;
            INSERT INTO AIMS_MESSAGE_ATT
                   (MESSAGE_ATT_ID,
                       MESSAGE_ID,
                    FILE_STREAM,
                    FILE_NAME,
                    FILE_CONTENT_TYPE)
            SELECT
                   SEQ_PK_MESSAGE_ATT.nextval,
                   v_message_id,
                   att,
                   att_file_name,
                   att_content_type
            FROM aims_email_messages_att
                   WHERE email_message_id = p_email_message_id;
            insert into aims_messages_recpts_grps
               (
                  message_id,
                  recipient_id,
                  group_id,
                  status
               )
              select distinct
                  v_message_id,
                  u.contact_id,
                  null,
                  'N'
              from
                  aims_users u
              where
                  u.username = p_email_address;
   END insert_messages;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE get_email_message_attachment
         (
            p_email_message_id           number,
            p_out_attachments_cursor OUT TYPES.cursor_type       -- Result cursor
         )
    IS
    /*
    || Overview:        Used by AIMS application for getting email messages attachment cursor
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 06-17-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */
   BEGIN
        OPEN p_out_attachments_cursor FOR
            select
                att,
                att_file_name,
                att_content_type
            from
                aims_email_messages_att
            where
                email_message_id = p_email_message_id;
   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_attachments_cursor%ISOPEN) THEN
            CLOSE p_out_attachments_cursor;
          END IF;
          RAISE;
   END get_email_message_attachment;
/* -------------------------------------------------------------------------------------------------------------  */
END AIMS_NOTIFICATIONS_PKG; -- Package Body
/
