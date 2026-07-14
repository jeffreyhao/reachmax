package com.xcyh.reachmax.adv.search;

import com.baidu.baselibrary.base.IBaseView;
import com.xcyh.reachmax.model.bean.search.SearchItem;

import java.util.List;

public interface ISearchView extends IBaseView<List<? extends SearchItem>> {

    void onSearchSuccess(List<? extends SearchItem> list, int total);
}
