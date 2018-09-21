package com.yuanting.n2erp.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.main.index.stockData.user.UserInfoItemType;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/8/22 17:40
 * Created by 薛立民
 * TEL 13262933389
 */
public class SettingDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.re_layout_ground)
    void onClick() {
        final String lev = AccountManager.getLev();
        if (lev.contains(UserInfoItemType.LEVER_0) || lev.contains(UserInfoItemType.LEVER_2)) {
            getParentDelegate().getSupportDelegate().start(new GroupDelegate());
        } else {
            Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting_erp;
    }
}
