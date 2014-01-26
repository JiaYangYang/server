#!/bin/sh

#PID=`$JAVA_HOME/bin/jps | grep jboss-modules | cut -d ' ' -f 1`
#if test x$PID != 'x'
#then
#	kill -9 $PID
#fi


JBOSS_HOME=/Users/frank/Servers/latest
PROJECT_NAME=server
PROJECT_HOME=/Users/frank/Projects/$PROJECT_NAME
#HTML_HOME=/var/www/html

#sudo rm -rf $HTML_HOME/$PROJECT_NAME
#sudo cp -r $PROJECT_HOME/web/src $HTML_HOME/$PROJECT_NAME

JAVA_OPTS="-server -XX:+UseCompressedOops -Xms2048m -Xmx2048m -XX:PermSize=256m -XX:MaxPermSize=1024m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true"
export JAVA_OPTS

cd $PROJECT_HOME
mvn clean install -Dmaven.test.skip=true
ln -s $PROJECT_HOME/resources/target/$PROJECT_NAME.ear $JBOSS_HOME/standalone/deployments/$PROJECT_NAME.ear
rm $JBOSS_HOME/standalone/deployments/*.failed

$JBOSS_HOME/bin/standalone.sh
