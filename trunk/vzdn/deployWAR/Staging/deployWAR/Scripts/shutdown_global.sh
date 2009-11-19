/usr/java/vzdn/tomcat_global/bin/shutdown.sh
ps -aef | grep tomcat_global | grep -v grep | awk {'print $2'} | xargs kill -9
ps -aef | grep tomcat
