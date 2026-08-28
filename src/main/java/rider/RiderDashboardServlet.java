package rider;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/rider")
public class RiderDashboardServlet extends HttpServlet {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/ridenow";
    private final String DB_USER = "root";
    private final String DB_PASS = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("user_id");

        String name="", email="", phone="";
        StringBuilder notifications = new StringBuilder();
        StringBuilder rides = new StringBuilder();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            // PROFILE
            PreparedStatement ps = con.prepareStatement(
                "SELECT full_name,email,phone FROM users WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet ru = ps.executeQuery();
            if (ru.next()) {
                name = ru.getString(1);
                email = ru.getString(2);
                phone = ru.getString(3);
            }

            // NOTIFICATIONS
            PreparedStatement pn = con.prepareStatement(
                "SELECT message FROM notifications WHERE user_id=? ORDER BY created_at DESC LIMIT 5");
            pn.setInt(1, userId);
            ResultSet rn = pn.executeQuery();
            while (rn.next()) {
                notifications.append("<div class='alert'>")
                             .append(rn.getString(1))
                             .append("</div>");
            }
            if (notifications.length()==0)
                notifications.append("<div class='alert'>No notifications</div>");

            // RIDES
            PreparedStatement pr = con.prepareStatement(
                "SELECT * FROM rides WHERE rider_id=? ORDER BY ride_date DESC LIMIT 5");
            pr.setInt(1, userId);
            ResultSet rr = pr.executeQuery();
            while (rr.next()) {
                rides.append("<tr>")
                     .append("<td>").append(rr.getInt("ride_id")).append("</td>")
                     .append("<td>").append(rr.getString("from_location")).append("</td>")
                     .append("<td>").append(rr.getString("to_location")).append("</td>")
                     .append("<td>").append(rr.getTimestamp("ride_date")).append("</td>")
                     .append("<td>₹").append(rr.getDouble("fare")).append("</td>")
                     .append("<td>").append(rr.getString("ride_status")).append("</td>")
                     .append("</tr>");
            }
            if (rides.length()==0)
                rides.append("<tr><td colspan='6'>No upcoming rides</td></tr>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("notifications", notifications.toString());
        request.setAttribute("rides", rides.toString());

        RequestDispatcher rd =
            request.getRequestDispatcher("rider.jsp");
        rd.forward(request, response);
    }
}
