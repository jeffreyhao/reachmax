package com.xcyh.reachmax.adv;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.collection.ListUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.PrincipalItem;
import com.xcyh.reachmax.model.bean.search.SearchItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2025/1/8.
 */
public class AdvListUtil {


    public static ArrayList<String> getStringArrayList(int level, List<ItemData> items) {
        ArrayList<String> list = new ArrayList<>();
        if (items == null || items.size() == 0) {
            return new ArrayList<>();
        }
        for (ItemData item : items) {
            list.add(item.getName(level));
        }
        return list;
    }

    public static String selectList2Param(int level, List<ItemData> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ItemData searchItem = list.get(i);
            if (i != 0) {
                sb.append(",");
            }
            sb.append(searchItem.getId(level));
        }
        return sb.toString();
    }

    public static String list2Param(List<? extends SearchItem> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            SearchItem searchItem = list.get(i);
            if (i != 0) {
                sb.append(",");
            }
            sb.append(searchItem.getSearchId());
        }
        return sb.toString();
    }

    public static String list2String(List<String> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            if (i != 0) {
                sb.append(",");
            }
            sb.append(item);
        }
        return sb.toString();
    }

    public static String principalList2String(List<PrincipalItem> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            PrincipalItem item = list.get(i);
            if (i != 0) {
                sb.append(",");
            }
            sb.append(item.getLaunch_id());
        }
        return sb.toString();
    }

    /**
     * 是否不同
     */
    public static boolean isListDifferent(int level, ArrayList<ItemData> leftList, ArrayList<ItemData> rightList) {
        if (leftList == null && rightList == null) {
            return false;
        }
        if (leftList == null || rightList == null) {
            return true;
        }
        if (leftList.size() != rightList.size()) {
            return true;
        }
        for (int i = 0; i < leftList.size(); i++) {
            String leftId = leftList.get(i).getId(level);
            String rightId = rightList.get(i).getId(level);
            if (leftId == null || !leftId.equals(rightId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 负责人列表 是否不同
     */
    public static boolean isDifferent(List<PrincipalItem> leftList, List<PrincipalItem> rightList){
        if(leftList == null && rightList == null){
            return false;
        }
        if(leftList == null || rightList == null){
            return true;
        }
        if(leftList.size() != rightList.size()){
            return true;
        }
        for(int i = 0; i < leftList.size(); i++){
            PrincipalItem left = leftList.get(i);
            boolean containsLeft = false;
            for(int j = 0; j < rightList.size(); j++){
                PrincipalItem right = rightList.get(i);
                if(left.getLaunch_id() == right.getLaunch_id()){
                    containsLeft = true;
                    break;
                }
            }
            if(!containsLeft){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否不同
     */
    public static boolean isDifferent(ArrayList<String> leftList, ArrayList<String> rightList){
        if(leftList == null && rightList == null){
            return false;
        }
        if(leftList == null || rightList == null){
            return true;
        }
        if(leftList.size() != rightList.size()){
            return true;
        }
        for(int i = 0; i < leftList.size(); i++){
            String left = leftList.get(i);
            String right = rightList.get(i);
            if(!left.equals(right)){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否不同
     */
    public static boolean isDifferent(int level, ArrayList<ItemData> leftList, ArrayList<ItemData> rightList){
        if(leftList == null && rightList == null){
            return false;
        }
        if(leftList == null || rightList == null){
            return true;
        }
        if(leftList.size() != rightList.size()){
            return true;
        }
        for(int i = 0; i < leftList.size(); i++){
            String leftId = leftList.get(i).getId(level);
            String rightId = rightList.get(i).getId(level);
            if(leftId != null && rightId != null && !leftId.equals(rightId)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> convertList(int level, ItemData itemData){
        String id = itemData.getId(level);
        ArrayList<String> list = new ArrayList<>();
        list.add(id);
        return list;
    }

    /**
     * 是否在列表中
     */
    public static boolean inList(int level, List<ItemData> list, ItemData data){
        if(data == null || ListUtil.isEmpty(list)){
            return false;
        }
        String id = data.getId(level);
        if(TextUtils.isEmpty(id)){
            return false;
        }
        for(ItemData item: list){
            if(id.equals(item.getId(level))){
                return true;
            }
        }
        return false;
    }


    /**
     * 设置按钮 enable 状态
     */
    public static void setBtnEnable(LinearLayout layout, boolean enable){
        layout.setEnabled(enable);
        if(enable){
            layout.setBackgroundResource(R.drawable.selector_item_btn);
        } else {
            layout.setBackgroundResource(R.drawable.bg_filter_box);
        }
        for(int i = 0; i < layout.getChildCount(); i++){
            View child = layout.getChildAt(i);
            if(child instanceof TextView){
                child.setEnabled(enable);
            }
        }
    }

    public static boolean removeItem(int level, ArrayList<ItemData> list, ItemData data){
        if(data == null || list == null || list.size() == 0){
            return false;
        }
        String dataId = data.getId(level);
        if(TextUtils.isEmpty(dataId)){
            return false;
        }
        ItemData removeTarget = null;
        for(ItemData item: list){
            String itemId = item.getId(level);
            if(dataId.equals(itemId)){
                removeTarget = item;
                break;
            }
        }
        if(removeTarget != null){
            list.remove(removeTarget);
            return true;
        }
        return false;
    }



    public static ArrayList<SearchItem> buildSelectedList(List<SearchItem> originList){
        ArrayList<SearchItem> list = new ArrayList<>();
        if(ListUtil.isEmpty(originList)){
            return list;
        }
        for(SearchItem origin : originList){
            list.add(origin.toSelectedItem());
        }
        return list;
    }

    public static boolean containsPrincipalItem(List<PrincipalItem> principalList, PrincipalItem data) {
        if(ListUtil.isEmpty(principalList) || data == null){
            return false;
        }
        for(PrincipalItem item: principalList){
            if(item.getLaunch_id() == data.getLaunch_id()){
                return true;
            }
        }
        return false;
    }

    public static List<PrincipalItem> addPrincipalItem(@NonNull List<PrincipalItem> list, PrincipalItem data){
        if(data == null){
            return list;
        }
        boolean contains = false;
        for(PrincipalItem item: list){
            if(item.getLaunch_id() == data.getLaunch_id()){
                contains = true;
                break;
            }
        }
        if(!contains){
            list.add(data);
        }
        return list;
    }

    public static List<PrincipalItem> removePrincipalItem(@NonNull List<PrincipalItem> list, PrincipalItem data){
        if(list.size() == 0 || data == null){
            return list;
        }
        PrincipalItem toRemoveItem = null;
        for(PrincipalItem item: list){
            if(item.getLaunch_id() == data.getLaunch_id()){
                toRemoveItem = item;
                break;
            }
        }
        if(toRemoveItem != null){
            list.remove(toRemoveItem);
        }
        return list;
    }
}
