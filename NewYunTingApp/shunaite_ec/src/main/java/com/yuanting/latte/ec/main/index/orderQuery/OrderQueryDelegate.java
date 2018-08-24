package com.yuanting.latte.ec.main.index.orderQuery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.latte.ec.sign.SignInDelegate;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.app.IUserChecker;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.util.callback.CallbackManager;
import com.yuanting.yunting_core.util.callback.CallbackType;
import com.yuanting.yunting_core.util.callback.IGlobalCallback;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/28 12:00
 * Created by 薛立民
 * TEL 13262933389
 */
public class OrderQueryDelegate extends LatteDelegate implements ISuccess, IError, IFailure {
    @BindView(R2.id.edit_search_view)
    AppCompatEditText mEditView;
    private String mSearchStr;
    private boolean mIsScanner = false;
    private String mWarrantyID = "";

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String args) {
                        scanSearchOrder(args);
                        mWarrantyID = args;
                    }
                });
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R2.id.btn_scan_view)
    void scan() {
        mIsScanner = true;
        startScanWithCheck(this);
    }

    @OnClick(R2.id.btn_search_view)
    void search() {
        mIsScanner = false;
        mSearchStr = mEditView.getText().toString();
        if (!mSearchStr.isEmpty()) {
            searchOrder(mSearchStr);
        } else {
            mEditView.setError("请填写查找内容");
        }
    }

    private void searchOrder(String param) {
        RestClient.builder()
                .url("Car/QueryWarrantyInfoByQuery")
                .params("Query", param)
                .loader(getContext())
                .success(this)
                .error(this)
                .failure(this)
                .build()
                .post();
    }

    private void scanSearchOrder(String param) {
        RestClient.builder()
                .url("Car/QueryWarrantyInfo/")
                .params("AppID", 1)
                .params("ID", param)
                .loader(getContext())
                .success(this)
                .error(this)
                .failure(this)
                .build()
                .post();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_query;
    }

    @Override
    public void onError(int code, String msg) {
        if (mIsScanner) {
            Toast.makeText(getContext(), "此二维码未查询到质保单" + msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "此码未查询到质保单" + msg, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onFailure() {
        if (mIsScanner) {
            Toast.makeText(getContext(), "此二维码未查询到质保单", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "此码未查询到质保单", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(final String response) {
        Log.i("re", response);
        JSONObject object = JSONObject.parseObject(response);
        if (object != null && object.containsKey("IsSale")) {
            final boolean IsSale = object.getBoolean("IsSale");
            //TODO 检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    final JSONObject object = JSON.parseObject(response);
                    if (!AccountManager.getArea().equals(object.getString("Area"))
                            && !AccountManager.getPartnerID().equals(object.getString("Agency"))) {
                        Toast.makeText(getContext(), "运营商或地址不批配！", Toast.LENGTH_SHORT).show();
                    } else
                        startOrderDetailsDelegate(response);
                }

                @Override
                public void onNoSignIn() {
                    if (!IsSale) {
                        new AlertDialog.Builder(getContext())
                                .setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getSupportDelegate().start(new SignInDelegate());
                                    }
                                })
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setCancelable(false)
                                .setMessage("账号未登录，是否登录")
                                .show();
                    } else {
                        startOrderDetailsDelegate(response);
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "此号未查询到质保单", Toast.LENGTH_LONG).show();
        }
    }

    private void startOrderDetailsDelegate(String response) {
        final OrderDetailsDelegate delegate = new OrderDetailsDelegate();
        final Bundle bundle = new Bundle();
        bundle.putBoolean("IsScanner", mIsScanner);
        bundle.putString("WarrantyID", mWarrantyID);
        bundle.putString("response", response);
        delegate.setArguments(bundle);
        getSupportDelegate().start(delegate);
    }
}
