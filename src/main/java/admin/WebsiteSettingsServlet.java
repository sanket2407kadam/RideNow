package admin;

import java.io.IOException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/WebsiteSettingsServlet")
public class WebsiteSettingsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        WebsiteSettings ws = new WebsiteSettings();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow","root","admin");

            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM website_settings WHERE setting_id=1");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ws.setWebsiteName(rs.getString("website_name"));
                ws.setSupportEmail(rs.getString("support_email"));
                ws.setSupportPhone(rs.getString("support_phone"));

                ws.setBaseFare(rs.getDouble("base_fare"));
                ws.setPerKmCharge(rs.getDouble("per_km_charge"));
                ws.setCancellationFee(rs.getDouble("cancellation_fee"));

                ws.setBannerText(rs.getString("banner_text"));
                ws.setAnnouncement(rs.getString("announcement"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("settings", ws);
        RequestDispatcher rd = request.getRequestDispatcher("website-settings.jsp");
        rd.forward(request, response);
    }
}
