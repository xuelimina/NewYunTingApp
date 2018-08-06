package com.yuanting.latte.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.OnClick;

/**
 * Created on 2018/8/6 10:31
 * Created by 薛立民
 * TEL 13262933389
 */
public class QADelegate extends LatteDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_qa;
    }
}
