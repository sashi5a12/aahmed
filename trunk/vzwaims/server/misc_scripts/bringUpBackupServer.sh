echo Shuting down Tomcat....
~/shutdownTomcat.sh
echo .
echo .
echo .
echo Deploying AIMS.....
/usr/java/DEPLOY_WARS/deployAims.sh
echo .
echo .
echo .
echo Deploying VZWReports.....
/usr/java/DEPLOY_WARS/deployVZWReports.sh
echo .
echo .
echo .
echo Deploying BINARY.....
/usr/java/DEPLOY_WARS/deployBinary.sh
echo .
echo .
echo .
echo Deploying BDS.....
/usr/java/DEPLOY_WARS/deployBds.sh
echo .
echo .
echo .
echo Starting Tomcat....
~/startupTomcat.sh
echo Tomcat Started.

