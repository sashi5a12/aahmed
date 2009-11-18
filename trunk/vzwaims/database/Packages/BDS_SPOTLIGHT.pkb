CREATE OR REPLACE PACKAGE BODY Bds_Spotlight
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_spotlight_page
         (
           p_solution_id                 IN NUMBER,               -- solution_id
           p_spotlight_type_id           IN NUMBER,               -- spotlight_type_id
   		   p_if_public                   IN VARCHAR2,              -- if only public solutions required
           p_out_partner_details        OUT Types.cursor_type,    -- Result cursor
           p_out_solution_details       OUT Types.cursor_type     -- Result cursor                     
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */
        sql_select  VARCHAR2(4000);
        sql_select2 VARCHAR2(4000);     
        
   BEGIN
   
  sql_select :=   sql_select || 'SELECT ';     
        sql_select :=   sql_select || '               a.alliance_id, a.company_name, a.street_address_1, ';
        sql_select :=   sql_select || '               a.steet_address_2, a.city, a.state, ';
        sql_select :=   sql_select || '               a.zip_code, a.web_site_url, a.num_full_time_emp, ';     
        sql_select :=   sql_select || '               c.first_name, c.last_name, c.phone, c.email_address, ';
        sql_select :=   sql_select || '               d.first_name, d.last_name, d.phone, d.email_address ';
        sql_select :=   sql_select || 'FROM ';
        sql_select :=   sql_select || '   aims_alliances a, ';
        sql_select :=   sql_select || '   aims_apps ap, ';
        sql_select :=   sql_select || '   aims_contacts c, ';
        sql_select :=   sql_select || '   aims_contacts d ';                
        sql_select :=   sql_select || 'WHERE ';
        sql_select :=   sql_select || '   ap.apps_id = :1 ';
        sql_select :=   sql_select || '   and ap.alliance_id = a.alliance_id ';
        sql_select :=   sql_select || '   and c.contact_id (+) = a.bus_contact_id  ';
        sql_select :=   sql_select || '   and d.contact_id (+) = a.tech_contact_id ';        
        sql_select :=   sql_select || ' ORDER BY 2 ';
        
        OPEN p_out_partner_details FOR sql_select USING p_solution_id;
         

        sql_select2 :=   sql_select2 || 'SELECT ';   
        sql_select2 :=   sql_select2 || '   a.apps_id, a.long_desc, a.title, ';          
        sql_select2 :=   sql_select2 || '   x.first_name, x.last_name, ';          
        sql_select2 :=   sql_select2 || '   y.first_name, y.last_name, y.phone, y.email_address, ';
        sql_select2 :=   sql_select2 || '  (        select ';
        sql_select2 :=   sql_select2 || '               max(t.created_date) ';
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               aims_ent_apps_spotlights t ';
        sql_select2 :=   sql_select2 || '           where  ';
        sql_select2 :=   sql_select2 || '               t.enterprise_app_id = a.apps_id ';
        sql_select2 :=   sql_select2 || '               and t.status = ''ACCEPT'' ';
        sql_select2 :=   sql_select2 || '               and t.spotlight_type_id = :1 ), ';
        sql_select2 :=   sql_select2 || '  (        select ';
        sql_select2 :=   sql_select2 || '               spotlight_type_name ';
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               aims_spotlight_types u ';
        sql_select2 :=   sql_select2 || '           where  ';
        sql_select2 :=   sql_select2 || '               u.spotlight_type_id = :2 ), ';        
        sql_select2 :=   sql_select2 || '  (        select ';
        sql_select2 :=   sql_select2 || '               v.spotlight_id ';
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               aims_ent_apps_spotlights v ';
        sql_select2 :=   sql_select2 || '           where  ';
        sql_select2 :=   sql_select2 || '               v.spotlight_type_id = 9  ';
        sql_select2 :=   sql_select2 || '               and v.status = ''ACCEPT'' ';
        sql_select2 :=   sql_select2 || '               and v.enterprise_app_id = a.apps_id ), ';        
        sql_select2 :=   sql_select2 || '   cursor( select ';
        sql_select2 :=   sql_select2 || '               r.region_id ';
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               aims_ent_apps_region r ';
        sql_select2 :=   sql_select2 || '           where ';
        sql_select2 :=   sql_select2 || '               r.enterprise_apps_id = e.enterprise_apps_id  ) apps_regions, ';
        sql_select2 :=   sql_select2 || '   cursor( select ';
        sql_select2 :=   sql_select2 || '               i.industry_id ';
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               aims_ent_apps_ind_focus i ';
        sql_select2 :=   sql_select2 || '           where ';
        sql_select2 :=   sql_select2 || '               i.enterprise_apps_id = e.enterprise_apps_id ) apps_ind_focus, ';
        sql_select2 :=   sql_select2 || '   cursor( select ';
        sql_select2 :=   sql_select2 || '               i.market_segment_id ';
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               bds_solution_market_segments i ';
        sql_select2 :=   sql_select2 || '           where ';
        sql_select2 :=   sql_select2 || '               i.is_excluded != ''Y'' ';
        sql_select2 :=   sql_select2 || '               and i.solution_id = e.enterprise_apps_id ) apps_mkt_segments, ';
        sql_select2 :=   sql_select2 || '   cursor( select ';
        sql_select2 :=   sql_select2 || '               s.spotlight_id, s.spotlight_name, s.spotlight_desc, ';
        sql_select2 :=   sql_select2 || '               s.spotlight_file_file_name, s.created_date,  ';
        sql_select2 :=   sql_select2 || '               substr(s.spotlight_file_file_name, decode(instr(s.spotlight_file_file_name, ''.''), 0, ';        
        sql_select2 :=   sql_select2 || '               length(s.spotlight_file_file_name), instr(s.spotlight_file_file_name, ''.'')) + 1) ';         
        sql_select2 :=   sql_select2 || '           from ';
        sql_select2 :=   sql_select2 || '               aims_ent_apps_spotlights s ';
        sql_select2 :=   sql_select2 || '           where ';
        sql_select2 :=   sql_select2 || '               s.enterprise_app_id = a.apps_id ';
        sql_select2 :=   sql_select2 || '               and s.status = ''ACCEPT'' ';
        sql_select2 :=   sql_select2 || '               and s.spotlight_type_id = :3 ) app_spotlight, '; 
        sql_select2 :=   sql_select2 || '   cursor( select ';
        sql_select2 :=   sql_select2 || '               spotlight_type_id, ';
        sql_select2 :=   sql_select2 || '               0 ';                      
        sql_select2 :=   sql_select2 || '           from  ';            
        sql_select2 :=   sql_select2 || '               aims_spotlight_types ';
        sql_select2 :=   sql_select2 || '           where ';             
        sql_select2 :=   sql_select2 || '               spotlight_type_id not in ';
        sql_select2 :=   sql_select2 || '               (select ';
        sql_select2 :=   sql_select2 || '                   s.spotlight_type_id  ';                     
        sql_select2 :=   sql_select2 || '               from  ';
        sql_select2 :=   sql_select2 || '                   aims_ent_apps_spotlights s ';
        sql_select2 :=   sql_select2 || '               where ';             
        sql_select2 :=   sql_select2 || '                   s.enterprise_app_id = a.apps_id ';
        sql_select2 :=   sql_select2 || '                   and s.status = ''ACCEPT'') ';
        sql_select2 :=   sql_select2 || '            union ';
        sql_select2 :=   sql_select2 || '            select '; 
        sql_select2 :=   sql_select2 || '               a.spotlight_type_id, ';
        sql_select2 :=   sql_select2 || '               count(*) ';                      
        sql_select2 :=   sql_select2 || '            from  ';
        sql_select2 :=   sql_select2 || '               aims_ent_apps_spotlights s, ';
        sql_select2 :=   sql_select2 || '               aims_spotlight_types a ';
        sql_select2 :=   sql_select2 || '            where  ';            
        sql_select2 :=   sql_select2 || '               s.enterprise_app_id = a.apps_id ';
        sql_select2 :=   sql_select2 || '               and s.status = ''ACCEPT'' '; 
        sql_select2 :=   sql_select2 || '               and a.spotlight_type_id = s.spotlight_type_id (+) ';
        sql_select2 :=   sql_select2 || '            group by  a.spotlight_type_id ) spot_type_count, y.title ';  
        sql_select2 :=   sql_select2 || 'FROM ';
        sql_select2 :=   sql_select2 || '   aims_apps a, aims_enterprise_apps e, ';
        sql_select2 :=   sql_select2 || '   bds_contacts x, aims_contacts y '; 
        sql_select2 :=   sql_select2 || 'WHERE ';
        sql_select2 :=   sql_select2 || '   a.apps_id = :4 ';   
        sql_select2 :=   sql_select2 || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded(e.enterprise_apps_id) = 1 ';
        IF (p_if_public = 'T') THEN
            sql_select2 :=   sql_select2 || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_if_public(e.enterprise_apps_id) = 1 ';
        END IF;
        sql_select2 :=   sql_select2 || '   and a.apps_id = e.enterprise_apps_id ';
        sql_select2 :=   sql_select2 || '   and x.contact_id (+) = e.alliance_sponsor  ';
        sql_select2 :=   sql_select2 || '   and y.contact_id (+) = a.tech_contact_id ';        

        OPEN p_out_solution_details FOR sql_select2 USING p_spotlight_type_id, p_spotlight_type_id,
                                                                p_spotlight_type_id, p_solution_id;
      
   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_partner_details%ISOPEN) THEN
            CLOSE p_out_partner_details;
          END IF;
          IF(p_out_solution_details%ISOPEN) THEN
            CLOSE p_out_solution_details;
          END IF;          
          RAISE;                                                                                      
                                                         
															 	
   END get_spotlight_page;

/* -------------------------------------------------------------------------------------------------------------  */

END Bds_Spotlight; -- Package Body BDS_SPOTLIGHT
/

