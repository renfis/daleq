#!/bin/sh

./gradlew --stacktrace --continue --quiet --no-color \
-Pintegration.db=HSQLDB,H2,Mysql \
-Pintegration.mysql.url=jdbc:mysql://127.0.0.1/daleq_test \
-Pintegration.mysql.user=root \
-Pintegration.mysql.password= \
check

