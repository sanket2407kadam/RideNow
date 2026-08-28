package admin;

import java.io.IOException;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateWebsiteSettingsServlet")
public class UpdateWebsiteSettingsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow","root","admin");

            PreparedStatement ps = null;

            if ("site".equals(action)) {
                ps = con.prepareStatement(
                    "UPDATE website_settings SET website_name=?, support_email=?, support_phone=? WHERE setting_id=1");
                ps.setString(1, request.getParameter("websiteName"));
                ps.setString(2, request.getParameter("supportEmail"));
                ps.setString(3, request.getParameter("supportPhone"));
            }

            if ("pricing".equals(action)) {
                ps = con.prepareStatement(
                    "UPDATE website_settings SET base_fare=?, per_km_charge=?, cancellation_fee=? WHERE setting_id=1");
                ps.setDouble(1, Double.parseDouble(request.getParameter("baseFare")));
                ps.setDouble(2, Double.parseDouble(request.getParameter("perKm")));
                ps.setDouble(3, Double.parseDouble(request.getParameter("cancelFee")));
            }

            if ("content".equals(action)) {
                ps = con.prepareStatement(
                    "UPDATE website_settings SET banner_text=?, announcement=? WHERE setting_id=1");
                ps.setString(1, request.getParameter("bannerText"));
                ps.setString(2, request.getParameter("announcement"));
            }

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("WebsiteSettingsServlet");
    }
}
