package com.yuanting.n2erp.main.index.stockData.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/9/6 16:49
 * Created by 薛立民
 * TEL 13262933389
 */
public class CustomerDelegate extends LatteDelegate {
    @BindView(R2.id.edit_customer_name)
    AppCompatEditText mEtCustomerName;
    @BindView(R2.id.edit_customer_address)
    AppCompatEditText mEtCustomerAddress;
    @BindView(R2.id.edit_customer_phone)
    AppCompatEditText mEtCustomerPhoneNumber;

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_add_customer)
    void onClick() {
        final String name = mEtCustomerName.getText().toString();
        final String number = mEtCustomerPhoneNumber.getText().toString();
        final String address = mEtCustomerAddress.getText().toString();
        boolean isCheck = true;
        if (name.isEmpty()) {
            mEtCustomerName.setError("请添写客户名称");
            isCheck = false;
        } else {
            mEtCustomerName.setError(null);
        }
        if (number.isEmpty()) {
            mEtCustomerPhoneNumber.setError("请添写联系方式");
            isCheck = false;
        } else {
            mEtCustomerPhoneNumber.setError(null);
        }
        if (address.isEmpty()) {
            mEtCustomerAddress.setError("请添写客户地址");
            isCheck = false;
        } else {
            mEtCustomerAddress.setError(null);
        }
        if (isCheck) {
            AddOutPt(name, address, number);
        }
    }

    private void AddOutPt(String name, String address, String phoneNumber) {
        RestClient.builder().url("AddOutPt?")
                .params("name", name).params("address", address).params("phone", phoneNumber).params("owner", AccountManager.getOwner())
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (response.equals("1")) {
                            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "添加失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_customer_erp;
    }
}
