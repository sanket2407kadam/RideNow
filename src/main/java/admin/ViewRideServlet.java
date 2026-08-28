package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewRideServlet")
public class ViewRideServlet extends HttpServlet {

    private final String JDBC_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private final String JDBC_USER = "root";
    private final String JDBC_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("ManageRidesServlet");
            return;
        }

        int rideId = Integer.parseInt(idStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn =
                DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql =
                "SELECT r.ride_id, " +
                "u1.full_name AS rider, " +
                "IFNULL(u2.full_name, 'Not Assigned') AS driver, " +
                "r.pickup_location, r.drop_location, " +
                "r.ride_status, r.ride_time, " +
                "IFNULL(rd.fare, 0) AS fare " +
                "FROM rides r " +
                "JOIN users u1 ON r.rider_id = u1.user_id " +
                "LEFT JOIN drivers d ON r.driver_id = d.driver_id " +
                "LEFT JOIN users u2 ON d.user_id = u2.user_id " +
                "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "WHERE r.ride_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, rideId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("rideId", rs.getInt("ride_id"));
                request.setAttribute("rider", rs.getString("rider"));
                request.setAttribute("driver", rs.getString("driver"));
                request.setAttribute("pickup", rs.getString("pickup_location"));
                request.setAttribute("drop", rs.getString("drop_location"));
                request.setAttribute("status", rs.getString("ride_status"));
                request.setAttribute("fare", rs.getDouble("fare"));
                request.setAttribute("time", rs.getTimestamp("ride_time"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("view-ride.jsp").forward(request, response);
    }
}
