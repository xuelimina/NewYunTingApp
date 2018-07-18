package com.yuanting.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.yuanting.latte.ec.main.ShuNaiTeBottomDelegate;
import com.yuanting.latte.ec.main.index.orderQuery.OrderQueryDelegate;
import com.yuanting.latte.ec.main.index.product.ProductDelegate;
import com.yuanting.latte.ec.main.index.shopQuery.ShopQueryDelegate;
import com.yuanting.latte.ec.main.shunaiteProduct.ShuNaiteProduct;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.ui.recycler.BaseDecoration;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created on 2018/5/3 16:35
 * Created by 薛立民
 * TEL 13262933389
 */
public class IndexDelegate extends BottomItemDelegate implements IndexMenuClickListener {
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;
    private IndexAdapter mAdapter = null;
    private JZVideoPlayerStandard video;

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 10));
        final ShuNaiTeBottomDelegate shuNaiTeBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(shuNaiTeBottomDelegate));
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
        return R.layout.delegate_index;
    }

    @Override
    public void shopQueryStart() {
        getParentDelegate().getSupportDelegate().start(new ShopQueryDelegate());
    }

    @Override
    public void shuNaiteProductStart() {
        getParentDelegate().getSupportDelegate().start(new ShuNaiteProduct());
    }

    @Override
    public void productPriceStart() {
        getParentDelegate().getSupportDelegate().start(new ProductDelegate());
    }

    @Override
    public void giftStart() {
//        Toast.makeText(getContext(), "精彩活动", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderQueryStart() {
        getParentDelegate().getSupportDelegate().start(new OrderQueryDelegate());
    }
}