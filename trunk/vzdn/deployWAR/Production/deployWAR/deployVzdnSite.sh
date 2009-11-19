#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
TOMCAT_FOLDER_NAME=tomcat_vzdnsite
TOMCAT_HOME=/opt/vzdn/$TOMCAT_FOLDER_NAME

#Shutdown Tomcat
$TOMCAT_HOME/bin/shutdown.sh
ps -aef | grep $TOMCAT_FOLDER_NAME | grep -v grep | awk {'print $2'} | xargs kill -9
ps -aef | grep tomcat

#Cleanup
rm -rf $TOMCAT_HOME/work/

#Start Tomcat
$TOMCAT_HOME/bin/startup.sh

