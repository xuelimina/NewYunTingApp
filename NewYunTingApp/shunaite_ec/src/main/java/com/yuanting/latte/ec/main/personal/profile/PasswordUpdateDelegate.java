package com.yuanting.latte.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.yuanting.latte.ec.sign.ISignListener;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/7 14:26
 * Created by 薛立民
 * TEL 13262933389
 */
public class PasswordUpdateDelegate extends LatteDelegate implements IError, ISuccess, IFailure {
    @BindView(R2.id.edit_old_password)
    AppCompatEditText mETOldPassword;
    @BindView(R2.id.edit_new_password)
    AppCompatEditText mETNewPassword;
    private String mOldPassword;
    private String mNewPassword;
    private ISignListener mISignListener = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_password_update)
    void passwordUpdate() {
        if (checkedEditText()) {
            RestClient.builder().url("User/UpdatePassword")
                    .params("UserAccount", AccountManager.getAccount())
                    .params("OldPassword", mOldPassword)
                    .params("NewPassword", mNewPassword)
                    .error(this).success(this).failure(this)
                    .build().post();
        }

    }

    private boolean checkedEditText() {
        mOldPassword = mETOldPassword.getText().toString();
        mNewPassword = mETNewPassword.getText().toString();
        boolean isPass = true;
        if (mOldPassword.isEmpty()) {
            mETOldPassword.setError("请填写旧密码");
            isPass = false;
        } else {
            mETOldPassword.setError(null);
        }
        if (mNewPassword.isEmpty()) {
            mETNewPassword.setError("请填写新密码");
            isPass = false;
        }
        if (mNewPassword.length() < 6) {
            mETNewPassword.setError("新密码需要超过6位数");
            isPass = false;
        } else {
            mETNewPassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_password_update;
    }

    @Override
    public void onError(int code, String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String response) {
        _mActivity.onBackPressed();
    }
}
