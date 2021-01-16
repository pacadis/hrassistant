package Model.dtos;

import java.util.Date;

public class SaveRequestDTO {

    private String usernameEmployee;
    private String description;
    private String requestStatus = "PENDING";
    private Date date;
    private String type;
    private Date fromDate;
    private Date toDate;
    private String proxyName;

    public SaveRequestDTO() {
    }

    public SaveRequestDTO(String usernameEmployee, String description, String requestStatus, Date date, String type, Date fromDate, Date toDate, String proxyName) {
        this.usernameEmployee = usernameEmployee;
        this.description = description;
        this.requestStatus = requestStatus;
        this.date = date;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.proxyName = proxyName;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }
}
