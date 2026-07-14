package com.xcyh.reachmax.adv.home;

import com.baidu.baselibrary.base.IBaseView;
import com.base.net.bean.ApiException;
import com.xcyh.reachmax.model.bean.PrincipalBody;
import com.xcyh.reachmax.model.request.Presenter;
import com.xcyh.reachmax.model.request.RequestCallback;
import com.xcyh.reachmax.model.request.Url;

import java.util.LinkedHashMap;

/**
 * Created by haojiangfeng on 2025/1/10.
 */
public class HomePresenter extends Presenter<IBaseView<PrincipalBody>> {


    /**
     * 获取负责人
     */
    public void getPrincipal(){
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        RequestCallback<PrincipalBody> requestCallback = new RequestCallback<PrincipalBody>(){

            @Override
            public void onSuccess(String content, PrincipalBody principalBody) {
                mView.onRequestSuccess(principalBody);
            }

            @Override
            public void onFail(ApiException e) {
                int i = 0;
            }
        };
        get(Url.API_LAUNCH_LIST, paramMap, false, requestCallback);
    }


}
