package com.hy.robot.utils;

import android.content.Context;
import android.widget.Toast;

import com.hy.robot.App;

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
}
