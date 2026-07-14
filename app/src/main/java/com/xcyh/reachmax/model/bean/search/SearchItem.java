package com.xcyh.reachmax.model.bean.search;

import android.os.Parcelable;

import com.fold.recyclyerview.entity.MultiItemEntity;

import java.io.Serializable;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
@Keep
public abstract class SearchItem implements Serializable, Parcelable, MultiItemEntity {

    /** 正常搜索结果状态 **/
    public static final int TYPE_NORMAL     = 1;

    /** 已选中搜索结果状态 **/
    public static final int TYPE_SELECTED   = 2;


    protected boolean isChecked = false;

    protected int type = TYPE_NORMAL;

    public SearchItem(){

    }

    public abstract String getSearchName();

    public abstract String getSearchId();

    @NonNull
    public abstract SearchItem toSelectedItem();


    public void setChecked(boolean isCheck){
        isChecked = isCheck;
    }

    public boolean isChecked(){
        return isChecked;
    }


    public void setItemType(int type){
        this.type = type;
    }

    public int getItemType() {
        return type;
    }
}
