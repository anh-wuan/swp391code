package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.AuthenticationDAO;
import Model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ProfileUser", urlPatterns = {"/profile"})
public class ProfileUser extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User userLogin = (User) session.getAttribute("USER");
        if (userLogin == null) {
            response.sendRedirect("views/common/sign-in.jsp");
            return;
        }
//        String email = ((User) request.getSession().getAttribute("USER")).getEmail();
//        User user = new AuthenticationDAO().readUserByUsername(email);
        request.setAttribute("users", userLogin);
        request.getRequestDispatcher("views/common/profile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User userLogin = (User) request.getSession().getAttribute("USER");

        AuthenticationDAO userDAO = new AuthenticationDAO();
//        User user = userDAO.readUserByUsername(email);
        User user = new User();
        user.setUserId(userLogin.getUserId());
        // Update user details based on the form submission
        user.setFullname(request.getParameter("fullName"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));

        new AuthenticationDAO().updateUser(user);
        System.out.println(user);
        // Redirect to the profile page after the update
        response.sendRedirect("profile?success");
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

}