package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/CancelRideServlet")
public class CancelRideServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String idStr = req.getParameter("id");
        if (idStr == null) {
            res.sendRedirect("ManageRidesServlet?msg=error");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow","root","admin")) {

            String sql =
                "UPDATE rides SET ride_status='cancelled' " +
                "WHERE ride_id=? AND ride_status!='completed'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            res.sendRedirect("cancel-success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("ManageRidesServlet?msg=error");
        }
    }
}
