package com.yuanting.n2erp.main.index.stockData.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.main.ERPBottomDelegate;
import com.yuanting.n2erp.sign.SignInDelegate;
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
            String fiannaDate = AccountManager.getFinnalDate();
            if (!resistTime.isEmpty() && resistTime.contains("T")) {
                resistTime = resistTime.replace("T", " ");
                if (fiannaDate.contains("/"))
                    fiannaDate = fiannaDate.replace("/", "-");
                mTvRegistTime.setText(resistTime);
                Date date = simpleDateFormat.parse(resistTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                fiannaDate = fiannaDate + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
                mTvPhone.setText(fiannaDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R2.id.btn_subaccount, R2.id.btn_initialization, R2.id.btn_ch_password, R2.id.btn_contact_us})
    void btnOnClick(View view) {
        final int id = view.getId();
        final String lev = AccountManager.getLev();
        if (id == R.id.btn_subaccount) {
            if (lev.contains(UserInfoItemType.LEVER_0)) {
//            Toast.makeText(getContext(), "子用户管理", Toast.LENGTH_SHORT).show();
                getSupportDelegate().start(new SubUserDetailsDelegate());
            } else {
                Toast.makeText(getContext(), "此账号无此权限", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btn_initialization) {
//            Toast.makeText(getContext(), "注销", Toast.LENGTH_SHORT).show();
            AccountManager.setSignState(false);
            getActivity().getSupportFragmentManager().popBackStackImmediate(ERPBottomDelegate.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportDelegate().startWithPop(new SignInDelegate());
        } else if (id == R.id.btn_ch_password) {
//            Toast.makeText(getContext(), "修改密码", Toast.LENGTH_SHORT).show();
            getSupportDelegate().start(new ChangePassword());
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
