// DeleteTimeshareServlet.java

package Controller;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.TimeShareDAO;

@WebServlet("/deleteTimeshare")
public class DeleteTimeshareServlet extends HttpServlet {
    private TimeShareDAO timeShareDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        timeShareDAO = new TimeShareDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int timeShareId = Integer.parseInt(request.getParameter("timeShareId"));

        try {
            timeShareDAO.deleteTimeShare(timeShareId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/views/common/admin.jsp");
    }
}

