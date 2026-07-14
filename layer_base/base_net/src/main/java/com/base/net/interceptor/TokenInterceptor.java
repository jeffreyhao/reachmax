package com.base.net.interceptor;

import android.text.TextUtils;

import com.base.api.Logger;
import com.base.api.UserApi;
import com.base.net.bean.HttpResult;
import com.base.util.content.GsonUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
* @author lhc
* @date 2022/5/9 9:12
* @desc token过期 拦截器
*/
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        if(isTokenExpired(proceed)) {
            UserApi.refreshAccessToken();
            return chain.proceed(request);
        }
        return proceed;
    }

    private boolean isTokenExpired(Response response) {
        try{
            ResponseBody responseBody = response.body();
            if(responseBody!=null) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                if(buffer!=null) {
                    String bodyContent = buffer.clone().readString(StandardCharsets.UTF_8);
                    if(!TextUtils.isEmpty(bodyContent)&&!bodyContent.startsWith("{")){
                        return false;
                    }
                    HttpResult result = GsonUtil.json2Bean(bodyContent, HttpResult.class);
                    if(result!=null&&result.getCode()==10003) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Logger.exception("TokenInterceptor", "isTokenExpired", e);
            return false;
        }
        return false;
    }

}
