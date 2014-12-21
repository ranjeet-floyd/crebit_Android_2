package com.bitblue.crebit.loginpage.login;

public class LoginCredentials {
    private String MobileNumber;
    private String Password;

    public LoginCredentials(String mobilenumber, String password) {
        MobileNumber = mobilenumber;
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

}
