package com.yuanting.n2erp.main.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

/**
 * Created on 2018/8/22 17:39
 * Created by 薛立民
 * TEL 13262933389
 */
public class InformationDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_information_erp;
    }
}
