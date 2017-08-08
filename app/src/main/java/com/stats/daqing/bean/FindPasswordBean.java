package com.stats.daqing.bean;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FindPasswordBean {


    /**
     * password : 123456789
     * message : 找回成功
     * status : 200
     */

    private String password;
    private String message;
    private int status;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
