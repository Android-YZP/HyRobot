package com.hy.robot;
import android.app.Notification;
import android.content.Context;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.com1075.library.base.BaseApplication;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.utils.SharedPreferencesUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.orhanobut.logger.Logger;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class App extends BaseApplication {

    private static Context context;

    private static String LoginToken;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();


        MultiDex.install(this);

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        StringBuffer param = new StringBuffer();
        param.append("appid=" + "596eee1b");
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(App.this, param.toString());



        UMConfigure.setLogEnabled(true);
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(getApplicationContext(), "5d78ae3a3fc1956a2f000251",
                "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "9fb2c819d86f922fa998f66fbf76433f");
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Logger.e("注册成功：deviceToken：-------->  " + deviceToken);
                SharedPreferencesUtils.setParam(getContext(),"deviceToken",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            @Override
            public Notification getNotification(Context context, UMessage msg) {
                //action 101 更新
                //action 102 智能问答列表的更新
                //action 103 解绑
                //action 202 换男性人物 203换女性角色 204换衣服
                //action 301 宠物模块（以后加的）
                for (Map.Entry entry : msg.extra.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    EventBus.getDefault().post(MessageWrap.getInstance2(value+"","order"));
                    Logger.e("自定义数值：-------->  " + "key:" + key + ",value:" + value);
                }
                return super.getNotification(context, msg);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }


    public static Context getContext() {
        return context;
    }
    public static String getLoginToken() {
        return LoginToken;
    }

    public static void setLoginToken(String loginToken) {
        LoginToken = loginToken;
    }
}
