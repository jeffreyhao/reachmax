package com.xcyh.reachmax.model.bean.search;

import java.util.List;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/12/18.
 */
@Keep
public class SearchPlanBody {


    private List<SearchItemPlan> data;

    private int total;

    public List<SearchItemPlan> getData() {
        return data;
    }

    public void setData(List<SearchItemPlan> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
