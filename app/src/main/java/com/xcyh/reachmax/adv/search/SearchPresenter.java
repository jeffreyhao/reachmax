package com.xcyh.reachmax.adv.search;

import com.base.global.GlobalBuildConfig;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.base.net.bean.ApiException;
import com.xcyh.reachmax.model.bean.search.SearchAccountBody;
import com.xcyh.reachmax.model.bean.search.SearchItemGroup;
import com.xcyh.reachmax.model.bean.search.SearchItemSerial;
import com.xcyh.reachmax.model.bean.search.SearchPlanBody;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.event.GlobalAdvParams;
import com.xcyh.reachmax.model.request.Presenter;
import com.xcyh.reachmax.model.request.RequestCallback;
import com.xcyh.reachmax.model.request.RequestListCallback;
import com.xcyh.reachmax.model.request.Url;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by haojiangfeng on 2024/12/27.
 */
public class SearchPresenter extends Presenter<ISearchView> {

    private int mLimit = 2000;

    public int getLimit(){
        return mLimit;
    }

    /**
     * 搜索关键词
     * @param searchContent 关键词
     */
    public void doSearch(int level, CharSequence searchContent, int page){
        String text = searchContent.toString();
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("keyword", text);
        paramMap.put("page", page);
        paramMap.put("pageSize", mLimit);

        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  searchAccount(paramMap);    break;
            case AdvItemLevel.ADV_SERIAL:   searchSerial(paramMap);     break;
            case AdvItemLevel.ADV_GROUP:    searchGroup(paramMap);      break;
            case AdvItemLevel.ADV_PLAN:     searchPlan(paramMap);       break;
        }
    }


    private void searchAccount (LinkedHashMap<String, Object> paramMap){
        RequestCallback<SearchAccountBody> requestCallback = new RequestCallback<SearchAccountBody>(){


            @Override
            public void onSuccess(String content, SearchAccountBody searchValue) {
                if(searchValue == null) {

                } else {
                    mView.onSearchSuccess(searchValue.getData(), mLimit);
                }
            }

            @Override
            public void onFail(ApiException e) {
                if(GlobalBuildConfig.DEBUG){
                    ToastUtils.showToastCenter("广告账户搜索失败\n" + e.getMsg());
                }
            }
        };
        get(Url.API_ADV_ACCOUNT, paramMap, false, requestCallback);
    }


    private void searchSerial(LinkedHashMap<String, Object> paramMap){
        paramMap.put("ad_account_ids", GlobalAdvParams.getAccounts());

        RequestListCallback<SearchItemSerial> requestCallback = new RequestListCallback<SearchItemSerial>(){

            @Override
            public void onSuccess(String content, List<SearchItemSerial> serialList) {
                mView.onSearchSuccess(serialList, mLimit);
            }

            @Override
            public void onFail(ApiException e) {
                if(GlobalBuildConfig.DEBUG){
                    ToastUtils.showToastCenter("广告系列搜索失败\n" + e.getMsg());
                }
            }
        };
        get(Url.API_ADV_SERIAL, paramMap, false, requestCallback);
    }

    private void searchGroup(LinkedHashMap<String, Object> paramMap){
        paramMap.put("ad_account_ids", GlobalAdvParams.getAccounts());
        paramMap.put("campaign_ids", GlobalAdvParams.getSerials());

        RequestListCallback<SearchItemGroup> requestCallback = new RequestListCallback<SearchItemGroup>(){

            @Override
            public void onSuccess(String content, List<SearchItemGroup> groupList) {
                mView.onSearchSuccess(groupList, mLimit);
            }

            @Override
            public void onFail(ApiException e) {
                if(GlobalBuildConfig.DEBUG){
                    ToastUtils.showToastCenter("广告组搜索失败\n" + e.getMsg());
                }
            }
        };

        get(Url.API_ADV_GROUP, paramMap, false, requestCallback);
    }


    private void searchPlan(LinkedHashMap<String, Object> paramMap){
        paramMap.put("ad_account_ids", GlobalAdvParams.getAccounts());
        paramMap.put("campaign_ids", GlobalAdvParams.getSerials());
        paramMap.put("adset_ids", GlobalAdvParams.getGroups());

        RequestCallback<SearchPlanBody> requestCallback = new RequestCallback<SearchPlanBody>(){

            @Override
            public void onSuccess(String content, SearchPlanBody planBody) {
                if(planBody == null) {

                } else {
                    mView.onSearchSuccess(planBody.getData(), mLimit);
                }
            }

            @Override
            public void onFail(ApiException e) {
                if(GlobalBuildConfig.DEBUG){
                    ToastUtils.showToastCenter("广告计划搜索失败\n" + e.getMsg());
                }
            }
        };

        get(Url.API_ADV_PLAN, paramMap, false, requestCallback);
    }


    public void cancelAllRequest(){
        getBaseRequest().disposeAllSubscribe();
    }
}
