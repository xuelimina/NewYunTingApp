package com.yuanting.dpfppu.main.index.shopQuery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.RestClientBuilder;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/28 11:57
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShopQueryDelegate extends LatteDelegate implements ISuccess, IError, IFailure, ShopItemOnClick {
    @BindView(R2.id.rv_select)
    RecyclerView mRecyclerViewSelect;
    @BindView(R2.id.ry_shop)
    RecyclerView mRecyclerViewShop;
    @BindView(R2.id.linear_layout_select)
    LinearLayoutCompat mLinearLayoutCompat;
    @BindView(R2.id.tv_province_select)
    AppCompatTextView mTVProvinceSelect;
    @BindView(R2.id.tv_city_select)
    AppCompatTextView mTVCitySelect;
    @BindView(R2.id.tv_district_select)
    AppCompatTextView mTVDistrictSelect;
    private ShopDataConverter mConverter;
    public final static int PROVINCE = 0;
    public final static int CITY = 1;
    public final static int DISTRICT = 2;
    public final static int SHOP = 3;
    /**
     * 获取省
     */
    private static final String PROVINCE_URL = "Bss/QueryStoreProvince";
    /**
     * 获取市
     */
    private static final String CITY_URL = "Bss/QueryStoreCity";
    /**
     * 获取区
     */
    private static final String DISTRICT_URL = "Bss/QueryStoreCounty";
    /***
     * 获取所有门店
     */
    private static final String SHOP_LIST_URL = "Bss/QueryStoreList";
    private int isSelect = 0;
    private String mProvince = null;
    private String mCity = null;
    private String mCounty = null;
    private ArrayList<MultipleItemEntity> mData = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_query_dpf;
    }

    @Override

    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mConverter = new ShopDataConverter();
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R2.id.tv_province_select)
    void onClickProvince() {
        isSelect = PROVINCE;
        mRecyclerViewSelect.removeAllViews();
        mRecyclerViewShop.removeAllViews();
        getShopData(PROVINCE_URL, null, null, null);
        mLinearLayoutCompat.setVisibility(View.VISIBLE);
        mRecyclerViewShop.setVisibility(View.GONE);
    }

    @OnClick(R2.id.tv_city_select)
    void onClickCity() {
        if (mProvince == null || mProvince.isEmpty()) {
            Toast.makeText(getContext(), "请选择省份", Toast.LENGTH_SHORT).show();
        } else {
            isSelect = CITY;
            mRecyclerViewSelect.removeAllViews();
            mRecyclerViewShop.removeAllViews();
            getShopData(CITY_URL, mProvince, null, null);
            mLinearLayoutCompat.setVisibility(View.VISIBLE);
            mRecyclerViewShop.setVisibility(View.GONE);
        }

    }

    @OnClick(R2.id.tv_district_select)
    void onClickDistrict() {
        if (mCity == null || mCity.isEmpty()) {
            Toast.makeText(getContext(), "请选择城市", Toast.LENGTH_SHORT).show();
        } else {
            isSelect = DISTRICT;
            mRecyclerViewSelect.removeAllViews();
            mRecyclerViewShop.removeAllViews();
            getShopData(DISTRICT_URL, mProvince, mCity, null);
            mLinearLayoutCompat.setVisibility(View.VISIBLE);
            mRecyclerViewShop.setVisibility(View.GONE);
        }
    }

    @OnClick(R2.id.btn_check_shop)
    void onClickShop() {
        if (mCounty == null || mCounty.isEmpty()) {
            Toast.makeText(getContext(), "请选择区县", Toast.LENGTH_SHORT).show();
        } else {
            isSelect = SHOP;
            mRecyclerViewSelect.removeAllViews();
            mRecyclerViewShop.removeAllViews();
            getShopData(SHOP_LIST_URL, mProvince, mCity, mCounty);
            mLinearLayoutCompat.setVisibility(View.GONE);
            mRecyclerViewShop.setVisibility(View.VISIBLE);
        }
    }

    void getShopData(String url, String province, String city, String county) {
        final RestClientBuilder builder = RestClient.builder();
        builder.url(url).loader(getContext()).success(this).paramsClear();
        switch (isSelect) {
            case PROVINCE:
                break;
            case CITY:
                builder.params("Province", province);
                break;
            case DISTRICT:
                builder.params("Province", province)
                        .params("City", city);
                break;
            case SHOP:
                builder.params("Province", province)
                        .params("City", city)
                        .params("County", county);
                break;
            default:
                break;
        }
        builder.build().post();
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewSelect.setLayoutManager(layoutManager);
        final LinearLayoutManager shopContentManager = new LinearLayoutManager(getContext());
        shopContentManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShop.setLayoutManager(shopContentManager);
    }

    @Override
    public void onSuccess(String response) {
        mData.clear();
        mData.addAll(mConverter.setJsonData(response).convert());
        ShopAdapter mAdapter = new ShopAdapter(mData);
        mAdapter.setItemOnClick(this);
        switch (isSelect) {
            case PROVINCE:
            case CITY:
            case DISTRICT:
                mRecyclerViewSelect.setAdapter(mAdapter);
                break;
            case SHOP:
                mRecyclerViewShop.setAdapter(mAdapter);
                break;
            default:
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void shopContent(MultipleItemEntity entity) {
        final String text = entity.getField(MultipleFields.TEXT);
        switch (isSelect) {
            case PROVINCE:
                mProvince = text;
                mTVProvinceSelect.setText(text);
                break;
            case CITY:
                mCity = text;
                mTVCitySelect.setText(text);
                break;
            case DISTRICT:
                mCounty = text;
                mTVDistrictSelect.setText(text);
                break;
            default:
                break;
        }
        mLinearLayoutCompat.setVisibility(View.GONE);
    }

    @Override
    public void onError(int code, String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
    }
}
