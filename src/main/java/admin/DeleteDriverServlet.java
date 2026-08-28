package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DeleteDriverServlet")
public class DeleteDriverServlet extends HttpServlet {

    private static final String JDBC_URL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("ManageDriversServlet?msg=invalid");
            return;
        }

        int driverId = Integer.parseInt(idStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =
                DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            PreparedStatement ps =
                con.prepareStatement(
                    "DELETE FROM drivers WHERE driver_id=?");
            ps.setInt(1, driverId);
            ps.executeUpdate();

            con.close();
            response.sendRedirect("ManageDriversServlet?msg=deleted");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ManageDriversServlet?msg=error");
        }
    }
}
