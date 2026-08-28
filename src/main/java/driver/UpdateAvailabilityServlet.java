package driver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateAvailabilityServlet")
public class UpdateAvailabilityServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ridenow";
    private final String dbUser = "root";
    private final String dbPassword = "admin";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer driverId = (Integer) session.getAttribute("driver_id");

        response.setContentType("text/plain");

        if(driverId == null){
            response.getWriter().println("Please login as driver to update availability.");
            return;
        }

        String newStatus = request.getParameter("availability");
        if(newStatus == null || (!newStatus.equals("available") && !newStatus.equals("busy"))){
            newStatus = "available";  // default
        }

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "UPDATE drivers SET availability=? WHERE driver_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, driverId);
            int updated = stmt.executeUpdate();

            if(updated > 0){
                response.getWriter().println("Availability updated to " + newStatus);
            } else {
                response.getWriter().println("Failed to update availability.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error!");
        }
    }
}
