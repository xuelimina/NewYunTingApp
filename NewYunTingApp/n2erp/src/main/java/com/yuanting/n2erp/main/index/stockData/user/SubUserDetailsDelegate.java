package com.yuanting.n2erp.main.index.stockData.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/9/7 12:34
 * Created by 薛立民
 * TEL 13262933389
 */
public class SubUserDetailsDelegate extends LatteDelegate {
    @BindView(R2.id.rv_user_info)
    RecyclerView mRvUserInfo;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        GetAllUserByOwner();
    }

    @OnClick(R2.id.btn_add_sub_user)
    void btnOnClick(View view) {
        getSupportDelegate().start(new AddSubUserDelegate());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    private void GetAllUserByOwner() {
        RestClient.builder().url("GetAllUserByOwner?")
                .params("owner", AccountManager.getOwner())
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
                        final JSONArray jsonArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        if (jsonArray != null && jsonArray.size() > 0) {
                            final int size = jsonArray.size();
                            for (int i = 0; i < size; i++) {
                                final JSONObject object = jsonArray.getJSONObject(i);
                                final String UserName = object.getString("UserName");
                                final String Password = object.getString("Password");
                                final String UserInfos = object.getString("UserInfos");
                                final MultipleItemEntity entity = MultipleItemEntity.builder()
                                        .setItemType(UserInfoItemType.USER_INFO_ITEM)
                                        .setField(UserInfoItemFields.USER_NAME, UserName)
                                        .setField(UserInfoItemFields.PASSWORD, Password)
                                        .build();
                                if (UserInfos.contains("<Lev>")) {
                                    String levName = "";
                                    String type = UserInfos.substring(UserInfos.indexOf("<Lev>") + "<Lev>".length(), UserInfos.indexOf("</Lev>"));
                                    String newUserInfo = UserInfos.substring(UserInfos.indexOf("</Lev>") + "<Lev>".length(), UserInfos.length());
                                    Log.i("new ", newUserInfo);
                                    if (newUserInfo.contains("<Lev>")) {
                                        type = type + "," + newUserInfo.substring(newUserInfo.indexOf("<Lev>") + "<Lev>".length(), newUserInfo.indexOf("</Lev>"));
                                    }
                                    switch (type) {
                                        case UserInfoItemType.LEVER_0:
                                            levName = "管理员";
                                            break;
                                        case UserInfoItemType.LEVER_1:
                                            levName = "出入库管理员";
                                            break;
                                        case UserInfoItemType.LEVER_2:
                                            levName = "排车管理员";
                                            break;
                                        case UserInfoItemType.LEVER_1_AND_2:
                                            levName = "出入库排车管理员";
                                            break;
                                        default:
                                            break;

                                    }
                                    entity.setField(UserInfoItemFields.LEV, levName);
                                }
                                entities.add(entity);
                            }
                        }
                        if (mRvUserInfo != null)
                            reAdapterCh(entities);
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

    @Override
    public void onSupportVisible() {
        super.onSupportInvisible();
        GetAllUserByOwner();
    }

    private void reAdapterCh(ArrayList<MultipleItemEntity> entities) {
        mRvUserInfo.removeAllViews();
        final UserInfoAdapter adapter = new UserInfoAdapter(entities);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvUserInfo.setLayoutManager(linearLayoutManager);
        mRvUserInfo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sub_user_erp;
    }
}
