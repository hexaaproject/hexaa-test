#!/bin/bash
# Deletes the cache and logs directories and recreates them with the necessary access for the user and the server app

# The full path of the hexaa cache directory
CACHE="/usr/share/hexaa/app/cache"
# The full path of the hexaa log directory
LOG="/var/log/hexaa"

# Deleting the content of the directories
rm -rf $CACHE/*
rm -rf $LOG/*

HTTPDUSER=`ps aux | grep -E '[a]pache|[h]ttpd|[_]www|[w]ww-data|[n]ginx' | grep -v root | head -1 | cut -d\  -f1`

setfacl -R -m u:"$HTTPDUSER":rwX -m u:`whoami`:rwX -m u:jenkins:rwX $CACHE $LOG
setfacl -dR -m u:"$HTTPDUSER":rwX -m u:`whoami`:rwX -m u:jenkins:rwX $CACHE $LOG

