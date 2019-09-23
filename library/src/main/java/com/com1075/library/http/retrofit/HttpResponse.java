package com.com1075.library.http.retrofit;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * @author 姚中平
 */
public class HttpResponse {

    /**
     * 描述信息
     */
    @SerializedName("msg")
    private String msg;

    /**
     * 状态码
     */
    @SerializedName("code")
    private int code;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    private Object data;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("result")
    private Object result;


    /**
     * 页数
     */
    @SerializedName("page")
    private Object page;

    /**
     * 是否成功(这里约定0)
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 0 ? true : false;
    }

    public String toString() {
        String response = "";

        if (data == null) {

            response = "{\"code\": " + code + ",\"msg\":" + "\"" + msg + "\"" + ",\"result\":" + new Gson().toJson(result) + ",\"page\":" + new Gson().toJson(page) + "}";
        } else {

            response = "{\"code\": " + code + ",\"msg\":" + "\"" + msg + "\"" + ",\"data\":" + new Gson().toJson(data) + ",\"page\":" + new Gson().toJson(page) + "}";

        }
        return response;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}
