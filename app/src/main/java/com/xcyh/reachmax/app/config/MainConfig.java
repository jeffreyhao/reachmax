package com.xcyh.reachmax.app.config;


import android.content.Context;

import com.base.global.GlobalBuildConfig;
import com.baidu.baselibrary.main.MainTabConfig;
import com.baidu.baselibrary.global.Clazz;
import com.base.api.AppApi;
import com.base.api.LanguageApi;
import com.base.api.Logger;
import com.base.api.PreferenceApi;
import com.base.api.UserApi;
import com.base.api.VersionApi;
import com.base.net.cache.CacheConfig;
import com.base.res.Res;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.app.FoApplication;
import com.xcyh.reachmax.app.impl.IAppImpl;
import com.xcyh.reachmax.app.impl.ICacheTimeImpl;
import com.xcyh.reachmax.app.impl.ILanguageImpl;
import com.xcyh.reachmax.app.impl.ILogImpl;
import com.xcyh.reachmax.app.impl.IPreferenceImpl;
import com.xcyh.reachmax.app.impl.IUserImpl;
import com.xcyh.reachmax.app.impl.IVersionImpl;
import com.xcyh.reachmax.adv.home.HomeFragment;
import com.xcyh.reachmax.main.login.LoginActivity;
import com.xcyh.reachmax.main.MainTabActivity;
import com.xcyh.reachmax.main.splash.LaunchActivity;
import com.xcyh.reachmax.main.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haojiangfeng on 2023/8/31.
 */
public class MainConfig {

    public static void configOnApplicationAttach(Context base){


        Clazz.initApplicationClass(FoApplication.class);

        Clazz.mainActivityClass = MainTabActivity.class;
        Clazz.loginActivityClass = LoginActivity.class;
        Clazz.splashActivityClass = LaunchActivity.class;

        Clazz.classDiscoverFragment = HomeFragment.class;
        Clazz.classProfileFragment = MineFragment.class;


        Res.setContext(base);
        Res.setAppName(GlobalBuildConfig.APPLICATION_ID, GlobalBuildConfig.FLAVOR);

        initInterfaceImplementation();
    }

    /**
     * 初始化全局接口实现
     */
    private static void initInterfaceImplementation() {
        LanguageApi.iLanguage = ILanguageImpl.get();
        Logger.iLog = ILogImpl.get();
        PreferenceApi.iPreference = IPreferenceImpl.get();
        UserApi.iUser = IUserImpl.get();
        VersionApi.iVersion = IVersionImpl.get();
        AppApi.iApp = IAppImpl.get();
        CacheConfig.iCacheTime = ICacheTimeImpl.get();
    }

    public static void configOnApplicationCreate(){
        MainTabConfig.setMainTabConfig(new MainTabConfig.OnMainTabConfigCallback() {
            @Override
            public List<MainTabConfig.ItemStyle> config() {
                List<MainTabConfig.ItemStyle> list = new ArrayList<>();

                MainTabConfig.ItemStyle item1 = new MainTabConfig.ItemStyle();
                item1.tabNameRes = R.string.nav_home;
                item1.clazz = Clazz.classDiscoverFragment;
                item1.textColorRec = R.color.bottom_nav_item_selector;
                item1.imageDrawableRes = R.drawable.ic_home_selector;
                item1.animatorRes = R.animator.animator_tab_select;
                list.add(item1);

                MainTabConfig.ItemStyle item2 = new MainTabConfig.ItemStyle();
                item2.tabNameRes = R.string.nav_mine;
                item2.clazz = Clazz.classProfileFragment;
                item2.textColorRec = R.color.bottom_nav_item_selector;
                item2.imageDrawableRes = R.drawable.ic_mine_selector;
                item2.animatorRes = R.animator.animator_tab_select;
                list.add(item2);

                return list;
            }
        });
    }



}