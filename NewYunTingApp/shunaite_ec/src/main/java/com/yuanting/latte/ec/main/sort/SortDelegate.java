package com.yuanting.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.latte.ec.main.sort.content.ContentDelegate;
import com.yuanting.latte.ec.main.sort.list.VerticalListDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_ec.R;

/**
 * Created on 2018/5/3 16:42
 * Created by 薛立民
 * TEL 13262933389
 */
public class SortDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate(). loadRootFragment(R.id.vertical_list_container, listDelegate);
//        replaceFragment();
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
//         getSupportDelegate().replaceFragment();
    }
}
