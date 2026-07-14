package com.xcyh.reachmax.adv;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.baidu.baselibrary.Widget;
import com.baidu.baselibrary.util.App;
import com.base.util.content.StringUtils;
import com.xcyh.reachmax.app.meta.dialog.ScrollTipDialog;
import com.xcyh.reachmax.app.meta.dialog.base.BaseDialog;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.app.meta.ui.custom.ReachMaxTitleView;
import com.xcyh.reachmax.model.constant.AdvRankRule;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by haojiangfeng on 2024/11/14.
 */
public class ViewUtil {


    /**
     *  Home页Tab-TextView
     */
    public static IPagerTitleView getPagerTitleView(Activity activity, String text, View.OnClickListener listener)  {
        ReachMaxTitleView textView = new ReachMaxTitleView(activity);
        textView.setNormalColor(ContextCompat.getColor(activity, R.color.pp_color_666666));
        textView.setSelectedColor(ContextCompat.getColor(activity, R.color.color_title_black));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setMinWidth(activity.getResources().getDimensionPixelSize(R.dimen.dp_88));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(listener);
//        textView.setBackgroundResource(R.color.Cr_FF503D);
        return textView;
    }


    /**
     *  Home页Tab-滑动条
     */
    public static IPagerIndicator getPagerIndicator(Activity activity) {
        LinePagerIndicator indicator = new LinePagerIndicator(activity) {
            @SuppressLint("DrawAllocation")
            @Override
            protected void onDraw(Canvas canvas) {
                try {
                    Class<LinePagerIndicator> clazz = LinePagerIndicator.class;
                    Field lineRectFField = clazz.getDeclaredField("mLineRect");
                    lineRectFField.setAccessible(true);
                    Field paintField = clazz.getDeclaredField("mPaint");
                    paintField.setAccessible(true);
                    RectF lineRectF = (RectF) lineRectFField.get(this);
                    Paint paint = (Paint) paintField.get(this);
                    if (lineRectF != null && paint != null) {
                        // 设置渐变色
                        paint.setShader(new LinearGradient(lineRectF.left, lineRectF.top, lineRectF.right, lineRectF.bottom,
                                new int[]{
                                        getResources().getColor(R.color.pp_color_222222),
                                        getResources().getColor(R.color.pp_color_222222,
                                                activity.getTheme())},
                                null, LinearGradient.TileMode.CLAMP));
                        canvas.drawRoundRect(lineRectF, getRoundRadius(), getRoundRadius(), paint);
                    } else {
                        super.onDraw(canvas);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                super.onDraw(canvas);
            }
        };

        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineWidth(Widget.dip2px(20));
        indicator.setLineHeight(Widget.dip2px(2));
        indicator.setRoundRadius(Widget.dip2px(1));
        // indicator拉伸效果
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        return indicator;
    }


    /**
     * 负责人 Textview
     */
    public static CheckedTextView newPrincipalView(Context context){
        /*
            <CheckedTextView
                android:layout_width="wrap_content"
                android:layout_height="30dp"
                android:paddingHorizontal="13dp"
                android:background="@drawable/selector_adv_state"
                android:gravity="center"
                android:textColor="@color/color_selector_state"
                android:textSize="12dp"
                android:textStyle="bold"
                android:checked="false"
                tools:text="负责人"
                />
         */
        CheckedTextView checkedTextView = (CheckedTextView) View.inflate(context, R.layout.view_principal, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                context.getResources().getDimensionPixelSize(R.dimen.height_state_view));
        checkedTextView.setLayoutParams(layoutParams);
        return checkedTextView;
    }


    @Deprecated
    public static void showRecyclerDialog(FragmentManager fragmentManager,
                                   String title,
                                   ArrayList<String> content,
                                   String confirmValue,
                                   ScrollTipDialog.OnDialogClickListener listener) {
        String tag = "dialog_app_alert";
        ScrollTipDialog appTipDialog = ScrollTipDialog.newInstance(title, content, "", confirmValue);
        appTipDialog.setOnDialogClickListener(listener);
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment instanceof BaseDialog) {
            return;
        }
        appTipDialog.show(fragmentManager, tag);
    }

    /**
     * 排序 view
     */
    public static void showRankDrawable(TextView textView, @AdvRankRule String rule){
        switch (rule){
            case AdvRankRule.DEFAULT:
                Drawable defaultRight = ResourcesCompat.getDrawable(textView.getResources(), R.drawable.ic_arrow_default, textView.getResources().newTheme());
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, defaultRight, null);
                break;
            case AdvRankRule.DESC:
                Drawable descRight = ResourcesCompat.getDrawable(textView.getResources(), R.drawable.ic_arrow_down, textView.getResources().newTheme());
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, descRight, null);
                break;
            case AdvRankRule.ASC:
                Drawable ascRight = ResourcesCompat.getDrawable(textView.getResources(), R.drawable.ic_arrow_up, textView.getResources().newTheme());
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ascRight, null);
                break;
        }
    }


    /**
     * @param originalText  原始文本
     * @param searchText    搜索关键词
     *
     * @return 标红搜索关键词的富文本
     */
    public static CharSequence getSpannableText(@Nullable String originalText, @Nullable String searchText){
        String text = StringUtils.nonNull(originalText);
        if(TextUtils.isEmpty(originalText) || TextUtils.isEmpty(searchText)){
            return text;
        }

        SpannableString spannableString = new SpannableString(text);

        int redColor = App.getColor(R.color.color_text_red);
        int index = 0;
        while ((index = originalText.toLowerCase().indexOf(searchText.toLowerCase(), index))!= -1) {
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(redColor);
            spannableString.setSpan(blueSpan, index, index + searchText.length(), 0);
            index += searchText.length();
        }
        return spannableString;
    }


    public static void setEnable(boolean enable, View... views) {
        if(views == null) {
            return;
        }
        for(View view : views) {
            view.setEnabled(enable);
        }
    }

}
