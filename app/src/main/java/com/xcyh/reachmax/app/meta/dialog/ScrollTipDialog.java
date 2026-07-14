package com.xcyh.reachmax.app.meta.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.baselibrary.recycler.BindingRecyclerAdapter;
import com.baidu.baselibrary.widget.dialog.LoggerDialogFragment;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.LayoutCommonTextItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollTipDialog extends LoggerDialogFragment {

    private final static String DIALOG_TITLE = "DIALOG_TITLE";
    private final static String DIALOG_CONTENT = "DIALOG_CONTENT";
    private final static String DIALOG_CANCEL_VALUE = "DIALOG_CANCEL_VALUE";
    private final static String DIALOG_CONFIRM_VALUE = "DIALOG_CONFIRM_VALUE";


    // dialog监听器
    private OnDialogClickListener listener;



    @Override
    protected String className() {
        return "ScrollTipDialog";
    }

    /**
     * 获取Dialog对象
     *
     * @param title 提示语
     * @param content 内容
     * @param cancelValue 取消按钮显示文本
     * @param confirmValue 确定按钮显示文本
     */
    public static ScrollTipDialog newInstance(String title, ArrayList<String> content, String cancelValue, String confirmValue) {
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        args.putStringArrayList(DIALOG_CONTENT, content);
        args.putString(DIALOG_CANCEL_VALUE, cancelValue);
        args.putString(DIALOG_CONFIRM_VALUE, confirmValue);
        ScrollTipDialog tipDialog = new ScrollTipDialog();
        tipDialog.setArguments(args);
        return tipDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_recycler, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = "";
        ArrayList<String> list = null;
        String cancelValue = "";
        String confirmValue = "";
        Bundle bundle = getArguments();
        if (null != bundle) {
            title = bundle.getString(DIALOG_TITLE);
            list = bundle.getStringArrayList(DIALOG_CONTENT);
            cancelValue  = bundle.getString(DIALOG_CANCEL_VALUE);
            confirmValue  = bundle.getString(DIALOG_CONFIRM_VALUE);
        }

        if(list != null){
            RecyclerView recyclerView = view.findViewById(R.id.layout_tip_recycler_view);
            recyclerView.setAdapter(new Adapter(list));
        }

        ((TextView) view.findViewById(R.id.module_dialog_app_tip_title)).setText(title);
        TextView cancelButton = view.findViewById(R.id.module_dialog_app_tip_cancel);
        if (!TextUtils.isEmpty(cancelValue)) {
            cancelButton.setText(cancelValue);
        }
        TextView confirmButton = view.findViewById(R.id.module_dialog_app_tip_sure);
        if (!TextUtils.isEmpty(confirmValue)) {
            confirmButton.setText(confirmValue);
        }

        cancelButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancel();
            }
            dismiss();
        });
        confirmButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onConfirm();
            }
            dismiss();
        });
    }


    /**
     * 添加Dialog监听器
     *
     * @param listener 监听器
     */
    public void setOnDialogClickListener(@NonNull OnDialogClickListener listener) {
        this.listener = listener;
    }


    public interface OnDialogClickListener {
        void onConfirm();
        void onCancel();
    }

    private static class Adapter extends BindingRecyclerAdapter<LayoutCommonTextItemBinding, String> {

        public Adapter(List<String> dataList) {
            super(R.layout.layout_common_text_item, dataList);
        }

        @Override
        protected void handleView(BindingViewHolder<LayoutCommonTextItemBinding, String> holder, String data, int position) {
            LayoutCommonTextItemBinding binding = holder.getViewDataBinding();
            binding.textView.setText(data);
        }
    }
}
