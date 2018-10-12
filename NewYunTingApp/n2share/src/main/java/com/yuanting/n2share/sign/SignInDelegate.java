package com.yuanting.n2share.sign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.yuanting.n2share.R;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * Created on 2018/9/17 13:53
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignInDelegate extends LatteDelegate implements ISignView {
    private SignPresenter mSignPresenter;
    @BindView(R.id.et_phone)
    AppCompatEditText mEtPhone;
    @BindView(R.id.et_password)
    AppCompatEditText mEtPassword;
    @BindView(R.id.btn_sign_in)
    AppCompatButton mBtnSignIn;
    @BindView(R.id.icon_delete)
    IconTextView mIconDelete;
    @BindView(R.id.icon_eye)
    IconTextView mIconEye;
    @BindView(R.id.tv_sign_in_error)
    AppCompatTextView mTvError;
    private String mPhone;
    private String mPassword;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSignPresenter = new SignPresenter(getContext(), this);
        mBtnSignIn.setEnabled(false);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @OnClick(R.id.btn_sign_in)
    void btnSignIn() {
        if (isCheck())
            mSignPresenter.signIn(mPhone, mPassword);
    }

    @OnClick(R.id.btn_go_sign_up)
    void btnGoSignUp() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    @OnClick(R.id.icon_delete)
    void iconDelete() {
        mEtPhone.setText("");
//        mIconDelete.setVisibility(View.GONE);
    }

    @OnTouch(R.id.icon_eye)
    boolean onTouchIconEye(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                mIconEye.setTextColor(Color.BLACK);
                break;
            case MotionEvent.ACTION_UP:
                mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                mIconEye.setTextColor(getResources().getColor(R.color.colorBtnEnabledFalse));
                break;
            default:
                break;
        }
        return true;
    }

    @OnTextChanged(R.id.et_phone)
    void textPhoneChange(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            mBtnSignIn.setBackgroundResource(R.color.colorBtnEnabledFalse);
            mBtnSignIn.setEnabled(false);
            mBtnSignIn.setTextColor(getResources().getColor(R.color.colorBtnTextFalse));
            mIconDelete.setVisibility(View.GONE);
        } else {
            mBtnSignIn.setBackgroundResource(R.color.colorBtnEnabledTrue);
            mBtnSignIn.setEnabled(true);
            mBtnSignIn.setTextColor(Color.WHITE);
            mIconDelete.setVisibility(View.VISIBLE);
        }
    }

    @OnTextChanged(R.id.et_password)
    void textPasswordChange(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            mIconEye.setVisibility(View.GONE);
        } else {
            mIconEye.setVisibility(View.VISIBLE);
        }
    }

    private boolean isCheck() {
        boolean isCheck = true;
        mPhone = mEtPhone.getText().toString();
        mPassword = mEtPassword.getText().toString();
        if (mPhone.isEmpty() || mPhone.length() < 11) {
            mEtPhone.setError("请填写正确的手机号");
            isCheck = false;
        } else {
            mEtPhone.setError(null);
        }
        if (mPassword.isEmpty() || mPassword.length() < 6) {
            mEtPassword.setError("密码不能小于6位");
            isCheck = false;
        } else {
            mEtPassword.setError(null);
        }
        return isCheck;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData(String data) {
    }

    @Override
    public void showFailureMessage(String msg) {
        mTvError.setText(msg);
    }

    @Override
    public void showErrorMessage(String msg) {
        mTvError.setText(msg);
    }
}
