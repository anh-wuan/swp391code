package Controller;

import Model.User;
import DAO.TimeShareDAO;
import Model.TimeShare;
import jakarta.mail.MessagingException;
import jakarta.mail.Part;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/addTimeshare")
@MultipartConfig
public class AddTimeshare extends HttpServlet {

    private TimeShareDAO timeShareDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        timeShareDAO = new TimeShareDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                url = "views/user/post-homestay.jsp";
            } else {
                url = "views/common/sign-in.jsp";
            }
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User userLogin = (User) session.getAttribute("USER");

            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String startDate = request.getParameter("start_date");
            String endDate = request.getParameter("end_date");
            String description = request.getParameter("description");
            jakarta.servlet.http.Part mainImage = request.getPart("image");

            TimeShare timeshare = new TimeShare();
            timeshare.setAddress(address);
            timeshare.setTSname(name);
            timeshare.setDescription(description);
            timeshare.setStartDate(startDate);
            timeshare.setEndDate(endDate);
            timeshare.setUserId(userLogin.getUserId());

            TimeShareDAO timeshareDAO = new TimeShareDAO();
            try {
                boolean result = timeshareDAO.addTimeShare(timeshare, mainImage);
                if (result) {
                    request.setAttribute("MESSAGE", "Tạo timeshare thành công");
                    request.getRequestDispatcher("views/user/post-homestay.jsp").forward(request, response);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddTimeshare.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(AddTimeshare.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
