package com.xcyh.reachmax.app.impl;

import com.base.abs.ILanguage;
import com.xcyh.reachmax.app.meta.utils.LanguageUtil;

/**
 * Created by haojiangfeng on 2024/10/30.
 */
public class ILanguageImpl implements ILanguage {

    public static ILanguageImpl get(){
        return new ILanguageImpl();
    }


    @Override
    public String getLanguage() {
        return LanguageUtil.getLanguage();
    }
}
