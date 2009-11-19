#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")
WAR_NAME=forum
TOMCAT_FOLDER_NAME=tomcat_forum
TOMCAT_HOME=/usr/java/vzdn/$TOMCAT_FOLDER_NAME
LINK_TO_FILE_SYSTEM_FOLDER=/usr/java/vzdn/rfs/forum/images/

#Remove physical folder for images
rm -rf $TOMCAT_HOME/webapps/$WAR_NAME/images

#Create soft link for images folder
ln -s $LINK_TO_FILE_SYSTEM_FOLDER $TOMCAT_HOME/webapps/$WAR_NAME/images


