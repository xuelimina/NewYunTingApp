package com.yuanting.nomisdun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.nomisdun.main.about.AboutDelegate;
import com.yuanting.nomisdun.main.orderQuery.OrderQueryDelegate;
import com.yuanting.nomisdun.main.product.SelectProductDelegate;
import com.yuanting.nomisdun.main.shopQuery.ShopQueryDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/7/19 10:11
 * Created by 薛立民
 * TEL 13262933389
 */
public class NoMisDunIndexDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_about)
    void onClickAbout() {
        getSupportDelegate().start(new AboutDelegate());
    }

    @OnClick(R2.id.btn_product)
    void onClickProduct() {
        getSupportDelegate().start(new SelectProductDelegate());
    }

    @OnClick(R2.id.btn_case)
    void onClickCase() {

    }

    @OnClick(R2.id.btn_shop_query)
    void onClickShopQuery() {
        getSupportDelegate().start(new ShopQueryDelegate());
    }

    @OnClick(R2.id.btn_order_query)
    void onClickOrderQuery() {
        getSupportDelegate().start(new OrderQueryDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_index;
    }
}
