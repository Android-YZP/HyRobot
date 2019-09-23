package com.hy.robot.bean;

public class MessageWrap {

    public  String message;
    public  String type;

    public static MessageWrap getInstance(String message) {
        return new MessageWrap(message);
    }

    public static MessageWrap getInstance2(String message,String type) {
        return new MessageWrap(message,type);
    }

    MessageWrap(String message) {
        this.message = message;
    }

    MessageWrap(String message,String type) {
        this.type = type;
        this.message = message;
    }
}