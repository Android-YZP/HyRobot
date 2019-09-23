package com.hy.robot.bean;

public class LoginBean {

    /**
     * code : 0
     * msg : success
     * data : {"expire":2592000,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MyIsImlhdCI6MTU2OTIxNDExOSwiZXhwIjoxNTcxODA2MTE5fQ.m8A43hgjX1XY-4TfiVcVtAY4VN6huYDgfQNSqLMeqxV6i9YyqBw7drY5CVrO_kpZBxZMpo63-zVYVmrZbEdi2Q"}
     * page : null
     */

    private int code;
    private String msg;
    private DataBean data;
    private Object page;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public static class DataBean {
        /**
         * expire : 2592000
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MyIsImlhdCI6MTU2OTIxNDExOSwiZXhwIjoxNTcxODA2MTE5fQ.m8A43hgjX1XY-4TfiVcVtAY4VN6huYDgfQNSqLMeqxV6i9YyqBw7drY5CVrO_kpZBxZMpo63-zVYVmrZbEdi2Q
         */

        private int expire;
        private String token;

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
