package com.yuanting.n2erp.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
public class GroupDetailDelegate extends LatteDelegate implements PositionItemOnClick {
    @BindView(R2.id.rv_group_detail_body)
    RecyclerView mRecyclerView;
    @BindView(R2.id.tv_group_name)
    AppCompatTextView mTvGroupName;
    private final ArrayList<MultipleItemEntity> mBodyEntities = new ArrayList<>();
    private String mGroupNameStr;
    private ArrayList<String> mGroupDetailNameList = new ArrayList<>();
    private String mGroupId;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            mGroupNameStr = args.getString("name");
            mGroupId = args.getString("ID");
            GetBodysByGroup(mGroupNameStr);
            mTvGroupName.setText(mGroupNameStr + "分组");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_group_detail_erp;
    }

    @OnClick(R2.id.btn_add_group_detail)
    void onClickAddGroup() {
        final DialogEditView dialogEditView = new DialogEditView(getContext());
        dialogEditView.setCallback(new DialogEditView.Callback() {
            @Override
            public void callback(String name) {
                if (!isCheckName(name)) {
                    final StringBuilder stringBuffer = new StringBuilder();
                    mGroupDetailNameList.add(name);
                    final int size = mGroupDetailNameList.size();
                    for (int i = 0; i < size; i++) {
                        stringBuffer.append(mGroupDetailNameList.get(i));
                        if (i != size - 1) {
                            stringBuffer.append(",");
                        }
                    }
                    Log.i("mGroupDetailNameList", stringBuffer.toString());
                    final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                            .setField(MultipleFields.ITEM_TYPE, GroupItemType.GROUP_DETAIL_ITEM)
                            .build();
                    itemEntity.setField(MultipleFields.NAME, name);
                    mBodyEntities.add(itemEntity);
                    SetGroupBodys(mGroupNameStr, stringBuffer.toString());
                    dialogEditView.dismiss();
                } else {
                    Toast.makeText(getContext(), "此成员已存在，请重新添加", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialogEditView.show();
        dialogEditView.setTitle("成员名称");
    }

    @OnClick(R2.id.btn_de_group)
    void deleteGroup() {
        DeleteGroup(mGroupId);
    }

    private void DeleteGroup(String id) {
        RestClient.builder().url("DeleteGroup?").loader(getContext())
                .params("Owner", AccountManager.getOwner())
                .params("ID", id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                        if (response.equals("1")) {
                            _mActivity.onBackPressed();
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
                        Toast.makeText(getContext(), "删除数据信息失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();
    }

    private void SetGroupBodys(final String GroupName, String Body) {
        RestClient.builder().url("SetGroupBodys?").loader(getContext())
                .params("Owner", AccountManager.getOwner())
                .params("GroupName", GroupName).params("Bodys", Body)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                        if (response.equals("1")) {
                            reRecyclerData(mBodyEntities);
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

    private void GetBodysByGroup(String name) {
        mBodyEntities.clear();
        mGroupDetailNameList.clear();
        RestClient.builder().url("GetBodysByGroup?").loader(getContext())
                .params("owner", AccountManager.getOwner()).params("GroupName", name)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetBodysByGroup", JsonUtils.getDecodeJSONStr(response));
                        final String[] strings = JsonUtils.getDecodeJSONStr(response).split(",");
                        for (String Name : strings) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, GroupItemType.GROUP_DETAIL_ITEM)
                                    .build();
                            if (Name.contains("\"")) {
                                Name = Name.replace("\"", "");
                            }
                            if (!Name.isEmpty()){
                                mGroupDetailNameList.add(Name);
                                itemEntity.setField(MultipleFields.NAME, Name);
                                mBodyEntities.add(itemEntity);
                            }
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
    }

    private boolean isCheckName(String name) {
        boolean isCheck = false;
        for (String string : mGroupDetailNameList) {
            if (string.equals(name)) {
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
        mBodyEntities.remove(entity);
        mGroupDetailNameList.remove(entity.getField(MultipleFields.NAME).toString());
        final StringBuilder stringBuffer = new StringBuilder();
        final int size = mGroupDetailNameList.size();
        for (int i = 0; i < size; i++) {
            stringBuffer.append(mGroupDetailNameList.get(i));
            if (i != size - 1) {
                stringBuffer.append(",");
            }
        }
        Log.i("mGroupDetailNameList", stringBuffer.toString());
        SetGroupBodys(mGroupNameStr, stringBuffer.toString());
    }
}
