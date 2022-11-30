## mvn/gradle
```
mvn比gradle好用, 而且mvn资源占用低很多。缺点就是pom.xml配置很落后。
以后项目还是优先使用mvn。
之前的gradle问题是由于gradle7新版本不兼容gradle6配置文件导致的，坑啊。
以后统一使用gradle6.8版本就行了。
```

## gradle usage
```
# init gradle project
mkdir project && cd project
gradle init (choose a type)

# build jar
gradle/gradlew build

# run jar
gradle run (gradle version >= 6.6)
或
java -jar xx.jar -cp 包路径

# gradlew
gradlew就是gradle wrapper
就是不需要安装gradle,项目中会多带一个gradle文件夹/gradlew的jar包,可以直接运行
gradlew会在后台自动下载好gradle到~/.gradle目录里面 
(完全没必要使用gradlew,就安装下gradle即可。
gradle文件夹和gradlew文件也可以全部删掉。)

# gradle设置代理
# gradle启动时是个后台java程序在运行,它不会识别http_proxy环境变量, 坑啊。。。
# 必须专门设置~/.gradle/gradle.properties文件

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

