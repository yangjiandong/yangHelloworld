lift
====

2010.05.08
-----------

1、试用idea9，感觉iu要全面点，采用scala plugin
   参考 http://blog.csdn.net/xmq_666/archive/2010/02/23/5320552.aspx，注意是导入pom.xml
   save/idea.txt

   感觉比eclipse + scala 速度快
   
2010.05.07
-----------

1、生成标准lift-blank 程序框架
mvn archetype:generate -U -DarchetypeGroupId=net.liftweb -DarchetypeArtifactId=lift-archetype-blank -DarchetypeVersion=2.0-SNAPSHOT -DarchetypeRepositories=http://scala-tools.org/repo-snapshots -DremoteRepositories=http://scala-tools.org/repo-snapshots  -DgroupId=com.yang.lift.helloworld -DartifactId=yangHelloworld

mvn jetty:run
mvn eclipse:eclipse

2、结合lift-book
  
   --END
