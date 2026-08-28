package payment;

import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String rideIdStr = req.getParameter("rideId");
        String pay = req.getParameter("pay");

        if (rideIdStr == null || pay == null) {
            res.sendError(400, "Invalid request");
            return;
        }

        int rideId = Integer.parseInt(rideIdStr);
        System.out.println("Ride ID: " + rideId);

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow", "root", "admin")) {

            // 1️⃣ Save payment
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO payments (ride_id, payment_method, payment_status) VALUES (?, ?, ?)"
            );
            ps.setInt(1, rideId);
            ps.setString(2, pay);
            ps.setString(3, "SUCCESS");
            ps.executeUpdate();

            System.out.println("Payment stored");

            // 2️⃣ Fetch rider email (CORRECT COLUMN: rider_id)
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT u.email " +
                "FROM users u " +
                "JOIN rides r ON u.user_id = r.rider_id " +
                "WHERE r.ride_id = ?"
            );
            ps2.setInt(1, rideId);

            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                System.out.println("Email found: " + email);

                // 3️⃣ Send email
                EmailSender.sendPaymentEmail(email, rideId, pay);
            } else {
                System.out.println("❌ No email found for rideId " + rideId);
            }

            res.sendRedirect("success.html");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("error.html");
        }
    }
}
