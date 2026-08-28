package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter(urlPatterns = {
    "/rider.html",
    "/DriverLogin.html",
    "/about.html",
    "/contact.html",
    "/bookRide",
    "/driver/*",
    "/user/*"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            res.sendRedirect("login.html");
            return;
        }

        chain.doFilter(request, response);
    }
}
