package Model;

import java.util.Date;

public class EmployeeLeave {
    private String usernameEmployee;
    private String type;
    private Date fromDate;
    private Date toDate;

    public EmployeeLeave(String usernameEmployee, String type, Date fromDate, Date toDate) {
        this.usernameEmployee = usernameEmployee;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
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
}
