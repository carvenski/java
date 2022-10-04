## gradle
*java有gradle在手...太TM好用了...*      
*推荐使用最新的gradle7 - 性能有很大提升*

#### init an java application/library/webapp project using gradle
```
mkdir projectname && cd projectname
gradle init (choose a type)
```

#### write your code inside src/main/java/

#### edit build.gradle by your project needs

#### build jar file
```
gradle/gradlew build
```

#### run your jar file
```
gradle run (gradle version >= 6.6)
或
java -jar xx.jar -cp 包路径
```

## gradlew
```
gradlew就是gradle wrapper,好处就是不需要安装gradle,项目中自带gradlew的jar包,可直接运行使用,很方便.
(gradlew会在后台自动下载好gradle到~/.gradle目录里面) 
gradlew tasks: 显示可执行的task,每个项目都会有自定义的task,例如sprintboot的bootRun.
```

## gradle设置全局代理
```
# gradle启动时就是个java程序在运行,并没有识别http_proxy环境变量,
# 需要专门设置下~/.gradle/gradle.properties文件
systemProp.http.proxyHost=10.191.113.100
systemProp.http.proxyPort=8002
systemProp.http.nonProxyHosts=192.168.*|localhost
systemProp.http.proxyUser=xxx
systemProp.http.proxyPassword=xxx

systemProp.https.proxyHost=10.191.113.100
systemProp.https.proxyPort=8002
systemProp.https.nonProxyHosts=192.168.*|localhost
systemProp.https.proxyUser=xxx
systemProp.https.proxyPassword=xxx
```

