package vn.edu.usth.emailclient;

import javax.mail.Store;

/**
 * Created by duy on 12/4/16.
 */

public class Shared {
    private static Shared instance;
    private String userName = "";
    private String userEmail;
    private String userPassword;
    private Store store;

    public static Shared getInstance(){
        if (instance == null) {
            instance = new Shared();
        }
        return instance;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
