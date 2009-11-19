#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
WAR_NAME=vzdncore
TOMCAT_FOLDER_NAME=tomcat_vzdncore
TOMCAT_HOME=/usr/java/vzdn/$TOMCAT_FOLDER_NAME

echo $TODAY_NOW
cd $TOMCAT_HOME/webapps/$WAR_NAME/WEB-INF/classes
export CLASSPATH=$CLASSPATH:.:../lib/lucene-core-2.4.1.jar
java org.apache.lucene.search.IndexHTML /usr/java/vzdn/fs/vzdnsite/index /usr/java/vzdn/tomcat_vzdnsite/webapps/ROOT
