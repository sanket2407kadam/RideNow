package payment;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private static final String FROM_EMAIL = "sanket240704@gmail.com";
    private static final String PASSWORD = "ezwz ufct yxrt ppxw"; // Gmail App Password

    public static void sendPaymentEmail(String toEmail, int rideId, String paymentMethod)
            throws MessagingException {

        String subject = "RideNow - Payment Successful";

        String body =
                "Dear Rider,\n\n" +
                "Your payment was successful.\n\n" +
                "Ride ID: " + rideId + "\n" +
                "Payment Method: " + paymentMethod + "\n\n" +
                "Thank you for choosing RideNow.\n\n" +
                "Regards,\nRideNow Team";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM_EMAIL));
        msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);

        System.out.println("✅ Email sent to: " + toEmail);
    }
}
