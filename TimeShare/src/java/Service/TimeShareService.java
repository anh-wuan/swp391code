/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Service;

import DAO.TimeShareDAO;
import java.sql.Timestamp;
import java.util.Date;

public class TimeShareService {
    private final TimeShareDAO timeShareDAO;

    public TimeShareService() {
        this.timeShareDAO = new TimeShareDAO();
    }

    public void createNewTimeShare(String name, String address, int userId, String startDate, String endDate, String description) throws ClassNotFoundException {
        Date currentDate = new Date();
        timeShareDAO.insertTimeShare(name, address, userId, startDate, endDate, description, (Timestamp) currentDate);
    }
}

