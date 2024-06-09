package Controller;

import DAO.TimeShareDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AcceptTimeShare")
public class AcceptTimeShare extends HttpServlet {

    private TimeShareDAO timeShareDAO;

    public AcceptTimeShare() {
        this.timeShareDAO = new TimeShareDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int timeShareId = Integer.parseInt(request.getParameter("timeShareId"));

        boolean success;
        try {
            success = timeShareDAO.acceptTimeShare(timeShareId);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Database connection error", e);
        }
        if (success) {
            response.getWriter().write("Timeshare accepted successfully.");
        } else {
            response.getWriter().write("Failed to accept timeshare.");
        }
    }
}
