package Model;

import java.util.Date;

public class Contract {
    private String usernameEmployee;
    private float grossSalary;
    private Date hireDate;
    private String type;
    private String duration;
    private Date expirationDate;

    public Contract(String usernameEmployee, float grossSalary, Date hireDate, String type,
                    String duration, Date expirationDate) {
        this.usernameEmployee = usernameEmployee;
        this.grossSalary = grossSalary;
        this.hireDate = hireDate;
        this.type = type;
        this.duration = duration;
        this.expirationDate = expirationDate;
    }

    public Contract() {

    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
