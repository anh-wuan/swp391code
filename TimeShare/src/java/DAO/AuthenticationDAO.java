/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import static DAL.DBContext.getConnection;
import Model.CreateModel.UserSignUp;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "SELECT * FROM [users] WHERE [username] = ? AND [password] = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                int userId = rs.getInt("UserID");
                String userName = rs.getString("username");
                String fullName = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int roleId = rs.getInt("RoleID");
                boolean isActive = rs.getBoolean("IsActive");
                user.setUserId(userId);
                user.setEmail(email);
                user.setFullname(fullName);
                user.setPhone(phone);
                user.setIsActive(isActive);
                user.setRoleID(roleId);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return user;

    }
    
    public String GetUserRole(int roleId) {
         String sql = "SELECT * FROM [roles] WHERE [RoleId] = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Teen cot Role name
                return rs.getString("role_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return null;
    }

    public boolean SignUp(UserSignUp userSignUp) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, userSignUp.getUserName());
            ps.setString(2, userSignUp.getPassword());       
            ps.setString(3, userSignUp.getEmail());

            int affectedRow = ps.executeUpdate(sql);
            if (affectedRow > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return false;
    };
}

    
