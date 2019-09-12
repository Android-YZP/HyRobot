package com.com1075.library.base;

/**
 * IBaseView
 *
 * @author 姚中平
 */

public interface IBaseContract {

    //加载数据中
    void LoadingData();

    //加载失败
    void LoadingDataFail(String result);

    //加载成功
    void LoadingDataSuccess(String result);



}
