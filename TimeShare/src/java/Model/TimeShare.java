package Model;

import java.sql.Timestamp;

public class TimeShare {
    private int TSid;
    private String TSname;
    private String image;
    private String address;
    private int status;
    private int userId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private Timestamp createAt;
    private Timestamp updateAt;
    private Timestamp deleteAt;

    // Getters and setters
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Timestamp getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Timestamp deleteAt) {
        this.deleteAt = deleteAt;
    }
}
