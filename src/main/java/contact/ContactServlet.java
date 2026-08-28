package contact;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {

    // Database credentials
    private final String DB_URL = "jdbc:mysql://localhost:3306/ridenow";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "admin"; // replace with your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // JDBC connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC driver
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, message);
            pst.executeUpdate();

            pst.close();
            conn.close();

            // Redirect with success message
            response.sendRedirect("contact.html?msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
