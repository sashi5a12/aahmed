CREATE OR REPLACE procedure p( p_string in varchar2 )
is
   l_string long default p_string;
begin
   loop
     exit when l_string is null;
     dbms_output.put_line( substr( l_string, 1, 250 ) );
     l_string := substr( l_string, 251 );
   end loop;
end;
/

