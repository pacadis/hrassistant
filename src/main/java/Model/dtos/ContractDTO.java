package Model.dtos;

import java.util.Date;

public class ContractDTO {
    private String firstName;
    private String lastName;
    private float grossSalary;
    private Date hireDate;
    private String type;
    private String duration;
    private Date expirationDate;
    private String cnp;

    public ContractDTO(String firstName, String lastName, float grossSalary, Date hireDate,
                       String type, String duration, Date expirationDate, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grossSalary = grossSalary;
        this.hireDate = hireDate;
        this.type = type;
        this.duration = duration;
        this.expirationDate = expirationDate;
        this.cnp = cnp;
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

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
