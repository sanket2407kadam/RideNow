package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AddDriverServlet")
public class AddDriverServlet extends HttpServlet {

    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String vehicleNo = request.getParameter("vehicleNo");
        String vehicleType = request.getParameter("vehicleType");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // 1️⃣ Validate driver user
            String checkUser =
                "SELECT user_id FROM users WHERE user_id=? AND role='driver'";
            PreparedStatement ps1 = conn.prepareStatement(checkUser);
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                response.sendRedirect("add-driver.jsp?msg=invalid");
                return;
            }

            // 2️⃣ Check already exists
            String checkDriver =
                "SELECT driver_id FROM drivers WHERE user_id=?";
            PreparedStatement ps2 = conn.prepareStatement(checkDriver);
            ps2.setInt(1, userId);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                response.sendRedirect("add-driver.jsp?msg=exists");
                return;
            }

            // 3️⃣ Insert driver
            String insert =
                "INSERT INTO drivers (user_id, vehicle_number, vehicle_type) VALUES (?,?,?)";
            PreparedStatement ps3 = conn.prepareStatement(insert);
            ps3.setInt(1, userId);
            ps3.setString(2, vehicleNo);
            ps3.setString(3, vehicleType);
            ps3.executeUpdate();

            conn.close();
            response.sendRedirect("add-driver.jsp?msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("add-driver.jsp?msg=invalid");
        }
    }
}
