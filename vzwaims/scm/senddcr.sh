#Variables
TODAY_NOW=$(date +"%Y%m%d%H%M%S")

echo
echo Date and Time: $TODAY_NOW
echo

cd /usr/java/GTM_SERVICES/scm
ant run
