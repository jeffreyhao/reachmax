package com.xcyh.reachmax.model.bean;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2025/12/9.
 */
@Keep
public class NoneIdAmount {


    /**
     * is_first_recharge : 0
     * recharge_count : 4
     * recharge_amount : 41.972
     * first_recharge_count : 2
     * first_recharge_amount : 27.985999679565428
     * new_recharge_count : 1
     * new_recharge_amount : 13.993
     * old_recharge_count : 3
     * old_recharge_amount : 27.978999519348143
     * new_first_recharge_count : 1
     * new_first_recharge_amount : 13.992999839782714
     * old_first_recharge_count : 1
     * old_first_recharge_amount : 13.992999839782714
     */

    private int is_first_recharge;
    private int recharge_count;
    private double recharge_amount;
    private int first_recharge_count;
    private double first_recharge_amount;
    private int new_recharge_count;
    private double new_recharge_amount;
    private int old_recharge_count;
    private double old_recharge_amount;
    private int new_first_recharge_count;
    private double new_first_recharge_amount;
    private int old_first_recharge_count;
    private double old_first_recharge_amount;

    public int getIs_first_recharge() {
        return is_first_recharge;
    }

    public void setIs_first_recharge(int is_first_recharge) {
        this.is_first_recharge = is_first_recharge;
    }

    public int getRecharge_count() {
        return recharge_count;
    }

    public void setRecharge_count(int recharge_count) {
        this.recharge_count = recharge_count;
    }

    public double getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(double recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public int getFirst_recharge_count() {
        return first_recharge_count;
    }

    public void setFirst_recharge_count(int first_recharge_count) {
        this.first_recharge_count = first_recharge_count;
    }

    public double getFirst_recharge_amount() {
        return first_recharge_amount;
    }

    public void setFirst_recharge_amount(double first_recharge_amount) {
        this.first_recharge_amount = first_recharge_amount;
    }

    public int getNew_recharge_count() {
        return new_recharge_count;
    }

    public void setNew_recharge_count(int new_recharge_count) {
        this.new_recharge_count = new_recharge_count;
    }

    public double getNew_recharge_amount() {
        return new_recharge_amount;
    }

    public void setNew_recharge_amount(double new_recharge_amount) {
        this.new_recharge_amount = new_recharge_amount;
    }

    public int getOld_recharge_count() {
        return old_recharge_count;
    }

    public void setOld_recharge_count(int old_recharge_count) {
        this.old_recharge_count = old_recharge_count;
    }

    public double getOld_recharge_amount() {
        return old_recharge_amount;
    }

    public void setOld_recharge_amount(double old_recharge_amount) {
        this.old_recharge_amount = old_recharge_amount;
    }

    public int getNew_first_recharge_count() {
        return new_first_recharge_count;
    }

    public void setNew_first_recharge_count(int new_first_recharge_count) {
        this.new_first_recharge_count = new_first_recharge_count;
    }

    public double getNew_first_recharge_amount() {
        return new_first_recharge_amount;
    }

    public void setNew_first_recharge_amount(double new_first_recharge_amount) {
        this.new_first_recharge_amount = new_first_recharge_amount;
    }

    public int getOld_first_recharge_count() {
        return old_first_recharge_count;
    }

    public void setOld_first_recharge_count(int old_first_recharge_count) {
        this.old_first_recharge_count = old_first_recharge_count;
    }

    public double getOld_first_recharge_amount() {
        return old_first_recharge_amount;
    }

    public void setOld_first_recharge_amount(double old_first_recharge_amount) {
        this.old_first_recharge_amount = old_first_recharge_amount;
    }


    @Override
    public String toString() {
        return "空ID数据：\n" +
                "【是否首充】" + is_first_recharge + "\n" +
                "【充值数】" + recharge_count + "\n" +
                "【充值金额】" + recharge_amount + "\n" +
                "【新充值数】" + new_recharge_count + "\n" +
                "【新充值金额】" + new_recharge_amount + "\n" +
                "【旧充值数】" + old_recharge_count + "\n" +
                "【旧充值金额】" + old_recharge_amount + "\n" +
                "【首充数量】" + first_recharge_count + "\n" +
                "【首充金额】" + first_recharge_amount + "\n" +
                "【新首充数量】" + new_first_recharge_count + "\n" +
                "【新首充金额】" + new_first_recharge_amount + "\n" +
                "【旧首充数量】" + old_first_recharge_count + "\n" +
                "【旧首充金额】" + old_first_recharge_amount + "\n"
                ;
    }

}