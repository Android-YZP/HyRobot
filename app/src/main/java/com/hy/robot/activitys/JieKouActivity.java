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
//        mRobotPresenter.HttpChangeLevelup();
//        mRobotPresenter.HttpChangeUserinfo();
//        mRobotPresenter.HttpList();
//        mRobotPresenter.HttpLogin();
        Logger.e(" mRobotPresenter.HttpXianPao();");
        mRobotPresenter.HttpXianPao();
//        AppUtils.installAppSilent("");
    }

    @Override
    protected void setListener() {

    }

    // * 获取个人信息
    @Override
    public void LoadingDataSuccess(String result) {

    }

    @Override
    public void LoadingXianPaoDataSuccess(String result) {
        Logger.json(result);

    }

    @Override
    public void LoadingListDataSuccess(String result) {

    }

    @Override
    public void LoadingChangeUserInfoDataSuccess(String result) {

    }

    @Override
    public void LoadingLevelupDataSuccess(String result) {

    }

    @Override
    public void LoadingLoginDataSuccess(String result) {

    }

    @Override
    public void LoadingSanYanDataSuccess(String result) {

    }

    @Override
    public void LoadingData() {

    }

    @Override
    public void LoadingDataFail(String result) {

    }


}
