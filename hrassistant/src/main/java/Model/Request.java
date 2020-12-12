package Model;

import java.util.Date;

public class Request {
    private String id;
    private String usernameEmployee;
    private String usernameCompany;
    private String description;
    private String requestStatus;
    private Date date;

    public Request(String usernameEmployee, String usernameCompany, String description, String requestStatus, Date date) {
        this.usernameEmployee = usernameEmployee;
        this.usernameCompany = usernameCompany;
        this.description = description;
        this.requestStatus = requestStatus;
        this.date = date;
    }

    public Request() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public String getUsernameCompany() {
        return usernameCompany;
    }

    public void setUsernameCompany(String usernameCompany) {
        this.usernameCompany = usernameCompany;
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
}
