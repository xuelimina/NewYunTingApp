package com.yuanting.latte.ec.main.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/21 15:37
 * Created by 薛立民
 * TEL 13262933389
 */
public class ContactUsDelegate extends BottomItemDelegate implements ISuccess, IFailure, IError {
    @BindView(R2.id.tv_contact_name)
    AppCompatEditText mNameTV;
    @BindView(R2.id.tv_contact_number)
    AppCompatEditText mNumberTV;
    @BindView(R2.id.tv_contact_email)
    AppCompatEditText mEmailTV;
    @BindView(R2.id.tv_contact_message)
    AppCompatEditText mMessageTV;
    private String mName = null;
    private String mNumber = null;
    private String mEmail = null;
    private String mMessage = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        clearTVData();
    }

    private void clearTVData() {
        mNameTV.setText("");
        mNumberTV.setText("");
        mEmailTV.setText("");
        mMessageTV.setText("");
    }

    @OnClick(R2.id.btn_contact_sub)
    void submission() {
        if (checkForm())
            submissionMessage(mName, mNumber, mEmail, mMessage);

    }

    private boolean checkForm() {
        boolean isPass = true;
        mName = mNameTV.getText().toString();
        mNumber = mNumberTV.getText().toString();
        mEmail = mEmailTV.getText().toString();
        mMessage = mMessageTV.getText().toString();
        if (mName.isEmpty()) {
            mNameTV.setError("请填写姓名");
            isPass = false;
        } else {
            mNameTV.setError(null);
        }
        if (mNumber.isEmpty() || mNumber.length() != 11) {
            mNumberTV.setError("手机号码错误");
            isPass = false;
        } else {
            mNumberTV.setError(null);
        }
        if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEmailTV.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmailTV.setError(null);
        }
        if (mMessage.isEmpty()) {
            mMessageTV.setError("请填写您的建议");
            isPass = false;
        } else {
            mMessageTV.setError(null);
        }
        return isPass;
    }

    private void submissionMessage(String contact, String contactNumber, String email, String message) {
        RestClient.builder()
                .url("Bss/SendMail")
                .params("Contact", contact)
                .params("ContactNumber", contactNumber)
                .params("Email", email)
                .params("Message", message)
                .loader(getContext())
                .success(this)
                .failure(this)
                .error(this)
                .build()
                .post();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_contact;
    }

    @Override
    public void onError(int code, String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String response) {
        clearTVData();
        Toast.makeText(getContext(), "您的意见已提交", Toast.LENGTH_SHORT).show();
    }
}
