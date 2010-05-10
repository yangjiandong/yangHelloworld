set REBEL_JAR=jrebel.jar
set MAVEN_OPTS=-Dfile.encoding=utf8 -Xms256m -Xmx512m -XX:MaxPermSize=256m -Djetty.scanIntervalSeconds=0 -noverify -javaagent:%REBEL_JAR%
mvn -o jetty:run