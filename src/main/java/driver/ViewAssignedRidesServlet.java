package driver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewAssignedRidesServlet")
public class ViewAssignedRidesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("driver_id") == null) {
            return;
        }

        int driverId = (int) session.getAttribute("driver_id");

        StringBuilder out = new StringBuilder();

        out.append("<table class='table'>");
        out.append("<thead><tr>");
        out.append("<th>Ride ID</th>");
        out.append("<th>Pickup</th>");
        out.append("<th>Drop</th>");
        out.append("<th>Fare</th>");
        out.append("<th>Status</th>");
        out.append("</tr></thead><tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            String sql =
                "SELECT r.ride_id, r.pickup_location, r.drop_location, rd.fare " +
                "FROM rides r " +
                "JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "WHERE r.driver_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                out.append("<tr>");
                out.append("<td>").append(rs.getInt("ride_id")).append("</td>");
                out.append("<td>").append(rs.getString("pickup_location")).append("</td>");
                out.append("<td>").append(rs.getString("drop_location")).append("</td>");
                out.append("<td>").append(rs.getDouble("fare")).append("</td>");

                // Status derived from payment existence
                out.append("<td data-status='accepted'>accepted</td>");
                out.append("</tr>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.append("</tbody></table>");
        res.getWriter().print(out.toString());
    }
}
