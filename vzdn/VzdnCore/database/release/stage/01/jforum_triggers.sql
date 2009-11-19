USE jforum;

DROP TRIGGER IF EXISTS jforum_apitrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_apitrgins AFTER INSERT ON jforum_api
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_api$aud (api_id,api_key,api_validity,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.api_id,NEW.api_key,NEW.api_validity,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attachtrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_attachtrgins AFTER INSERT ON jforum_attach
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach$aud (attach_id,post_id,privmsgs_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.attach_id,NEW.post_id,NEW.privmsgs_id,NEW.user_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attach_desctrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_attach_desctrgins AFTER INSERT ON jforum_attach_desc
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach_desc$aud (attach_desc_id,attach_id,physical_filename,real_filename,download_count,description,mimetype,filesize,upload_time,thumb,extension_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.attach_desc_id,NEW.attach_id,NEW.physical_filename,NEW.real_filename,NEW.download_count,NEW.description,NEW.mimetype,NEW.filesize,NEW.upload_time,NEW.thumb,NEW.extension_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attach_quotatrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_attach_quotatrgins AFTER INSERT ON jforum_attach_quota
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach_quota$aud (attach_quota_id,group_id,quota_limit_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.attach_quota_id,NEW.group_id,NEW.quota_limit_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_banlisttrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_banlisttrgins AFTER INSERT ON jforum_banlist
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_banlist$aud (banlist_id,user_id,banlist_ip,banlist_email,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.banlist_id,NEW.user_id,NEW.banlist_ip,NEW.banlist_email,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_bannertrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_bannertrgins AFTER INSERT ON jforum_banner
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_banner$aud (banner_id,banner_name,banner_placement,banner_description,banner_clicks,banner_views,banner_url,banner_weight,banner_active,banner_comment,banner_type,banner_width,banner_height,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.banner_id,NEW.banner_name,NEW.banner_placement,NEW.banner_description,NEW.banner_clicks,NEW.banner_views,NEW.banner_url,NEW.banner_weight,NEW.banner_active,NEW.banner_comment,NEW.banner_type,NEW.banner_width,NEW.banner_height,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_bookmarkstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_bookmarkstrgins AFTER INSERT ON jforum_bookmarks
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_bookmarks$aud (bookmark_id,user_id,relation_id,relation_type,public_visible,title,description,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.bookmark_id,NEW.user_id,NEW.relation_id,NEW.relation_type,NEW.public_visible,NEW.title,NEW.description,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_categoriestrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_categoriestrgins AFTER INSERT ON jforum_categories
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_categories$aud (categories_id,title,display_order,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.categories_id,NEW.title,NEW.display_order,NEW.moderated,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_configtrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_configtrgins AFTER INSERT ON jforum_config
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_config$aud (config_name,config_value,config_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.config_name,NEW.config_value,NEW.config_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_extension_groupstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_extension_groupstrgins AFTER INSERT ON jforum_extension_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_extension_groups$aud (extension_group_id,name,allow,upload_icon,download_mode,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.extension_group_id,NEW.name,NEW.allow,NEW.upload_icon,NEW.download_mode,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_extensionstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_extensionstrgins AFTER INSERT ON jforum_extensions
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_extensions$aud (extension_id,extension_group_id,description,upload_icon,extension,allow,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.extension_id,NEW.extension_group_id,NEW.description,NEW.upload_icon,NEW.extension,NEW.allow,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_forumstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_forumstrgins AFTER INSERT ON jforum_forums
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_forums$aud (forum_id,categories_id,forum_name,forum_desc,forum_order,forum_topics,forum_last_post_id,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.forum_id,NEW.categories_id,NEW.forum_name,NEW.forum_desc,NEW.forum_order,NEW.forum_topics,NEW.forum_last_post_id,NEW.moderated,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_forums_watchtrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_forums_watchtrgins AFTER INSERT ON jforum_forums_watch
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_forums_watch$aud (forum_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.forum_id,NEW.user_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_groupstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_groupstrgins AFTER INSERT ON jforum_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_groups$aud (group_id,group_name,group_description,parent_id,vzdn_manager_role,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.group_id,NEW.group_name,NEW.group_description,NEW.parent_id,NEW.vzdn_manager_role,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_karmatrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_karmatrgins AFTER INSERT ON jforum_karma
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_karma$aud (karma_id,post_id,topic_id,post_user_id,from_user_id,points,rate_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.karma_id,NEW.post_id,NEW.topic_id,NEW.post_user_id,NEW.from_user_id,NEW.points,NEW.rate_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_mail_integrationtrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_mail_integrationtrgins AFTER INSERT ON jforum_mail_integration
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_mail_integration$aud (forum_id,forum_email,pop_username,pop_password,pop_host,pop_port,pop_ssl,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.forum_id,NEW.forum_email,NEW.pop_username,NEW.pop_password,NEW.pop_host,NEW.pop_port,NEW.pop_ssl,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_moderation_logtrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_moderation_logtrgins AFTER INSERT ON jforum_moderation_log
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_moderation_log$aud (log_id,user_id,log_description,log_original_message,log_date,log_type,post_id,topic_id,post_user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.log_id,NEW.user_id,NEW.log_description,NEW.log_original_message,NEW.log_date,NEW.log_type,NEW.post_id,NEW.topic_id,NEW.post_user_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_poststrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_poststrgins AFTER INSERT ON jforum_posts
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_posts$aud (post_id,topic_id,forum_id,user_id,post_time,poster_ip,enable_bbcode,enable_html,enable_smilies,enable_sig,post_edit_time,post_edit_count,status,attach,need_moderate,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.post_id,NEW.topic_id,NEW.forum_id,NEW.user_id,NEW.post_time,NEW.poster_ip,NEW.enable_bbcode,NEW.enable_html,NEW.enable_smilies,NEW.enable_sig,NEW.post_edit_time,NEW.post_edit_count,NEW.status,NEW.attach,NEW.need_moderate,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_posts_texttrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_posts_texttrgins AFTER INSERT ON jforum_posts_text
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_posts_text$aud (post_id,post_text,post_subject,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.post_id,NEW.post_text,NEW.post_subject,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_privmsgstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_privmsgstrgins AFTER INSERT ON jforum_privmsgs
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_privmsgs$aud (privmsgs_id,privmsgs_type,privmsgs_subject,privmsgs_from_userid,privmsgs_to_userid,privmsgs_date,privmsgs_ip,privmsgs_enable_bbcode,privmsgs_enable_html,privmsgs_enable_smilies,privmsgs_attach_sig,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.privmsgs_id,NEW.privmsgs_type,NEW.privmsgs_subject,NEW.privmsgs_from_userid,NEW.privmsgs_to_userid,NEW.privmsgs_date,NEW.privmsgs_ip,NEW.privmsgs_enable_bbcode,NEW.privmsgs_enable_html,NEW.privmsgs_enable_smilies,NEW.privmsgs_attach_sig,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_privmsgs_texttrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_privmsgs_texttrgins AFTER INSERT ON jforum_privmsgs_text
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_privmsgs_text$aud (privmsgs_id,privmsgs_text,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.privmsgs_id,NEW.privmsgs_text,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_quota_limittrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_quota_limittrgins AFTER INSERT ON jforum_quota_limit
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_quota_limit$aud (quota_limit_id,quota_desc,quota_limit,quota_type,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.quota_limit_id,NEW.quota_desc,NEW.quota_limit,NEW.quota_type,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_rankstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_rankstrgins AFTER INSERT ON jforum_ranks
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_ranks$aud (rank_id,rank_title,rank_min,rank_special,rank_image,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.rank_id,NEW.rank_title,NEW.rank_min,NEW.rank_special,NEW.rank_image,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_role_valuestrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_role_valuestrgins AFTER INSERT ON jforum_role_values
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_role_values$aud (role_id,role_value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.role_value,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_rolestrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_rolestrgins AFTER INSERT ON jforum_roles
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_roles$aud (role_id,group_id,name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.group_id,NEW.name,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_sessionstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_sessionstrgins AFTER INSERT ON jforum_sessions
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_sessions$aud (session_id,session_user_id,session_start,session_time,session_ip,session_page,session_logged_int,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.session_id,NEW.session_user_id,NEW.session_start,NEW.session_time,NEW.session_ip,NEW.session_page,NEW.session_logged_int,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_smiliestrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_smiliestrgins AFTER INSERT ON jforum_smilies
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_smilies$aud (smilie_id,code,url,disk_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.smilie_id,NEW.code,NEW.url,NEW.disk_name,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_themestrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_themestrgins AFTER INSERT ON jforum_themes
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_themes$aud (themes_id,template_name,style_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.themes_id,NEW.template_name,NEW.style_name,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_topicstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_topicstrgins AFTER INSERT ON jforum_topics
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_topics$aud (topic_id,forum_id,topic_title,user_id,topic_time,topic_views,topic_replies,topic_status,topic_vote_id,topic_type,topic_first_post_id,topic_last_post_id,topic_moved_id,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.topic_id,NEW.forum_id,NEW.topic_title,NEW.user_id,NEW.topic_time,NEW.topic_views,NEW.topic_replies,NEW.topic_status,NEW.topic_vote_id,NEW.topic_type,NEW.topic_first_post_id,NEW.topic_last_post_id,NEW.topic_moved_id,NEW.moderated,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_topics_watchtrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_topics_watchtrgins AFTER INSERT ON jforum_topics_watch
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_topics_watch$aud (topic_id,user_id,is_read,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.topic_id,NEW.user_id,NEW.is_read,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_user_groupstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_user_groupstrgins AFTER INSERT ON jforum_user_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_user_groups$aud (group_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.group_id,NEW.user_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_userstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_userstrgins AFTER INSERT ON jforum_users
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_users$aud (user_id,user_active,username,user_password,user_session_time,user_session_page,user_lastvisit,user_regdate,user_level,user_posts,user_timezone,user_style,user_lang,user_dateformat,user_new_privmsg,user_unread_privmsg,user_last_privmsg,user_emailtime,user_viewemail,user_attachsig,user_allowhtml,user_allowbbcode,user_allowsmilies,user_allowavatar,user_allow_pm,user_allow_viewonline,user_notify,user_notify_always,user_notify_text,user_notify_pm,user_popup_pm,rank_id,user_avatar,user_avatar_type,user_email,user_icq,user_website,user_from,user_sig,user_sig_bbcode_uid,user_aim,user_yim,user_msnm,user_occ,user_interests,user_biography,user_actkey,gender,themes_id,deleted,user_viewonline,security_hash,user_karma,user_authhash,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.user_id,NEW.user_active,NEW.username,NEW.user_password,NEW.user_session_time,NEW.user_session_page,NEW.user_lastvisit,NEW.user_regdate,NEW.user_level,NEW.user_posts,NEW.user_timezone,NEW.user_style,NEW.user_lang,NEW.user_dateformat,NEW.user_new_privmsg,NEW.user_unread_privmsg,NEW.user_last_privmsg,NEW.user_emailtime,NEW.user_viewemail,NEW.user_attachsig,NEW.user_allowhtml,NEW.user_allowbbcode,NEW.user_allowsmilies,NEW.user_allowavatar,NEW.user_allow_pm,NEW.user_allow_viewonline,NEW.user_notify,NEW.user_notify_always,NEW.user_notify_text,NEW.user_notify_pm,NEW.user_popup_pm,NEW.rank_id,NEW.user_avatar,NEW.user_avatar_type,NEW.user_email,NEW.user_icq,NEW.user_website,NEW.user_from,NEW.user_sig,NEW.user_sig_bbcode_uid,NEW.user_aim,NEW.user_yim,NEW.user_msnm,NEW.user_occ,NEW.user_interests,NEW.user_biography,NEW.user_actkey,NEW.gender,NEW.themes_id,NEW.deleted,NEW.user_viewonline,NEW.security_hash,NEW.user_karma,NEW.user_authhash,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_desctrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_desctrgins AFTER INSERT ON jforum_vote_desc
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_desc$aud (vote_id,topic_id,vote_text,vote_start,vote_length,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.vote_id,NEW.topic_id,NEW.vote_text,NEW.vote_start,NEW.vote_length,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_resultstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_resultstrgins AFTER INSERT ON jforum_vote_results
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_results$aud (vote_id,vote_option_id,vote_option_text,vote_result,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.vote_id,NEW.vote_option_id,NEW.vote_option_text,NEW.vote_result,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_voterstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_voterstrgins AFTER INSERT ON jforum_vote_voters
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_voters$aud (vote_id,vote_user_id,vote_user_ip,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.vote_id,NEW.vote_user_id,NEW.vote_user_ip,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_wordstrgins;

DELIMITER $$ 
CREATE TRIGGER jforum_wordstrgins AFTER INSERT ON jforum_words
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_words$aud (word_id,word,replacement,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.word_id,NEW.word,NEW.replacement,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_apitrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_apitrgupd AFTER UPDATE ON jforum_api
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_api$aud (api_id,api_key,api_validity,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.api_id,NEW.api_key,NEW.api_validity,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attachtrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_attachtrgupd AFTER UPDATE ON jforum_attach
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach$aud (attach_id,post_id,privmsgs_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.attach_id,NEW.post_id,NEW.privmsgs_id,NEW.user_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attach_desctrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_attach_desctrgupd AFTER UPDATE ON jforum_attach_desc
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach_desc$aud (attach_desc_id,attach_id,physical_filename,real_filename,download_count,description,mimetype,filesize,upload_time,thumb,extension_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.attach_desc_id,NEW.attach_id,NEW.physical_filename,NEW.real_filename,NEW.download_count,NEW.description,NEW.mimetype,NEW.filesize,NEW.upload_time,NEW.thumb,NEW.extension_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attach_quotatrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_attach_quotatrgupd AFTER UPDATE ON jforum_attach_quota
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach_quota$aud (attach_quota_id,group_id,quota_limit_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.attach_quota_id,NEW.group_id,NEW.quota_limit_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_banlisttrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_banlisttrgupd AFTER UPDATE ON jforum_banlist
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_banlist$aud (banlist_id,user_id,banlist_ip,banlist_email,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.banlist_id,NEW.user_id,NEW.banlist_ip,NEW.banlist_email,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_bannertrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_bannertrgupd AFTER UPDATE ON jforum_banner
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_banner$aud (banner_id,banner_name,banner_placement,banner_description,banner_clicks,banner_views,banner_url,banner_weight,banner_active,banner_comment,banner_type,banner_width,banner_height,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.banner_id,NEW.banner_name,NEW.banner_placement,NEW.banner_description,NEW.banner_clicks,NEW.banner_views,NEW.banner_url,NEW.banner_weight,NEW.banner_active,NEW.banner_comment,NEW.banner_type,NEW.banner_width,NEW.banner_height,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_bookmarkstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_bookmarkstrgupd AFTER UPDATE ON jforum_bookmarks
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_bookmarks$aud (bookmark_id,user_id,relation_id,relation_type,public_visible,title,description,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.bookmark_id,NEW.user_id,NEW.relation_id,NEW.relation_type,NEW.public_visible,NEW.title,NEW.description,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_categoriestrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_categoriestrgupd AFTER UPDATE ON jforum_categories
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_categories$aud (categories_id,title,display_order,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.categories_id,NEW.title,NEW.display_order,NEW.moderated,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_configtrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_configtrgupd AFTER UPDATE ON jforum_config
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_config$aud (config_name,config_value,config_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.config_name,NEW.config_value,NEW.config_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_extension_groupstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_extension_groupstrgupd AFTER UPDATE ON jforum_extension_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_extension_groups$aud (extension_group_id,name,allow,upload_icon,download_mode,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.extension_group_id,NEW.name,NEW.allow,NEW.upload_icon,NEW.download_mode,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_extensionstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_extensionstrgupd AFTER UPDATE ON jforum_extensions
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_extensions$aud (extension_id,extension_group_id,description,upload_icon,extension,allow,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.extension_id,NEW.extension_group_id,NEW.description,NEW.upload_icon,NEW.extension,NEW.allow,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_forumstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_forumstrgupd AFTER UPDATE ON jforum_forums
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_forums$aud (forum_id,categories_id,forum_name,forum_desc,forum_order,forum_topics,forum_last_post_id,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.forum_id,NEW.categories_id,NEW.forum_name,NEW.forum_desc,NEW.forum_order,NEW.forum_topics,NEW.forum_last_post_id,NEW.moderated,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_forums_watchtrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_forums_watchtrgupd AFTER UPDATE ON jforum_forums_watch
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_forums_watch$aud (forum_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.forum_id,NEW.user_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_groupstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_groupstrgupd AFTER UPDATE ON jforum_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_groups$aud (group_id,group_name,group_description,parent_id,vzdn_manager_role,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.group_id,NEW.group_name,NEW.group_description,NEW.parent_id,NEW.vzdn_manager_role,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_karmatrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_karmatrgupd AFTER UPDATE ON jforum_karma
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_karma$aud (karma_id,post_id,topic_id,post_user_id,from_user_id,points,rate_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.karma_id,NEW.post_id,NEW.topic_id,NEW.post_user_id,NEW.from_user_id,NEW.points,NEW.rate_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_mail_integrationtrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_mail_integrationtrgupd AFTER UPDATE ON jforum_mail_integration
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_mail_integration$aud (forum_id,forum_email,pop_username,pop_password,pop_host,pop_port,pop_ssl,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.forum_id,NEW.forum_email,NEW.pop_username,NEW.pop_password,NEW.pop_host,NEW.pop_port,NEW.pop_ssl,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_moderation_logtrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_moderation_logtrgupd AFTER UPDATE ON jforum_moderation_log
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_moderation_log$aud (log_id,user_id,log_description,log_original_message,log_date,log_type,post_id,topic_id,post_user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.log_id,NEW.user_id,NEW.log_description,NEW.log_original_message,NEW.log_date,NEW.log_type,NEW.post_id,NEW.topic_id,NEW.post_user_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_poststrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_poststrgupd AFTER UPDATE ON jforum_posts
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_posts$aud (post_id,topic_id,forum_id,user_id,post_time,poster_ip,enable_bbcode,enable_html,enable_smilies,enable_sig,post_edit_time,post_edit_count,status,attach,need_moderate,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.post_id,NEW.topic_id,NEW.forum_id,NEW.user_id,NEW.post_time,NEW.poster_ip,NEW.enable_bbcode,NEW.enable_html,NEW.enable_smilies,NEW.enable_sig,NEW.post_edit_time,NEW.post_edit_count,NEW.status,NEW.attach,NEW.need_moderate,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_posts_texttrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_posts_texttrgupd AFTER UPDATE ON jforum_posts_text
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_posts_text$aud (post_id,post_text,post_subject,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.post_id,NEW.post_text,NEW.post_subject,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_privmsgstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_privmsgstrgupd AFTER UPDATE ON jforum_privmsgs
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_privmsgs$aud (privmsgs_id,privmsgs_type,privmsgs_subject,privmsgs_from_userid,privmsgs_to_userid,privmsgs_date,privmsgs_ip,privmsgs_enable_bbcode,privmsgs_enable_html,privmsgs_enable_smilies,privmsgs_attach_sig,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.privmsgs_id,NEW.privmsgs_type,NEW.privmsgs_subject,NEW.privmsgs_from_userid,NEW.privmsgs_to_userid,NEW.privmsgs_date,NEW.privmsgs_ip,NEW.privmsgs_enable_bbcode,NEW.privmsgs_enable_html,NEW.privmsgs_enable_smilies,NEW.privmsgs_attach_sig,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_privmsgs_texttrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_privmsgs_texttrgupd AFTER UPDATE ON jforum_privmsgs_text
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_privmsgs_text$aud (privmsgs_id,privmsgs_text,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.privmsgs_id,NEW.privmsgs_text,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_quota_limittrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_quota_limittrgupd AFTER UPDATE ON jforum_quota_limit
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_quota_limit$aud (quota_limit_id,quota_desc,quota_limit,quota_type,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.quota_limit_id,NEW.quota_desc,NEW.quota_limit,NEW.quota_type,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_rankstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_rankstrgupd AFTER UPDATE ON jforum_ranks
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_ranks$aud (rank_id,rank_title,rank_min,rank_special,rank_image,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.rank_id,NEW.rank_title,NEW.rank_min,NEW.rank_special,NEW.rank_image,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_role_valuestrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_role_valuestrgupd AFTER UPDATE ON jforum_role_values
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_role_values$aud (role_id,role_value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.role_value,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_rolestrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_rolestrgupd AFTER UPDATE ON jforum_roles
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_roles$aud (role_id,group_id,name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.group_id,NEW.name,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_sessionstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_sessionstrgupd AFTER UPDATE ON jforum_sessions
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_sessions$aud (session_id,session_user_id,session_start,session_time,session_ip,session_page,session_logged_int,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.session_id,NEW.session_user_id,NEW.session_start,NEW.session_time,NEW.session_ip,NEW.session_page,NEW.session_logged_int,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_smiliestrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_smiliestrgupd AFTER UPDATE ON jforum_smilies
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_smilies$aud (smilie_id,code,url,disk_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.smilie_id,NEW.code,NEW.url,NEW.disk_name,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_themestrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_themestrgupd AFTER UPDATE ON jforum_themes
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_themes$aud (themes_id,template_name,style_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.themes_id,NEW.template_name,NEW.style_name,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_topicstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_topicstrgupd AFTER UPDATE ON jforum_topics
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_topics$aud (topic_id,forum_id,topic_title,user_id,topic_time,topic_views,topic_replies,topic_status,topic_vote_id,topic_type,topic_first_post_id,topic_last_post_id,topic_moved_id,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.topic_id,NEW.forum_id,NEW.topic_title,NEW.user_id,NEW.topic_time,NEW.topic_views,NEW.topic_replies,NEW.topic_status,NEW.topic_vote_id,NEW.topic_type,NEW.topic_first_post_id,NEW.topic_last_post_id,NEW.topic_moved_id,NEW.moderated,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_topics_watchtrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_topics_watchtrgupd AFTER UPDATE ON jforum_topics_watch
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_topics_watch$aud (topic_id,user_id,is_read,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.topic_id,NEW.user_id,NEW.is_read,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_user_groupstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_user_groupstrgupd AFTER UPDATE ON jforum_user_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_user_groups$aud (group_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.group_id,NEW.user_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_userstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_userstrgupd AFTER UPDATE ON jforum_users
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_users$aud (user_id,user_active,username,user_password,user_session_time,user_session_page,user_lastvisit,user_regdate,user_level,user_posts,user_timezone,user_style,user_lang,user_dateformat,user_new_privmsg,user_unread_privmsg,user_last_privmsg,user_emailtime,user_viewemail,user_attachsig,user_allowhtml,user_allowbbcode,user_allowsmilies,user_allowavatar,user_allow_pm,user_allow_viewonline,user_notify,user_notify_always,user_notify_text,user_notify_pm,user_popup_pm,rank_id,user_avatar,user_avatar_type,user_email,user_icq,user_website,user_from,user_sig,user_sig_bbcode_uid,user_aim,user_yim,user_msnm,user_occ,user_interests,user_biography,user_actkey,gender,themes_id,deleted,user_viewonline,security_hash,user_karma,user_authhash,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.user_id,NEW.user_active,NEW.username,NEW.user_password,NEW.user_session_time,NEW.user_session_page,NEW.user_lastvisit,NEW.user_regdate,NEW.user_level,NEW.user_posts,NEW.user_timezone,NEW.user_style,NEW.user_lang,NEW.user_dateformat,NEW.user_new_privmsg,NEW.user_unread_privmsg,NEW.user_last_privmsg,NEW.user_emailtime,NEW.user_viewemail,NEW.user_attachsig,NEW.user_allowhtml,NEW.user_allowbbcode,NEW.user_allowsmilies,NEW.user_allowavatar,NEW.user_allow_pm,NEW.user_allow_viewonline,NEW.user_notify,NEW.user_notify_always,NEW.user_notify_text,NEW.user_notify_pm,NEW.user_popup_pm,NEW.rank_id,NEW.user_avatar,NEW.user_avatar_type,NEW.user_email,NEW.user_icq,NEW.user_website,NEW.user_from,NEW.user_sig,NEW.user_sig_bbcode_uid,NEW.user_aim,NEW.user_yim,NEW.user_msnm,NEW.user_occ,NEW.user_interests,NEW.user_biography,NEW.user_actkey,NEW.gender,NEW.themes_id,NEW.deleted,NEW.user_viewonline,NEW.security_hash,NEW.user_karma,NEW.user_authhash,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_desctrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_desctrgupd AFTER UPDATE ON jforum_vote_desc
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_desc$aud (vote_id,topic_id,vote_text,vote_start,vote_length,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.vote_id,NEW.topic_id,NEW.vote_text,NEW.vote_start,NEW.vote_length,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_resultstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_resultstrgupd AFTER UPDATE ON jforum_vote_results
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_results$aud (vote_id,vote_option_id,vote_option_text,vote_result,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.vote_id,NEW.vote_option_id,NEW.vote_option_text,NEW.vote_result,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_voterstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_voterstrgupd AFTER UPDATE ON jforum_vote_voters
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_voters$aud (vote_id,vote_user_id,vote_user_ip,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.vote_id,NEW.vote_user_id,NEW.vote_user_ip,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_wordstrgupd;

DELIMITER $$ 
CREATE TRIGGER jforum_wordstrgupd AFTER UPDATE ON jforum_words
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_words$aud (word_id,word,replacement,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.word_id,NEW.word,NEW.replacement,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_apitrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_apitrgdel AFTER DELETE ON jforum_api
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_api$aud (api_id,api_key,api_validity,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.api_id,OLD.api_key,OLD.api_validity,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attachtrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_attachtrgdel AFTER DELETE ON jforum_attach
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach$aud (attach_id,post_id,privmsgs_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.attach_id,OLD.post_id,OLD.privmsgs_id,OLD.user_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attach_desctrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_attach_desctrgdel AFTER DELETE ON jforum_attach_desc
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach_desc$aud (attach_desc_id,attach_id,physical_filename,real_filename,download_count,description,mimetype,filesize,upload_time,thumb,extension_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.attach_desc_id,OLD.attach_id,OLD.physical_filename,OLD.real_filename,OLD.download_count,OLD.description,OLD.mimetype,OLD.filesize,OLD.upload_time,OLD.thumb,OLD.extension_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_attach_quotatrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_attach_quotatrgdel AFTER DELETE ON jforum_attach_quota
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_attach_quota$aud (attach_quota_id,group_id,quota_limit_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.attach_quota_id,OLD.group_id,OLD.quota_limit_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_banlisttrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_banlisttrgdel AFTER DELETE ON jforum_banlist
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_banlist$aud (banlist_id,user_id,banlist_ip,banlist_email,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.banlist_id,OLD.user_id,OLD.banlist_ip,OLD.banlist_email,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_bannertrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_bannertrgdel AFTER DELETE ON jforum_banner
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_banner$aud (banner_id,banner_name,banner_placement,banner_description,banner_clicks,banner_views,banner_url,banner_weight,banner_active,banner_comment,banner_type,banner_width,banner_height,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.banner_id,OLD.banner_name,OLD.banner_placement,OLD.banner_description,OLD.banner_clicks,OLD.banner_views,OLD.banner_url,OLD.banner_weight,OLD.banner_active,OLD.banner_comment,OLD.banner_type,OLD.banner_width,OLD.banner_height,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_bookmarkstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_bookmarkstrgdel AFTER DELETE ON jforum_bookmarks
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_bookmarks$aud (bookmark_id,user_id,relation_id,relation_type,public_visible,title,description,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.bookmark_id,OLD.user_id,OLD.relation_id,OLD.relation_type,OLD.public_visible,OLD.title,OLD.description,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_categoriestrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_categoriestrgdel AFTER DELETE ON jforum_categories
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_categories$aud (categories_id,title,display_order,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.categories_id,OLD.title,OLD.display_order,OLD.moderated,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_configtrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_configtrgdel AFTER DELETE ON jforum_config
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_config$aud (config_name,config_value,config_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.config_name,OLD.config_value,OLD.config_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_extension_groupstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_extension_groupstrgdel AFTER DELETE ON jforum_extension_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_extension_groups$aud (extension_group_id,name,allow,upload_icon,download_mode,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.extension_group_id,OLD.name,OLD.allow,OLD.upload_icon,OLD.download_mode,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_extensionstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_extensionstrgdel AFTER DELETE ON jforum_extensions
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_extensions$aud (extension_id,extension_group_id,description,upload_icon,extension,allow,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.extension_id,OLD.extension_group_id,OLD.description,OLD.upload_icon,OLD.extension,OLD.allow,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_forumstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_forumstrgdel AFTER DELETE ON jforum_forums
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_forums$aud (forum_id,categories_id,forum_name,forum_desc,forum_order,forum_topics,forum_last_post_id,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.forum_id,OLD.categories_id,OLD.forum_name,OLD.forum_desc,OLD.forum_order,OLD.forum_topics,OLD.forum_last_post_id,OLD.moderated,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_forums_watchtrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_forums_watchtrgdel AFTER DELETE ON jforum_forums_watch
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_forums_watch$aud (forum_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.forum_id,OLD.user_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_groupstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_groupstrgdel AFTER DELETE ON jforum_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_groups$aud (group_id,group_name,group_description,parent_id,vzdn_manager_role,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.group_id,OLD.group_name,OLD.group_description,OLD.parent_id,OLD.vzdn_manager_role,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_karmatrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_karmatrgdel AFTER DELETE ON jforum_karma
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_karma$aud (karma_id,post_id,topic_id,post_user_id,from_user_id,points,rate_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.karma_id,OLD.post_id,OLD.topic_id,OLD.post_user_id,OLD.from_user_id,OLD.points,OLD.rate_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_mail_integrationtrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_mail_integrationtrgdel AFTER DELETE ON jforum_mail_integration
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_mail_integration$aud (forum_id,forum_email,pop_username,pop_password,pop_host,pop_port,pop_ssl,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.forum_id,OLD.forum_email,OLD.pop_username,OLD.pop_password,OLD.pop_host,OLD.pop_port,OLD.pop_ssl,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_moderation_logtrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_moderation_logtrgdel AFTER DELETE ON jforum_moderation_log
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_moderation_log$aud (log_id,user_id,log_description,log_original_message,log_date,log_type,post_id,topic_id,post_user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.log_id,OLD.user_id,OLD.log_description,OLD.log_original_message,OLD.log_date,OLD.log_type,OLD.post_id,OLD.topic_id,OLD.post_user_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_poststrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_poststrgdel AFTER DELETE ON jforum_posts
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_posts$aud (post_id,topic_id,forum_id,user_id,post_time,poster_ip,enable_bbcode,enable_html,enable_smilies,enable_sig,post_edit_time,post_edit_count,status,attach,need_moderate,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.post_id,OLD.topic_id,OLD.forum_id,OLD.user_id,OLD.post_time,OLD.poster_ip,OLD.enable_bbcode,OLD.enable_html,OLD.enable_smilies,OLD.enable_sig,OLD.post_edit_time,OLD.post_edit_count,OLD.status,OLD.attach,OLD.need_moderate,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_posts_texttrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_posts_texttrgdel AFTER DELETE ON jforum_posts_text
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_posts_text$aud (post_id,post_text,post_subject,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.post_id,OLD.post_text,OLD.post_subject,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_privmsgstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_privmsgstrgdel AFTER DELETE ON jforum_privmsgs
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_privmsgs$aud (privmsgs_id,privmsgs_type,privmsgs_subject,privmsgs_from_userid,privmsgs_to_userid,privmsgs_date,privmsgs_ip,privmsgs_enable_bbcode,privmsgs_enable_html,privmsgs_enable_smilies,privmsgs_attach_sig,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.privmsgs_id,OLD.privmsgs_type,OLD.privmsgs_subject,OLD.privmsgs_from_userid,OLD.privmsgs_to_userid,OLD.privmsgs_date,OLD.privmsgs_ip,OLD.privmsgs_enable_bbcode,OLD.privmsgs_enable_html,OLD.privmsgs_enable_smilies,OLD.privmsgs_attach_sig,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_privmsgs_texttrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_privmsgs_texttrgdel AFTER DELETE ON jforum_privmsgs_text
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_privmsgs_text$aud (privmsgs_id,privmsgs_text,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.privmsgs_id,OLD.privmsgs_text,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_quota_limittrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_quota_limittrgdel AFTER DELETE ON jforum_quota_limit
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_quota_limit$aud (quota_limit_id,quota_desc,quota_limit,quota_type,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.quota_limit_id,OLD.quota_desc,OLD.quota_limit,OLD.quota_type,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_rankstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_rankstrgdel AFTER DELETE ON jforum_ranks
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_ranks$aud (rank_id,rank_title,rank_min,rank_special,rank_image,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.rank_id,OLD.rank_title,OLD.rank_min,OLD.rank_special,OLD.rank_image,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_role_valuestrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_role_valuestrgdel AFTER DELETE ON jforum_role_values
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_role_values$aud (role_id,role_value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.role_id,OLD.role_value,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_rolestrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_rolestrgdel AFTER DELETE ON jforum_roles
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_roles$aud (role_id,group_id,name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.role_id,OLD.group_id,OLD.name,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_sessionstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_sessionstrgdel AFTER DELETE ON jforum_sessions
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_sessions$aud (session_id,session_user_id,session_start,session_time,session_ip,session_page,session_logged_int,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.session_id,OLD.session_user_id,OLD.session_start,OLD.session_time,OLD.session_ip,OLD.session_page,OLD.session_logged_int,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_smiliestrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_smiliestrgdel AFTER DELETE ON jforum_smilies
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_smilies$aud (smilie_id,code,url,disk_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.smilie_id,OLD.code,OLD.url,OLD.disk_name,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_themestrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_themestrgdel AFTER DELETE ON jforum_themes
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_themes$aud (themes_id,template_name,style_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.themes_id,OLD.template_name,OLD.style_name,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_topicstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_topicstrgdel AFTER DELETE ON jforum_topics
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_topics$aud (topic_id,forum_id,topic_title,user_id,topic_time,topic_views,topic_replies,topic_status,topic_vote_id,topic_type,topic_first_post_id,topic_last_post_id,topic_moved_id,moderated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.topic_id,OLD.forum_id,OLD.topic_title,OLD.user_id,OLD.topic_time,OLD.topic_views,OLD.topic_replies,OLD.topic_status,OLD.topic_vote_id,OLD.topic_type,OLD.topic_first_post_id,OLD.topic_last_post_id,OLD.topic_moved_id,OLD.moderated,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_topics_watchtrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_topics_watchtrgdel AFTER DELETE ON jforum_topics_watch
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_topics_watch$aud (topic_id,user_id,is_read,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.topic_id,OLD.user_id,OLD.is_read,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_user_groupstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_user_groupstrgdel AFTER DELETE ON jforum_user_groups
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_user_groups$aud (group_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.group_id,OLD.user_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_userstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_userstrgdel AFTER DELETE ON jforum_users
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_users$aud (user_id,user_active,username,user_password,user_session_time,user_session_page,user_lastvisit,user_regdate,user_level,user_posts,user_timezone,user_style,user_lang,user_dateformat,user_new_privmsg,user_unread_privmsg,user_last_privmsg,user_emailtime,user_viewemail,user_attachsig,user_allowhtml,user_allowbbcode,user_allowsmilies,user_allowavatar,user_allow_pm,user_allow_viewonline,user_notify,user_notify_always,user_notify_text,user_notify_pm,user_popup_pm,rank_id,user_avatar,user_avatar_type,user_email,user_icq,user_website,user_from,user_sig,user_sig_bbcode_uid,user_aim,user_yim,user_msnm,user_occ,user_interests,user_biography,user_actkey,gender,themes_id,deleted,user_viewonline,security_hash,user_karma,user_authhash,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.user_id,OLD.user_active,OLD.username,OLD.user_password,OLD.user_session_time,OLD.user_session_page,OLD.user_lastvisit,OLD.user_regdate,OLD.user_level,OLD.user_posts,OLD.user_timezone,OLD.user_style,OLD.user_lang,OLD.user_dateformat,OLD.user_new_privmsg,OLD.user_unread_privmsg,OLD.user_last_privmsg,OLD.user_emailtime,OLD.user_viewemail,OLD.user_attachsig,OLD.user_allowhtml,OLD.user_allowbbcode,OLD.user_allowsmilies,OLD.user_allowavatar,OLD.user_allow_pm,OLD.user_allow_viewonline,OLD.user_notify,OLD.user_notify_always,OLD.user_notify_text,OLD.user_notify_pm,OLD.user_popup_pm,OLD.rank_id,OLD.user_avatar,OLD.user_avatar_type,OLD.user_email,OLD.user_icq,OLD.user_website,OLD.user_from,OLD.user_sig,OLD.user_sig_bbcode_uid,OLD.user_aim,OLD.user_yim,OLD.user_msnm,OLD.user_occ,OLD.user_interests,OLD.user_biography,OLD.user_actkey,OLD.gender,OLD.themes_id,OLD.deleted,OLD.user_viewonline,OLD.security_hash,OLD.user_karma,OLD.user_authhash,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_desctrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_desctrgdel AFTER DELETE ON jforum_vote_desc
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_desc$aud (vote_id,topic_id,vote_text,vote_start,vote_length,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.vote_id,OLD.topic_id,OLD.vote_text,OLD.vote_start,OLD.vote_length,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_resultstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_resultstrgdel AFTER DELETE ON jforum_vote_results
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_results$aud (vote_id,vote_option_id,vote_option_text,vote_result,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.vote_id,OLD.vote_option_id,OLD.vote_option_text,OLD.vote_result,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_vote_voterstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_vote_voterstrgdel AFTER DELETE ON jforum_vote_voters
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_vote_voters$aud (vote_id,vote_user_id,vote_user_ip,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.vote_id,OLD.vote_user_id,OLD.vote_user_ip,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS jforum_wordstrgdel;

DELIMITER $$ 
CREATE TRIGGER jforum_wordstrgdel AFTER DELETE ON jforum_words
FOR EACH ROW BEGIN 
INSERT INTO jforum_audit.jforum_words$aud (word_id,word,replacement,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.word_id,OLD.word,OLD.replacement,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
