package com.xcyh.reachmax.model.bean;

import java.util.List;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2025/1/10.
 */
@Keep
public class PrincipalBody {

    private List<PrincipalItem> data;


    public List<PrincipalItem> getData() {
        return data;
    }

    public void setData(List<PrincipalItem> data) {
        this.data = data;
    }
}
