/opt/vzdn/tomcat_vzdncore/bin/shutdown.sh
ps -aef | grep tomcat_vzdncore | grep -v grep | awk {'print $2'} | xargs kill -9
/opt/vzdn/tomcat_vzdncore/bin/startup.sh