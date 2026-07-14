package com.xcyh.reachmax.adv.pages;


import com.xcyh.reachmax.model.bean.AmountData;
import com.xcyh.reachmax.model.bean.NoneIdAmount;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
public interface IAdvTotalAmountView {

    void setAmountView(boolean isListEmpty, AmountData amount, NoneIdAmount noneIdAmount);

}
