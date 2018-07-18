package com.yuanting.latte.ec.pay;

/**
 * Created on 2018/5/28 10:20
 * Created by 薛立民
 * TEL 13262933389
 */
public interface IAlPayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
