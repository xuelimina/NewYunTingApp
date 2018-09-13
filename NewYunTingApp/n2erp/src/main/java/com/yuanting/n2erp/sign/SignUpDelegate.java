package com.yuanting.n2erp.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/4/27 11:14
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_up_name)
    AppCompatEditText mName = null;
    @BindView(R2.id.edit_sign_up_address)
    AppCompatEditText mAddress = null;
    @BindView(R2.id.edit_sign_up_company_name)
    AppCompatEditText mCompanyName = null;
    @BindView(R2.id.edit_sign_up_phone)
    AppCompatEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    AppCompatEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    AppCompatEditText mRePassword = null;
    private ISignListener mISignListener = null;

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            final StringBuffer infos = new StringBuffer();
            infos.append("<Number>")
                    .append(mPhone.getText().toString())
                    .append("</Number><Address>")
                    .append(mAddress.getText().toString())
                    .append("</Address><Name>")
                    .append(mCompanyName.getText().toString())
                    .append("</Name>");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// HH:mm:ss//获取当前时间
            Date date = new Date(System.currentTimeMillis());
            RestClient.builder().url("AddUser?").loader(getContext())
                    .params("username", mName.getText().toString())
                    .params("time", simpleDateFormat.format(date))
                    .params("text", "")
                    .params("pwd", mPassword.getText().toString())
                    .params("infos", infos)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            SignHandler.onSignUp(response, mISignListener);

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Log.i("error", msg);
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }).build().get();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mISignListener = new ISignListener() {
            @Override
            public void onSignInSuccess() {

            }

            @Override
            public void onSignUpSuccess() {
                getSupportDelegate().startWithPop(new SignInDelegate());
            }
        };
    }


    @OnClick(R2.id.tv_link_sign_in)
    void linSignIn() {
        getSupportDelegate().startWithPop(new SignInDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String companyName = mCompanyName.getText().toString();
        final String address = mAddress.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError(getString(R.string.sign_up_delegate_input_name_erp));
            isPass = false;
        } else {
            mName.setError(null);
        }
        if (address.isEmpty()) {
            mAddress.setError(getString(R.string.sign_up_delegate_company_address_error_erp));
            isPass = false;
        } else {
            mAddress.setError(null);
        }
        if (companyName.isEmpty()) {
            mCompanyName.setError(getString(R.string.sign_up_delegate_company_name_error_erp));
            isPass = false;
        } else {
            mCompanyName.setError(null);
        }
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
        if (rePassword.isEmpty() || rePassword.length() < 6 || !rePassword.equals(password)) {
            mRePassword.setError(getString(R.string.sign_up_delegate_re_password_error));
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up_erp;
    }
}
