package driver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/driverprofile")
public class DriverProfileServlet extends HttpServlet {

    // ================= LOGIN (POST) =================
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            String sql = "SELECT user_id FROM users WHERE email=? AND password=? AND role='driver'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", rs.getInt("user_id"));

                // Redirect → GET
                response.sendRedirect("driverprofile");
            } else {
                response.setContentType("text/html");
                response.getWriter().println("<h3 style='color:red'>Invalid Login</h3>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= SHOW PROFILE (GET) =================
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("DriverLogin.html");
            return;
        }

        int userId = (int) session.getAttribute("user_id");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            String sql =
                "SELECT u.user_id, u.full_name, u.email, " +
                "d.driver_id, d.vehicle_number, d.vehicle_type, d.availability " +
                "FROM users u JOIN drivers d ON u.user_id = d.user_id " +
                "WHERE u.user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>Driver Profile</title>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");

            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

            out.println("<style>");
            out.println("body{background:#f4f6f9;font-family:'Segoe UI',sans-serif;}");
            out.println(".profile-card{max-width:650px;margin:auto;background:#fff;border-radius:15px;");
            out.println("box-shadow:0 15px 35px rgba(0,0,0,.15);overflow:hidden}");
            out.println(".profile-header{background:linear-gradient(135deg,#2563eb,#1e40af);");
            out.println("color:#fff;padding:30px;text-align:center}");
            out.println(".profile-header i{font-size:60px}");
            out.println(".profile-body{padding:30px}");
            out.println(".profile-body th{width:40%;color:#555}");
            out.println("</style>");

            out.println("</head><body>");

            if (rs.next()) {

                String status = rs.getString("availability");
                String badge = status.equalsIgnoreCase("available") ? "success" : "danger";

                out.println("<div class='profile-card mt-5'>");

                out.println("<div class='profile-header'>");
                out.println("<i class='bi bi-person-circle'></i>");
                out.println("<h2 class='mt-2'>Driver Profile</h2>");
                out.println("</div>");

                out.println("<div class='profile-body'>");
                out.println("<table class='table table-borderless'>");

                out.println("<tr><th><i class='bi bi-person-badge'></i> User ID</th><td>" + rs.getInt("user_id") + "</td></tr>");
                out.println("<tr><th><i class='bi bi-card-list'></i> Driver ID</th><td>" + rs.getInt("driver_id") + "</td></tr>");
                out.println("<tr><th><i class='bi bi-person'></i> Name</th><td>" + rs.getString("full_name") + "</td></tr>");
                out.println("<tr><th><i class='bi bi-envelope'></i> Email</th><td>" + rs.getString("email") + "</td></tr>");
                out.println("<tr><th><i class='bi bi-truck'></i> Vehicle No</th><td>" + rs.getString("vehicle_number") + "</td></tr>");
                out.println("<tr><th><i class='bi bi-car-front'></i> Vehicle Type</th><td>" + rs.getString("vehicle_type") + "</td></tr>");

                out.println("<tr><th><i class='bi bi-toggle-on'></i> Availability</th>");
                out.println("<td><span class='badge bg-" + badge + " fs-6'>" + status.toUpperCase() + "</span></td></tr>");

                out.println("</table>");

                out.println("<div class='text-center mt-4'>");
                out.println("<a href='DriverLogoutServlet' class='btn btn-danger px-4'>Logout</a>");
                out.println("</div>");

                out.println("</div></div>");
            }

            out.println("</body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
