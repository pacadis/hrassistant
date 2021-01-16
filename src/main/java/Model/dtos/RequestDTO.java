package Model.dtos;

import java.util.Date;

public class RequestDTO {
    private String id;
    private String firstNameEmployee;
    private String lastNameEmployee;
    private String description;
    private String requestStatus;
    private Date date;

    public RequestDTO(String id, String firstNameEmployee, String lastNameEmployee, String description, String requestStatus, Date date) {
        this.id = id;
        this.firstNameEmployee = firstNameEmployee;
        this.lastNameEmployee = lastNameEmployee;
        this.description = description;
        this.requestStatus = requestStatus;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstNameEmployee() {
        return firstNameEmployee;
    }

    public void setFirstNameEmployee(String firstNameEmployee) {
        this.firstNameEmployee = firstNameEmployee;
    }

    public String getLastNameEmployee() {
        return lastNameEmployee;
    }

    public void setLastNameEmployee(String lastNameEmployee) {
        this.lastNameEmployee = lastNameEmployee;
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
