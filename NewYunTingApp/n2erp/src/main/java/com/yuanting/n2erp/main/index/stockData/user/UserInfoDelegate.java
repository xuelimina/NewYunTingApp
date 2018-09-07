package com.yuanting.n2erp.main.index.stockData.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/9/7 12:34
 * Created by 薛立民
 * TEL 13262933389
 */
public class UserInfoDelegate extends LatteDelegate {
    @BindView(R2.id.tv_company_name)
    AppCompatTextView mTvCompanyName;
    @BindView(R2.id.tv_user_info_regist_time)
    AppCompatTextView mTvRegistTime;
    @BindView(R2.id.tv_user_info_phone)
    AppCompatTextView mTvPhone;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// HH:mm:ss//获取当前时间

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        try {
            mTvCompanyName.setText(AccountManager.getCompanyName().isEmpty() ? "无单位信息" : AccountManager.getCompanyName());
            String resistTime = AccountManager.getRegistTime();
            if (!resistTime.isEmpty() && resistTime.contains("T")) {
                resistTime = resistTime.replace("T", " ");
                mTvRegistTime.setText(resistTime);
                Date date = simpleDateFormat.parse(resistTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 30);
                String number = simpleDateFormat.format(calendar.getTime());
                mTvPhone.setText(number);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R2.id.btn_subaccount, R2.id.btn_initialization, R2.id.btn_ch_password, R2.id.btn_re_password, R2.id.btn_contact_us})
    void btnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_subaccount) {
            Toast.makeText(getContext(), "子用户管理", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_initialization) {
            Toast.makeText(getContext(), "初始化数据", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_ch_password) {
            Toast.makeText(getContext(), "修改密码", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_re_password) {
            Toast.makeText(getContext(), "找回密码", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_contact_us) {
            Toast.makeText(getContext(), "遇到问题？联系客服", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_info_erp;
    }
}
