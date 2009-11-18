#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")

echo
echo Date and Time: $TODAY_NOW
echo

cd /usr/java/tomcat/webapps/aims/WEB-INF
ant -f standalonebuild.xml $1 -Dtomcat.home=/usr/java/tomcat
