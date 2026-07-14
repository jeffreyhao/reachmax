package com.xcyh.reachmax.adv.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.baidu.baselibrary.recycler.BindingRecyclerAdapter;
import com.baidu.baselibrary.recycler.OnItemChildClickListener;
import com.baidu.baselibrary.util.App;
import com.baidu.baselibrary.util.sys.LogUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.baidu.baselibrary.widget.SwitchButton;
import com.base.net.bean.ApiException;
import com.base.util.collection.ListUtil;
import com.base.util.content.StringUtils;
import com.base.util.ui.UIUtil;
import com.base.watcher.IWatched;
import com.base.watcher.Watcher;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.adv.AdvGotoHelper;
import com.xcyh.reachmax.adv.home.callback.IAdvParamsController;
import com.xcyh.reachmax.adv.home.callback.OnParamsChangeListener;
import com.xcyh.reachmax.app.callback.ConfirmListener;
import com.xcyh.reachmax.app.callback.SubmitListener;
import com.xcyh.reachmax.app.meta.dialog.base.ClipBoardDialog;
import com.xcyh.reachmax.databinding.ItemAdvBinding;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.PrincipalItem;
import com.xcyh.reachmax.model.bean.search.SearchItem;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.AdvStateFilter;
import com.xcyh.reachmax.model.constant.ClickAction;
import com.xcyh.reachmax.model.constant.TaskStatus;
import com.xcyh.reachmax.model.event.AdvWatchEvent;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by haojiangfeng on 2024/11/12.
 */
