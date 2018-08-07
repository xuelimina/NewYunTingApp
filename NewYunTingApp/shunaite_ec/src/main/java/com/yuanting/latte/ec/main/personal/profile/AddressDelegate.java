package com.yuanting.latte.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/6 10:32
 * Created by 薛立民
 * TEL 13262933389
 */
public class AddressDelegate extends LatteDelegate {
    @BindView(R2.id.tv_user_name)
    AppCompatTextView mTVUserName;
    @BindView(R2.id.tv_user_area)
    AppCompatTextView mTVUserArea;
    @BindView(R2.id.tv_user_partner_id)
    AppCompatTextView mTVUserPartnerId;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTVUserName.setText(AccountManager.getAccount());
        mTVUserArea.setText(AccountManager.getArea());
        mTVUserPartnerId.setText(AccountManager.getPartnerID());
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R2.id.tv_update_password)
    void passwordUpdate() {
        getSupportDelegate().start(new PasswordUpdateDelegate());
    }

    @OnClick(R2.id.btn_user_sign_out)
    void signOut() {
        AccountManager.setSignState(false);
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }
}
