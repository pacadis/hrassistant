package hr.model;

import java.io.Serializable;

public class Company implements Serializable {
    private String idCompany;
    private String name;
    private String adress;
    private String describe;

    public Company(String idCompany, String name, String adress, String describe) {
        this.idCompany = idCompany;
        this.name = name;
        this.adress = adress;
        this.describe = describe;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
