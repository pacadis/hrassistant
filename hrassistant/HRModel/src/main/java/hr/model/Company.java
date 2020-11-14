package hr.model;

import java.io.Serializable;

public class Company extends User<String> implements Serializable {
    private String name;
    private String adress;
    private String description;

    public Company(String name, String adress, String description) {
        this.name = name;
        this.adress = adress;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
