/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenticationDAO;
import Model.CreateModel.UserSignUp;
import Model.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Datnt
 */
public class AuthenticationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        if (session != null && session.getAttribute("USER") != null) {
            switch (action) {
                case "login":
                    url = "views/common/sign-in.jsp";
                    break;
                case "sign-up":
                    url = "views/common/sign-up.jsp";
                    break;
            }
        } else {
            url = "views/common/sign-in.jsp";
            request.getRequestDispatcher(url).forward(request, response);
        }
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

    private void Login(HttpServletRequest request, HttpServletResponse response) {
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
                } else if(roleName.equals("Admin")) {
                    url = "views/common/admin.jsp";
                }
//                session.setAttribute("USER", userLogedIn);
            } else {
                request.setAttribute("ERRORMESSAGE", "Wrong username or Password! Please try again!");
            }
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            System.out.println("Login method" + e.getMessage());
        }
    }

    private void SignUp(HttpServletRequest request, HttpServletResponse response) {
    
        try {
            String url = "views/common/sign-in.jsp";
            String userName = request.getParameter("username");
            String password = request.getParameter("password");    
            String email = request.getParameter("email");
            
            // create create modal to signup
            UserSignUp userSignUp = new UserSignUp();
            userSignUp.setUserName(userName);
            userSignUp.setPassword(password);
            userSignUp.setEmail(email);
            
            
            HttpSession session = request.getSession(false);
            AuthenticationDAO authDAO = new AuthenticationDAO();
            boolean result = authDAO.SignUp(userSignUp);
            if (result) {
                    url = "views/common/sign-in.jsp";
                    request.setAttribute("SUCCESSMESSAGE", "Register Successfully!");
            } else {
                request.setAttribute("ERRORMESSAGE", "CONFRIM PASSWORD NOT MATCHING");
            }
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            System.out.println("Login method" + e.getMessage());
        }
    }
    
    
}
