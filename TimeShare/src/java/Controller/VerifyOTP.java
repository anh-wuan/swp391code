package Controller;

import DAO.AuthenticationDAO;
import Model.CreateModel.UserSignUp;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "VerifyOTP", urlPatterns = {"/verify"})
public class VerifyOTP extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String otp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("Email");
        String userName = (String) session.getAttribute("Username");
        String password = (String) session.getAttribute("Password");
        String fullName = (String) session.getAttribute("FullName");

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

                // Set success message and redirect to sign-in page
                session.setAttribute("successMessage", "OTP verified successfully and user registered!");
                response.sendRedirect("views/common/sign-in.jsp");
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
