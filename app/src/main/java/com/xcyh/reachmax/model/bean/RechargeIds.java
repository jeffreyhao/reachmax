package com.xcyh.reachmax.model.bean;

/**
 * Created by haojiangfeng on 2025/12/10.
 */
public class RechargeIds {

    public String recharge_account_ids;
    public String first_recharge_account_ids;

    public RechargeIds(String recharge_account_ids, String first_recharge_account_ids) {
        this.recharge_account_ids = recharge_account_ids;
        this.first_recharge_account_ids = first_recharge_account_ids;
    }

    @Override
    public String toString() {
        return "充值用户：\n"
                + recharge_account_ids + "\n"
                + "首充用户：\n"
                + first_recharge_account_ids + "\n";
    }

}
