package javaapp;

import java.net.URLDecoder;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import org.eclipse.jetty.unixsocket.UnixSocketConnector;


public class App {
    public static void main( String[] args ) throws Exception{
        // 监听 ip:port
        // int port = 30000;
        // Server server = new Server(port);

        // 监听 unix socket
        Server server = new Server();
        UnixSocketConnector connector = new UnixSocketConnector(server);
        connector.setUnixSocket("/tmp/jetty.sock");
        server.addConnector(connector);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // This is a raw Servlet, not a Servlet that has been configured
        // through a web.xml @WebServlet annotation, or anything similar.
        handler.addServletWithMapping(TestServlet.class, "/test");
        handler.addServletWithMapping(PasswdServlet.class, "/passwd");

        server.start();
        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        server.join();
    }


    @SuppressWarnings("serial")
    public static class TestServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Test from jetty HelloServlet...</h1>");
        }
    }

    @SuppressWarnings("serial")
    public static class PasswdServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            String passwd = request.getParameter("v");
            System.out.println("passwd=" + passwd);
            // jetty do url decode already ! dont decode again.
            // passwd = URLDecoder.decode(passwd, "utf-8");
            String en_passwd = PasswdUtil.getEncryptPwd(passwd);            
            System.out.println("en_passwd=" + en_passwd);
            response.getWriter().println(en_passwd);
        }
    }
}
