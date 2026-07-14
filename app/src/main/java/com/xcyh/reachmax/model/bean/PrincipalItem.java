package com.xcyh.reachmax.model.bean;

import android.text.TextUtils;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2025/1/10.
 */
@Keep
public class PrincipalItem {


    /**
     * launch_id : 35
     * launch_name : rongzihan
     * launch_description : advertiser
     * roles :
     * organization :
     * create_time : 2024-11-06 09:39:57
     * update_time : 2025-01-07 15:38:03
     */

    private int launch_id;
    private String launch_name;
    private String launch_description;
    private String roles;
    private String organization;
    private String create_time;
    private String update_time;

    private int status;
    private String nick_name;



    public PrincipalItem(){
        launch_id = 0;
        launch_name = "";
        launch_description = "";
        roles = "";
        organization = "";
        create_time = "";
        update_time = "";
        nick_name = "";
        status = -1;
    }


    public int getLaunch_id() {
        return launch_id;
    }

    public void setLaunch_id(int launch_id) {
        this.launch_id = launch_id;
    }

    public String getLaunch_name() {
        return launch_name;
    }

    public void setLaunch_name(String launch_name) {
        this.launch_name = launch_name;
    }

    public String getLaunch_description() {
        return launch_description;
    }

    public void setLaunch_description(String launch_description) {
        this.launch_description = launch_description;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getShowName(){
        return TextUtils.isEmpty(nick_name) ? "全部" : nick_name;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
