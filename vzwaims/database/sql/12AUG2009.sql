update AIMS_PLATFORMS ap 
set ap.PLATFORM_NAME='V CAST Video'
,  ap.PLATFORM_DESC='V CAST Video'
where ap.PLATFORM_ID = 6 
/
update AIMS_PLATFORMS ap 
set ap.PLATFORM_NAME='V CAST Apps'
, ap.PLATFORM_DESC='V CAST Applications' 
where ap.PLATFORM_ID = 44
/
update AIMS_SUB_MENUS asm 
set asm.SUB_MENU_NAME='All Approved V CAST Apps' 
where asm.SUB_MENU_ID=2000
/