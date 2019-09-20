package com.hy.robot.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import androidx.appcompat.app.AppCompatActivity;
import com.hy.robot.R;
import com.hy.robot.utils.WIFIConnectionManager;
import com.orhanobut.logger.Logger;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import static com.hy.robot.App.getContext;

public class QRCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private WIFIStateReceiver mWIFIStateReceiver;
    private ZXingView mZXingView;
    private boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }

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
        if (mWIFIStateReceiver != null)
            unregisterReceiver(mWIFIStateReceiver);
        super.onDestroy();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Logger.e(result+getPhoneIMEI());
        isError = false;
        if (mWIFIStateReceiver == null) {
            mWIFIStateReceiver = new WIFIStateReceiver(QRCodeActivity.this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mWIFIStateReceiver, filter);
            WIFIConnectionManager.getInstance(QRCodeActivity.this).connect("yaozp", "12345678");
        }

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.e("打开相机出错");
    }



    @SuppressLint("MissingPermission")
    private String getPhoneIMEI() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Service.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    public class WIFIStateReceiver extends BroadcastReceiver {
        private Context mContext;

        public WIFIStateReceiver(Context context) {
            this.mContext = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            //wifi连接上与否
            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                    Logger.e(intent.getAction() + "没有连接上");

                } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                    if (WIFIConnectionManager.getInstance(mContext).isConnected("yaozp"))
                        Logger.e(intent.getAction() + "连接上");
                }
            }

        }

    }
}
