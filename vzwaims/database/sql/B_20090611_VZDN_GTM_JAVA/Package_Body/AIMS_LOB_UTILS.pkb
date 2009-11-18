CREATE OR REPLACE PACKAGE BODY Aims_Lob_Utils
IS

/* -------------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_into_uploaded_documents
         (
           p_document_name          IN  VARCHAR2,  -- name of the document
           p_document_content_type  IN  VARCHAR2,  -- content type of the document
           p_pk_expr_to_table       IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_name          IN  VARCHAR2,  -- name of the table in which the record will be updated
           p_to_table_col_name      IN  VARCHAR2,  -- name of the column of the table in which the record will be updated
           p_user_id                IN  VARCHAR2   -- user id of the current user
         )
    IS

    /*
    || Overview:        Inserts a record in the aims_uploaded_documents for the uploaded documents
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 08-13-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_col_name          VARCHAR2(100);
        v_col_value         VARCHAR2(100);
        v_equal_pos         NUMBER;

    BEGIN

        v_equal_pos := INSTR(p_pk_expr_to_table, '=');
        v_col_name  := trim(SUBSTR(p_pk_expr_to_table, 1, v_equal_pos - 1));
        v_col_value := trim(SUBSTR(p_pk_expr_to_table, v_equal_pos + 1));

        INSERT INTO AIMS_UPLOADED_DOCUMENTS
            (   document_id,
                document_name,
                document_type,
                document_content_type,
                upload_table_name,
                upload_col_name,
                upload_pk_name,
                upload_pk_value,
                created_by,
                created_date
            )
        VALUES
            (   seq_pk_uploaded_documents.NEXTVAL,
                p_document_name,
                p_to_table_col_name,
                p_document_content_type,
                p_to_table_name,
                p_to_table_col_name,
                v_col_name,
                v_col_value,
                p_user_id,
                SYSDATE
             );
    EXCEPTION
        WHEN OTHERS THEN
            NULL;
    END insert_into_uploaded_documents;

/* -------------------------------------------------------------------------------------------------------------------  */

    PROCEDURE copy_lob_from_temp_table
         ( p_temp_table_id          IN  NUMBER,    -- primary key of the temp table
           p_pk_expr_to_table       IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_name          IN  VARCHAR2,  -- name of the table in which the record will be updated
           p_to_table_col_name      IN  VARCHAR2,  -- name of the column of the table in which the record will be updated
           p_user_id                IN  VARCHAR2   -- user id of the current user
         )
    IS

    /*
    || Overview:        Copies a lob column value from temp table to a given table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 12-17-2003       rqazi           Created
    || 09-21-2007       MSQ             Modified. To clone new images for WAP FTP module.
    ||
    ||
    ||
    ||
    */
        sql_update          VARCHAR2(2000);
        v_file              BLOB;
        v_file_name         VARCHAR2(150);
        v_file_content_type VARCHAR2(100);

   BEGIN
        SELECT
            temp_file,
            temp_file_name,
            temp_file_content_type
        INTO
            v_file,
            v_file_name,
            v_file_content_type
        FROM
            AIMS_TEMP_FILES
        WHERE
            temp_file_id = p_temp_table_id
            AND created_by = p_user_id;


        sql_update := sql_update || 'update ';
        sql_update := sql_update ||     p_to_table_name || ' ';
        sql_update := sql_update || 'set ';
        sql_update := sql_update ||     p_to_table_col_name;
        sql_update := sql_update || '   = :1, ';
        sql_update := sql_update ||     p_to_table_col_name || '_file_name ';
        sql_update := sql_update || '   = :2, ';
        sql_update := sql_update ||     p_to_table_col_name || '_content_type ';
        sql_update := sql_update || '   = :3 ';
        sql_update := sql_update || 'where ';
        sql_update := sql_update ||     p_pk_expr_to_table;

        --p('Value of sql_update='||sql_update);
        EXECUTE IMMEDIATE sql_update USING v_file, v_file_name, v_file_content_type;

        DELETE
        FROM
            AIMS_TEMP_FILES
        WHERE
            temp_file_id = p_temp_table_id
            AND created_by = p_user_id;

        insert_into_uploaded_documents
         (
           v_file_name,
           v_file_content_type,
           p_pk_expr_to_table,
           p_to_table_name,
           p_to_table_col_name,
           p_user_id
         );

   END copy_lob_from_temp_table;


   
