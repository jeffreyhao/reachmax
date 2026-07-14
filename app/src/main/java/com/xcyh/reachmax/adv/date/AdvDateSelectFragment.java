package com.xcyh.reachmax.adv.date;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.baselibrary.base.fragment.BaseDialogFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.date.DateUtil;
import com.base.watcher.IWatched;
import com.base.watcher.Watcher;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.FragmentDateSelectBinding;
import com.xcyh.reachmax.app.callback.SelectListener;
import com.xcyh.reachmax.model.constant.AdvConst;
import com.xcyh.reachmax.model.event.AdvWatchEvent;

import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 *  日期筛选弹窗
 */
public class AdvDateSelectFragment extends BaseDialogFragment<FragmentDateSelectBinding, EmptyPresenter> implements IWatched {



    public static AdvDateSelectFragment newInstance(String dateText, String dateStart, String dateEnd, SelectListener listener) {
        AdvDateSelectFragment chapterSheetDialogFragment = new AdvDateSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", dateText);
        bundle.putString("dateStart", dateStart);
        bundle.putString("dateEnd", dateEnd);
        chapterSheetDialogFragment.setArguments(bundle);
        chapterSheetDialogFragment.setSelectListener(listener);
        return chapterSheetDialogFragment;
    }



    private SelectListener mSelectListener;

