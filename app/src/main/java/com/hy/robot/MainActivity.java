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
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
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
import com.hy.robot.bean.TimeBean;
import com.hy.robot.contract.IAIUIContract;
import com.hy.robot.presenter.AIUIPresenter;
import com.hy.robot.utils.CalendarReminderUtils;
import com.hy.robot.utils.SwitchBGUtils;
import com.hy.robot.utils.UIUtils;
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
    protected void onDestroy() {
        super.onDestroy();
        aiuiPresenter.exit();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void initView() {
        mImageView = findViewById(R.id.im_bg);
        startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
//        startActivity(new Intent(MainActivity.this, VideoActivity.class));
//        startActivity(new Intent(MainActivity.this, MusicActivity.class));
//        startActivity(new Intent(MainActivity.this, TrieyeNewsActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {
        requestPermissions();
        aiuiPresenter.startAIUI();
        aiuiPresenter.startSpeaker();
        Glide.with(App.getContext()).load(R.mipmap.zhuanqq).into(mImageView);
//        createAlarm("房东舒服舒服", 20, 47, 55);
//        Logger.e(System.currentTimeMillis() + "");
    }

    @Override
    protected void setListener() {

        Intent i = new Intent("com.hy.robot.USER_ACTION");
        i.putExtra("key", "com.hy.robot.USER_ACTION");
        sendBroadcast(i);

    }

    @Override
    public void eventWeakup(int arg1) {

    }

    @Override
    public void eventResult(String s) {
        Logger.i(s);
        try {
            AiUiResultBean aiUiResultBean = new Gson().fromJson(s, AiUiResultBean.class);
            UIUtils.showTip(aiUiResultBean.getIntent().getService());
            SwitchBGUtils.getInstance(mImageView).switchBg(aiUiResultBean.getIntent().getService() + "");

            if (aiUiResultBean.getIntent().getAnswer().getText().equals("闲炮视频")) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
                return;
            }

            aiUiResult(aiUiResultBean.getIntent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        // 手机端指令
        Logger.e(event.message);
        if (event.message.equals("stop")) {//音视频停止播放
            aiuiPresenter.aiUiOn();
        } else if (event.message.equals("play")) {//音视频开始播放
            aiuiPresenter.aiUiOff();
        }

        if (event.type.equals("read")) {
            aiuiPresenter.speachText(event.message);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void aiUiResult(AiUiResultBean.IntentBean result) {

        switch (result.getService()) {
            case "drama": //戏曲
            case "crossTalk": //相声
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                intent.putExtra("data", new Gson().toJson(result.getData()));
                intent.putExtra("type", result.getService());
                startActivity(intent);
                break;
            case "闲泡视频": //闲泡视频
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
                break;
            case "飞鸽短信": //飞鸽短信

                break;
            case "飞鸽通话": //飞鸽通话

                break;
            case "news": //三眼蛙资讯
                startActivity(new Intent(MainActivity.this, TrieyeNewsActivity.class));
                break;
            case "跳舞": //跳舞

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
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS}, 0x0010);
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
    }


}
