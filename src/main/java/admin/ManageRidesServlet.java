package admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ManageRidesServlet")
public class ManageRidesServlet extends HttpServlet {

    private static final String JDBC_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> rides = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql =
                "SELECT r.ride_id, " +
                "ru.full_name AS rider, " +
                "COALESCE(du.full_name, 'Not Assigned') AS driver, " +
                "r.pickup_location, r.drop_location, r.ride_time, " +
                "r.ride_status, IFNULL(rd.fare, 0) AS fare " +
                "FROM rides r " +
                "JOIN users ru ON r.rider_id = ru.user_id " +
                "LEFT JOIN drivers d ON r.driver_id = d.driver_id " +
                "LEFT JOIN users du ON d.user_id = du.user_id " +
                "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "ORDER BY r.ride_time DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> ride = new HashMap<>();
                ride.put("id", rs.getInt("ride_id"));
                ride.put("rider", rs.getString("rider"));
                ride.put("driver", rs.getString("driver"));
                ride.put("from", rs.getString("pickup_location"));
                ride.put("to", rs.getString("drop_location"));
                ride.put("time", rs.getTimestamp("ride_time"));
                ride.put("status", rs.getString("ride_status"));
                ride.put("fare", rs.getDouble("fare"));
                rides.add(ride);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("rides", rides);
        request.getRequestDispatcher("manage-rides.jsp").forward(request, response);
    }
}
