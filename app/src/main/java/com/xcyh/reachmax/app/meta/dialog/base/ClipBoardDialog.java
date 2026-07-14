package com.xcyh.reachmax.app.meta.dialog.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.baidu.baselibrary.base.fragment.BaseDialogFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.base.util.hybrid.ClipBoardUtil;
import com.base.util.ui.UIUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.DialogClipBoardBinding;

import androidx.fragment.app.FragmentActivity;



public class ClipBoardDialog extends BaseDialogFragment<DialogClipBoardBinding, EmptyPresenter> {



    public static void show(FragmentActivity activity, String text){
        if(TextUtils.isEmpty(text)) {
            return;
        }
        ClipBoardDialog dialog = new ClipBoardDialog();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(), "ClipBoardDialog");
    }


    @Override
    protected String className() {
        return "ClipBoardDialog";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_clip_board;
    }

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected int getWidth() {
        return UIUtil.dip2px(300);
    }

    protected boolean cancelable() {
        return false;
    }

    protected boolean canceledOnTouchOutside() {
        return false;
    }

    @Override
    protected void initTitleBar() {

    }

    @Override
    protected void initListener() {
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.clipBoardTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String clipboardContent = mBinding.clipBoardTextView.getText().toString();
                ClipBoardUtil.copy(clipboardContent);
                ToastUtils.showToastCenterLong("已复制到剪贴板");
                return true;
            }
        });
    }


    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle == null) {
            dismissAllowingStateLoss();
            return;
        }
        String text = bundle.getString("text", "");
        if(TextUtils.isEmpty(text)) {
            return;
        }
        mBinding.clipBoardTextView.setText(text);
    }


}
