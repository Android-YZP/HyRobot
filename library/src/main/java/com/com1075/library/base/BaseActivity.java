package com.com1075.library.base;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.com1075.library.listener.LifeCycleListener;
import com.com1075.library.manager.ActivityStackManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 *
 * @author 姚中平
 */
public abstract class BaseActivity extends RxAppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        setContentView(getContentViewId());
        //手动对activity栈的管理
        ActivityStackManager.getManager().push(this);
        initBundleData(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }

        ActivityStackManager.getManager().remove(this);
    }


    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int getContentViewId();

    /**
     * 获取上一个界面传送过来的数据
     * @param savedInstanceState
     */
    protected  void initBundleData(Bundle savedInstanceState){}

    /**
     * 初始化应用程序，设置一些界面控件的初始化
     */
    protected abstract void initView();

    /**
     * 初始化应用程序，设置一些初始化数据,获取数据等操作
     */
    protected abstract void initData();

    /**
     * 设置一些控件的各种监听
     */
    protected abstract void setListener();


    /**
     * 回调LifeCycleListener的监听
     */
    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }


}
