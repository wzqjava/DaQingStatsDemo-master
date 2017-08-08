package com.stats.daqing.bean;

/**
 * Created by Administrator on 2017/7/21.
 */

public class LoginResultBean {


    /**
     * message : 登录成功
     * user : {"createTime":1500837289000,"email":"123@qq.com","id":11,"passWord":"123456","phoneNum":"18667119152","userName":"哈"}
     * status : 200
     */

    private String message;
    private UserBean user;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class UserBean {
        /**
         * createTime : 1500837289000
         * email : 123@qq.com
         * id : 11
         * passWord : 123456
         * phoneNum : 18667119152
         * userName : 哈
         */

        private long createTime;
        private String email;
        private int id;
        private String passWord;
        private String phoneNum;
        private String userName;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
