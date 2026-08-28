package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/Admin-Dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/ridenow";
    private final String USER = "root";
    private final String PASS = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int users = 0;
        int drivers = 0;
        int rides = 0;
        double payments = 0.0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            // USERS (riders only)
            users = getCount(con, "SELECT COUNT(*) FROM users WHERE role='rider'");

            // DRIVERS
            drivers = getCount(con, "SELECT COUNT(*) FROM drivers");

            // RIDES
            rides = getCount(con, "SELECT COUNT(*) FROM rides");

            // PAYMENTS (SUCCESS only)
            PreparedStatement ps = con.prepareStatement(
                "SELECT IFNULL(SUM(rd.fare),0) " +
                "FROM ride_details rd " +
                "JOIN payments p ON rd.ride_id = p.ride_id " +
                "WHERE p.payment_status='SUCCESS'"
            );

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                payments = rs.getDouble(1);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // DEBUG (CHECK CONSOLE)
        System.out.println("USERS=" + users);
        System.out.println("DRIVERS=" + drivers);
        System.out.println("RIDES=" + rides);
        System.out.println("PAYMENTS=" + payments);

        request.setAttribute("users", users);
        request.setAttribute("drivers", drivers);
        request.setAttribute("rides", rides);
        request.setAttribute("payments", payments);

        request.getRequestDispatcher("/admin-dashboard.jsp")
               .forward(request, response);
    }

    private int getCount(Connection con, String sql) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
