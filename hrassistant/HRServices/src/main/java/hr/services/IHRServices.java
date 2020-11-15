package hr.services;

public interface IHRServices {
    void login(String username, String password, IHRObserver client) throws HRException;
}
