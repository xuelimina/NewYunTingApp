package com.yuanting.latte.ec.main.index.shopQuery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.delegates.web.WebDelegateImpl;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.RestClientBuilder;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

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
    @BindView(R2.id.linear_layout_select)
    LinearLayoutCompat mLinearLayoutCompat;
    @BindView(R2.id.tv_select_title)
    AppCompatTextView mTVSelectTitle;
    @BindView(R2.id.tv_province_select)
    AppCompatTextView mTVProvinceSelect;
    @BindView(R2.id.tv_city_select)
    AppCompatTextView mTVCitySelect;
    @BindView(R2.id.tv_district_select)
    AppCompatTextView mTVDistrictSelect;
    @BindView(R2.id.tv_shop_select)
    AppCompatTextView mTVShopSelect;
    @BindView(R2.id.tv_shop)
    AppCompatTextView mTVShop;
    @BindView(R2.id.tv_shop_address)
    AppCompatTextView mTVShopAddress;
    private ShopDataConverter mConverter;
    private final static int PROVINCE = 0;
    private final static int CITY = 1;
    private final static int DISTRICT = 2;
    private final static int SHOP = 3;
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
        return R.layout.delegate_shop_query;
    }

    @Override

    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mConverter = new ShopDataConverter();
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R2.id.relayout_province)
    void onClickProvince() {
        isSelect = PROVINCE;
        mRecyclerViewSelect.removeAllViews();
        getShopData(PROVINCE_URL, null, null, null);
        mTVSelectTitle.setText("请选择省份");
        mLinearLayoutCompat.setVisibility(View.VISIBLE);

    }

    @OnClick(R2.id.relayout_city)
    void onClickCity() {
        if (mProvince == null || mProvince.isEmpty()) {
            Toast.makeText(getContext(), "请选择省份", Toast.LENGTH_SHORT).show();
        } else {
            isSelect = CITY;
            mRecyclerViewSelect.removeAllViews();
            getShopData(CITY_URL, mProvince, null, null);
            mTVSelectTitle.setText("请选择城市");
            mLinearLayoutCompat.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R2.id.relayout_district)
    void onClickDistrict() {
        if (mCity == null || mCity.isEmpty()) {
            Toast.makeText(getContext(), "请选择城市", Toast.LENGTH_SHORT).show();
        } else {
            isSelect = DISTRICT;
            mRecyclerViewSelect.removeAllViews();
            getShopData(DISTRICT_URL, mProvince, mCity, null);
            mTVSelectTitle.setText("请选择区县");
            mLinearLayoutCompat.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R2.id.relayout_shop)
    void onClickShop() {
        if (mCounty == null || mCounty.isEmpty()) {
            Toast.makeText(getContext(), "请选择区县", Toast.LENGTH_SHORT).show();
        } else {
            isSelect = SHOP;
            mRecyclerViewSelect.removeAllViews();
            getShopData(SHOP_LIST_URL, mProvince, mCity, mCounty);
            mTVSelectTitle.setText("请选择门店");
            mLinearLayoutCompat.setVisibility(View.VISIBLE);
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
        final WebDelegateImpl delegate = WebDelegateImpl.create("location.html");
        delegate.setTopDelegate(getParentDelegate());
        getSupportDelegate().loadRootFragment(R.id.web_gaode_map, delegate);
    }

    @Override
    public void onSuccess(String response) {
        mData.clear();
        mData.addAll(mConverter.setJsonData(response).convert());
        ShopAdapter mAdapter = new ShopAdapter(mData);
        mAdapter.setItemOnClick(this);
        mRecyclerViewSelect.setAdapter(mAdapter);
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
            case SHOP:
                mTVShop.setText(text);
                mTVShopSelect.setText(text);
                if (entity.getField(ShopItemFields.ADDRESS) != null) {
                    mTVShopAddress.setText("联系地址:" + entity.getField(ShopItemFields.ADDRESS));
                }
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
