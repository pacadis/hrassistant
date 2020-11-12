package hr.model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String idEmployee;
    private String idCompany;
    private String firstName;
    private String lastName;

    public Employee(String idEmployee, String idCompany, String firstName, String lastName) {
        this.idEmployee = idEmployee;
        this.idCompany = idCompany;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
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
}
