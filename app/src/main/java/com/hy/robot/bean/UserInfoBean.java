package com.hy.robot.bean;

public class UserInfoBean {

    /**
     * msg : success
     * code : 0
     * data : {"user":{"userId":53,"mobile":"17625017026","username":"17625017026","password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","createTime":"2019-09-23 23:03:31","status":1,"avator":"http://videoapp-1253520711.coscd.myqcloud.com/headimg/head2.png","wxId":null,"nickname":null,"sex":null,"height":null,"weight":null,"xw":null,"yw":null,"tw":null,"avator3d":null,"device":"f44dd14af2fd4d71","version":"11","notice":"1","deviceToken":null,"mobileToken":"Ag2NrpwvJiApPZ4luVeId5vUbB23wabsjOOTlytU0WKc","flag":0}}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user : {"userId":53,"mobile":"17625017026","username":"17625017026","password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","createTime":"2019-09-23 23:03:31","status":1,"avator":"http://videoapp-1253520711.coscd.myqcloud.com/headimg/head2.png","wxId":null,"nickname":null,"sex":null,"height":null,"weight":null,"xw":null,"yw":null,"tw":null,"avator3d":null,"device":"f44dd14af2fd4d71","version":"11","notice":"1","deviceToken":null,"mobileToken":"Ag2NrpwvJiApPZ4luVeId5vUbB23wabsjOOTlytU0WKc","flag":0}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * userId : 53
             * mobile : 17625017026
             * username : 17625017026
             * password : 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
             * createTime : 2019-09-23 23:03:31
             * status : 1
             * avator : http://videoapp-1253520711.coscd.myqcloud.com/headimg/head2.png
             * wxId : null
             * nickname : null
             * sex : null
             * height : null
             * weight : null
             * xw : null
             * yw : null
             * tw : null
             * avator3d : null
             * device : f44dd14af2fd4d71
             * version : 11
             * notice : 1
             * deviceToken : null
             * mobileToken : Ag2NrpwvJiApPZ4luVeId5vUbB23wabsjOOTlytU0WKc
             * flag : 0
             */

            private int userId;
            private String mobile;
            private String username;
            private String password;
            private String createTime;
            private int status;
            private String avator;
            private Object wxId;
            private Object nickname;
            private Object sex;
            private Object height;
            private Object weight;
            private Object xw;
            private Object yw;
            private Object tw;
            private Object avator3d;
            private String device;
            private String version;
            private String notice;
            private Object deviceToken;
            private String mobileToken;
            private int flag;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getAvator() {
                return avator;
            }

            public void setAvator(String avator) {
                this.avator = avator;
            }

            public Object getWxId() {
                return wxId;
            }

            public void setWxId(Object wxId) {
                this.wxId = wxId;
            }

            public Object getNickname() {
                return nickname;
            }

            public void setNickname(Object nickname) {
                this.nickname = nickname;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getXw() {
                return xw;
            }

            public void setXw(Object xw) {
                this.xw = xw;
            }

            public Object getYw() {
                return yw;
            }

            public void setYw(Object yw) {
                this.yw = yw;
            }

            public Object getTw() {
                return tw;
            }

            public void setTw(Object tw) {
                this.tw = tw;
            }

            public Object getAvator3d() {
                return avator3d;
            }

            public void setAvator3d(Object avator3d) {
                this.avator3d = avator3d;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public Object getDeviceToken() {
                return deviceToken;
            }

            public void setDeviceToken(Object deviceToken) {
                this.deviceToken = deviceToken;
            }

            public String getMobileToken() {
                return mobileToken;
            }

            public void setMobileToken(String mobileToken) {
                this.mobileToken = mobileToken;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }
        }
    }
}
