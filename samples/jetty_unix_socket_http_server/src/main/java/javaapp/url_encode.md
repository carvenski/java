## url encode/decode
```
url encode使用场景：
py/go和java本机进程通信，采用unix socket方式，
使用py/go写的unix socket http client发送http请求给java jetty的unix socket http server,
url中的passwd会有#&等特殊字符，需要url encode/decode一下。
(url encode会把url中的特殊字符替换成%xx形式的再去传输，然后server端再decode解码回来)

需要url encode的原因所在：
socket本身传输的是字节，它不管你传输的是什么，只要是byte即可。
但是http不是面向字节的，http协议需要基于很多特殊字符去解析的 ！
比如\r\n就是用来表示header分隔的，比如get请求参数里的#会被丢弃，&表示参数分隔，等等，
而%在http协议里没什么特殊意义，所以特殊字符就编码成它呗。。
```

```python
from urllib.parse import quote 
# url encode
passwd = "cd#@!$%^&*()_+zz"
print(quote(passwd))
```

```go
package main

import (
	"fmt"
	"net/url"
)

func main() {
    // url encode
	var s string = url.QueryEscape("cd#@!$%^&*()_+zz")
	fmt.Println(s)
}
```

```java
import java.net.URLDecoder;

public class main {
    public static void main( String[] args ) throws Exception{
            String passwd = "cd%23%40!%24%25%5e%26*()_%2bzz";
            // url decode, (jetty会自动解码的,所以不需要再url decode了)
            passwd = URLDecoder.decode(passwd, "utf-8");
            System.out.println(passwd);
        }
    }
```
