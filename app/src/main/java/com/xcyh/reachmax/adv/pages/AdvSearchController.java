package com.xcyh.reachmax.adv.pages;

import android.os.Bundle;

import com.base.util.collection.ListUtil;
import com.base.util.content.StringUtils;
import com.xcyh.reachmax.adv.home.callback.IAdvSearchController;
import com.xcyh.reachmax.adv.home.callback.OnParamsChangeListener;
import com.xcyh.reachmax.adv.AdvListUtil;
import com.xcyh.reachmax.model.bean.search.SearchItem;
import com.xcyh.reachmax.model.constant.AdvItemLevel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 搜索框相关的参数。
 *
 *      包含：
 *          搜索词、搜索结果、选中结果
 *
 * Created by haojiangfeng on 2024/12/30.
 */
public class AdvSearchController implements IAdvSearchController {



    protected @AdvItemLevel int     mAdvLevel;


    protected OnParamsChangeListener mOnParamsChangeListener;



    /** 最近一次有搜索结果的 搜索词 **/
    @NonNull
    protected CharSequence          mLatestSearchText;

    /** 当前搜索框中的文字 **/
    @NonNull
    protected CharSequence          mCurrentSearchBarText;


    @NonNull
    protected ArrayList<SearchItem> mSearchResultItems;

    @NonNull
    protected ArrayList<SearchItem> mSelectSearchItems;

    /**
     * 是否需要通知参数变化
     */
    protected boolean               mNeedNotifyParamsChanged;


    public AdvSearchController(int level, OnParamsChangeListener listener){
        this.mAdvLevel = level;
        this.mOnParamsChangeListener = listener;
        this.mNeedNotifyParamsChanged = true;

        mLatestSearchText = "";
        mCurrentSearchBarText = "";

        mSearchResultItems = new ArrayList<>();
        mSelectSearchItems = new ArrayList<>();
    }


    public Bundle getSearchParams(){
        Bundle bundle = new Bundle();
        bundle.putInt("level", mAdvLevel);
        bundle.putString("search_text", mLatestSearchText.toString());
        bundle.putString("search_bar_text", mCurrentSearchBarText.toString());
        bundle.putParcelableArrayList("search_result_list", mSearchResultItems);
        bundle.putParcelableArrayList("search_select_list", mSelectSearchItems);
        return bundle;
    }


    public void onClickSearchValue(CharSequence searchText, @Nullable ArrayList<SearchItem> searchResultList, @Nullable ArrayList<SearchItem> checkedList) {
        mLatestSearchText = StringUtils.nonNull(searchText);

        mSearchResultItems.clear();
        if(searchResultList != null){
            mSearchResultItems.addAll(searchResultList);
        }

        mSelectSearchItems.clear();
        if(checkedList != null){
            mSelectSearchItems.addAll(checkedList);
        }
    }

    /**
     * 报表请求使用
     */
    public String getSearchValues(){
        return ListUtil.isEmpty(mSelectSearchItems)
                ? mLatestSearchText.toString()
                : AdvListUtil.list2Param(mSelectSearchItems);
    }

    /**
     * 报表请求完匹配搜索key
     */
    public CharSequence getSearchText(){
        return mLatestSearchText;
    }

    @Override
    public void onSearchBarTextChanged(CharSequence s) {
        mCurrentSearchBarText = s;
    }


    protected void onParamsChanged(){
        if(mNeedNotifyParamsChanged && mOnParamsChangeListener != null){
            mOnParamsChangeListener.onParamsChanged();
        }
    }

    /**
     * 设置是否可以触发参数变化回调。缺省为true。
     */
    public void setNeedNotifyParamsChanged(boolean needNotify){
        this.mNeedNotifyParamsChanged = needNotify;
    }

}
