#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
WAR_NAME=vzdncore
TOMCAT_FOLDER_NAME=tomcat_vzdncore
TOMCAT_HOME=/usr/java/vzdn/$TOMCAT_FOLDER_NAME

#Backup WAR
cp $TOMCAT_HOME/webapps/$WAR_NAME.war Backup/$WAR_NAME/$WAR_NAME.war.$TODAY_NOW

#Shutdown Tomcat
$TOMCAT_HOME/bin/shutdown.sh
ps -aef | grep $TOMCAT_FOLDER_NAME | grep -v grep | awk {'print $2'} | xargs kill -9
ps -aef | grep tomcat

#Cleanup
rm -rf $TOMCAT_HOME/webapps/$WAR_NAME.war
rm -rf $TOMCAT_HOME/webapps/$WAR_NAME
rm -rf $TOMCAT_HOME/work/Catalina/localhost/$WAR_NAME

#Copy WAR
cp WAR/$WAR_NAME.war $TOMCAT_HOME/webapps

#Start Tomcat
$TOMCAT_HOME/bin/startup.sh

