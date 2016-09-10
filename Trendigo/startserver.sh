#!/usr/bin/env bash
export MAVEN_OPTS="-Xmx8192m -XX:+UseConcMarkSweepGC";
rm nohup.out 2> /dev/null;
nohup mvn clean install tomcat7:run &
echo $! > nohupPid.txt;
tail -f nohup.out;#!/usr/bin/env bash