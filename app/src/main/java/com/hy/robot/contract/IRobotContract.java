package com.hy.robot.contract;

import com.com1075.library.base.IBaseContract;

public interface IRobotContract extends IBaseContract {

    /**
     * 获取闲泡视频
     * 获取个人信息
     * 获取自定义问答
     * 修改用户信息 token
     * 获取升级
     * 获取三眼咨询
     */

    //获取闲泡视频
    void LoadingXianPaoDataSuccess(String result);
    //获取自定义问答
    void LoadingListDataSuccess(String result);
    //修改用户信息 token
    void LoadingChangeUserInfoDataSuccess(String result);
    //获取升级
    void LoadingLevelupDataSuccess(String result);
    //获取登陆信息
    void LoadingLoginDataSuccess(String result);
    //获取三眼咨询
    void LoadingSanYanDataSuccess(String result);
    //修改版本号
    void LoadingChangeVersionDataSuccess(String result);
}

