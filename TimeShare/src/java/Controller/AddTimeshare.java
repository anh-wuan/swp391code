package Controller;

import Model.User;
import DAO.TimeShareDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addTimeshare")
public class AddTimeshare extends HttpServlet {

    private TimeShareDAO timeShareDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        timeShareDAO = new TimeShareDAO();
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

            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String currentTimeString = sdf.format(new Date());

           
            Timestamp currentTime = Timestamp.valueOf(currentTimeString);

            try {
                
                timeShareDAO.insertTimeShare(name, address, userLogin.getUserId(), startDate, endDate, description, currentTime);
            } catch (Exception e) {
                e.printStackTrace(); 
            }

            response.sendRedirect(request.getContextPath() + "/views/common/addSuccess.jsp");
        }
    }
}
