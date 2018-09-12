package com.yuanting.n2erp.main.index.stockData.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.main.ERPBottomDelegate;
import com.yuanting.n2erp.sign.SignInDelegate;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/9/11 19:58
 * Created by 薛立民
 * TEL 13262933389
 */
public class ChangePassword extends LatteDelegate {
    @BindView(R2.id.edit_new_password)
    AppCompatEditText mTvNewPassword;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_change_password)
    void btnOnClick() {
        final String newPassword = mTvNewPassword.getText().toString();
        if (newPassword.isEmpty()) {
            mTvNewPassword.setError("请填写新的密码");
        } else {
            mTvNewPassword.setError(null);
            ChangeUserPWD(newPassword);
        }

    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    private void ChangeUserPWD(String newPassword) {
        RestClient.builder().url("ChangeUserPWD?")
                .params("id", AccountManager.getID())
                .params("pwd", newPassword)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        AccountManager.setSignState(false);
                        getActivity().getSupportFragmentManager().popBackStackImmediate(ERPBottomDelegate.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getSupportDelegate().startWithPop(new SignInDelegate());
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("msg", msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("msg", "失败");
                    }
                }).build().get();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_change_password_erp;
    }
}
