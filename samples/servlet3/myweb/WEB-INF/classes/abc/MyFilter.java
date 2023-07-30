package abc;                                                                                         

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebFilter;

// 配置filter的url
@WebFilter("/filter/*")
public  class MyFilter implements Filter {
      public void destroy() {
          // TODO Auto-generated method stub
      }
      public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println( "<html><h1> Hello from Servlet Filter. </h1></html>" ); 
                out.flush();
                out.close();
        }

      public void init(FilterConfig arg0) throws ServletException {
          // TODO Auto-generated method stub
      }
  }
