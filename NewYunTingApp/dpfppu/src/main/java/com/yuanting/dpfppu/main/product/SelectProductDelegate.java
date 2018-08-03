package com.yuanting.dpfppu.main.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.R2;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/7/19 14:49
 * Created by 薛立民
 * TEL 13262933389
 */
public class SelectProductDelegate extends BottomItemDelegate {
    private boolean IsInterior = true;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        IsInterior = false;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_select_product_dpf;
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
        getParentDelegate().getSupportDelegate().start(delegate);
    }
}
