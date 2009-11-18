CREATE OR REPLACE Package AIMS_EMAIL_MESSAGES_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for EMAIL groups maintenance.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 05-04-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_email_messages
         (
           p_from_id                IN  number,             -- from user id
           p_recepients             IN  varchar2,           -- comma seperated email recepients
           p_email_groups           IN  varchar2,           -- comma seperated email groups
           p_subject                IN  varchar2,           -- subject of the message
           p_text                   IN  varchar2,            -- text of the message
           p_bcc_cursor            OUT  TYPES.cursor_type
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_invalid_email_msg_recpts
         (
           p_recipients_cursor     OUT  TYPES.cursor_type   -- the message recipients cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_message_status
         (
           p_to_id                  IN  number,             -- to user id
           p_message_id             IN  number              -- message id
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_message_recipients_string
         (
           p_message_id             IN  number,             -- message id
           p_recipients_string     OUT  varchar2            -- the first couple of recipients string
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_all_message_recipients
         (
           p_message_id             IN  number,             -- message id
           p_recipients_cursor     OUT  TYPES.cursor_type   -- the message recipients cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_message_recp_contact_ids
         (
           p_message_id             IN  number,             -- message id
           p_recipients_string     OUT  varchar2            -- the first couple of recipients string
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_bcc_cursor
         (
           p_bcc_cursor    OUT  TYPES.cursor_type            -- the cursor for recipients
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_MESSAGES_PKG; -- Package Specification AIMS_EMAIL_MESSAGES_PKG
/

