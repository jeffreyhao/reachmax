package com.xcyh.reachmax.adv.date;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.baselibrary.base.fragment.BaseDialogFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.date.DateUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.xcyh.reachmax.app.meta.dialog.base.BaseDialog;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.FragmentAdvDatePickBinding;
import com.github.gzuliyujiang.calendarpicker.core.CalendarView;
import com.github.gzuliyujiang.calendarpicker.core.ColorScheme;
import com.github.gzuliyujiang.calendarpicker.core.OnDateSelectedListener;
import com.xcyh.reachmax.app.callback.SelectListener;
import com.xcyh.reachmax.model.constant.AdvConst;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * 自定义日期选择弹窗
 */
public class AdvDatePickFragment extends BaseDialogFragment<FragmentAdvDatePickBinding, EmptyPresenter> implements OnDateSelectedListener {


    public static boolean show(FragmentActivity activity, String dateStart, String dateEnd, SelectListener listener){
        AdvDatePickFragment advDatePickFragment = new AdvDatePickFragment();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag("date_pick");
        if (fragment instanceof BaseDialog) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("dateStart", dateStart);
        bundle.putString("dateEnd", dateEnd);
        advDatePickFragment.setArguments(bundle);
        advDatePickFragment.setSelectListener(listener);
        advDatePickFragment.showFragment(activity, "date_pick");
        return true;
    }

    private Date mDateStart;
    private Date mDateEnd;

    private SelectListener mSelectListener;

    public void setSelectListener(SelectListener listener){
        mSelectListener = listener;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adv_date_pick;
    }

    @Override
    protected int getAnimations() {
        return R.style.bottomDialog;
    }

    @Override
    protected void initTitleBar() {

    }

    @Override
    public void initListener() {
        mBinding.ivClose.setOnClickListener(v->{
            closeDialog();
        });
        mBinding.btnCancel.setOnClickListener(v->{
            closeDialog();
        });
        mBinding.btnSubmit.setOnClickListener(v->{
            doSubmit();
        });
    }


    @Override
    protected void initData() {
        Date minDate = new Date(System.currentTimeMillis() - 2 * DateUtil.MILLIS_PER_YEAR);
        Date maxDate = Calendar.getInstance().getTime();

        Date startDate = null, endDate = null;
        Bundle arguments = getArguments();
        if(arguments != null){
            String dateStart = arguments.getString("dateStart", ""); // 2025-03-03
            String dateEnd = arguments.getString("dateEnd", "");  // 2025-03-26
            if(!TextUtils.isEmpty(dateStart) && !TextUtils.isEmpty(dateEnd)){
                startDate = DateUtil.parseYmdDate(dateStart);
                endDate = DateUtil.parseYmdDate(dateEnd);
            }
        }
        if(startDate != null && endDate!= null){
            mDateStart = startDate;
            mDateEnd = endDate;
            updateSelectedText();
        }

        ColorScheme colorScheme = new ColorScheme();
        colorScheme
                .dayNormalTextColor(getResources().getColor(R.color.black))
                .daySelectTextColor(getResources().getColor(R.color.black))
                .daySelectBackgroundColor(Color.parseColor("#FFE9F3FF"))
                .daySelectBorderBackgroundColor(Color.parseColor("#FF559AF0"))
                ;

        CalendarView horizontalCalendarView = mBinding.datePicker;
        horizontalCalendarView.setColorScheme(colorScheme);
        horizontalCalendarView.enablePagerSnap();
        horizontalCalendarView.getAdapter().setOnCalendarSelectedListener(this);
        horizontalCalendarView.getAdapter()
                .notify(false)
                .single(false)
                .valid(minDate, maxDate)
                .intervalNotes("开始", "结束")
                .range(minDate, maxDate)
                .select(startDate, endDate)
                .refresh();

        int pageCount = horizontalCalendarView.getAdapter().getItemCount();
        if(pageCount > 1){
            horizontalCalendarView.getBodyView().scrollToPosition(pageCount - 1);
        }
    }


    private void closeDialog() {
        if (null == getActivity()) {
            return;
        }
        getActivity().runOnUiThread(this::dismiss);
    }

    private void doSubmit(){
        if(mDateStart == null || mDateEnd == null){
            ToastUtils.showToastCenter("请选择日期");
            return;
        }
        String currentYear = DateUtil.getDateYYYY();
        boolean inThisYear = DateUtil.getDateYYYY(mDateStart).equals(currentYear) && DateUtil.getDateYYYY(mDateEnd).equals(currentYear);
        String dateStart = DateUtil.getDateYMD(mDateStart);
        String dateEnd  = DateUtil.getDateYMD(mDateEnd);
        String text = inThisYear ? (DateUtil.getDateMD(mDateStart) + AdvConst.TXT_SPLIT + DateUtil.getDateMD(mDateEnd))
                : dateStart + AdvConst.TXT_SPLIT + dateEnd;

        if(mSelectListener != null){
            Bundle bundle = new Bundle();
            bundle.putString("text", text);
            bundle.putString("dateStart", dateStart);
            bundle.putString("dateEnd", dateEnd);
            bundle.putLong("startTimeStamp", mDateStart.getTime());
            bundle.putLong("endTimeStamp", mDateEnd.getTime());
            mSelectListener.onSelect(bundle);
        }
        closeDialog();
    }


    @Override
    public void onSingleSelected(@NonNull Date date) {
        mDateStart = date;
        mDateEnd = date;
        updateSelectedText();
    }

    @Override
    public void onRangeSelected(@NonNull Date start, @NonNull Date end) {
        mDateStart = start;
        mDateEnd = end;
        updateSelectedText();
    }

    private void updateSelectedText() {
        String currentYear = DateUtil.getDateYYYY();
        if(mDateStart.getTime() == mDateEnd.getTime()){
            boolean inThisYear = DateUtil.getDateYYYY(mDateStart).equals(currentYear);
            String date = inThisYear
                    ? DateUtil.getDateMD(mDateStart)
                    : DateUtil.getDateYMD(mDateStart);
            mBinding.tvSelectedDate.setText(date);
        } else {
            boolean inThisYear = DateUtil.getDateYYYY(mDateStart).equals(currentYear) && DateUtil.getDateYYYY(mDateEnd).equals(currentYear);
            String date = inThisYear
                    ? DateUtil.getDateMD(mDateStart) + AdvConst.TXT_SPLIT + DateUtil.getDateMD(mDateEnd)
                    : DateUtil.getDateYMD(mDateStart) + AdvConst.TXT_SPLIT + DateUtil.getDateYMD(mDateEnd);
            mBinding.tvSelectedDate.setText(date);
        }

        long interval = mDateEnd.getTime() - mDateStart.getTime();
        int days = (int) (interval / DateUtil.MILLIS_PER_DAY + 1);
        mBinding.tvSelectedDays.setText(String.format(Locale.CHINA, "已选择%d天", days));
    }

    @Override
    protected String className() {
        return "AdvDatePickFragment";
    }
}