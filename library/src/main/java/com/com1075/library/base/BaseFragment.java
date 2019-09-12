package com.com1075.library.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.com1075.library.listener.LifeCycleListener;
import com.trello.rxlifecycle2.components.support.RxFragment;



/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 *
 * @author 姚中平
 */
public abstract class BaseFragment extends RxFragment  {

    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }

        mContext = getActivity();
        initBundleData(savedInstanceState);
        initView();
        initData();
        setListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
    }


    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int getContentViewId();

    /**
     * 获取上一个界面传送过来的数据
     * @param savedInstanceState
     */
    protected void initBundleData(Bundle savedInstanceState){}

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
