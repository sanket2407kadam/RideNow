package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RetryPaymentServlet")
public class RetryPaymentServlet extends HttpServlet {

    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentIdStr = request.getParameter("id");

        if (paymentIdStr == null || paymentIdStr.isEmpty()) {
            response.sendRedirect("ManagePaymentsServlet?msg=error");
            return;
        }

        Connection conn = null;

        try {
            int paymentId = Integer.parseInt(paymentIdStr);
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "UPDATE payments SET payment_status='Success' WHERE payment_id=? AND payment_status='Failed'";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, paymentId);
                int updated = ps.executeUpdate();

                if (updated > 0) {
                    response.sendRedirect("ManagePaymentsServlet?msg=retry_success");
                } else {
                    response.sendRedirect("ManagePaymentsServlet?msg=error");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ManagePaymentsServlet?msg=error");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
