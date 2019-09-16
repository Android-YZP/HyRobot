package com.hy.robot.api;


import com.com1075.library.http.retrofit.HttpResponse;
import com.hy.robot.api.requestbean.LoginBean;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author 姚中平application/json
 */
public interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("http://192.168.108.176/hyedu/app/login")
    Observable<HttpResponse> login(@Body LoginBean request);



}
