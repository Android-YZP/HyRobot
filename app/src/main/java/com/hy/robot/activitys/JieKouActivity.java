package com.hy.robot.activitys;

import android.text.Html;
import android.widget.TextView;
import com.com1075.library.base.BaseActivity;
import com.hy.robot.R;
import com.hy.robot.contract.IRobotContract;
import com.hy.robot.presenter.RobotPresenter;
import com.orhanobut.logger.Logger;

public class JieKouActivity extends BaseActivity implements IRobotContract {

    private RobotPresenter mRobotPresenter = new RobotPresenter(this, this);
    String  string = "<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">8月18日晚间，中装建设发布公告称，已于近日与上海玳鸽信息技术有限公司(以下简称“玳鸽信息”)签署了《关于共同设立区块链金融平台的协议》，中装建设将于玳鸽信息共同设立建筑装饰业区块链技术金融信息服务平台。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">据公告显示，该区块链金融平台将帮助中装建设有效减轻上下游的现金兑付压力，减少资金占用以及降低资金成本。该区块链金融平台除了应用于中装建设自身内部业务之外，还向同行业其他公司输出技术以及提供服务，并由此获取相关收益。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">中装建设全称为深圳市中装建设集团股份有限公司，成立于1994年，于2016年在深圳证券交易所A股上市，除了装饰主业之外，还涉及建筑装饰、设计研发、园林绿化、房建市政、新能源、物业管理、城市微更新等多个行业。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">经财经网.链上财经查阅，在2018年7月，中装建设曾在互动平台上表示，中装建设暂时并未涉及区块链业务。在过去一年时间内，中装建设也并未在区块链领域中有任何动作，因此，此次建立区块链金融平台可谓是中装建设公开层面上的首次区块链布局。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">今日早晨，中装建设开盘即上涨5.79%，达到8.40元每股，股价创一年半以来的新高。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">此前，有大量上市公司依靠区块链热点逆风翻盘，接连上涨，其中“成绩”最为辉煌的即为美股上市公司Overstock。据链上财经8月15日《STO先行者美“区块链概念股”Overstock辉煌难续》报道显示，Overstock2014年开始布局区块链，此后Overstock股价接连上涨，至2018年1月，Overstock股价达到了其上市以来的最高价——89.80美元。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">链上财经查询后得知，2019年中装建设二级市场走势较好，其主体业务总体亦属于稳健开展状态。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">综合多方信息显示，此次中装建设与玳鸽信息合作建立的区块链金融平台，是一个典型的将区块链技术与供应链金融相结合的平台。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">供应链金融是目前区块链技术商业落地的重要场景之一，据苏宁金融研究院表示，供应链金融存在着应收账款无法直接流通、融资成本较高、信用环境差等问题，而区块链技术可保证供应链金融资产更高效安全，拥有助力供应链金融资产数字化、推动多主题更好的合作、实现多层级信用传递以及通过智能合约防范履约风险等多个优点。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">但是据清华大学金融科技研究院区块链研究中心蒋峰明表示，区块链技术只能解决供应链金融带来的部分问题，其中还存在着行业数字化水平不足、票据市场基础设施建设滞后、相关法律滞后以及核心企业参与动力不足等多种问题。</span>\r\n</p>\r\n<p style=\"font-size:16px;color:#333333;text-align:justify;font-family:arial;background-color:#FFFFFF;\">\r\n\t<span class=\"bjh-p\">据链上财经了解，目前京东、平安壹账通、阿里等多个传统企业均已经开始涉足区块链技术在供应链金融中的落地开发。</span>\r\n</p>";

    @Override

    protected int getContentViewId() {
        return R.layout.activity_jie_kou;
    }

    @Override
    protected void initView() {
        TextView textView  = findViewById(R.id.tv);
        textView.setText(Html.fromHtml(string));

        Logger.e(textView.getText().toString());
    }

    @Override
    protected void initData() {


//        mRobotPresenter.HttpList();
//        AppUtils.installAppSilent("");
//        mRobotPresenter.HttpChangeUserinfo();
//        mRobotPresenter.HttpChangeLevelup();
//        mRobotPresenter.HttpUserInfo();
//        mRobotPresenter.HttpLogin();
//        mRobotPresenter.HttpChangeVersion();
//        mRobotPresenter.HttpXianPao();
    }

    @Override
    protected void setListener() {

    }

    // * 获取个人信息
    @Override
    public void LoadingDataSuccess(String result) {
        Logger.e(result);
    }

    @Override
    public void LoadingXianPaoDataSuccess(String result) {
        Logger.e(result);

    }

    @Override
    public void LoadingListDataSuccess(String result) {
        Logger.e(result);
    }

    @Override
    public void LoadingChangeUserInfoDataSuccess(String result) {
        Logger.e(result);
    }

    @Override
    public void LoadingLevelupDataSuccess(String result) {
        Logger.e(result);
    }

    @Override
    public void LoadingLoginDataSuccess(String result) {
        Logger.e(result);

    }

    @Override
    public void LoadingSanYanDataSuccess(String result) {
        Logger.e(result);
    }

    @Override
    public void LoadingChangeVersionDataSuccess(String result) {
        Logger.e(result);
    }

    @Override
    public void LoadingData() {
    }

    @Override
    public void LoadingDataFail(String result) {

    }


}
