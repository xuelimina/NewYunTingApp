package com.yuanting.n2erp.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.main.index.entry.EntryDelegate;
import com.yuanting.n2erp.main.index.form.FormDelegate;
import com.yuanting.n2erp.main.index.stockData.StockDataDelegate;
import com.yuanting.n2erp.main.index.stockData.user.UserInfoItemType;
import com.yuanting.n2erp.main.index.stockIn.StockInDelegate;
import com.yuanting.n2erp.main.index.stockOut.StockOutDelegate;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/8/22 15:20
 * Created by 薛立民
 * TEL 13262933389
 */
public class IndexDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick({R2.id.icon_stock_in, R2.id.icon_stock_out, R2.id.icon_stock_data, R2.id.icon_entry, R2.id.icon_expect, R2.id.icon_form})
    void onClickBtn(View view) {
        final int id = view.getId();
        final String lev = AccountManager.getLev();
        if (id == R.id.icon_stock_in) {
            if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_1)) {
                getParentDelegate().getSupportDelegate().start(new StockInDelegate());
            } else {
                Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.icon_stock_out) {
            if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_1)) {
                getParentDelegate().getSupportDelegate().start(new StockOutDelegate());
            } else {
                Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.icon_entry) {
            if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_2)) {
                getParentDelegate().getSupportDelegate().start(new EntryDelegate());
//            Toast.makeText(getContext(), "接车录入", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.icon_form) {
            if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_2)) {
                getParentDelegate().getSupportDelegate().start(new FormDelegate());
//            Toast.makeText(getContext(), "接车列表", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
            }
        }else if (id == R.id.icon_stock_data) {
            getParentDelegate().getSupportDelegate().start(new StockDataDelegate());
//            Toast.makeText(getContext(), "数据管理", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.icon_expect) {
            Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_erp;
    }
}
