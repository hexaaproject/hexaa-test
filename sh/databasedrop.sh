#!/bin/bash
# Deletes and recreates the whole database. All data will be lost.

# The location of the hexaa
HEXAA="/usr/share/hexaa"

# Deletes the whole database,
php $HEXAA/app/console doctrine:database:drop --force
# recreates,
php $HEXAA/app/console doctrine:database:create
# forces a full update.
php $HEXAA/app/console doctrine:schema:update --force

