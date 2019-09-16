package com.hy.robot.api;


import com.com1075.library.http.retrofit.HttpResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author 姚中平
 */
public interface XianPaoApi {
//    @Headers("token: max-age=640000")
    @FormUrlEncoded
    @POST("https://xianpaotv.com/video/api/video/randList")
    Observable<HttpResponse> getVideo(@FieldMap Map<String, Object> request);
}
