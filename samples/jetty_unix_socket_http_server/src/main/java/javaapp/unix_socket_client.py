# python unix socket client, connect to local jetty.sock, with http protocol.
import requests_unixsocket
from urllib.parse import quote # url encode

passwd = "23we@#WE"
path = "/passwd?v=%s" % quote(passwd)
url = "/tmp/jetty.sock"
url = "http+unix://" + url.replace('/', '%2F') + path

session = requests_unixsocket.Session()
r = session.get(url)

print(r.status_code)
print( r.content )
passwd = str( r.content[:-1] )
print(passwd)

# p13 api
import requests
user = "shoufena"
url = "http://10.182.1.59/ssoApp/p13info/newValidate.htm?user=%s&password=%s&appid=BI_WorkStation" % \
        ("shoufena", passwd)

r = requests.get(url)
print(r.status_code)
print(r.content)

# 测试环境的p13帐号 shoufena/23we@#WE
# 测试请求 http://10.182.1.59/ssoApp/p13info/newUserValidate.htm?user=shoufena&password=LH7d+WETAVusCCwrhXXxa5e4HvcVbDe8MUTPOQc65CGL1v8K0flNWLvlnpeeJZ+ArRyzfRlvkihpHvUKLCQQ0V/X3BUCCnhg3MIFtbdvpQg=&appid=BI_WorkStation

# 接入生产环境后才能验证其他员工的p13帐号


