package com.yuanting.n2erp.main.index.stockData.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/9/7 12:34
 * Created by 薛立民
 * TEL 13262933389
 */
public class AddSubUserDelegate extends LatteDelegate implements UserLevItemOnClick {
    @BindView(R2.id.edit_add_user_name)
    AppCompatEditText mTvName;
    @BindView(R2.id.edit_add_user_password)
    AppCompatEditText mTvPassword;
    @BindView(R2.id.edit_add_user_re_password)
    AppCompatEditText mTvRePassword;
    @BindView(R2.id.edit_add_user_level)
    AppCompatTextView mTvAddUserLevel;
    @BindView(R2.id.linear_layout_select)
    LinearLayoutCompat mSelectLayout;
    @BindView(R2.id.tv_select_title)
    AppCompatTextView mTvSelectTitle;
    @BindView(R2.id.rv_select)
    RecyclerView mRvSelect;
    private String name = "";
    private String password = "";
    private String rePassword = "";
    private String lev = "";
    private String lever = "";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// HH:mm:ss//获取当前时间

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final UserInfoAdapter adapter = new UserInfoAdapter(initAdapterData());
        adapter.setItemOnClick(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(manager);
        mRvSelect.setAdapter(adapter);
    }

    private ArrayList<MultipleItemEntity> initAdapterData() {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(UserInfoItemType.USER_INFO_LEV_NAME_ITEM)
                    .build();
            String Name = "";
            String lev = "";
            switch (i) {
                case 0:
                    Name = "全部";
                    lev = UserInfoItemType.LEVER_1_AND_2;
                    break;
                case 1:
                    Name = "仓库管理";
                    lev = UserInfoItemType.LEVER_1;
                    break;
                case 2:
                    Name = "排车管理";
                    lev = UserInfoItemType.LEVER_2;
                    break;
                default:
                    break;

            }
            entity.setField(MultipleFields.NAME, Name);
            entity.setField(UserInfoItemFields.LEV, lev);
            entities.add(entity);
        }

        return entities;
    }

    @OnClick({R2.id.btn_add_user, R2.id.edit_add_user_level, R2.id.rv_select_cancel})
    void btnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_add_user) {
            if (isCheck()) {
                final Date date = new Date(System.currentTimeMillis());
                final String time = simpleDateFormat.format(date);
                AddSubUser(name, rePassword, time, "", lever);
            }
//            Toast.makeText(getContext(), "添加子账户", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.edit_add_user_level) {
            mTvSelectTitle.setText("品类");
            mSelectLayout.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "等级选择", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.rv_select_cancel) {
            mSelectLayout.setVisibility(View.GONE);
        }

    }

    private boolean isCheck() {
        boolean isCheck = true;
        name = mTvName.getText().toString();
        password = mTvPassword.getText().toString();
        rePassword = mTvRePassword.getText().toString();
        lev = mTvAddUserLevel.getText().toString();
        if (name.isEmpty()) {
            mTvName.setError("请填写用户名");
            isCheck = false;
        } else {
            mTvName.setError(null);
        }
        if (password.isEmpty()) {
            mTvPassword.setError("请填写密码");
            isCheck = false;
        } else {
            mTvPassword.setError(null);
        }
        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            mTvRePassword.setError("密码不一样请重新输入");
            isCheck = false;
        } else {
            mTvRePassword.setError(null);
        }
        if (lever.isEmpty()) {
            Toast.makeText(getContext(), "请选择权限", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }

    private void AddSubUser(String name, String password, String time, String text, String lev) {
        RestClient.builder().url("AddSubUser?")
                .params("username", name)
                .params("pwd", password)
                .params("time", time)
                .params("text", text)
                .params("owner", AccountManager.getOwner())
                .params("Lev", lev)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                        if (response.contains("注册成功")) {
                            _mActivity.onBackPressed();
                        }
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
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

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_add_sub_user_erp;
    }

    @Override
    public void userLevItemOnClick(MultipleItemEntity entity) {
        final String name = entity.getField(MultipleFields.NAME);
        final String lev = entity.getField(UserInfoItemFields.LEV);
        mTvAddUserLevel.setText(name);
        lever = lev;
        mSelectLayout.setVisibility(View.GONE);
    }
}
