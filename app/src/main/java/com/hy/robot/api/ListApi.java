package com.hy.robot.api;


import com.com1075.library.http.retrofit.HttpResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author 姚中平
 */
public interface ListApi {
    @Headers("token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MCIsImlhdCI6MTU2ODYwOTY0OCwiZXhwIjoxNTcxMjAxNjQ4fQ.EdnAP3LCViYpomcCXS7zZtLRKJBEy47uhDn0gPMDHVKlZtnkt4DeNR5jDMyFa2c_D0JCnc-AxOi4OV8PxI9ZWA")
    @FormUrlEncoded
    @POST("http://192.168.108.176/hyedu/app/aq/list")
    Observable<HttpResponse> list(@FieldMap Map<String, Object> request);

}
