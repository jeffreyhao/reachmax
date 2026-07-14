package com.xcyh.reachmax.adv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.baidu.baselibrary.widget.SwitchButton;
import com.baidu.baselibrary.widget.dialog.AppTipDialog;
import com.xcyh.reachmax.app.meta.dialog.base.BaseDialog;
import com.xcyh.reachmax.app.meta.dialog.base.DialogController;
import com.xcyh.reachmax.adv.budget.AdvBudgetFragment;
import com.xcyh.reachmax.adv.date.AdvDateSelectFragment;
import com.xcyh.reachmax.adv.detail.AdvDetailActivity;
import com.xcyh.reachmax.adv.timer.AdvTimerBudgetFragment;
import com.xcyh.reachmax.adv.timer.AdvTimerTaskCreateFragment;
import com.xcyh.reachmax.app.callback.ConfirmListener;
import com.xcyh.reachmax.app.callback.SelectListener;
import com.xcyh.reachmax.app.callback.SubmitListener;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.constant.AdvConst;
import com.xcyh.reachmax.model.constant.TaskStatus;
import com.xcyh.reachmax.model.custom.DateRange;
import com.xcyh.reachmax.model.event.GlobalAdvParams;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by haojiangfeng on 2024/11/23.
 */
public class AdvGotoHelper {


    /**
     *  选择日期页
     */
    public static void showSelectDateDialog(FragmentActivity activity, String text, DateRange dateRange, SelectListener selectListener) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag("date");
        if (fragment instanceof BaseDialog) {
            return;
        }
        AdvDateSelectFragment dateSelectFragment = AdvDateSelectFragment.newInstance(text, dateRange.dateStart, dateRange.dateEnd, selectListener);
        dateSelectFragment.showFragment(activity, "date");
    }


    /**
     *  广告详情页
     */
    public static void gotoAdvDetail(Activity activity, int level, ItemData data) {
        AdvDetailActivity.start(activity, level, data);
    }


    /**
     *  定时器修改页
     */
    public static void gotoAdvTimer(FragmentActivity activity, int level, ItemData data, SubmitListener listener){
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag("timer");
        if (fragment instanceof BaseDialog) {
            return;
        }
        AdvTimerTaskCreateFragment timerTaskCreateFragment = AdvTimerTaskCreateFragment.newInstance(level, data, new SubmitListener(){
            @Override
            public void onClickSubmit(int action, Bundle bundle) {
                int status = bundle.getInt("status", TaskStatus.ALL);
                String tip = "";
                if(status == TaskStatus.CANCEL){
                    tip = "确定要取消任务吗？";
                } else {
                    tip = "确定要修改或者创建任务吗？";
                }
                showTimerModifyDialog(activity, tip, new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        if(listener != null){
                            listener.onClickSubmit(action, bundle);
                        }
                    }
                });
            }
        });
        timerTaskCreateFragment.show(activity.getSupportFragmentManager(), "timer");
    }


    /**
     * 预算定时器
     */
    public static void gotoBudgetTimer(FragmentActivity activity, int level, ItemData data, SubmitListener listener){
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag("budgetTimer");
        if (fragment instanceof BaseDialog) {
            return;
        }
        AdvTimerBudgetFragment budgetTimerFragment = AdvTimerBudgetFragment.newInstance(level, data, new SubmitListener(){
            @Override
            public void onClickSubmit(int action, Bundle bundle) {
                int status = bundle.getInt("status", TaskStatus.ALL);
                String tip = "";
                if(status == TaskStatus.CANCEL){
                    tip = "确定要取消任务吗？";
                } else {
                    tip = "确定要修改或者创建任务吗？";
                }
                showTimerModifyDialog(activity, tip, new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        if(listener != null){
                            listener.onClickSubmit(action, bundle);
                        }
                    }
                });
            }
        });
        budgetTimerFragment.show(activity.getSupportFragmentManager(), "budgetTimer");
    }

    /**
     *  定时器提示dialog
     */
    public static void showTimerModifyDialog(FragmentActivity activity, String content, ConfirmListener listener) {
        DialogController.getInstance().showAppTipDialog(activity.getSupportFragmentManager(),
                "提示", content, "取消", "确定",
                new AppTipDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirm() {
                        if(listener != null){
                            listener.onClickConfirm();
                        }
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }


    /**
     * 广告开关弹窗
     */
    public static void showAdvSwitchDialog(FragmentActivity activity, int advLevel, SwitchButton switchView, ConfirmListener listener) {
        boolean checked = switchView.isChecked();
        String content = (checked? "确定要打开此" : "确定要关闭此") + AdvConst.getLevelText(advLevel) + "?";
        DialogController.getInstance().showAppTipDialog(activity.getSupportFragmentManager(),
                "提示", content, "取消", "确定",
                new AppTipDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirm() {
                        if(listener != null){
                            listener.onClickConfirm();
                        }
                    }

                    @Override
                    public void onCancel() {
                        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = switchView.getOnCheckedChangeListener();
                        switchView.setOnCheckedChangeListener(null);
                        switchView.setChecked(!checked);
                        switchView.setOnCheckedChangeListener(onCheckedChangeListener);
                    }
                });
    }




    /**
     *  预算修改页
     */
    public static void gotoAdvBudget(FragmentActivity activity, ItemData data, SubmitListener listener) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag("budget");
        if (fragment instanceof BaseDialog) {
            return;
        }
        AdvBudgetFragment budgetFragment = AdvBudgetFragment.newInstance(data, new SubmitListener() {
            @Override
            public void onClickSubmit(int action, Bundle bundle) {
                showBudgetModifyDialog(activity, new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        if(listener != null){
                            listener.onClickSubmit(action, bundle);
                        }
                    }
                });
            }
        });
        budgetFragment.show(activity.getSupportFragmentManager(), "budget");
    }

    /**
     *  预算提示dialog
     */
    private static void showBudgetModifyDialog(FragmentActivity activity, ConfirmListener listener){
        DialogController.getInstance().showAppTipDialog(activity.getSupportFragmentManager(),
                "提示", "确定要调整预算吗？", "取消", "确定",
                new AppTipDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirm() {
                        if(listener != null){
                            listener.onClickConfirm();
                        }
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }


}
