package com.yuanting.n2erp.main.index.stockData.partner;

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
 * Created on 2018/9/6 13:00
 * Created by 薛立民
 * TEL 13262933389
 */
public class PartnerDelegate extends LatteDelegate {
    @BindView(R2.id.edit_partner_name)
    AppCompatEditText mEtPartnerName;
    @BindView(R2.id.edit_partner_address)
    AppCompatEditText mEtPartnerAddress;
    @BindView(R2.id.edit_partner_phone)
    AppCompatEditText mEtPartnerPhoneNumber;

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_add_partner)
    void onClick() {
        final String name = mEtPartnerName.getText().toString();
        final String number = mEtPartnerPhoneNumber.getText().toString();
        final String address = mEtPartnerAddress.getText().toString();
        boolean isCheck = true;
        if (name.isEmpty()) {
            mEtPartnerName.setError("请添写门店名称");
            isCheck = false;
        } else {
            mEtPartnerName.setError(null);
        }
        if (number.isEmpty() ) {
            mEtPartnerPhoneNumber.setError("请添写联系方式");
            isCheck = false;
        } else {
            mEtPartnerPhoneNumber.setError(null);
        }
        if (address.isEmpty()) {
            mEtPartnerAddress.setError("请添写门店地址");
            isCheck = false;
        } else {
            mEtPartnerAddress.setError(null);
        }
        if (isCheck) {
            AddPartner(name, address, number);
        }
    }

    private void AddPartner(String name, String address, String phoneNumber) {
        RestClient.builder().url("AddPartner?")
                .params("Name", name).params("Address", address).params("PhoneNumber", phoneNumber).params("Owner", AccountManager.getOwner())
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
        return R.layout.delegate_partner_erp;
    }
}
