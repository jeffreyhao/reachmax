package com.xcyh.reachmax.adv.home.callback;

import android.os.Bundle;

/**
 * Created by haojiangfeng on 2024/12/23.
 */
public interface IAdvSearchController  {


    void onSearchBarTextChanged(CharSequence s);

//    void onClearSearchResult();
//
//    String getSearchBarText();
//    void setLatestSearchValue(CharSequence searchText, List<? extends SearchItem> searchValues);


    /**
     * 搜索条参数
     */
    Bundle getSearchParams();

}
