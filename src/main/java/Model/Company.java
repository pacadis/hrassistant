package Model;

import java.io.Serializable;

public class Company implements Serializable {
    private String id;
    private String username;
    private String password;
    private String name;
    private String address;

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Company() {
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
