package com.hy.robot.presenter;

import com.com1075.library.base.BaseActivity;
import com.com1075.library.base.BasePresenter;
import com.com1075.library.http.exception.ApiException;
import com.com1075.library.http.observer.HttpRxObservable;
import com.com1075.library.http.observer.HttpRxObserver;
import com.com1075.library.http.retrofit.HttpRequest;
import com.hy.robot.api.ApiUtils;
import com.hy.robot.contract.ITestContract;
import com.hy.robot.utils.UIUtils;
import com.orhanobut.logger.Logger;

import java.util.Map;

import io.reactivex.disposables.Disposable;

public class TestPresenter extends BasePresenter<ITestContract, BaseActivity> {
    private final String TAG = TestPresenter.class.getSimpleName();
    private BaseActivity activity;
    public TestPresenter(ITestContract view, BaseActivity activity) {
        super(view, activity);
        this.activity = activity;
    }

    /**
     * 获取信息
     *
     * @author 姚中平
     */
    public void HttpLogin() {

        //构建请求数据https://xianpaotv.com/video/api/video/randList {currentUid=48976, count=20, page=1}
        Map<String, Object> request = HttpRequest.getRequest();
        request.put("count", "5");
        request.put("page", "1");

        HttpRxObserver httpRxObserver = new HttpRxObserver(TAG + "randList") {

            @Override
            protected void onStart(Disposable d) {
                if (getView() != null)
                    getView().LoadingData();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e(e.toString() + "");
                if (getView() != null){
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

        HttpRxObservable.getObservable(ApiUtils.getXianPaoApi().getVideo(request), getActivity()).subscribe(httpRxObserver);
    }
}
