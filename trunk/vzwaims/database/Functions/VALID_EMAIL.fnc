CREATE OR REPLACE function valid_email( p_email_text in varchar2 )
    return number
is
  v_at number;
  v_dot number;
  v_email_text varchar2(150) := trim(p_email_text);
  v_len_email number := length(v_email_text);
begin
    v_at := instr(v_email_text , '@') ;
    v_dot := instr(v_email_text , '.', -1, 1);
    if v_at = 0 or v_dot= 0 or v_at > v_dot or v_dot >= v_len_email then
      return -1;
    else
      return 0;
    end if;
exception
    when others then
        return -1;
end;
/

