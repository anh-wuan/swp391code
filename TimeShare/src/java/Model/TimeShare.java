package Model;

import java.sql.Timestamp;

public class TimeShare {
    private int TSid;
    private String TSname;
    private String image;
    private String address;
    private int status;
    private int userId;
    private String startDate;
    private String endDate;
    private String description;
    private String createAt;
    private String updateAt;
    private String deleteAt;

    public TimeShare() {
    }

    public TimeShare(int TSid, String TSname, String image, String address, int status, int userId, String startDate, String endDate, String description, String createAt, String updateAt, String deleteAt) {
        this.TSid = TSid;
        this.TSname = TSname;
        this.image = image;
        this.address = address;
        this.status = status;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }
    
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    // Getters and setters
    public String getDeleteAt() {    
        return deleteAt;
    }

    public int getTSid() {
        return TSid;
    }

    public void setTSid(int TSid) {
        this.TSid = TSid;
    }

    public String getTSname() {
        return TSname;
    }

    public void setTSname(String TSname) {
        this.TSname = TSname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
