package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RefundPaymentServlet")
public class RefundPaymentServlet extends HttpServlet {

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

            String sql = "UPDATE payments SET payment_status='Refunded' WHERE payment_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, paymentId);
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ManagePaymentsServlet");
    }
}
