package DAO;

import DAL.DBContext;
import Model.TimeShare;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TimeShareDAO {

    public List<TimeShare> getAllTimeShare() throws ClassNotFoundException {
        List<TimeShare> timeshareList = new ArrayList<>();
        String query = "SELECT TimeShareId, Name, Image, Address, Status, UserId, StartDate, EndDate, Description, CreateAt, UpdateAt, DeleteAt FROM TimeShare";
        try (Connection conn = DBContext.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                TimeShare ts = new TimeShare();
                ts.setTSid(resultSet.getInt("TimeShareId"));
                ts.setTSname(resultSet.getString("Name"));
                ts.setImage(resultSet.getString("Image"));
                ts.setAddress(resultSet.getString("Address"));
                ts.setStatus(resultSet.getInt("Status"));
                ts.setUserId(resultSet.getInt("UserId"));
//                ts.setStartDate(resultSet.getTimestamp("StartDate"));
//                ts.setEndDate(resultSet.getTimestamp("EndDate"));
//                ts.setDescription(resultSet.getString("Description"));
//                ts.setCreateAt(resultSet.getTimestamp("CreateAt"));
//                ts.setUpdateAt(resultSet.getTimestamp("UpdateAt"));
//                ts.setDeleteAt(resultSet.getTimestamp("DeleteAt"));
                timeshareList.add(ts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeshareList;
    }

    public List<TimeShare> getRandomTimeShare(int count) throws ClassNotFoundException {
        List<TimeShare> timeshareList = new ArrayList<>();
        String query = "SELECT TOP (?) * FROM TimeShare ORDER BY NEWID()";
        try (Connection conn = DBContext.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, count);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TimeShare ts = new TimeShare();
                    ts.setTSid(resultSet.getInt("TimeShareId"));
                    ts.setTSname(resultSet.getString("Name"));
                    ts.setImage(resultSet.getString("Image"));
                    ts.setAddress(resultSet.getString("Address"));
                    ts.setStatus(resultSet.getInt("Status"));
                    ts.setUserId(resultSet.getInt("UserId"));
//                    ts.setStartDate(resultSet.getTimestamp("StartDate"));
//                    ts.setEndDate(resultSet.getTimestamp("EndDate"));
//                    ts.setDescription(resultSet.getString("Description"));
//                    ts.setCreateAt(resultSet.getTimestamp("CreateAt"));
//                    ts.setUpdateAt(resultSet.getTimestamp("UpdateAt"));
//                    ts.setDeleteAt(resultSet.getTimestamp("DeleteAt"));
                    timeshareList.add(ts);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeshareList;
    }

    public void insertTimeShare(String name, String address, int userId, String startDate, String endDate, String description, String createAt) throws ClassNotFoundException {
        String sql = "INSERT INTO TimeShare (Name, Address, Status, UserId, StartDate, EndDate, Description, CreateAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, 0);
            ps.setInt(4, userId);
            ps.setTimestamp(5, Timestamp.valueOf(startDate));
            ps.setTimestamp(6, Timestamp.valueOf(endDate));
            ps.setString(7, description);
            ps.setTimestamp(8, Timestamp.valueOf(createAt));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTimeShare(int timeShareId) throws ClassNotFoundException {
        String sql = "UPDATE TimeShare SET DeleteAt = ? WHERE TimeShareId = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.from(Instant.now()));
            ps.setInt(2, timeShareId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTimeShare(int timeShareId, String name, byte[] image, String address, int status, String startDate, String endDate, String description, Timestamp updateAt) throws ClassNotFoundException {
        String sql = "UPDATE TimeShare SET Name=?, Image=?, Address=?, Status=?, StartDate=?, EndDate=?, Description=?, UpdateAt=? WHERE TimeShareId=?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBytes(2, image);
            ps.setString(3, address);
            ps.setInt(4, status);
            ps.setTimestamp(5, Timestamp.valueOf(startDate));
            ps.setTimestamp(6, Timestamp.valueOf(endDate));
            ps.setString(7, description);
            ps.setTimestamp(8, updateAt);
            ps.setInt(9, timeShareId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalHomePage() {
        try {
            String sql = "Select COUNT(*) FROM TimeShare";
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<TimeShare> getAll_HomePage(int index) {
        try {
            List<TimeShare> listTimeShare = new ArrayList<>();
            String sql = "SELECT * FROM TimeShare WHERE Status = 1 ORDER BY TimeshareId OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 12);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TimeShare timeshare = new TimeShare();
                timeshare.setTSid(rs.getInt("TimeShareId"));
                timeshare.setAddress(rs.getString("Address"));
                timeshare.setDescription(rs.getString("Description"));
                timeshare.setTSname(rs.getString("Name"));
                timeshare.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                timeshare.setImage(imageBase64);
                listTimeShare.add(timeshare);
            }
            return listTimeShare;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTimeShare(TimeShare timeshare, Part image) throws ClassNotFoundException, IOException, MessagingException {
        String sql = "INSERT INTO TimeShare (Name, Address, Status, UserId, StartDate, EndDate, Description, CreateAt, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            ps.setString(1, timeshare.getTSname());
            ps.setString(2, timeshare.getAddress());
            // choox nay anh chinh mac dinh la 1 em chinh lai thanh 0 nhe =?> Sau khi admin confrim thi moi chinh lai 1
            ps.setInt(3, 1);
            ps.setInt(4, timeshare.getUserId());
            ps.setString(5, timeshare.getStartDate());
            ps.setObject(6, timeshare.getEndDate());
            ps.setObject(7, timeshare.getDescription());

            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setObject(8, currentDate);
            InputStream fileContent = image.getInputStream();
            ps.setBinaryStream(9, fileContent, (int) image.getSize());

            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean acceptTimeShare(int timeShareId) throws ClassNotFoundException {
        String sql = "UPDATE TimeShare SET Status = ?, UpdateAt = ? WHERE TimeShareId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 1);
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.setInt(3, timeShareId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean declineTimeShare(int timeShareId) throws ClassNotFoundException {
        String sql = "UPDATE TimeShare SET Status = ?, UpdateAt = ? WHERE TimeShareId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 2);
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.setInt(3, timeShareId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
