package com.yuanting.nomisdun.main.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/7/19 14:49
 * Created by 薛立民
 * TEL 13262933389
 */
public class SelectProductDelegate extends LatteDelegate {
    private boolean IsInterior = true;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        IsInterior = false;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_select_product;
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }


    @OnClick(R2.id.btn_interior)
    void interiorBtnOnClick() {
        IsInterior = true;
        startProduct();
    }

    @OnClick(R2.id.btn_external)
    void externalBtnOnClick() {
        IsInterior = false;
        startProduct();
    }

    private void startProduct() {
        final ProductDelegate delegate = new ProductDelegate();
        final Bundle bundle = new Bundle();
        bundle.putBoolean("IsInterior", IsInterior);
        delegate.setArguments(bundle);
        getSupportDelegate().start(delegate);
    }
}
