package com.hy.robot.api;


import com.com1075.library.http.retrofit.HttpResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author 姚中平
 */
public interface LevelUpApi {
    @Headers("token: max-age=640000")
    @FormUrlEncoded
    @POST("/app/user/levelup")
    Observable<HttpResponse> levelup(@QueryMap Map<String, Object> request);

}
