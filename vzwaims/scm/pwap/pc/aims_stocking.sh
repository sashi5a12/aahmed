scp -P 32970 -i /usr/java/GTM_SERVICES/scm/keys/Connect_To_NDM_Server_For_ZON.key $1/$2 psmsvzw@172.20.40.99:/usr/java/Projects/vzwaims/scm/ndm_scripts/$2
ssh -p 32970 -i /usr/java/GTM_SERVICES/scm/keys/Connect_To_NDM_Server_For_ZON.key -l psmsvzw 172.20.40.99 /usr/java/Projects/vzwaims/scm/ndm_scripts/aims_stocking.sh /usr/java/Projects/vzwaims/scm/ndm_scripts $2
