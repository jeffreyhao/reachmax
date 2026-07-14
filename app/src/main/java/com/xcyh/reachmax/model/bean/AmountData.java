package com.xcyh.reachmax.model.bean;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2025/1/10.
 */
@Keep
public class AmountData {


    /**
     * spend : 6254.533
     * clicks : 11110
     * click_per_price : 0.563
     * impressions : 346052
     * install_count : 1832
     * new_install_count : 1536
     * old_install_count : 296
     * install_per_price : 3.414
     * recharge_count : 333
     * recharge_amount : 3057.499
     * new_recharge_count : 261
     * new_recharge_amount : 2395.855
     * old_recharge_count : 72
     * old_recharge_amount : 661.644
     * recharge_roi : 0.489
     * recharge_per_price : 18.782
     * first_recharge_count : 223
     * first_recharge_amount : 2166.712
     * new_first_recharge_count : 194
     * new_first_recharge_amount : 1882.203
     * old_first_recharge_count : 29
     * old_first_recharge_amount : 284.509
     * first_recharge_per_price : 28.047
     * first_recharge_roi : 0.346
     * recharge_account_ids : 1222571,1290356,1316044,1475161,1548811,1551261,1636174,1643793,1645318,2759035,1728469,1889698,1897251,2055569,2065621,2122999,2126927,2198037,2213098,2228475,2231200,2243267,2244736,2249054,2254163,2263175,2290063,2293705,2343848,2349713,2363014,2370436,2376078,2377994,2380597,2381586,2417637,2425477,2450917,2459592,2481362,2489090,2491587,2499764,2508206,2521725,2525352,2757928,2525439,2530645,2535252,2568115,2574884,2674100,2677358,2689338,2697199,2712435,2715181,2715384,2723404,2731354,2741921,2756998,2756999,2757077,2757094,2757029,2757033,2757044,2757068,2757070,2757071,2757076,2757080,2757117,2757118,2757137,2757155,2757162,2757174,2757176,2757188,2757189,2757205,2757210,2757233,2757252,2757305,2757313,2757331,2757339,2757423,2757439,2757449,2757501,2757502,2757504,2757507,2757526,2757527,2757529,2757540,2757547,2757569,2757580,2757581,2757602,2757585,2757596,2757605,2757613,2757620,2757625,27
     * first_recharge_account_ids : 1222571,1290356,1316044,1551261,1645318,2759035,1728469,2065621,2126927,2213098,2228475,2231200,2244736,2254163,2263175,2293705,2450917,2491587,2499764,2508206,2521725,2525352,2757928,2535252,2568115,2697199,2715181,2715384,2731354,2756998,2756999,2757077,2757094,2757029,2757033,2757044,2757068,2757070,2757071,2757076,2757080,2757117,2757118,2757137,2757155,2757162,2757174,2757176,2757188,2757189,2757205,2757210,2757233,2757252,2757305,2757313,2757331,2757339,2757423,2757439,2757449,2757501,2757502,2757504,2757507,2757526,2757527,2757529,2757540,2757547,2757569,2757580,2757581,2757602,2757585,2757596,2757605,2757613,2757620,2757625,2757632,2757663,2757666,2757668,2757681,2757700,2757708,2757717,2757720,2757729,2757734,2757739,2757754,2757890,2757756,2757761,2757876,2757763,2757764,2757765,2757778,2757834,2757813,2757833,2757841,2757863,2757909,2757920,2757926,2757944,2757951,2757969,2757974,2757981,27579
     * recharge_account_count : 115
     * first_recharge_account_count : 115
     */

    private double spend;
    private int clicks;
    private double click_per_price;
    private int impressions;
    private int install_count;
    private int new_install_count;
    private int old_install_count;
    private double install_per_price;
    private int recharge_count;
    private double recharge_amount;
    private int new_recharge_count;
    private double new_recharge_amount;
    private int old_recharge_count;
    private double old_recharge_amount;
    private double recharge_roi;
    private double recharge_per_price;
    private int first_recharge_count;
    private double first_recharge_amount;
    private int new_first_recharge_count;
    private double new_first_recharge_amount;
    private int old_first_recharge_count;
    private double old_first_recharge_amount;
    private double first_recharge_per_price;
    private double first_recharge_roi;
    private String recharge_account_ids;
    private String first_recharge_account_ids;
    private int recharge_account_count;
    private int first_recharge_account_count;

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getClick_per_price() {
        return click_per_price;
    }

