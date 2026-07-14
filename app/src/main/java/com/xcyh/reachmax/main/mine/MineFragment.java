package com.xcyh.reachmax.main.mine;

import android.content.Intent;

import com.baidu.baselibrary.base.fragment.BindingCustomFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.ui.AnimationUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.FragmentMineBinding;
import com.xcyh.reachmax.main.task.TaskCenterActivity;
import com.xcyh.reachmax.model.manager.Pitcher;

import java.util.Random;

/**
 * Created by haojiangfeng on 2024/11/12.
 */
public class MineFragment extends BindingCustomFragment<FragmentMineBinding, EmptyPresenter>  {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        int[] colorResArray = {
                R.color.color_avatar_0, R.color.color_avatar_1, R.color.color_avatar_2, R.color.color_avatar_3, R.color.color_avatar_4,
                R.color.color_avatar_5, R.color.color_avatar_6, R.color.color_avatar_7, R.color.color_avatar_8, R.color.color_avatar_9,
                R.color.color_avatar_10, R.color.color_avatar_11, R.color.color_avatar_12, R.color.color_avatar_13, R.color.color_avatar_14
        };
        int colorRes = colorResArray[new Random().nextInt(colorResArray.length)];
        mBinding.ivUserAvatar.setBackgroundResource(colorRes);
    }

    @Override
    protected void initListener() {
        mBinding.infoLayout.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), MineInfoActivity.class));
            AnimationUtil.overridePendingTransition(getActivity(), R.anim.slide_right_in_slow, R.anim.anim_none);
        });
        mBinding.taskLayout.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), TaskCenterActivity.class));
            AnimationUtil.overridePendingTransition(getActivity(), R.anim.slide_right_in_slow, R.anim.anim_none);
        });
    }

    @Override
    protected void initData() {
        mBinding.tvUserWord.setText(Pitcher.getInstance().getNameCapitalizeWord());
        mBinding.tvUserName.setText(Pitcher.getInstance().getUserName());
    }

}
