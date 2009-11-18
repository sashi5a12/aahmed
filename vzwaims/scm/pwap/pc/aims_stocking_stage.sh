#!/bin/sh
#
# This script invokes the CONNECT:Direct for UNIX CLI
# and submits a process inline to copy a file to a remote
# node.
#  $1 is the source directory.
#  $2 is the source file.
#
#       step2 run task snode sysopts="echo 'Run Task Snode data' > rtsnode.txt"
#
#        step2 run task snode sysopts="/edr/IAF/bin/ems_netpace_prodcat_complete.sh $1"
#
#
#		     to    (file=/edr1/EDR_TSFR/ndm_in/EMS/NETPACE_PC/$2)
#
set -v
/home/ndm/cdunix/ndm/bin/ndmcli  -x << EOJ
submit proc1    process    snode=punchline
       step1    copy from  (file=$1/$2 pnode)
		     to    (file=/tmp/$2)
       pend ;
EOJ
