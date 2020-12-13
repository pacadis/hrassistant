package Model.dtos;

import java.util.Date;

public class EmployeeLeaveDTO {
    private String type;
    private Date fromDate;
    private Date toDate;
    private String proxyName;

    public EmployeeLeaveDTO(String type, Date fromDate, Date toDate, String proxyName) {
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.proxyName = proxyName;
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
