package com.stats.daqing.bean;

/**
 * app升级
 * Created by Administrator on 2017/6/6.
 */

public class AppVersionBean {


    /**
     * downAppUrl : http://202.97.194.240:9080/upload/app/2.apk
     * updateMessage : testUpdateMessage
     * versionCode : 2
     * versionName : testVersionName
     */

    private String downAppUrl;
    private String updateMessage;
    private String versionCode;
    private String versionName;

    public String getDownAppUrl() {
        return downAppUrl;
    }

    public void setDownAppUrl(String downAppUrl) {
        this.downAppUrl = downAppUrl;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public String toString() {
        return "AppVersionBean{" +
                "downAppUrl='" + downAppUrl + '\'' +
                ", updateMessage='" + updateMessage + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}
