lift
====

1、生成标准lift-blank 程序框架
mvn archetype:generate -U -DarchetypeGroupId=net.liftweb -DarchetypeArtifactId=lift-archetype-blank -DarchetypeVersion=2.0-SNAPSHOT -DarchetypeRepositories=http://scala-tools.org/repo-snapshots -DremoteRepositories=http://scala-tools.org/repo-snapshots  -DgroupId=com.yang.lift.helloworld -DartifactId=yangHelloworld

mvn jetty:run
mvn eclipse:eclipse

2、结合lift-book

   --END
