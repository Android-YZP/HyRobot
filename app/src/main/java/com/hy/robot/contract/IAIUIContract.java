package com.hy.robot.contract;

public interface IAIUIContract {
    //进入识别状态
    void eventWeakup(int arg1);
    //结果返回事件
    void eventResult(String s);


    //上传动态实体成功
    void uploadContactsSuccess();



     //语音播报开始和结束
    void onSpeakBegin();
    void onSpeakCompleted();


    //错误事件
    void error(String e);





}
