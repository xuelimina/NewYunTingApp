package com.yuanting.n2share.sign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.yuanting.n2share.R;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.util.timer.BaserTimerTask;
import com.yuanting.yunting_core.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created on 2018/9/17 13:53
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignUpDelegate extends LatteDelegate implements ISignView, ITimerListener {
    private SignPresenter mSignPresenter;
    @BindView(R.id.btn_register_msg)
    AppCompatButton mBtnRegisterMsg;
    @BindView(R.id.et_sign_up_phone)
    AppCompatEditText mEtPhone;
    @BindView(R.id.et_sign_up_password)
    AppCompatEditText mEtPassword;
    @BindView(R.id.et_register_msg)
    AppCompatEditText mEtRegisterMsg;
    @BindView(R.id.et_sign_up_user_name)
    AppCompatEditText mEtUserName;
    @BindView(R.id.tv_sign_up_error)
    AppCompatTextView mTvError;
    @BindView(R.id.btn_sign_up)
    AppCompatButton mBtnSignUp;
    @BindView(R.id.icon_delete)
    IconTextView mIconDelete;
    private String mPhone;
    private String mPassword;
    private String mCode;
    private String mUserName;
    private int mCount = 60;
    private Timer mTimer = null;
    private boolean isSignUp = false;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mSignPresenter = new SignPresenter(getContext(), this);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @OnTextChanged(R.id.et_sign_up_phone)
    void textPhoneChange(CharSequence charSequence) {
        if (charSequence.length() < 11) {
            mBtnSignUp.setBackgroundResource(R.color.colorBtnEnabledFalse);
            mBtnSignUp.setEnabled(false);
            mBtnSignUp.setTextColor(getResources().getColor(R.color.colorBtnTextFalse));
            mIconDelete.setVisibility(View.GONE);
            mBtnRegisterMsg.setEnabled(false);
        } else {
            mBtnSignUp.setBackgroundResource(R.color.colorBtnEnabledTrue);
            mBtnSignUp.setEnabled(true);
            mBtnRegisterMsg.setEnabled(true);
            mBtnSignUp.setTextColor(Color.WHITE);
            mIconDelete.setVisibility(View.VISIBLE);
            mBtnRegisterMsg.setTextColor(Color.BLACK);
        }
    }

    @OnClick(R.id.icon_delete)
    void iconDelete() {
        mEtPhone.setText("");
        mBtnRegisterMsg.setTextColor(getResources().getColor(R.color.colorBtnEnabledFalse));
        mBtnRegisterMsg.setEnabled(false);
    }

    @OnClick(R.id.btn_register_msg)
    void btnRegisterMsg() {
        isSignUp = false;
        mPhone = mEtPhone.getText().toString();
        if (mPhone.isEmpty() || mPhone.length() < 11) {
            mEtPhone.setError("请填写正确的手机号");
        } else {
            mEtPhone.setError(null);
            mTimer = new Timer();
            final BaserTimerTask task = new BaserTimerTask(this);
            mTimer.schedule(task, 0, 1000);
            mSignPresenter.signUpRegisterMsg(mPhone);
        }

    }

    @OnClick(R.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R.id.btn_sign_up)
    void btnSignUp() {
        if (isCheck()) {
            isSignUp = false;
            mSignPresenter.signUp(mPhone, mCode, mUserName, mPassword);
        }

    }

    private boolean isCheck() {
        boolean isCheck = true;
        mPhone = mEtPhone.getText().toString();
        mCode = mEtRegisterMsg.getText().toString();
        mUserName = mEtUserName.getText().toString();
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
        if (mCode.isEmpty()) {
            mEtRegisterMsg.setError("请填写验证码");
            isCheck = false;
        } else {
            mEtRegisterMsg.setError(null);
        }
        if (mUserName.isEmpty()) {
            mEtUserName.setError("请填写昵称");
            isCheck = false;
        } else {
            mEtUserName.setError(null);
        }
        return isCheck;
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBtnRegisterMsg != null) {
                    mBtnRegisterMsg.setText(MessageFormat.format("{0}s重新获取", mCount));
                    mBtnRegisterMsg.setTextColor(getResources().getColor(R.color.colorBtnEnabledFalse));
                    mBtnRegisterMsg.setEnabled(false);
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            mCount = 60;
                            mBtnRegisterMsg.setText("重新获取");
                            mBtnRegisterMsg.setEnabled(true);
                            mBtnRegisterMsg.setTextColor(Color.BLACK);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData(String data) {
        if (isSignUp) {
            _mActivity.onBackPressed();
        }
//        else {
//            mTvError.setText(data);
//        }

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
