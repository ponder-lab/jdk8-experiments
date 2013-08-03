#!/bin/bash
rm -Rfv /opt/jdk-builds/lambda-trunk/docs
/opt/jdk-builds/lambda-trunk/bin/javadoc \
-d /opt/jdk-builds/lambda-trunk/docs \
-use \
-author \
-version \
-splitIndex \
-windowtitle 'Java 2 Platform 8.0 API Specification' \
-doctitle 'Java<sup><font size="-2">TM</font></sup> 2 Platform 8.0 API Specification' \
-header '<b>Java 2 Platform </b><br><font size="-1">8.0</font>' \
-group "Core Packages" "java.*" \
-group "Functional Packages" "java.util.function*:java.util.stream*" \
-group "Extension Packages" "javax.*" \
-group "Third-Parties" "org.*" \
-overview /home/edalorzo/JDK8/lambda/jdk/src/share/classes/overview-bundled.html \
-sourcepath /home/edalorzo/JDK8/lambda/jdk/src/share/classes \
-subpackages java:javax:org
