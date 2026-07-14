package com.xcyh.reachmax.model.bean;

import android.os.Bundle;

import com.baidu.baselibrary.fragment.CustomFragmentUtil;
import com.xcyh.reachmax.adv.pages.AdvListFragment;
import com.xcyh.reachmax.model.constant.AdvItemLevel;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/12/24.
 */
@Keep
public class PagerBean {


    public @AdvItemLevel int level;

    public String title;

    public Class<? extends AdvListFragment> clazz;

    public PagerBean(@AdvItemLevel int level, String title, Class<? extends AdvListFragment> clazz){
        this.level = level;
        this.title = title;
        this.clazz = clazz;
    }

    public AdvListFragment buildFragment(){
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        bundle.putString("title", title);
        return CustomFragmentUtil.newInstance(clazz, bundle);
    }

}
