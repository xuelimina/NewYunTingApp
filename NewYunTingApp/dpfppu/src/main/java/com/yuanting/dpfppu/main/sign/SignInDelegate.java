package com.yuanting.dpfppu.main.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/4/27 13:01
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;
    private ISignListener mISignListener = null;

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder().url("User/Login")
                    .params("account", mPhone.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            SignHandler.onSignIn(response, mISignListener);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }).build().post();
        }
    }


    private boolean checkForm() {
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        boolean isPass = true;
        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError(getString(R.string.sign_up_delegate_phone_error));
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError(getString(R.string.sign_up_delegate_password_error));
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        return isPass;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
            mISignListener = new ISignListener() {
                @Override
                public void onSignInSuccess() {
                    activity.onBackPressed();
                }

                @Override
                public void onSignUpSuccess() {

                }
            };
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in_dpf;
    }
}
