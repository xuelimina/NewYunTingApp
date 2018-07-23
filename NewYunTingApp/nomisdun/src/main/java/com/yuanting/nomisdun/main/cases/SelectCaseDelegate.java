package com.yuanting.nomisdun.main.cases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created on 2018/7/23 15:02
 * Created by 薛立民
 * TEL 13262933389
 */
public class SelectCaseDelegate extends LatteDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_select_case;
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }


    @OnClick(R2.id.btn_picture)
    void pictureBtnOnClick() {
        getSupportDelegate().start(new ShowPictureCaseDelegate());
    }

    @OnClick(R2.id.btn_video)
    void videoBtnOnClick() {
        getSupportDelegate().start(new ShowVideoCaseDelegate());
    }

    private void startProduct() {

    }
}
