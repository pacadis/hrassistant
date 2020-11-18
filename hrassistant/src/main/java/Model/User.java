package Model;


public interface User {
    String id = null;
    String username = null;
    String password = null;

    String getId();

    void setId(String id);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);
}