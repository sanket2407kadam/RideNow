package driver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CompleteRideServlet")
public class CompleteRideServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int rideId = Integer.parseInt(request.getParameter("ride_id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            // 1️⃣ INSERT PAYMENT (AUTO)
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO payments (ride_id, payment_method, payment_status) VALUES (?, ?, ?)");
            ps.setInt(1, rideId);
            ps.setString(2, "CASH");
            ps.setString(3, "SUCCESS");
            ps.executeUpdate();

            response.getWriter().print("Ride completed & payment added");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Error completing ride");
        }
    }
}
