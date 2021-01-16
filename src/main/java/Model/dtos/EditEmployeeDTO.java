package Model.dtos;

import java.util.Date;

public class EditEmployeeDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cnp;
    private float grossSalary;
    private Date hireDate;
    private String type;
    private String duration;
    private Date expirationDate;

    public EditEmployeeDTO(String username, String password, String firstName, String lastName,
                           String cnp, float grossSalary, Date hireDate, String type, String duration,
                           Date expirationDate) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.grossSalary = grossSalary;
        this.hireDate = hireDate;
        this.type = type;
        this.duration = duration;
        this.expirationDate = expirationDate;
    }

    public EditEmployeeDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
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
