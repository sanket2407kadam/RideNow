package admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ResolveSupportServlet")
public class ResolveSupportServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int messageId = Integer.parseInt(request.getParameter("messageId"));

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow", "root", "admin");

            String sql = "UPDATE contact_messages SET status='Resolved' WHERE message_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, messageId);
            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("SupportServlet");
    }
}
