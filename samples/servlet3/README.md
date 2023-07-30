## Servlet开发步骤
Servlet之于java,其实就类似python的WSGI,      
其实就是http server与你的web应用之间约定好的接口规范。      

- 写好Servlet代码(继承Servlet类覆盖doGet/doPost等方法,自定义处理逻辑,使用@WebServlet映射请求url)
- 编译好Servlet的.class文件放到WEB-INF/classes/目录下(或者打好jar包放到lib/目录下)
- 拷贝项目目录到webapps/目录下
- 启动jetty/tomcat即可

## 手动编译命令
javac -Djava.ext.dirs="/home/michael/github/java/sample-code/servlet3/" Z.java


可以直接使用Servlet开发web应用,也可以使用一些框架,     
比如SpringMVC框架,其实它就是Servlet的一层包装,     
web框架原理都类似,替你写好了很多轮子代码而已.     

[Servlet介绍参考](https://blog.csdn.net/qq_19782019/article/details/80292110)

## jetty踩坑
jetty9.4是不支持识别@WebServlet装饰器的,所以url没有正确映射。
所以jetty9.4必须使用web.xml方式去配置url ?? 草
tomcat8.5没有问题。

## jetty部署servlet项目
- 拷贝项目目录到webapps/
- 配置jetty安装目录下的start.ini
- 启动jetty,查看url

