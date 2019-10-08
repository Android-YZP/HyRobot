package com.hy.robot.presenter;

import com.com1075.library.base.BaseActivity;
import com.com1075.library.base.BasePresenter;
import com.com1075.library.http.exception.ApiException;
import com.com1075.library.http.observer.HttpRxObservable;
import com.com1075.library.http.observer.HttpRxObserver;
import com.com1075.library.http.retrofit.HttpRequest;
import com.hy.robot.api.ApiUtils;
import com.hy.robot.api.requestbean.ChangeUserInfoBean;
import com.hy.robot.api.requestbean.LoginBean;
import com.hy.robot.contract.IQRCodeContract;
import com.hy.robot.contract.IRobotContract;
import com.hy.robot.utils.UIUtils;
import com.orhanobut.logger.Logger;

import java.util.Map;

import io.reactivex.disposables.Disposable;


/**
 * 获取闲泡视频
 * 获取个人信息
 * 获取自定义问答
 * 修改用户信息 token
 * 获取升级
 * 获取三眼咨询
 */
public class QRCodePresenter extends BasePresenter<IQRCodeContract, BaseActivity> {
    private final String TAG = QRCodePresenter.class.getSimpleName();
    private BaseActivity activity;

    public QRCodePresenter(IQRCodeContract view, BaseActivity activity) {
        super(view, activity);
        this.activity = activity;
    }

    /**
     * 获取闲泡视频
     */


    /**
     * 登陆
     *
     * @param s
     */
    public void HttpLogin(final String s) {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
        LoginBean LoginBean = new LoginBean();
        LoginBean.setPassword("123456");
        LoginBean.setMobile(s);

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "HttpLogin") {

            @Override
            protected void onStart(Disposable d) {
                Logger.e(s);
                if (getView() != null)
                    getView().LoadingData();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e(e.toString() + "");
                if (getView() != null) {
                    getView().LoadingDataFail(e.getMsg());
                    UIUtils.showTip(e.getMsg());

                }

            }

            @Override
            protected void onSuccess(Object response) {
                if (getView() != null)
                    getView().LoadingLoginDataSuccess(response.toString());

            }
        };


        HttpRxObservable.getObservable(ApiUtils.getLoginApi().login(LoginBean), getActivity()).subscribe(httpRxObserver);
    }


    /**
     * 获取个人信息
     */
    public void HttpUserInfo(String LoginToken) {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
        Map<String, Object> request = HttpRequest.getRequest();

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "HttpUserInfo") {

            @Override
            protected void onStart(Disposable d) {
                Logger.d(d.toString() + "");
                if (getView() != null)
                    getView().LoadingData();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e(e.toString() + "");
                if (getView() != null) {
                    getView().LoadingDataFail(e.getMsg());
                    UIUtils.showTip(e.getMsg());
                }

            }

            @Override
            protected void onSuccess(Object response) {
                if (getView() != null)
                    getView().LoadingDataSuccess(response.toString());

            }
        };

        HttpRxObservable.getObservable(ApiUtils.getUserInfoApi().userInfo(request, LoginToken), getActivity()).subscribe(httpRxObserver);
    }


    /**
     * 修改个人信息
     */
    public void HttpChangeUserinfo(final String token, final String device, final String LoginToken) {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
////        Map<String, Object> request = HttpRequest.getRequest();
////        request.put("device_token", "1000");
////        request.put("version", "1");

        ChangeUserInfoBean changeUserInfoBean = new ChangeUserInfoBean();
        changeUserInfoBean.setDevice_token(token);
        changeUserInfoBean.setDevice(device);
        changeUserInfoBean.setVersion(UIUtils.getAppVersionCode());

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "randList") {

            @Override
            protected void onStart(Disposable d) {
                Logger.e(token + device);
                if (getView() != null)
                    getView().LoadingData();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e(e.toString() + "");
                if (getView() != null) {
                    getView().LoadingDataFail(e.getMsg());
                    UIUtils.showTip(e.getMsg());
                }

            }

            @Override
            protected void onSuccess(Object response) {
                if (getView() != null)
                    getView().LoadingChangeUserInfoDataSuccess(response.toString());

            }
        };

        HttpRxObservable.getObservable(ApiUtils.getChangeUserInfoApi().changeUserinfo(changeUserInfoBean, LoginToken), getActivity()).subscribe(httpRxObserver);
    }


    /**
     * 修改版本号
     */
    public void HttpChangeVersion( String LoginToken) {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
        Map<String, Object> request = HttpRequest.getRequest();
        request.put("version", "1");

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "HttpChangeVersion") {

            @Override
            protected void onStart(Disposable d) {
                if (getView() != null)
                    getView().LoadingData();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e(e.toString() + "");
                if (getView() != null) {
                    getView().LoadingDataFail(e.getMsg());
                    UIUtils.showTip(e.getMsg());
                }

            }

            @Override
            protected void onSuccess(Object response) {
                if (getView() != null)
                    getView().LoadingChangeVersionDataSuccess(response.toString());

            }
        };

        HttpRxObservable.getObservable(ApiUtils.getChangeVersionApi().changeVersion(request,LoginToken), getActivity()).subscribe(httpRxObserver);
    }


}
