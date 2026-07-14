package com.xcyh.reachmax.main.task;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.baidu.baselibrary.base.BaseActivity;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.ui.AnimationUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.ActivityTaskCenterBinding;
import com.xcyh.reachmax.app.meta.utils.ViewPagerHelper2;
import com.gyf.immersionbar.ImmersionBar;
import com.xcyh.reachmax.adv.ViewUtil;
import com.xcyh.reachmax.model.constant.TaskStatus;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Created by haojiangfeng on 2024/11/12.
 */
public class TaskCenterActivity extends BaseActivity<ActivityTaskCenterBinding, EmptyPresenter>  {

    private final List<String> mTabList = new ArrayList<>();
    private final List<Fragment> mFragments = new ArrayList<>();
    private FragmentStateAdapter mAdapter;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_center;
    }

    @Override
    protected void initTitleBar(Bundle savedInstanceState) {
        mRootBinding.titleBar.setTitle("任务中心");
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .autoStatusBarDarkModeEnable(true)
                .navigationBarColor(R.color.white)
                .autoNavigationBarDarkModeEnable(true)
                .init();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        mFragments.add(com.xcyh.reachmax.main.task.TaskListFragment.getInstance(TaskStatus.ALL));
        mFragments.add(com.xcyh.reachmax.main.task.TaskListFragment.getInstance(TaskStatus.NOT_START));
        mFragments.add(com.xcyh.reachmax.main.task.TaskListFragment.getInstance(TaskStatus.FINISH));
        mFragments.add(com.xcyh.reachmax.main.task.TaskListFragment.getInstance(TaskStatus.CANCEL));
        mAdapter = new FragmentStateAdapter(this) {

            @Override
            public int getItemCount() {
                return mFragments.size();
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return mFragments.get(position);
            }
        };
        mBinding.viewPager.setAdapter(mAdapter);
    }

    private void initTabLayout() {
        mTabList.add("全部");
        mTabList.add("未开始");
        mTabList.add("已完成");
        mTabList.add("已取消");

        // Tab适配器、设置渐变色、indicator拉伸效果
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(false);
        CommonNavigatorAdapter mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTabList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                return ViewUtil.getPagerTitleView(TaskCenterActivity.this, mTabList.get(index), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBinding.viewPager.setCurrentItem(index, false);
                    }
                });
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return ViewUtil.getPagerIndicator(TaskCenterActivity.this);
            }
        };
        commonNavigator.setAdapter(mCommonNavigatorAdapter);
        mBinding.magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper2.bind(mBinding.magicIndicator, mBinding.viewPager);
    }


    @Override
    public void finish() {
        super.finish();
        AnimationUtil.overridePendingTransition(this, R.anim.anim_none, R.anim.push_right_out);
    }
}
