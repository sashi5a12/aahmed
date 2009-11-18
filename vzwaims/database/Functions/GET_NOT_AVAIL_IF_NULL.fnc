CREATE OR REPLACE function get_not_avail_if_null(appid number, colname varchar2, tabname varchar2)

return blob is

    v_image                   blob;
    sql_select                varchar2(500);
begin

    if (tabname = 'aims_apps') then

      sql_select := sql_select || 'select';
      sql_select := sql_select || '   decode( nvl(dbms_lob.getlength(' || colname || '), 0), 0, ';
      sql_select := sql_select || '   (select image_file from AIMS_MISC_IMAGES where image_name = ''not-available.jpg''), ' || colname || ') ';
      sql_select := sql_select || 'from ';
      sql_select := sql_select || '   aims_apps ';
      sql_select := sql_select || 'where ';
      sql_select := sql_select || '   apps_id = :1';

   elsif (tabname = 'aims_brew_apps') then

      sql_select := sql_select || 'select';
      sql_select := sql_select || '   decode( nvl(dbms_lob.getlength(' || colname || '), 0), 0, ';
      sql_select := sql_select || '   (select image_file from AIMS_MISC_IMAGES where image_name = ''not-available.jpg''), ' || colname || ') ';
      sql_select := sql_select || 'from ';
      sql_select := sql_select || '   aims_brew_apps ';
      sql_select := sql_select || 'where ';
      sql_select := sql_select || '   brew_apps_id = :1';

   end if;

    execute immediate sql_select into v_image using appid;
    return v_image;

end get_not_avail_if_null;
/

