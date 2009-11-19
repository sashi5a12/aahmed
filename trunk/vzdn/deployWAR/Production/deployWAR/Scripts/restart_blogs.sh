/opt/vzdn/tomcat_blogs/bin/shutdown.sh
ps -aef | grep tomcat_blogs | grep -v grep | awk {'print $2'} | xargs kill -9
/opt/vzdn/tomcat_blogs/bin/startup.sh
