TODAY=$(date +"%Y%m%d")

cp /usr/java/GTM_SERVICES/scm/pwap/error_log.txt /usr/java/SCM_Log_Backup/logs
cp /usr/java/GTM_SERVICES/scm/pwap/output_log.txt /usr/java/SCM_Log_Backup/logs
cp /usr/java/GTM_SERVICES/scm/pwap/pc/prdcat.properties /usr/java/SCM_Log_Backup/logs
cp /usr/java/GTM_SERVICES/scm/pwap/sent_folder/*.xml /usr/java/SCM_Log_Backup/logs
cd /usr/java/SCM_Log_Backup
tar -zcvf SCM_LogsBackup_$TODAY.tar.gz logs/