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
public interface NewsInfo {
    @Headers("token: C47B1071")
    @FormUrlEncoded
    @POST("http://www.wuxiheyoo.com/api/free/information/getInformationById")
    Observable<HttpResponse> getNewsInfo(@FieldMap Map<String, Object> request);

}
