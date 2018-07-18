package com.yuanting.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.delegates.web.IPageLoadListener;
import com.yuanting.yunting_core.delegates.web.WebDelegateImpl;
import com.yuanting.yunting_ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created on 2018/5/17 15:51
 * Created by 薛立民
 * TEL 13262933389
 */
public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("https://blog.csdn.net/chengxu_hou/article/details/72466914");
        delegate.setTopDelegate(getParentDelegate());
        delegate.setPageLoadListener(new IPageLoadListener() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadEnd() {

            }
        });
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container, delegate);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
