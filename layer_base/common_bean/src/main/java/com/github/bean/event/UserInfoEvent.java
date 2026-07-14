package com.github.bean.event;


import com.github.bean.user.AccountInfo;

public class UserInfoEvent {

    public boolean success;
    public AccountInfo data;

    public UserInfoEvent(boolean success, AccountInfo data) {
        this.success = success;
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserInfoEvent{" +
                "success=" + success +
                ", account=" + (data == null ? "null" : data.accountId) +
                '}';
    }
}
