package com.yuanting.yunting_core.ui.refresh;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created on 2018/5/4 09:55
 * Created by 薛立民
 * TEL 13262933389
 */
public class RefreshHandler implements BaseQuickAdapter.RequestLoadMoreListener {
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter;
    private final DataConverter CONVERTER;

    private RefreshHandler(RecyclerView recyclerView, DataConverter converter) {
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
    }

    public static RefreshHandler create(RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(recyclerView, converter);
    }


    public void firstPage(String url) {
        //设置adapter
        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(""));
        RECYCLERVIEW.setAdapter(mAdapter);
    }


    @Override
    public void onLoadMoreRequested() {

    }
}
