package com.xcyh.reachmax.adv.home.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.baselibrary.global.callback.OnVisibilityChangeListener;
import com.base.util.collection.ListUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.adv.AdvListUtil;
import com.xcyh.reachmax.adv.ViewUtil;
import com.xcyh.reachmax.app.meta.ui.widget.ExpandableFlowLayout;
import com.xcyh.reachmax.model.bean.PrincipalItem;
import com.xcyh.reachmax.model.constant.AdvStateFilter;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/11/22.
 */
public class AdvStateFilterView extends LinearLayout {


    public interface OnStateFilterListener {
        void onFilterSubmit(AdvStateFilter filter, ArrayList<PrincipalItem> principalList);
    }


    private TextView btnCancel, btnSubmit;
    private CheckedTextView tvStateAll, tvState1, tvState2, tvState3, tvState4, tvPrincipalAll;
    private View btnLayout;
    private ViewGroup stateFilterLinearLayout;
    private ExpandableFlowLayout mFlowLayout;

    private OnStateFilterListener mOnStateFilterListener;

    private OnVisibilityChangeListener mOnVisibilityChangeListener;

    private AdvStateFilter mSelectFilter;


    @NonNull
    private ArrayList<PrincipalItem> mSelectPrincipal;



    public AdvStateFilterView(Context context) {
        super(context);
        init();
    }

