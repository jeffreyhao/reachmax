package com.xcyh.reachmax.adv.pages;

import android.view.View;

import com.baidu.baselibrary.base.BasePresenter;
import com.baidu.baselibrary.base.IBaseListView;
import com.baidu.baselibrary.base.fragment.BindingCustomFragment;
import com.baidu.baselibrary.log.ALog;
import com.baidu.baselibrary.util.sys.MvvmUtil;
import com.baidu.baselibrary.util.ui.RefreshUtils;
import com.baidu.baselibrary.widget.AutoLoadRecyclerView;
import com.base.net.bean.ApiException;
import com.base.util.collection.ListUtil;
import com.base.util.net.NetworkUtil;
import com.base.util.ui.UIUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.adv.AdvClickUtil;
import com.xcyh.reachmax.app.meta.dialog.base.ClipBoardDialog;
import com.xcyh.reachmax.databinding.BaseListLayoutBinding;
import com.xcyh.reachmax.model.bean.AmountData;
import com.xcyh.reachmax.model.bean.NoneIdAmount;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copy from {@link com.baidu.baselibrary.base.fragment.BaseListCustomFragment}
 * Created by haojiangfeng on 2025/1/8.
 */
public abstract class BaseAdvListFragment<T, P extends BasePresenter<?>> extends BindingCustomFragment<BaseListLayoutBinding, P>
        implements IBaseListView<List<T>>, IAdvTotalAmountView {

    private int mPage = 1;
    private int mLimit = 10;
    private List<T> mList = new ArrayList<>();
    private boolean hasMoreData = false;


    @Override
    public int getLayoutId(){
        return R.layout.base_list_layout;
    }

    @Override
    public void initView() {
        super.initView();
        mBinding.recyclerView.setLayoutManager(createLayoutManager());
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if(itemDecoration != null){
            mBinding.recyclerView.addItemDecoration(itemDecoration);
        }
        mBinding.recyclerView.setAdapter(createAdapter());
    }

    @Override
    protected void initData() {
        mBinding.recyclerView.setOnceRequestLimit(mLimit);
        getData();
    }

    @Override
    protected void initListener() {
        mBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(NetworkUtil.isNetAvailable(mContext)){
                    mPage = 1;
                    mBinding.recyclerView.setCurPage(mPage);
                    getData();
                }else{
                    finishRefreshOrLoadMore();
                    toastTop(getString(R.string.net_connect_tip), UIUtil.dip2px(mContext,70f));
                }
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if(NetworkUtil.isNetAvailable(mContext)){
                    if (hasMoreData) {
                        if(mBinding.recyclerView.isRequestPageInit(mPage)) {
                            mBinding.recyclerView.requestPage(mPage);
                            doLoadMore();
                        }
                    } else {
                        mBinding.refreshLayout.finishLoadMore();
                    }
                } else {
                    finishRefreshOrLoadMore();
                    toastBottom(getString(R.string.net_connect_tip), UIUtil.dip2px(mContext,65f));
                }
            }
        });
        mBinding.emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtil.isNetAvailable(mContext)){
                    MvvmUtil.setVisible(v, false);
                    mPage = 1;
                    mBinding.recyclerView.setCurPage(mPage);
                    getData();
                }else{
                    toast(getString(R.string.net_connect_tip));
                }
            }
        });
        mBinding.recyclerView.setOnLoadMoreListener(new AutoLoadRecyclerView.LoadMoreListener(){
            @Override
            public void loadMore() {
                ALog.d("isLoadingMore=====>");
                if(hasMoreData) {
                    mBinding.recyclerView.requestPage(mPage);
                    doLoadMore();
                }
            }
        });

        View.OnClickListener continuousClickListener = AdvClickUtil.buildContinuousClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performContinuousClick(v);
            }
        });
        mBinding.layoutAmountSpend.setOnClickListener(continuousClickListener);
        mBinding.layoutAmountRecharge.setOnClickListener(continuousClickListener);
        mBinding.layoutNoneIdRecharge.setOnClickListener(continuousClickListener);
    }

    public void doLoadMore() {
        getData();
    }

    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    abstract RecyclerView.Adapter createAdapter() ;

    abstract void getData();

    protected abstract void onGetDataSuccess(List<T> t);

    @Override
    public void onRequestSuccess(List<T> t) {
        List<T> list = t;
        hasMoreData = false;
        boolean showEmptyView = mPage ==1 && ListUtil.isEmpty(list);
        mBinding.refreshLayout.setVisibility(showEmptyView ? View.GONE : View.VISIBLE);
        mBinding.emptyView.setVisibility(showEmptyView ? View.VISIBLE : View.GONE);
        if(showEmptyView) {
            mBinding.emptyView.setContent(R.string.common_no_data, R.drawable.default_no_data);
        }
        if(mPage == 1){
            mBinding.recyclerView.setCurPage(1);
        }
        mBinding.recyclerView.finishRequest(mPage);
        if(!ListUtil.isEmpty(list)) {
            if(mPage == 1) {
                mList.clear();
            }
            mList.addAll(list);
            onGetDataSuccess(t);
            if(list.size() >= mLimit) {
                hasMoreData = true;
                mPage++;
            }
        }
        RefreshUtils.setRefreshFooter(hasMoreData,mBinding.refreshLayout);
    }

    @Override
    public void onRequestFail(ApiException e) {
        super.onRequestFail(e);
        if(mPage == 1){
            if(ListUtil.isEmpty(mList)){
                if(!NetworkUtil.isNetAvailable(mContext)) {
                    mBinding.emptyView.setVisibility(View.VISIBLE);
                    mBinding.emptyView.setContent(R.string.error_net_tip, R.drawable.default_no_net);
                }else{
                    mBinding.emptyView.setContent(R.string.error_data_tip, R.drawable.default_load_fail);
                }
            } else {
                if(!NetworkUtil.isNetAvailable(mContext)) {
                    toast(getResources().getString(R.string.error_net_tip));
                }else{
                    toast(getResources().getString(R.string.error_data_tip));
                }
            }
        } else {
            mBinding.recyclerView.requestFail(mPage);
        }
    }

    @Override
    public void showLoading(boolean show) {
        if(mBinding.refreshLayout.getState() == RefreshState.PullDownToRefresh
                ||mBinding.refreshLayout.getState() == RefreshState.Refreshing
                ||mBinding.refreshLayout.getState() == RefreshState.PullUpToLoad
                ||mBinding.refreshLayout.getState() == RefreshState.Loading) {
            return;
        }
        super.showLoading(show);
    }

    @Override
    public void onRequestCacheSuccess(List<T> t) {
        hasMoreData = false;
        mPage = 1;
        mBinding.recyclerView.setCurPage(1);
        List<T> list = t;
        boolean showEmptyView = ListUtil.isEmpty(list);
        UIUtil.setVisible(mBinding.refreshLayout, !showEmptyView);
        UIUtil.setVisible(mBinding.emptyView, showEmptyView);
        if(showEmptyView) {
            mBinding.emptyView.setContent(R.string.common_no_data, R.drawable.default_no_data);
        } else{
            mList.clear();
            mList.addAll(list);
            onGetDataSuccess(t);
        }
        RefreshUtils.setRefreshFooter(false, mBinding.refreshLayout);
    }

    @Override
    public void onRequestCacheFail(ApiException e) {
        // none
    }

    public void finishRefreshOrLoadMore(){
        mBinding.refreshLayout.finishRefresh();
        mBinding.refreshLayout.finishLoadMore();
    }

    @Override
    public void setAmountView(boolean isListEmpty, AmountData amount, NoneIdAmount noneIdAmount) {
        mBinding.bottomTotalLayout.setVisibility(View.VISIBLE);
        if(!isListEmpty) {
            if(amount == null) {
                mBinding.tvAmountSpend.setText("0");        // 消耗
                mBinding.tvAmountRecharge.setText("$0");    // 充值金额
                mBinding.tvAmountRoi.setText("$0");         // ROI
                mBinding.tvAmountCpi.setText("$0");         // CPI
            } else {
                mBinding.tvAmountSpend.setText(String.format("$%s", amount.getSpend()));        // 消耗
                mBinding.tvAmountRecharge.setText(String.format("$%s", amount.getRecharge_amount()));   // 充值金额
                mBinding.tvAmountRoi.setText(String.format("%s", amount.getRecharge_roi()));    // ROI
                mBinding.tvAmountCpi.setText(String.format("%s", amount.getInstall_per_price()));         // CPI

                mBinding.layoutAmountSpend.setTag(R.id.tag_recharge_amount, amount);
                mBinding.layoutAmountRecharge.setTag(R.id.tag_recharge_amount, amount.buildRechargeIds());
            }

            // 空Id充值
            if(noneIdAmount == null) {
                mBinding.tvNoneIdRecharge.setText("$0");
            } else {
                mBinding.tvNoneIdRecharge.setText(String.format("$%s", noneIdAmount.getRecharge_amount()));
                mBinding.layoutNoneIdRecharge.setTag(R.id.tag_recharge_amount, noneIdAmount);
            }
        }
    }

    private void performContinuousClick(View v) {
        Object object = v.getTag(R.id.tag_recharge_amount);
        if(object != null) {
            String text = object.toString();
            ClipBoardDialog.show(getActivity(), text);
        }
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page){
        mPage = page;
    }

    public List<T> getList() {
        return mList;
    }

}
