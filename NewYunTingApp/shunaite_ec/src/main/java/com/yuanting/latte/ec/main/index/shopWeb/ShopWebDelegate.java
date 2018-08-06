package com.yuanting.latte.ec.main.index.shopWeb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.delegates.web.WebDelegateImpl;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/6 11:01
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShopWebDelegate extends LatteDelegate {
    @BindView(R2.id.tv_bar_title)
    AppCompatTextView mTVTitle;
    private String url;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            url = args.getString("Url");
            mTVTitle.setText(args.getString("Title"));
            final WebDelegateImpl delegate = WebDelegateImpl.create(url);
            delegate.setTopDelegate(getParentDelegate());
            getSupportDelegate().loadRootFragment(R.id.web_discovery_container, delegate);
        }
    }
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_web;
    }
}
