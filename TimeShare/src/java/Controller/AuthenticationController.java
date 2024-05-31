/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenticationDAO;
import Model.CreateModel.UserSignUp;
import Model.User;
import Service.MailService;
import Service.OtpService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Datnt
 */
public class AuthenticationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "views/common/sign-in.jsp";
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        switch (action) {
            case "login":
                url = "views/common/sign-in.jsp";
                break;
            case "sign-up":
                url = "views/common/sign-up.jsp";
                break;
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "login":
                Login(request, response);
                break;
            // auth?action=sign-up
            case "sign-up":
                SignUp(request, response);
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void Login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "views/common/sign-in.jsp";
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            HttpSession session = request.getSession(false);

            AuthenticationDAO authDAO = new AuthenticationDAO();
            User userLogedIn = authDAO.Login(userName, password);
            if (userLogedIn != null) {
                String roleName = authDAO.GetUserRole(userLogedIn.RoleID);
                if (roleName.equals("User")) {
                    url = "views/common/homepage.jsp";
                } else if (roleName.equals("Admin")) {
                    url = "views/common/admin.jsp";
                }
                session.setAttribute("USER", userLogedIn);
            } else {
                request.setAttribute("ERRORMESSAGE", "Wrong username or Password! Please try again!");
            }
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            System.out.println("Login method" + e.getMessage());
        }
    }

    public void SignUp(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();

            String url = "views/common/sign-in.jsp";
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");

            // Store user information in session temporarily
            session.setAttribute("tempUserName", userName);
            session.setAttribute("tempPassword", password);
            session.setAttribute("tempEmail", email);
            session.setAttribute("tempFullName", fullname);

            // Implement OtpService to get OTP code
            OtpService otpService = new OtpService();
            String otp = OtpService.genarateOtp();

            session.setAttribute("otp", otp);

            // Send OTP to user's email
            MailService mailService = new MailService();
            mailService.sendMail(email, otp);

            // Redirect to OTP verification page
            url = "views/common/enter-otp.jsp";
            request.setAttribute("SUCCESSMESSAGE", "Register Successfully! Please enter the OTP sent to your email.");
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            System.out.println("SignUp method" + e.getMessage());
        }
    }
}
