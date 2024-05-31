/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Category;
import Model.CreateModel.UserSignUp;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthenticationDAO extends DBContext {

    private Connection con;
    private List<User> user;
    PreparedStatement ps;
    ResultSet rs;

    public AuthenticationDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User Login(String username, String password) {
        String sql = "SELECT * FROM [User] WHERE [Username] = ? AND [Password] = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                int userId = rs.getInt("UserID");
                String UserName = rs.getString("Username");
                String FullName = rs.getString("Fullname");
                String Email = rs.getString("Email");
                String Phone = rs.getString("Phone");
                int RoleId = rs.getInt("RoleId");
                boolean isActive = rs.getBoolean("IsActive");
                user.setUserId(userId);
                user.setEmail(Email);
                user.setFullname(FullName);
                user.setPhone(Phone);
                user.setIsActive(isActive);
                user.setRoleID(RoleId);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return user;

    }

    public String GetUserRole(int roleId) {
        String sql = "SELECT * FROM [Role] WHERE [RoleId] = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Teen cot Role name
                return rs.getString("RoleName");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return null;
    }

    public boolean SignUp(UserSignUp userSignUp) {
        // User Content ,...
        String sql = "INSERT INTO [User] (IsConfirmEmail, Username, Password, Email, RoleId, CreateAt, IsActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setString(2, userSignUp.getUserName());
            ps.setString(3, userSignUp.getPassword());
            ps.setString(4, userSignUp.getEmail());
            // role default is 1 - User
            ps.setInt(5, 1);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setObject(6, currentDate);   
            ps.setBoolean(7, false);

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return false;
    }

    ;

    public User readUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM [User] WHERE UserId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("readUserById: " + e.getMessage());
        }

        return user;
    }

    public User readUserByUsername(String username) {
        User user = null;

        String sql = "SELECT * FROM User WHERE Username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("readUserByUsername: " + e.getMessage());
        }
        return user;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("UserID"));
        user.setRoleID(resultSet.getInt("Role"));
        user.setPassword(resultSet.getString("Password"));
        user.setFullname(resultSet.getString("FullName"));
        user.setEmail(resultSet.getString("Email"));
        user.setPhone(resultSet.getString("Phone"));
        user.setCreateAt(resultSet.getDate(""));
        user.setUpdateAt(resultSet.getDate(""));
        user.setDeleteAt(resultSet.getDate(""));
        return user;
    }

    public void updateUser(User user) {
        String sql = "UPDATE [User] SET RoleId = ?, Password = ?, Fullname = ?, "
                + "Email = ?, Phone = ? WHERE UserId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getRoleID());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFullname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());

            preparedStatement.setInt(8, user.getUserId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("updateUser: " + e.getMessage());
        }
    }
    public List<Category> getAllCategory() { 
    List<Category> list = new ArrayList<>(); 
    String query = "SELECT * FROM Category"; 
 
    try (PreparedStatement ps = con.prepareStatement(query); 
         ResultSet rs = ps.executeQuery()) { 
        while (rs.next()) { 
            int categoryId = rs.getInt("CategoryId"); 
            String categoryName = rs.getString("CategoryName"); 
            list.add(new Category(categoryId, categoryName)); 
        } 
    } catch (SQLException e) { 
        // Log the exception and handle it appropriately 
        e.printStackTrace(); 
    } 
 
    return list; 
}


}
