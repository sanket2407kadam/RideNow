package driver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DriverEarningsServlet")
public class DriverEarningsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("driver_id") == null) {
            response.getWriter().print("0");
            return;
        }

        // Safe session parsing
        int driverId = Integer.parseInt(session.getAttribute("driver_id").toString());
        double total = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC",
                "root",
                "admin"
            );

            String sql =
                "SELECT COALESCE(SUM(ride_total),0) " +
                "FROM ( " +
                "   SELECT r.ride_id, SUM(rd.fare) AS ride_total " +
                "   FROM rides r " +
                "   JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "   JOIN payments p ON r.ride_id = p.ride_id " +
                "   WHERE r.driver_id = ? " +
                "   AND p.payment_status = 'SUCCESS' " +
                "   GROUP BY r.ride_id " +
                ") AS earnings";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, driverId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }

            response.getWriter().print(total);

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("0");
        }
    }
}
