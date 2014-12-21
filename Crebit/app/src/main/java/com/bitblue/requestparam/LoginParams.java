package com.bitblue.requestparam;

import java.io.Serializable;

public class LoginParams implements Serializable {
    public static final long serialVersionUID=1L;
    private String Mobile;
    private String Pass;
    private String Version;

    public LoginParams() {
    }

    public String getPass() {
        return Pass;
    }

    public String getMobile() {
        return Mobile;
    }


    public String getVersion() {
        return Version;
    }

    public LoginParams(String mobile, String pass, String version) {
        Mobile = mobile;
        Pass = pass;
        Version = version;
    }
}
