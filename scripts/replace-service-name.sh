#!/bin/bash

sed -i 's/host: redis/host: 127.0.0.1/g; s/mysql:3306/localhost:3306/g' src/main/resources/application.yml