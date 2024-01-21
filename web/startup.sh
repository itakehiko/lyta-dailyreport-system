#!/bin/bash

# Spring Bootアプリケーションの起動
java -jar /var/www/java/DailyReportSystemApplication.jar &

# Apache2の起動
rm -f /var/run/apache2/apache2.pid
apachectl -D FOREGROUND
