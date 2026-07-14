package com.xcyh.reachmax.adv.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.baselibrary.global.callback.OnVisibilityChangeListener;
import com.base.util.collection.ListUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.app.meta.ui.widget.RippleResource;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by haojiangfeng on 2025/1/7.
 */
public class AdvSelectItemLayout extends LinearLayout {

    private TextView tvSelectAccount, tvSelectSerial, tvSelectGroup;
    private ImageView ivLeftInterval, ivRightInterval, ivAccountClose, ivSerialClose, ivGroupClose;

    private TextView tvClear;

    private LifecycleOwner mLifecycleOwner;

    private @AdvItemLevel int mLevel;

    private OnVisibilityChangeListener mOnVisibilityChangeListener;

    public AdvSelectItemLayout(Context context) {
        super(context);
        init();
    }

    public AdvSelectItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdvSelectItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AdvSelectItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.layout_select_items, this);
        tvSelectAccount = findViewById(R.id.tv_select_accounts);
        tvSelectSerial = findViewById(R.id.tv_select_serial);
        tvSelectGroup = findViewById(R.id.tv_select_group);
        ivLeftInterval = findViewById(R.id.iv_left_interval);
        ivRightInterval = findViewById(R.id.iv_right_interval);
        ivAccountClose = findViewById(R.id.iv_account_close);
        ivSerialClose = findViewById(R.id.iv_serial_close);
        ivGroupClose = findViewById(R.id.iv_group_close);
        tvClear = findViewById(R.id.tv_select_clear);
        tvClear.setBackgroundResource(RippleResource.rippleBlockResource);

        mLevel = AdvItemLevel.ADV_ACCOUNT;
        initListeners();
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener listener){
        mOnVisibilityChangeListener = listener;
    }

    public void setLifecycleOwner(@NonNull LifecycleOwner owner){
        mLifecycleOwner = owner;
        GlobalAdvParams.sSelectAccounts.observe(mLifecycleOwner, selectParams -> {
            updateSelectedInfo();
            checkIntervalVisibility();
        });
        GlobalAdvParams.sSelectSerials.observe(mLifecycleOwner, selectParams -> {
            updateSelectedInfo();
            checkIntervalVisibility();
        });
        GlobalAdvParams.sSelectGroups.observe(mLifecycleOwner, selectParams -> {
            updateSelectedInfo();
            checkIntervalVisibility();
        });
    }

    public void updateLevel(int level){
        mLevel = level;
        updateSelectedInfo();
        checkIntervalVisibility();
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

    private void updateSelectedInfo(){
        ArrayList<ItemData> accountList = GlobalAdvParams.sSelectAccounts.getValue();
        ArrayList<ItemData> serialList = GlobalAdvParams.sSelectSerials.getValue();
        ArrayList<ItemData> groupList = GlobalAdvParams.sSelectGroups.getValue();

        if(ListUtil.isEmpty(accountList)){
            tvSelectAccount.setVisibility(View.GONE);
            ivAccountClose.setVisibility(View.GONE);
        } else {
            tvSelectAccount.setVisibility(View.VISIBLE);
            ivAccountClose.setVisibility(View.VISIBLE);
            tvSelectAccount.setText(String.format(Locale.CHINA, "%d个广告账户", accountList.size()));
        }
        if(ListUtil.isEmpty(serialList)){
            tvSelectSerial.setVisibility(View.GONE);
            ivSerialClose.setVisibility(View.GONE);
        } else {
            tvSelectSerial.setVisibility(View.VISIBLE);
            ivSerialClose.setVisibility(View.VISIBLE);
            tvSelectSerial.setText(String.format(Locale.CHINA, "%d个广告系列", serialList.size()));
        }
        if(ListUtil.isEmpty(groupList)){
            tvSelectGroup.setVisibility(View.GONE);
            ivGroupClose.setVisibility(View.GONE);
        } else {
            tvSelectGroup.setVisibility(View.VISIBLE);
            ivGroupClose.setVisibility(View.VISIBLE);
            tvSelectGroup.setText(String.format(Locale.CHINA, "%d个广告组", groupList.size()));
        }

        switch (mLevel){
            case AdvItemLevel.ADV_ACCOUNT:
                tvSelectAccount.setVisibility(View.GONE);
                ivAccountClose.setVisibility(View.GONE);
                tvSelectSerial.setVisibility(View.GONE);
                ivSerialClose.setVisibility(View.GONE);
                tvSelectGroup.setVisibility(View.GONE);
                ivGroupClose.setVisibility(View.GONE);
                break;
            case AdvItemLevel.ADV_SERIAL:
                tvSelectSerial.setVisibility(View.GONE);
                ivSerialClose.setVisibility(View.GONE);
                tvSelectGroup.setVisibility(View.GONE);
                ivGroupClose.setVisibility(View.GONE);
                break;
            case AdvItemLevel.ADV_GROUP:
                tvSelectGroup.setVisibility(View.GONE);
                ivGroupClose.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 显示间隔箭头
     */
    private void checkIntervalVisibility() {
        boolean showAccount = tvSelectAccount.getVisibility() == View.VISIBLE;
        boolean showSerial = tvSelectSerial.getVisibility() == View.VISIBLE;
        boolean showGroup = tvSelectGroup.getVisibility() == View.VISIBLE;
        int showSelectionCount = Boolean.compare(showAccount, false) + Boolean.compare(showSerial, false) + Boolean.compare(showGroup, false);
        switch (showSelectionCount){
            case 0:
                this.setVisibility(View.GONE);
                ivLeftInterval.setVisibility(View.GONE);
                ivRightInterval.setVisibility(View.GONE);
                break;
            case 1:
                this.setVisibility(View.VISIBLE);
                ivLeftInterval.setVisibility(View.GONE);
                ivRightInterval.setVisibility(View.GONE);
                break;
            case 2:
                this.setVisibility(View.VISIBLE);
                if(showAccount){
                    ivLeftInterval.setVisibility(View.VISIBLE);
                    ivRightInterval.setVisibility(View.GONE);
                } else {
                    ivLeftInterval.setVisibility(View.GONE);
                    ivRightInterval.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                this.setVisibility(View.VISIBLE);
                ivLeftInterval.setVisibility(View.VISIBLE);
                ivRightInterval.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initListeners() {
        tvSelectAccount.setOnClickListener(v -> GlobalAdvParams.removeSelectedLevel(AdvItemLevel.ADV_ACCOUNT));
        tvSelectSerial.setOnClickListener(v -> GlobalAdvParams.removeSelectedLevel(AdvItemLevel.ADV_SERIAL));
        tvSelectGroup.setOnClickListener(v -> GlobalAdvParams.removeSelectedLevel(AdvItemLevel.ADV_GROUP));
        ivAccountClose.setOnClickListener(v -> GlobalAdvParams.removeSelectedLevel(AdvItemLevel.ADV_ACCOUNT));
        ivSerialClose.setOnClickListener(v -> GlobalAdvParams.removeSelectedLevel(AdvItemLevel.ADV_SERIAL));
        ivGroupClose.setOnClickListener(v -> GlobalAdvParams.removeSelectedLevel(AdvItemLevel.ADV_GROUP));
        tvClear.setOnClickListener(v -> GlobalAdvParams.clearSelectParams());
    }

}
