package Controller;

import java.io.IOException;
import java.util.Properties;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.mail.*;
import jakarta.mail.internet.*;

@WebServlet("/register")
public class SendMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        
        
        String otp = generateOTP();
        sendEmail(email, otp);
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);

        // Redirect to OTP input page
        response.sendRedirect(request.getContextPath() + "/views/common/enter-otp.jsp");

    }
    
    private String generateOTP() {
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = (int)(numbers.length() * Math.random());
            sb.append(numbers.charAt(index));
        }
        return sb.toString();
    }

    private void sendEmail(String email, String otp) {
        final String username = "anhpham161223@gmail.com";
        final String password = "dpmtgtmguxwknswr";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

      try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("VERIFY YOUR OTP FROM VIVATIMESHARE"); // Email subject

            // Email content with enhanced design
            String htmlBody = "<html><head><style>" +
                              "body { font-family: Arial, sans-serif; }" +
                              ".header { color: #007bff; }" +
                              ".otp { color: #28a745; }" +
                              ".footer { margin-top: 20px; text-align: center; color: #6c757d; }" +
                              ".content { color: #343a40; }" +
                              "</style></head>" +
                              "<body>" +
                              "<h1 class='header'>VERIFY YOUR OTP FROM VIVATIMESHARE</h1>" +
                              "<p class='content'>Your OTP is: <strong class='otp'>" + otp + "</strong></p>" +
                              "<p class='content'>Please enter this code on the website to complete the verification process.</p>" +
                              "<p class='footer'>Thank you from VivaTimeshare.</p>" +
                              "</body></html>";

            message.setContent(htmlBody, "text/html");

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}