package com.base.api;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.base.abs.IRecharge;

public class RechargeApi {

    public static IRecharge iRecharge;

    public static boolean checkShowPremiumActiveDialog(String productId, String orderNo) {
        if(iRecharge != null){
            return iRecharge.checkShowPremiumActiveDialog(productId, orderNo);
        }
        return false;
    }

    public static void checkShowPremiumSubscribeDialog(FragmentActivity activity) {
        if(iRecharge != null){
            iRecharge.checkShowPremiumSubscribeDialog(activity);
        }
    }

    public static void checkShowCommentHintDialog(FragmentActivity activity) {
        if(iRecharge != null){
            iRecharge.checkShowCommentHintDialog(activity);
        }
    }
}
