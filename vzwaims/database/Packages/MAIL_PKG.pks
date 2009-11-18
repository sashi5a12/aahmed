CREATE OR REPLACE package mail_pkg
as
    type array is table of varchar2(255);

procedure send( p_sender_email in varchar2,
                p_from         in varchar2 default NULL,
                p_to           in array default array(),
                p_cc           in array default array(),
                p_bcc          in array default array(),
                p_subject      in varchar2 default NULL,
                p_body         in long  default NULL );
end;
/

