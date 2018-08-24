package com.yuanting.n2erp.main.index.entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/8/23 13:10
 * Created by 薛立民
 * TEL 13262933389
 */
public class EntryDelegate extends LatteDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_entry_erp;
    }
}
