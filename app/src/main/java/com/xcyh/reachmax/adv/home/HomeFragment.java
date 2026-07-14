package com.xcyh.reachmax.adv.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.baidu.baselibrary.base.IBaseView;
import com.baidu.baselibrary.base.fragment.BindingCustomFragment;
import com.baidu.baselibrary.fragment.CustomFragment;
import com.baidu.baselibrary.global.callback.OnVisibilityChangeListener;
import com.baidu.baselibrary.util.date.DateUtil;
import com.base.watcher.IWatched;
import com.base.watcher.Watcher;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.adv.AdvGotoHelper;
import com.xcyh.reachmax.adv.AdvListUtil;
import com.xcyh.reachmax.adv.ViewUtil;
import com.xcyh.reachmax.adv.home.callback.IAdvParamsController;
import com.xcyh.reachmax.adv.home.callback.IAdvSearchController;
import com.xcyh.reachmax.adv.pages.AdvListFragment;
import com.xcyh.reachmax.adv.search.SearchFragment;
import com.xcyh.reachmax.app.callback.SelectListener;
import com.xcyh.reachmax.app.meta.ui.widget.RippleResource;
import com.xcyh.reachmax.app.meta.utils.ViewPagerHelper2;
import com.xcyh.reachmax.app.utils.AnimatorUtil;
import com.xcyh.reachmax.databinding.FragmentHomeBinding;
import com.xcyh.reachmax.model.bean.PrincipalBody;
import com.xcyh.reachmax.model.bean.search.SearchItem;
import com.xcyh.reachmax.model.constant.AdvConst;
import com.xcyh.reachmax.model.constant.AdvStateFilter;
import com.xcyh.reachmax.model.custom.DateRange;
import com.xcyh.reachmax.model.event.AdvWatchEvent;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by haojiangfeng on 2024/11/12.
 */
public class HomeFragment extends BindingCustomFragment<FragmentHomeBinding, HomePresenter> implements IWatched, IBaseView<PrincipalBody> {

    private HomePagerAdapter mPagerAdapter;

    private SearchFragment mSearchFragment;



    @Override
    public void onStart() {
        super.onStart();
        CustomFragment fragment = mPagerAdapter.getVisibleFragment();
        if(fragment != null){
            fragment.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomFragment fragment = mPagerAdapter.getVisibleFragment();
        if(fragment != null){
            fragment.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CustomFragment fragment = mPagerAdapter.getVisibleFragment();
        if(fragment != null){
            fragment.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        CustomFragment fragment = mPagerAdapter.getVisibleFragment();
        if(fragment != null){
            fragment.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CustomFragment fragment = mPagerAdapter.getVisibleFragment();
        if(fragment != null){
            fragment.onDestroy();
        }
        Watcher.getInstance().unregisterObserver(this);
    }

    @Override
    public String className() {
        return "DiscoverFragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();

        mSearchFragment = new SearchFragment();
        getCustomFragmentManager().replaceFragment(mSearchFragment, mBinding.search);

        mBinding.viewPager.setOffscreenPageLimit(5);
        mPagerAdapter = new HomePagerAdapter(getCustomFragmentManager(), this);
        mBinding.viewPager.setAdapter(mPagerAdapter);

        initTabLayout();

        mBinding.selectLayout.setLifecycleOwner(this);
    }

    private void initTabLayout() {
        if (mContext == null) {
            return;
        }
        // Tab适配器、设置渐变色、indicator拉伸效果
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
        CommonNavigatorAdapter mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mPagerAdapter.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                return ViewUtil.getPagerTitleView(getActivity(), mPagerAdapter.getTitle(index), v -> {
                    if(GlobalAdvParams.isMultiSelectMode()) {
                        // 多选模式下不切换
                    } else {
                        mBinding.viewPager.setCurrentItem(index, false);
                    }
                });
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return ViewUtil.getPagerIndicator(getActivity());
            }
        };
        commonNavigator.setAdapter(mCommonNavigatorAdapter);
        mBinding.magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper2.bind(mBinding.magicIndicator, mBinding.viewPager);
    }

    @Override
    protected void initListener() {

        initGlobalParamsListener();

        initViewPagerListener();

        initTabSearchListeners();

        initFilterListeners();
    }

    private void initGlobalParamsListener(){
        Watcher.getInstance().registerDataSetObserver(this);

        // 多选模式
        GlobalAdvParams.sMultiSelectMode.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isMultiSelectMode) {
                mBinding.viewPager.setCanScroll(!isMultiSelectMode);
            }
        });

