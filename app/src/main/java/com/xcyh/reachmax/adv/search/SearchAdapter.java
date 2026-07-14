package com.xcyh.reachmax.adv.search;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.baidu.baselibrary.widget.CheckBox;
import com.base.util.collection.ListUtil;
import com.base.util.content.StringUtils;
import com.xcyh.reachmax.R;
import com.fold.recyclyerview.BaseMultiItemQuickAdapter;
import com.fold.recyclyerview.BaseViewHolder;
import com.xcyh.reachmax.adv.ViewUtil;
import com.xcyh.reachmax.model.bean.search.SearchItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/12/30.
 */
public class SearchAdapter extends BaseMultiItemQuickAdapter<SearchItem, BaseViewHolder> {


    public interface OnItemCheckChangeListener {
        void onItemCheckChange(CompoundButton checkBox, SearchItem item, int position);
    }

    private OnItemCheckChangeListener mOnItemCheckChangeListener;

    @NonNull
    private String mSearchText = "";


    public SearchAdapter(List<SearchItem> list) {
        super(list);
        addItemType(SearchItem.TYPE_NORMAL, R.layout.layout_search_result_item);
        addItemType(SearchItem.TYPE_SELECTED, R.layout.layout_search_select_item);
    }

    public void setOnItemCheckChangeListener(OnItemCheckChangeListener listener){
        mOnItemCheckChangeListener = listener;
    }

    public void setSearchText(String searchText){
        mSearchText = StringUtils.nonNull(searchText);
    }

    @NonNull
    public List<SearchItem> getCheckedList(){
        ArrayList<SearchItem> list = new ArrayList<>();
        if(ListUtil.isEmpty(getData())){
            return list;
        } else {
            for(SearchItem searchItem : getData()){
                if(searchItem.isChecked()){
                    list.add(searchItem);
                }
            }
            return list;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchItem item, int position) {
        int type = item.getItemType();
        if(type == SearchItem.TYPE_NORMAL){
            bindSearchResult(helper, item, mSearchText, position);
        } else {
            bindSearchSelected(helper, item, position);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchItem item) {
        // none
    }

    /**
     * 选中列表
     *
     * {@link R.layout#layout_search_select_item}
     */
    private void bindSearchSelected(BaseViewHolder helper, SearchItem item, int position) {
        helper.setTag(R.id.search_item_root, R.id.tag_item_data, item);
        helper.setText(R.id.search_item_Id, item.getSearchId());

        CheckBox checkBox = helper.getView(R.id.search_item_checkbox);
        checkBox.setCheckedWithoutCallback(item.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
                if(mOnItemCheckChangeListener != null){
                    mOnItemCheckChangeListener.onItemCheckChange(buttonView, item, position);
                }
            }
        });

        TextView textView = helper.getView(R.id.search_item_name);
        textView.setText(item.getSearchName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    /**
     * 搜索提示列表
     *
     * {@link R.layout#layout_search_result_item}
     */
    private void bindSearchResult(BaseViewHolder helper, SearchItem item, String searchText, int position){
        helper.setTag(R.id.search_item_root, R.id.tag_item_data, item);

        CheckBox checkBox = helper.getView(R.id.search_item_checkbox);
        checkBox.setCheckedWithoutCallback(item.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
                if(mOnItemCheckChangeListener != null){
                    mOnItemCheckChangeListener.onItemCheckChange(buttonView, item, position);
                }
            }
        });

        TextView textView = helper.getView(R.id.search_item_title);
        textView.setText(ViewUtil.getSpannableText(item.getSearchName(), searchText));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }





}
