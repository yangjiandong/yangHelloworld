lift
====

2010.05.09
-----------

1. use intellij-ic9.0 in ubuntu10.4

2. git为显示加上颜色 
$ git config color.(branch|diff|interactive|status) auto，
或者$ git config color.ui true

2010.05.08
-----------

1、试用idea9，感觉iu要全面点，采用scala plugin
   参考 http://blog.csdn.net/xmq_666/archive/2010/02/23/5320552.aspx，注意是导入pom.xml
   save/idea.txt

   感觉比eclipse + scala 速度快

2、自定义快捷
   alt + . -- base code complete,取代 ctl + space
   
2010.05.07
-----------

1、生成标准lift-blank 程序框架
mvn archetype:generate -U -DarchetypeGroupId=net.liftweb -DarchetypeArtifactId=lift-archetype-blank -DarchetypeVersion=2.0-SNAPSHOT -DarchetypeRepositories=http://scala-tools.org/repo-snapshots -DremoteRepositories=http://scala-tools.org/repo-snapshots  -DgroupId=com.yang.lift.helloworld -DartifactId=yangHelloworld

mvn jetty:run
mvn eclipse:eclipse

2、结合lift-book
  
   --END
