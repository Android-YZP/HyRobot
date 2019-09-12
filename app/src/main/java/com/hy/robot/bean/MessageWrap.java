package com.hy.robot.bean;

public class MessageWrap {

    public  String message;

    public static MessageWrap getInstance(String message) {
        return new MessageWrap(message);
    }

    MessageWrap(String message) {
        this.message = message;
    }
}