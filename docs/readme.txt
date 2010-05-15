lift
====

2010.05.14
----------

1、增加git仓库 http://github.com/yangjiandong/yangHelloworld 
   git remote add github git@github.com:yangjiandong/yangHelloworld.git
   提交时，需将ssh-key 加到 github
   github user:yangjiandong,123456789,young.jiandong@gmail.com

2010.05.09
-----------

1. use intellij-ic9.0 in ubuntu10.4

2、git为显示加上颜色
$ git config color.(branch|diff|interactive|status) auto，
或者$ git config color.ui true

3、firefox plugin
https://addons.mozilla.org/zh-CN/firefox/addon/398

4、idea vm
-Xms256m
-Xmx512m
-XX:MaxPermSize=256m
-ea
-server
-XX:-UseParallelGC

5、idea 开发lift，采用jetty:run debug 方式，修改代码后，需手工编译

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
