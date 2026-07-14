package com.xcyh.reachmax.adv.budget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.baidu.baselibrary.base.fragment.BaseDialogFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.FragmentAdvBudgetBinding;
import com.xcyh.reachmax.app.callback.SubmitListener;
import com.xcyh.reachmax.model.bean.ItemData;

/**
 * 预算
 */
public class AdvBudgetFragment extends BaseDialogFragment<FragmentAdvBudgetBinding, EmptyPresenter>  {



    public static AdvBudgetFragment newInstance(ItemData itemData, SubmitListener listener) {
        AdvBudgetFragment chapterSheetDialogFragment = new AdvBudgetFragment();
        chapterSheetDialogFragment.mSubmitListener = listener;
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemData", itemData);
        chapterSheetDialogFragment.setArguments(bundle);
        return chapterSheetDialogFragment;
    }


    private SubmitListener mSubmitListener;
    private ItemData mItemData;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adv_budget;
    }


    @Override
    protected void initTitleBar() {
    }

    @Override
    public void initListener() {
        mBinding.btnCancel.setOnClickListener(v->{
            closeDialog();
        });
        mBinding.btnSubmit.setOnClickListener(v->{
            doSubmit();
        });
    }


    @Override
    protected void initData() {
        mItemData = (ItemData) getArguments().getSerializable("itemData");
        mBinding.etBudgetAmount.setText(String.valueOf(mItemData.getDaily_budget()));
        mBinding.etBudgetAmount.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.etBudgetAmount.requestFocus();
                String text = mBinding.etBudgetAmount.getText().toString();
                mBinding.etBudgetAmount.setSelection(text.length());

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mBinding.etBudgetAmount, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);
    }


    private void closeDialog() {
        if (null == getActivity()) {
            return;
        }
        getActivity().runOnUiThread(this::dismiss);
    }

    private void doSubmit(){
        String text = mBinding.etBudgetAmount.getText().toString();
        if(TextUtils.isEmpty(text)){
            ToastUtils.showToastCenter("请输入预算数字");
            return;
        }
        if(text.equals("0")){
            ToastUtils.showToastCenter("预算不能为0");
            return;
        }
        closeDialog();
        if(mSubmitListener != null){
            Bundle bundle = new Bundle();
            try {
                int number = Integer.parseInt(text);
                bundle.putInt("budget", number);
                bundle.putSerializable("itemData", mItemData);
                mSubmitListener.onClickSubmit(0, bundle);
            } catch (Throwable e){
                ToastUtils.showToastCenter("请填写正常预算");
            }
        }
    }


    @Override
    protected String className() {
        return getClass().getSimpleName();
    }
}