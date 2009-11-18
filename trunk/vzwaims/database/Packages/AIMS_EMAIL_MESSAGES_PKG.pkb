CREATE OR REPLACE Package Body      AIMS_EMAIL_MESSAGES_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_email_messages
         (
           p_from_id                IN  number,             -- from user id
           p_recepients             IN  varchar2,           -- comma seperated email recepients
           p_email_groups           IN  varchar2,           -- comma seperated email groups
           p_subject                IN  varchar2,           -- subject of the message
           p_text                   IN  varchar2,            -- text of the message
           p_bcc_cursor            OUT  TYPES.cursor_type  
         )

    IS

    /*
    || Overview:        Inserts email messages in the AIMS_MESSAGES table against the given recepients
    ||
    ||
    || Dependencies:
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


        v_recepients_array    DBMS_UTILITY.UNCL_ARRAY;
        v_email_groups_array  DBMS_UTILITY.UNCL_ARRAY;


        v_cnt_recepients      number;
        v_cnt_email_groups    number;
        v_groups_contacts     varchar2(32000);
        t_null  DBMS_UTILITY.UNCL_ARRAY;
        t_temp  DBMS_UTILITY.UNCL_ARRAY;
        t_full  DBMS_UTILITY.UNCL_ARRAY;

        v_full_count number; 
        v_created_date date := sysdate;
        v_message_id  number;
   BEGIN 
   
   /*
        ins_sql('p_from_id --> ' || p_from_id);
        ins_sql('p_recepients --> ' || p_recepients);
        ins_sql('p_email_groups --> ' || p_email_groups);
        ins_sql('p_subject --> ' || p_subject);
        ins_sql('p_text --> ' || p_text);        
    */    
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
                p_subject,
                p_text,
                p_from_id,
                'N',
                v_created_date 
            )
            returning message_id into v_message_id;            
            
  
        
        if (INSTR(p_recepients,';') > 0) then
            PARSE.delimstring_to_table(p_recepients, v_recepients_array, v_cnt_recepients, ';');
        elsif (LENGTH(p_recepients) > 0) then
            v_recepients_array(1) := p_recepients;
            v_cnt_recepients := 1;
        end if;

  
        
        for i in 1..v_recepients_array.COUNT loop
            begin
                insert into aims_distinct_contact_ids (contact_id)
                    values (v_recepients_array(i));
            exception
                when others then
                    null;
            end;      
        end loop;        

        
        
        if (INSTR(p_email_groups,';') > 0) then
            PARSE.delimstring_to_table(p_email_groups, v_email_groups_array, v_cnt_email_groups, ';');
        elsif (LENGTH(p_email_groups) > 0) then
            v_email_groups_array(1) := p_email_groups;
            v_cnt_email_groups := 1;
        end if;


        
        if (v_cnt_email_groups > 0) then           

            for i IN 1..v_email_groups_array.count loop
                if(length(trim(v_email_groups_array(i))) > 0) then
                    t_temp := t_null;  
                    --07/02/2004 (amakda): Commented the line below as the query for sending out to
	                --Email Groups is not working as expected. It is 'OR'ing instead of 'AND'ing
                    AIMS_EMAIL_MESSAGES_PKG_UTILS.all_group_contacts(v_email_groups_array(i), t_temp);                    
                end if;
            end loop;
           
        end if;
            
            insert into aims_messages_recpts_grps
               (
                  message_id,
                  recipient_id,
                  group_id,
                  status
               )
                 select
                      v_message_id,
                      contact_id,
                      group_id,
                      'N'             
                 from       
                    aims_distinct_contact_ids;                
       
        
        
      OPEN p_bcc_cursor FOR
          select
              email_address
          from
              aims_contacts
          where
              valid_email(email_address) = 0
              and contact_id in 
                (select contact_id from aims_distinct_contact_ids);
     EXCEPTION
          WHEN OTHERS THEN
             IF(p_bcc_cursor%isopen) THEN
                 CLOSE p_bcc_cursor;
             END IF;
             RAISE;        
        
   END insert_email_messages;
   
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_invalid_email_msg_recpts
         (                     
           p_recipients_cursor     OUT  TYPES.cursor_type   -- the message recipients cursor
         )

    IS

    /*
    || Overview:        Returns a cursor which contains all recipients of a given message
    ||
    || Dependencies:
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
    
    v_recipients_string     varchar2(200);
    v_recipients_cnt        number := 0;
    
   BEGIN    
        
       OPEN p_recipients_cursor for       
            select
                a.company_name,          
                c.first_name,
                c.last_name,
                c.email_address                
            from          
               aims_contacts c,
               aims_users u,
               aims_alliances a,
               aims_distinct_contact_ids t
            where
               valid_email(c.email_address) = -1 
               and t.contact_id = c.contact_id
               and u.contact_id = c.contact_id
               and a.alliance_id(+) = u.alliance_id
            order by upper(a.company_name), upper(c.first_name), upper(c.last_name);
                   
        EXCEPTION
        WHEN OTHERS THEN
           IF(p_recipients_cursor%isopen) THEN
               CLOSE p_recipients_cursor;
           END IF;
           RAISE;        
   
   END get_invalid_email_msg_recpts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_message_status
         (
           p_to_id                  IN  number,             -- to user id
           p_message_id             IN  number              -- message id   
         )

    IS

    /*
    || Overview:        Updates the status of the message to read
    ||
    ||
    || Dependencies:
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
   BEGIN    
        
        update
            aims_messages_recpts_grps
        set 
            status = 'R',
            viewed_date = sysdate
        where
            message_id = p_message_id
            and recipient_id = p_to_id;
   
   END update_message_status;
   
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_message_recipients_string
         (           
           p_message_id             IN  number,             -- message id
           p_recipients_string     OUT  varchar2            -- the first couple of recipients string  
         )

    IS

    /*
    || Overview:        Returns a string which contains one or two recipients and if there are more concatenates
    ||                  a ... to the end of the string.
    ||
    || Dependencies:
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
    
    v_recipients_string     varchar2(200);
    v_recipients_cnt        number := 0;
    
   BEGIN    
        
        for c in (
                    select name from (
                                    select distinct
                                        g.group_title name
                                    from
                                        aims_messages_recpts_grps m,
                                        aims_email_groups g
                                    where
                                        m.message_id = p_message_id
                                        and m.group_id is not null
                                        and g.group_id = m.group_id(+)
                                        and rownum <= 2
                                                                      
                                    union all                                   
                                    select
                                        c.first_name || ' ' || c.last_name name
                                    from
                                        aims_messages_recpts_grps g, aims_contacts c
                                    where
                                        g.recipient_id = c.contact_id
                                        and g.message_id = p_message_id
                                        and rownum <= 2
                    ) 
                    where rownum <= 2
                    --order by upper(name)
                )
        loop
            v_recipients_cnt := v_recipients_cnt + 1;
            
            --dbms_output.put_line('Value of v_recipients_cnt='||v_recipients_cnt);
            
            --dbms_output.put_line('Value of c.name='||c.name);
            
            if (v_recipients_cnt = 1) then
                v_recipients_string := v_recipients_string || c.name;
            end if;
            
            if (v_recipients_cnt = 2) then
                v_recipients_string := v_recipients_string || '...';
                p_recipients_string := v_recipients_string;
                return;                
            end if;
            
            p_recipients_string := v_recipients_string;                      
        
        end loop;
   
   END get_message_recipients_string;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_all_message_recipients
         (           
           p_message_id             IN  number,             -- message id
           p_recipients_cursor     OUT  TYPES.cursor_type   -- the message recipients cursor
         )

    IS

    /*
    || Overview:        Returns a cursor which contains all recipients of a given message
    ||
    || Dependencies:
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
    
    v_recipients_string     varchar2(200);
    v_recipients_cnt        number := 0;
    
   BEGIN    
        
       OPEN p_recipients_cursor for       
            select 
                nvl(g.group_title, '') title,
                c.first_name,
                c.last_name,
                c.email_address,
                a.company_name
            from
               aims_messages_recpts_grps rg,
               aims_email_groups g,
               aims_contacts c,
               aims_users u,
               aims_alliances a
            where
               rg.group_id = g.group_id(+)
               and rg.recipient_id = c.contact_id
               and rg.message_id = p_message_id
               and u.contact_id = c.contact_id
               and a.alliance_id(+) = u.alliance_id
            order by upper(title), upper(c.first_name), upper(c.last_name);
                   
        EXCEPTION
        WHEN OTHERS THEN
           IF(p_recipients_cursor%isopen) THEN
               CLOSE p_recipients_cursor;
           END IF;
           RAISE;        
   
   END get_all_message_recipients;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_message_recp_contact_ids
         (           
           p_message_id             IN  number,             -- message id
           p_recipients_string     OUT  varchar2            -- the contact ids of recipients string  
         )

    IS

    /*
    || Overview:        Returns a string which contains all the contact_ids for the recipients of a given message
    ||               
    ||
    || Dependencies:
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
    
    v_recipients_string     varchar2(32000);
    v_recipients_cnt        number := 0;
    
   BEGIN    
        
        for c in (                                             
                    select
                        recipient_id
                    from
                        aims_messages_recpts_grps
                    where                        
                        message_id = p_message_id
                        and group_id is null                      
                 )   
        loop
                   
            if (v_recipients_string is null) then
                v_recipients_string := v_recipients_string || c.recipient_id;
            else
                v_recipients_string := v_recipients_string || ';' || c.recipient_id;
            end if;
            
        end loop;
        
            if (v_recipients_string is not null) then
                v_recipients_string := v_recipients_string || ';';
            end if;

            p_recipients_string := v_recipients_string;  
   
   END get_message_recp_contact_ids;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_bcc_cursor
         ( 
           p_bcc_cursor    OUT  TYPES.cursor_type            -- the cursor for recipients 
         )

    IS

    /*
    || Overview:        Returns a cursor which contains all the email ids for the recipients of a given message
    ||               
    ||
    || Dependencies:
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
    
    
   BEGIN    
        
        OPEN p_bcc_cursor FOR
            select
                email_address
            from
                aims_contacts
            where
                contact_id in 
                (select contact_id from aims_distinct_contact_ids);
       EXCEPTION
            WHEN OTHERS THEN
               IF(p_bcc_cursor%isopen) THEN
                   CLOSE p_bcc_cursor;
               END IF;
               RAISE;
                       
   END get_bcc_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_MESSAGES_PKG; -- Package Body AIMS_EMAIL_MESSAGES_PKG
/

