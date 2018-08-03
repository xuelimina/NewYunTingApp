package com.yuanting.dpfppu.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.R2;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import butterknife.BindView;

/**
 * Created on 2018/8/1 15:29
 * Created by 薛立民
 * TEL 13262933389
 */
public class IndexDelegate extends BottomItemDelegate implements IndexMenuClickListener {
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    private IndexAdapter mAdapter = null;

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecyclerView();
        mAdapter = new IndexAdapter(new IndexDataConverter().convert());
        mAdapter.setMenuClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_dpf;
    }

    @Override
    public void shopQueryStart() {
        Toast.makeText(getContext(),"授权网点",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void videoQueryStart() {
        Toast.makeText(getContext(),"视频案例",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderQueryStart() {
        Toast.makeText(getContext(),"质保查询",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExternal() {
        Toast.makeText(getContext(),"DPF汽车漆面保护膜",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInside() {
        Toast.makeText(getContext(),"DPF内饰保护膜",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSunlight() {
        Toast.makeText(getContext(),"太阳膜",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showColorModification() {
        Toast.makeText(getContext(),"改色膜",Toast.LENGTH_SHORT).show();
    }
}
