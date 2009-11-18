CREATE OR REPLACE procedure temp_replicate_spotlight_data is
begin
insert into aims_ent_apps_spotlights
(
 SPOTLIGHT_ID,
 ENTERPRISE_APP_ID,
 FILE_TYPE_ID,
 SPOTLIGHT_TYPE_ID,
 SPOTLIGHT_NAME,
 SPOTLIGHT_DESC,
 SPOTLIGHT_FILE,
 SPOTLIGHT_FILE_FILE_NAME,
 SPOTLIGHT_FILE_CONTENT_TYPE,
 STATUS,
 CREATED_BY,
 CREATED_DATE
 )
 select SEQ_PK_ENT_APPS_SPOTLIGHTS.nextval,
        b.APPS_ID,
           a.FILE_TYPE_ID,
           a.SPOTLIGHT_TYPE_ID,
           a.SPOTLIGHT_NAME ,
           a.SPOTLIGHT_DESC,
           a.SPOTLIGHT_FILE,
           a.SPOTLIGHT_FILE_FILE_NAME,
           a.SPOTLIGHT_FILE_CONTENT_TYPE,
           a.STATUS,
           a.CREATED_BY,
           a.CREATED_DATE
           from aims_alliance_spotlights a, aims_apps b
           where a.ALLIANCE_ID = b.ALLIANCE_ID
           and b.PLATFORM_ID=5
           and b.phase_id = 7
           and a.SPOTLIGHT_ID in (1957,1958);

end;
/

