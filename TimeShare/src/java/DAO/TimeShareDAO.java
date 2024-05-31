 package DAO;

import DAL.DBContext;
import static DAL.DBContext.connection;
import static DAL.DBContext.getConnection;
import Model.TimeShare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeShareDAO {

    public List<TimeShare> getAllTimeShare() throws ClassNotFoundException {
        List<TimeShare> timeshareList = new ArrayList<>();
        String query = "SELECT TimeShareId, Name, Image, Address, Status, UserId, StartDate, EndDate, Description, CreateAt, UpdateAt, DeleteAt FROM TimeShare";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                TimeShare ts = new TimeShare();
                ts.setTSid(resultSet.getInt("TimeShareId"));
                ts.setTSname(resultSet.getString("Name"));
                ts.setImage(resultSet.getString("Image"));
                ts.setAddress(resultSet.getString("Address"));
                ts.setStatus(resultSet.getInt("Status"));
                ts.setUserId(resultSet.getInt("UserId"));
                ts.setStartDate(resultSet.getTimestamp("StartDate"));
                ts.setEndDate(resultSet.getTimestamp("EndDate"));
                ts.setDescription(resultSet.getString("Description"));
                ts.setCreateAt(resultSet.getTimestamp("CreateAt"));
                ts.setUpdateAt(resultSet.getTimestamp("UpdateAt"));
                ts.setDeleteAt(resultSet.getTimestamp("DeleteAt"));
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
        try (Connection conn = DBContext.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
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
                    ts.setStartDate(resultSet.getTimestamp("StartDate"));
                    ts.setEndDate(resultSet.getTimestamp("EndDate"));
                    ts.setDescription(resultSet.getString("Description"));
                    ts.setCreateAt(resultSet.getTimestamp("CreateAt"));
                    ts.setUpdateAt(resultSet.getTimestamp("UpdateAt"));
                    ts.setDeleteAt(resultSet.getTimestamp("DeleteAt"));
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
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.from(Instant.now()));
            ps.setInt(2, timeShareId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTimeShare(int timeShareId, String name, byte[] image, String address, int status, String startDate, String endDate, String description, Timestamp updateAt) throws ClassNotFoundException {
        String sql = "UPDATE TimeShare SET Name=?, Image=?, Address=?, Status=?, StartDate=?, EndDate=?, Description=?, UpdateAt=? WHERE TimeShareId=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
}