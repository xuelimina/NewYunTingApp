package com.yuanting.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created on 2018/5/7 16:28
 * Created by 薛立民
 * TEL 13262933389
 */
public class GoodsDetailDelegate extends LatteDelegate {
    public static GoodsDetailDelegate create() {
        return new GoodsDetailDelegate();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
