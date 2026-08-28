package rider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "MyRidesServlet", urlPatterns = {"/myrides"})
public class RideServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ridenow";
    private final String dbUser = "root";       // Your DB username
    private final String dbPassword = "admin";  // Your DB password

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Simulate logged-in user
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        if(userId == null){
            response.sendRedirect("login.html");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            String sql = "SELECT pickup_location, drop_location, ride_status, ride_time " +
                         "FROM rides WHERE rider_id=? ORDER BY ride_time DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            // Start HTML output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>My Rides</title>");
            out.println("<style>");
            out.println("body{font-family:Segoe UI;background:#f4f6f8;}");
            out.println(".box{width:700px;margin:60px auto;background:#fff;padding:30px;border-radius:10px;box-shadow:0 4px 10px rgba(0,0,0,.1);}");
            out.println("table{width:100%;border-collapse:collapse;}");
            out.println("th,td{padding:10px;border-bottom:1px solid #ddd;text-align:center;}");
            out.println("th{background:#2563eb;color:#fff;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='box'>");
            out.println("<h2>My Rides</h2>");
            out.println("<table>");
            out.println("<tr><th>Pickup</th><th>Drop</th><th>Status</th><th>Date</th></tr>");

            boolean hasData = false;
            while(rs.next()){
                hasData = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("pickup_location") + "</td>");
                out.println("<td>" + rs.getString("drop_location") + "</td>");
                out.println("<td>" + rs.getString("ride_status") + "</td>");
                out.println("<td>" + rs.getTimestamp("ride_time") + "</td>");
                out.println("</tr>");
            }

            if(!hasData){
                out.println("<tr><td colspan='4'>No rides found</td></tr>");
            }

            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            conn.close();
        } catch(Exception e){
            e.printStackTrace(out);
            out.println("<tr><td colspan='4'>Error fetching rides</td></tr>");
        }
    }
}
