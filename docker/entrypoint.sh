#!/usr/bin/env bash

# enable job control (-m) & execution print (-x)
set -mx

# start mysqld
docker-entrypoint.sh mysqld &

# wait for mysqld startup
sleep 20

# retry until mysqld exits
while jobs %% &> /dev/null; do
	java -jar app.jar
	sleep 5
done
