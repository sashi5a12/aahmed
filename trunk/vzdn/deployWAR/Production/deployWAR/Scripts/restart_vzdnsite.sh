/opt/vzdn/tomcat_vzdnsite/bin/shutdown.sh
ps -aef | grep tomcat_vzdnsite | grep -v grep | awk {'print $2'} | xargs kill -9
/opt/vzdn/tomcat_vzdnsite/bin/startup.sh
