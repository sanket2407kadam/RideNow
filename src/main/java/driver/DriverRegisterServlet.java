package driver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DriverRegisterServlet")
public class DriverRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        // ---------- Common Page Header ----------
        out.println("""
        <!DOCTYPE html>
        <html>
        <head>
            <title>Driver Registration | RideNow</title>
            <style>
                body{
                    margin:0;
                    font-family:'Segoe UI',Tahoma,sans-serif;
                    background:#f4f6f8;
                    display:flex;
                    justify-content:center;
                    align-items:center;
                    height:100vh;
                }
                .card{
                    background:#fff;
                    padding:35px 45px;
                    border-radius:12px;
                    box-shadow:0 8px 20px rgba(0,0,0,0.12);
                    max-width:420px;
                    width:100%;
                    text-align:center;
                }
                h2{
                    color:#2563eb;
                    margin-bottom:12px;
                }
                p{
                    color:#555;
                    margin-bottom:25px;
                }
                a{
                    display:inline-block;
                    text-decoration:none;
                    background:#2563eb;
                    color:#fff;
                    padding:12px 28px;
                    border-radius:8px;
                    font-weight:600;
                    transition:0.3s;
                }
                a:hover{
                    background:#1e4fd1;
                }
                .error h2{
                    color:#dc2626;
                }
            </style>
        </head>
        <body>
        """);

        if (userId == null) {
            out.println("""
            <div class="card error">
                <h2>Please Login First</h2>
                <p>You must be logged in to register as a driver.</p>
                <a href="login.html">Go to Login</a>
            </div>
            </body></html>
            """);
            return;
        }

        String vehicleNumber = request.getParameter("vehicle_number");
        String vehicleType = request.getParameter("vehicle_type");
        String availability = request.getParameter("availability");

        String url = "jdbc:mysql://localhost:3306/ridenow";
        String dbUser = "root";
        String dbPassword = "admin";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPassword);

            // Check if already registered
            PreparedStatement checkPs =
                    con.prepareStatement("SELECT driver_id FROM drivers WHERE user_id=?");
            checkPs.setInt(1, userId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                out.println("""
                <div class="card">
                    <h2>Already Registered</h2>
                    <p>You are already registered as a driver.</p>
                    <a href=driver.html">Go to Dashboard</a>
                </div>
                </body></html>
                """);
                con.close();
                return;
            }

            // Insert driver
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO drivers (user_id, vehicle_number, vehicle_type, availability) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, userId);
            ps.setString(2, vehicleNumber);
            ps.setString(3, vehicleType);
            ps.setString(4, availability);

            int i = ps.executeUpdate();

            if (i > 0) {
                out.println("""
                <div class="card">
                    <h2>🎉 Registration Successful!</h2>
                    <p>You are now officially a RideNow driver.<br>
                       Manage rides and update availability anytime.</p>
                    <a href="driver.html">Go to Driver Dashboard</a>
                </div>
                """);
            } else {
                out.println("""
                <div class="card error">
                    <h2>Registration Failed</h2>
                    <p>Something went wrong. Please try again.</p>
                    <a href="DriverRegister.html">Back to Registration</a>
                </div>
                """);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("""
            <div class="card error">
                <h2>Error Occurred</h2>
                <p>""" + e.getMessage() + """
            <a href="DriverRegister.html">Back</a>
            </div>
            """);
        }

        out.println("</body></html>");
    }
}
