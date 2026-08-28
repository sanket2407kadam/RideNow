package driver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DriverLoginServlet")
public class DriverLoginServlet extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/ridenow";
    private final String USER = "root";
    private final String PASS = "admin";

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html;charset=UTF-8");

        String driverIdStr = req.getParameter("driver_id");

        if (driverIdStr == null || driverIdStr.trim().isEmpty()) {
            res.getWriter().print("Driver ID required");
            return;
        }

        int driverId = Integer.parseInt(driverIdStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement ps =
                    con.prepareStatement("SELECT driver_id FROM drivers WHERE driver_id=?");
            ps.setInt(1, driverId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("driver_id", driverId);
                session.setMaxInactiveInterval(30 * 60);

                res.sendRedirect("driver.html");
            } else {
                res.getWriter().print("Invalid Driver ID");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().print("Server Error");
        }
    }
}
