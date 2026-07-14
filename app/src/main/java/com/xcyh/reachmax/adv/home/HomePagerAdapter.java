package com.xcyh.reachmax.adv.home;


import com.baidu.baselibrary.fragment.CustomFragment;
import com.baidu.baselibrary.fragment.CustomFragmentManager;
import com.baidu.baselibrary.fragment.adapter.CustomFragmentPagerAdapter;
import com.baidu.baselibrary.fragment.adapter.CustomPageItem;
import com.xcyh.reachmax.adv.pages.AdvListFragment;
import com.xcyh.reachmax.adv.pages.account.AdvAccountListFragment;
import com.xcyh.reachmax.adv.pages.group.AdvGroupListFragment;
import com.xcyh.reachmax.adv.pages.plan.AdvPlanListFragment;
import com.xcyh.reachmax.adv.pages.serial.AdvSerialListFragment;
import com.xcyh.reachmax.model.bean.PagerBean;
import com.xcyh.reachmax.model.constant.AdvItemLevel;

import java.util.ArrayList;
import java.util.List;


public class HomePagerAdapter extends CustomFragmentPagerAdapter<PagerBean> {


    private List<PagerBean> mPageList;


    public HomePagerAdapter(CustomFragmentManager coverFragmentManager, CustomFragment parentFragment) {
        super(coverFragmentManager, parentFragment);
        mPageList = new ArrayList<>();
        mPageList.add(new PagerBean(AdvItemLevel.ADV_ACCOUNT, "广告账户", AdvAccountListFragment.class));
        mPageList.add(new PagerBean(AdvItemLevel.ADV_SERIAL, "广告系列", AdvSerialListFragment.class));
        mPageList.add(new PagerBean(AdvItemLevel.ADV_GROUP, "广告组", AdvGroupListFragment.class));
        mPageList.add(new PagerBean(AdvItemLevel.ADV_PLAN, "广告计划", AdvPlanListFragment.class));
        setData(mPageList);
    }


    @Override
    public CustomFragment getItem(int position, CustomPageItem<PagerBean> pageItem) {
        return mPageList.get(position).buildFragment();
    }


    public int size(){
        return mPageList.size();
    }

    public String getTitle(int index){
        return mPageList.get(index).title;
    }

    public @AdvItemLevel int getLevel(int position){
        return mPageList.get(position).level;
    }

    public AdvListFragment<?> getVisibleFragment() {
        AdvListFragment<?> fragment = (AdvListFragment<?>) super.getCurrentFragment();
        return fragment;
    }

    public AdvListFragment<?> getTargetFragment(int position) {
        CustomFragment fragment = getFragment(position);
        return (AdvListFragment<?>) fragment;
    }


}
