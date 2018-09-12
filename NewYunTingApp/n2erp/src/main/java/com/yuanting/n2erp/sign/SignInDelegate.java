package com.yuanting.n2erp.sign;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.main.ERPBottomDelegate;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.wechat.LatteWeChat;
import com.yuanting.yunting_core.wechat.callbacks.IWeChatSignInCallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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


    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder().url("Landing?")
                    .params("username", mPhone.getText().toString())
                    .params("pwd", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.i("response", response);
                            if (response.equals("0")) {
                                Toast.makeText(getContext(), "用户名错误或者密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                SignHandler.onSignIn(response, mISignListener);
                            }

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
                    }).build().get();
        }
    }


    private boolean checkForm() {
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        boolean isPass = true;
        if (phone.isEmpty()) {
            mPhone.setError(getString(R.string.sign_up_delegate_phone_error));
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty()) {
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
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// HH:mm:ss//获取当前时间
                String fiannaDate = AccountManager.getFinnalDate();
                if (fiannaDate.contains("/"))
                    fiannaDate = fiannaDate.replace("/", "-") + " 23:59:59";
                Date date = null;
                try {
                    date = simpleDateFormat.parse(fiannaDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                Calendar fiannaCalendar = Calendar.getInstance();
                fiannaCalendar.setTime(date);
                if (calendar.after(fiannaCalendar)) {
                    AccountManager.setSignState(false);
                    new AlertDialog.Builder(getContext())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    _mActivity.finish();
                                }
                            })
                            .setCancelable(false)
                            .setMessage("账号已过期，请联系管理员续费")
                            .show();
                } else {
                    AccountManager.setSignState(true);
                    getSupportDelegate().startWithPop(new ERPBottomDelegate());
                }
            }

            @Override
            public void onSignUpSuccess() {

            }
        };
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_sign_up)
    void signUp() {
        getSupportDelegate().startWithPop(new SignUpDelegate());
    }

    @OnClick(R2.id.tv_sign_in_wechat)
    void signInWeChat() {
        LatteWeChat.getInstance().onSignSuccess(new IWeChatSignInCallBack() {
            @Override
            public void onSignInSuccess(String userInfo) {

            }
        }).signIn();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in_erp;
    }
}
