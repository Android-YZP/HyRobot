package com.hy.robot.api;


import com.com1075.library.http.retrofit.RetrofitUtils;

/**
 * 接口工具类
 *
 * @author 姚中平
 */

public class ApiUtils {

    private static XianPaoApi xianPaoApi;
    private static ChangeUserInfoApi changeUserInfoApi;
    private static ChangeVersionApi changeVersionApi ;
    private static LevelUpApi levelUpApi;
    private static ListApi listApi;
    private static LoginApi loginApi;
    private static UserInfoApi userInfoApi;

    public static XianPaoApi getXianPaoApi() {
        if (xianPaoApi == null) {
            xianPaoApi = RetrofitUtils.get().retrofit().create(XianPaoApi.class);
        }
        return xianPaoApi;
    }

    public static LoginApi getLoginApi() {
        if (loginApi == null) {
            loginApi = RetrofitUtils.get().retrofit().create(LoginApi.class);
        }
        return loginApi;
    }


    public static LevelUpApi getLevelUpApi() {
        if (levelUpApi == null) {
            levelUpApi = RetrofitUtils.get().retrofit().create(LevelUpApi.class);
        }
        return levelUpApi;
    }


    public static ListApi getListApi() {
        if (listApi == null) {
            listApi = RetrofitUtils.get().retrofit().create(ListApi.class);
        }
        return listApi;
    }


    public static ChangeUserInfoApi getChangeUserInfoApi() {
        if (changeUserInfoApi == null) {
            changeUserInfoApi = RetrofitUtils.get().retrofit().create(ChangeUserInfoApi.class);
        }
        return changeUserInfoApi;
    }

    public static ChangeVersionApi getChangeVersionApi() {
        if (changeVersionApi == null) {
            changeVersionApi = RetrofitUtils.get().retrofit().create(ChangeVersionApi.class);
        }
        return changeVersionApi;
    }


    public static UserInfoApi getUserInfoApi() {
        if (userInfoApi == null) {
            userInfoApi = RetrofitUtils.get().retrofit().create(UserInfoApi.class);
        }
        return userInfoApi;
    }

}
