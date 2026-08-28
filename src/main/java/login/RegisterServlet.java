package login;

import java.io.IOException;
import java.sql.*;
import java.net.URLEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
	  
  	response.setContentType("text/html");


    String fullname = request.getParameter("fullname");
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    String password = request.getParameter("password");
    String role = request.getParameter("role");

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/ridenow","root","admin");

      PreparedStatement ps = con.prepareStatement(
        "INSERT INTO users(full_name,email,phone,password,role) VALUES(?,?,?,?,?)",
        Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, fullname);
      ps.setString(2, email);
      ps.setString(3, phone);
      ps.setString(4, password);
      ps.setString(5, role);

      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();

      HttpSession session = request.getSession();
      session.setAttribute("user_id", rs.getInt(1));
      session.setAttribute("username", fullname);
      session.setAttribute("role", role);
      

      response.sendRedirect(
        "index.html?user=" + URLEncoder.encode(fullname, "UTF-8")
      );

      con.close();

    } catch (Exception e) {
      e.printStackTrace();
      response.sendRedirect("register.html");
    }
  }
}