    private boolean isDateInit = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Watcher.getInstance().registerDataSetObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Watcher.getInstance().unregisterObserver(this);
    }

    public void setSelectListener(SelectListener listener){
        mSelectListener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_date_select;
    }

    @Override
    protected int getAnimations() {
        return R.style.bottomDialog;
    }

    @Override
    protected void initTitleBar() {
        // none
    }

    @Override
    public void initListener() {
        mBinding.layoutToday.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutYesterday.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutLastWeek.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutLastMonth.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutLastHalfYear.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutThisWeek.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutThisMonth.setOnClickListener(v->{
            selectDate(v);
        });
        mBinding.layoutCustom.setOnClickListener(v->{
            showCustomSelect();
        });
        mBinding.ivClose.setOnClickListener(v->{
            closeDialog();
        });
    }

    @Override
    protected void initData() {
        long current = System.currentTimeMillis();
        long todayStartMills = DateUtil.getTodayStartTimestamp();
        long todayEndMills = DateUtil.getTodayEndTimestamp();
        long yesterdayStartMills = todayStartMills - DateUtil.MILLIS_PER_DAY;

        long last7DaysStartMills = todayStartMills - DateUtil.MILLIS_PER_WEEK;
        long last30DaysStartMills = todayStartMills - 30 * DateUtil.MILLIS_PER_DAY;
        long last180DaysStartMills = todayStartMills - 180 * DateUtil.MILLIS_PER_DAY;

        Date thisWeekStart = DateUtil.getWeekStartDate();
        Date thisMonthStart = DateUtil.getMonthStartDate();

        String today = DateUtil.getFormatDate(current, DateUtil.formatYMD, Locale.CHINESE);
        String yesterday = DateUtil.getFormatDate(yesterdayStartMills, DateUtil.formatYMD, Locale.CHINESE);

        String last7DaysStart = DateUtil.getFormatDate(last7DaysStartMills, DateUtil.formatYMD, Locale.CHINESE);
        String last30DaysStart = DateUtil.getFormatDate(last30DaysStartMills, DateUtil.formatYMD, Locale.CHINESE);
        String last180DaysStart = DateUtil.getFormatDate(last180DaysStartMills, DateUtil.formatYMD, Locale.CHINESE);
        String last7Days = last7DaysStart + AdvConst.TXT_SPLIT + yesterday;
        String last30Days = last30DaysStart + AdvConst.TXT_SPLIT + yesterday;
        String last180Days = last180DaysStart + AdvConst.TXT_SPLIT + yesterday;

        String weekStart = DateUtil.getFormatDate(thisWeekStart, DateUtil.formatYMD, Locale.CHINESE);
        String monthStart = DateUtil.getFormatDate(thisMonthStart, DateUtil.formatYMD, Locale.CHINESE);
        String thisWeek = weekStart + AdvConst.TXT_SPLIT + today;
        String thisMonth = monthStart + AdvConst.TXT_SPLIT + today;

        mBinding.tvToday.setText(today);
        mBinding.layoutToday.setTag(R.id.tag_time_title, mBinding.tvTodayTitle.getText().toString());
        mBinding.layoutToday.setTag(R.id.tag_time_string, today);
        mBinding.layoutToday.setTag(R.id.tag_start_timestamp, todayStartMills);
        mBinding.layoutToday.setTag(R.id.tag_end_timestamp, todayEndMills);
        mBinding.layoutToday.setTag(R.id.tag_date_start, today);
        mBinding.layoutToday.setTag(R.id.tag_date_end, today);

        mBinding.tvYesterday.setText(yesterday);
        mBinding.layoutYesterday.setTag(R.id.tag_time_title, mBinding.tvYesterdayTitle.getText().toString());
        mBinding.layoutYesterday.setTag(R.id.tag_time_string, yesterday);
        mBinding.layoutYesterday.setTag(R.id.tag_start_timestamp, yesterdayStartMills);
        mBinding.layoutYesterday.setTag(R.id.tag_end_timestamp, todayStartMills);
        mBinding.layoutYesterday.setTag(R.id.tag_date_start, yesterday);
        mBinding.layoutYesterday.setTag(R.id.tag_date_end, yesterday);

        mBinding.tvLastWeek.setText(last7Days);
        mBinding.layoutLastWeek.setTag(R.id.tag_time_title, mBinding.tvLastWeekTitle.getText().toString());
        mBinding.layoutLastWeek.setTag(R.id.tag_time_string, last7Days);
        mBinding.layoutLastWeek.setTag(R.id.tag_start_timestamp, last7DaysStartMills);
        mBinding.layoutLastWeek.setTag(R.id.tag_end_timestamp, todayStartMills);
        mBinding.layoutLastWeek.setTag(R.id.tag_date_start, last7DaysStart);
        mBinding.layoutLastWeek.setTag(R.id.tag_date_end, yesterday);

        mBinding.tvLastMonth.setText(last30Days);
        mBinding.layoutLastMonth.setTag(R.id.tag_time_title, mBinding.tvLastMonthTitle.getText().toString());
        mBinding.layoutLastMonth.setTag(R.id.tag_time_string, last30Days);
        mBinding.layoutLastMonth.setTag(R.id.tag_start_timestamp, last30DaysStartMills);
        mBinding.layoutLastMonth.setTag(R.id.tag_end_timestamp, todayStartMills);
        mBinding.layoutLastMonth.setTag(R.id.tag_date_start, last30DaysStart);
        mBinding.layoutLastMonth.setTag(R.id.tag_date_end, yesterday);

        mBinding.tvLastHalfYear.setText(last180Days);
        mBinding.layoutLastHalfYear.setTag(R.id.tag_time_title, mBinding.tvLastHalfYearTitle.getText().toString());
        mBinding.layoutLastHalfYear.setTag(R.id.tag_time_string, last180Days);
        mBinding.layoutLastHalfYear.setTag(R.id.tag_start_timestamp, last180DaysStartMills);
        mBinding.layoutLastHalfYear.setTag(R.id.tag_end_timestamp, todayStartMills);
        mBinding.layoutLastHalfYear.setTag(R.id.tag_date_start, last180DaysStart);
        mBinding.layoutLastHalfYear.setTag(R.id.tag_date_end, yesterday);

        mBinding.tvThisWeek.setText(thisWeek);
        mBinding.layoutThisWeek.setTag(R.id.tag_time_title, mBinding.tvThisWeekTitle.getText().toString());
        mBinding.layoutThisWeek.setTag(R.id.tag_time_string, thisWeek);
        mBinding.layoutThisWeek.setTag(R.id.tag_start_timestamp, thisWeekStart.getTime());
        mBinding.layoutThisWeek.setTag(R.id.tag_end_timestamp, todayEndMills);
        mBinding.layoutThisWeek.setTag(R.id.tag_date_start, weekStart);
        mBinding.layoutThisWeek.setTag(R.id.tag_date_end, today);

        mBinding.tvThisMonth.setText(thisMonth);
        mBinding.layoutThisMonth.setTag(R.id.tag_time_title, mBinding.tvThisMonthTitle.getText().toString());
        mBinding.layoutThisMonth.setTag(R.id.tag_time_string, thisMonth);
        mBinding.layoutThisMonth.setTag(R.id.tag_start_timestamp, thisMonthStart.getTime());
        mBinding.layoutThisMonth.setTag(R.id.tag_end_timestamp, todayEndMills);
        mBinding.layoutThisMonth.setTag(R.id.tag_date_start, monthStart);
        mBinding.layoutThisMonth.setTag(R.id.tag_date_end, today);

        if(getArguments() != null){
            String dateStart = getArguments().getString("dateStart", "");
            String dateEnd = getArguments().getString("dateEnd", "");
            String dateText = getArguments().getString("text", "");
            setSelectDate(dateText);
        }
        isDateInit = false;
    }

    private void setSelectDate(String dateText) {
        switch (dateText){
            case "今天":        setItemSelect(mBinding.layoutToday);        break;
            case "昨天":        setItemSelect(mBinding.layoutYesterday);     break;
            case "过去7天":     setItemSelect(mBinding.layoutLastWeek);      break;
            case "过去一个月":   setItemSelect(mBinding.layoutLastMonth);     break;
            case "过去六个月":   setItemSelect(mBinding.layoutLastHalfYear);  break;
            case "本周":        setItemSelect(mBinding.layoutThisWeek);      break;
            case "本月":        setItemSelect(mBinding.layoutThisMonth);     break;
            default:
                setItemSelect(mBinding.layoutCustom);
                mBinding.tvCustom.setText(dateText);
                break;
        }
    }

    private void setItemSelect(ViewGroup layout){
        layout.setSelected(true);
        int colorAccent = ContextCompat.getColor(mContext, R.color.colorAccent);
        for(int i = 0; i < layout.getChildCount(); i++){
            View child = layout.getChildAt(i);
            if(child instanceof TextView){
                TextView textView = (TextView) child;
                textView.setTextColor(colorAccent);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
            } else if(child instanceof ImageView){
                ((ImageView) child).setColorFilter(colorAccent);
            }
        }
    }

    private void selectDate(View v) {
        String text = (String) v.getTag(R.id.tag_time_title);
        String date = (String) v.getTag(R.id.tag_time_string);
        long startTimeStamp = (long) v.getTag(R.id.tag_start_timestamp);
        long endTimeStamp = (long) v.getTag(R.id.tag_end_timestamp);
        String dateStart = (String) v.getTag(R.id.tag_date_start);
        String dateEnd = (String) v.getTag(R.id.tag_date_end);

        if(mSelectListener != null){
            Bundle bundle = new Bundle();
            bundle.putString("text", text);
            bundle.putString("dateStart", dateStart);
            bundle.putString("dateEnd", dateEnd);
            bundle.putLong("startTimeStamp", startTimeStamp);
            bundle.putLong("endTimeStamp", endTimeStamp);
            mSelectListener.onSelect(bundle);
        }
        closeDialog();
    }

    private void closeDialog() {
        if (null == getActivity()) {
            return;
        }
        getActivity().runOnUiThread(this::dismiss);
    }

    private void showCustomSelect() {
        if(getActivity() == null){
            return;
        }
        String dateStart = "", dateEnd = "";
        if(!TextUtils.isEmpty(mBinding.tvCustom.getText().toString())){
            Bundle arguments = getArguments();
            dateStart = arguments == null ? "" : arguments.getString("dateStart", "");
            dateEnd = arguments == null ? "" : arguments.getString("dateEnd", "");
        }
        boolean showDialog = AdvDatePickFragment.show(getActivity(), dateStart, dateEnd, mSelectListener);
        if(showDialog){
            closeDialog();
        }
    }

    @Override
    public void notifyWatcher(int event, Object object) {
        if(event == AdvWatchEvent.EXTEND_MID_NIGHT){ // 跨天
            if(isDateInit){
                initData();
            }
        }
    }

    @Override
    protected String className() {
        return getClass().getSimpleName();
    }
}