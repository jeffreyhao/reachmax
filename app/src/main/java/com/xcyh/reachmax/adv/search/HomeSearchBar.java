package com.xcyh.reachmax.adv.search;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.baselibrary.util.keyboard.KeyboardUtil;
import com.base.util.collection.ListUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.app.meta.ui.widget.RippleResource;
import com.xcyh.reachmax.model.bean.search.SearchItem;
import com.xcyh.reachmax.model.constant.AdvItemLevel;

import java.util.List;
import java.util.Locale;

/**
 * Created by haojiangfeng on 2024/11/22.
 */
public class HomeSearchBar extends FrameLayout {


    public interface OnSearchBarListener {

        /**
         * 搜索框文字变化
         */
        void onSearchBarTextChanged(CharSequence s);

        /**
         * 输入法中的搜索键
         */
        void onClickKeyboardSearch(CharSequence searchText);

        /**
         * 输入法中的回车键
         */
        void onClickKeyboardDone(CharSequence searchText);

        /**
         * 点击选中选项
         */
        void onClickSelected();
    }


    private TextView tvResult1, tvResult2, tvResultCount;
    private EditText etSearch;
    private ImageView ivIcon, ivClose;
    private ViewGroup llResultLayout;


    private OnSearchBarListener mOnSearchBarListener;


    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ivClose.setVisibility(s.toString().trim().isEmpty() ? View.GONE : View.VISIBLE);
            if(mOnSearchBarListener != null){
                mOnSearchBarListener.onSearchBarTextChanged(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


    public HomeSearchBar(Context context) {
        super(context);
        init();
    }

    public HomeSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HomeSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){
        inflate(getContext(), R.layout.view_adv_home_search, this);

        etSearch = findViewById(R.id.et_search_txt);
        ivIcon = findViewById(R.id.iv_search_icon);
        ivClose = findViewById(R.id.iv_search_close);

        llResultLayout = findViewById(R.id.ll_result_layout);
        tvResult1 = findViewById(R.id.tv_result_1);
        tvResult2 = findViewById(R.id.tv_result_2);
        tvResultCount = findViewById(R.id.tv_result_count);

        tvResult1.setTag(R.id.tag_tab_level, AdvItemLevel.ADV_ACCOUNT);
        tvResult2.setTag(R.id.tag_tab_level, AdvItemLevel.ADV_SERIAL);
        tvResultCount.setTag(R.id.tag_tab_level, AdvItemLevel.ADV_GROUP);

        ivClose.setBackgroundResource(RippleResource.rippleCircleResource);
        initListeners();
    }

    private void initListeners(){
        ivClose.setOnClickListener(v -> {
            if(isSearchSelectedMode()){
                clearSearchResult();
            } else {
                clearSearchText();
            }
        });

        tvResultCount.setOnClickListener(v -> {
            if(mOnSearchBarListener != null){
                mOnSearchBarListener.onClickSelected();
            }
        });

        etSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            String text = textView.getText().toString();
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEARCH:  // 搜索键
                    if(mOnSearchBarListener != null){
                        mOnSearchBarListener.onClickKeyboardSearch(text);
                    }
                    break;
                case EditorInfo.IME_ACTION_DONE:  // 回车键
                    if(mOnSearchBarListener != null){
                        mOnSearchBarListener.onClickKeyboardDone(text);
                    }
                    break;
            }
            return false;
        });
        etSearch.addTextChangedListener(mTextWatcher);
    }

    private void clearSearchResult() {
        selectSearchResult(null);
        etSearch.setText("");
    }

    private void clearSearchText(){
        etSearch.setText("");
    }


    public void setOnSearchBarListener(OnSearchBarListener listener){
        this.mOnSearchBarListener = listener;
    }

    public EditText getEditText(){
        return etSearch;
    }

    public void resetSearchText(String content){
        etSearch.removeTextChangedListener(mTextWatcher);
        etSearch.setText(content);
        if(content != null){
            etSearch.setSelection(content.length());
        }
        etSearch.addTextChangedListener(mTextWatcher);
        ivClose.setVisibility(!TextUtils.isEmpty(content) ? View.VISIBLE : View.GONE);
    }

    public void selectSearchResult(List<SearchItem> list){
        if(ListUtil.isEmpty(list)){
            ivIcon.setVisibility(View.VISIBLE);
            etSearch.setVisibility(View.VISIBLE);
            llResultLayout.setVisibility(View.GONE);
            tvResultCount.setVisibility(View.GONE);
        } else {
            ivIcon.setVisibility(View.GONE);
            etSearch.setVisibility(View.INVISIBLE);
            llResultLayout.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.VISIBLE);

            int size = list.size();
            if(size == 1){
                tvResult1.setVisibility(View.VISIBLE);;
                tvResult2.setVisibility(View.GONE);
                tvResult1.setText(list.get(0).getSearchName());
                tvResultCount.setVisibility(View.GONE);
            } else {
                tvResult1.setVisibility(View.VISIBLE);
                tvResult2.setVisibility(View.VISIBLE);
                tvResult1.setText(list.get(0).getSearchName());
                tvResult2.setText(list.get(1).getSearchName());
                tvResultCount.setVisibility(View.VISIBLE);
                tvResultCount.setText(String.format(Locale.CHINA, "已选%d项", size));
            }
        }
    }

    /**
     * 是否是搜索结果模式
     */
    private boolean isSearchSelectedMode(){
        return llResultLayout.getVisibility() == View.VISIBLE;
    }

    public void setKeyboardVisible(boolean show){
        if(show){
            KeyboardUtil.showKeyboard(getContext(), etSearch);
        } else {
            KeyboardUtil.hideKeyboard((Activity) getContext());
        }
    }


}
