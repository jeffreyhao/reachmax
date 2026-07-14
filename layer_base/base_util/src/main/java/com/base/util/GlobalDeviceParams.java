package com.base.util;

import android.text.TextUtils;

import com.base.api.PreferenceApi;

public class GlobalDeviceParams {

    /** 调试覆盖用的缓存 key，留空表示不覆盖、使用真实设备值 */
    private static final String KEY_OVERRIDE_COUNTRY = "debug_override_country";
    private static final String KEY_OVERRIDE_LANGUAGE = "debug_override_language";

    private static String countryCode;
    private static String deviceLanguage;


    static {
        countryCode = AppUtil.getCountryCode();
        deviceLanguage = AppUtil.getDeviceLanguage();
    }

    public static String getCountryCode(){
        String override = PreferenceApi.get(KEY_OVERRIDE_COUNTRY, "");
        if(!TextUtils.isEmpty(override)){
            return override;
        }
        return countryCode == null ? AppUtil.getCountryCode() : countryCode;
    }

    public static String getDeviceLanguage(){
        String override = PreferenceApi.get(KEY_OVERRIDE_LANGUAGE, "");
        if(!TextUtils.isEmpty(override)){
            return override;
        }
        return deviceLanguage == null ? AppUtil.getDeviceLanguage() : deviceLanguage;
    }

    /**
     * 调试覆盖国家编码，传空串则恢复真实值。
     */
    public static void overrideCountryCode(String country){
        PreferenceApi.put(KEY_OVERRIDE_COUNTRY, country == null ? "" : country);
        GlobalDeviceParams.countryCode = country;
    }

    /**
     * 调试覆盖设备语言，传空串则恢复真实值。
     */
    public static void overrideDeviceLanguage(String language){
        PreferenceApi.put(KEY_OVERRIDE_LANGUAGE, language == null ? "" : language);
    }

    public static String getOverrideCountryCode(){
        return PreferenceApi.get(KEY_OVERRIDE_COUNTRY, "");
    }

    public static String getOverrideDeviceLanguage(){
        return PreferenceApi.get(KEY_OVERRIDE_LANGUAGE, "");
    }

}
