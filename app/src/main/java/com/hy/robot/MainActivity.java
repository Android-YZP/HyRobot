package com.hy.robot;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.com1075.library.base.BaseActivity;
import com.google.gson.Gson;
import com.hy.robot.activitys.JieKouActivity;
import com.hy.robot.bean.AiUiResultBean;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.contract.IAIUIContract;
import com.hy.robot.presenter.AIUIPresenter;
import com.hy.robot.utils.SwitchBGUtils;
import com.hy.robot.utils.UIUtils;
import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements IAIUIContract {
    /**
     * 获取闲泡视频
     * 获取个人信息
     * 获取自定义问答
     * 修改用户信息 token
     * 获取升级
     * 获取三眼咨询
     * 音频 视频  IM 每个单独一个类去解决
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        // 手机端指令
        Logger.e(event.message);
        if (!event.message.equals("语音激活")) {
            aiuiPresenter.speachText(Html.fromHtml(event.message).toString());
        }

    }


    @Override
    protected void initView() {
        mImageView = findViewById(R.id.im_bg);
//      startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
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
//            aiuiPresenter.speachText(Html.fromHtml(string).toString());
            aiuiPresenter.speachText(aiUiResultBean.getIntent().getAnswer().getText());
            Logger.e(aiUiResultBean.getIntent().getAnswer().toString());

            SwitchBGUtils.getInstance(mImageView).switchBg(aiUiResultBean.getIntent().getService() + "");

        } catch (Exception e) {
            e.printStackTrace();
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