/* -------------------------------------------------------------------------------------------------------------------  */

    PROCEDURE copy_lob_from_to_table
         ( p_from_table_col_name     IN  VARCHAR2,  -- name of the column of the table which will be selected for update. 
           p_from_table_name         IN  VARCHAR2,  -- name of table which record will be selected for update.
           p_pk_expr_from_table      IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_col_name       IN  VARCHAR2,  -- name of the column of the table which record will be updated
           p_to_table_name           IN  VARCHAR2,  -- name of the table which column will be updated
           p_pk_expr_to_table        IN  VARCHAR2   -- expression in the to pkid = value
         )
    IS

    /*
    || Overview:        Copies a lob column value from table to a given table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 26-03-2003       MSQ           Created
	|| 11-16-2008       MMahmood       Modified
    || 
    ||
    ||
    ||
    ||
    */
        sql_update          VARCHAR2(2000);

   BEGIN

        sql_update := sql_update || 'update ';
        sql_update := sql_update ||     p_to_table_name;
        sql_update := sql_update || ' set ';
        sql_update := sql_update ||    '('||p_to_table_col_name||',';
        sql_update := sql_update ||         p_to_table_col_name||'_file_name,';
        sql_update := sql_update ||         p_to_table_col_name||'_content_type)';
        sql_update := sql_update || '   =  (select ';
        sql_update := sql_update ||         p_from_table_col_name||' ,';
        sql_update := sql_update ||         p_from_table_col_name||'_file_name,';
        sql_update := sql_update ||         p_from_table_col_name||'_content_type';
        sql_update := sql_update || ' from ';
        sql_update := sql_update ||    p_from_table_name;
        sql_update := sql_update || ' where ';
        sql_update := sql_update ||     p_pk_expr_from_table|| ' ) ';
        sql_update := sql_update || ' where ';
        sql_update := sql_update ||     p_pk_expr_to_table;

       EXECUTE IMMEDIATE sql_update;

   END copy_lob_from_to_table;


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE clone_images
         ( p_clone_from_app_id      IN  NUMBER,    -- application id to clone from
           p_clone_to_app_id        IN  VARCHAR2,  -- application id to clone to
           p_platform_id            IN  VARCHAR2   -- platform id
         )
   IS
        v_faq_doc AIMS_APPS.faq_doc%TYPE;
        v_faq_doc_file_name AIMS_APPS.faq_doc_file_name%TYPE;
        v_faq_doc_content_type AIMS_APPS.faq_doc_content_type%TYPE;

        v_user_guide AIMS_APPS.user_guide%TYPE;
        v_user_guide_file_name AIMS_APPS.user_guide_file_name%TYPE;
        v_user_guide_content_type AIMS_APPS.user_guide_content_type%TYPE;

        v_test_plan_results AIMS_APPS.test_plan_results%TYPE;
        v_test_plan_results_file_name AIMS_APPS.test_plan_results_file_name%TYPE;
        v_test_plan_results_cont_type AIMS_APPS.test_plan_results_content_type%TYPE;

        v_splash_screen_eps AIMS_APPS.splash_screen_eps%TYPE;
        v_splash_screen_eps_file_name AIMS_APPS.splash_screen_eps_file_name%TYPE;
        v_splash_screen_eps_cont_type AIMS_APPS.splash_screen_eps_content_type%TYPE;

        v_screen_jpeg AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_2 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_2_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_2_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_3 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_3_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_3_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_4 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_4_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_4_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_5 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_5_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_5_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_flash_demo AIMS_APPS.flash_demo%TYPE;
        v_flash_demo_file_name AIMS_APPS.flash_demo_file_name%TYPE;
        v_flash_demo_content_type AIMS_APPS.flash_demo_content_type%TYPE;

        v_active_screen_eps AIMS_APPS.active_screen_eps%TYPE;
        v_active_screen_eps_file_name AIMS_APPS.active_screen_eps_file_name%TYPE;
        v_active_screen_eps_cont_type AIMS_APPS.active_screen_eps_content_type%TYPE;


  --For BREW
        v_style_guide AIMS_BREW_APPS.style_guide%TYPE;
        v_style_guide_file_name AIMS_BREW_APPS.style_guide_file_name%TYPE;
        v_style_guide_content_type AIMS_BREW_APPS.style_guide_content_type%TYPE;

        v_mktg_slick_sheet AIMS_BREW_APPS.mktg_slick_sheet%TYPE;
        v_mktg_slick_sheet_file_name AIMS_BREW_APPS.mktg_slick_sheet_file_name%TYPE;
        v_mktg_slick_sheet_cont_type AIMS_BREW_APPS.mktg_slick_sheet_content_type%TYPE;

     v_app_logo_bw_small AIMS_BREW_APPS.app_logo_bw_small%TYPE;
        v_app_logo_bw_small_file_name AIMS_BREW_APPS.app_logo_bw_small_file_name%TYPE;
        v_app_logo_bw_small_cont_type AIMS_BREW_APPS.app_logo_bw_small_content_type%TYPE;

     v_app_logo_bw_large AIMS_BREW_APPS.app_logo_bw_large%TYPE;
        v_app_logo_bw_large_file_name AIMS_BREW_APPS.app_logo_bw_large_file_name%TYPE;
        v_app_logo_bw_large_cont_type AIMS_BREW_APPS.app_logo_bw_large_content_type%TYPE;

     v_app_logo_clrsmall AIMS_BREW_APPS.app_logo_clrsmall%TYPE;
        v_app_logo_clrsmall_file_name AIMS_BREW_APPS.app_logo_clrsmall_file_name%TYPE;
        v_app_logo_clrsmall_cont_type AIMS_BREW_APPS.app_logo_clrsmall_content_type%TYPE;

  v_app_logo_clrlarge AIMS_BREW_APPS.app_logo_clrlarge%TYPE;
        v_app_logo_clrlarge_file_name AIMS_BREW_APPS.app_logo_clrlarge_file_name%TYPE;
        v_app_logo_clrlarge_cont_type AIMS_BREW_APPS.app_logo_clrlarge_content_type%TYPE;

  v_clr_pub_logo AIMS_BREW_APPS.clr_pub_logo%TYPE;
        v_clr_pub_logo_file_name AIMS_BREW_APPS.clr_pub_logo_file_name%TYPE;
        v_clr_pub_logo_cont_type AIMS_BREW_APPS.clr_pub_logo_content_type%TYPE;

        v_prog_guide AIMS_BREW_APPS.prog_guide%TYPE;
        v_prog_guide_file_name AIMS_BREW_APPS.prog_guide_file_name%TYPE;
        v_prog_guide_cont_type AIMS_BREW_APPS.prog_guide_content_type%TYPE;

        v_app_title_name AIMS_BREW_APPS.app_title_name%TYPE;
        v_app_title_name_file_name AIMS_BREW_APPS.app_title_name_file_name%TYPE;
        v_app_title_name_cont_type AIMS_BREW_APPS.app_title_name_content_type%TYPE;

        v_brew_presentation AIMS_BREW_APPS.brew_presentation%TYPE;
        v_brew_presentation_file_name AIMS_BREW_APPS.brew_presentation_file_name%TYPE;
        v_brew_presentation_cont_type AIMS_BREW_APPS.brew_presentation_content_type%TYPE;

        v_brew_company_logo AIMS_BREW_APPS.company_logo%TYPE;
        v_brew_company_logo_file_name AIMS_BREW_APPS.company_logo_file_name%TYPE;
        v_brew_company_logo_cont_type AIMS_BREW_APPS.company_logo_content_type%TYPE;

        v_brew_title_shot AIMS_BREW_APPS.title_shot%TYPE;
        v_brew_title_shot_file_name AIMS_BREW_APPS.title_shot_file_name%TYPE;
        v_brew_title_shot_cont_type AIMS_BREW_APPS.title_shot_content_type%TYPE;
        
        v_brew_high_res_spl AIMS_BREW_APPS.high_res_splash%TYPE;
        v_brew_high_res_spl_cont_type AIMS_BREW_APPS.high_res_splash_content_type%TYPE;
        v_brew_high_res_spl_file_name AIMS_BREW_APPS.high_res_splash_file_name%TYPE;
                            
        v_brew_high_res_act AIMS_BREW_APPS.high_res_active%TYPE;
        v_brew_high_res_act_cont_type AIMS_BREW_APPS.high_res_active_content_type%TYPE;
        v_brew_high_res_act_file_name AIMS_BREW_APPS.high_res_active_file_name%TYPE;                
        
        v_brew_splash_screen AIMS_BREW_APPS.splash_screen%TYPE;
        v_brew_splash_screen_cont_type AIMS_BREW_APPS.splash_screen_content_type%TYPE;
        v_brew_splash_screen_file_name AIMS_BREW_APPS.splash_screen_file_name%TYPE;        
                
        v_brew_small_splash AIMS_BREW_APPS.small_splash%TYPE;
        v_brew_small_splash_cont_type AIMS_BREW_APPS.small_splash_content_type%TYPE;
        v_brew_small_splash_file_name AIMS_BREW_APPS.small_splash_file_name%TYPE;        
        
        v_brew_act_screen_1 AIMS_BREW_APPS.active_screen_1%TYPE;
        v_brew_act_screen_1_cont_type AIMS_BREW_APPS.active_screen_1_content_type%TYPE;
        v_brew_act_screen_1_file_name AIMS_BREW_APPS.active_screen_1_file_name%TYPE;        
                
        v_brew_act_screen_2 AIMS_BREW_APPS.active_screen_2%TYPE;
        v_brew_act_screen_2_cont_type AIMS_BREW_APPS.active_screen_2_content_type%TYPE;
        v_brew_act_screen_2_file_name AIMS_BREW_APPS.active_screen_2_file_name%TYPE;        
                
        v_brew_sml_active_screen AIMS_BREW_APPS.sml_active_screen%TYPE;
        v_brew_smlactscreen_cont_type AIMS_BREW_APPS.sml_active_screen_content_type%TYPE;
        v_brew_smlactscreen_file_name AIMS_BREW_APPS.sml_active_screen_file_name%TYPE;        
                
        v_brew_app_act_flash AIMS_BREW_APPS.app_actiion_flash%TYPE;
        v_brew_app_act_flash_cont_type AIMS_BREW_APPS.app_actiion_flash_content_type%TYPE;
        v_brew_app_act_flash_file_name AIMS_BREW_APPS.app_actiion_flash_file_name%TYPE;
                        
        v_brew_app_gif_act AIMS_BREW_APPS.app_gif_action%TYPE;
        v_brew_app_gif_act_cont_type AIMS_BREW_APPS.app_gif_action_content_type%TYPE;
        v_brew_app_gif_act_file_name AIMS_BREW_APPS.app_gif_action_file_name%TYPE;        
                
        v_brew_media_store AIMS_BREW_APPS.media_store%TYPE;
        v_brew_media_store_cont_type AIMS_BREW_APPS.media_store_content_type%TYPE;        
        v_brew_media_store_file_name AIMS_BREW_APPS.media_store_file_name%TYPE;
        
        v_brew_flash_dem_mov AIMS_BREW_APPS.flash_demo_movie%TYPE;
        v_brew_flash_dem_mov_cont_type AIMS_BREW_APPS.flash_demo_movie_content_type%TYPE;        
        v_brew_flash_dem_mov_file_name AIMS_BREW_APPS.flash_demo_movie_file_name%TYPE;
        
        v_brew_dash_scr_img AIMS_BREW_APPS.dashboard_scr_img%TYPE;
        v_brew_dash_scr_img_cont_type AIMS_BREW_APPS.dashboard_scr_img_content_type%TYPE;        
        v_brew_dash_scr_img_file_name AIMS_BREW_APPS.dashboard_scr_img_file_name%TYPE;
                    
  --For Enterprise
        v_presentation AIMS_ENTERPRISE_APPS.presentation%TYPE;
        v_presentation_file_name AIMS_ENTERPRISE_APPS.presentation_file_name%TYPE;
        v_presentation_content_type AIMS_ENTERPRISE_APPS.presentation_content_type%TYPE;


  --For MMS
        v_sample_content AIMS_MMS_APPS.sample_content%TYPE;
        v_sample_content_file_name AIMS_MMS_APPS.sample_content_file_name%TYPE;
        v_sample_content_content_type AIMS_MMS_APPS.sample_content_content_type%TYPE;


        --For SMS
        v_message_flow AIMS_SMS_APPS.message_flow%TYPE;
        v_message_flow_file_name AIMS_SMS_APPS.message_flow_file_name%TYPE;
        v_message_flow_content_type AIMS_SMS_APPS.message_flow_content_type%TYPE;


     --For WAP
        v_screen_shot AIMS_WAP_APPS.screen_shot%TYPE;
        v_screen_shot_file_name AIMS_WAP_APPS.screen_shot_file_name%TYPE;
        v_screen_shot_content_type AIMS_WAP_APPS.screen_shot_content_type%TYPE;

        v_wap_presentation AIMS_WAP_APPS.presentation%TYPE;
        v_wap_presentation_file_name AIMS_WAP_APPS.presentation_file_name%TYPE;
        v_wap_presentation_cont_type AIMS_WAP_APPS.presentation_content_type%TYPE;

        v_wap_product_logo AIMS_WAP_APPS.product_logo%TYPE;
        v_wap_product_logo_file_name AIMS_WAP_APPS.product_logo_file_name%TYPE;
        v_wap_product_logo_cont_type AIMS_WAP_APPS.product_logo_content_type%TYPE;

        v_wap_product_icon AIMS_WAP_APPS.product_icon%TYPE;
        v_wap_product_icon_file_name AIMS_WAP_APPS.product_icon_file_name%TYPE;
        v_wap_product_icon_cont_type AIMS_WAP_APPS.product_icon_content_type%TYPE;

        v_wap_img_medium AIMS_WAP_APPS.app_img_medium%TYPE;
        v_wap_img_medium_file_name AIMS_WAP_APPS.app_img_medium_file_name%TYPE;
        v_wap_img_medium_content_type AIMS_WAP_APPS.app_img_medium_content_type%TYPE;
        
        v_wap_img_potrait AIMS_WAP_APPS.app_img_potrait%TYPE;
        v_wap_img_potrait_file_name AIMS_WAP_APPS.app_img_potrait_file_name%TYPE;
        v_wap_img_potrait_content_type AIMS_WAP_APPS.app_img_potrait_content_type%TYPE;
        
        v_wap_img_landscape AIMS_WAP_APPS.app_img_landscape%TYPE;
        v_wap_img_landscape_file_name AIMS_WAP_APPS.app_img_landscape_file_name%TYPE;
        v_wap_img_landscape_cont_type AIMS_WAP_APPS.app_img_landscape_content_type%TYPE;

        
     --For VCAST
        v_sample_clip_1 AIMS_VCAST_APPS.sample_clip_1%TYPE;
        v_sample_clip_1_file_name AIMS_VCAST_APPS.sample_clip_1_file_name%TYPE;
        v_sample_clip_1_content_type AIMS_VCAST_APPS.sample_clip_1_content_type%TYPE;

        v_sample_clip_2 AIMS_VCAST_APPS.sample_clip_2%TYPE;
        v_sample_clip_2_file_name AIMS_VCAST_APPS.sample_clip_2_file_name%TYPE;
        v_sample_clip_2_content_type AIMS_VCAST_APPS.sample_clip_2_content_type%TYPE;
        
        v_sample_clip_3 AIMS_VCAST_APPS.sample_clip_3%TYPE;
        v_sample_clip_3_file_name AIMS_VCAST_APPS.sample_clip_3_file_name%TYPE;
        v_sample_clip_3_content_type AIMS_VCAST_APPS.sample_clip_3_content_type%TYPE;

     
     -- For VZ_APPS_ZON
        v_app_landing_page AIMS_VZAPPZONE_APPS.app_landing_page%TYPE;
        v_app_landing_page_content_typ AIMS_VZAPPZONE_APPS.app_landing_page_content_type%TYPE;
        v_app_landing_page_file_name AIMS_VZAPPZONE_APPS.app_landing_page_file_name%TYPE;

        v_app_presentation AIMS_VZAPPZONE_APPS.app_presentation%TYPE;
        v_app_presentation_content_typ AIMS_VZAPPZONE_APPS.app_presentation_content_type%TYPE;
        v_app_presentation_file_name AIMS_VZAPPZONE_APPS.app_presentation_file_name%TYPE;

                 
     --For Dashboard
        v_dash_pub_logo AIMS_DASHBOARD_APPS.clr_pub_logo%TYPE;
        v_dash_pub_logo_file_name AIMS_DASHBOARD_APPS.clr_pub_logo_file_name%TYPE;
        v_dash_pub_logo_cont_type AIMS_DASHBOARD_APPS.clr_pub_logo_content_type%TYPE;

        v_dash_title_name AIMS_DASHBOARD_APPS.app_title_name%TYPE;
        v_dash_title_name_file_name AIMS_DASHBOARD_APPS.app_title_name_file_name%TYPE;
        v_dash_title_name_cont_type AIMS_DASHBOARD_APPS.app_title_name_content_type%TYPE;

        v_dash_content_file AIMS_DASHBOARD_APPS.content_zip_file%TYPE;
        v_dash_content_file_file_name AIMS_DASHBOARD_APPS.content_zip_file_file_name%TYPE;
        v_dash_content_file_cont_type AIMS_DASHBOARD_APPS.content_zip_file_content_type%TYPE;

        v_dash_company_logo AIMS_DASHBOARD_APPS.company_logo%TYPE;
        v_dash_company_logo_file_name AIMS_DASHBOARD_APPS.company_logo_file_name%TYPE;
        v_dash_company_logo_cont_type AIMS_DASHBOARD_APPS.company_logo_content_type%TYPE;
        
        v_dash_title_image AIMS_DASHBOARD_APPS.title_image%TYPE;
        v_dash_title_image_file_name AIMS_DASHBOARD_APPS.title_image_file_name%TYPE;
        v_dash_title_image_cont_type AIMS_DASHBOARD_APPS.title_image_content_type%TYPE;
        
	--For JAVA
	  v_j_hr_pub_logo		        AIMS_JAVA_APPS.hr_publisher_logo%TYPE;
	  v_j_hr_pub_logo_file_name		AIMS_JAVA_APPS.hr_publisher_logo_file_name%TYPE;
	  v_j_hr_pub_logo_content_type	AIMS_JAVA_APPS.hr_publisher_logo_content_type%TYPE;

	  v_j_ch_title_icon		        AIMS_JAVA_APPS.chnl_title_icon%TYPE;
	  v_j_ch_title_icon_file_name		AIMS_JAVA_APPS.chnl_title_icon_file_name%TYPE;
	  v_j_ch_title_icon_content_type	AIMS_JAVA_APPS.chnl_title_icon_content_type%TYPE;

	  v_j_sp_scr_shot		        AIMS_JAVA_APPS.splh_screen_shot%TYPE;
	  v_j_sp_scr_shot_file_name		AIMS_JAVA_APPS.splh_screen_shot_file_name%TYPE;
	  v_j_sp_scr_shot_content_type	AIMS_JAVA_APPS.splh_screen_shot_content_type%TYPE;

	  v_j_actv_scr_shot		        AIMS_JAVA_APPS.actv_screen_shot%TYPE;
	  v_j_actv_scr_shot_file_name		AIMS_JAVA_APPS.actv_screen_shot_file_name%TYPE;
	  v_j_actv_scr_shot_content_type	AIMS_JAVA_APPS.actv_screen_shot_content_type%TYPE;
	  
	  v_j_app_scr		        AIMS_JAVA_APPS.app_screen_shot%TYPE;
	  v_j_app_scr_file_name		AIMS_JAVA_APPS.app_screen_shot_file_name%TYPE;
	  v_j_app_scr_content_type	AIMS_JAVA_APPS.app_screen_shot_content_type%TYPE;
	  
	  v_j_app_scr_1		        AIMS_JAVA_APPS.app_screen_shot_1%TYPE;
	  v_j_app_scr_1_file_name	AIMS_JAVA_APPS.app_screen_shot_1_file_name%TYPE;
	  v_j_app_scr_1_content_type	AIMS_JAVA_APPS.app_screen_shot_1_content_type%TYPE;
	  
  	  v_j_app_scr_2		        AIMS_JAVA_APPS.app_screen_shot_2%TYPE;
	  v_j_app_scr_2_file_name	AIMS_JAVA_APPS.app_screen_shot_2_file_name%TYPE;
	  v_j_app_scr_2_content_type	AIMS_JAVA_APPS.app_screen_shot_2_content_type%TYPE;
	  
  	  v_j_app_scr_3		        AIMS_JAVA_APPS.app_screen_shot_3%TYPE;
	  v_j_app_scr_3_file_name	AIMS_JAVA_APPS.app_screen_shot_3_file_name%TYPE;
	  v_j_app_scr_3_content_type	AIMS_JAVA_APPS.app_screen_shot_3_content_type%TYPE;
	  
  	  v_j_app_scr_4		        AIMS_JAVA_APPS.app_screen_shot_4%TYPE;
	  v_j_app_scr_4_file_name	AIMS_JAVA_APPS.app_screen_shot_4_file_name%TYPE;
	  v_j_app_scr_4_content_type	AIMS_JAVA_APPS.app_screen_shot_4_content_type%TYPE;
	  
	  v_j_app_scr_5		        AIMS_JAVA_APPS.app_screen_shot_5%TYPE;
	  v_j_app_scr_5_file_name	AIMS_JAVA_APPS.app_screen_shot_5_file_name%TYPE;
	  v_j_app_scr_5_content_type	AIMS_JAVA_APPS.app_screen_shot_5_content_type%TYPE;
	  
  	  v_j_app_scr_6		        AIMS_JAVA_APPS.app_screen_shot_6%TYPE;
	  v_j_app_scr_6_file_name	AIMS_JAVA_APPS.app_screen_shot_6_file_name%TYPE;
	  v_j_app_scr_6_content_type	AIMS_JAVA_APPS.app_screen_shot_6_content_type%TYPE;
	  
  	  v_j_app_scr_7		        AIMS_JAVA_APPS.app_screen_shot_7%TYPE;
	  v_j_app_scr_7_file_name	AIMS_JAVA_APPS.app_screen_shot_7_file_name%TYPE;
	  v_j_app_scr_7_content_type	AIMS_JAVA_APPS.app_screen_shot_7_content_type%TYPE;
	  
  	  v_j_app_scr_8		        AIMS_JAVA_APPS.app_screen_shot_8%TYPE;
	  v_j_app_scr_8_file_name	AIMS_JAVA_APPS.app_screen_shot_8_file_name%TYPE;
	  v_j_app_scr_8_content_type	AIMS_JAVA_APPS.app_screen_shot_8_content_type%TYPE;	  

  	  v_j_app_scr_9		        AIMS_JAVA_APPS.app_screen_shot_9%TYPE;
	  v_j_app_scr_9_file_name	AIMS_JAVA_APPS.app_screen_shot_9_file_name%TYPE;
	  v_j_app_scr_9_content_type	AIMS_JAVA_APPS.app_screen_shot_9_content_type%TYPE;	  
	  
	  v_j_faq_file		        		AIMS_JAVA_APPS.faq_file%TYPE;
	  v_j_faq_file_file_name				AIMS_JAVA_APPS.faq_file_file_name%TYPE;
	  v_j_faq_file_content_type			AIMS_JAVA_APPS.faq_file_content_type%TYPE;
	  
  	  v_j_company_logo		        	AIMS_JAVA_APPS.company_logo%TYPE;
	  v_j_company_logo_file_name			AIMS_JAVA_APPS.company_logo_file_name%TYPE;
	  v_j_company_logo_content_type		AIMS_JAVA_APPS.company_logo_content_type%TYPE;

   	  v_j_app_title		     		AIMS_JAVA_APPS.app_title_name%TYPE;
	  v_j_app_title_file_name		AIMS_JAVA_APPS.app_title_name_file_name%TYPE;
	  v_j_app_title_content_type	AIMS_JAVA_APPS.app_title_name_content_type%TYPE;
              
   BEGIN

        SELECT
            faq_doc, faq_doc_file_name, faq_doc_content_type,
            user_guide, user_guide_file_name, user_guide_content_type,
            test_plan_results, test_plan_results_file_name, test_plan_results_content_type,
            splash_screen_eps, splash_screen_eps_file_name, splash_screen_eps_content_type,
            screen_jpeg, screen_jpeg_file_name, screen_jpeg_content_type,
            screen_jpeg_2, screen_jpeg_2_file_name, screen_jpeg_2_content_type,
            screen_jpeg_3, screen_jpeg_3_file_name, screen_jpeg_3_content_type,
            screen_jpeg_4, screen_jpeg_4_file_name, screen_jpeg_4_content_type,
            screen_jpeg_5, screen_jpeg_5_file_name, screen_jpeg_5_content_type,
            flash_demo, flash_demo_file_name, flash_demo_content_type,
            active_screen_eps, active_screen_eps_file_name, active_screen_eps_content_type
        INTO
            v_faq_doc, v_faq_doc_file_name, v_faq_doc_content_type,
            v_user_guide, v_user_guide_file_name, v_user_guide_content_type,
            v_test_plan_results, v_test_plan_results_file_name, v_test_plan_results_cont_type,
            v_splash_screen_eps, v_splash_screen_eps_file_name, v_splash_screen_eps_cont_type,
            v_screen_jpeg, v_screen_jpeg_file_name, v_screen_jpeg_content_type,
            v_screen_jpeg_2, v_screen_jpeg_2_file_name, v_screen_jpeg_2_content_type,
            v_screen_jpeg_3, v_screen_jpeg_3_file_name, v_screen_jpeg_3_content_type,
            v_screen_jpeg_4, v_screen_jpeg_4_file_name, v_screen_jpeg_4_content_type,
            v_screen_jpeg_5, v_screen_jpeg_5_file_name, v_screen_jpeg_5_content_type,
            v_flash_demo, v_flash_demo_file_name, v_flash_demo_content_type,
            v_active_screen_eps, v_active_screen_eps_file_name, v_active_screen_eps_cont_type
        FROM
            AIMS_APPS
        WHERE
            apps_id = p_clone_from_app_id;

        UPDATE
            AIMS_APPS
        SET
            faq_doc = v_faq_doc,
            faq_doc_file_name = v_faq_doc_file_name,
            faq_doc_content_type = v_faq_doc_content_type,

            user_guide = v_user_guide,
            user_guide_file_name = v_user_guide_file_name,
            user_guide_content_type = v_user_guide_content_type,

            test_plan_results = v_test_plan_results,
            test_plan_results_file_name = v_test_plan_results_file_name,
            test_plan_results_content_type = v_test_plan_results_cont_type,

            splash_screen_eps = v_splash_screen_eps,
            splash_screen_eps_file_name = v_splash_screen_eps_file_name,
            splash_screen_eps_content_type = v_splash_screen_eps_cont_type,

            screen_jpeg = v_screen_jpeg,
            screen_jpeg_file_name = v_screen_jpeg_file_name,
            screen_jpeg_content_type = v_screen_jpeg_content_type,

            screen_jpeg_2 = v_screen_jpeg_2,
            screen_jpeg_2_file_name = v_screen_jpeg_2_file_name,
            screen_jpeg_2_content_type = v_screen_jpeg_2_content_type,

            screen_jpeg_3 = v_screen_jpeg_3,
            screen_jpeg_3_file_name = v_screen_jpeg_3_file_name,
            screen_jpeg_3_content_type = v_screen_jpeg_3_content_type,

            screen_jpeg_4 = v_screen_jpeg_4,
            screen_jpeg_4_file_name = v_screen_jpeg_4_file_name,
            screen_jpeg_4_content_type = v_screen_jpeg_4_content_type,

            screen_jpeg_5 = v_screen_jpeg_5,
            screen_jpeg_5_file_name = v_screen_jpeg_5_file_name,
            screen_jpeg_5_content_type = v_screen_jpeg_5_content_type,



            flash_demo = v_flash_demo,
            flash_demo_file_name = v_flash_demo_file_name,
            flash_demo_content_type = v_flash_demo_content_type,

            active_screen_eps = v_active_screen_eps,
            active_screen_eps_file_name = v_active_screen_eps_file_name,
            active_screen_eps_content_type = v_active_screen_eps_cont_type

        WHERE
            apps_id = p_clone_to_app_id;


        -- For BREW
        IF (p_platform_id = 1) THEN
            SELECT
                style_guide, style_guide_file_name, style_guide_content_type,
                mktg_slick_sheet, mktg_slick_sheet_file_name, mktg_slick_sheet_content_type,
                app_logo_bw_small, app_logo_bw_small_file_name, app_logo_bw_small_content_type,
                app_logo_bw_large, app_logo_bw_large_file_name, app_logo_bw_large_content_type,
                app_logo_clrsmall, app_logo_clrsmall_file_name, app_logo_clrsmall_content_type,
                app_logo_clrlarge, app_logo_clrlarge_file_name, app_logo_clrlarge_content_type,
                clr_pub_logo, clr_pub_logo_file_name, clr_pub_logo_content_type,
                prog_guide, prog_guide_file_name, prog_guide_content_type,
                app_title_name, app_title_name_file_name, app_title_name_content_type,
                brew_presentation, brew_presentation_file_name, brew_presentation_content_type,
                company_logo, company_logo_file_name, company_logo_content_type, 
                title_shot, title_shot_file_name, title_shot_content_type,

                high_res_splash, high_res_splash_file_name, high_res_splash_content_type,    
                high_res_active, high_res_active_file_name, high_res_active_content_type,
                splash_screen, splash_screen_file_name, splash_screen_content_type,
                small_splash, small_splash_file_name, small_splash_content_type,
                active_screen_1, active_screen_1_file_name, active_screen_1_content_type,
                active_screen_2, active_screen_2_file_name, active_screen_2_content_type,
                sml_active_screen, sml_active_screen_file_name, sml_active_screen_content_type,
                app_actiion_flash, app_actiion_flash_file_name, app_actiion_flash_content_type,
                app_gif_action, app_gif_action_file_name, app_gif_action_content_type,
                media_store, media_store_file_name, media_store_content_type,
                flash_demo_movie,flash_demo_movie_file_name,flash_demo_movie_content_type,
                dashboard_scr_img,dashboard_scr_img_file_name,dashboard_scr_img_content_type
        
        
            INTO
                v_style_guide, v_style_guide_file_name, v_style_guide_content_type,
                v_mktg_slick_sheet, v_mktg_slick_sheet_file_name, v_mktg_slick_sheet_cont_type,
                v_app_logo_bw_small, v_app_logo_bw_small_file_name, v_app_logo_bw_small_cont_type,
                v_app_logo_bw_large, v_app_logo_bw_large_file_name, v_app_logo_bw_large_cont_type,
                v_app_logo_clrsmall, v_app_logo_clrsmall_file_name, v_app_logo_clrsmall_cont_type,
                v_app_logo_clrlarge, v_app_logo_clrlarge_file_name, v_app_logo_clrlarge_cont_type,
                v_clr_pub_logo, v_clr_pub_logo_file_name, v_clr_pub_logo_cont_type,
                v_prog_guide, v_prog_guide_file_name, v_prog_guide_cont_type,
                v_app_title_name, v_app_title_name_file_name, v_app_title_name_cont_type,
                v_brew_presentation, v_brew_presentation_file_name, v_brew_presentation_cont_type,
                v_brew_company_logo, v_brew_company_logo_file_name, v_brew_company_logo_cont_type, 
                v_brew_title_shot, v_brew_title_shot_file_name, v_brew_title_shot_cont_type,
                
                v_brew_high_res_spl, v_brew_high_res_spl_file_name, v_brew_high_res_spl_cont_type ,
                v_brew_high_res_act, v_brew_high_res_act_file_name, v_brew_high_res_act_cont_type, 
                v_brew_splash_screen, v_brew_splash_screen_file_name, v_brew_splash_screen_cont_type,
                v_brew_small_splash, v_brew_small_splash_file_name, v_brew_small_splash_cont_type,
                v_brew_act_screen_1, v_brew_act_screen_1_file_name, v_brew_act_screen_1_cont_type, 
                v_brew_act_screen_2, v_brew_act_screen_2_file_name, v_brew_act_screen_2_cont_type, 
                v_brew_sml_active_screen, v_brew_smlactscreen_file_name, v_brew_smlactscreen_cont_type, 
                v_brew_app_act_flash, v_brew_app_act_flash_file_name, v_brew_app_act_flash_cont_type, 
                v_brew_app_gif_act, v_brew_app_gif_act_file_name, v_brew_app_gif_act_cont_type, 
                v_brew_media_store, v_brew_media_store_file_name, v_brew_media_store_cont_type, 
                v_brew_flash_dem_mov, v_brew_flash_dem_mov_file_name, v_brew_flash_dem_mov_cont_type,
                v_brew_dash_scr_img, v_brew_dash_scr_img_file_name, v_brew_dash_scr_img_cont_type
                
                
            FROM
                AIMS_BREW_APPS
            WHERE
                brew_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_BREW_APPS
            SET
                style_guide = v_style_guide,
                style_guide_file_name = v_style_guide_file_name,
                style_guide_content_type = v_style_guide_content_type,

                mktg_slick_sheet = v_mktg_slick_sheet,
                mktg_slick_sheet_file_name = v_mktg_slick_sheet_file_name,
                mktg_slick_sheet_content_type = v_mktg_slick_sheet_cont_type,

                app_logo_bw_small = v_app_logo_bw_small,
                app_logo_bw_small_file_name = v_app_logo_bw_small_file_name,
                app_logo_bw_small_content_type = v_app_logo_bw_small_cont_type,

                app_logo_bw_large = v_app_logo_bw_large,
                app_logo_bw_large_file_name = v_app_logo_bw_large_file_name,
                app_logo_bw_large_content_type = v_app_logo_bw_large_cont_type,

                app_logo_clrsmall = v_app_logo_clrsmall,
                app_logo_clrsmall_file_name = v_app_logo_clrsmall_file_name,
                app_logo_clrsmall_content_type = v_app_logo_clrsmall_cont_type,

                app_logo_clrlarge = v_app_logo_clrlarge,
                app_logo_clrlarge_file_name = v_app_logo_clrlarge_file_name,
                app_logo_clrlarge_content_type = v_app_logo_clrlarge_cont_type,

                clr_pub_logo = v_clr_pub_logo,
                clr_pub_logo_file_name = v_clr_pub_logo_file_name,
                clr_pub_logo_content_type = v_clr_pub_logo_cont_type,

                prog_guide = v_prog_guide,
                prog_guide_file_name = v_prog_guide_file_name,
                prog_guide_content_type = v_prog_guide_cont_type,

                app_title_name = v_app_title_name,
                app_title_name_file_name = v_app_title_name_file_name,
                app_title_name_content_type = v_app_title_name_cont_type,

                brew_presentation = v_brew_presentation,
                brew_presentation_file_name = v_brew_presentation_file_name,
                brew_presentation_content_type = v_brew_presentation_cont_type,
    
                company_logo = v_brew_company_logo, 
                company_logo_file_name = v_brew_company_logo_file_name, 
                company_logo_content_type = v_brew_company_logo_cont_type, 

                title_shot = v_brew_title_shot, 
                title_shot_file_name = v_brew_title_shot_file_name, 
                title_shot_content_type = v_brew_title_shot_cont_type,
                
                high_res_splash = v_brew_high_res_spl,
                high_res_splash_file_name = v_brew_high_res_spl_file_name,
                high_res_splash_content_type = v_brew_high_res_spl_cont_type,
                
                high_res_active = v_brew_high_res_act, 
                high_res_active_file_name = v_brew_high_res_act_file_name,
                high_res_active_content_type = v_brew_high_res_act_cont_type, 
                
                splash_screen = v_brew_splash_screen, 
                splash_screen_file_name = v_brew_splash_screen_file_name,
                splash_screen_content_type = v_brew_splash_screen_cont_type,
                
                small_splash = v_brew_small_splash, 
                small_splash_file_name = v_brew_small_splash_file_name,
                small_splash_content_type = v_brew_small_splash_cont_type,
                
                active_screen_1 = v_brew_act_screen_1, 
                active_screen_1_file_name = v_brew_act_screen_1_file_name,    
                active_screen_1_content_type = v_brew_act_screen_1_cont_type, 
                
                active_screen_2 = v_brew_act_screen_2, 
                active_screen_2_file_name = v_brew_act_screen_2_file_name,    
                active_screen_2_content_type = v_brew_act_screen_2_cont_type, 
                
                sml_active_screen = v_brew_sml_active_screen, 
                sml_active_screen_file_name = v_brew_smlactscreen_file_name,    
                sml_active_screen_content_type = v_brew_smlactscreen_cont_type, 
                
                app_actiion_flash = v_brew_app_act_flash, 
                app_actiion_flash_file_name = v_brew_app_act_flash_file_name,    
                app_actiion_flash_content_type = v_brew_app_act_flash_cont_type, 
                
                app_gif_action = v_brew_app_gif_act, 
                app_gif_action_file_name = v_brew_app_gif_act_file_name,    
                app_gif_action_content_type = v_brew_app_gif_act_cont_type, 
                
                media_store = v_brew_media_store, 
                media_store_file_name = v_brew_media_store_file_name,                                
                media_store_content_type = v_brew_media_store_cont_type, 
    
                flash_demo_movie = v_brew_flash_dem_mov, 
                flash_demo_movie_file_name = v_brew_flash_dem_mov_file_name,
                flash_demo_movie_content_type = v_brew_flash_dem_mov_cont_type, 
                
                dashboard_scr_img = v_brew_dash_scr_img, 
                dashboard_scr_img_file_name = v_brew_dash_scr_img_file_name,
                dashboard_scr_img_content_type = v_brew_dash_scr_img_cont_type
                

            WHERE
                brew_apps_id = p_clone_to_app_id;

        END IF;


        -- For Enterprise
        IF (p_platform_id = 5) THEN
            SELECT
                presentation, presentation_file_name, presentation_content_type
            INTO
                v_presentation, v_presentation_file_name, v_presentation_content_type
            FROM
                AIMS_ENTERPRISE_APPS
            WHERE
                enterprise_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_ENTERPRISE_APPS
            SET
                presentation = v_presentation,
                presentation_file_name = v_presentation_file_name,
                presentation_content_type = v_presentation_content_type

            WHERE
                enterprise_apps_id = p_clone_to_app_id;

        END IF;


        -- For MMS
        IF (p_platform_id = 3) THEN
            SELECT
                sample_content, sample_content_file_name, sample_content_content_type
            INTO
                v_sample_content, v_sample_content_file_name, v_sample_content_content_type
            FROM
                AIMS_MMS_APPS
            WHERE
                mms_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_MMS_APPS
            SET
                sample_content = v_sample_content,
                sample_content_file_name = v_sample_content_file_name,
                sample_content_content_type = v_sample_content_content_type

            WHERE
                mms_apps_id = p_clone_to_app_id;

        END IF;


        -- For SMS
        IF (p_platform_id = 2) THEN
            SELECT
                message_flow, message_flow_file_name, message_flow_content_type
            INTO
                v_message_flow, v_message_flow_file_name, v_message_flow_content_type
            FROM
                AIMS_SMS_APPS
            WHERE
                sms_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_SMS_APPS
            SET
                message_flow = v_message_flow,
                message_flow_file_name = v_message_flow_file_name,
                message_flow_content_type = v_message_flow_content_type

            WHERE
                sms_apps_id = p_clone_to_app_id;

        END IF;


        -- For WAP
        IF (p_platform_id = 4) THEN
            SELECT
                screen_shot, screen_shot_file_name, screen_shot_content_type,
                presentation, presentation_file_name, presentation_content_type,
                product_logo, product_logo_file_name, product_logo_content_type,
                product_icon, product_icon_file_name, product_icon_content_type,
                app_img_medium,app_img_medium_file_name,app_img_medium_content_type,
                app_img_potrait,app_img_potrait_file_name,app_img_potrait_content_type,
                app_img_landscape,app_img_landscape_file_name,app_img_landscape_content_type     
            INTO
                v_screen_shot, v_screen_shot_file_name, v_screen_shot_content_type,
                v_wap_presentation, v_wap_presentation_file_name, v_wap_presentation_cont_type,
                v_wap_product_logo, v_wap_product_logo_file_name, v_wap_product_logo_cont_type,
                v_wap_product_icon, v_wap_product_icon_file_name, v_wap_product_icon_cont_type,               
                v_wap_img_medium,v_wap_img_medium_file_name,v_wap_img_medium_content_type,                        
                v_wap_img_potrait,v_wap_img_potrait_file_name,v_wap_img_potrait_content_type,
                v_wap_img_landscape,v_wap_img_landscape_file_name,v_wap_img_landscape_cont_type       
            FROM
                AIMS_WAP_APPS
            WHERE
                wap_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_WAP_APPS
            SET
                screen_shot = v_screen_shot,
                screen_shot_file_name = v_screen_shot_file_name,
                screen_shot_content_type = v_screen_shot_content_type,

                presentation = v_wap_presentation,
                presentation_file_name = v_wap_presentation_file_name,
                presentation_content_type = v_wap_presentation_cont_type,

                product_logo = v_wap_product_logo,
                product_logo_file_name = v_wap_product_logo_file_name,
                product_logo_content_type = v_wap_product_logo_cont_type,

                product_icon = v_wap_product_icon,
                product_icon_file_name = v_wap_product_icon_file_name,
                product_icon_content_type = v_wap_product_icon_cont_type,
                
                app_img_medium = v_wap_img_medium,            
                app_img_medium_file_name = v_wap_img_medium_file_name,     
                app_img_medium_content_type = v_wap_img_medium_content_type,
                
                app_img_potrait = v_wap_img_potrait,            
                app_img_potrait_file_name = v_wap_img_potrait_file_name, 
                app_img_potrait_content_type = v_wap_img_potrait_content_type,
                
                app_img_landscape = v_wap_img_landscape,       
                app_img_landscape_file_name = v_wap_img_landscape_file_name,
                app_img_landscape_content_type = v_wap_img_landscape_cont_type
      
            WHERE
                wap_apps_id = p_clone_to_app_id;

        END IF;


        -- For VCAST
        IF (p_platform_id = 6) THEN
            SELECT
                sample_clip_1, sample_clip_1_file_name, sample_clip_1_content_type,
                sample_clip_2, sample_clip_2_file_name, sample_clip_2_content_type,
                sample_clip_3, sample_clip_3_file_name, sample_clip_3_content_type
            INTO
                v_sample_clip_1, v_sample_clip_1_file_name, v_sample_clip_1_content_type,
                v_sample_clip_2, v_sample_clip_2_file_name, v_sample_clip_2_content_type,
                v_sample_clip_3, v_sample_clip_3_file_name, v_sample_clip_3_content_type
            FROM
                AIMS_VCAST_APPS
            WHERE
                vcast_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_VCAST_APPS
            SET
                sample_clip_1 = v_sample_clip_1,
                sample_clip_1_file_name = v_sample_clip_1_file_name,
                sample_clip_1_content_type = v_sample_clip_1_content_type,

                sample_clip_2 = v_sample_clip_2,
                sample_clip_2_file_name = v_sample_clip_2_file_name,
                sample_clip_2_content_type = v_sample_clip_2_content_type,

                sample_clip_3 = v_sample_clip_3,
                sample_clip_3_file_name = v_sample_clip_3_file_name,
                sample_clip_3_content_type = v_sample_clip_3_content_type

            WHERE
                vcast_apps_id = p_clone_to_app_id;

        END IF;
  
  
        -- For Dashboard
        IF (p_platform_id = 43) THEN
            SELECT
                clr_pub_logo, clr_pub_logo_file_name, clr_pub_logo_content_type,
                app_title_name, app_title_name_file_name, app_title_name_content_type,                   
                content_zip_file, content_zip_file_file_name, content_zip_file_content_type,
                title_image, title_image_file_name, title_image_content_type,
                company_logo, company_logo_file_name, company_logo_content_type                                        
            INTO
                v_dash_pub_logo, v_dash_pub_logo_file_name, v_dash_pub_logo_cont_type,
                v_dash_title_name, v_dash_title_name_file_name, v_dash_title_name_cont_type,    
                v_dash_content_file, v_dash_content_file_file_name,v_dash_content_file_cont_type,    
                v_dash_title_image, v_dash_title_image_file_name,v_dash_title_image_cont_type,
                v_dash_company_logo, v_dash_company_logo_file_name,v_dash_company_logo_cont_type                
            FROM
                AIMS_DASHBOARD_APPS
            WHERE
                dashboard_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_DASHBOARD_APPS
            SET
                clr_pub_logo = v_dash_pub_logo,
                clr_pub_logo_file_name = v_dash_pub_logo_file_name,
                clr_pub_logo_content_type = v_dash_pub_logo_cont_type,

                app_title_name = v_dash_title_name,
                app_title_name_file_name = v_dash_title_name_file_name,
                app_title_name_content_type = v_dash_title_name_cont_type,
                       
                content_zip_file = v_dash_content_file,
                content_zip_file_file_name = v_dash_content_file_file_name,
                content_zip_file_content_type = v_dash_content_file_cont_type,   
        

                title_image = v_dash_title_image,
                title_image_file_name = v_dash_title_image_file_name,
                title_image_content_type = v_dash_title_image_cont_type,
                
                company_logo = v_dash_company_logo,
                company_logo_file_name = v_dash_company_logo_file_name,
                company_logo_content_type = v_dash_company_logo_cont_type                      
                
            WHERE
                dashboard_apps_id = p_clone_to_app_id;

        END IF;  
        
        
        -- For VZ_APPS_ZON
     IF (p_platform_id = 42) THEN
            SELECT
                app_landing_page,app_landing_page_content_type,app_landing_page_file_name,
                app_presentation,app_presentation_content_type,app_presentation_file_name
            INTO
                v_app_landing_page,v_app_landing_page_content_typ,v_app_landing_page_file_name,
                v_app_presentation,v_app_presentation_content_typ,v_app_presentation_file_name
            FROM
                AIMS_VZAPPZONE_APPS
            WHERE
                vzappzone_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_VZAPPZONE_APPS
            SET
                app_landing_page = v_app_landing_page,
                app_landing_page_content_type = v_app_landing_page_content_typ,
                app_landing_page_file_name = v_app_landing_page_file_name,
                app_presentation = v_app_presentation,
                app_presentation_content_type = v_app_presentation_content_typ,
                app_presentation_file_name = v_app_presentation_file_name
            WHERE
                vzappzone_apps_id = p_clone_to_app_id;

        END IF;
        
		-- For JAVA
        IF (p_platform_id = 44) THEN
            SELECT
			  hr_publisher_logo, hr_publisher_logo_file_name, hr_publisher_logo_content_type
			  , chnl_title_icon, chnl_title_icon_file_name, chnl_title_icon_content_type
			  , splh_screen_shot, splh_screen_shot_file_name, splh_screen_shot_content_type
			  , actv_screen_shot, actv_screen_shot_file_name, actv_screen_shot_content_type
			  , app_screen_shot, app_screen_shot_file_name, app_screen_shot_content_type
			  , app_screen_shot_1, app_screen_shot_1_file_name, app_screen_shot_1_content_type
			  , app_screen_shot_2, app_screen_shot_2_file_name, app_screen_shot_2_content_type
			  , app_screen_shot_3, app_screen_shot_3_file_name, app_screen_shot_3_content_type
			  , app_screen_shot_4, app_screen_shot_4_file_name, app_screen_shot_4_content_type
			  , app_screen_shot_5, app_screen_shot_5_file_name, app_screen_shot_5_content_type
			  , app_screen_shot_6, app_screen_shot_6_file_name, app_screen_shot_6_content_type
			  , app_screen_shot_7, app_screen_shot_7_file_name, app_screen_shot_7_content_type
			  , app_screen_shot_8, app_screen_shot_8_file_name, app_screen_shot_8_content_type
			  , app_screen_shot_9, app_screen_shot_9_file_name, app_screen_shot_9_content_type
			  , faq_file, faq_file_file_name, faq_file_content_type
			  , company_logo, company_logo_file_name, company_logo_content_type
			  , app_title_name, app_title_name_file_name, app_title_name_content_type

            INTO
			  v_j_hr_pub_logo, v_j_hr_pub_logo_file_name, v_j_hr_pub_logo_content_type
			  , v_j_ch_title_icon, v_j_ch_title_icon_file_name, v_j_ch_title_icon_content_type
			  , v_j_sp_scr_shot, v_j_sp_scr_shot_file_name, v_j_sp_scr_shot_content_type
			  , v_j_actv_scr_shot, v_j_actv_scr_shot_file_name, v_j_actv_scr_shot_content_type
			  , v_j_app_scr, v_j_app_scr_file_name, v_j_app_scr_content_type
			  , v_j_app_scr_1, v_j_app_scr_1_file_name, v_j_app_scr_1_content_type
			  , v_j_app_scr_2, v_j_app_scr_2_file_name, v_j_app_scr_2_content_type
			  , v_j_app_scr_3, v_j_app_scr_3_file_name, v_j_app_scr_3_content_type
			  , v_j_app_scr_4, v_j_app_scr_4_file_name, v_j_app_scr_4_content_type
			  , v_j_app_scr_5, v_j_app_scr_5_file_name, v_j_app_scr_5_content_type
			  , v_j_app_scr_6, v_j_app_scr_6_file_name, v_j_app_scr_6_content_type
			  , v_j_app_scr_7, v_j_app_scr_7_file_name, v_j_app_scr_7_content_type
			  , v_j_app_scr_8, v_j_app_scr_8_file_name, v_j_app_scr_8_content_type
			  , v_j_app_scr_9, v_j_app_scr_9_file_name, v_j_app_scr_9_content_type
			  , v_j_faq_file, v_j_faq_file_file_name, v_j_faq_file_content_type
			  , v_j_company_logo, v_j_company_logo_file_name, v_j_company_logo_content_type
			  , v_j_app_title, v_j_app_title_file_name, v_j_app_title_content_type

            FROM
                AIMS_JAVA_APPS
            WHERE
                java_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_JAVA_APPS
            SET
			  hr_publisher_logo		     	 =v_j_hr_pub_logo
			  ,hr_publisher_logo_file_name	 =v_j_hr_pub_logo_file_name
			  ,hr_publisher_logo_content_type=v_j_hr_pub_logo_content_type
		
			  ,chnl_title_icon		        =v_j_ch_title_icon
			  ,chnl_title_icon_file_name	=v_j_ch_title_icon_file_name
			  ,chnl_title_icon_content_type	=v_j_ch_title_icon_content_type
		
			  ,splh_screen_shot		        =v_j_sp_scr_shot
			  ,splh_screen_shot_file_name	=v_j_sp_scr_shot_file_name
			  ,splh_screen_shot_content_type=v_j_sp_scr_shot_content_type
		
			  ,actv_screen_shot		        =v_j_actv_scr_shot
			  ,actv_screen_shot_file_name	=v_j_actv_scr_shot_file_name
			  ,actv_screen_shot_content_type=v_j_actv_scr_shot_content_type
			  
			  ,app_screen_shot		        =v_j_app_scr
			  ,app_screen_shot_file_name	=v_j_app_scr_file_name
			  ,app_screen_shot_content_type	=v_j_app_scr_content_type
			  
			  ,app_screen_shot_1		     =v_j_app_scr_1
			  ,app_screen_shot_1_file_name	 =v_j_app_scr_1_file_name
			  ,app_screen_shot_1_content_type=v_j_app_scr_1_content_type
			  
		  	  ,app_screen_shot_2		     =v_j_app_scr_2
			  ,app_screen_shot_2_file_name	 =v_j_app_scr_2_file_name
			  ,app_screen_shot_2_content_type=v_j_app_scr_2_content_type
			  
		  	  ,app_screen_shot_3		     =v_j_app_scr_3
			  ,app_screen_shot_3_file_name	 =v_j_app_scr_3_file_name
			  ,app_screen_shot_3_content_type=v_j_app_scr_3_content_type
			  
		  	  ,app_screen_shot_4		     =v_j_app_scr_4
			  ,app_screen_shot_4_file_name	 =v_j_app_scr_4_file_name
			  ,app_screen_shot_4_content_type=v_j_app_scr_4_content_type
			  
			  ,app_screen_shot_5		     =v_j_app_scr_5
			  ,app_screen_shot_5_file_name	 =v_j_app_scr_5_file_name
			  ,app_screen_shot_5_content_type=v_j_app_scr_5_content_type
			  
		  	  ,app_screen_shot_6		     =v_j_app_scr_6
			  ,app_screen_shot_6_file_name	 =v_j_app_scr_6_file_name
			  ,app_screen_shot_6_content_type=v_j_app_scr_6_content_type
			  
		  	  ,app_screen_shot_7		     =v_j_app_scr_7
			  ,app_screen_shot_7_file_name	 =v_j_app_scr_7_file_name
			  ,app_screen_shot_7_content_type=v_j_app_scr_7_content_type
			  
		  	  ,app_screen_shot_8		     =v_j_app_scr_8
			  ,app_screen_shot_8_file_name	 =v_j_app_scr_8_file_name
			  ,app_screen_shot_8_content_type=v_j_app_scr_8_content_type	  
		
		  	  ,app_screen_shot_9		     =v_j_app_scr_9
			  ,app_screen_shot_9_file_name	 =v_j_app_scr_9_file_name
			  ,app_screen_shot_9_content_type=v_j_app_scr_9_content_type	  
			  
			  ,faq_file		        		 =v_j_faq_file
			  ,faq_file_file_name			 =v_j_faq_file_file_name
			  ,faq_file_content_type		 =v_j_faq_file_content_type
			  
		  	  ,company_logo		        	 =v_j_company_logo
			  ,company_logo_file_name		 =v_j_company_logo_file_name
			  ,company_logo_content_type	 =v_j_company_logo_content_type
		
		   	  ,app_title_name		     	 =v_j_app_title
			  ,app_title_name_file_name		 =v_j_app_title_file_name
			  ,app_title_name_content_type	 =v_j_app_title_content_type

            WHERE
                java_apps_id = p_clone_to_app_id;

        END IF;


   END clone_images;


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_single_files
         (
            p_file_column               IN  VARCHAR2,           -- application id
            p_file_name_column          IN  VARCHAR2,           -- application id
            p_table_name                IN  VARCHAR2,           -- application id
            p_pk_column_name            IN  VARCHAR2,           -- application id
            p_pk_id                     IN  NUMBER,           -- application id
            p_out_result                OUT  VARCHAR2           -- 'Y' one of the filter words is used 'N' words are good
          )

    IS
        sql_select VARCHAR2(1000);
    BEGIN
          p_out_result := 'N';


          sql_select := 'select ''Y'' from ' || p_table_name || ' where '
                                || p_pk_column_name || ' = :1 and '
                                || p_file_column || ' is null and '
                                || p_file_name_column || ' is not null ';

          EXECUTE IMMEDIATE sql_select
                                INTO p_out_result USING p_pk_id;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_result := 'N';

    END check_single_files;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_uploaded_files
         (
            p_apps_id                IN  NUMBER,           -- application id
            p_out_result            OUT  VARCHAR2         -- 'Y' one of the filter words is used 'N' words are good
          )

    IS

    /*
    || Overview:        Checks if words are from filter words table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 03-18-2004       rqazi           Created
    ||
    ||
    ||
    ||

FLASH_DEMO
USER_GUIDE
SPLASH_SCREEN_EPS
ACTIVE_SCREEN_EPS
FAQ_DOC
TEST_PLAN_RESULTS
SCREEN_JPEG
SCREEN_JPEG_2
SCREEN_JPEG_3
SCREEN_JPEG_4
SCREEN_JPEG_5

    */


        v_out_result VARCHAR2(1):= 'N';
        v_out_filter_words VARCHAR2(32767);
        v_temp_filter_word VARCHAR2(200);
        v_file_name VARCHAR2(200);
        v_temp_result VARCHAR2(1);
        v_cnt NUMBER;
        v_file_names_array DBMS_UTILITY.UNCL_ARRAY;

   BEGIN
        v_cnt := 0;
        p_out_result := 'N';

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('ACTIVE_SCREEN_EPS', 'ACTIVE_SCREEN_EPS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Active Screen Shot';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('FAQ_DOC', 'FAQ_DOC_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'FAQ';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('FLASH_DEMO', 'FLASH_DEMO_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Flash Demo of Running App';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG', 'SCREEN_JPEG_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_2', 'SCREEN_JPEG_2_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 2';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_3', 'SCREEN_JPEG_3_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 3';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_4', 'SCREEN_JPEG_4_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 4';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_5', 'SCREEN_JPEG_5_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 5';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SPLASH_SCREEN_EPS', 'SPLASH_SCREEN_EPS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Splash Screen Shot';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('TEST_PLAN_RESULTS', 'TEST_PLAN_RESULTS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Testing Plan And Results';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('USER_GUIDE', 'USER_GUIDE_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'User Guide';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_BW_LARGE', 'APP_LOGO_BW_LARGE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo BW Large';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_BW_SMALL', 'APP_LOGO_BW_SMALL_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo BW Small';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_CLRLARGE', 'APP_LOGO_CLRLARGE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo Color Large';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_CLRSMALL', 'APP_LOGO_CLRSMALL_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo Color Small';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('CLR_PUB_LOGO', 'CLR_PUB_LOGO_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'High Resolution Publisher Logo';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('PROG_GUIDE', 'PROG_GUIDE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Programming Guide';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('MKTG_SLICK_SHEET', 'MKTG_SLICK_SHEET_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Marketing Slick Sheet';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('STYLE_GUIDE', 'STYLE_GUIDE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Style Guide for Use of Application Logo';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_TITLE_NAME', 'APP_TITLE_NAME_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Title Image';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('BREW_PRESENTATION', 'BREW_PRESENTATION_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Presentation';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('PRESENTATION', 'PRESENTATION_FILE_NAME', 'AIMS_ENTERPRISE_APPS', 'ENTERPRISE_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Presentation File';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CONTENT', 'SAMPLE_CONTENT_FILE_NAME', 'AIMS_MMS_APPS', 'MMS_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Content File';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('MESSAGE_FLOW', 'MESSAGE_FLOW_FILE_NAME', 'AIMS_SMS_APPS', 'SMS_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Message Flow';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CLIP_1', 'SAMPLE_CLIP_1_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CLIP_2', 'SAMPLE_CLIP_2_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip 2';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CLIP_3', 'SAMPLE_CLIP_3_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip 3';
        END IF;

        IF (v_file_names_array.COUNT > 0) THEN
            Parse.table_to_delimstring(v_file_names_array, p_out_result, ', ');
        ELSE
            p_out_result := '';
        END IF;

   END check_uploaded_files;

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Lob_Utils; -- Package Body AIMS_LOB_UTILS
/
