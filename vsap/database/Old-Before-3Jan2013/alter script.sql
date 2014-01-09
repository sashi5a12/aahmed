alter table vap_nda_question_answer
add answer varchar(500);

alter table vap_nda_vendor_data
drop foreign key FK_vap_registration_status_vap_company_fk2;
alter table vap_nda_vendor_data
change current_status current_status int(2) ;

alter table vap_nda_vendor_data
change country country varchar(50) ;


select ndavendor0_.vendor_data_id as vendor1_0_, ndavendor0_.is_active as is2_0_, ndavendor0_.created_by as created3_0_, ndavendor0_.created_date as created4_0_, ndavendor0_.updated_by as updated5_0_, ndavendor0_.updated_date as updated6_0_, ndavendor0_.address1 as address7_0_, ndavendor0_.city as city0_, ndavendor0_.company_id as company18_0_, ndavendor0_.contact_email as contact9_0_, ndavendor0_.contact_full_name as contact10_0_, ndavendor0_.contact_phone as contact11_0_, ndavendor0_.contact_title as contact12_0_, ndavendor0_.corporate_name as corporate13_0_, ndavendor0_.country as country0_, ndavendor0_.state_of_incorporation as state15_0_, ndavendor0_.state as state0_, ndavendor0_.user_id as user19_0_, 
ndavendor0_.zipcode as zipcode0_ 
from vap_nda_vendor_data ndavendor0_ 
where ndavendor0_.company_id=17