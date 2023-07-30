package javaapp;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class App{
    public static void main( String[] args ) throws Exception{
        Server server = new Server(18000);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // This is a raw Servlet, not a Servlet that has been configured
        // through a web.xml @WebServlet annotation, or anything similar.
        handler.addServletWithMapping(HelloServlet.class, "/hello");
        handler.addServletWithMapping(TimeServlet.class, "/time");

        server.start();

        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        server.join();
    }


    @SuppressWarnings("serial")
    public static class HelloServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
                                                                ServletException, IOException{
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello from jetty HelloServlet...</h1>");
        }
    }

    @SuppressWarnings("serial")
    public static class TimeServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException{
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.getWriter().println("<h1>time is: " + df.format(new Date()) + "</h1>");
        }
    }


}


