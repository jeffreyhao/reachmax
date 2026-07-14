package com.xcyh.reachmax.model.request;

import com.baidu.baselibrary.log.ALog;
import com.base.net.webhook.DingDingSender;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.Keep;

@Keep
public class RequestResult<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> RequestResult<T> format(String resultJson, Class<T> clazz){
        return formatBody(resultJson, "body", clazz);
    }

    /**
     * @param resultJson  {"code":0, "msg":"success", "data":{}}
     * @param <T>   泛型Bean
     *
     * @return      RequestResult
     */
    public static <T> RequestResult<T> formatBody(String resultJson, String bodyName, Class<T> clazz){
        RequestResult<T> requestResult = new RequestResult<>();
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            requestResult.setCode(jsonObject.optInt("code", -1));
            requestResult.setMsg(jsonObject.optString("msg", ""));
            Object body = jsonObject.opt(bodyName);
            if(body != null) {
                T t = new Gson().fromJson(body.toString(), clazz);
                requestResult.setData(t);
            }
            return requestResult;

        } catch (Throwable e){
            ALog.exception("RequestResult", "formatBody", e);
            DingDingSender.sendException(Thread.currentThread(), e, "RequestResult.formatList");
        }
        return null;
    }

    public static <D> RequestResult<List<D>> formatList(String resultJson, String bodyName, Type listType){
        RequestResult<List<D>> requestResult = new RequestResult<>();
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            requestResult.setCode(jsonObject.optInt("code", -1));
            requestResult.setMsg(jsonObject.optString("msg", ""));
            Object body = jsonObject.opt(bodyName);
            if(body != null) {
                List<D> list = new Gson().fromJson(body.toString(), listType);
                requestResult.setData(list);
            }
            return requestResult;

        } catch (Throwable e){
            ALog.exception("RequestResult", "formatList", e);
            DingDingSender.sendException(Thread.currentThread(), e, "RequestResult.formatList");
        }
        return null;
    }


}
