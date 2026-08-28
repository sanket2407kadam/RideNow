package admin;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SupportServlet")
public class SupportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final String DB_URL  = "jdbc:mysql://localhost:3306/ridenow";
    private final String DB_USER = "root";
    private final String DB_PASS = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Support> supportList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT message_id, email, message, status, created_at " +
                         "FROM contact_messages ORDER BY created_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Support s = new Support();
                s.setMessageId(rs.getInt("message_id"));
                s.setEmail(rs.getString("email"));
                s.setMessage(rs.getString("message"));
                s.setStatus(rs.getString("status"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                supportList.add(s);
            }

            System.out.println("Support records found: " + supportList.size());

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("supports", supportList);
        RequestDispatcher rd = request.getRequestDispatcher("support.jsp");
        rd.forward(request, response);
    }
}
