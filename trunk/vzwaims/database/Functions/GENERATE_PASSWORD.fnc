CREATE OR REPLACE function generate_password (
    in_userid in varchar2, l_date in date
    )
    return varchar2
  is
    j                             number := 0;
    k                             number;
    str                           varchar2(30);
    result                        varchar2(30);
  begin
    if in_userid is null then
      return null;
    end if;
    str := substr (in_userid, 1, 4) || to_char (l_date, 'SSSS');
    for i in 1 .. least (length (str), 8)
    loop
      j := mod (j + ascii (substr (str, i, 1)), 256);
      k := mod (bitand (j, ascii (substr (str, i, 1))), 74) + 48;
      if k between 58 and 64 then
        k := k + 7;
      elsif k between 91 and 96 then
        k := k + 6;
      end if;
      result := result || chr (k);
    end loop;
    result := replace (result, '1', '2');
    result := replace (result, 'l', 'L');
    result := replace (result, '0', '9');
    result := replace (result, 'O', 'P');
    result := 'A' || substr (result, 2);
    return result;
  end generate_password;
/

