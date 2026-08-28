package login;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	response.setContentType("text/html");;

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String url = "jdbc:mysql://localhost:3306/ridenow";
        String dbUser = "root";
        String dbPassword = "admin";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPassword);

            String sql = "SELECT user_id, full_name, role FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String role = rs.getString("role");

                // store only user_id in session
                HttpSession session = request.getSession();
                session.setAttribute("user_id", userId);
                session.setAttribute("role", role);

                if ("driver".equalsIgnoreCase(role)) {
                    // check if already registered as driver
                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM drivers WHERE user_id=?");
                    ps2.setInt(1, userId);
                    ResultSet rs2 = ps2.executeQuery();

                    if (rs2.next()) {
                        // redirect to dashboard with full name in URL
                        response.sendRedirect("index.html?user=" + java.net.URLEncoder.encode(fullName, "UTF-8"));
                    } else {
                        // redirect to driver registration page
                        response.sendRedirect("DriverRegister.html");
                    }
                } else {
                    // rider or other roles
                    response.sendRedirect("index.html?user=" + java.net.URLEncoder.encode(fullName, "UTF-8"));
                }

            } else {
                response.getWriter().println("<h2>Invalid email or password</h2>");
                response.getWriter().println("<a href='login.html'>Back to Login</a>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}