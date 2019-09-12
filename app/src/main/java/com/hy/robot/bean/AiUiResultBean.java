package com.hy.robot.bean;

public class AiUiResultBean {








    /**
     * intent : {"answer":"","category":"IFLYTEK.weather","data":"","dialog_stat":"DataValid","rc":0,"save_history":true,"semantic":"","service":"weather","state":"","text":"今天的天气。","used_state":"","uuid":"cid7364210b@dx000b10d522b4010002","version":"81.0","sid":"cid7364210b@dx000b10d522b4010002"}
     */

    private IntentBean intent;
    /**
     * answer : {"text":"请听笑话【米其林牌橡胶鞋】，学校招聘会上，米其林（就是做轮胎的）的一道笔试题说：为什么鸟站在高压线上不会触电？我寝室一同学回答：因为它穿着米其林牌橡胶鞋！结果他是全校唯一被录用的本科生","type":"T"}
     */



    public IntentBean getIntent() {
        return intent;
    }

    public void setIntent(IntentBean intent) {
        this.intent = intent;
    }


    public static class IntentBean {
        /**
         * answer :
         * category : IFLYTEK.weather
         * data :
         * dialog_stat : DataValid
         * rc : 0
         * save_history : true
         * semantic :
         * service : weather
         * state :
         * text : 今天的天气。
         * used_state :
         * uuid : cid7364210b@dx000b10d522b4010002
         * version : 81.0
         * sid : cid7364210b@dx000b10d522b4010002
         */

        private AnswerBean answer;
        private String category;
        private Object data;
        private String dialog_stat;
        private int rc;
        private boolean save_history;
        private Object semantic;
        private String service;
        private Object state;
        private String text;
        private Object used_state;
        private String uuid;
        private String version;
        private String sid;

        public AnswerBean getAnswer() {
            return answer;
        }

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getDialog_stat() {
            return dialog_stat;
        }

        public void setDialog_stat(String dialog_stat) {
            this.dialog_stat = dialog_stat;
        }

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public boolean isSave_history() {
            return save_history;
        }

        public void setSave_history(boolean save_history) {
            this.save_history = save_history;
        }

        public  Object getSemantic() {
            return semantic;
        }

        public void setSemantic(Object semantic) {
            this.semantic = semantic;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getUsed_state() {
            return used_state;
        }

        public void setUsed_state(Object used_state) {
            this.used_state = used_state;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }

    public static class AnswerBean {
        /**
         * text : 请听笑话【米其林牌橡胶鞋】，学校招聘会上，米其林（就是做轮胎的）的一道笔试题说：为什么鸟站在高压线上不会触电？我寝室一同学回答：因为它穿着米其林牌橡胶鞋！结果他是全校唯一被录用的本科生
         * type : T
         */

        private String text;
        private String type;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
