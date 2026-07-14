package com.xcyh.reachmax.model.bean.search;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
@Keep
public class SearchItemSerial extends SearchItem{

    public String campaign_id;

    public String campaign_name;



    public SearchItemSerial(){

    }

    public SearchItemSerial(Parcel in) {
        this.campaign_id = in.readString();
        this.campaign_name = in.readString();
        this.type = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.isChecked = in.readBoolean();
        } else {
            this.isChecked = in.readInt() == 1;
        }
    }


    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    @Override
    public String getSearchName() {
        return campaign_name;
    }

    @Override
    public String getSearchId() {
        return campaign_id;
    }

    @NonNull
    @Override
    public SearchItem toSelectedItem() {
        SearchItemSerial item = new SearchItemSerial();
        item.campaign_id = campaign_id;
        item.campaign_name = campaign_name;
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
        dest.writeString(this.campaign_id);
        dest.writeString(this.campaign_name);
        dest.writeInt(this.type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(this.isChecked);
        } else {
            dest.writeInt(this.isChecked ? 1 : 0);
        }
    }

    public static final Creator<SearchItemSerial> CREATOR = new Creator<SearchItemSerial>() {
        @Override
        public SearchItemSerial createFromParcel(Parcel source) {
            return new SearchItemSerial(source);
        }

        @Override
        public SearchItemSerial[] newArray(int size) {
            return new SearchItemSerial[size];
        }
    };
}
