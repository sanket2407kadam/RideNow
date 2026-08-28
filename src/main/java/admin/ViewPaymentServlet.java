package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewPaymentServlet")
public class ViewPaymentServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int paymentId = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT p.payment_id, p.ride_id, p.payment_method, p.payment_status, p.payment_time, " +
                    "r.pickup_location, r.drop_location, r.ride_time, rd.fare, ru.full_name AS rider, " +
                    "du.full_name AS driver " +
                    "FROM payments p " +
                    "JOIN rides r ON p.ride_id = r.ride_id " +
                    "JOIN users ru ON r.rider_id = ru.user_id " +
                    "LEFT JOIN drivers d ON r.driver_id = d.driver_id " +
                    "LEFT JOIN users du ON d.user_id = du.user_id " +
                    "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id " +
                    "WHERE p.payment_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, paymentId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                request.setAttribute("paymentId", rs.getInt("payment_id"));
                request.setAttribute("rideId", rs.getInt("ride_id"));
                request.setAttribute("rider", rs.getString("rider"));
                request.setAttribute("driver", rs.getString("driver"));
                request.setAttribute("pickup", rs.getString("pickup_location"));
                request.setAttribute("drop", rs.getString("drop_location"));
                request.setAttribute("fare", rs.getDouble("fare"));
                request.setAttribute("method", rs.getString("payment_method"));
                request.setAttribute("status", rs.getString("payment_status"));
                request.setAttribute("time", rs.getTimestamp("payment_time"));
                request.setAttribute("rideTime", rs.getTimestamp("ride_time"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("view-payment.jsp").forward(request, response);
    }
}
