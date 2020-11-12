package hr.model;

import java.io.Serializable;

public class Company extends User<String> implements Serializable {
    private String name;
    private String adress;
    private String describe;

    public Company(String name, String adress, String describe) {
        this.name = name;
        this.adress = adress;
        this.describe = describe;
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
