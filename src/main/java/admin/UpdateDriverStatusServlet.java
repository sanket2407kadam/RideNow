package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateDriverStatusServlet")
public class UpdateDriverStatusServlet extends HttpServlet {

    private static final String JDBC_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("ManageDriversServlet?msg=invalid");
            return;
        }

        int driverId = Integer.parseInt(idStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =
                DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // ✅ Get current availability
            PreparedStatement ps1 =
                con.prepareStatement(
                    "SELECT availability FROM drivers WHERE driver_id=?");
            ps1.setInt(1, driverId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                String current = rs.getString("availability");
                String next =
                    "available".equalsIgnoreCase(current)
                    ? "busy" : "available";

                // ✅ Update availability
                PreparedStatement ps2 =
                    con.prepareStatement(
                        "UPDATE drivers SET availability=? WHERE driver_id=?");
                ps2.setString(1, next);
                ps2.setInt(2, driverId);
                ps2.executeUpdate();
            }

            con.close();
            response.sendRedirect("ManageDriversServlet?msg=updated");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ManageDriversServlet?msg=error");
        }
    }
}