public abstract class AdvListFragment<P extends AdvListPresenter>
        extends BaseAdvListFragment<ItemData, P>
        implements IWatched, IAdvListView, OnItemChildClickListener<ItemAdvBinding, ItemData>, OnParamsChangeListener {

    private static boolean sTabScrollEnable = true;
    private static int sScrollLimit  = App.getContext().getResources().getDimensionPixelSize(R.dimen.height_search_bar);
    private static int sScrollSync   = 0;


    /**
     * Tab级别
     */
    protected @AdvItemLevel int mAdvLevel;

    /**
     * adapter
     */
    protected AdvListRecyclerAdapter mListAdapter;

    /**
     * 过滤参数控制器
     */
    protected AdvParamsController mFiltersController;

    /**
     * 搜索条参数控制器
     */
    protected AdvSearchController mSearchController;

    /**
     * 是否需要请求数据
     */
    protected boolean mNeedRequest = true;


    protected Runnable postClearRequestRunnable = new Runnable() {
        @Override
        public void run() {
            clearAndTryRequest();
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:        // 滑动结束
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:    // 主动滑动
                case RecyclerView.SCROLL_STATE_SETTLING:    // 惯性滑动
                    UIUtil.hideSoftKeyboard(getActivity());
                    break;
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int targetScrollSync = 0;
            if(dy > 0) {            // 向上滑动
                if(sScrollSync < sScrollLimit) {
                    targetScrollSync = (sScrollSync + dy < sScrollLimit) ? dy : sScrollLimit - sScrollSync;
                }
            } else {                // 向下滑动
                if(sScrollSync > 0) {
                    targetScrollSync = (sScrollSync + dy > 0) ? dy : - sScrollSync;
                }
            }
            sScrollSync += targetScrollSync;
            LogUtil.v("onScrolled", "Limit:" + sScrollLimit + ", dy = " + dy + ",  targetScrollSync= " + targetScrollSync);
            if(sTabScrollEnable && targetScrollSync != 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("scrollLevel", mAdvLevel);
                bundle.putInt("scrollDy", targetScrollSync);
                Watcher.getInstance().notifyWatcher(AdvWatchEvent.ON_SCROLL, bundle);
            }
        }
    };


    public AdvListFragment(){

    }

    protected abstract List<AdvStateFilter> createFilteStateList();


    public IAdvParamsController getFiltersController(){
        return mFiltersController;
    }

    public AdvSearchController getSearchController(){
        return mSearchController;
    }



    @CallSuper
    @Override
    protected void intPresenter() {
        super.intPresenter();
        mPresenter.init(this, mAdvLevel);
        mFiltersController = new AdvParamsController(mAdvLevel, createFilteStateList(), this);
        mSearchController = new AdvSearchController(mAdvLevel, this);
    }


    @CallSuper
    @Override
    protected void initListener() {
        super.initListener();
        Watcher.getInstance().registerDataSetObserver(this);
        initGlobalParamListeners();

        mBinding.recyclerView.addOnScrollListener(mOnScrollListener);
        mListAdapter.setOnItemCheckChangeListener(new AdvListRecyclerAdapter.OnItemCheckChangeListener() {
            @Override
            public void onItemCheckChange(CompoundButton checkBox, ItemData item, int position) {
                int size = mListAdapter.getCheckedList().size();
                mBinding.multiBtnSubmit.setText(String.format(Locale.CHINA, "(%d) 确定", size));
            }
        });
        mBinding.multiBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 多选取消
                mBinding.multiBtnLayout.setVisibility(View.GONE);
                mBinding.bottomTotalLayout.setVisibility(View.VISIBLE);
                mListAdapter.setSingleSelect(true);
                GlobalAdvParams.sMultiSelectMode.postValue(false);
            }
        });
        mBinding.multiBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // 多选确定
                ArrayList<ItemData> checkedList = mListAdapter.getCheckedList();
                switch (mAdvLevel){
                    case AdvItemLevel.ADV_ACCOUNT:  GlobalAdvParams.setSelectAccount(checkedList);   break;
                    case AdvItemLevel.ADV_SERIAL:   GlobalAdvParams.setSelectSerial(checkedList);    break;
                    case AdvItemLevel.ADV_GROUP:    GlobalAdvParams.setSelectGroup(checkedList);     break;
                }
                mBinding.multiBtnLayout.setVisibility(View.GONE);
                mBinding.bottomTotalLayout.setVisibility(View.VISIBLE);
                mListAdapter.setSingleSelect(true);
                GlobalAdvParams.sMultiSelectMode.postValue(false);

                // 通知home页切换tab
                Watcher.getInstance().notifyWatcher(AdvWatchEvent.HOME_MULTI_SUBMIT, mAdvLevel);
            }
        });
    }

    private void initGlobalParamListeners(){
        GlobalAdvParams.sPrincipal.observe(this, new Observer<List<PrincipalItem>>() {
            @Override
            public void onChanged(List<PrincipalItem> principal) {
                clearAndTryRequest();
            }
        });
        initSelectParamsListeners();
    }

    protected abstract void initSelectParamsListeners();


    protected void postClearAndTryRequest(){
        App.removeRunnable(postClearRequestRunnable);
        App.postDelayed(postClearRequestRunnable, 100);
    }

    protected void notifyRangeAll(){
        int size = mListAdapter.getData().size();
        if(size > 0){
            mListAdapter.notifyItemRangeChanged(0, size);
        }
    }


    public void clearAndTryRequest(){
        clear();
        if(isResumed()){
            doRequest();
        } else {
            mNeedRequest = true;
        }
    }

    public void clear(){
        mNeedRequest = true;
        mListAdapter.clear();
        getList().clear();
        setPage(1);
        mBinding.refreshLayout.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public RecyclerView.Adapter<?> createAdapter() {
        mListAdapter = new AdvListRecyclerAdapter(mAdvLevel);
        mListAdapter.setOnItemChildClickListener(this);
        return mListAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    protected void setData() {
        mAdvLevel = getLevel();
        super.setData();
    }

    @Override
    public void getData() {
        if(isResumed()){
            doRequest();
        }
    }

    @Override
    public void doLoadMore() {
        doRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNeedRequest){
            doRequest();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Watcher.getInstance().unregisterObserver(this);
    }

    /**
     * 点击搜索结果item
     */
    public void clickSearchValue(CharSequence searchText, @Nullable ArrayList<SearchItem> searchResultList, @Nullable ArrayList<SearchItem> checkedList){
        mSearchController.onClickSearchValue(searchText, searchResultList, checkedList);
        clearAndTryRequest();
    }

    @Override
    public void onParamsChanged() {
        clearAndTryRequest();
    }


    ////////////////////////////////    item点击相关的逻辑 start    ////////////////////////////////

    @CallSuper
    @Override
    public void notifyWatcher(int event, Object object){
        switch (event){
            case AdvWatchEvent.ITEM_ADV_NAME:
                Bundle bundle = (Bundle) object;
                int level = bundle.getInt("level");
                ItemData data = (ItemData) bundle.getSerializable("itemData");
                onNameClick (level, data);
                break;

            case AdvWatchEvent.EXTEND_5_MIN:
                clear();
                mNeedRequest = true;
                break;

            case AdvWatchEvent.ON_SCROLL:
                Bundle scrollBundle = (Bundle) object;
                int scrollLevel = scrollBundle.getInt("scrollLevel", 0);
                int scrollDy = scrollBundle.getInt("scrollDy", 0);
                if(scrollLevel != mAdvLevel) {
                    mBinding.recyclerView.removeOnScrollListener(mOnScrollListener);
                    mBinding.recyclerView.scrollBy(0, scrollDy);
                    mBinding.recyclerView.addOnScrollListener(mOnScrollListener);
                }
                break;
        }
    }


    @Override
    public void onItemChildClick(BindingRecyclerAdapter.BindingViewHolder<ItemAdvBinding, ItemData> holder, View view, int clickAction, ItemData data, int position) {
        switch (clickAction){
            case ClickAction.ITEM_ADV_NAME:  // 单选
                if(getLevel() == AdvItemLevel.ADV_PLAN){
                    return;
                }
                ArrayList<ItemData> selectList = new ArrayList<>();
                selectList.add(data);
                switch (mAdvLevel){
                    case AdvItemLevel.ADV_ACCOUNT:  GlobalAdvParams.setSelectAccount(selectList);   break;
                    case AdvItemLevel.ADV_SERIAL:   GlobalAdvParams.setSelectSerial(selectList);    break;
                    case AdvItemLevel.ADV_GROUP:    GlobalAdvParams.setSelectGroup(selectList);     break;
                }
                notifyRangeAll();

                // 通知home页切换tab
                Watcher.getInstance().notifyWatcher(AdvWatchEvent.HOME_ITEM_CLICK, mAdvLevel);
                break;

            case ClickAction.ITEM_ADV_MULTI:    // 多选
                mBinding.multiBtnLayout.setVisibility(View.VISIBLE);
                mBinding.bottomTotalLayout.setVisibility(View.GONE);
                GlobalAdvParams.sMultiSelectMode.postValue(true);

                int size = mListAdapter.getCheckedList().size();
                mBinding.multiBtnSubmit.setText(String.format(Locale.CHINA, "(%d) 确定", size));
                break;

            case ClickAction.ITEM_DETAIL:       // 详情
                AdvGotoHelper.gotoAdvDetail(getActivity(), mAdvLevel, data);
                break;

            case ClickAction.ITEM_BUDGET:       // 修改预算
                AdvGotoHelper.gotoAdvBudget(getActivity(), data, new SubmitListener() {
                    @Override
                    public void onClickSubmit(int action, Bundle bundle) {
                        int budget = bundle.getInt("budget", 0);
                        ItemData itemData = (ItemData) bundle.getSerializable("itemData");
                        if(itemData != null){
                            mPresenter.modifyBudget(mAdvLevel, itemData, itemData.getState(mAdvLevel), String.valueOf(budget));
                        }
                    }
                });
                break;

            case ClickAction.ITEM_SWITCH:       // 开关
                SwitchButton switchView = (SwitchButton) view;
                AdvGotoHelper.showAdvSwitchDialog(getActivity(), mAdvLevel, switchView, new ConfirmListener(){
                    @Override
                    public void onClickConfirm() {
                        String state = switchView.isChecked() ? AdvActionState.ACTIVE : AdvActionState.PAUSED;
                        mPresenter.modifySwitch(mAdvLevel, data, state);
                    }
                });
                break;

            case ClickAction.ITEM_SWITCH_TIMER: // 定时器开关
                if(mAdvLevel != AdvItemLevel.ADV_ACCOUNT){
                    AdvGotoHelper.gotoAdvTimer(getActivity(), mAdvLevel, data, new SubmitListener() {
                        @Override
                        public void onClickSubmit(int taskAction, Bundle bundle) {
                            ItemData itemData = (ItemData) bundle.getSerializable("itemData");
                            String actionValue = bundle.getString("action", "");
                            String startTime = bundle.getString("startTime", "");
                            int taskId = bundle.getInt("taskId", -1);
                            int status = bundle.getInt("status", TaskStatus.NOT_START);
                            assert itemData != null;
                            switch (taskAction){
                                case ClickAction.ITEM_TASK_CREATE:
                                    mPresenter.createSwitchTimer(mAdvLevel, itemData, actionValue, startTime);
                                    break;
                                case ClickAction.ITEM_TASK_MODIFY:
                                case ClickAction.ITEM_TASK_CANCEL:
                                    mPresenter.modifyTimer(mAdvLevel, itemData, taskId, status, actionValue, startTime, -1);
                                    break;
                            }
                        }
                    });
                }
                break;

            case ClickAction.ITEM_BUDGET_TIMER: // 预算定时器
                AdvGotoHelper.gotoBudgetTimer(getActivity(), mAdvLevel, data, new SubmitListener() {
                    @Override
                    public void onClickSubmit(int taskAction, Bundle bundle) {
                        ItemData itemData = (ItemData) bundle.getSerializable("itemData");
                        String startTime = bundle.getString("startTime", "");
                        int budget = bundle.getInt("budget", 0);
                        int taskId = bundle.getInt("taskId", -1);
                        int status = bundle.getInt("status", TaskStatus.ALL);
                        assert itemData != null;
                        switch (taskAction){
                            case ClickAction.ITEM_TASK_CREATE:
                                mPresenter.createBudgetTimer(mAdvLevel, itemData, startTime, budget);
                                break;
                            case ClickAction.ITEM_TASK_MODIFY:
                            case ClickAction.ITEM_TASK_CANCEL:
                                mPresenter.modifyTimer(mAdvLevel, itemData, taskId, status, AdvActionState.CHANGE_DAILY_BUDGET, startTime, budget);
                                break;
                        }
                    }
                });
                break;

            case ClickAction.ITEM_BUY_SPEND:    // Item充值数据
                ClipBoardDialog.show(getActivity(), data.toString());
                break;

            case ClickAction.ITEM_BUY_USERS:    // 购买人数
                ClipBoardDialog.show(getActivity(), data.getUserIdsText());
                break;

            default:
                onItemChildClick(view, clickAction, data, position);
                break;
        }
    }

    protected void onItemChildClick(View view, @ClickAction int action, ItemData data, int position){

    }

    ////////////////////////////////    item点击相关的逻辑 end    ////////////////////////////////


    /**
     * 会走到 {@link #getReportList(boolean, int)}
     */
    public abstract void doRequest();

    public void getReportList(boolean showLoading, int page){
        mPresenter.getAdvReport(mAdvLevel, showLoading, page, mFiltersController, mSearchController);
    }


    @Override
    public void onRequestSuccess(@NonNull List<ItemData> t) {
        LogUtil.d("adv", "super.onRequestSuccess() before -> \n      ƒmPage: " + getPage() + ", data size: " + (mListAdapter.getData() == null ? 0 : mListAdapter.getData().size()));
        super.onRequestSuccess(t);
        LogUtil.d("adv", "super.onRequestSuccess() after -> \n      mPage: " + getPage() + ", data size: " + (mListAdapter.getData() == null ? 0 : mListAdapter.getData().size()));

        // 如果tab已上滑，就把 RecyclerView 也往上移
        if(sTabScrollEnable && mBinding.recyclerView.geCurPage() == 1 && sScrollSync != 0) {
            mBinding.recyclerView.scrollBy(0, sScrollSync);
        }
    }

    @Override
    public void onGetDataSuccess(@NonNull List<ItemData> list) {
        String searchText = StringUtils.toNonnullString(mSearchController.getSearchText());
        int page = getPage();
        if(page == 1) {
            mListAdapter.setData(searchText, list);
        } else {
            mListAdapter.addData(searchText, list);
        }
        mNeedRequest = false;
    }


    @Override
    public void onRequestFail(@Nullable ApiException e) {
        super.onRequestFail(e);
    }

    @Override
    public void onActionStateModifyFail(int level, ItemData itemData) {
        if(mAdvLevel != level){
            return;
        }
        String id = itemData.getId(level);
        List<ItemData> list = mListAdapter.getData();
        for(int i = 0; i < list.size(); i++){
            ItemData item = list.get(i);
            String itemId = item.getId(level);
            if(itemId != null && itemId.equals(id)){
                mListAdapter.notifyItemChanged(i);
            }
        }
    }


    protected void onNameClick(int level, ItemData data) {
        if(data == null || mAdvLevel >= level){
            return;  // 向下关联
        }

        mSearchController.setNeedNotifyParamsChanged(true);

        clear();
    }

    @CallSuper
    @Override
    public void onTimerModifySuccess(int level, ItemData itemData, List<TaskBean> taskList, @AdvActionState String action, String startTime) {
        if(mAdvLevel != level || ListUtil.isEmpty(mListAdapter.getData())){
            return;
        }
        List<ItemData> list = mListAdapter.getData();
        for(int i = 0; i < list.size(); i++){
            ItemData item = list.get(i);
            String itemId = item.getId(level);
            String modifyItemId = itemData.getId(level);
            if(itemId != null && itemId.equals(modifyItemId)){
                item.setTask_list(taskList);
                mListAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onBudgetTimerSuccess(ItemData itemData, List<TaskBean> taskList, String startTime) {
        if(ListUtil.isEmpty(mListAdapter.getData())){
            return;
        }
        List<ItemData> list = mListAdapter.getData();
        for(int i = 0; i < list.size(); i++){
            ItemData item = list.get(i);
            String itemId = item.getId(mAdvLevel);
            String modifyItemId = itemData.getId(mAdvLevel);
            if(itemId != null && itemId.equals(modifyItemId)){
                item.setTask_list(taskList);
                mListAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onActionStateUpdated(int level, ItemData itemData, String status, String budget) {
        if(mAdvLevel != level){
            return;
        }
        String id = itemData.getId(mAdvLevel);
        List<ItemData> list = mListAdapter.getData();
        for(int i = 0; i < list.size(); i++){
            ItemData item = list.get(i);
            String itemId = item.getId(mAdvLevel);
            if(itemId != null && itemId.equals(id)){
                if(mAdvLevel == AdvItemLevel.ADV_SERIAL || mAdvLevel == AdvItemLevel.ADV_GROUP) {
                    item.setDaily_budget(budget);
                }
                item.setState(mAdvLevel, status);
                mListAdapter.notifyItemChanged(i);
            }
        }
        ToastUtils.showToastCenter("修改成功");
    }

    /**
     * 滑动到下钻item
     */
    public void scrollToSelectParams() {
        if(ListUtil.isEmpty(mListAdapter.getData())){
            return;
        }
        ItemData singleSelectItem = GlobalAdvParams.getSingleSelectItem(mAdvLevel);
        if(singleSelectItem == null){
            return;
        }
        String singleSelectId = singleSelectItem.getId(mAdvLevel);
        if(singleSelectId == null){
            return;
        }
        for(int i = 0; i < mListAdapter.getData().size(); i++){
            ItemData item = mListAdapter.getData().get(i);
            if(singleSelectId.equals(item.getId(mAdvLevel))){
                LinearLayoutManager layoutManager = (LinearLayoutManager) mBinding.recyclerView.getLayoutManager();
                if(layoutManager != null){
                    layoutManager.scrollToPosition(i);
                }
            }
        }
    }
}
