package com.hy.robot.bean;

import java.util.List;

public class ClockBean {

    /**
     * intent : CREATE
     * slots : [{"name":"datetime","value":"8点","normValue":"{\"datetime\":\"T08:00:00\",\"suggestDatetime\":\"2019-09-24T20:00:00\"}"},{"name":"name","value":"reminder"}]
     */

    private String intent;
    private List<SlotsBean> slots;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public List<SlotsBean> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotsBean> slots) {
        this.slots = slots;
    }

    public static class SlotsBean {
        /**
         * name : datetime
         * value : 8点
         * normValue : {"datetime":"T08:00:00","suggestDatetime":"2019-09-24T20:00:00"}
         */

        private String name;
        private String value;
        private String normValue;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getNormValue() {
            return normValue;
        }

        public void setNormValue(String normValue) {
            this.normValue = normValue;
        }
    }
}
