package com.yuanting.n2erp.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

/**
 * Created on 2018/8/22 17:40
 * Created by 薛立民
 * TEL 13262933389
 */
public class SettingDelegate extends BottomItemDelegate{
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting_erp;
    }
}
