package com.xcyh.reachmax.model.manager;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.baidu.baselibrary.util.ui.AnimationUtil;
import com.baidu.baselibrary.util.App;
import com.baidu.baselibrary.util.sys.EventUtil;
import com.base.global.PreferencesUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.base.util.content.StringUtils;
import com.xcyh.reachmax.R;
import com.tencent.common.util.ActivityUtils;
import com.xcyh.reachmax.main.login.LoginActivity;
import com.xcyh.reachmax.model.bean.LoginBean;
import com.xcyh.reachmax.model.constant.SpKey;
import com.xcyh.reachmax.model.event.TokenInvalidEvent;
import com.xcyh.reachmax.model.request.RequestResult;

import java.util.List;

/**
 * 投手
 */
public class Pitcher {


    private static Pitcher mInstance = new Pitcher();
    public static Pitcher getInstance(){
        return mInstance;
    }
    private Pitcher(){
        init();
    }
    private void init(){
        String content = PreferencesUtil.get(SpKey.USER_LOGIN, "");
        if(!TextUtils.isEmpty(content)) {
            RequestResult<LoginBean> result = RequestResult.formatBody(content, "data", LoginBean.class);
            setData(result == null ? null : result.getData());
        }
    }




    private int userId;
    private String userName;
    private String token;
    private int dashboard;
    private boolean isAnalystAdmin;
    private List<String> role;

    private String avatar;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getNameCapitalizeWord() {
        if(TextUtils.isEmpty(userName)){
            return "";
        }
        return StringUtils.capitalize(userName.substring(0, 1));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDashboard() {
        return dashboard;
    }

    public void setDashboard(int dashboard) {
        this.dashboard = dashboard;
    }

    public boolean isAnalystAdmin() {
        return isAnalystAdmin;
    }

    public void setAnalystAdmin(boolean analystAdmin) {
        isAnalystAdmin = analystAdmin;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////



    public void setData(LoginBean data){
        if(data == null){
            return;
        }
        setToken(data.getToken());
        LoginBean.UserInfoBean bean = data.getUserInfo();
        if(bean != null){
            setUserId(bean.getUserId());
            setUserName(bean.getUserName());
            setAnalystAdmin(bean.isIsAnalystAdmin());
            setDashboard(bean.getDashboard());
            setRole(bean.getRole());
        }
    }


    public void clear(){
        this.userId = 0;
        this.userName = "";
        this.token = "";
        this.dashboard = 0;
        this.isAnalystAdmin = false;
        if(role != null){
            role.clear();
        }
        PreferencesUtil.put(SpKey.USER_LOGIN, "");
    }


    public void dealTokenInvalid(){
        Pitcher.getInstance().clear();
        Activity activity = ActivityUtils.getTopActivity();
        if(activity != null && !(activity instanceof LoginActivity)){
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            AnimationUtil.overridePendingTransition(activity, R.anim.dialog_fade_in, R.anim.anim_none);
            App.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.finish();
                    EventUtil.post(new TokenInvalidEvent());
                    ToastUtils.showToastCenter("token已过期，请重新登录");
                }
            }, 1500);
        }
    }


}
