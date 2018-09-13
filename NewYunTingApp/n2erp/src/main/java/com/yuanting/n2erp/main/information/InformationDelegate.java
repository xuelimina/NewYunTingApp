package com.yuanting.n2erp.main.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created on 2018/8/22 17:39
 * Created by 薛立民
 * TEL 13262933389
 */
public class InformationDelegate extends BottomItemDelegate {
    @BindView(R2.id.rv_system_news)
    RecyclerView mRvSystemNews;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        GetSystemNewsByMaxID(AccountManager.getReadId().isEmpty() ? "0" : AccountManager.getReadId());
    }

    private void GetSystemNewsByMaxID(final String id) {
        RestClient.builder().url("GetSystemNewsByMaxID")
                .params("id", id)
                .loader(getContext())
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("error", msg);
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", JsonUtils.getDecodeJSONStr(response));
                        int max = Integer.valueOf(id);
                        final JSONArray jsonArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        final int size = jsonArray.size();
                        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            final int Id = jsonArray.getJSONObject(i).getInteger("Id");
                            if (Id > max) {
                                max = Id;
                                final String SystenNews = jsonArray.getJSONObject(i).getString("SystenNews");
                                final String Time = jsonArray.getJSONObject(i).getString("Time");
                                final MultipleItemEntity entity = MultipleItemEntity.builder().setItemType(InformationAdapter.INFORMATION_TYPE)
                                        .setField(MultipleFields.TITLE, SystenNews).setField(MultipleFields.TEXT, Time).build();
                                entities.add(entity);
                            }
                        }
                        AccountManager.setReadId(String.valueOf(max >= 3 ? max - 3 : 0));
                        reRecyclerCalendarData(entities);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("response", "onFailure");
                    }
                }).build().get();
    }

    private void reRecyclerCalendarData(ArrayList<MultipleItemEntity> entities) {
        mRvSystemNews.removeAllViews();
        final InformationAdapter adapter = new InformationAdapter(entities);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSystemNews.setLayoutManager(manager);
        mRvSystemNews.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_information_erp;
    }
}
