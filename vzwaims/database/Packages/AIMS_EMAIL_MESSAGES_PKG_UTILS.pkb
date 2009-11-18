CREATE OR REPLACE Package Body      AIMS_EMAIL_MESSAGES_PKG_UTILS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_contact_id (p_user_id number)
    RETURN  number

    IS

    /*
    || Overview:        Gets the to contact_id for a given user_id
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 02-02-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_contact_id     AIMS_CONTACTS.contact_id%type;

    BEGIN

        if nvl(p_user_id, 0) > 0 then
          select
              c.contact_id
          into
              v_contact_id
          from
              aims_contacts c, aims_users u
          where
              u.user_id = p_user_id
              and u.contact_id = c.contact_id;
        end if;

        RETURN v_contact_id;

    END get_contact_id;

/* -------------------------------------------------------------------------------------------------------------  */

      FUNCTION get_admin_to_id (p_str_contact_ids varchar2)
    RETURN  number

    IS

    /*
    || Overview:        Gets the to contact id from a given string (the first id)
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 02-02-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        to_id     number;

    BEGIN

        if (length(p_str_contact_ids) > 0) then
            to_id  := to_number(substr(p_str_contact_ids, 1, instr(p_str_contact_ids,';') - 1));
        else
            to_id := null;
        end if;

        RETURN to_id;

    END get_admin_to_id;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_admin_from_id
    RETURN  number
    IS

    /*
    || Overview:        Gets the from contact id
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 02-02-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        admin_id     number;

    BEGIN

        select
            contact_id
        into
            admin_id
        from
            aims_contacts
        where
            email_address ='developerspogram@vzw.com';

        RETURN admin_id;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
           RETURN 7;

    END get_admin_from_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_technical_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                    select
                        distinct a.tech_contact_id, p_in_group_id
                    from
                        aims_alliances a
                    where
                        a.alliance_id is not null
                        and nvl(a.tech_contact_id, 0) > 0;

            exception
                when others then
                    null;
            end;

    END get_technical_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_executive_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                    select
                        distinct a.executive_contact_id, p_in_group_id
                    from
                        aims_alliances a
                    where
                        a.alliance_id is not null
                        and nvl(a.executive_contact_id, 0) > 0;

            exception
                when others then
                    null;
            end;

    END get_executive_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_marketing_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                    select
                        distinct a.mktg_pr_contact_id, p_in_group_id
                    from
                        aims_alliances a
                    where
                        a.alliance_id is not null
                        and nvl(a.mktg_pr_contact_id, 0) > 0;

            exception
                when others then
                    null;
            end;

    END get_marketing_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_business_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                    select
                        distinct a.bus_contact_id, p_in_group_id
                    from
                        aims_alliances a
                    where
                        a.alliance_id is not null
                        and nvl(a.bus_contact_id, 0) > 0;

            exception
                when others then
                    null;
            end;
            /*
        for c in (select
                      distinct a.bus_contact_id
                  from
                      aims_alliances a
                  where
                      a.alliance_id is not null
                      and nvl(a.bus_contact_id, 0) > 0 )
        loop
            cnt := cnt + 1;
            p_out_result (cnt) := c.bus_contact_id;
        end loop;
    */
    END get_business_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_admin_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

           for c in (select
                        distinct u.contact_id
                    from
                        aims_alliances a,
                        aims_users u
                    where
                        a.alliance_id is not null
                        and nvl(a.admin_user_id, 0) > 0
                        and a.admin_user_id = u.user_id) loop
            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                        values(c.contact_id, p_in_group_id);

            exception
                when others then
                    null;
            end;
         end loop;

    END get_admin_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_contract_status_contacts
         (
           p_status                      IN     varchar2,
           p_group_id                    IN     number
         )
    IS

    /*
    || Overview:        Takes the contract status and returns a table of contact_ids of the alliance administrators
    ||                      who have contracts with the corresponding status.
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

           for c in (select
                        u.contact_id
                    from
                        aims_alliances a,
                        aims_alliance_contracts ac,
                        aims_users u
                    where
                        ac.status = p_status
                        and nvl(a.admin_user_id, 0) > 0
                        and a.alliance_id = ac.alliance_id) loop
            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                        values(c.contact_id, p_group_id);

            exception
                when others then
                    null;
            end;
         end loop;

    END get_contract_status_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_contract_ids_contacts
         (
           p_contract_id                 IN     number,
           p_group_id                    IN     number
         )
    IS

    /*
    || Overview:        Takes the contract id and returns a table of contact_ids of the alliance administrators
    ||                      who have contracts with the corresponding contract_id.
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN

           for c in (select
                        u.contact_id
                    from
                        aims_alliances a,
                        aims_alliance_contracts ac,
                        aims_users u
                    where
                        ac.contract_id = p_contract_id
                        and nvl(a.admin_user_id, 0) > 0
                        and a.alliance_id = ac.alliance_id) loop
            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                        values(c.contact_id, p_group_id);

            exception
                when others then
                    null;
            end;
         end loop;

    END get_contract_ids_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE contact_type_group_contacts
         (
           p_in_group_id                 IN     number
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||

'E' executive       EXECUTIVE_CONTACT_ID
'B' business        BUS_CONTACT_ID
'A' administrator   ADMIN_USER_ID
'T' technical       TECH_CONTACT_ID
'M' marketing       MKTG_PR_CONTACT_ID
    */

        cnt  pls_integer := 0;
        t_null  DBMS_UTILITY.UNCL_ARRAY;
        t_temp  DBMS_UTILITY.UNCL_ARRAY;

    BEGIN

        for c in (select
                      g.contact_type contact_type
                  from
                      aims_email_group_contact_types g
                  where
                      g.group_id = p_in_group_id)
        loop


            if (c.contact_type = 'E') then
                get_executive_contacts(p_in_group_id, t_temp);
            elsif (c.contact_type = 'B') then
                get_business_contacts(p_in_group_id, t_temp);
            elsif (c.contact_type = 'A') then
                get_admin_contacts(p_in_group_id, t_temp);
            elsif (c.contact_type = 'T') then
                get_technical_contacts(p_in_group_id, t_temp);
            elsif (c.contact_type = 'M') then
                get_marketing_contacts(p_in_group_id, t_temp);
            end if;

        end loop;

        --dbms_output.put_line('Value of cnt='||cnt);
    END contact_type_group_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE contract_status_group_contacts
         (
           p_in_group_id                 IN     number
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||

'A' Accepted       ACCEPTED
'O' Offered        OFFERED
'R' Rejected       REJECTED

    */


        status  varchar2(20);

    BEGIN

        for c in (select
                      g.contract_status
                  from
                      aims_email_group_contract_stat g
                  where
                      g.group_id = p_in_group_id)
        loop


            if (c.contract_status = 'A') then
                status := 'ACCEPTED';
            elsif (c.contract_status = 'O') then
                status := 'OFFERED';
            elsif (c.contract_status = 'R') then
                status := 'REJECTED';
            end if;


            get_contract_status_contacts(status, p_in_group_id);


        end loop;

    END contract_status_group_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE contract_ids_group_contacts
         (
           p_in_group_id                 IN     number
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||

    */




    BEGIN

        for c in (select
                      g.contract_id
                  from
                      aims_email_group_contract_ids g
                  where
                      g.group_id = p_in_group_id)
        loop


            get_contract_ids_contacts(c.contract_id, p_in_group_id);


        end loop;

    END contract_ids_group_contacts;

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE platform_group_contacts
         (
           p_in_group_id                 IN     number
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        cnt  pls_integer := 0;
    BEGIN


           for c in (select
                        distinct u.contact_id, g.group_id
                    from
                        aims_alliances a,
                        aims_apps p,
                        aims_email_group_platforms g,
                        aims_users u
                    where
                        g.group_id = p_in_group_id
                        and g.platform_id = p.platform_id
                        and p.alliance_id = a.alliance_id
                        and a.admin_user_id = u.user_id) loop
            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                        values(c.contact_id, c.group_id);

            exception
                when others then
                    null;
            end;
         end loop;
            /*

        for c in (select
                      distinct u.contact_id contact_id
                  from
                      aims_alliances a,
                      aims_apps p,
                      aims_email_group_platforms g,
                      aims_users u
                  where
                      g.group_id = p_in_group_id
                      and g.platform_id = p.platform_id
                      and p.alliance_id = a.alliance_id
                      and a.admin_user_id = u.user_id)
        loop
            cnt := cnt + 1;
            p_out_result (cnt) := c.contact_id;
        end loop;
        */
        --dbms_output.put_line('Value of cnt='||cnt);
    END platform_group_contacts;

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE all_group_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        t_null  DBMS_UTILITY.UNCL_ARRAY;
        t_temp  DBMS_UTILITY.UNCL_ARRAY;

    BEGIN


            for c in
            (
                    select
                        distinct a.alliance_id
                    from
                        aims_alliances a,
                        aims_alliance_contracts ac,
                        aims_email_group_contract_stat g
                    where
                        g.group_id = p_in_group_id
                        and g.contract_status = ac.status
                        and ac.alliance_id = a.alliance_id

                    INTERSECT

                    select
                        distinct a.alliance_id
                    from
                        aims_alliances a,
                        aims_alliance_contracts ac,
                        aims_email_group_contract_ids g
                    where
                        g.group_id = p_in_group_id
                        and g.contract_id = ac.contract_id
                        and ac.alliance_id = a.alliance_id

                    INTERSECT

                    select
                        distinct a.alliance_id
                    from
                        aims_alliances a,
                        aims_apps p,
                        aims_email_group_platforms g
                    where
                        g.group_id = p_in_group_id
                        and g.platform_id = p.platform_id
                        and p.alliance_id = a.alliance_id


            )loop

                begin
                    for d in
                    (
                        select
                            distinct a.executive_contact_id cont_id
                        from
                            aims_alliances a, aims_email_group_contact_types g
                        where
                            a.alliance_id is not null
                            and nvl(a.executive_contact_id, 0) > 0
                            and g.contact_type = 'E'
                            and g.group_id = p_in_group_id
                            and a.alliance_id = c.alliance_id

                        UNION

                        select
                           distinct a.bus_contact_id cont_id
                        from
                            aims_alliances a, aims_email_group_contact_types g
                        where
                            a.alliance_id is not null
                            and nvl(a.bus_contact_id, 0) > 0
                            and g.contact_type = 'B'
                            and g.group_id = p_in_group_id
                            and a.alliance_id = c.alliance_id

                        UNION

                        select
                            distinct a.mktg_pr_contact_id cont_id
                        from
                            aims_alliances a, aims_email_group_contact_types g
                        where
                            a.alliance_id is not null
                            and nvl(a.mktg_pr_contact_id, 0) > 0
                            and g.contact_type = 'M'
                            and g.group_id = p_in_group_id
                            and a.alliance_id = c.alliance_id

                        UNION

                        select
                            distinct a.tech_contact_id cont_id
                        from
                            aims_alliances a, aims_email_group_contact_types g
                        where
                            a.alliance_id is not null
                            and nvl(a.tech_contact_id, 0) > 0
                            and g.contact_type = 'T'
                            and g.group_id = p_in_group_id
                            and a.alliance_id = c.alliance_id

                        UNION

                        select
                            distinct u.contact_id cont_id
                        from
                            aims_alliances a, aims_email_group_contact_types g, aims_users u
                        where
                            a.alliance_id is not null
                            and nvl(a.admin_user_id, 0) > 0
                            and a.admin_user_id = u.user_id
                            and g.contact_type = 'A'
                            and g.group_id = p_in_group_id
                            and a.alliance_id = c.alliance_id

                    )loop

                        begin
                            insert into aims_distinct_contact_ids (contact_id, group_id)
                                values(d.cont_id, p_in_group_id);
                        exception
                            when others then
                                null;
                        end;

                    end loop;

                exception
                    when others then
                        null;
                end;

            end loop;

    END all_group_contacts;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_complete_recipient_string
        RETURN varchar2
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_temp_contact_id  varchar2(200);
        v_full_contact_id  varchar2(32000);

    BEGIN
        for c in (select contact_id from aims_distinct_contact_ids) loop
            v_temp_contact_id := c.contact_id;

            if (v_full_contact_id is null) then
                v_full_contact_id := v_temp_contact_id;
            else
                v_full_contact_id := v_full_contact_id || ';' || v_temp_contact_id;
            end if;
            v_temp_contact_id := null;
        end loop;

        if (length(v_full_contact_id) > 0) then
            v_full_contact_id := v_full_contact_id || ';';
        end if;

        --ins_sql('v_full_contact_id --> ' || v_full_contact_id);
        return v_full_contact_id;

    END get_complete_recipient_string;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_to_recipient_string
        RETURN varchar2
    IS

    /*
    || Overview:        Takes the group_id and retuns a table of contact_ids
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_to_contact_id  varchar2(30);

    BEGIN
        for c in (select contact_id from aims_distinct_contact_ids order by contact_id) loop
            v_to_contact_id := c.contact_id;
            return v_to_contact_id;
        end loop;

    END get_to_recipient_string;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_dist_contact_ids
         (
           p_in_group_id                 IN     number,
           p_in_tbl_contacts             IN     DBMS_UTILITY.UNCL_ARRAY
         )
    IS

    /*
    || Overview:        Takes the group_id and table of contacts and inserts into the
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 01-31-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

    BEGIN

        for i in 1..p_in_tbl_contacts.COUNT loop
            begin
                insert into aims_distinct_contact_ids (contact_id, group_id)
                    values (p_in_tbl_contacts(i), p_in_group_id);
            exception
                when others then
                    null;
            end;
        end loop;

    END insert_dist_contact_ids;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_MESSAGES_PKG_UTILS; -- Package Body AIMS_EMAIL_MESSAGES_PKG_UTILS
/

