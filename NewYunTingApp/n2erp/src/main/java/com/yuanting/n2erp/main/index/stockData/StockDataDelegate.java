package com.yuanting.n2erp.main.index.stockData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.main.index.stockData.customer.CustomerDelegate;
import com.yuanting.n2erp.main.index.stockData.partner.PartnerDelegate;
import com.yuanting.n2erp.main.index.stockData.user.UserInfoDelegate;
import com.yuanting.n2erp.main.index.stockData.user.UserInfoItemType;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/8/23 13:10
 * Created by 薛立民
 * TEL 13262933389
 */
public class StockDataDelegate extends LatteDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick({R2.id.icon_stock_in_data, R2.id.icon_stock_out_data, R2.id.icon_customer,
            R2.id.icon_user_info, R2.id.icon_stock_data_data, R2.id.icon_cooperation})
    void onClick(View view) {
        final int id = view.getId();
        final String lev = AccountManager.getLev();
        if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_1)) {
            if (id == R.id.icon_stock_in_data) {
                getSupportDelegate().start(new StockInDataDelegate());
            } else if (id == R.id.icon_stock_out_data) {
                getSupportDelegate().start(new StockOutDataDelegate());
            } else if (id == R.id.icon_stock_data_data) {
                getSupportDelegate().start(new StockDataDataDelegate());
            } else if (id == R.id.icon_customer) {
                getSupportDelegate().start(new CustomerDelegate());
            }
        } else {
            Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
        }
        if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_1)) {
            if (id == R.id.icon_cooperation) {
                getSupportDelegate().start(new PartnerDelegate());
            }
        } else {
            Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.icon_user_info) {
            getSupportDelegate().start(new UserInfoDelegate());
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_stock_data_erp;
    }
}
