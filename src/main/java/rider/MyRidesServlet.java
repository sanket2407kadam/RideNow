package rider;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "MyRidesServlet", urlPatterns = {"/myrides"})
public class MyRidesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            res.sendRedirect("login.html");
            return;
        }

        int riderId = (int) session.getAttribute("user_id");

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            String sql =
                "SELECT r.ride_id, r.pickup_location, r.drop_location, r.ride_status, " +
                "r.ride_time, rd.tracking_no " +
                "FROM rides r " +
                "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "WHERE r.rider_id=? " +
                "ORDER BY r.ride_time DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, riderId);
            ResultSet rs = ps.executeQuery();

            out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>RideNow | My Rides</title>
                <meta name='viewport' content='width=device-width, initial-scale=1'>
                <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>
                <style>
                    body{
                        background:#f4f6f9;
                        font-family:'Segoe UI',Tahoma,sans-serif;
                    }
                    .card{
                        border:none;
                        border-radius:16px;
                        box-shadow:0 20px 40px rgba(0,0,0,.12);
                    }
                    .page-title{
                        font-weight:800;
                        color:#2563eb;
                    }
                    .table thead{
                        background:#2563eb;
                        color:#fff;
                    }
                    .table td, .table th{
                        vertical-align:middle;
                        text-align:center;
                    }
                    .badge-status{
                        padding:6px 12px;
                        border-radius:20px;
                        font-size:.75rem;
                        font-weight:700;
                        letter-spacing:.04em;
                    }
                    .requested{background:#2563eb;}
                    .accepted{background:#f59e0b;}
                    .completed{background:#16a34a;}
                    .cancelled{background:#dc2626;}
                    .btn-track{
                        background:#2563eb;
                        color:#fff;
                        border-radius:20px;
                        padding:6px 14px;
                        font-size:.8rem;
                        font-weight:700;
                        text-decoration:none;
                    }
                    .btn-track:hover{
                        background:#1e4fd8;
                        color:#fff;
                    }
                </style>
            </head>

            <body>
            <div class='container py-5'>
                <div class='card'>
                    <div class='card-body'>
                        <h3 class='page-title mb-4 text-center'>🛣 My Rides</h3>

                        <div class='table-responsive'>
                        <table class='table table-hover align-middle'>
                            <thead>
                                <tr>
                                    <th>Ride ID</th>
                                    <th>Pickup</th>
                                    <th>Drop</th>
                                    <th>Status</th>
                                    <th>Tracking No</th>
                                    <th>Ride Time</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
            """);

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                String status = rs.getString("ride_status");
                int rideId = rs.getInt("ride_id");

                out.println("<tr>");
                out.println("<td>#"+ rideId +"</td>");
                out.println("<td>"+ rs.getString("pickup_location") +"</td>");
                out.println("<td>"+ rs.getString("drop_location") +"</td>");
                out.println("<td><span class='badge-status "+ status +"'>"+ status.toUpperCase() +"</span></td>");
                out.println("<td>"+ (rs.getString("tracking_no") != null ? rs.getString("tracking_no") : "-") +"</td>");
                out.println("<td>"+ rs.getTimestamp("ride_time") +"</td>");
                out.println("<td><a class='btn-track' href='track-ride?rideId="+ rideId +"'>Track</a></td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("""
                    <tr>
                        <td colspan='7' class='text-center text-muted py-4'>
                            No rides found 🚕
                        </td>
                    </tr>
                """);
            }

            out.println("""
                            </tbody>
                        </table>
                        </div>

                        <div class='text-center mt-3'>
                            <a href='rider.jsp' class='btn btn-outline-primary'>
                                ← Back to Dashboard
                            </a>
                        </div>

                    </div>
                </div>
            </div>
            </body>
            </html>
            """);

            con.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
