package com.hy.robot;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Html;
import android.widget.ImageView;

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
     */

    private AIUIPresenter aiuiPresenter = new AIUIPresenter(this, this);
    private ImageView mImageView;
    private String string = "<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">8月18日晚间，中装建设发布公告称，已于近日与上海玳鸽信息技术有限公司(以下简称“玳鸽信息”)签署了《关于共同设立区块链金融平台的协议》，中装建设将于玳鸽信息共同设立建筑装饰业区块链技术金融信息服务平台。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">据公告显示，该区块链金融平台将帮助中装建设有效减轻上下游的现金兑付压力，减少资金占用以及降低资金成本。该区块链金融平台除了应用于中装建设自身内部业务之外，还向同行业其他公司输出技术以及提供服务，并由此获取相关收益。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">中装建设全称为深圳市中装建设集团股份有限公司，成立于1994年，于2016年在深圳证券交易所A股上市，除了装饰主业之外，还涉及建筑装饰、设计研发、园林绿化、房建市政、新能源、物业管理、城市微更新等多个行业。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">经财经网.链上财经查阅，在2018年7月，中装建设曾在互动平台上表示，中装建设暂时并未涉及区块链业务。在过去一年时间内，中装建设也并未在区块链领域中有任何动作，因此，此次建立区块链金融平台可谓是中装建设公开层面上的首次区块链布局。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">今日早晨，中装建设开盘即上涨5.79%，达到8.40元每股，股价创一年半以来的新高。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">此前，有大量上市公司依靠区块链热点逆风翻盘，接连上涨，其中“成绩”最为辉煌的即为美股上市公司Overstock。据链上财经8月15日《STO先行者美“区块链概念股”Overstock辉煌难续》报道显示，Overstock2014年开始布局区块链，此后Overstock股价接连上涨，至2018年1月，Overstock股价达到了其上市以来的最高价——89.80美元。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">链上财经查询后得知，2019年中装建设二级市场走势较好，其主体业务总体亦属于稳健开展状态。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">综合多方信息显示，此次中装建设与玳鸽信息合作建立的区块链金融平台，是一个典型的将区块链技术与供应链金融相结合的平台。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">供应链金融是目前区块链技术商业落地的重要场景之一，据苏宁金融研究院表示，供应链金融存在着应收账款无法直接流通、融资成本较高、信用环境差等问题，而区块链技术可保证供应链金融资产更高效安全，拥有助力供应链金融资产数字化、推动多主题更好的合作、实现多层级信用传递以及通过智能合约防范履约风险等多个优点。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">但是据清华大学金融科技研究院区块链研究中心蒋峰明表示，区块链技术只能解决供应链金融带来的部分问题，其中还存在着行业数字化水平不足、票据市场基础设施建设滞后、相关法律滞后以及核心企业参与动力不足等多种问题。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">据链上财经了解，目前京东、平安壹账通、阿里等多个传统企业均已经开始涉足区块链技术在供应链金融中的落地开发。</span>\r\n</p>";

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
    }


    @Override
    protected void initView() {
        mImageView = findViewById(R.id.im_bg);
//      startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
        startActivity(new Intent(MainActivity.this, JieKouActivity.class));

    }

    @Override
    protected void initData() {
        requestPermissions();
        aiuiPresenter.startAIUI();
        aiuiPresenter.startSpeaker();
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
