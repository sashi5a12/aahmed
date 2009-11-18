#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
TOMCAT_HOME=/usr/java/tomcat

#Start Tomcat
echo
echo Starting Tomcat...
echo
cd /usr/java/DEPLOY_WARS/logs/Tomcat
$TOMCAT_HOME/bin/startup.sh


#END
echo
echo DONE.
echo

