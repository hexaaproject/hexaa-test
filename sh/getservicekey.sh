#!/bin/bash
# Returns the enable_token that would otherwise acquired through e-mail by the user

# Mysql user
USER="root"
# Mysql password
PASS="pass"

# Mysql invoke and query
mysql --user=$USER --password=$PASS hexaa -e "SELECT enable_token FROM service;"

