package com.yuanting.latte.ec.main.index.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuanting.latte.ec.main.index.product.content.ProductContentDelegate;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/21 17:47
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductDelegate extends LatteDelegate implements ISuccess, IFailure, ProductImageOnClick {
    /**
     * 内饰模式查品牌
     */
    private static final String INTERIOR = "Car/QueryBrandOfInterior";
    /**
     * 普通模式查品牌
     */
    private static final String EXTERNAL = "Car/QueryBrand";
    /**
     * 内饰模式查品牌
     */
    private static final String QUERY_CAR_INTERIOR = "Car/QueryCarOfInterior";
    /**
     * 普通模式查品牌
     */
    private static final String QUERY_CAR_EXTERNAL = "Car/QueryCar";
    /**
     * 内饰模式查品牌
     */
    private static final String QUERY_PACKAGE_INTERIOR = "Car/QueryPackageOfInterior";
    /**
     * 普通模式查品牌
     */
    private static final String QUERY_PACKAGE_EXTERNAL = "Car/QueryPackage";
    @BindView(R2.id.btn_interior)
    AppCompatButton mInteriorBtn = null;
    @BindView(R2.id.btn_external)
    AppCompatButton mExternalBtn = null;
    @BindView(R2.id.product_recycler_view)
    RecyclerView mProductRecyclerView = null;
    private ProductAdapter mAdapter = null;
    private boolean IsInterior = true;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mInteriorBtn.setTextColor(getResources().getColor(android.R.color.white));
        mInteriorBtn.setBackgroundResource(R.color.app_main_background);
        IsInterior = false;
        clientHelp(EXTERNAL);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void clientHelp(String url) {
        RestClient.builder()
                .url(url)
                .loader(getContext())
                .success(this)
                .build()
                .post();
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }


    @OnClick(R2.id.btn_interior)
    void interiorBtnOnClick() {
        IsInterior = true;
        mInteriorBtn.setTextColor(getResources().getColor(android.R.color.white));
        mInteriorBtn.setBackgroundResource(R.color.app_main_background);
        mExternalBtn.setTextColor(getResources().getColor(android.R.color.black));
        mExternalBtn.setBackgroundResource(R.drawable.product_button_bg);
        clientHelp(INTERIOR);
    }

    @OnClick(R2.id.btn_external)
    void externalBtnOnClick() {
        IsInterior = false;
        mInteriorBtn.setTextColor(getResources().getColor(android.R.color.black));
        mInteriorBtn.setBackgroundResource(R.drawable.product_button_bg);
        mExternalBtn.setTextColor(getResources().getColor(android.R.color.white));
        mExternalBtn.setBackgroundResource(R.color.app_main_background);
        clientHelp(EXTERNAL);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_product;
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSuccess(String response) {
        mAdapter = new ProductAdapter(new ProductDataConverter().setJsonData(response).convert());
        mAdapter.setProductImageOnClick(this);
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mProductRecyclerView.setLayoutManager(manager);
        mProductRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void startProductContent(MultipleItemEntity entity) {

        final ProductContentDelegate delegate = new ProductContentDelegate();
        final Bundle bundle = new Bundle();
        bundle.putBoolean("IsInterior", IsInterior);
        bundle.putString("QueryCar", IsInterior ? QUERY_CAR_INTERIOR : QUERY_CAR_EXTERNAL);
        bundle.putString("QueryPackage", IsInterior ? QUERY_PACKAGE_INTERIOR : QUERY_PACKAGE_EXTERNAL);
        bundle.putString(ProductItemFields.CARIMG.name(), entity.getField(ProductItemFields.CARIMG).toString());
        bundle.putString(ProductItemFields.LOGOIMG.name(), entity.getField(ProductItemFields.LOGOIMG).toString());
        bundle.putString(ProductItemFields.BRAND_CN.name(), entity.getField(ProductItemFields.BRAND_CN).toString());
        bundle.putString(ProductItemFields.BRAND.name(), entity.getField(ProductItemFields.BRAND).toString());
        delegate.setArguments(bundle);
        getSupportDelegate().start(delegate);
    }
}
