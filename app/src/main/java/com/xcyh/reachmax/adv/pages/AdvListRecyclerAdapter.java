package com.xcyh.reachmax.adv.pages;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.baidu.baselibrary.recycler.BindingRecyclerAdapter;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.baidu.baselibrary.widget.CheckBox;
import com.base.util.collection.ListUtil;
import com.base.util.hybrid.ClipBoardUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.adv.AdvClickUtil;
import com.xcyh.reachmax.adv.ViewUtil;
import com.xcyh.reachmax.app.meta.ui.widget.RippleResource;
import com.xcyh.reachmax.databinding.ItemAdvBinding;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.ClickAction;
import com.xcyh.reachmax.model.constant.TaskStatus;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AdvListRecyclerAdapter extends BindingRecyclerAdapter<ItemAdvBinding, ItemData> {


    public interface OnItemCheckChangeListener {
        void onItemCheckChange(CompoundButton checkBox, ItemData item, int position);
    }



    private final @AdvItemLevel int mAdvLevel;

    /**
     * 是否单选模式
     */
    private boolean mSingleSelect = true;

    private String mSearchText;

    private OnItemCheckChangeListener mOnItemCheckChangeListener;



    public AdvListRecyclerAdapter(@AdvItemLevel int level) {
        super(R.layout.item_adv);
        this.mAdvLevel = level;
    }

    public void setOnItemCheckChangeListener(OnItemCheckChangeListener listener){
        mOnItemCheckChangeListener = listener;
    }


    public void setData(String searchText, List<ItemData> dataList) {
        mSearchText = searchText;
        super.setData(dataList);
    }

    public void addData(String searchText, List<ItemData> dataList) {
        mSearchText = searchText;
        super.addData(dataList);
    }

    public ArrayList<ItemData> getCheckedList(){
        ArrayList<ItemData> list = new ArrayList<>();
        if(getData().isEmpty()){
            return list;
        }
        for(ItemData item: getData()){
            if(item.isChecked()){
                list.add(item);
            }
        }
        return list;
    }

    public void setSingleSelect(boolean isSingleSelect){
        this.mSingleSelect = isSingleSelect;
        if(!getData().isEmpty()) {
            if(mSingleSelect){  // 单选
                for(ItemData item: getData()){
                    item.setChecked(false);
                }
            } else {  // 多选
                for(ItemData item: getData()){
                    item.setChecked(GlobalAdvParams.haveSelected(mAdvLevel, item));
                }
            }
        }
        notifyItemRangeChanged(0, getData().size());
    }

    @Override
    protected void handleView(BindingViewHolder<ItemAdvBinding, ItemData> holder, ItemData data, int position) {
        ItemAdvBinding binding = holder.getViewDataBinding();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.getRoot().getLayoutParams();
        if(position == 0){
            params.topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.height_search_bar)
                    + mContext.getResources().getDimensionPixelSize(R.dimen.dp_6);
            binding.itemAdvDivider.setVisibility(View.GONE);
        } else {
            params.topMargin = 0;
            binding.itemAdvDivider.setVisibility(View.VISIBLE);
        }
        binding.getRoot().setTag(R.id.tag_item_data, data);

        showName(binding, data);

        setCheckBoxState(binding.tvCheckBox, data);

        showAdvData(binding, data);

        showTimerImage(binding, data);

        bindListeners(binding, holder, data, position);
    }

    /**
     * 显示name和按钮
     */
    private void showName(ItemAdvBinding binding, ItemData item) {
        switch (mAdvLevel){
            case AdvItemLevel.ADV_ACCOUNT:
                binding.tvAdvName.setTextColor(mContext.getResources().getColor(R.color.colorAccent, mContext.getTheme()));
                binding.tvAdvName.setText(ViewUtil.getSpannableText(item.getAd_account_name(), mSearchText));
                binding.tvAdvNo.setText(item.getAd_account());
                setItemState(binding, item, item.getCampaign_status());

                binding.btnMore.setVisibility(View.VISIBLE);    // 所有的item都有详情按钮

                binding.advSwitch.setVisibility(View.GONE);     // 账号下的开关不展示
                binding.btnSwitchTimer.setVisibility(View.GONE);

                binding.btnBudgetTimer.setVisibility(View.GONE);
                binding.btnBudget.setVisibility(View.GONE);
                break;

            case AdvItemLevel.ADV_SERIAL:
                binding.tvAdvName.setTextColor(mContext.getResources().getColor(R.color.colorAccent, mContext.getTheme()));
                binding.tvAdvName.setText(ViewUtil.getSpannableText(item.getCampaign_name(), mSearchText));
                binding.tvAdvNo.setText(item.getCampaign_id());
                setItemState(binding, item, item.getCampaign_status());

                binding.btnMore.setVisibility(View.VISIBLE);

                binding.advSwitch.setVisibility(View.VISIBLE);
                binding.btnSwitchTimer.setVisibility(View.VISIBLE);     // 下面三个纬度都有定时任务

                binding.btnBudgetTimer.setVisibility(View.VISIBLE);     // 系列有定时预算任务
                binding.btnBudget.setVisibility(View.VISIBLE);          // 系列有预算
                break;

            case AdvItemLevel.ADV_GROUP:
                binding.tvAdvName.setTextColor(mContext.getResources().getColor(R.color.colorAccent, mContext.getTheme()));
                binding.tvAdvName.setText(ViewUtil.getSpannableText(item.getAdset_name(), mSearchText));
                binding.tvAdvNo.setText(item.getAdset_id());
                setItemState(binding, item, item.getAdset_status());

                binding.btnMore.setVisibility(View.VISIBLE);

                binding.advSwitch.setVisibility(View.VISIBLE);
                binding.btnSwitchTimer.setVisibility(View.VISIBLE);

                binding.btnBudgetTimer.setVisibility(View.VISIBLE);
                binding.btnBudget.setVisibility(View.VISIBLE);          // 广告组也有预算
                break;

            case AdvItemLevel.ADV_PLAN:
                binding.tvAdvName.setTextColor(mContext.getResources().getColor(R.color.black, mContext.getTheme()));
                binding.tvAdvName.setText(ViewUtil.getSpannableText(item.getAd_name(), mSearchText));
                binding.tvAdvNo.setText(item.getAd_id());
                setItemState(binding, item, item.getAd_status());

                binding.btnMore.setVisibility(View.VISIBLE);

                binding.advSwitch.setVisibility(View.VISIBLE);
                binding.btnSwitchTimer.setVisibility(View.VISIBLE);

                binding.btnBudgetTimer.setVisibility(View.GONE);
                binding.btnBudget.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 设置多选框状态
     */
    private void setCheckBoxState(CheckBox tvCheckBox, ItemData item) {
        if(mSingleSelect){  // 单选
            if(GlobalAdvParams.haveSelected(mAdvLevel, item)){ // 已选中
                tvCheckBox.setVisibility(View.VISIBLE);
                tvCheckBox.setCheckedWithoutCallback(true);
            } else {
                tvCheckBox.setVisibility(View.GONE);
                tvCheckBox.setCheckedWithoutCallback(false);
            }
        } else {
            tvCheckBox.setVisibility(View.VISIBLE);
            tvCheckBox.setCheckedWithoutCallback(item.isChecked());
        }
    }

    private void setItemState(ItemAdvBinding binding, ItemData item, String actionState){
        String state = actionState == null ? "" : actionState;
        switch (state){
            case AdvActionState.ACTIVE:
                binding.tvAdvState.setText("投放中");
                binding.tvAdvState.setBackgroundResource(R.drawable.bg_task_state_finish);

                binding.advSwitch.setCheckedWithoutCallback(true);
                binding.advSwitch.setVisibility(View.VISIBLE);
                binding.advSwitch.setEnabled(true);

                ViewUtil.setEnable(true, binding.btnSwitchTimer);
                setBudget(binding, item, true);
                break;

            case AdvActionState.PAUSED:
                binding.tvAdvState.setText("暂停");
                binding.tvAdvState.setBackgroundResource(R.drawable.bg_adv_state_pause);

                binding.advSwitch.setCheckedWithoutCallback(false);
                binding.advSwitch.setVisibility(View.VISIBLE);
                binding.advSwitch.setEnabled(true);

                ViewUtil.setEnable(true, binding.btnSwitchTimer);
                setBudget(binding, item, false);
                break;

            case AdvActionState.CLOSE:
                binding.tvAdvState.setText("删除");
                binding.tvAdvState.setBackgroundResource(R.drawable.bg_adv_state_delete);

                binding.advSwitch.setCheckedWithoutCallback(false);
                binding.advSwitch.setVisibility(View.GONE);
                binding.advSwitch.setEnabled(false);

                ViewUtil.setEnable(false, binding.btnSwitchTimer);
                setBudget(binding, item, false);
                break;

            default:
                if(mAdvLevel == AdvItemLevel.ADV_ACCOUNT){
                    binding.tvAdvState.setText("正常");
                    binding.tvAdvState.setBackgroundResource(R.drawable.bg_task_state_finish);
                } else {
                    binding.tvAdvState.setText(""); // 无状态
                    binding.tvAdvState.setBackgroundResource(R.drawable.drawable_transparent);
                }
                binding.advSwitch.setCheckedWithoutCallback(false);
                binding.advSwitch.setVisibility(View.GONE);
                binding.advSwitch.setEnabled(false);

                ViewUtil.setEnable(false, binding.btnSwitchTimer);
                setBudget(binding, item, false);
                break;
        }
    }

    private void setBudget(ItemAdvBinding binding, ItemData item, boolean defaultBudgetEnable){
        if(item.getDaily_budget() == null){
            binding.tvBudget.setVisibility(View.GONE);
            ViewUtil.setEnable(false, binding.btnBudgetTimer, binding.btnBudget, binding.tvBudgetText, binding.tvBudgetTimerText);
        } else {
            if(item.getDaily_budget().isEmpty()) {
                binding.tvBudget.setVisibility(View.GONE);
            } else {
                binding.tvBudget.setVisibility(View.VISIBLE);
                binding.tvBudget.setText(String.format(Locale.CHINA, "$%s", item.getDaily_budget()));          // 预算
            }
            ViewUtil.setEnable(defaultBudgetEnable, binding.btnBudget, binding.tvBudgetText);
            ViewUtil.setEnable(true, binding.btnBudgetTimer, binding.tvBudgetTimerText);
        }
    }

    /**
     * 广告数据
     */
    private void showAdvData(ItemAdvBinding binding, ItemData item) {
        binding.ivCopy.setBackgroundResource(RippleResource.rippleCircleResource);
        binding.tvSpend.setText(String.format("$ %s", item.getSpend()));                                        // 消耗
        binding.tvInstallVolume.setText(String.format(Locale.CHINA, "%d / ", item.getInstall_count()));   // 安装数量
        binding.tvInstallCost.setText(String.format("$ %s", item.getInstallCost()));                            // 安装成本 = 消耗 / 安装数量
        binding.tvRechargeAmount.setText(String.format("$ %s", item.getRecharge_amount()));                     // 充值金额

        binding.tvRoi.setText(String.format("%s", item.getRecharge_roi()));                                     // ROI
        binding.tvCpm.setText(String.format(Locale.CHINA, "$ %.3f", item.getCtm()));                      // CPM = 千次曝光成本 = spend / impressions * 1000
        binding.tvCpc.setText(String.format(Locale.CHINA, "$ %.3f", item.getClick_per_price()));          // CPC = 单次点击成本 = spend / clicks

        binding.tvBuyCount.setText(String.format(Locale.CHINA, "%d / ", item.getRecharge_count()));       // 购物数量
        binding.tvBuyCost.setText(item.getRechargeCost());                                                      // 购物成本 = 消耗 / 购物数量
        binding.tvClickVolume.setText(String.format(Locale.CHINA,"%d / ", item.getClicks()));             // 点击量
        binding.tvClickRate.setText(String.format("%s", item.getClickRate()));                                  // 点击率
        binding.tvIndependentPersons.setText(String.format(Locale.CHINA, "%d / ", item.getRecharge_account_count()));     // 独立用户人数
        binding.tvIndependentCost.setText(item.getUserCost());                                                                  // 独立用户成本 = 消耗 / 独立用户人数
    }

    private void showTimerImage(ItemAdvBinding binding, ItemData item) {
        binding.ivTimerBudget.setVisibility(View.GONE);
        binding.ivTimerSwitch.setVisibility(View.GONE);

        if(ListUtil.isNotEmpty(item.getTask_list())){
            for(TaskBean taskBean : item.getTask_list()){
                if(AdvActionState.CHANGE_DAILY_BUDGET.equals(taskBean.getAction())){
                    if(taskBean.getStatus() == TaskStatus.NOT_START){
                        binding.ivTimerBudget.setVisibility(View.VISIBLE);
                    }
                } else {
                    if(taskBean.getStatus() == TaskStatus.NOT_START){
                        binding.ivTimerSwitch.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


    private void bindListeners(ItemAdvBinding binding, BindingViewHolder<ItemAdvBinding, ItemData> holder, ItemData data, int position) {
        binding.ivCopy.setOnClickListener(v->{
            String text = binding.tvAdvNo.getText().toString();
            ClipBoardUtil.copy(text);
            ToastUtils.showToastCenter("已复制到剪贴板");
        });
        binding.btnMore.setOnClickListener(v->{
            performChildClick(holder, v, ClickAction.ITEM_DETAIL, data, position);
        });
        binding.btnBudgetTimer.setOnClickListener(v->{
            performChildClick(holder, v, ClickAction.ITEM_BUDGET_TIMER, data, position);
        });
        binding.btnSwitchTimer.setOnClickListener(v->{
            performChildClick(holder, v, ClickAction.ITEM_SWITCH_TIMER, data, position);
        });
        binding.btnBudget.setOnClickListener(v->{
            performChildClick(holder, v, ClickAction.ITEM_BUDGET, data, position);
        });

        // title 点击、长按
        if(mAdvLevel == AdvItemLevel.ADV_PLAN){
            binding.tvAdvName.setOnClickListener(null);
            binding.tvAdvName.setSuperLongClickListener(null);
        } else {
            if(mSingleSelect){
                binding.tvAdvName.setOnClickListener(v-> {
                    performChildClick(holder, v, ClickAction.ITEM_ADV_NAME, data, position);
                });
                binding.tvAdvName.setSuperLongClickListener(v -> {
                    setSingleSelect(false);
                    data.setChecked(true);
                    performChildClick(holder, v, ClickAction.ITEM_ADV_MULTI, data, position);
                    return true;
                });
            } else {
                binding.tvAdvName.setOnClickListener(v-> {
                    binding.tvCheckBox.setChecked(!binding.tvCheckBox.isChecked());
                });
                binding.tvAdvName.setSuperLongClickListener(null);
            }
        }

        // 开关
        binding.advSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                performChildClick(holder, buttonView, ClickAction.ITEM_SWITCH, data, position);
            }
        });

        // 多选框
        binding.tvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mSingleSelect){
                    GlobalAdvParams.removeDataItem(mAdvLevel, data);
                } else {
                    data.setChecked(isChecked);
                    if(mOnItemCheckChangeListener != null){
                        mOnItemCheckChangeListener.onItemCheckChange(buttonView, data, position);
                    }
                }
            }
        });

        // 消耗
        View.OnClickListener spendClickListener = AdvClickUtil.buildContinuousClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performChildClick(holder, v, ClickAction.ITEM_BUY_SPEND, data, position);
            }
        });
        binding.tvTextSpend.setOnClickListener(spendClickListener);
        binding.tvSpend.setOnClickListener(spendClickListener);

        // 购买人数
        View.OnClickListener independentClickListener = AdvClickUtil.buildContinuousClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performChildClick(holder, v, ClickAction.ITEM_BUY_USERS, data, position);
            }
        });
        binding.independentLayout.setOnClickListener(independentClickListener);
        binding.textIndependent.setOnClickListener(independentClickListener);
    }

    @Override
    protected void performChildClick(BindingViewHolder<ItemAdvBinding, ItemData> holder, View view, @ClickAction int action, ItemData data, int position) {
        super.performChildClick(holder, view, action, data, position);
    }

}
