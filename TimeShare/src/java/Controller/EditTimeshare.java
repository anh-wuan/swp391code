package Controller;

import DAO.TimeShareDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editTimeshare")
public class EditTimeshare extends HttpServlet {

    private TimeShareDAO timeShareDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        timeShareDAO = new TimeShareDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int timeShareId = Integer.parseInt(request.getParameter("timeShareId"));
        String name = request.getParameter("name");
        byte[] image = null;
        String address = request.getParameter("address");
        int status = Integer.parseInt(request.getParameter("status"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String description = request.getParameter("description");

        Timestamp updateAt = Timestamp.from(Instant.now());

        try {
            timeShareDAO.updateTimeShare(timeShareId, name, image, address, status, startDate, endDate, description, updateAt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/views/common/admin.jsp");
    }
}
