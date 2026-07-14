package com.xcyh.reachmax.adv.pages.account;


import com.base.net.bean.ApiException;
import com.xcyh.reachmax.adv.pages.AdvListFragment;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.AdvStateFilter;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;


/**
 * Created by haojiangfeng on 2024/11/12.
 */
public class AdvAccountListFragment extends AdvListFragment<AdvAccountListPresenter> {


    @Override
    public int getLevel() {
        return AdvItemLevel.ADV_ACCOUNT;
    }

    @Override
    protected List<AdvStateFilter> createFilteStateList() {
        List<AdvStateFilter> list = new ArrayList<>();
        list.add(AdvStateFilter.ALL);
        list.add(AdvStateFilter.ACCOUNT_NORMAL);
        list.add(AdvStateFilter.ACCOUNT_ABNORMAL);
        return list;
    }

    @Override
    protected void initSelectParamsListeners(){
        GlobalAdvParams.sSelectAccounts.observe(this, new Observer<ArrayList<ItemData>>() {
            @Override
            public void onChanged(ArrayList<ItemData> itemData) {
                notifyRangeAll();
            }
        });
    }

    @Override
    public void onActionStateUpdated(int level, ItemData itemData, String status, String budget) {
        // none
    }

    @Override
    public void doRequest() {
        getReportList(getPage() == 1, getPage());
    }


    @Override
    public void onRequestFail(@Nullable ApiException e) {
        super.onRequestFail(e);
    }


}
