"""
tcp socket和unix socket的区别:
A UNIX socket is an inter-process communication mechanism that allows bidirectional data exchange between processes running on the same machine.
IP sockets (especially TCP/IP sockets) are a mechanism allowing communication between processes over the network. In some cases, you can use TCP/IP sockets to talk with processes running on the same computer (by using the loopback interface).
UNIX domain sockets know that they’re executing on the same system, so they can avoid some checks and operations (like routing); which makes them faster and lighter than IP sockets. So if you plan to communicate with processes on the same host, this is a better option than IP sockets.
Edit: As per Nils Toedtmann's comment: UNIX domain sockets are subject to file system permissions, while TCP sockets can be controlled only on the packet filter level.

UNIX Socket是同一台服务器上不同进程间的通信机制。TCP/IP Socket是网络上不同服务器之间进程的通信机制，也可以让同一服务器的不同进程通信。
Postgres的一位核心开发者曾经做过实验，证明UNIX Socket的方式比TCP/IP Socket方式要快31%，所以，在同一个服务器上应该优先选择UNIX Socket方式。
"""

# 1.unix socket http client, connect to local jetty.sock
# Time: 10ms
import requests_unixsocket
import time
session = requests_unixsocket.Session()
url = "http+unix://%2Ftmp%2Fjetty.sock/test"
t1 = time.time()
r = session.get(url)
print(time.time() - t1)
print(r.status_code)
print(r.content)

# TODO: 
# 进程通信，使用unix socket方式和ip:port方式一次请求都是10ms左右，性能并没什么区别 ??
# 理论上应该是unix socket方式会更快(不走TCP网络协议栈,绕过了内核态拷贝等过程),
# 但是测试的结果都是10ms左右,难道是我测试不标准 ?? 
# 不过至少说明两种方式都能采用,耗时不过10ms而已嘛.
# 以后这就是java对接python的方式了! 

# 2. tcp socket http client, connect to http://localhost:port
# Time: 10ms
import requests 
import time
url = "http://localhost:30000/test"
t1 = time.time()
r = requests.get(url)
print(time.time() - t1)
print(r.status_code)
print(r.content)





