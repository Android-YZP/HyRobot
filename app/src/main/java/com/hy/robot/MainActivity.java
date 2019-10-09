package com.hy.robot;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.com1075.library.base.BaseActivity;
import com.google.gson.Gson;
import com.hy.robot.activitys.MusicActivity;
import com.hy.robot.activitys.QRCodeActivity;
import com.hy.robot.activitys.TrieyeNewsActivity;
import com.hy.robot.activitys.VideoActivity;
import com.hy.robot.bean.AiUiResultBean;
import com.hy.robot.bean.ClockBean;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.bean.PushBean;
import com.hy.robot.bean.TimeBean;
import com.hy.robot.contract.IAIUIContract;
import com.hy.robot.presenter.AIUIPresenter;
import com.hy.robot.utils.CalendarReminderUtils;
import com.hy.robot.utils.SwitchBGUtils;
import com.hy.robot.utils.UIUtils;
import com.hy.robot.utils.WIFIConnectionManager;
import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends BaseActivity implements IAIUIContract {
    /**
     * 获取闲泡视频
     * 获取个人信息
     * 获取自定义问答
     * 修改用户信息 token
     * 获取升级
     * 获取三眼咨询
     * 音频 视频  IM 每个单独一个类去解决
     * <p>
     * <p>
     * 闲泡自定义技能   闹钟   推送对接
     * <p>
     * 自定义的二个技能  先跑视频  ，  跳舞
     * <p>
     * 对接IM通讯录 IM发消息
     */

    private AIUIPresenter aiuiPresenter = new AIUIPresenter(this, this);
    private ImageView mImageView;

    @Override
    protected int getContentViewId() {
        PushAgent.getInstance(this).onAppStart();
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        aiuiPresenter.exit();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void initView() {
        mImageView = findViewById(R.id.im_bg);
    }

    @Override
    protected void initData() {
        requestPermissions();
        aiuiPresenter.startAIUI();
        aiuiPresenter.startSpeaker();
        Glide.with(App.getContext()).load(R.mipmap.zhuanqq).into(mImageView);
    }

    @Override
    protected void setListener() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiuiPresenter.upLoad();
            }
        });
    }


    @Override
    public void eventWeakup(int arg1) {

    }

    @Override
    public void eventResult(String s) {
        Logger.json(s);
        try {
            AiUiResultBean aiUiResultBean = new Gson().fromJson(s, AiUiResultBean.class);

            Logger.e(new Gson().toJson(aiUiResultBean.getIntent().getData()) + "================");


            UIUtils.showTip(aiUiResultBean.getIntent().getService());

            SwitchBGUtils.getInstance(mImageView).switchBg(aiUiResultBean.getIntent().getService() + "");

//            if (aiUiResultBean.getIntent().getAnswer().getText().equals("闲炮视频")) {
//                startActivity(new Intent(MainActivity.this, VideoActivity.class));
//                return;
//            }

            aiUiResult(aiUiResultBean.getIntent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {

        // 手机端指令
//        Logger.e(event.message);
//        if (event.message.equals("stop")) {//音视频停止播放
//            aiuiPresenter.aiUiOn();
//        } else if (event.message.equals("play")) {//音视频开始播放
//            aiuiPresenter.aiUiOff();
//        }


        if (event.type.equals("read")) {//阅读指定内容
            aiuiPresenter.speachText(event.message);
        } else if (event.type.equals("Action")) {//推送过来的消息
            PushBean pushBean = new Gson().fromJson(event.message, PushBean.class);

            if (pushBean.getAction().equals("avator3d")) {
                EventBus.getDefault().post(MessageWrap.getInstance2("语音激活", "status"));
                SwitchBGUtils.getInstance(mImageView).switchBg("joke");
                EventBus.getDefault().post(MessageWrap.getInstance2("您好;初次见面;请多多指教", "read"));

            } else if (pushBean.getAction().equals("levelup")) {

                Intent i = new Intent("com.hy.robot.USER_ACTION");
                i.putExtra("downloadUrl", "com.hy.robot.USER_ACTION");
                sendBroadcast(i);
                EventBus.getDefault().post(MessageWrap.getInstance2("我要去升级去了;失陪一会，马上就回来奥！！！", "read"));


            } else if (pushBean.getAction().equals("unbind")) {
                EventBus.getDefault().post(MessageWrap.getInstance2("与设备解绑成功，再见", "read"));
                WIFIConnectionManager.getInstance(MainActivity.this).closeWifi();

            }
        }
    }


    @Override
    public void uploadContactsSuccess() {

    }

    @Override
    public void onSpeakBegin() {

    }

    @Override
    public void onSpeakCompleted() {

    }

    @Override
    public void error(String e) {

    }


    //还差二个自定义技能  跳舞和看个视频
    private void aiUiResult(AiUiResultBean.IntentBean result) {
        //计时器清空，重新设置时间

        switch (result.getService()) {
            case "drama": //戏曲
            case "crossTalk": //相声
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                intent.putExtra("data", new Gson().toJson(result.getData()));
                intent.putExtra("type", result.getService());
                startActivity(intent);
                break;
            case "OS2451892976.XianPaoVideo": //闲泡视频
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
                break;
            case "message": //飞鸽短信

                aiuiPresenter.speachText(result.getAnswer().getText());
                String jsonclockBeanMessage = new Gson().toJson(result.getSemantic());
                String substringclockBeanMessage = jsonclockBeanMessage.substring(1, jsonclockBeanMessage.length() - 1);
                Logger.e(substringclockBeanMessage);
                String contentMessage = "";
                String nameMessage = "";
                ClockBean clockBeanMessage = new Gson().fromJson(substringclockBeanMessage, ClockBean.class);
                for (int i = 0; i < clockBeanMessage.getSlots().size(); i++) {
                    if (clockBeanMessage.getSlots().get(i).getName().equals("content")) {
                        contentMessage = clockBeanMessage.getSlots().get(i).getValue();
                        Logger.e(clockBeanMessage.getSlots().get(i).getValue());

                    } else if (clockBeanMessage.getSlots().get(i).getName().equals("name")) {
                        nameMessage = clockBeanMessage.getSlots().get(i).getValue();
                        Logger.e(clockBeanMessage.getSlots().get(i).getValue());
                    }
                }
                if (result.getAnswer().getText().contains("已发送")  || result.getAnswer().getText().contains("抱歉")) {
                    Logger.e("aiUiReset");
                    aiuiPresenter.aiUiReset();

                } else if (result.getAnswer().getText().contains("请问您想发给谁") ) {
                    aiuiPresenter.speachText("请对我说发送信息给某某某");
                    aiuiPresenter.aiUiReset();
                }
                break;
            case "飞鸽通话": //飞鸽通话

                break;
            case "news": //三眼蛙资讯
                startActivity(new Intent(MainActivity.this, TrieyeNewsActivity.class));
                break;
            case "OS2451892976.dance": //跳舞
                aiuiPresenter.speachText("你看看我跳的好不好看，嘻嘻");
                SwitchBGUtils.getInstance(mImageView).switchBg("joke");
                break;

            case "待机": //待机
                break;
            case "长时间待机": //长时间待机
                break;
            case "更换角色": //更换角色

                break;
            case "scheduleX": //闹钟提醒

                aiuiPresenter.speachText(result.getAnswer().getText());
                String json = new Gson().toJson(result.getSemantic());
                String substring = json.substring(1, json.length() - 1);
                Logger.e(substring);
                String content = "";
                String datetime = "";
                String repeat = "";
                String name = "";
                ClockBean clockBean = new Gson().fromJson(substring, ClockBean.class);
                for (int i = 0; i < clockBean.getSlots().size(); i++) {
                    if (clockBean.getSlots().get(i).getName().equals("datetime")) {
                        TimeBean timeBean = new Gson().fromJson(clockBean.getSlots().get(i).getNormValue(), TimeBean.class);
                        datetime = timeBean.getSuggestDatetime();
                        Logger.e(timeBean.getSuggestDatetime());

                    } else if (clockBean.getSlots().get(i).getName().equals("content")) {
                        content = clockBean.getSlots().get(i).getValue();
                        Logger.e(clockBean.getSlots().get(i).getValue());

                    } else if (clockBean.getSlots().get(i).getName().equals("repeat")) {
                        repeat = clockBean.getSlots().get(i).getValue();
                        Logger.e(clockBean.getSlots().get(i).getValue());
                    } else if (clockBean.getSlots().get(i).getName().equals("name")) {
                        name = clockBean.getSlots().get(i).getValue();
                        Logger.e(clockBean.getSlots().get(i).getValue());
                    }
                }
                if (StringUtils.isEmpty(datetime)) return;
                if (name.equals("reminder")) {
                    CalendarReminderUtils.addCalendarEvent(MainActivity.this,
                            content + "",
                            "小飞为你提醒",
                            datetime,
                            10, repeat + "");
                } else {
                    CalendarReminderUtils.createAlarm(MainActivity.this, repeat, content, datetime, (int) System.currentTimeMillis());
                }

                break;
            default:
                aiuiPresenter.speachText(result.getAnswer().getText());
                break;
        }

    }


    private void daiji(int time) {
        if (time == 20) {//c长时间待机

        } else {//待机

        }
    }


    private void requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS,
                            Manifest.permission.LOCATION_HARDWARE, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.READ_CONTACTS}, 0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        aiuiPresenter.startAIUI();
        if (!NetworkUtils.isConnected()) {
            startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
        }
    }


}
