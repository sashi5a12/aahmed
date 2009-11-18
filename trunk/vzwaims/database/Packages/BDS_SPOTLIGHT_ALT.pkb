CREATE OR REPLACE Package Body BDS_SPOTLIGHT_ALT
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_spotlight_page
         (
           p_solution_id                 IN number,               -- solution_id
           p_spotlight_type_id           IN number,               -- spotlight_type_id
           p_out_partner_details        OUT TYPES.cursor_type,    -- Result cursor
           p_out_solution_details       OUT TYPES.cursor_type     -- Result cursor
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
        sql_select  varchar2(4000);
        sql_select2 varchar2(4000);

   BEGIN

        OPEN p_out_partner_details FOR

            SELECT
               a.alliance_id, a.company_name, a.street_address_1,
               a.steet_address_2, a.city, a.state,
               a.zip_code, a.web_site_url, a.num_full_time_emp,
               c.first_name, c.last_name, c.phone, c.email_address,
               d.first_name, d.last_name, d.phone, d.email_address
            FROM
               aims_alliances a,
               aims_apps ap,
               aims_contacts c,
               aims_contacts d
            WHERE
               ap.apps_id = p_solution_id
               and ap.alliance_id = a.alliance_id
               and c.contact_id (+) = a.bus_contact_id
               and d.contact_id (+) = a.tech_contact_id
             ORDER BY 2;
    




      OPEN p_out_solution_details FOR

      SELECT
         a.apps_id, a.long_desc, a.title,
         x.first_name, x.last_name,
         y.first_name, y.last_name, y.phone, y.email_address,
        (        select
                     max(t.created_date)
                 from
                     aims_ent_apps_spotlights t
                 where
                     t.enterprise_app_id = a.apps_id
                     and t.status = 'ACCEPT'
                     and t.spotlight_type_id = p_spotlight_type_id ),
        (        select
                     spotlight_type_name
                 from
                     aims_spotlight_types u
                 where
                     u.spotlight_type_id = p_spotlight_type_id ),
        (        select
                     v.spotlight_id
                 from
                     aims_ent_apps_spotlights v
                 where
                     v.spotlight_type_id = 9
                     and v.status = 'ACCEPT'
                     and v.enterprise_app_id = a.apps_id ),
         cursor( select
                     r.region_id
                 from
                     aims_ent_apps_region r
                 where
                     r.enterprise_apps_id = e.enterprise_apps_id  ) apps_regions,
         cursor( select
                     i.industry_id
                 from
                     aims_ent_apps_ind_focus i
                 where
                     i.enterprise_apps_id = e.enterprise_apps_id ) apps_ind_focus,
         cursor( select
                     s.spotlight_id, s.spotlight_name, s.spotlight_desc,
                     s.spotlight_file_file_name, s.created_date,
                     substr(s.spotlight_file_file_name, decode(instr(s.spotlight_file_file_name, '.'), 0,
                     length(s.spotlight_file_file_name), instr(s.spotlight_file_file_name, '.')) + 1)
                 from
                     aims_ent_apps_spotlights s
                 where
                     s.enterprise_app_id = a.apps_id
                     and s.status = 'ACCEPT'
                     and s.spotlight_type_id = p_spotlight_type_id ) app_spotlight,
         cursor( select
                     spotlight_type_id,
                     0
                 from
                     aims_spotlight_types
                 where
                     spotlight_type_id not in
                     (select
                         s.spotlight_type_id
                     from
                         aims_ent_apps_spotlights s
                     where
                         s.enterprise_app_id = a.apps_id
                         and s.status = 'ACCEPT')
                  union
                  select
                     a.spotlight_type_id,
                     count(*)
                  from
                     aims_ent_apps_spotlights s,
                     aims_spotlight_types a
                  where
                     s.enterprise_app_id = a.apps_id
                     and s.status = 'ACCEPT'
                     and a.spotlight_type_id = s.spotlight_type_id (+)
                  group by  a.spotlight_type_id ) spot_type_count
      FROM
         aims_apps a, aims_enterprise_apps e,
         bds_contacts x, aims_contacts y
      WHERE
         a.apps_id = p_solution_id
         and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded(e.enterprise_apps_id) = 1
         and a.apps_id = e.enterprise_apps_id
         and x.contact_id (+) = e.alliance_sponsor
         and y.contact_id (+) = a.tech_contact_id ;
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

END BDS_SPOTLIGHT_ALT; -- Package Body BDS_SPOTLIGHT_ALT
/

