package abc;

import java.util.Enumeration;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

// 配置servlet的url
@WebServlet("/Z/*")
public class Z extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {

         // 转发请求
         // req.getRequestDispatcher("/servlet").forward(req, res);
         res.sendRedirect("/myweb/servlet");

    }

}
