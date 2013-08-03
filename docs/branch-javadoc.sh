#!/bin/bash
rm -Rfv /opt/jdk-builds/lambda-it2/docs
/opt/jdk-builds/lambda-it2/bin/javadoc \
-d /opt/jdk-builds/lambda-it2/docs \
-use \
-author \
-version \
-linksource \
-splitIndex \
-windowtitle 'Java 2 Platform 8.0 API Specification' \
-doctitle 'Java<sup><font size="-2">TM</font></sup> 2 Platform 8.0 API Specification' \
-header '<b>Java 2 Platform </b><br><font size="-1">8.0</font>' \
-group "Core Packages" "java.\*" \
-overview /home/edalorzo/JDK8/lambda-it2/jdk/src/share/classes/overview-bundled.html \
-sourcepath /home/edalorzo/JDK8/lambda-it2/jdk/src/share/classes \
-subpackages java
