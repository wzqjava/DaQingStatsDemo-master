package com.stats.daqing.bean;

/**
 * Created by Administrator on 2017/6/7.
 */

public class ResultBean {


    /**
     * result : 0
     * message : success
     * status : 200
     */

    /** 0：蓝色，1：红色 **/
    private String result;
    private String message;
    private int status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
