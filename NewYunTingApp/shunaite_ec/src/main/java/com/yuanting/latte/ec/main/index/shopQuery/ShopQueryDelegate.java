package com.yuanting.latte.ec.main.index.shopQuery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.yuanting.yunting_core.delegates.LatteDelegate;
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
public class ShopQueryDelegate extends LatteDelegate implements ISuccess, IError, IFailure, ShopItemOnClick
        , AMapLocationListener, AMap.OnMapClickListener, LocationSource {
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
    @BindView(R2.id.web_gaode_map)
    MapView mapView;
    private AMap aMap;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mConverter = new ShopDataConverter();
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setOnMapClickListener(this);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
            } else {
                //   显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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
//        final WebDelegateImpl delegate = WebDelegateImpl.create("location.html");
//        delegate.setTopDelegate(getParentDelegate());
//        getSupportDelegate().loadRootFragment(R.id.web_gaode_map, delegate);
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

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {

    }
}
