package Controller;

import DAO.AuthenticationDAO;
import Model.CreateModel.UserSignUp;
import Service.MailService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "VerifyOTP", urlPatterns = {"/verify"})
public class VerifyOTP extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String otp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("tempEmail");
        String userName = (String) session.getAttribute("tempUserName");
        String password = (String) session.getAttribute("tempPassword");
        String fullName = (String) session.getAttribute("tempFullName");

        String enteredOTP = request.getParameter("otp");

        boolean isOTPValid = otp != null && !otp.isEmpty() && otp.equals(enteredOTP);

        if (isOTPValid) {
            AuthenticationDAO authDAO = new AuthenticationDAO();
            UserSignUp userSignUp = new UserSignUp();
            userSignUp.setUserName(userName);
            userSignUp.setPassword(password);
            userSignUp.setEmail(email);
            userSignUp.setFullName(fullName);

            boolean result = authDAO.SignUp(userSignUp);

            if (result) {
                session.removeAttribute("otp");
                session.removeAttribute("tempEmail");
                session.removeAttribute("tempUserName");
                session.removeAttribute("tempPassword");
                session.removeAttribute("tempFullName");

                String successMessage = "OTP verified successfully and user registered!";
                request.setAttribute("successMessage", successMessage);
            } else {
                request.setAttribute("errorMessage", "Failed to register user.");
                request.getRequestDispatcher("views/common/enter-otp.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "OTP verification failed!");
            request.getRequestDispatcher("views/common/enter-otp.jsp").forward(request, response);
        }
    }
}
