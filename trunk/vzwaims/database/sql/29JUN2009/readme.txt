Folder 29JUN2009 contains altered script of AIMS_UTILS package.

Only delete_app_with_relations function in body of AIMS_UTILS package is altered

where i have added following sql statement.
	DELETE FROM AIMS_JAVA_APPS WHERE java_apps_id = p_apps_id;
	
	
Another script which this folder (29JUN2009) contains is del_place_holder_script.
This script will delete a redundant place holder for the AllianceServie notification 