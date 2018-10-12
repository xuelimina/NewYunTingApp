package com.yuanting.n2share.sign;

import android.content.Context;

/**
 * Created on 2018/9/26 13:46
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignPresenter implements ISignModel {
    private final ISignView iSignView;
    private SignModel signModel;
    private Context context;

     SignPresenter(Context context, ISignView iSignView) {
        this.iSignView = iSignView;
        signModel = new SignModel(this);
        this.context = context;
    }

    public void signIn(String name, String password) {
        signModel.signIn(context, name, password);
    }
    public void signInResult(String result) {
    }
    public void signUp(String UserPhone, String Code, String name, String password) {
        signModel.signUp(context, UserPhone, Code, name, password);
    }

    public void signUpRegisterMsg(String UserPhone) {
        signModel.signUpSendRegisterMsg(context, UserPhone);
    }



    @Override
    public void onSuccessError(String data) {
        iSignView.showErrorMessage(data);
    }

    @Override
    public void onSuccessResult(String data) {
        iSignView.showData(data);
    }

    @Override
    public void onFailure(String msg) {
        iSignView.showFailureMessage(msg);
    }

    @Override
    public void onError(String msg) {
        iSignView.showErrorMessage(msg);
    }

}
