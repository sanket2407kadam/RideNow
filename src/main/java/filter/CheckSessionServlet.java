package filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CheckSessionServlet")
public class CheckSessionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        response.setContentType("text/plain");

        if (session != null && session.getAttribute("username") != null) {
            response.getWriter().print(session.getAttribute("username"));
        } else {
            response.getWriter().print("guest");
        }
    }
}
