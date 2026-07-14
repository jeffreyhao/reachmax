package com.xcyh.reachmax.adv.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.baidu.baselibrary.base.fragment.BindingCustomFragment;
import com.baidu.baselibrary.util.click.ClickUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.base.net.bean.ApiException;
import com.base.util.collection.ListUtil;
import com.base.watcher.IWatched;
import com.base.watcher.Watcher;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.adv.AdvListUtil;
import com.xcyh.reachmax.app.utils.InputMethodHelper;
import com.xcyh.reachmax.databinding.FragmentSearchResultBinding;
import com.xcyh.reachmax.model.bean.search.SearchItem;
import com.xcyh.reachmax.model.constant.AdvConst;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.event.AdvWatchEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by haojiangfeng on 2024/12/27.
 */
public class SearchFragment extends BindingCustomFragment<FragmentSearchResultBinding, SearchPresenter>
        implements ISearchView, IWatched {


    public interface SearchCallback {

        /**
         * 搜索结果 提交选项
         *
         * @param searchText        搜索关键词
         * @param searchResultList  搜索结果列表
         * @param checkedList       选中列表
         */
        void onSelectItems(CharSequence searchText, @Nullable ArrayList<SearchItem> searchResultList, @Nullable ArrayList<SearchItem> checkedList);

        /**
         * 搜索框中文字变化
         */
        void onSearchBarTextChanged(CharSequence s);
    }


    private int mLevel = AdvItemLevel.ADV_ACCOUNT;

    private int mPage = 1;
    private boolean hasMoreData = false;


    private SearchAdapter mAdapter;

    private SearchCallback mSearchCallback;

    private CharSequence mSearchText;

    private List<SearchItem> mSelectItems;

    private InputMethodHelper mInputMethodHelper;

    private int mScrollY = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initView() {
        super.initView();
        mBinding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SearchAdapter(null);
        mBinding.searchRecyclerView.setAdapter(mAdapter);
        mBinding.searchRecyclerView.setOnceRequestLimit(mPresenter.getLimit());
        mInputMethodHelper = new InputMethodHelper(getActivity());
    }

    @Override
    protected void initListener() {
        Watcher.getInstance().registerDataSetObserver(this);
        mBinding.searchBar.setOnSearchBarListener(new HomeSearchBar.OnSearchBarListener() {

            @Override
            public void onSearchBarTextChanged(CharSequence searchText) {  // clear搜索框或者搜索选项
                if(mSearchCallback != null){
                    mSearchCallback.onSearchBarTextChanged(searchText);
                }
                searchKeyWords(mLevel, searchText);
            }

            @Override
            public void onClickKeyboardSearch(CharSequence searchText) {    // 输入法中的搜索按钮
                if(ClickUtil.inQuickClick(100)){
                    return;
                }
                submitSearchResult();
            }

            @Override
            public void onClickKeyboardDone(CharSequence searchText) {

            }

            @Override
            public void onClickSelected() {     // 点击已选中项
                mBinding.contentLayout.setVisibility(View.VISIBLE);
                mBinding.searchShadow.setVisibility(View.VISIBLE);
                mAdapter.setNewData(mSelectItems);
            }
        });
        mBinding.searchRecyclerView.setOnLoadMoreListener(() -> {
            if(hasMoreData){
                searchMore(mLevel, mSearchText);
            }
        });
        mBinding.searchShadowTop.setOnClickListener(v -> {});
        mBinding.searchShadow.setOnClickListener(v -> {});

        mBinding.multiBtnCancel.setOnClickListener(v -> {
            mBinding.contentLayout.setVisibility(View.GONE);
            mBinding.searchShadow.setVisibility(View.GONE);
            if(ListUtil.isNotEmpty(mSelectItems)){
                for(SearchItem item : mSelectItems){
                    item.setChecked(true);
                }
            }
        });
        mBinding.multiBtnSubmit.setOnClickListener(v -> {
            submitSearchResult();
        });
        mAdapter.setOnItemCheckChangeListener(new SearchAdapter.OnItemCheckChangeListener() {
            @Override
            public void onItemCheckChange(CompoundButton checkBox, SearchItem item, int position) {
                int size = mAdapter.getCheckedList().size();
                mBinding.multiBtnSubmit.setText(String.format(Locale.CHINA, "(%d) 确定", size));
            }
        });
        // 键盘弹出关闭监听
        mInputMethodHelper.registerKeyboardVisibilityListener(isVisible -> {
            if (isVisible) { // 键盘弹出
                List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
                if(fragments.size() == 1){
                    String searchText = mBinding.searchBar.getEditText().getText().toString();
                    if(!TextUtils.isEmpty(searchText)){
                        searchKeyWords(mLevel, searchText);
                    }
                }
            }
        });
    }


    @Override
    protected void initData() {
        // none
    }


    public void setSearchCallback(SearchCallback callback){
        this.mSearchCallback = callback;
    }

    public HomeSearchBar getSearchBar(){
        return mBinding.searchBar;
    }


    public void resetSearchBar(int level,  String searchText, String searchBarText, ArrayList<SearchItem> selectSearchItems){
        mLevel = level;
        mSearchText = searchText;
        mSelectItems = selectSearchItems;
        mBinding.searchBar.resetSearchText(searchText);
        mBinding.searchBar.selectSearchResult(selectSearchItems);
        mBinding.contentLayout.setVisibility(View.GONE);
        mBinding.searchShadow.setVisibility(View.GONE);
    }

    /**
     * 搜索关键词
     *
     * @param searchText 关键词
     */
    private void searchKeyWords(int level, CharSequence searchText) {
        mSearchText = searchText;
        mAdapter.setSearchText(mSearchText.toString().trim());
        mPresenter.cancelAllRequest();
        mPage = 1;
        if(searchText.toString().trim().isEmpty()){
            hideAndClear();
            if(mSearchCallback != null){
                mSearchCallback.onSelectItems(mSearchText, null, null);
            }
        } else {
            mPresenter.doSearch(level, searchText, 1);
        }
    }

    private void searchMore(int level, CharSequence searchText){
        mPresenter.doSearch(level, searchText, mPage);
    }

    @Override
    public void onRequestSuccess(List<? extends SearchItem> searchItems) {
        // none
    }

    @Override
    public void onSearchSuccess(List<? extends SearchItem> searchItems, int total) {
        List<SearchItem> list = (List<SearchItem>) searchItems;
        hasMoreData = false;

        if(mPage == 1){
            mBinding.searchRecyclerView.setCurPage(1);
        }
        mBinding.searchRecyclerView.finishRequest(mPage);
        if(ListUtil.isNotEmpty(list)) {
            if(mPage == 1) {
                mAdapter.setNewData(list);
            } else {
                mAdapter.addData(list);
            }
            if(list.size() >= total) {
                hasMoreData = true;
                mPage++;
            }
        }
        if(!mAdapter.getData().isEmpty()){
            mBinding.contentLayout.setVisibility(View.VISIBLE);
            mBinding.searchShadow.setVisibility(View.VISIBLE);
        } else {
            toastNoData();
        }
    }


    private void toastNoData(){
        String noData = AdvConst.getLevelText(mLevel) + getResources().getString(R.string.no_search_data);
        ToastUtils.showToastCenter(noData);
    }


    @Override
    public void onRequestFail(ApiException e) {
        super.onRequestFail(e);
    }

    private void hideAndClear(){
        mBinding.contentLayout.setVisibility(View.GONE);
        mBinding.searchShadow.setVisibility(View.GONE);
        mBinding.multiBtnSubmit.setText("确定");
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mPage = 1;
    }

    private void submitSearchResult() {
        mBinding.searchBar.setKeyboardVisible(false);
        List<SearchItem> checkedList = mAdapter.getCheckedList();
        ArrayList<SearchItem> selectedList = AdvListUtil.buildSelectedList(checkedList);
        if(ListUtil.isEmpty(checkedList)){

        } else {
            mSelectItems = selectedList;
            mBinding.searchBar.selectSearchResult(selectedList);
        }

        if(mSearchCallback != null){
            mSearchCallback.onSelectItems(mSearchText, (ArrayList<SearchItem>) mAdapter.getData(), selectedList);
        }
        hideAndClear();
    }

    @Override
    public void notifyWatcher(int event, Object object) {
        switch (event) {
            case AdvWatchEvent.ON_SCROLL:
                Bundle bundle = (Bundle) object;
                int scrollLevel = bundle.getInt("scrollLevel", 0);
                int scrollDy = bundle.getInt("scrollDy", 0);
                mBinding.getRoot().setTranslationY(mBinding.getRoot().getTranslationY() - scrollDy);
                break;
        }
    }
}
