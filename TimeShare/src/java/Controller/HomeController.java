/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.TimeShareDAO;
import Model.TimeShare;
import java.util.List;
import DAO.AuthenticationDAO;
import Model.Category;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class HomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String indexS = request.getParameter("index");
        if (indexS == null) {
            indexS = "1";
        }
        int index = Integer.parseInt(indexS);
        TimeShareDAO timeshareDAO = new TimeShareDAO();
        List<TimeShare> listHomeStay = timeshareDAO.getAll_HomePage(index);
        int total = timeshareDAO.getTotalHomePage();
        int lastPage = total / 12;
        if (total % 12 != 0) {
            lastPage++;
        }
        request.setAttribute("endP", lastPage);
        request.setAttribute("selectedPage", index);
        session.setAttribute("TIMESHARE", listHomeStay);
        TimeShareDAO tsDAO = new TimeShareDAO();
        AuthenticationDAO auDAO = new AuthenticationDAO();
        List<TimeShare> tsList = tsDAO.getRandomTimeShare(4);
        List<Category> caList = auDAO.getAllCategory();
        request.setAttribute("tsList", tsList);
        request.setAttribute("caList", caList);
        request.getRequestDispatcher("views/common/homepage.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
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

}
