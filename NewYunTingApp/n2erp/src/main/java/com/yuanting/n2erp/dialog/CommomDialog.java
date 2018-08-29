package com.yuanting.n2erp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yuanting.yunting_core.R;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created on 2018/8/29 17:49
 * Created by 薛立民
 * TEL 13262933389
 */
public class CommomDialog extends Dialog {
    private MultipleRecyclerAdapter mAdapter;
    private AppCompatTextView mTitle;

    public CommomDialog(@NonNull Context context) {
        super(context);
    }

    public CommomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public CommomDialog(Context context, int themeResId, MultipleRecyclerAdapter adapter) {
        super(context, themeResId);
        this.mAdapter = adapter;
    }

    protected CommomDialog(@NonNull Context context, boolean cancelable, @NonNull OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CommomDialog setTitle(String title) {
        mTitle.setText(title);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(true);
        mTitle = findViewById(R.id.title);
        RecyclerView mRecyclerView = findViewById(R.id.content);
        mRecyclerView.removeAllViews();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
