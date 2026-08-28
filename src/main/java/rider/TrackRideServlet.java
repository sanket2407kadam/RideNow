package rider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name="TrackRideServlet", urlPatterns={"/track-ride"})
public class TrackRideServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ridenow";
    private final String dbUser = "root";
    private final String dbPassword = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.html");
            return;
        }
        String rideIdStr = request.getParameter("rideId");
        if(rideIdStr == null || rideIdStr.isEmpty()){
            out.println("Ride ID is required.");
            return;
        }

        int rideId = Integer.parseInt(rideIdStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            String sql = "SELECT r.pickup_location, r.drop_location, r.ride_status, " +
                         "d.vehicle_number, u.full_name AS driver_name, rd.tracking_no, " +
                         "d.latitude AS driver_lat, d.longitude AS driver_lng " +
                         "FROM rides r " +
                         "LEFT JOIN drivers d ON r.driver_id = d.driver_id " +
                         "LEFT JOIN users u ON d.user_id = u.user_id " +
                         "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                         "WHERE r.ride_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, rideId);
            ResultSet rs = pst.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Track Ride | RideNow</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<link rel='stylesheet' href='https://unpkg.com/leaflet@1.9.4/dist/leaflet.css' />");
            out.println("<style>");
            out.println("body{background:#f4f6f8;font-family:'Segoe UI',Tahoma,sans-serif;}");
            out.println(".container{margin-top:40px;}");
            out.println(".card{border-radius:15px;box-shadow:0 15px 35px rgba(0,0,0,.1);}");
            out.println("#map{height:400px;width:100%;border-radius:15px;margin-top:20px;}");
            out.println(".status-badge{padding:0.5em 1em;font-weight:600;border-radius:12px;color:#fff;}");
            out.println(".requested{background:#2563eb;}");
            out.println(".accepted{background:#f59e0b;}");
            out.println(".completed{background:#16a34a;}");
            out.println(".cancelled{background:#dc2626;}");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<div class='container'><div class='card p-4'>");
            out.println("<h2 class='text-center mb-3'>🚦 Ride Tracking</h2>");

            if(rs.next()){
                String rideStatus = rs.getString("ride_status");

                // Default coordinates for pickup/drop if not stored separately
                double driverLat = rs.getDouble("driver_lat");
                double driverLng = rs.getDouble("driver_lng");
                double pickupLat = driverLat; // placeholder, you can store actual pickup_lat in rides table
                double pickupLng = driverLng; 
                double dropLat = driverLat;   // placeholder, you can store actual drop_lat in rides table
                double dropLng = driverLng;

                // Ride info
                out.println("<p><strong>Status:</strong> " +
                        "<span class='status-badge " + rideStatus + "'>" + rideStatus.toUpperCase() + "</span></p>");
                out.println("<p><strong>Driver:</strong> " + 
                        (rs.getString("driver_name") != null ? rs.getString("driver_name") : "Not Assigned") + "</p>");
                out.println("<p><strong>Vehicle:</strong> " + 
                        (rs.getString("vehicle_number") != null ? rs.getString("vehicle_number") : "-") + "</p>");
                out.println("<p><strong>Pickup:</strong> " + rs.getString("pickup_location") + "</p>");
                out.println("<p><strong>Drop:</strong> " + rs.getString("drop_location") + "</p>");

                // Map container
                out.println("<div id='map'></div>");

                // Leaflet JS for map
                out.println("<script src='https://unpkg.com/leaflet@1.9.4/dist/leaflet.js'></script>");
                out.println("<script>");
                out.println("var map = L.map('map').fitBounds([");
                out.println("[" + driverLat + "," + driverLng + "],");
                out.println("[" + pickupLat + "," + pickupLng + "],");
                out.println("[" + dropLat + "," + dropLng + "]");
                out.println("]);");

                out.println("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',{maxZoom:19}).addTo(map);");

                // Markers
                out.println("L.marker([" + driverLat + "," + driverLng + "]).addTo(map).bindPopup('🚘 Driver Location').openPopup();");
                out.println("L.marker([" + pickupLat + "," + pickupLng + "]).addTo(map).bindPopup('📍 Pickup').openPopup();");
                out.println("L.marker([" + dropLat + "," + dropLng + "]).addTo(map).bindPopup('🏁 Drop').openPopup();");

                // Optional: draw route line
                out.println("var routeLine = L.polyline([");
                out.println("[" + pickupLat + "," + pickupLng + "],");
                out.println("[" + driverLat + "," + driverLng + "],");
                out.println("[" + dropLat + "," + dropLng + "]");
                out.println("], {color:'blue', weight:4, opacity:0.7}).addTo(map);");

                out.println("</script>");
            } else {
                out.println("<p class='text-danger'>No ride found with ID " + rideId + "</p>");
            }

            out.println("</div></div>");
            out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
            out.println("</body></html>");

            conn.close();
        } catch(Exception e){
            e.printStackTrace(out);
        }
    }
}
