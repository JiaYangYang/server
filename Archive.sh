# /bin/sh

rm -rf target
rm -rf */target
rm -rf */*/target
rm *.*~
rm -rf *.iml
rm -rf */*.iml

tar -czf server.tar *
