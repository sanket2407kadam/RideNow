package rider;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookRideServlet")
public class BookRideServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int riderId = (int) session.getAttribute("user_id");

        String pickup = request.getParameter("pickup");
        String drop = request.getParameter("drop");
        double baseFare = 100 + Math.random() * 200;

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow", "root", "admin")) {

            con.setAutoCommit(false);

            // Find available driver
            PreparedStatement dps = con.prepareStatement(
                "SELECT driver_id, vehicle_type FROM drivers WHERE availability='available' LIMIT 1 FOR UPDATE");
            ResultSet drs = dps.executeQuery();

            Integer driverId = null;
            String vehicleType = null;

            if (drs.next()) {
                driverId = drs.getInt("driver_id");
                vehicleType = drs.getString("vehicle_type");

                PreparedStatement upd = con.prepareStatement(
                    "UPDATE drivers SET availability='busy' WHERE driver_id=?");
                upd.setInt(1, driverId);
                upd.executeUpdate();
            }

            // Insert ride
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO rides (rider_id, driver_id, pickup_location, drop_location, ride_status) VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, riderId);
            if (driverId != null) ps.setInt(2, driverId);
            else ps.setNull(2, Types.INTEGER);

            ps.setString(3, pickup);
            ps.setString(4, drop);
            ps.setString(5, "accepted");
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int rideId = rs.getInt(1);

            // Insert ride details
            PreparedStatement rds = con.prepareStatement(
                "INSERT INTO ride_details (ride_id, vehicle_type, fare, tracking_no) VALUES (?,?,?,?)");

            rds.setInt(1, rideId);
            rds.setString(2, vehicleType != null ? vehicleType : "Cab");
            rds.setDouble(3, baseFare);
            rds.setString(4, UUID.randomUUID().toString().substring(0, 8));
            rds.executeUpdate();

            con.commit();

            response.sendRedirect(
                "showRoute.html?rideId=" + rideId +
                "&pickup=" + URLEncoder.encode(pickup, "UTF-8") +
                "&drop=" + URLEncoder.encode(drop, "UTF-8")
            );

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
