package admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AssignRoleServlet")
public class AssignRoleServlet extends HttpServlet {

    private final String jdbcURL =
        "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = "admin";

    // LOAD USERS
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            String sql = "SELECT user_id, full_name, email, role FROM users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("users", users);
        request.getRequestDispatcher("assign-roles.jsp").forward(request, response);
    }

    // ASSIGN ROLE
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String role = request.getParameter("role");

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {

            PreparedStatement updateRole =
                conn.prepareStatement("UPDATE users SET role=? WHERE user_id=?");
            updateRole.setString(1, role);
            updateRole.setInt(2, userId);
            updateRole.executeUpdate();

            if ("driver".equals(role)) {
                PreparedStatement check =
                    conn.prepareStatement("SELECT * FROM drivers WHERE user_id=?");
                check.setInt(1, userId);
                ResultSet rs = check.executeQuery();

                if (!rs.next()) {
                    PreparedStatement insert =
                        conn.prepareStatement(
                            "INSERT INTO drivers(user_id, vehicle_type, availability) VALUES (?, 'Car', 'available')");
                    insert.setInt(1, userId);
                    insert.executeUpdate();
                }
            }

            response.sendRedirect("AssignRoleServlet?msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AssignRoleServlet?msg=error");
        }
    }
}
