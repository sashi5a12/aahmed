#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
TOMCAT_HOME=/usr/java/tomcat

#Shutdown Tomcat
echo
echo Shutting Down Tomcat...
echo
ps -aef | grep tomcat
$TOMCAT_HOME/bin/shutdown.sh
ps -aef | grep tomcat | grep -v grep | awk {'print $2'} | xargs kill -9
ps -aef | grep tomcat


#END
echo
echo DONE.
echo

