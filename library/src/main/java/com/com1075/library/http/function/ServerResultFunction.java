package com.com1075.library.http.function;

import com.com1075.library.http.exception.ServerException;
import com.com1075.library.http.retrofit.HttpResponse;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 * @author 姚中平
 */
public class ServerResultFunction implements Function<HttpResponse, Object> {
    @Override
    public Object apply(@NonNull HttpResponse response) throws Exception {
        //当响应码不为0的时候这里抛出异常
        if (!response.isSuccess()) {//
            throw new ServerException(response.getCode(), response.getMsg());
        }
        return response.toString();
    }
}