        // 更新时间
        GlobalAdvParams.sUpdateTime.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String eventDate) {
                if(!TextUtils.isEmpty(eventDate)){       // 2024-12-16T11:12:38+08:00
                    String updateTime = DateUtil.parseFromISO8061(eventDate, DateUtil.YMD_T_HMSs, DateUtil.formatYMDHMS); // 2024-12-16 11:12:38
                    String targetText = String.format("%s 更新", updateTime);
                    if(mBinding.tvUpdateTime.getText() == null || !mBinding.tvUpdateTime.getText().toString().equals(targetText)){
                        mBinding.tvUpdateTime.setText(targetText);
                        AnimatorUtil.startAnimator(mBinding.tvUpdateTime, R.animator.animator_update_time_scale);
                    }
                }
            }
        });
    }

    /**
     *  ViewPager tab切换
     */
    private void initViewPagerListener() {
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                int level = mPagerAdapter.getLevel(position);
                AdvListFragment fragment = mPagerAdapter.getTargetFragment(position);
                fragment.scrollToSelectParams();

                // 搜索框
                IAdvSearchController searchController = fragment.getSearchController();
                Bundle searchBundle = searchController.getSearchParams();
                String search_content = searchBundle.getString("search_text", "");
                String searchBarText = searchBundle.getString("search_bar_text", "");
                ArrayList<SearchItem> selectSearchItems = searchBundle.getParcelableArrayList("search_select_list");
                mSearchFragment.resetSearchBar(level, search_content, searchBarText, selectSearchItems);

                // 过滤选项
                IAdvParamsController filtersController = fragment.getFiltersController();
                List<AdvStateFilter> filterList = filtersController.getInitFilterList();
                AdvStateFilter currentFilter = filtersController.getSelectFilter();
                mBinding.stateFilterView.resetFilterLayout(filterList);
                mBinding.stateFilterView.resetSelectFilter(currentFilter);
                mBinding.tvFilter.setChecked(currentFilter != AdvStateFilter.ALL || GlobalAdvParams.hasSelectPrincipal());

                // 日期
                DateRange dateRange = filtersController.getDateRange();
                mBinding.tvFilterDate.setText(dateRange.dateRangeText);

                // 排序
                ViewUtil.showRankDrawable(mBinding.tvSpendFilter, filtersController.getRankRule());

                // 下钻展示条
                mBinding.selectLayout.updateLevel(level);
            }
        });
    }

    /**
     * 过滤器中的点击事件
     */
    private void initFilterListeners(){
        // 日期
        mBinding.tvFilterDate.setOnClickListener(v->{
            IAdvParamsController paramsController = mPagerAdapter.getVisibleFragment().getFiltersController();
            DateRange dateRange = paramsController.getDateRange();
            AdvGotoHelper.showSelectDateDialog(getActivity(), mBinding.tvFilterDate.getText().toString(), dateRange, new SelectListener() {
                @Override
                public void onSelect(Bundle bundle) {
                    String text = bundle.getString("text", "");
                    String dateStart = bundle.getString("dateStart", "");
                    String dateEnd = bundle.getString("dateEnd", "");
                    DateRange dateRange = new DateRange(dateStart, dateEnd, text);
                    IAdvParamsController paramsController = mPagerAdapter.getVisibleFragment().getFiltersController();
                    // 更新今天的日期
                    if(dateRange.isDifferent(paramsController.getDateRange())){
                        mBinding.tvFilterDate.setText(dateRange.dateRangeText);
                        paramsController.setDateRange(dateRange);
                    }
                    // 更新下钻的日期
                    int nextTabPosition = AdvConst.getNextTabPosition(paramsController.getLevel());
                    if(nextTabPosition < 4) {
                        forwardDateRange(nextTabPosition, dateRange);
                    }
                }
            });
        });
        // 消耗排序
        mBinding.tvSpendFilter.setOnClickListener(v->{
            IAdvParamsController paramsController = mPagerAdapter.getVisibleFragment().getFiltersController();
            String nextRankRule = paramsController.getNextRankRule();
            ViewUtil.showRankDrawable(mBinding.tvSpendFilter, nextRankRule);
            paramsController.setSelectRankType(nextRankRule);
        });
        // 筛选
        mBinding.tvFilter.setOnClickListener(v->{
            if(mBinding.stateFilterView.getVisibility() == View.VISIBLE){
                mBinding.stateFilterView.setVisibility(View.GONE);
                mBinding.stateFilterShadow.setVisibility(View.GONE);
            } else {
                mBinding.stateFilterView.setVisibility(View.VISIBLE);
                mBinding.stateFilterShadow.setVisibility(View.VISIBLE);
                IAdvParamsController paramsController = mPagerAdapter.getVisibleFragment().getFiltersController();
                AdvStateFilter filter = paramsController.getSelectFilter();
                mBinding.stateFilterView.resetSelectFilter(filter);
            }
            mSearchFragment.getSearchBar().setKeyboardVisible(false);
        });
        // 过滤选项
        mBinding.stateFilterView.setOnFilterSelectListener((filter, principalList) -> {
            IAdvParamsController paramsController = mPagerAdapter.getVisibleFragment().getFiltersController();
            paramsController.setSelectFilter(filter);
            if(AdvListUtil.isDifferent(GlobalAdvParams.sPrincipal.getValue(), principalList)){
                GlobalAdvParams.sPrincipal.setValue(new ArrayList<>(principalList));
            }
            mBinding.tvFilter.setChecked(filter != AdvStateFilter.ALL || GlobalAdvParams.hasSelectPrincipal());
        });
        mBinding.selectLayout.setOnVisibilityChangeListener(new OnVisibilityChangeListener() {
            @Override
            public void onVisibilityChange(int oldVisibility, int newVisibility) {
                mBinding.fakeSelectLayout.setVisibility(newVisibility);
            }
        });
        mBinding.stateFilterView.setOnVisibilityChangeListener(new OnVisibilityChangeListener() {
            @Override
            public void onVisibilityChange(int oldVisibility, int newVisibility) {
                mBinding.stateFilterShadow.setVisibility(newVisibility);
            }
        });
        mBinding.stateFilterShadow.setOnClickListener(v-> {
            // none
        });
    }

    /**
     * tab和搜索框的事件回调
     */
    private void initTabSearchListeners() {
        View rootView = getActivity().findViewById(android.R.id.content);
        rootView.setOnApplyWindowInsetsListener((view, windowInsets) -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                boolean imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime());
                if (imeVisible) {  // Soft keyboard is shown
                    mBinding.stateFilterView.setVisibility(View.GONE);
                }
            }
            return windowInsets;
        });
        mSearchFragment.setSearchCallback(new SearchFragment.SearchCallback() {

            @Override
            public void onSelectItems(CharSequence searchText, @Nullable ArrayList<SearchItem> searchResultList, @Nullable ArrayList<SearchItem> checkedList) {
                AdvListFragment advListFragment = mPagerAdapter.getVisibleFragment();
                advListFragment.clickSearchValue(searchText, searchResultList, checkedList);
            }

            @Override
            public void onSearchBarTextChanged(CharSequence s) {
                IAdvSearchController searchController = mPagerAdapter.getVisibleFragment().getSearchController();
                searchController.onSearchBarTextChanged(s);
            }
        });
    }

    @Override
    public void notifyWatcher(int event, Object object) {
        switch (event){
            case AdvWatchEvent.HOME_MULTI_SUBMIT:
            case AdvWatchEvent.HOME_ITEM_CLICK:
                int level = (int) object;
                IAdvParamsController currentParamsController = mPagerAdapter.getVisibleFragment().getFiltersController();
                DateRange currentDateRange = currentParamsController.getDateRange();
                int nextTabPosition = AdvConst.getNextTabPosition(level);
                if(nextTabPosition < 4) {
                    forwardDateRange(nextTabPosition, currentDateRange);
                    mBinding.viewPager.setCurrentItem(nextTabPosition);
                }
                break;

            case AdvWatchEvent.ON_SCROLL:
                Bundle bundle = (Bundle) object;
                int scrollLevel = bundle.getInt("scrollLevel", 0);
                int scrollDy = bundle.getInt("scrollDy", 0);
                mBinding.selectionLayout.setTranslationY(mBinding.selectionLayout.getTranslationY() - scrollDy);
                break;
        }
    }

    private void forwardDateRange(int position, DateRange dateRange){
        if(position < 4) {
            for(int i = position; i < 4; i++) {
                AdvListFragment<?> targetFragment = mPagerAdapter.getTargetFragment(i);
                IAdvParamsController targetParamsController = targetFragment.getFiltersController();
                targetParamsController.setDateRange(dateRange);
            }
        }
    }


    @Override
    protected void initData() {
        mPresenter.getPrincipal();
    }

    @Override
    public void onRequestSuccess(PrincipalBody principalBody) {
        mBinding.stateFilterView.initPrincipalList(principalBody.getData());
    }
}
