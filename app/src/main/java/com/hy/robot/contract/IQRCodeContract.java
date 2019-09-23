package com.hy.robot.contract;

import com.com1075.library.base.IBaseContract;

public interface IQRCodeContract extends IBaseContract {

    void LoadingLoginDataSuccess(String data);

    void LoadingChangeUserInfoDataSuccess(String data);

    void LoadingChangeVersionDataSuccess(String data);
}

