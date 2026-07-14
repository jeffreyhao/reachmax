package com.xcyh.reachmax.model.bean.search;

import android.os.Build;
import android.os.Parcel;

import java.util.List;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/12/18.
 */
@Keep
public class SearchAccountBody {


    private List<SearchItemAccount> data;


    public List<SearchItemAccount> getData() {
        return data;
    }

    public void setData(List<SearchItemAccount> data) {
        this.data = data;
    }



    @Keep
    public static class SearchItemAccount extends SearchItem {

        public String ad_account;

        public String ad_account_name;


        public SearchItemAccount(){

        }

        public SearchItemAccount(Parcel in) {
            this.ad_account = in.readString();
            this.ad_account_name = in.readString();
            this.type = in.readInt();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.isChecked = in.readBoolean();
            } else {
                this.isChecked = in.readInt() == 1;
            }
        }


        public String getAd_account() {
            return ad_account;
        }

        public void setAd_account(String ad_account) {
            this.ad_account = ad_account;
        }

        public String getAd_account_name() {
            return ad_account_name;
        }

        public void setAd_account_name(String ad_account_name) {
            this.ad_account_name = ad_account_name;
        }

        @Override
        public String getSearchName() {
            return ad_account_name;
        }

        @Override
        public String getSearchId() {
            return ad_account;
        }

        @Override
        public com.xcyh.reachmax.model.bean.search.SearchItem toSelectedItem() {
            SearchItemAccount item = new SearchItemAccount();
            item.ad_account = ad_account;
            item.ad_account_name = ad_account_name;
            item.setItemType(com.xcyh.reachmax.model.bean.search.SearchItem.TYPE_SELECTED);
            item.setChecked(true);
            return item;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.ad_account);
            dest.writeString(this.ad_account_name);
            dest.writeInt(this.type);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                dest.writeBoolean(this.isChecked);
            } else {
                dest.writeInt(this.isChecked ? 1 : 0);
            }
        }

        public static final Creator<SearchItemAccount> CREATOR = new Creator<SearchItemAccount>() {
            @Override
            public SearchItemAccount createFromParcel(Parcel source) {
                return new SearchItemAccount(source);
            }

            @Override
            public SearchItemAccount[] newArray(int size) {
                return new SearchItemAccount[size];
            }
        };

    }

}
