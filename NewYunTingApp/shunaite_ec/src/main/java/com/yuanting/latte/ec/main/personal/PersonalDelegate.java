package com.yuanting.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuanting.latte.ec.main.index.orderQuery.OrderQueryDelegate;
import com.yuanting.latte.ec.main.personal.list.ListAdapter;
import com.yuanting.latte.ec.main.personal.list.ListBean;
import com.yuanting.latte.ec.main.personal.list.ListItemType;
import com.yuanting.latte.ec.main.personal.profile.AddressDelegate;
import com.yuanting.latte.ec.main.personal.profile.QADelegate;
import com.yuanting.latte.ec.sign.SignInDelegate;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.app.IUserChecker;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/5/28 13:07
 * Created by 薛立民
 * TEL 13262933389
 */
public class PersonalDelegate extends BottomItemDelegate {
    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;
    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;
    @BindView(R2.id.btn_sign_in)
    AppCompatButton mBtnSignIn = null;
    @BindView(R2.id.layout_show_personal_data)
    LinearLayoutCompat mLayoutShowUser;
    @BindView(R2.id.tv_user_area)
    AppCompatTextView mTVUserArea;
    @BindView(R2.id.tv_user_account)
    AppCompatTextView mTVUserAccount;
    @BindView(R2.id.tv_user_partner_id)
    AppCompatTextView mTVPartnerID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                mLayoutShowUser.setVisibility(View.VISIBLE);
                mBtnSignIn.setVisibility(View.GONE);
                mTVUserAccount.setText(AccountManager.getAccount());
                mTVUserArea.setText(AccountManager.getArea());
                mTVPartnerID.setText(AccountManager.getPartnerID());
            }

            @Override
            public void onNoSignIn() {
                mLayoutShowUser.setVisibility(View.GONE);
                mBtnSignIn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean userData = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setImageValue(R.drawable.setting_user_data)
                .setDelegate(new AddressDelegate())
                .setText("我的资料")
                .build();

        final ListBean service = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setImageValue(R.drawable.setting_service)
                .setDelegate(new OrderQueryDelegate())
                .setText("质保服务")
                .build();
        final ListBean QA = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setImageValue(R.drawable.setting_q_a)
                .setDelegate(new QADelegate())
                .setText("问题问答")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(userData);
        data.add(service);
        data.add(QA);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }

    @OnClick(R2.id.btn_sign_in)
    void onSignIn() {
        getParentDelegate().getSupportDelegate().start(new SignInDelegate());
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }
}
