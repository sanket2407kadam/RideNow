package driver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewPaymentsServlet")
public class ViewPaymentsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        int driverId = (int) session.getAttribute("driver_id");

        response.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            PreparedStatement ps = con.prepareStatement(
                "SELECT r.ride_id, rd.fare, p.payment_method, p.payment_status, p.payment_time " +
                "FROM rides r " +
                "JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "JOIN payments p ON r.ride_id = p.ride_id " +
                "WHERE r.driver_id = ?");

            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            StringBuilder html = new StringBuilder();
            html.append("<table class='table'>");
            html.append("<tr><th>Ride ID</th><th>Fare</th><th>Method</th><th>Status</th><th>Time</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                html.append("<tr>")
                    .append("<td>").append(rs.getInt(1)).append("</td>")
                    .append("<td>₹").append(rs.getDouble(2)).append("</td>")
                    .append("<td>").append(rs.getString(3)).append("</td>")
                    .append("<td data-status='").append(rs.getString(4)).append("'>")
                    .append(rs.getString(4)).append("</td>")
                    .append("<td>").append(rs.getTimestamp(5)).append("</td>")
                    .append("</tr>");
            }

            if (!found) {
                html.append("<tr><td colspan='5'>No payments found</td></tr>");
            }

            html.append("</table>");
            response.getWriter().print(html);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Error loading payments");
        }
    }
}
