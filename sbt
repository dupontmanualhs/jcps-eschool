java -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256m -Xmx1024M -Xss2M -jar `dirname $0`/sbt-launcher-0.11.0.jar "$@"
