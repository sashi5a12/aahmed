cd /usr/java/vzdn/tomcat_vzdncore/webapps/vzdncore/WEB-INF/classes/
export CLASSPATH=$CLASSPATH:.:../lib/lucene-core-2.4.1.jar
java org.apache.lucene.search.IndexHTML /usr/java/vzdn/fs/vzdnsite/index /usr/java/vzdn/tomcat_vzdnsite/webapps/ROOT
