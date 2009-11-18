CREATE OR REPLACE Package aims_notifications_pkg
  IS

/*
|| All contents are Copyright 2004 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for getting notification, users, message cursor.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 06-03-2004       rqazi           Created
|| 07-05-2004       aimtiaz		   	Modified for attachments in insert_messages
|| 08-03-2009       sraza			Modified for alliance_id and apps_id in insert_messages
||
||
||
*/
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_email_message_attachment
         (
            p_email_message_id           number,
            p_out_attachments_cursor OUT TYPES.cursor_type       -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_messages
         (
            p_email_address  varchar2,
            p_message_title  varchar2,
            p_message_body   varchar2,
            p_from_id        varchar2,
            p_email_message_id varchar2,
            p_apps_id        varchar2,
	    	p_alliance_id    varchar2
         );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_replaced_message_body
         (
            p_message_body                  varchar2,
            p_properties_string             varchar2
         )
    RETURN varchar2;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_apps_notification_cursor
         (
            p_alliance_id               number,
            p_apps_id                   number,
            p_event_id                  number,
            p_properties_string         varchar2,
            p_user_ids_string           varchar2,               -- comma separated user ids string
            p_out_apps_notification OUT TYPES.cursor_type       -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_NOTIFICATIONS_PKG; -- Package Specification AIMS_NOTIFICATIONS_PKG
/

