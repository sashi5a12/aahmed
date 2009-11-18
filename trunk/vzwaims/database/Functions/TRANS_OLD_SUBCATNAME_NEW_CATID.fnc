CREATE OR REPLACE function TRANS_OLD_SUBCATNAME_NEW_CATID
( old_sub_cat_name varchar2)

RETURN  number IS

new_sub_cat_name varchar2(100) ;
cat_id number ;

BEGIN

if (old_sub_cat_name = 'Email-Pim') then
    new_sub_cat_name := 'Email/PIM' ;
elsif (old_sub_cat_name = 'IM') then
    new_sub_cat_name := 'Instant Messaging' ;
elsif (old_sub_cat_name = 'Notification') then
    new_sub_cat_name := 'Notification' ;
elsif (old_sub_cat_name = 'Voice Recognition') then
    new_sub_cat_name := 'Voice Recognition' ;
elsif (old_sub_cat_name = 'WAP Phone') then
    new_sub_cat_name := 'WAP Phone' ;
elsif (old_sub_cat_name = 'Smartphone') then
    new_sub_cat_name := 'Smartphone' ;
elsif (old_sub_cat_name = 'Ruggedized Laptop') then
    new_sub_cat_name := 'Ruggedized Laptop' ;
elsif (old_sub_cat_name = 'PDA') then
    new_sub_cat_name := 'PDA' ;
elsif (old_sub_cat_name = 'Modem Card') then
    new_sub_cat_name := 'Modem Card' ;
elsif (old_sub_cat_name = 'Software') then
    new_sub_cat_name := 'Software' ;
elsif (old_sub_cat_name = 'Hardware') then
    new_sub_cat_name := 'Hardware' ;
elsif (old_sub_cat_name = 'Web Access') then
    new_sub_cat_name := 'Web Access/Transcoding' ;
elsif (old_sub_cat_name = 'Sales Force Automation') then
    new_sub_cat_name := 'Sales Force Automation' ;
elsif (old_sub_cat_name = 'Field Force Automation') then
    new_sub_cat_name := 'Field Force Automation' ;
elsif (old_sub_cat_name = 'Supply Chain Management') then
    new_sub_cat_name := 'Supply Chain Management' ;
elsif (old_sub_cat_name = 'Telematics') then
    new_sub_cat_name := 'Telematics' ;
elsif (old_sub_cat_name = 'Document Management') then
    new_sub_cat_name := 'Document Management' ;
elsif (old_sub_cat_name = 'Device Management') then
    new_sub_cat_name := 'Device Management' ;
elsif (old_sub_cat_name = 'Wi-Fi') then
    new_sub_cat_name := 'Wi-Fi' ;
elsif (old_sub_cat_name = 'GPS') then
    new_sub_cat_name := 'GPS' ;
elsif (old_sub_cat_name = 'Bluetooth') then
    new_sub_cat_name := 'Bluetooth' ;
elsif (old_sub_cat_name = 'Location-Based Services') then
    new_sub_cat_name := 'Location-Based Services' ;
elsif (old_sub_cat_name = 'Compression/Optimization') then
    new_sub_cat_name := 'Compression/Optimization' ;
elsif (old_sub_cat_name = 'Other Mobile Desktop') then
    new_sub_cat_name := 'Other Mobile Desktop' ;
elsif (old_sub_cat_name = 'Other Device') then
    new_sub_cat_name := 'Other Device' ;
elsif (old_sub_cat_name = 'RIM Blackberry') then
    new_sub_cat_name := 'RIM Blackberry' ;
elsif (old_sub_cat_name = 'Other Security') then
    new_sub_cat_name := 'Other Security' ;
elsif (old_sub_cat_name = 'Other Business Capability Extension') then
    new_sub_cat_name := 'Other Business Capability Extension' ;
elsif (old_sub_cat_name = 'Other Operations/Support') then
    new_sub_cat_name := 'Other Operations/Support' ;
elsif (old_sub_cat_name = 'Other Connectivity') then
    new_sub_cat_name := 'Other Connectivity' ;
end if ;



select category_id
into cat_id
from aims_app_sub_categories
where sub_category_name = new_sub_cat_name ;

return cat_id ;

END TRANS_OLD_SUBCATNAME_NEW_CATID;
/

