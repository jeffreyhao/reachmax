package com.xcyh.reachmax.adv.pages.serial;


import com.xcyh.reachmax.adv.pages.AdvListFragment;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.AdvStateFilter;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;


/**
 * Created by haojiangfeng on 2024/11/12.
 */
public class AdvSerialListFragment extends AdvListFragment<AdvSerialListPresenter>  {


    @Override
    public int getLevel() {
        return AdvItemLevel.ADV_SERIAL;
    }


    @Override
    protected List<AdvStateFilter> createFilteStateList() {
        List<AdvStateFilter> list = new ArrayList<>();
        list.add(AdvStateFilter.ALL);
        list.add(AdvStateFilter.ADV_PUTTING);
        list.add(AdvStateFilter.ADV_PAUSED);
        list.add(AdvStateFilter.ADV_DELETED);
        return list;
    }

    @Override
    protected void initSelectParamsListeners(){
        GlobalAdvParams.sSelectAccounts.observe(this, new Observer<ArrayList<ItemData>>() {
            @Override
            public void onChanged(ArrayList<ItemData> itemData) {
                postClearAndTryRequest();
            }
        });
        GlobalAdvParams.sSelectSerials.observe(this, new Observer<ArrayList<ItemData>>() {
            @Override
            public void onChanged(ArrayList<ItemData> itemData) {
                notifyRangeAll();
            }
        });
    }


    @Override
    public void doRequest() {
        getReportList(getPage() == 1, getPage());
    }


}
