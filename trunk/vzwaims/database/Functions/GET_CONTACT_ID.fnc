CREATE OR REPLACE function get_contact_id (p_email_address varchar2)
return number is

p_id number;
begin
    --dbms_output.put_line('Value of p_email_address='||p_email_address);

    begin
        select contact_id
        into
            p_id
        from
            aims_users
        where
            username = p_email_address;

    return p_id;
    /*
    exception
    when no_data_found then
        return null;
        */
    end;

end get_contact_id;
/

