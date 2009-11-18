CREATE OR REPLACE FUNCTION GET_DEVICE_PLATFORM( p_device_id IN NUMBER )
    RETURN VARCHAR2
    IS
        l_str  VARCHAR2(2000) DEFAULT NULL;
        l_sep  VARCHAR2(5) DEFAULT NULL;
    BEGIN
        FOR x IN (
			SELECT pf.platform_name AS p_name
  				FROM aims_devices d, aims_device_platforms dp, aims_platforms pf
				WHERE  d.device_id = dp.device_id
				AND   dp.platform_id = pf.platform_id
 				AND d.device_id = p_device_id
				order by pf.platform_name
				 )
        LOOP
           l_str := l_str || l_sep || x.p_name;
           l_sep := ', ';
       END LOOP;
       RETURN l_str;
   END;
/
