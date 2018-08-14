package com.yuanting.latte.ec.main.index;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.yuanting.latte.ec.main.ShuNaiTeBottomDelegate;
import com.yuanting.latte.ec.main.index.gift.GiftDelegate;
import com.yuanting.latte.ec.main.index.orderQuery.OrderQueryDelegate;
import com.yuanting.latte.ec.main.index.picture.PictureDelegate;
import com.yuanting.latte.ec.main.index.product.ProductDelegate;
import com.yuanting.latte.ec.main.index.shopQuery.ShopQueryDelegate;
import com.yuanting.latte.ec.main.index.shopWeb.ShopWebDelegate;
import com.yuanting.latte.ec.main.shunaiteProduct.ShuNaiteProduct;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.ui.recycler.BaseDecoration;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;

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
       final IndexAdapter mAdapter = new IndexAdapter(new IndexDataConverter().convert());
        mAdapter.setMenuClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R2.id.gw_web_link)
    void webOnClick(View view) {
        final ShopWebDelegate delegate = new ShopWebDelegate();
        final Bundle bundle = new Bundle();
        final AppCompatTextView view1 = (AppCompatTextView) view;
        final String url = view1.getText().toString();
        bundle.putString("Title", "官网");
        bundle.putString("Url", url);
        delegate.setArguments(bundle);
        getParentDelegate().getSupportDelegate().start(delegate);
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            final boolean coarseLocation = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == 0;
            final boolean fineLocation = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == 0;
            if (!coarseLocation && !fineLocation) {
                locationWithCheck();
            } else {
                getParentDelegate().getSupportDelegate().start(new ShopQueryDelegate());
            }
        } else {
            getParentDelegate().getSupportDelegate().start(new ShopQueryDelegate());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0) {
            getParentDelegate().getSupportDelegate().start(new ShopQueryDelegate());
        } else if (grantResults[0] == -1) {
            Toast.makeText(getContext(),"权限获取失败，不能锁定当前位置",Toast.LENGTH_LONG).show();
            getParentDelegate().getSupportDelegate().start(new ShopQueryDelegate());
        }
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
        getParentDelegate().getSupportDelegate().start(new GiftDelegate());
    }

    @Override
    public void onlineStar() {
        new AlertDialog.Builder(getContext())
                .setTitle("请选择订购平台")
                .setPositiveButton("淘宝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ShopWebDelegate delegate = new ShopWebDelegate();
                        final Bundle bundle = new Bundle();
                        bundle.putString("Title", "淘宝");
                        bundle.putString("Url", "https://shop.m.taobao.com/shop/shop_index.htm?shop_id=254549626");
                        delegate.setArguments(bundle);
                        getParentDelegate().getSupportDelegate().start(delegate);
                    }
                }).setNegativeButton("京东", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ShopWebDelegate delegate = new ShopWebDelegate();
                final Bundle bundle = new Bundle();
                bundle.putString("Title", "京东");
                bundle.putString("Url", "https://mall.jd.com/index-759942.html");
                delegate.setArguments(bundle);
                getParentDelegate().getSupportDelegate().start(delegate);
            }
        }).create().show();
    }

    @Override
    public void orderQueryStart() {
        getParentDelegate().getSupportDelegate().start(new OrderQueryDelegate());
    }

    @Override
    public void pictureQueryStart() {
        getParentDelegate().getSupportDelegate().start(new PictureDelegate());
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        JZVideoPlayer.releaseAllVideos();
    }
}
