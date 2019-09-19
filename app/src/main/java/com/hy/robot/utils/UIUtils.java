package com.hy.robot.utils;

import android.content.Context;
import android.widget.Toast;

import com.hy.robot.App;
import com.hy.robot.bean.NewsListBean;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class UIUtils {
    private static Toast mToast;

    public static Context getContext() {
        return App.getContext();
    }

    public static Toast showTip(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(str);
        }
        mToast.show();
        return mToast;
    }


    public static List<NewsListBean.ResultBean> chooseNewsList(List<NewsListBean.ResultBean> list) {
        List<NewsListBean.ResultBean> chooseList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTemplateType() == 3) {
                chooseList.add(list.get(i));
            }
        }

        return chooseList;
    }


}
