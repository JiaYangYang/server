#!/bin/sh

#PID=`$JAVA_HOME/bin/jps | grep jboss-modules | cut -d ' ' -f 1`
#if test x$PID != 'x'
#then
#	kill -9 $PID
#fi


JBOSS_HOME=/home/frank/Servers/wildfly-8.0.0.Final
PROJECT_NAME=server
PROJECT_HOME=/home/frank/Projects/$PROJECT_NAME
CLIENT_HOME=/home/frank/Projects/client
HTML_HOME=/var/www/html

sudo rm -rf $HTML_HOME/$CLIENT_HOME
sudo cp -r $CLIENT_HOME $HTML_HOME/

JAVA_OPTS="-server -XX:+UseCompressedOops -Xms2048m -Xmx2048m -XX:PermSize=256m -XX:MaxPermSize=1024m -Djava.net.preferIPv4Stack=true"
export JAVA_OPTS

cd $PROJECT_HOME
mvn clean install -Dmaven.test.skip=true
rm $JBOSS_HOME/standalone/deployments/$PROJECT_NAME.ear.*
ln -s $PROJECT_HOME/resources/target/$PROJECT_NAME.ear $JBOSS_HOME/standalone/deployments/$PROJECT_NAME.ear

$JBOSS_HOME/bin/standalone.sh
