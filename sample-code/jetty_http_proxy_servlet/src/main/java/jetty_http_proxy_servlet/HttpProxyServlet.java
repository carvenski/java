package jetty_http_proxy_servlet;

import org.eclipse.jetty.proxy.ProxyServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.*;

// 使用注解写法则不需要配置web.xml
@WebServlet(name = "HttpProxyServlet",
 urlPatterns = {
        "/decision/biauth/*",
        "/decision/13rate/*",
        "/decision/api/sms/*",
        "/bimp/*",
        "/proxy/*",
    },
 loadOnStartup = 1,
 asyncSupported = true,
 initParams = {@WebInitParam(name="maxThreads", value="10")}
)
public class HttpProxyServlet extends ProxyServlet {
    
    // proxy to backend server
    private String target = "http://localhost:47798";
    protected String rewriteTarget(HttpServletRequest request){        
        String param = request.getQueryString() != null ? "?"+request.getQueryString() : "";
        String origin_url = request.getRequestURL()+param;
        String target_url = this.target + request.getRequestURI()+param;
        System.out.println("== proxy origin URL: " + origin_url);
        System.out.println("== proxy target URL: " + target_url);
        return target_url;
    }

}
/*
必须要设置好maxThreads参数:
这是ProxyServlet内部的HttpClient需要使用的executor线程池的最大数量.

// jetty setting:
handler.addServletWithMapping(HttpProxyServlet.class, "/proxy/*").setInitParameter("maxThreads", "5");

// 配置tomcat web.xml
<servlet>
    <servlet-name>HttpProxyServlet</servlet-name>
    <servlet-class>jetty_http_proxy_servlet.HttpProxyServlet</servlet-class>
    <init-param>
        <param-name>maxThreads</param-name>
        <param-value>5</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
    <async-supported>true</async-supported>
</servlet>
<servlet-mapping>
    <servlet-name>HttpProxyServlet</servlet-name>
    <url-pattern>/proxy/*</url-pattern>
</servlet-mapping>
*/
