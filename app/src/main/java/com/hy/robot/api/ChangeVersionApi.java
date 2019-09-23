package com.hy.robot.api;


import com.com1075.library.http.retrofit.HttpResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author 姚中平
 */
public interface ChangeVersionApi {
    @POST("http://192.168.108.176/hyedu/app/user/changeVersion")
    Observable<HttpResponse> changeVersion(@QueryMap Map<String, Object> request, @Header("token") String token);

}
