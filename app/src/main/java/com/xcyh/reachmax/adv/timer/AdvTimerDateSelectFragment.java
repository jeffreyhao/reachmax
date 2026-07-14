package com.xcyh.reachmax.adv.timer;

import android.os.Bundle;

import com.baidu.baselibrary.base.fragment.BaseDialogFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.date.DateUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.FragmentTimerDateSelectBinding;
import com.loper7.date_time_picker.DateTimeConfig;
import com.xcyh.reachmax.app.callback.SubmitListener;


/**
 * 日期选择
 */
public class AdvTimerDateSelectFragment extends BaseDialogFragment<FragmentTimerDateSelectBinding, EmptyPresenter>{



    public static AdvTimerDateSelectFragment newInstance(long timeStamp, SubmitListener listener) {
        AdvTimerDateSelectFragment chapterSheetDialogFragment = new AdvTimerDateSelectFragment();
        chapterSheetDialogFragment.mSubmitListener = listener;
        Bundle bundle = new Bundle();
        bundle.putLong("timeStamp", timeStamp);
        chapterSheetDialogFragment.setArguments(bundle);
        return chapterSheetDialogFragment;
    }


    private long mSelectTimeStamp;
    private SubmitListener mSubmitListener;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_timer_date_select;
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
        mBinding.picker.setOnDateTimeChangedListener(aLong -> {
            mSelectTimeStamp = aLong;
            return null;
        });
    }


    @Override
    protected void initData() {
        mSelectTimeStamp = getArguments().getLong("timeStamp", 0);
        mBinding.picker.setDisplayType(new int[]{
                DateTimeConfig.YEAR, DateTimeConfig.MONTH, DateTimeConfig.DAY, DateTimeConfig.HOUR, DateTimeConfig.MIN
        });
        mBinding.picker.setDefaultMillisecond(mSelectTimeStamp);

        long current = System.currentTimeMillis();
        long yesterday = current - DateUtil.MILLIS_PER_DAY;
        mBinding.picker.setMinMillisecond(yesterday);
        mBinding.picker.setMaxMillisecond(current + DateUtil.MILLIS_PER_YEAR * 5);
    }


    private void closeDialog() {
        if (null == getActivity()) {
            return;
        }
        getActivity().runOnUiThread(this::dismiss);
    }

    private void doSubmit(){
        if(mSelectTimeStamp == 0){
            ToastUtils.showToastCenter("请选择时间");
            return;
        }
        closeDialog();
        if(mSubmitListener != null){
            Bundle bundle = new Bundle();
            bundle.putLong("timestamp", mSelectTimeStamp);
            bundle.putString("startTime", DateUtil.getDateYMDHMS(mSelectTimeStamp));
            mSubmitListener.onClickSubmit(0, bundle);
        }
    }


    @Override
    protected String className() {
        return getClass().getSimpleName();
    }
}