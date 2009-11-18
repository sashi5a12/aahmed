- Make the following entry in CRON:

0 1 * * * su - psmsvzw /usr/java/GTM_SERVICES/scm/senddcr.sh

- NDM must be installed on this machine (with VPN)

- IMPORTANT: If installing on another machine after checking out from CVS, be sure to update the following in "prdcat.properties" file:
    FILEID=x, where x is the number of last file sent + 1 (e.g. if last file sent is 000000040_800000_STOCKING.xml, then FILEID=41 (i.e. 40+1)

- "pwap/pc/aims_stocking.sh" contains information for 
    -- 'NDM Node' e.g. fresno
    -- 'Location for placing the file (on client's end)' e.g. /scm1/SCM_TSFR/ndm_in/stocking/800000/
    -- 'Task to execute on client's end, after the transfer' e.g. /scm/CCE/bin/scm_stocking_800000_complete.sh 000000020_800000_STOCKING.xml


- To transfer file via NDM (same as to what 'senddcr.sh' (run via CRON) is doing)

    * su psmsvzw
    * ant run (standing in vzwaims/scm folder)

- To verify for successful transfer:

    * su psmsvzw
    * cd /home/psmsvzw/cdunix/ndm/bin
    * ./direct
    * select stat;
    * quit <-- VERY VERY IMPORTANT. Not exit but QUIT
        

- START/STOP NDM Server:

    * To start the NDM Server:
         1. In /home/psmsvzw/cdunix/ndm/bin directory, run the following:
            ./cdpmgr -i /home/psmsvzw/cdunix/ndm/cfg/netpace/initparm.cfg

    * To stop the NDM Server:
         1. In /home/psmsvzw/cdunix/ndm/bin dir run ./direct to start the NDM client
         2. and run Direct> stop; [stop force - to forcefully shutdown ndm]



- Process to change NDM Node IP:

    * su psmsvzw
    * Take Backup /home/psmsvzw/cdunix/ndm/cfg/netpace/netmap.cfg
    * vi /home/psmsvzw/cdunix/ndm/cfg/netpace/netmap.cfg
    * change IP (e.g of node Wendell)
    * Stop NDM:
        - In /home/psmsvzw/cdunix/ndm/bin dir run ./direct to start the NDM client
        - run Direct> stop; [stop force - to forcefully shutdown ]
    * Start NDM:
        - In /home/psmsvzw/cdunix/ndm/bin directory, run the following:
          ./cdpmgr -i /home/psmsvzw/cdunix/ndm/cfg/netpace/initparm.cfg

    * vi /home/psmsvzw/data/wendell/upload/test_send.sh (Test Script to send test file to Node (e.g. Wendell)) 
    

