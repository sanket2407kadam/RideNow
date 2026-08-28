package admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ManagePaymentsServlet")
public class ManagePaymentsServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        List<Payment> payments = new ArrayList<>();
        String statusFilter = request.getParameter("status");
        String searchQuery = request.getParameter("query");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT p.payment_id, p.ride_id, " +
                    "ru.full_name AS rider_name, " +
                    "du.full_name AS driver_name, " +
                    "rd.fare AS amount, " +
                    "p.payment_method, p.payment_status, p.payment_time " +
                    "FROM payments p " +
                    "JOIN rides r ON p.ride_id = r.ride_id " +
                    "JOIN users ru ON r.rider_id = ru.user_id " +
                    "LEFT JOIN drivers d ON r.driver_id = d.driver_id " +
                    "LEFT JOIN users du ON d.user_id = du.user_id " +
                    "LEFT JOIN ride_details rd ON r.ride_id = rd.ride_id WHERE 1=1 ";

            if(statusFilter != null && !statusFilter.isEmpty()) {
                sql += " AND p.payment_status = ?";
            }
            if(searchQuery != null && !searchQuery.isEmpty()) {
                sql += " AND (ru.full_name LIKE ? OR du.full_name LIKE ? OR r.ride_id LIKE ?)";
            }

            sql += " ORDER BY p.payment_time DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            int idx = 1;
            if(statusFilter != null && !statusFilter.isEmpty()) ps.setString(idx++, statusFilter);
            if(searchQuery != null && !searchQuery.isEmpty()) {
                String q = "%" + searchQuery + "%";
                ps.setString(idx++, q);
                ps.setString(idx++, q);
                ps.setString(idx++, q);
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setRideId(rs.getInt("ride_id"));
                p.setRider(rs.getString("rider_name"));
                p.setDriver(rs.getString("driver_name"));
                p.setAmount(rs.getDouble("amount"));
                p.setMethod(rs.getString("payment_method"));
                p.setStatus(rs.getString("payment_status"));
                p.setTime(rs.getTimestamp("payment_time"));
                payments.add(p);
            }

            rs.close();
            ps.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("payments", payments);
        request.getRequestDispatcher("manage-payments.jsp").forward(request, response);
    }
}
