CREATE OR REPLACE function translate_platform_old_to_new
( old_platform_id number)

RETURN  number IS

aims_brew_platform_id       constant number := 1;
aims_sms_platform_id        constant number := 2;
aims_mms_platform_id        constant number := 3;
aims_wap_platform_id        constant number := 4;
aims_enterprise_platform_id constant number := 5;

vzw_brew_platform_id        constant number := 1;
vzw_sms_platform_id         constant number := 2;
vzw_wap_platform_id         constant number := 3;
vzw_enterprise_platform_id  constant number := 4;

v_platform_id   number;
BEGIN
    if (old_platform_id = vzw_brew_platform_id) then
        return aims_brew_platform_id;
    end if;

    if (old_platform_id = vzw_sms_platform_id) then
        return aims_sms_platform_id;
    end if;

    if (old_platform_id = vzw_wap_platform_id) then
        return aims_wap_platform_id;
    end if;

    if (old_platform_id = vzw_enterprise_platform_id) then
        return aims_enterprise_platform_id;
    end if;

END translate_platform_old_to_new;
/

