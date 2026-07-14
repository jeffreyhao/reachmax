package com.xcyh.reachmax.model.bean;

import java.util.List;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/12/13.
 */
@Keep
public class AdvPageBody {


    /**
     * data : [{"label":"2024-12-02 - 2024-12-03","uniq_id":"863548945104395863548945104395","date":0,"year":0,"month":0,"week":0,"hour":0,"platform":0,"ad_account":"863548945104395","ad_account_name":"Panda-ZT-PeakNovel-0729-周hq-W2A","spend":908.796,"recharge_count":99,"recharge_amount":754.055,"recharge_account_ids":"2194751,2195016,2228863,2230985,2236076,2239236,2246523,2269524,2321960,2353156,2354203,2357076,2360230,2365748,2381193,2382801,2426580,2430984,2441144,2524330,2537352,2631511,2642867,2677024,2677196,2677453,2678086,2678102,2678262,2678791,2679078,2679215,2679384,2679452,2386932,2679523","new_recharge_count":0,"new_recharge_amount":247.313,"old_recharge_count":65,"old_recharge_amount":506.742,"recharge_roi":0.83,"recharge_per_price":9.18,"install_count":69,"new_install_count":44,"old_install_count":25,"install_per_price":13.171,"first_recharge_count":21,"first_recharge_amount":172.584,"first_recharge_account_ids":"2230985,2269524,2631511,2677024,2677196,2677453,2678086,2678102,2678262,2678791,2679078,2679215,2679384,2679452,2386932,2679523","new_first_recharge_count":14,"new_first_recharge_amount":122.023,"old_first_recharge_count":7,"old_first_recharge_amount":50.561,"first_recharge_per_price":43.276,"first_recharge_roi":0.19,"recharge_account_count":36,"first_recharge_account_count":16,"clicks":2448,"impressions":29882,"task":{"id":8,"launch_id":28,"campaign_id":"120214036313320093","adset_id":"","type":"","action":"ACTIVE","name":"广告系列定时任务","status":0,"start_time":"2024-12-12T00:00:00+08:00","time_zone":"Asia/Shanghai","create_time":"2024-12-12T19:42:44+08:00","update_time":"2024-12-12T19:42:44+08:00"}}]
     * total : 1
     * last_update : 2024-12-10T14:03:05+08:00
     */

    private int total;
    private String last_update;
    private List<ItemData> data;
    private AmountData amount;
    private NoneIdAmount none_id_recharge_amount;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public List<ItemData> getData() {
        return data;
    }

    public void setData(List<ItemData> data) {
        this.data = data;
    }

    public AmountData getAmount() {
        return amount;
    }

    public void setAmount(AmountData amount) {
        this.amount = amount;
    }

    public NoneIdAmount getNone_id_recharge_amount() {
        return none_id_recharge_amount;
    }

    public void setNone_id_recharge_amount(NoneIdAmount none_id_recharge_amount) {
        this.none_id_recharge_amount = none_id_recharge_amount;
    }
}
