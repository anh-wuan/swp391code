/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Datnt
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public int UserId;
    public String UserName;
    public String Fullname;
    public String Email;    
    public String phone;
    public String password;
    public int RoleID ;
    public String code;
    public boolean IsActive;
    public Date CreateAt;
    public Date UpdateAt;
    public Date DeleteAt;
}
