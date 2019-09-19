package com.hy.robot.presenter;

import com.com1075.library.base.BaseActivity;
import com.com1075.library.base.BaseActivity2;
import com.com1075.library.base.BasePresenter;
import com.com1075.library.http.exception.ApiException;
import com.com1075.library.http.observer.HttpRxObservable;
import com.com1075.library.http.observer.HttpRxObserver;
import com.com1075.library.http.retrofit.HttpRequest;
import com.hy.robot.api.ApiUtils;
import com.hy.robot.contract.INewsContract;
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
public class NewsPresenter extends BasePresenter<INewsContract, BaseActivity2> {
    private final String TAG = NewsPresenter.class.getSimpleName();
    private BaseActivity2 activity;

    public NewsPresenter(INewsContract view, BaseActivity2 activity) {
        super(view, activity);
        this.activity = activity;
    }


    /**
     * 获取咨询列表
     */
    public void HttpRandomGetInformationByContentType() {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
        Map<String, Object> request = HttpRequest.getRequest();
        request.put("contentType", "15");

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "HttpRandomGetInformationByContentType") {

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
                    getView().LoadingDataSuccess(response.toString());
            }
        };

        HttpRxObservable.getObservable(ApiUtils.getNewsListApi().getNewsList(request), getActivity()).subscribe(httpRxObserver);
    }

    /**
     * 获取咨询列表
     */
    public void getInformationById(String id) {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
        Map<String, Object> request = HttpRequest.getRequest();
        request.put("id", id);

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "getInformationById") {

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
                    getView().LoadingNewsDataSuccess(response.toString());
            }
        };

        HttpRxObservable.getObservable(ApiUtils.getNewsinfoApi().getNewsInfo(request), getActivity()).subscribe(httpRxObserver);
    }


}
