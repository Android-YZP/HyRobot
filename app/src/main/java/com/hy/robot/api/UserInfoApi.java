package com.hy.robot.api;


import com.com1075.library.http.retrofit.HttpResponse;
import com.hy.robot.App;
import com.hy.robot.utils.SharedPreferencesUtils;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.hy.robot.App.getContext;

/**
 * @author 姚中平
 */
public interface UserInfoApi {
    @FormUrlEncoded
    @POST("http://192.168.108.176/hyedu/app/user/info")
    Observable<HttpResponse> userInfo(@FieldMap Map<String, Object> request, @Header("token") String token);


}
