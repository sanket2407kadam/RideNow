package admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ManageDriversServlet")
public class ManageDriversServlet extends HttpServlet {

    private static final String JDBC_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> drivers = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql =
                "SELECT d.driver_id, u.full_name, d.vehicle_number, " +
                "d.vehicle_type, d.availability, d.latitude, d.longitude " +
                "FROM drivers d JOIN users u ON d.user_id = u.user_id";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> driver = new HashMap<>();
                driver.put("id", rs.getInt("driver_id"));
                driver.put("name", rs.getString("full_name"));
                driver.put("vehicleNo", rs.getString("vehicle_number"));
                driver.put("type", rs.getString("vehicle_type"));
                driver.put("status", rs.getString("availability"));
                driver.put("latitude", rs.getDouble("latitude"));
                driver.put("longitude", rs.getDouble("longitude"));
                drivers.add(driver);
            }

            System.out.println("Drivers loaded: " + drivers.size());

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("drivers", drivers);
        RequestDispatcher rd =
            request.getRequestDispatcher("manage-drivers.jsp");
        rd.forward(request, response);
    }
}
