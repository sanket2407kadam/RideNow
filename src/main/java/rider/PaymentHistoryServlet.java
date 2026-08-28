package rider;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name="PaymentHistoryServlet", urlPatterns={"/payment-history"})
public class PaymentHistoryServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ridenow";
    private final String dbUser = "root";
    private final String dbPassword = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
        	response.sendRedirect("login.html");
        }; // demo user

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            String sql =
                "SELECT r.pickup_location, r.drop_location, rd.fare, " +
                "p.payment_method, p.payment_status, p.payment_time " +
                "FROM payments p " +
                "JOIN rides r ON p.ride_id = r.ride_id " +
                "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                "WHERE r.rider_id=? " +
                "ORDER BY p.payment_time DESC";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            /* ================= HTML ================= */

            out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>RideNow | Payment History</title>
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
                        box-shadow:0 15px 30px rgba(0,0,0,.1);
                    }
                    .badge-success{background:#16a34a;}
                    .badge-failed{background:#dc2626;}
                    .header{
                        font-weight:800;
                        color:#2563eb;
                    }
                </style>
            </head>

            <body>
            <div class='container py-5'>
                <div class='card'>
                    <div class='card-body'>
                        <h3 class='header mb-4'>💳 Payment History</h3>

                        <div class='table-responsive'>
                        <table class='table align-middle table-hover'>
                            <thead class='table-light'>
                                <tr>
                                    <th>Pickup</th>
                                    <th>Drop</th>
                                    <th>Fare (₹)</th>
                                    <th>Method</th>
                                    <th>Status</th>
                                    <th>Date</th>
                                </tr>
                            </thead>
                            <tbody>
            """);

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                String status = rs.getString("payment_status");
                String badgeClass = "badge-success";
                if (!"success".equalsIgnoreCase(status)) {
                    badgeClass = "badge-failed";
                }

                out.println("<tr>");
                out.println("<td>" + rs.getString("pickup_location") + "</td>");
                out.println("<td>" + rs.getString("drop_location") + "</td>");
                out.println("<td><strong>" + rs.getDouble("fare") + "</strong></td>");
                out.println("<td>" + rs.getString("payment_method") + "</td>");
                out.println("<td><span class='badge " + badgeClass + "'>" + status + "</span></td>");
                out.println("<td>" + rs.getTimestamp("payment_time") + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("""
                    <tr>
                        <td colspan='6' class='text-center text-muted py-4'>
                            No payment records found
                        </td>
                    </tr>
                """);
            }

            out.println("""
                            </tbody>
                        </table>
                        </div>

                        <a href='rider.jsp' class='btn btn-primary mt-3'>
                            ← Back to Dashboard
                        </a>

                    </div>
                </div>
            </div>
            </body>
            </html>
            """);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
