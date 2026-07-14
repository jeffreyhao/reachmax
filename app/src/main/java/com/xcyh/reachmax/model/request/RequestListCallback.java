package com.xcyh.reachmax.model.request;

import android.text.TextUtils;

import com.baidu.baselibrary.log.ALog;
import com.baidu.baselibrary.util.clz.ClassUtil;
import com.baidu.baselibrary.util.sys.LogUtil;
import com.base.net.bean.ApiErrorCode;
import com.base.net.bean.ApiException;
import com.base.net.callback.ResponseListener;
import com.base.net.webhook.DingDingSender;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.manager.Pitcher;

import java.util.List;
import java.util.Map;

import androidx.annotation.Keep;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by haojiangfeng on 2024/12/12.
 *
 * @param <D> {@link ItemData}
 */
@Keep
public abstract class RequestListCallback<D> implements ResponseListener {


    @Override
    public void onSuccess(String apiPath, Map<String,Object> paramMap, String content, Response<ResponseBody> response, boolean isCache) {
        try {
            RequestResult<List<D>> result = RequestResult.formatList(content, "body", ClassUtil.getListClassType(this));
            if(result == null){
                onFail(apiPath, paramMap, new ApiException(ApiErrorCode.LOCAL_UNKNOWN_ERROR, "parse json error"), isCache);
            } else {
                if(result.getCode() == 0) {
                    LogUtil.d("Response", apiPath +  "\n       " + paramMap.toString() + "\n       " + content + "\n       ------");
                    onSuccess(content, result.getData());
                } else {
                    onFail(apiPath, paramMap, new ApiException(result.getCode(), result.getMsg()), isCache);
                }
            }
        } catch (Throwable e) {
            ALog.exception("RequestListCallback", "onSuccess", e);
            DingDingSender.sendException(Thread.currentThread(), e, "RequestListCallback.onSuccess");

            String errMsg = TextUtils.isEmpty(e.getMessage())
                    ? ((response == null || TextUtils.isEmpty(response.message())) ? "response is empty" : response.message())
                    : e.getMessage();
            onFail(apiPath, paramMap, new ApiException(ApiErrorCode.LOCAL_UNKNOWN_ERROR, errMsg), isCache);
        }
    }

    @Override
    public void onFail(String apiPath, Map<String,Object> paramMap, ApiException e, boolean isCache) {
        LogUtil.e("Response", "onFail, " + apiPath  + (e == null ? "" : ", " + e.getMsg()));

        if(e != null && e.getCode() == ApiErrorCode.REACH_MAX_TOKEN_INVALID) {
            Pitcher.getInstance().dealTokenInvalid();
        } else {
            onFail(e);
        }
    }


    @Override
    public void onCancel(String apiPath, Map<String, Object> paramMap) {

    }

    public abstract void onSuccess(String content, List<D> t);

    public abstract void onFail(ApiException e);
}