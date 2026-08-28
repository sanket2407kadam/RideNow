package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ridenow";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin"; // change if needed

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "UPDATE users SET password=? WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, password); // plain text (for demo)
            ps.setString(2, email);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                response.sendRedirect("forgot-password.html?success=1");
            } else {
                response.sendRedirect("forgot-password.html?error=1");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("forgot-password.html?error=1");
        }
    }
}
