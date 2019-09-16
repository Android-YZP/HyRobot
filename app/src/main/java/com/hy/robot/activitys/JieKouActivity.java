package com.hy.robot.activitys;

import com.com1075.library.base.BaseActivity;
import com.hy.robot.R;
import com.hy.robot.contract.IRobotContract;
import com.hy.robot.presenter.RobotPresenter;
import com.orhanobut.logger.Logger;

public class JieKouActivity extends BaseActivity implements IRobotContract {

    private RobotPresenter mRobotPresenter = new RobotPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jie_kou;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


        mRobotPresenter.HttpList();
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
