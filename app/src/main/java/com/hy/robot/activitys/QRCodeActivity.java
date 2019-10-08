package com.hy.robot.activitys;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.com1075.library.base.BaseActivity;
import com.google.gson.Gson;
import com.hy.robot.App;
import com.hy.robot.R;
import com.hy.robot.bean.LoginBean;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.bean.QRCodeBean;
import com.hy.robot.contract.IQRCodeContract;
import com.hy.robot.presenter.QRCodePresenter;
import com.hy.robot.utils.SharedPreferencesUtils;
import com.hy.robot.utils.WIFIConnectionManager;
import com.orhanobut.logger.Logger;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import org.greenrobot.eventbus.EventBus;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import static com.hy.robot.App.getContext;

public class QRCodeActivity extends BaseActivity implements QRCodeView.Delegate, IQRCodeContract {

    private ZXingView mZXingView;
    private int i = 0;
    private QRCodePresenter qrCodePresenter = new QRCodePresenter(this, this);
    private QRCodeBean mQrCodeBean;

    /**
     * 1.连wifi
     * 2。登陆
     * 3。拉去个人信息
     * 4。更新个人信息
     */

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 101:
                    if (NetworkUtils.isConnected() && mQrCodeBean != null && !TextUtils.isEmpty("" + SharedPreferencesUtils.getParam(getContext(), "deviceToken", ""))) {
                        qrCodePresenter.HttpLogin(mQrCodeBean.getAccount());
                    } else {
                        Logger.e("循环" + i++);
                        initData();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onStart() {
        super.onStart();

        mZXingView.startCamera(); // 打开后置摄像头开始 预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo); // 打开前置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        mZXingView.setType(BarcodeType.TWO_DIMENSION, null); // 只识别二维条码
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        handler = null;
        PushAgent.getInstance(App.getContext()).register(null);
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        mZXingView = findViewById(R.id.zxingview);
    }

    @Override
    protected void initData() {
        mZXingView.setDelegate(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (handler == null)return;
                Message message = new Message();
                message.what = 101;
                handler.sendMessage(message);
            }
        }, 2000);

        if (NetworkUtils.isConnected()) {
            PushAgent.getInstance(App.getContext()).register(new IUmengRegisterCallback() {
                @Override
                public void onSuccess(String deviceToken) {
                    Logger.e("注册成功：deviceToken：-------->  " + deviceToken);
                    SharedPreferencesUtils.setParam(getContext(), "deviceToken", deviceToken);
                }

                @Override
                public void onFailure(String s, String s1) {
                    Logger.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
                }
            });
        }

    }

    @Override
    protected void setListener() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void LoadingLoginDataSuccess(String data) {//拉取个人信息，修改个人信息，修改版本号
        Logger.e(data);
        LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
        String deviceToken = (String) SharedPreferencesUtils.getParam(getContext(), "deviceToken", "");
        SharedPreferencesUtils.setParam(getContext(), "LoginToken", loginBean.getData().getToken());
        qrCodePresenter.HttpChangeUserinfo(deviceToken, PhoneUtils.getIMEI(), loginBean.getData().getToken());
    }

    @Override
    public void LoadingChangeUserInfoDataSuccess(String data) {
        Logger.e(data);
        qrCodePresenter.HttpUserInfo(SharedPreferencesUtils.getToken());
    }

    @Override
    public void LoadingChangeVersionDataSuccess(String data) {
    }

    @Override
    public void LoadingDataSuccess(String result) {
        Logger.e(result);
        //保存下来个人信息
        SharedPreferencesUtils.setParam(App.getContext(), "UserInfo", result);
//        UserInfoBean userInfoBean = new Gson().fromJson(result, UserInfoBean.class);
        EventBus.getDefault().post(MessageWrap.getInstance2("您好;初次见面;请多多指教", "read"));
        finish();

    }

    @Override
    public void LoadingData() {

    }

    @Override
    public void LoadingDataFail(String result) {
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        initData();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Logger.e(result);
        mQrCodeBean = new Gson().fromJson(result, QRCodeBean.class);
        WIFIConnectionManager.getInstance(QRCodeActivity.this).connect(mQrCodeBean.getWifi(), mQrCodeBean.getWifi_pwd());
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.e("打开相机出错");
    }


//    @SuppressLint("MissingPermission")
//    private String getPhoneIMEI() {
//        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Service.TELEPHONY_SERVICE);
//        String deviceId = tm.getDeviceId();
//        return deviceId;
//    }

//
//    public class WIFIStateReceiver extends BroadcastReceiver {
//        private Context mContext;
//
//        public WIFIStateReceiver(Context context) {
//            this.mContext = context;
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            //wifi连接上与否
//            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
//                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//                if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//                    Logger.e(intent.getAction() + "没有连接上");
//
//                } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
//                    if (WIFIConnectionManager.getInstance(mContext).isConnected(mQrCodeBean.getWifi())) {
//                        Logger.e(intent.getAction() + "连接上");
//
//
//                    }
//                }
//            }
//
//        }
//
//    }
}
