/opt/vzdn/tomcat_forum/bin/shutdown.sh
ps -aef | grep tomcat_forum | grep -v grep | awk {'print $2'} | xargs kill -9
/opt/vzdn/tomcat_forum/bin/startup.sh
