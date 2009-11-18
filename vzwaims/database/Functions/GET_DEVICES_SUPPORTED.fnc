CREATE OR REPLACE function get_devices_supported(brew_app_id number)

return varchar2 is

    v_devices_supported     varchar2(32000) := '';
    v_devices_array         DBMS_UTILITY.UNCL_ARRAY;
    v_cnt                   pls_integer := 0;
begin
     
    for c in (  select
                    d.device_model
                from
                    aims_devices d,
                    aims_brew_apps_devices bd
                where
                    d.device_id = bd.device_id
                    and bd.brew_apps_id = brew_app_id
                order by upper(d.device_model)
             ) loop

         v_cnt := v_cnt + 1;
         v_devices_array(v_cnt) := c.device_model;

   end loop;
   PARSE.table_to_delimstring(v_devices_array, v_devices_supported, ', ');
   return '' || v_devices_supported;
 
end get_devices_supported;
/