    public void setClick_per_price(double click_per_price) {
        this.click_per_price = click_per_price;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getInstall_count() {
        return install_count;
    }

    public void setInstall_count(int install_count) {
        this.install_count = install_count;
    }

    public int getNew_install_count() {
        return new_install_count;
    }

    public void setNew_install_count(int new_install_count) {
        this.new_install_count = new_install_count;
    }

    public int getOld_install_count() {
        return old_install_count;
    }

    public void setOld_install_count(int old_install_count) {
        this.old_install_count = old_install_count;
    }

    public double getInstall_per_price() {
        return install_per_price;
    }

    public void setInstall_per_price(double install_per_price) {
        this.install_per_price = install_per_price;
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

    public double getRecharge_roi() {
        return recharge_roi;
    }

    public void setRecharge_roi(double recharge_roi) {
        this.recharge_roi = recharge_roi;
    }

    public double getRecharge_per_price() {
        return recharge_per_price;
    }

    public void setRecharge_per_price(double recharge_per_price) {
        this.recharge_per_price = recharge_per_price;
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

    public double getFirst_recharge_per_price() {
        return first_recharge_per_price;
    }

    public void setFirst_recharge_per_price(double first_recharge_per_price) {
        this.first_recharge_per_price = first_recharge_per_price;
    }

    public double getFirst_recharge_roi() {
        return first_recharge_roi;
    }

    public void setFirst_recharge_roi(double first_recharge_roi) {
        this.first_recharge_roi = first_recharge_roi;
    }

    public String getRecharge_account_ids() {
        return recharge_account_ids;
    }

    public void setRecharge_account_ids(String recharge_account_ids) {
        this.recharge_account_ids = recharge_account_ids;
    }

    public String getFirst_recharge_account_ids() {
        return first_recharge_account_ids;
    }

    public void setFirst_recharge_account_ids(String first_recharge_account_ids) {
        this.first_recharge_account_ids = first_recharge_account_ids;
    }

    public int getRecharge_account_count() {
        return recharge_account_count;
    }

    public void setRecharge_account_count(int recharge_account_count) {
        this.recharge_account_count = recharge_account_count;
    }

    public int getFirst_recharge_account_count() {
        return first_recharge_account_count;
    }

    public void setFirst_recharge_account_count(int first_recharge_account_count) {
        this.first_recharge_account_count = first_recharge_account_count;
    }

    @Override
    public String toString() {
        return "合计数据：\n" +
                "【消耗】     " + spend + "\n" +
                "【点击数】    " + clicks + "\n" +
                "【点击单价】  " + click_per_price + "\n" +
                "【曝光次数】  " + impressions + "\n" +
                "【安装数量】  " + install_count + "\n" +
                "【新安装数量】 " + new_install_count + "\n" +
                "【旧安装数量】 " + old_install_count + "\n" +
                "【安装单价】  " + install_per_price + "\n" +
                "【充值数】    " + recharge_count + "\n" +
                "【充值金额】  " + recharge_amount + "\n" +
                "【新充值数】  " + new_recharge_count + "\n" +
                "【新充值金额】" + new_recharge_amount + "\n" +
                "【旧充值数】  " + old_recharge_count + "\n" +
                "【旧充值金额】" + old_recharge_amount + "\n" +
                "【充值ROI】  " + recharge_roi + "\n" +
                "【充值单价】  " + recharge_per_price + "\n" +
                "【首充数量】  " + first_recharge_count + "\n" +
                "【首充金额】  " + first_recharge_amount + "\n" +
                "【新首充数量】" + new_first_recharge_count + "\n" +
                "【新首充金额】" + new_first_recharge_amount + "\n" +
                "【旧首充数量】" + old_first_recharge_count + "\n" +
                "【旧首充金额】" + old_first_recharge_amount + "\n" +
                "【首充单价】  " + first_recharge_per_price + "\n" +
                "【首充ROI】  " + first_recharge_roi + "\n" +
                "【充值账号数量】" + recharge_account_count + "\n" +
                "【首充账号数量】" + first_recharge_account_count + "\n"
                ;
    }


    public RechargeIds buildRechargeIds(){
        return new RechargeIds(recharge_account_ids, first_recharge_account_ids);
    }
}
