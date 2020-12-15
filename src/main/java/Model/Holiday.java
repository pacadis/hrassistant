package Model;

import java.util.Date;

public class Holiday {
    private String usernameEmployee;
    private String idRequest;
    private int daysOff;
    private String type;
    private Date fromDate;
    private Date toDate;
    private String proxyUsername;

    public Holiday(String usernameEmployee, int daysOff, String type, Date fromDate, Date toDate, String proxyUsername) {
        this.usernameEmployee = usernameEmployee;
        this.daysOff = daysOff;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.proxyUsername = proxyUsername;
    }

    public Holiday() {

    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
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
