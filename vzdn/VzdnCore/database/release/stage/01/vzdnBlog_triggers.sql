USE vzdnBlog;

DROP TRIGGER IF EXISTS autopingtrgins;

DELIMITER $$ 
CREATE TRIGGER autopingtrgins AFTER INSERT ON autoping
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.autoping$aud (id,websiteid,pingtargetid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.pingtargetid,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS bookmarktrgins;

DELIMITER $$ 
CREATE TRIGGER bookmarktrgins AFTER INSERT ON bookmark
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.bookmark$aud (id,folderid,name,description,url,weight,priority,image,feedurl,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.folderid,NEW.name,NEW.description,NEW.url,NEW.weight,NEW.priority,NEW.image,NEW.feedurl,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS entryattributetrgins;

DELIMITER $$ 
CREATE TRIGGER entryattributetrgins AFTER INSERT ON entryattribute
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.entryattribute$aud (id,entryid,name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entryid,NEW.name,NEW.value,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS foldertrgins;

DELIMITER $$ 
CREATE TRIGGER foldertrgins AFTER INSERT ON folder
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.folder$aud (id,name,description,websiteid,parentid,path,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.websiteid,NEW.parentid,NEW.path,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS folderassoctrgins;

DELIMITER $$ 
CREATE TRIGGER folderassoctrgins AFTER INSERT ON folderassoc
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.folderassoc$aud (id,folderid,ancestorid,relation,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.folderid,NEW.ancestorid,NEW.relation,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS newsfeedtrgins;

DELIMITER $$ 
CREATE TRIGGER newsfeedtrgins AFTER INSERT ON newsfeed
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.newsfeed$aud (id,name,description,link,websiteid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.link,NEW.websiteid,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingcategorytrgins;

DELIMITER $$ 
CREATE TRIGGER pingcategorytrgins AFTER INSERT ON pingcategory
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingcategory$aud (id,autopingid,categoryid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.autopingid,NEW.categoryid,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingqueueentrytrgins;

DELIMITER $$ 
CREATE TRIGGER pingqueueentrytrgins AFTER INSERT ON pingqueueentry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingqueueentry$aud (id,entrytime,pingtargetid,websiteid,attempts,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entrytime,NEW.pingtargetid,NEW.websiteid,NEW.attempts,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingtargettrgins;

DELIMITER $$ 
CREATE TRIGGER pingtargettrgins AFTER INSERT ON pingtarget
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingtarget$aud (id,name,pingurl,websiteid,conditioncode,lastsuccess,autoenabled,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.pingurl,NEW.websiteid,NEW.conditioncode,NEW.lastsuccess,NEW.autoenabled,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_entrytrgins;

DELIMITER $$ 
CREATE TRIGGER rag_entrytrgins AFTER INSERT ON rag_entry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_entry$aud (id,subscription_id,handle,title,guid,permalink,author,content,categories,published,updated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.subscription_id,NEW.handle,NEW.title,NEW.guid,NEW.permalink,NEW.author,NEW.content,NEW.categories,NEW.published,NEW.updated,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_grouptrgins;

DELIMITER $$ 
CREATE TRIGGER rag_grouptrgins AFTER INSERT ON rag_group
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_group$aud (id,planet_id,handle,title,description,max_page_entries,max_feed_entries,cat_restriction,group_page,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.planet_id,NEW.handle,NEW.title,NEW.description,NEW.max_page_entries,NEW.max_feed_entries,NEW.cat_restriction,NEW.group_page,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_group_subscriptiontrgins;

DELIMITER $$ 
CREATE TRIGGER rag_group_subscriptiontrgins AFTER INSERT ON rag_group_subscription
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_group_subscription$aud (group_id,subscription_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.group_id,NEW.subscription_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_planettrgins;

DELIMITER $$ 
CREATE TRIGGER rag_planettrgins AFTER INSERT ON rag_planet
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_planet$aud (id,handle,title,description,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.handle,NEW.title,NEW.description,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_propertiestrgins;

DELIMITER $$ 
CREATE TRIGGER rag_propertiestrgins AFTER INSERT ON rag_properties
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_properties$aud (name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.name,NEW.value,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_subscriptiontrgins;

DELIMITER $$ 
CREATE TRIGGER rag_subscriptiontrgins AFTER INSERT ON rag_subscription
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_subscription$aud (id,title,feed_url,site_url,author,last_updated,inbound_links,inbound_blogs,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.title,NEW.feed_url,NEW.site_url,NEW.author,NEW.last_updated,NEW.inbound_links,NEW.inbound_blogs,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS referertrgins;

DELIMITER $$ 
CREATE TRIGGER referertrgins AFTER INSERT ON referer
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.referer$aud (id,websiteid,entryid,datestr,refurl,refpermalink,reftime,requrl,title,excerpt,dayhits,totalhits,visible,duplicate,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.entryid,NEW.datestr,NEW.refurl,NEW.refpermalink,NEW.reftime,NEW.requrl,NEW.title,NEW.excerpt,NEW.dayhits,NEW.totalhits,NEW.visible,NEW.duplicate,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_audit_logtrgins;

DELIMITER $$ 
CREATE TRIGGER roller_audit_logtrgins AFTER INSERT ON roller_audit_log
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_audit_log$aud (id,user_id,object_id,object_class,comment_text,change_time,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.user_id,NEW.object_id,NEW.object_class,NEW.comment_text,NEW.change_time,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_commenttrgins;

DELIMITER $$ 
CREATE TRIGGER roller_commenttrgins AFTER INSERT ON roller_comment
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_comment$aud (id,entryid,name,email,url,content,posttime,notify,remotehost,referrer,useragent,status,plugins,contenttype,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entryid,NEW.name,NEW.email,NEW.url,NEW.content,NEW.posttime,NEW.notify,NEW.remotehost,NEW.referrer,NEW.useragent,NEW.status,NEW.plugins,NEW.contenttype,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_hitcountstrgins;

DELIMITER $$ 
CREATE TRIGGER roller_hitcountstrgins AFTER INSERT ON roller_hitcounts
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_hitcounts$aud (id,websiteid,dailyhits,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.dailyhits,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_propertiestrgins;

DELIMITER $$ 
CREATE TRIGGER roller_propertiestrgins AFTER INSERT ON roller_properties
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_properties$aud (name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.name,NEW.value,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_tasklocktrgins;

DELIMITER $$ 
CREATE TRIGGER roller_tasklocktrgins AFTER INSERT ON roller_tasklock
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_tasklock$aud (id,name,islocked,timeacquired,timeleased,lastrun,client,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.islocked,NEW.timeacquired,NEW.timeleased,NEW.lastrun,NEW.client,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_user_permissionstrgins;

DELIMITER $$ 
CREATE TRIGGER roller_user_permissionstrgins AFTER INSERT ON roller_user_permissions
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_user_permissions$aud (id,website_id,user_id,permission_mask,pending,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.website_id,NEW.user_id,NEW.permission_mask,NEW.pending,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_weblogentrytagtrgins;

DELIMITER $$ 
CREATE TRIGGER roller_weblogentrytagtrgins AFTER INSERT ON roller_weblogentrytag
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_weblogentrytag$aud (id,entryid,websiteid,userid,name,time,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entryid,NEW.websiteid,NEW.userid,NEW.name,NEW.time,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_weblogentrytagaggtrgins;

DELIMITER $$ 
CREATE TRIGGER roller_weblogentrytagaggtrgins AFTER INSERT ON roller_weblogentrytagagg
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_weblogentrytagagg$aud (id,websiteid,name,total,lastused,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.name,NEW.total,NEW.lastused,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rollerconfigtrgins;

DELIMITER $$ 
CREATE TRIGGER rollerconfigtrgins AFTER INSERT ON rollerconfig
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rollerconfig$aud (id,sitedescription,sitename,emailaddress,absoluteurl,adminusers,encryptpasswords,algorithm,newuserallowed,editorpages,userthemes,indexdir,memdebug,autoformatcomments,escapecommenthtml,emailcomments,enableaggregator,enablelinkback,rsscachetime,rssusecache,uploadallow,uploadforbid,uploadenabled,uploaddir,uploadpath,uploadmaxdirmb,uploadmaxfilemb,dbversion,refspamwords,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.sitedescription,NEW.sitename,NEW.emailaddress,NEW.absoluteurl,NEW.adminusers,NEW.encryptpasswords,NEW.algorithm,NEW.newuserallowed,NEW.editorpages,NEW.userthemes,NEW.indexdir,NEW.memdebug,NEW.autoformatcomments,NEW.escapecommenthtml,NEW.emailcomments,NEW.enableaggregator,NEW.enablelinkback,NEW.rsscachetime,NEW.rssusecache,NEW.uploadallow,NEW.uploadforbid,NEW.uploadenabled,NEW.uploaddir,NEW.uploadpath,NEW.uploadmaxdirmb,NEW.uploadmaxfilemb,NEW.dbversion,NEW.refspamwords,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rollerusertrgins;

DELIMITER $$ 
CREATE TRIGGER rollerusertrgins AFTER INSERT ON rolleruser
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rolleruser$aud (id,username,passphrase,screenname,fullname,emailaddress,activationcode,datecreated,locale,timezone,isenabled,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.username,NEW.passphrase,NEW.screenname,NEW.fullname,NEW.emailaddress,NEW.activationcode,NEW.datecreated,NEW.locale,NEW.timezone,NEW.isenabled,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS usercookietrgins;

DELIMITER $$ 
CREATE TRIGGER usercookietrgins AFTER INSERT ON usercookie
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.usercookie$aud (id,username,cookieid,datecreated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.username,NEW.cookieid,NEW.datecreated,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS userroletrgins;

DELIMITER $$ 
CREATE TRIGGER userroletrgins AFTER INSERT ON userrole
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.userrole$aud (id,rolename,username,userid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.rolename,NEW.username,NEW.userid,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogcategorytrgins;

DELIMITER $$ 
CREATE TRIGGER weblogcategorytrgins AFTER INSERT ON weblogcategory
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogcategory$aud (id,name,description,websiteid,image,parentid,path,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.websiteid,NEW.image,NEW.parentid,NEW.path,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogcategoryassoctrgins;

DELIMITER $$ 
CREATE TRIGGER weblogcategoryassoctrgins AFTER INSERT ON weblogcategoryassoc
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogcategoryassoc$aud (id,categoryid,ancestorid,relation,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.categoryid,NEW.ancestorid,NEW.relation,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogentrytrgins;

DELIMITER $$ 
CREATE TRIGGER weblogentrytrgins AFTER INSERT ON weblogentry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogentry$aud (id,userid,anchor,title,text,pubtime,updatetime,websiteid,categoryid,publishentry,link,plugins,allowcomments,commentdays,rightToLeft,pinnedtomain,locale,status,summary,content_type,content_src,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.userid,NEW.anchor,NEW.title,NEW.text,NEW.pubtime,NEW.updatetime,NEW.websiteid,NEW.categoryid,NEW.publishentry,NEW.link,NEW.plugins,NEW.allowcomments,NEW.commentdays,NEW.rightToLeft,NEW.pinnedtomain,NEW.locale,NEW.status,NEW.summary,NEW.content_type,NEW.content_src,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS webpagetrgins;

DELIMITER $$ 
CREATE TRIGGER webpagetrgins AFTER INSERT ON webpage
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.webpage$aud (id,name,description,link,websiteid,template,updatetime,hidden,navbar,templatelang,decorator,outputtype,action,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.link,NEW.websiteid,NEW.template,NEW.updatetime,NEW.hidden,NEW.navbar,NEW.templatelang,NEW.decorator,NEW.outputtype,NEW.action,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS websitetrgins;

DELIMITER $$ 
CREATE TRIGGER websitetrgins AFTER INSERT ON website
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.website$aud (id,name,handle,description,userid,defaultpageid,weblogdayid,ignorewords,enablebloggerapi,editorpage,bloggercatid,defaultcatid,allowcomments,emailcomments,emailfromaddress,emailaddress,editortheme,locale,timezone,defaultplugins,isenabled,isactive,datecreated,blacklist,defaultallowcomments,defaultcommentdays,commentmod,displaycnt,lastmodified,pagemodels,enablemultilang,showalllangs,customstylesheet,about,icon,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.handle,NEW.description,NEW.userid,NEW.defaultpageid,NEW.weblogdayid,NEW.ignorewords,NEW.enablebloggerapi,NEW.editorpage,NEW.bloggercatid,NEW.defaultcatid,NEW.allowcomments,NEW.emailcomments,NEW.emailfromaddress,NEW.emailaddress,NEW.editortheme,NEW.locale,NEW.timezone,NEW.defaultplugins,NEW.isenabled,NEW.isactive,NEW.datecreated,NEW.blacklist,NEW.defaultallowcomments,NEW.defaultcommentdays,NEW.commentmod,NEW.displaycnt,NEW.lastmodified,NEW.pagemodels,NEW.enablemultilang,NEW.showalllangs,NEW.customstylesheet,NEW.about,NEW.icon,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS autopingtrgupd;

DELIMITER $$ 
CREATE TRIGGER autopingtrgupd AFTER UPDATE ON autoping
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.autoping$aud (id,websiteid,pingtargetid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.pingtargetid,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS bookmarktrgupd;

DELIMITER $$ 
CREATE TRIGGER bookmarktrgupd AFTER UPDATE ON bookmark
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.bookmark$aud (id,folderid,name,description,url,weight,priority,image,feedurl,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.folderid,NEW.name,NEW.description,NEW.url,NEW.weight,NEW.priority,NEW.image,NEW.feedurl,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS entryattributetrgupd;

DELIMITER $$ 
CREATE TRIGGER entryattributetrgupd AFTER UPDATE ON entryattribute
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.entryattribute$aud (id,entryid,name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entryid,NEW.name,NEW.value,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS foldertrgupd;

DELIMITER $$ 
CREATE TRIGGER foldertrgupd AFTER UPDATE ON folder
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.folder$aud (id,name,description,websiteid,parentid,path,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.websiteid,NEW.parentid,NEW.path,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS folderassoctrgupd;

DELIMITER $$ 
CREATE TRIGGER folderassoctrgupd AFTER UPDATE ON folderassoc
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.folderassoc$aud (id,folderid,ancestorid,relation,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.folderid,NEW.ancestorid,NEW.relation,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS newsfeedtrgupd;

DELIMITER $$ 
CREATE TRIGGER newsfeedtrgupd AFTER UPDATE ON newsfeed
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.newsfeed$aud (id,name,description,link,websiteid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.link,NEW.websiteid,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingcategorytrgupd;

DELIMITER $$ 
CREATE TRIGGER pingcategorytrgupd AFTER UPDATE ON pingcategory
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingcategory$aud (id,autopingid,categoryid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.autopingid,NEW.categoryid,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingqueueentrytrgupd;

DELIMITER $$ 
CREATE TRIGGER pingqueueentrytrgupd AFTER UPDATE ON pingqueueentry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingqueueentry$aud (id,entrytime,pingtargetid,websiteid,attempts,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entrytime,NEW.pingtargetid,NEW.websiteid,NEW.attempts,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingtargettrgupd;

DELIMITER $$ 
CREATE TRIGGER pingtargettrgupd AFTER UPDATE ON pingtarget
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingtarget$aud (id,name,pingurl,websiteid,conditioncode,lastsuccess,autoenabled,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.pingurl,NEW.websiteid,NEW.conditioncode,NEW.lastsuccess,NEW.autoenabled,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_entrytrgupd;

DELIMITER $$ 
CREATE TRIGGER rag_entrytrgupd AFTER UPDATE ON rag_entry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_entry$aud (id,subscription_id,handle,title,guid,permalink,author,content,categories,published,updated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.subscription_id,NEW.handle,NEW.title,NEW.guid,NEW.permalink,NEW.author,NEW.content,NEW.categories,NEW.published,NEW.updated,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_grouptrgupd;

DELIMITER $$ 
CREATE TRIGGER rag_grouptrgupd AFTER UPDATE ON rag_group
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_group$aud (id,planet_id,handle,title,description,max_page_entries,max_feed_entries,cat_restriction,group_page,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.planet_id,NEW.handle,NEW.title,NEW.description,NEW.max_page_entries,NEW.max_feed_entries,NEW.cat_restriction,NEW.group_page,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_group_subscriptiontrgupd;

DELIMITER $$ 
CREATE TRIGGER rag_group_subscriptiontrgupd AFTER UPDATE ON rag_group_subscription
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_group_subscription$aud (group_id,subscription_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.group_id,NEW.subscription_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_planettrgupd;

DELIMITER $$ 
CREATE TRIGGER rag_planettrgupd AFTER UPDATE ON rag_planet
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_planet$aud (id,handle,title,description,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.handle,NEW.title,NEW.description,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_propertiestrgupd;

DELIMITER $$ 
CREATE TRIGGER rag_propertiestrgupd AFTER UPDATE ON rag_properties
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_properties$aud (name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.name,NEW.value,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_subscriptiontrgupd;

DELIMITER $$ 
CREATE TRIGGER rag_subscriptiontrgupd AFTER UPDATE ON rag_subscription
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_subscription$aud (id,title,feed_url,site_url,author,last_updated,inbound_links,inbound_blogs,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.title,NEW.feed_url,NEW.site_url,NEW.author,NEW.last_updated,NEW.inbound_links,NEW.inbound_blogs,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS referertrgupd;

DELIMITER $$ 
CREATE TRIGGER referertrgupd AFTER UPDATE ON referer
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.referer$aud (id,websiteid,entryid,datestr,refurl,refpermalink,reftime,requrl,title,excerpt,dayhits,totalhits,visible,duplicate,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.entryid,NEW.datestr,NEW.refurl,NEW.refpermalink,NEW.reftime,NEW.requrl,NEW.title,NEW.excerpt,NEW.dayhits,NEW.totalhits,NEW.visible,NEW.duplicate,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_audit_logtrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_audit_logtrgupd AFTER UPDATE ON roller_audit_log
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_audit_log$aud (id,user_id,object_id,object_class,comment_text,change_time,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.user_id,NEW.object_id,NEW.object_class,NEW.comment_text,NEW.change_time,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_commenttrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_commenttrgupd AFTER UPDATE ON roller_comment
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_comment$aud (id,entryid,name,email,url,content,posttime,notify,remotehost,referrer,useragent,status,plugins,contenttype,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entryid,NEW.name,NEW.email,NEW.url,NEW.content,NEW.posttime,NEW.notify,NEW.remotehost,NEW.referrer,NEW.useragent,NEW.status,NEW.plugins,NEW.contenttype,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_hitcountstrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_hitcountstrgupd AFTER UPDATE ON roller_hitcounts
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_hitcounts$aud (id,websiteid,dailyhits,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.dailyhits,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_propertiestrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_propertiestrgupd AFTER UPDATE ON roller_properties
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_properties$aud (name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.name,NEW.value,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_tasklocktrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_tasklocktrgupd AFTER UPDATE ON roller_tasklock
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_tasklock$aud (id,name,islocked,timeacquired,timeleased,lastrun,client,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.islocked,NEW.timeacquired,NEW.timeleased,NEW.lastrun,NEW.client,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_user_permissionstrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_user_permissionstrgupd AFTER UPDATE ON roller_user_permissions
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_user_permissions$aud (id,website_id,user_id,permission_mask,pending,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.website_id,NEW.user_id,NEW.permission_mask,NEW.pending,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_weblogentrytagtrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_weblogentrytagtrgupd AFTER UPDATE ON roller_weblogentrytag
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_weblogentrytag$aud (id,entryid,websiteid,userid,name,time,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.entryid,NEW.websiteid,NEW.userid,NEW.name,NEW.time,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_weblogentrytagaggtrgupd;

DELIMITER $$ 
CREATE TRIGGER roller_weblogentrytagaggtrgupd AFTER UPDATE ON roller_weblogentrytagagg
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_weblogentrytagagg$aud (id,websiteid,name,total,lastused,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.websiteid,NEW.name,NEW.total,NEW.lastused,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rollerconfigtrgupd;

DELIMITER $$ 
CREATE TRIGGER rollerconfigtrgupd AFTER UPDATE ON rollerconfig
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rollerconfig$aud (id,sitedescription,sitename,emailaddress,absoluteurl,adminusers,encryptpasswords,algorithm,newuserallowed,editorpages,userthemes,indexdir,memdebug,autoformatcomments,escapecommenthtml,emailcomments,enableaggregator,enablelinkback,rsscachetime,rssusecache,uploadallow,uploadforbid,uploadenabled,uploaddir,uploadpath,uploadmaxdirmb,uploadmaxfilemb,dbversion,refspamwords,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.sitedescription,NEW.sitename,NEW.emailaddress,NEW.absoluteurl,NEW.adminusers,NEW.encryptpasswords,NEW.algorithm,NEW.newuserallowed,NEW.editorpages,NEW.userthemes,NEW.indexdir,NEW.memdebug,NEW.autoformatcomments,NEW.escapecommenthtml,NEW.emailcomments,NEW.enableaggregator,NEW.enablelinkback,NEW.rsscachetime,NEW.rssusecache,NEW.uploadallow,NEW.uploadforbid,NEW.uploadenabled,NEW.uploaddir,NEW.uploadpath,NEW.uploadmaxdirmb,NEW.uploadmaxfilemb,NEW.dbversion,NEW.refspamwords,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rollerusertrgupd;

DELIMITER $$ 
CREATE TRIGGER rollerusertrgupd AFTER UPDATE ON rolleruser
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rolleruser$aud (id,username,passphrase,screenname,fullname,emailaddress,activationcode,datecreated,locale,timezone,isenabled,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.username,NEW.passphrase,NEW.screenname,NEW.fullname,NEW.emailaddress,NEW.activationcode,NEW.datecreated,NEW.locale,NEW.timezone,NEW.isenabled,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS usercookietrgupd;

DELIMITER $$ 
CREATE TRIGGER usercookietrgupd AFTER UPDATE ON usercookie
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.usercookie$aud (id,username,cookieid,datecreated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.username,NEW.cookieid,NEW.datecreated,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS userroletrgupd;

DELIMITER $$ 
CREATE TRIGGER userroletrgupd AFTER UPDATE ON userrole
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.userrole$aud (id,rolename,username,userid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.rolename,NEW.username,NEW.userid,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogcategorytrgupd;

DELIMITER $$ 
CREATE TRIGGER weblogcategorytrgupd AFTER UPDATE ON weblogcategory
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogcategory$aud (id,name,description,websiteid,image,parentid,path,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.websiteid,NEW.image,NEW.parentid,NEW.path,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogcategoryassoctrgupd;

DELIMITER $$ 
CREATE TRIGGER weblogcategoryassoctrgupd AFTER UPDATE ON weblogcategoryassoc
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogcategoryassoc$aud (id,categoryid,ancestorid,relation,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.categoryid,NEW.ancestorid,NEW.relation,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogentrytrgupd;

DELIMITER $$ 
CREATE TRIGGER weblogentrytrgupd AFTER UPDATE ON weblogentry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogentry$aud (id,userid,anchor,title,text,pubtime,updatetime,websiteid,categoryid,publishentry,link,plugins,allowcomments,commentdays,rightToLeft,pinnedtomain,locale,status,summary,content_type,content_src,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.userid,NEW.anchor,NEW.title,NEW.text,NEW.pubtime,NEW.updatetime,NEW.websiteid,NEW.categoryid,NEW.publishentry,NEW.link,NEW.plugins,NEW.allowcomments,NEW.commentdays,NEW.rightToLeft,NEW.pinnedtomain,NEW.locale,NEW.status,NEW.summary,NEW.content_type,NEW.content_src,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS webpagetrgupd;

DELIMITER $$ 
CREATE TRIGGER webpagetrgupd AFTER UPDATE ON webpage
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.webpage$aud (id,name,description,link,websiteid,template,updatetime,hidden,navbar,templatelang,decorator,outputtype,action,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.description,NEW.link,NEW.websiteid,NEW.template,NEW.updatetime,NEW.hidden,NEW.navbar,NEW.templatelang,NEW.decorator,NEW.outputtype,NEW.action,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS websitetrgupd;

DELIMITER $$ 
CREATE TRIGGER websitetrgupd AFTER UPDATE ON website
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.website$aud (id,name,handle,description,userid,defaultpageid,weblogdayid,ignorewords,enablebloggerapi,editorpage,bloggercatid,defaultcatid,allowcomments,emailcomments,emailfromaddress,emailaddress,editortheme,locale,timezone,defaultplugins,isenabled,isactive,datecreated,blacklist,defaultallowcomments,defaultcommentdays,commentmod,displaycnt,lastmodified,pagemodels,enablemultilang,showalllangs,customstylesheet,about,icon,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.name,NEW.handle,NEW.description,NEW.userid,NEW.defaultpageid,NEW.weblogdayid,NEW.ignorewords,NEW.enablebloggerapi,NEW.editorpage,NEW.bloggercatid,NEW.defaultcatid,NEW.allowcomments,NEW.emailcomments,NEW.emailfromaddress,NEW.emailaddress,NEW.editortheme,NEW.locale,NEW.timezone,NEW.defaultplugins,NEW.isenabled,NEW.isactive,NEW.datecreated,NEW.blacklist,NEW.defaultallowcomments,NEW.defaultcommentdays,NEW.commentmod,NEW.displaycnt,NEW.lastmodified,NEW.pagemodels,NEW.enablemultilang,NEW.showalllangs,NEW.customstylesheet,NEW.about,NEW.icon,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS autopingtrgdel;

DELIMITER $$ 
CREATE TRIGGER autopingtrgdel AFTER DELETE ON autoping
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.autoping$aud (id,websiteid,pingtargetid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.websiteid,OLD.pingtargetid,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS bookmarktrgdel;

DELIMITER $$ 
CREATE TRIGGER bookmarktrgdel AFTER DELETE ON bookmark
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.bookmark$aud (id,folderid,name,description,url,weight,priority,image,feedurl,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.folderid,OLD.name,OLD.description,OLD.url,OLD.weight,OLD.priority,OLD.image,OLD.feedurl,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS entryattributetrgdel;

DELIMITER $$ 
CREATE TRIGGER entryattributetrgdel AFTER DELETE ON entryattribute
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.entryattribute$aud (id,entryid,name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.entryid,OLD.name,OLD.value,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS foldertrgdel;

DELIMITER $$ 
CREATE TRIGGER foldertrgdel AFTER DELETE ON folder
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.folder$aud (id,name,description,websiteid,parentid,path,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.description,OLD.websiteid,OLD.parentid,OLD.path,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS folderassoctrgdel;

DELIMITER $$ 
CREATE TRIGGER folderassoctrgdel AFTER DELETE ON folderassoc
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.folderassoc$aud (id,folderid,ancestorid,relation,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.folderid,OLD.ancestorid,OLD.relation,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS newsfeedtrgdel;

DELIMITER $$ 
CREATE TRIGGER newsfeedtrgdel AFTER DELETE ON newsfeed
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.newsfeed$aud (id,name,description,link,websiteid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.description,OLD.link,OLD.websiteid,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingcategorytrgdel;

DELIMITER $$ 
CREATE TRIGGER pingcategorytrgdel AFTER DELETE ON pingcategory
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingcategory$aud (id,autopingid,categoryid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.autopingid,OLD.categoryid,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingqueueentrytrgdel;

DELIMITER $$ 
CREATE TRIGGER pingqueueentrytrgdel AFTER DELETE ON pingqueueentry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingqueueentry$aud (id,entrytime,pingtargetid,websiteid,attempts,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.entrytime,OLD.pingtargetid,OLD.websiteid,OLD.attempts,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS pingtargettrgdel;

DELIMITER $$ 
CREATE TRIGGER pingtargettrgdel AFTER DELETE ON pingtarget
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.pingtarget$aud (id,name,pingurl,websiteid,conditioncode,lastsuccess,autoenabled,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.pingurl,OLD.websiteid,OLD.conditioncode,OLD.lastsuccess,OLD.autoenabled,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_entrytrgdel;

DELIMITER $$ 
CREATE TRIGGER rag_entrytrgdel AFTER DELETE ON rag_entry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_entry$aud (id,subscription_id,handle,title,guid,permalink,author,content,categories,published,updated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.subscription_id,OLD.handle,OLD.title,OLD.guid,OLD.permalink,OLD.author,OLD.content,OLD.categories,OLD.published,OLD.updated,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_grouptrgdel;

DELIMITER $$ 
CREATE TRIGGER rag_grouptrgdel AFTER DELETE ON rag_group
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_group$aud (id,planet_id,handle,title,description,max_page_entries,max_feed_entries,cat_restriction,group_page,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.planet_id,OLD.handle,OLD.title,OLD.description,OLD.max_page_entries,OLD.max_feed_entries,OLD.cat_restriction,OLD.group_page,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_group_subscriptiontrgdel;

DELIMITER $$ 
CREATE TRIGGER rag_group_subscriptiontrgdel AFTER DELETE ON rag_group_subscription
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_group_subscription$aud (group_id,subscription_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.group_id,OLD.subscription_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_planettrgdel;

DELIMITER $$ 
CREATE TRIGGER rag_planettrgdel AFTER DELETE ON rag_planet
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_planet$aud (id,handle,title,description,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.handle,OLD.title,OLD.description,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_propertiestrgdel;

DELIMITER $$ 
CREATE TRIGGER rag_propertiestrgdel AFTER DELETE ON rag_properties
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_properties$aud (name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.name,OLD.value,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rag_subscriptiontrgdel;

DELIMITER $$ 
CREATE TRIGGER rag_subscriptiontrgdel AFTER DELETE ON rag_subscription
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rag_subscription$aud (id,title,feed_url,site_url,author,last_updated,inbound_links,inbound_blogs,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.title,OLD.feed_url,OLD.site_url,OLD.author,OLD.last_updated,OLD.inbound_links,OLD.inbound_blogs,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS referertrgdel;

DELIMITER $$ 
CREATE TRIGGER referertrgdel AFTER DELETE ON referer
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.referer$aud (id,websiteid,entryid,datestr,refurl,refpermalink,reftime,requrl,title,excerpt,dayhits,totalhits,visible,duplicate,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.websiteid,OLD.entryid,OLD.datestr,OLD.refurl,OLD.refpermalink,OLD.reftime,OLD.requrl,OLD.title,OLD.excerpt,OLD.dayhits,OLD.totalhits,OLD.visible,OLD.duplicate,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_audit_logtrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_audit_logtrgdel AFTER DELETE ON roller_audit_log
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_audit_log$aud (id,user_id,object_id,object_class,comment_text,change_time,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.user_id,OLD.object_id,OLD.object_class,OLD.comment_text,OLD.change_time,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_commenttrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_commenttrgdel AFTER DELETE ON roller_comment
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_comment$aud (id,entryid,name,email,url,content,posttime,notify,remotehost,referrer,useragent,status,plugins,contenttype,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.entryid,OLD.name,OLD.email,OLD.url,OLD.content,OLD.posttime,OLD.notify,OLD.remotehost,OLD.referrer,OLD.useragent,OLD.status,OLD.plugins,OLD.contenttype,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_hitcountstrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_hitcountstrgdel AFTER DELETE ON roller_hitcounts
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_hitcounts$aud (id,websiteid,dailyhits,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.websiteid,OLD.dailyhits,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_propertiestrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_propertiestrgdel AFTER DELETE ON roller_properties
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_properties$aud (name,value,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.name,OLD.value,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_tasklocktrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_tasklocktrgdel AFTER DELETE ON roller_tasklock
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_tasklock$aud (id,name,islocked,timeacquired,timeleased,lastrun,client,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.islocked,OLD.timeacquired,OLD.timeleased,OLD.lastrun,OLD.client,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_user_permissionstrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_user_permissionstrgdel AFTER DELETE ON roller_user_permissions
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_user_permissions$aud (id,website_id,user_id,permission_mask,pending,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.website_id,OLD.user_id,OLD.permission_mask,OLD.pending,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_weblogentrytagtrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_weblogentrytagtrgdel AFTER DELETE ON roller_weblogentrytag
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_weblogentrytag$aud (id,entryid,websiteid,userid,name,time,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.entryid,OLD.websiteid,OLD.userid,OLD.name,OLD.time,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS roller_weblogentrytagaggtrgdel;

DELIMITER $$ 
CREATE TRIGGER roller_weblogentrytagaggtrgdel AFTER DELETE ON roller_weblogentrytagagg
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.roller_weblogentrytagagg$aud (id,websiteid,name,total,lastused,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.websiteid,OLD.name,OLD.total,OLD.lastused,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rollerconfigtrgdel;

DELIMITER $$ 
CREATE TRIGGER rollerconfigtrgdel AFTER DELETE ON rollerconfig
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rollerconfig$aud (id,sitedescription,sitename,emailaddress,absoluteurl,adminusers,encryptpasswords,algorithm,newuserallowed,editorpages,userthemes,indexdir,memdebug,autoformatcomments,escapecommenthtml,emailcomments,enableaggregator,enablelinkback,rsscachetime,rssusecache,uploadallow,uploadforbid,uploadenabled,uploaddir,uploadpath,uploadmaxdirmb,uploadmaxfilemb,dbversion,refspamwords,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.sitedescription,OLD.sitename,OLD.emailaddress,OLD.absoluteurl,OLD.adminusers,OLD.encryptpasswords,OLD.algorithm,OLD.newuserallowed,OLD.editorpages,OLD.userthemes,OLD.indexdir,OLD.memdebug,OLD.autoformatcomments,OLD.escapecommenthtml,OLD.emailcomments,OLD.enableaggregator,OLD.enablelinkback,OLD.rsscachetime,OLD.rssusecache,OLD.uploadallow,OLD.uploadforbid,OLD.uploadenabled,OLD.uploaddir,OLD.uploadpath,OLD.uploadmaxdirmb,OLD.uploadmaxfilemb,OLD.dbversion,OLD.refspamwords,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS rollerusertrgdel;

DELIMITER $$ 
CREATE TRIGGER rollerusertrgdel AFTER DELETE ON rolleruser
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.rolleruser$aud (id,username,passphrase,screenname,fullname,emailaddress,activationcode,datecreated,locale,timezone,isenabled,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.username,OLD.passphrase,OLD.screenname,OLD.fullname,OLD.emailaddress,OLD.activationcode,OLD.datecreated,OLD.locale,OLD.timezone,OLD.isenabled,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS usercookietrgdel;

DELIMITER $$ 
CREATE TRIGGER usercookietrgdel AFTER DELETE ON usercookie
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.usercookie$aud (id,username,cookieid,datecreated,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.username,OLD.cookieid,OLD.datecreated,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS userroletrgdel;

DELIMITER $$ 
CREATE TRIGGER userroletrgdel AFTER DELETE ON userrole
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.userrole$aud (id,rolename,username,userid,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.rolename,OLD.username,OLD.userid,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogcategorytrgdel;

DELIMITER $$ 
CREATE TRIGGER weblogcategorytrgdel AFTER DELETE ON weblogcategory
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogcategory$aud (id,name,description,websiteid,image,parentid,path,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.description,OLD.websiteid,OLD.image,OLD.parentid,OLD.path,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogcategoryassoctrgdel;

DELIMITER $$ 
CREATE TRIGGER weblogcategoryassoctrgdel AFTER DELETE ON weblogcategoryassoc
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogcategoryassoc$aud (id,categoryid,ancestorid,relation,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.categoryid,OLD.ancestorid,OLD.relation,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS weblogentrytrgdel;

DELIMITER $$ 
CREATE TRIGGER weblogentrytrgdel AFTER DELETE ON weblogentry
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.weblogentry$aud (id,userid,anchor,title,text,pubtime,updatetime,websiteid,categoryid,publishentry,link,plugins,allowcomments,commentdays,rightToLeft,pinnedtomain,locale,status,summary,content_type,content_src,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.userid,OLD.anchor,OLD.title,OLD.text,OLD.pubtime,OLD.updatetime,OLD.websiteid,OLD.categoryid,OLD.publishentry,OLD.link,OLD.plugins,OLD.allowcomments,OLD.commentdays,OLD.rightToLeft,OLD.pinnedtomain,OLD.locale,OLD.status,OLD.summary,OLD.content_type,OLD.content_src,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS webpagetrgdel;

DELIMITER $$ 
CREATE TRIGGER webpagetrgdel AFTER DELETE ON webpage
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.webpage$aud (id,name,description,link,websiteid,template,updatetime,hidden,navbar,templatelang,decorator,outputtype,action,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.description,OLD.link,OLD.websiteid,OLD.template,OLD.updatetime,OLD.hidden,OLD.navbar,OLD.templatelang,OLD.decorator,OLD.outputtype,OLD.action,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS websitetrgdel;

DELIMITER $$ 
CREATE TRIGGER websitetrgdel AFTER DELETE ON website
FOR EACH ROW BEGIN 
INSERT INTO vzdnBlog_audit.website$aud (id,name,handle,description,userid,defaultpageid,weblogdayid,ignorewords,enablebloggerapi,editorpage,bloggercatid,defaultcatid,allowcomments,emailcomments,emailfromaddress,emailaddress,editortheme,locale,timezone,defaultplugins,isenabled,isactive,datecreated,blacklist,defaultallowcomments,defaultcommentdays,commentmod,displaycnt,lastmodified,pagemodels,enablemultilang,showalllangs,customstylesheet,about,icon,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.name,OLD.handle,OLD.description,OLD.userid,OLD.defaultpageid,OLD.weblogdayid,OLD.ignorewords,OLD.enablebloggerapi,OLD.editorpage,OLD.bloggercatid,OLD.defaultcatid,OLD.allowcomments,OLD.emailcomments,OLD.emailfromaddress,OLD.emailaddress,OLD.editortheme,OLD.locale,OLD.timezone,OLD.defaultplugins,OLD.isenabled,OLD.isactive,OLD.datecreated,OLD.blacklist,OLD.defaultallowcomments,OLD.defaultcommentdays,OLD.commentmod,OLD.displaycnt,OLD.lastmodified,OLD.pagemodels,OLD.enablemultilang,OLD.showalllangs,OLD.customstylesheet,OLD.about,OLD.icon,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
