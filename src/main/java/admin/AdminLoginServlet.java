package admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if("admin".equals(username) && "admin".equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("admin", "admin");

            response.sendRedirect("Admin-Dashboard");

        } else {
            response.sendRedirect("adminlogin.html?error=1");
        }
    }
}
