package com.yuanting.n2erp.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.n2erp.main.index.entry.PositionItemOnClick;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/9/19 15:08
 * Created by 薛立民
 * TEL 13262933389
 */
public class GroupDelegate extends LatteDelegate implements PositionItemOnClick {
    @BindView(R2.id.rv_group_body)
    RecyclerView mRecyclerView;
    private final ArrayList<MultipleItemEntity> mBodyEntities = new ArrayList<>();

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_group_erp;
    }

    @OnClick(R2.id.btn_add_group)
    void onClickAddGroup() {
        final DialogEditView dialogEditView = new DialogEditView(getContext());
        dialogEditView.setCallback(new DialogEditView.Callback() {
            @Override
            public void callback(String name) {
                if (!isCheckName(name)) {
                    AddGroup(name);
                    dialogEditView.dismiss();
                } else {
                    Toast.makeText(getContext(), "此组已存在，请重新添加", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialogEditView.show();
        dialogEditView.setTitle("小组名称");
    }

    private void AddGroup(final String name) {
        RestClient.builder().url("AddGroup?").loader(getContext())
                .params("Owner", AccountManager.getOwner())
                .params("name", name).params("Bodys", "").params("Info", "0")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                        if (response.equals("1")) {
                            GetAllGroup();
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
                        Toast.makeText(getContext(), "获取数据信息失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();
    }

    private void GetAllGroup() {
        mBodyEntities.clear();
        RestClient.builder().url("GetAllGroup?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetAllGroup", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray dataArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, GroupItemType.GROUP_ITEM)
                                    .build();
                            final JSONObject object = dataArray.getJSONObject(i);
                            final String Id = object.getString("Id");
                            final String Name = object.getString("Name");
                            final String Bodys = object.getString("Bodys");
                            final String Info = object.getString("Info");
                            itemEntity.setField(MultipleFields.ID, Id);
                            itemEntity.setField(MultipleFields.NAME, Name);
                            itemEntity.setField(GroupItemFields.BODYS, Bodys);
                            itemEntity.setField(GroupItemFields.INFO, Info);
                            mBodyEntities.add(itemEntity);
                        }
                        reRecyclerData(mBodyEntities);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("onError", msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("onFailure", "onFailure");
                    }
                }).build().get();

    }
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        GetAllGroup();
    }

    private boolean isCheckName(String name) {
        boolean isCheck = false;
        for (MultipleItemEntity entity : mBodyEntities) {
            if (entity.getField(MultipleFields.NAME).equals(name)) {
                isCheck = true;
                break;
            }
        }
        return isCheck;

    }

    private void reRecyclerData(ArrayList<MultipleItemEntity> entities) {
        mRecyclerView.removeAllViews();
        final GroupAdapter mAdapter = new GroupAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void positionItemOnClick(MultipleItemEntity entity) {
        final Bundle bundle = new Bundle();
        bundle.putString("name", entity.getField(MultipleFields.NAME).toString());
        bundle.putString("ID", entity.getField(MultipleFields.ID).toString());
        final GroupDetailDelegate delegate = new GroupDetailDelegate();
        delegate.setArguments(bundle);
        getSupportDelegate().start(delegate);
    }
}
