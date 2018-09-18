package com.yuanting.nomisdun.main.shopQuery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/7/20 15:31
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShowMapDelegate extends LatteDelegate {
    @BindView(R2.id.map_view_shop)
    MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private PoiResult poiResult; // poi返回的结果
    private PoiSearch.Query query;// Poi查询条件类
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mapView.onCreate(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }
    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener listener) {
                mListener = listener;
                if (mlocationClient == null) {
                    mlocationClient = new AMapLocationClient(getContext());
                    mLocationOption = new AMapLocationClientOption();
                    //设置定位监听
                    mlocationClient.setLocationListener(new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aMapLocation) {
                            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                                mListener.onLocationChanged(aMapLocation);//显示小蓝标
                                final LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                                final CameraUpdate up = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                        latLng, 18, 30, 30));
                                aMap.moveCamera(up);
                            } else {
                                Log.e("amapErr", "定位失败," + aMapLocation.getErrorCode());
                            }
                        }
                    });
                    //设置为高精度定位模式
                    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    //设置定位参数
                    mlocationClient.setLocationOption(mLocationOption);
                    // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                    // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                    // 在定位结束后，在合适的生命周期调用onDestroy()方法
                    // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                    mlocationClient.startLocation();
                }
            }

            @Override
            public void deactivate() {
                mListener = null;
                if (mlocationClient != null) {
                    mlocationClient.stopLocation();
                    mlocationClient.onDestroy();
                }
                mlocationClient = null;
            }
        });// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();

    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return new View(getContext());
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        final Bundle args = getArguments();
        if (args != null) {
            doSearchQuery(args.getString("address"), args.getString("city"));
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
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @OnClick(R2.id.about_back)
    void back() {
        _mActivity.onBackPressed();
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord, String city) {
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(1);// 设置每页最多返回多少条poiitem
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(query)) {// 是否是同一条
                            poiResult = result;
                            // 取得搜索到的poiitems有多少页
                            List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                            List<SuggestionCity> suggestionCities = poiResult
                                    .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                            if (poiItems != null && poiItems.size() > 0) {
                                aMap.clear();// 清理之前的图标
                                PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                                poiOverlay.removeFromMap();
                                poiOverlay.addToMap();
                                poiOverlay.zoomToSpan();
                            } else if (suggestionCities != null
                                    && suggestionCities.size() > 0) {
//                                showSuggestCity(suggestionCities);
                            } else {
//                                ToastUtil.show(PoiKeywordSearchActivity.this,
//                                        R.string.no_result);
                            }
                        }
                    } else {
//                        ToastUtil.show(PoiKeywordSearchActivity.this,
//                                R.string.no_result);
                    }
                } else {
//                    ToastUtil.showerror(PoiKeywordSearchActivity.this, rCode);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_show_shop_map;
    }
}
