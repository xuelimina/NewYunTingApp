package com.yuanting.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.util.log.LatteLogger;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/4/27 11:14
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;
    private ISignListener mISignListener = null;

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
//            RestClient.builder().url("")
//                    .params("name", mName.getText().toString())
//                    .params("email", mEmail.getText().toString())
//                    .params("phone", mPhone.getText().toString())
//                    .params("password", mPassword.getText().toString())
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            String userData = "{ \"code\" : 0,\"message\":\"ok\"," +
//                                    " \"data\": {" +
//                                    "\"seerId\":\"1\"," +
//                                    "\"name\":\"张三\"," +
//                                    "\"avatar\":\"1\"," +
//                                    "\"gender\":\"男\"," +
//                                    "\"address\":\"张江高科\",}} ";
//                            LatteLogger.json("user_profile",userData);
//                            SignHandler.onSignUp(userData);
//
//                        }
//                    }).build().post();
            String userData = "{ \"code\" : 0,\"message\":\"ok\"," +
                    " \"data\": {" +
                    "\"userId\":\"2\"," +
                    "\"name\":\"张三\"," +
                    "\"avatar\":\"1\"," +
                    "\"gender\":\"男\"," +
                    "\"address\":\"张江高科\",}} ";
            LatteLogger.json("user_profile", userData);
            SignHandler.onSignUp(userData, mISignListener);
//            Toast.makeText(getContext(), getString(R.string.sign_up_delegate_success), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    private void setTestString() {
        mName.setText("张三");
        mEmail.setText("123456@qq.com");
        mPhone.setText("11111111111");
        mPassword.setText("123456");
        mRePassword.setText("123456");
    }

    @OnClick(R2.id.tv_link_sign_in)
    void linSignIn() {
        getSupportDelegate().start(new SignInDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError(getString(R.string.sign_up_delegate_input_name));
            isPass = false;
        } else {
            mName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getString(R.string.sign_up_delegate_email_error));
            isPass = false;
        } else {
            mEmail.setError(null);
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
        setTestString();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }
}