    public AdvStateFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdvStateFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AdvStateFilterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_adv_state_filter, this);
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        setOrientation(VERTICAL);

        tvStateAll = findViewById(R.id.tv_state_all);
        tvState1 = findViewById(R.id.tv_state_1);
        tvState2 = findViewById(R.id.tv_state_2);
        tvState3 = findViewById(R.id.tv_state_3);
        tvState4 = findViewById(R.id.tv_state_4);
        tvPrincipalAll = findViewById(R.id.tv_principal_all);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSubmit = findViewById(R.id.btn_submit);
        btnLayout = findViewById(R.id.btn_layout);
        stateFilterLinearLayout = findViewById(R.id.state_filter_linear_layout);
        mFlowLayout = findViewById(R.id.principal_flow_layout);

        tvStateAll.setTag(R.id.tag_state_filter, AdvStateFilter.ALL);
        tvState1.setTag(R.id.tag_state_filter, AdvStateFilter.ACCOUNT_NORMAL);
        tvState2.setTag(R.id.tag_state_filter, AdvStateFilter.ACCOUNT_ABNORMAL);

        initPrincipalFlowLayout();
        setListeners();
    }

    private void setListeners() {
        tvState1.setOnClickListener(v -> onStateFilterSelected(tvState1));
        tvState2.setOnClickListener(v -> onStateFilterSelected(tvState2));
        tvState3.setOnClickListener(v -> onStateFilterSelected(tvState3));
        tvState4.setOnClickListener(v -> onStateFilterSelected(tvState4));

        tvStateAll.setOnClickListener(v -> {
            onStateFilterSelected(tvStateAll);
        });
        tvPrincipalAll.setOnClickListener(v -> {
            mSelectPrincipal.clear();
            updatePrincipalState();
        });

        btnCancel.setOnClickListener(v->{
            setVisibility(View.GONE);
        });
        btnSubmit.setOnClickListener(v->{
            setVisibility(View.GONE);
            if(mOnStateFilterListener != null){
                mOnStateFilterListener.onFilterSubmit(mSelectFilter, mSelectPrincipal);
            }
        });
        btnLayout.setOnClickListener(v->{});
        setOnClickListener(v->{});
    }

    private void initPrincipalFlowLayout(){
        mFlowLayout.setHorizontalSpacing(UIUtil.dip2px(getContext(), 12));
        mFlowLayout.setVerticalSpacing(UIUtil.dip2px(getContext(), 15));
        mSelectPrincipal = new ArrayList<>();
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener listener){
        mOnVisibilityChangeListener = listener;
    }

    public void setOnFilterSelectListener(OnStateFilterListener listener){
        this.mOnStateFilterListener = listener;
    }

    /**
     * 重置filter列表布局
     */
    public void resetFilterLayout(List<AdvStateFilter> filters){
        int filterSize = filters == null ? 0 : filters.size();
        for(int i = 0; i < stateFilterLinearLayout.getChildCount(); i++){
            TextView textView = (TextView) stateFilterLinearLayout.getChildAt(i);
            if(i < filterSize){
                AdvStateFilter filter = filters.get(i);
                textView.setVisibility(View.VISIBLE);
                textView.setText(filter.text);
                textView.setTag(R.id.tag_state_filter, filter);
                textView.setEnabled(filter.enable);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 重置选中的选项
     */
    public void resetSelectFilter(AdvStateFilter filter){
        mSelectFilter = filter;
        ViewGroup parent = (ViewGroup) stateFilterLinearLayout;
        for(int i = 0; i < parent.getChildCount(); i++){
            CheckedTextView child = (CheckedTextView) parent.getChildAt(i);
            AdvStateFilter childTag = (AdvStateFilter) child.getTag(R.id.tag_state_filter);
            if(childTag == filter){
                child.setChecked(true);
            } else {
                child.setChecked(false);
            }
        }
    }

    /**
     * 状态过滤器选择
     */
    public void onStateFilterSelected(CheckedTextView v) {
        updateSelectedState(v, true);
        mSelectFilter = (AdvStateFilter) v.getTag(R.id.tag_state_filter);
        tvStateAll.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * 负责人列表
     */
    public void initPrincipalList(List<PrincipalItem> list) {
        if(ListUtil.isEmpty(list)){
            return;
        }
        tvPrincipalAll.setTag(new PrincipalItem());
        tvPrincipalAll.setChecked(true);

        View.OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckedTextView checkedTextView = (CheckedTextView) v;
                PrincipalItem principalItem = (PrincipalItem) v.getTag();
                if(checkedTextView.isChecked()){
                    AdvListUtil.removePrincipalItem(mSelectPrincipal, principalItem);
                } else {
                    AdvListUtil.addPrincipalItem(mSelectPrincipal, principalItem);
                }
                updatePrincipalState();
            }
        };
        for(PrincipalItem item : list){
            CheckedTextView checkedTextView = ViewUtil.newPrincipalView(getContext());
            checkedTextView.setText(item.getShowName());
            checkedTextView.setTag(item);
            checkedTextView.setOnClickListener(onClickListener);
            mFlowLayout.addView(checkedTextView);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        int currentVisibility = getVisibility();
        if(currentVisibility != visibility) {
            super.setVisibility(visibility);
            if(mOnVisibilityChangeListener != null) {
                mOnVisibilityChangeListener.onVisibilityChange(currentVisibility, visibility);
            }
        }
    }

    /**
     * 更新负责人状态
     */
    private void updatePrincipalState(){
        tvPrincipalAll.setTypeface(Typeface.DEFAULT_BOLD);
        if(mSelectPrincipal.size() == 0){
            tvPrincipalAll.setChecked(true);
        } else {
            tvPrincipalAll.setChecked(false);
        }
        for(int i = 0; i < mFlowLayout.getChildCount(); i++){
            if(i == 0){
                continue;
            }
            CheckedTextView child = (CheckedTextView) mFlowLayout.getChildAt(i);
            PrincipalItem childTag = (PrincipalItem) child.getTag();
            if(AdvListUtil.containsPrincipalItem(mSelectPrincipal, childTag)){
                child.setChecked(true);
            } else {
                child.setChecked(false);
            }
        }
    }

    /**
     * 更新单个按钮状态
     */
    private void updateSelectedState(CheckedTextView v, boolean isSingleSelect){
        if(isSingleSelect) {    // 单选
            ViewGroup parent = (ViewGroup) v.getParent();
            for(int i = 0; i < parent.getChildCount(); i++){
                CheckedTextView child = (CheckedTextView) parent.getChildAt(i);
                if(child == v){
                    child.setChecked(true);
                } else {
                    child.setChecked(false);
                }
            }
        } else {    // 多选
            boolean targetChecked = !v.isChecked();
            v.setChecked(targetChecked);
        }
    }
}
