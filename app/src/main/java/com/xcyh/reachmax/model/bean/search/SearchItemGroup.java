package com.xcyh.reachmax.model.bean.search;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
@Keep
public class SearchItemGroup extends SearchItem{

    public String adset_id;

    public String adset_name;


    public SearchItemGroup(){

    }

    public SearchItemGroup(Parcel in) {
        this.adset_id = in.readString();
        this.adset_name = in.readString();
        this.type = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.isChecked = in.readBoolean();
        } else {
            this.isChecked = in.readInt() == 1;
        }
    }


    public String getAdset_id() {
        return adset_id;
    }

    public void setAdset_id(String adset_id) {
        this.adset_id = adset_id;
    }

    public String getAdset_name() {
        return adset_name;
    }

    public void setAdset_name(String adset_name) {
        this.adset_name = adset_name;
    }


    @Override
    public String getSearchName() {
        return adset_name;
    }

    @Override
    public String getSearchId() {
        return adset_id;
    }

    @NonNull
    @Override
    public SearchItem toSelectedItem() {
        SearchItemGroup item = new SearchItemGroup();
        item.adset_id = adset_id;
        item.adset_name = adset_name;
        item.setItemType(SearchItem.TYPE_SELECTED);
        item.setChecked(true);
        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.adset_id);
        dest.writeString(this.adset_name);
        dest.writeInt(this.type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(this.isChecked);
        } else {
            dest.writeInt(this.isChecked ? 1 : 0);
        }
    }

    public static final Creator<SearchItemGroup> CREATOR = new Creator<SearchItemGroup>() {
        @Override
        public SearchItemGroup createFromParcel(Parcel source) {
            return new SearchItemGroup(source);
        }

        @Override
        public SearchItemGroup[] newArray(int size) {
            return new SearchItemGroup[size];
        }
    };
}
