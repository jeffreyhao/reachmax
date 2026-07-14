package com.base.abs;

/**
 * Created by haojiangfeng on 2024/10/29.
 */
public interface IUser {

    String getUserId();

    String getAccessToken();

    void saveToken(String token);

    void refreshAccessToken();

    boolean isPaying();

    boolean isPremium();

    void requestUserInfo();

}
