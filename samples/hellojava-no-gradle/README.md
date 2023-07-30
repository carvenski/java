## hellojava是个简单版的JAVA项目结构demo(未使用gradle标准项目结构)

#### 目录结构
```
hellojava/
          Main.java
          p1/
             Test1.java
          p2/
             Test2.java
```

## 编译/运行
```
cd hellojava/../    # 敲命令时必须和hellojava在同一级目录才行,这样helljava才在当前目录,才能作为包根目录被识别.
javac hellojava/Main.java && java hellojava/Main

/*
因为CLASSPATH的路径里面是包含[当前路径]的,
所以编译时想要让解释器能够找到当前项目路径(就是代码里声明的根包hellojava),
就必须在[和hellojava根包同一级的文件夹/也就是它的外层文件夹]里面敲javac/java来编译/运行.
否则就会报找不到项目路径找不到class的错误!!

(所以也可以有另一种做法: 把项目的根包的外层文件夹的路径加入CLASSPATH即可认识了.)
*/
```

## 依赖的jar包
```
项目依赖的jar包就放在jre的lib/ext/目录里就行了.
```

## 总结
```
本项目的这种项目结构和做法,其实就把java变得和python一样的用法了...(搞得像解释性语言一样随处带着代码去编译运行了)
```




