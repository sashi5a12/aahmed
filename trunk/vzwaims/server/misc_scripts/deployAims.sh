#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
WAR_NAME=aims
TOMCAT_HOME=/usr/java/tomcat


#Shutdown Tomcat
~/shutdownTomcat.sh


#Backup WAR
echo
echo Backing up WAR...
echo
cp $TOMCAT_HOME/webapps/$WAR_NAME.war Backup/$WAR_NAME.war.$TODAY_NOW


#Cleanup
echo
echo Cleaning up...
echo
rm -rf $TOMCAT_HOME/webapps/$WAR_NAME.war
rm -rf $TOMCAT_HOME/webapps/$WAR_NAME
rm -rf $TOMCAT_HOME/work/Catalina/localhost/$WAR_NAME
rm -rf $TOMCAT_HOME/conf/Catalina/localhost/$WAR_NAME.xml


#Copy WAR
echo
echo Copying WAR...
echo
cp WAR/$WAR_NAME.war $TOMCAT_HOME/webapps
cp WAR/$WAR_NAME.xml $TOMCAT_HOME/conf/Catalina/localhost/$WAR_NAME.xml


#Listing After Copy
echo
echo Listing After Copy
echo
echo $TOMCAT_HOME/webapps/
ls -al $TOMCAT_HOME/webapps/
echo
echo $TOMCAT_HOME/work/Catalina/localhost
ls -al $TOMCAT_HOME/work/Catalina/localhost
echo
echo $TOMCAT_HOME/conf/Catalina/localhost
ls -al $TOMCAT_HOME/conf/Catalina/localhost


#Copy WAR to PreviousBuilds Folder
echo
echo Copying new WAR to PreviousBuilds Folder...
echo
cp WAR/$WAR_NAME.war PreviousBuilds/
cp WAR/$WAR_NAME.xml PreviousBuilds/


#SCP the WAR to SPIKE
echo
echo Copying WAR to Backup Application Server - SPIKE...
echo
scp -P 32970 -r -i /usr/java/Projects/sync_keys/priv_key_sync_war_spike /usr/java/DEPLOY_WARS/WAR/$WAR_NAME.war root@172.20.40.15:/usr/java/DEPLOY_WARS/WAR
scp -P 32970 -r -i /usr/java/Projects/sync_keys/priv_key_sync_war_spike /usr/java/DEPLOY_WARS/WAR/$WAR_NAME.xml root@172.20.40.15:/usr/java/DEPLOY_WARS/WAR


#Archive and Rotate Tomcat Log (catalina.out)
echo
echo Archiving and Rotating Tomcat Log  - catalina.out...
echo
mv /usr/java/tomcat/logs/catalina.out /usr/java/TOMCAT_Log_Backups/logs/
tar -zcvf /usr/java/TOMCAT_Log_Backups/logsbackup_$TODAY_NOW.tar.gz /usr/java/TOMCAT_Log_Backups/logs/
rm -rf /usr/java/TOMCAT_Log_Backups/logs/catalina.out


#END
echo
echo DONE.
echo

