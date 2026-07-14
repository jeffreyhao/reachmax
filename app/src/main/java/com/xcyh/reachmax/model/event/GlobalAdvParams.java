package com.xcyh.reachmax.model.event;

import com.baidu.baselibrary.util.date.DateUtil;
import com.base.util.collection.ListUtil;
import com.xcyh.reachmax.adv.AdvListUtil;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.PrincipalItem;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.custom.DateRange;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

/**
 * 全局参数
 *
 *      包含：
 *          日期范围、是否扣费渠道、更新时间、负责人、下钻参数
 *
 * Created by haojiangfeng on 2024/12/13.
 */
public class GlobalAdvParams {

    /** 多选模式 **/
    public static MutableLiveData<Boolean>      sMultiSelectMode;

    /** 更新时间 **/
    public static MutableLiveData<String>       sUpdateTime;

    /** 负责人 **/
    public static MutableLiveData<ArrayList<PrincipalItem>>  sPrincipal;

    /** 下钻参数-账号 **/
    public static MutableLiveData<ArrayList<ItemData>>         sSelectAccounts;

    /** 下钻参数-广告系列 **/
    public static MutableLiveData<ArrayList<ItemData>>         sSelectSerials;

    /** 下钻参数-广告组 **/
    public static MutableLiveData<ArrayList<ItemData>>         sSelectGroups;



    static {
        sMultiSelectMode = new MutableLiveData<>(false);

        String today = DateUtil.getDateYMD();
        DateRange dateRange = new DateRange(today, today, "今天");

        sUpdateTime = new MutableLiveData<>("");
        sPrincipal = new MutableLiveData<>(new ArrayList<>());

        sSelectAccounts = new MutableLiveData<>(new ArrayList<>());
        sSelectSerials = new MutableLiveData<>(new ArrayList<>());
        sSelectGroups = new MutableLiveData<>(new ArrayList<>());
    }


    public static MutableLiveData<ArrayList<ItemData>> getSelectLiveData(int level){
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  return sSelectAccounts;
            case AdvItemLevel.ADV_SERIAL:   return sSelectSerials;
            case AdvItemLevel.ADV_GROUP:    return sSelectGroups;
            default:                        return null;
        }
    }

    public static @Nullable ItemData getSingleSelectItem(int level){
        MutableLiveData<ArrayList<ItemData>> liveData = getSelectLiveData(level);
        if(liveData == null || liveData.getValue() == null){
            return null;
        }
        ArrayList<ItemData> selectedList = liveData.getValue();
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:
            case AdvItemLevel.ADV_SERIAL:
            case AdvItemLevel.ADV_GROUP:
                return selectedList.size() == 1 ? selectedList.get(0) : null;
            default:
                return null;
        }
    }


    @NonNull
    public static String getAccounts(){
        ArrayList<ItemData> accountList = sSelectAccounts.getValue();
        return AdvListUtil.selectList2Param(AdvItemLevel.ADV_ACCOUNT, accountList);
    }

    @NonNull
    public static String getSerials(){
        ArrayList<ItemData> serialList = sSelectSerials.getValue();
        return AdvListUtil.selectList2Param(AdvItemLevel.ADV_SERIAL, serialList);
    }

    @NonNull
    public static String getGroups(){
        ArrayList<ItemData> groupList = sSelectGroups.getValue();
        return AdvListUtil.selectList2Param(AdvItemLevel.ADV_GROUP, groupList);
    }

    public static void setSelectAccount(ArrayList<ItemData> list){
        ArrayList<ItemData> selectedList = sSelectAccounts.getValue();
        if(AdvListUtil.isDifferent(AdvItemLevel.ADV_ACCOUNT, selectedList, list)){
            sSelectAccounts.setValue(list);
        }
    }

    public static void setSelectSerial(ArrayList<ItemData> list){
        ArrayList<ItemData> selectedList = sSelectSerials.getValue();
        if(AdvListUtil.isDifferent(AdvItemLevel.ADV_SERIAL, selectedList, list)){
            sSelectSerials.setValue(list);
        }
    }

    public static void setSelectGroup(ArrayList<ItemData> list){
        ArrayList<ItemData> selectedList = sSelectGroups.getValue();
        if(AdvListUtil.isDifferent(AdvItemLevel.ADV_GROUP, selectedList, list)){
            sSelectGroups.setValue(list);
        }
    }


    public static boolean haveSelected(int level, ItemData item){
        MutableLiveData<ArrayList<ItemData>> liveData = getSelectLiveData(level);
        if(liveData == null || liveData.getValue() == null){
            return false;
        }
        ArrayList<ItemData> selectedList = liveData.getValue();
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:
            case AdvItemLevel.ADV_SERIAL:
            case AdvItemLevel.ADV_GROUP:
                return AdvListUtil.inList(level, selectedList, item);
            default:
                return false;
        }
    }

    public static void removeSelectedLevel(int level) {
        MutableLiveData<ArrayList<ItemData>> liveData = getSelectLiveData(level);
        if(liveData == null){
            return;
        }
        ArrayList<ItemData> selectedList = liveData.getValue();
        if(ListUtil.isEmpty(selectedList)){
            return;
        }
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:
            case AdvItemLevel.ADV_SERIAL:
            case AdvItemLevel.ADV_GROUP:
                selectedList.clear();
                break;
        }
        liveData.postValue(selectedList);
    }


    public static void removeDataItem(int level, ItemData item){
        MutableLiveData<ArrayList<ItemData>> liveData = getSelectLiveData(level);
        if(liveData == null){
            return;
        }
        ArrayList<ItemData> selectedList = liveData.getValue();
        if(ListUtil.isEmpty(selectedList)){
            return;
        }
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:
            case AdvItemLevel.ADV_SERIAL:
            case AdvItemLevel.ADV_GROUP:
                if(AdvListUtil.removeItem(level, selectedList, item)){
                    liveData.postValue(selectedList);
                }
                break;
        }
    }

    public static boolean isMultiSelectMode(){
        return sMultiSelectMode.getValue();
    }


    public static ArrayList<String> getStringList(int level){
        MutableLiveData<ArrayList<ItemData>> liveData = getSelectLiveData(level);
        if(liveData == null){
            return new ArrayList<>();
        }
        ArrayList<ItemData> selectedList = liveData.getValue();
        if(ListUtil.isEmpty(selectedList) || level == AdvItemLevel.ADV_PLAN){
            return new ArrayList<>();
        }
        return AdvListUtil.getStringArrayList(level, selectedList);
    }

    public static boolean hasSelectPrincipal(){
        List<PrincipalItem> principalItem = sPrincipal.getValue();
        return principalItem == null || principalItem.size() > 0;
    }

    public static String getLaunchIds() {
        return AdvListUtil.principalList2String(sPrincipal.getValue());
    }

    public static void clearSelectParams() {
        ArrayList<ItemData> selectedAccounts = sSelectAccounts.getValue();
        if(ListUtil.isNotEmpty(selectedAccounts)){
            selectedAccounts.clear();
            sSelectAccounts.postValue(selectedAccounts);
        }
        ArrayList<ItemData> selectedSerials = sSelectSerials.getValue();
        if(ListUtil.isNotEmpty(selectedSerials)){
            selectedSerials.clear();
            sSelectSerials.postValue(selectedSerials);
        }
        ArrayList<ItemData> selectedGroups = sSelectGroups.getValue();
        if(ListUtil.isNotEmpty(selectedGroups)){
            selectedGroups.clear();
            sSelectGroups.postValue(selectedGroups);
        }
    }
}
