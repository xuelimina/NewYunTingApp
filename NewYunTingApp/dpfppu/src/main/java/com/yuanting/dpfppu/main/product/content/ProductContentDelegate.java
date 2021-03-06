package com.yuanting.dpfppu.main.product.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.R2;
import com.yuanting.dpfppu.main.product.ProductItemFields;
import com.yuanting.dpfppu.main.product.content.left.ProductLeftAdapter;
import com.yuanting.dpfppu.main.product.content.left.ProductLeftItemOnClick;
import com.yuanting.dpfppu.main.product.content.price.ProductPriceDelegate;
import com.yuanting.dpfppu.main.product.content.right.ProductContentDataConverter;
import com.yuanting.dpfppu.main.product.content.right.ProductRightAdapter;
import com.yuanting.dpfppu.main.product.content.right.ProductRightItemFields;
import com.yuanting.dpfppu.main.product.content.right.ProductRightItemOnClick;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/25 10:03
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductContentDelegate extends LatteDelegate implements ISuccess, IFailure, ProductRightItemOnClick, ProductLeftItemOnClick {
    @BindView(R2.id.product_title_ch)
    AppCompatTextView mProductName;
    @BindView(R2.id.product_title_en)
    AppCompatTextView mProductEName;
    @BindView(R2.id.product_title_recycler_view)
    RecyclerView mProductRightRecyclerView;
    @BindView(R2.id.product_image_view)
    AppCompatImageView mProductImageView;
    @BindView(R2.id.product_logo_image_view)
    AppCompatImageView mProductLogoImageView;
    @BindView(R2.id.product_select_title_recycler_view)
    RecyclerView mProductLeftRecyclerView;
    private String CAR_IMG = "";
    private String LOGO_IMG = "";
    private String BRAND_CN = "";
    private String BRAND = "";
    private ProductRightAdapter mRightAdapter = null;
    private ProductLeftAdapter mLeftAdapter = null;
    private ProductContentDataConverter mConverter;
    private ArrayList<MultipleItemEntity> mLeftData = new ArrayList<>();
    private String QueryPackage;
    private boolean IsInterior = false;
    private boolean IsEnd = false;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            CAR_IMG = args.getString(ProductItemFields.CARIMG.name());
            LOGO_IMG = args.getString(ProductItemFields.LOGOIMG.name());
            BRAND_CN = args.getString(ProductItemFields.BRAND_CN.name());
            BRAND = args.getString(ProductItemFields.BRAND.name());
            QueryPackage = args.getString("QueryPackage");
            getProductContent(args.getString("QueryCar"), BRAND);
            IsInterior = args.getBoolean("IsInterior");
            mProductName.setText(BRAND_CN);
            mProductEName.setText(BRAND);
            final String carImg = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + CAR_IMG + "/Nick2/";
            final String logoImg = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + LOGO_IMG + "/Nick2/";
            RequestOptions RECYCLER_OPTIONS =
                    new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .dontAnimate();
            Glide.with(getContext())
                    .load(carImg)
                    .apply(RECYCLER_OPTIONS)
                    .into(mProductImageView);
            Glide.with(getContext())
                    .load(logoImg)
                    .apply(RECYCLER_OPTIONS)
                    .into(mProductLogoImageView);
        }
    }
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_product_content_dpf;
    }

    private void getProductContent(String url, String brand) {
        RestClient.builder()
                .url(url)
                .params("Brand", brand)
                .loader(getContext())
                .success(this)
                .failure(this)
                .build()
                .post();
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSuccess(String response) {
        Log.i("resopse",response);
        mConverter = new ProductContentDataConverter();
        mConverter.setInterior(IsInterior);
        mRightAdapter = new ProductRightAdapter(mConverter.setJsonData(response).convert());
        mRightAdapter.setItemOnClick(this);
        mLeftAdapter = new ProductLeftAdapter(mLeftData);
        mLeftAdapter.setItemOnClick(this);
        final LinearLayoutManager rightManager = new LinearLayoutManager(getContext());
        rightManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProductRightRecyclerView.setLayoutManager(rightManager);
        mProductRightRecyclerView.setAdapter(mRightAdapter);
        final LinearLayoutManager leftManager = new LinearLayoutManager(getContext());
        leftManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProductLeftRecyclerView.setLayoutManager(leftManager);
        mProductLeftRecyclerView.setAdapter(mLeftAdapter);
    }

    @Override
    public void modelItemOnClick(MultipleItemEntity entity) {
        final MultipleItemEntity entity1 = getLeftData(entity);
        final ArrayList<MultipleItemEntity> entities = mConverter.getYears(entity);
        IsEnd = entity.getField(ProductRightItemFields.IS_LAST);
        if (!IsEnd) {
            mLeftAdapter.getData().add(entity1);
            mLeftAdapter.notifyDataSetChanged();
        }
        if (IsInterior && entities.size() == 0) {
            endItemOnClick(entity);
        } else {
            mRightAdapter.getData().clear();
            mRightAdapter.addData(entities);
            mRightAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void yearItemOnClick(MultipleItemEntity entity) {
        final MultipleItemEntity entity1 = getLeftData(entity);
        final ArrayList<MultipleItemEntity> entities = mConverter.getSubModels(entity);
        IsEnd = entity.getField(ProductRightItemFields.IS_LAST);
        if (!IsEnd) {
            mLeftAdapter.getData().add(entity1);
            mLeftAdapter.notifyDataSetChanged();
        }
        if (IsInterior && entities.size() == 0) {
            endItemOnClick(entity);
        } else {
            mRightAdapter.getData().clear();
            mRightAdapter.addData(entities);
            mRightAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void subModelItemOnClick(MultipleItemEntity entity) {
        final MultipleItemEntity entity1 = getLeftData(entity);
        final ArrayList<MultipleItemEntity> entities = mConverter.getSeries(entity);
        IsEnd = entity.getField(ProductRightItemFields.IS_LAST);
        if (!IsEnd) {
            mLeftAdapter.getData().add(entity1);
            mLeftAdapter.notifyDataSetChanged();
        }
        if (IsInterior && entities.size() == 0) {
            endItemOnClick(entity);
        } else {
            mRightAdapter.getData().clear();
            mRightAdapter.addData(entities);
            mRightAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void seriesItemOnClick(MultipleItemEntity entity) {
        final MultipleItemEntity entity1 = getLeftData(entity);
        final ArrayList<MultipleItemEntity> entities = mConverter.getEndList(entity);
        IsEnd = entity.getField(ProductRightItemFields.IS_LAST);
        if (!IsEnd) {
            mLeftAdapter.getData().add(entity1);
            mLeftAdapter.notifyDataSetChanged();
        }
        if (IsInterior) {
            endItemOnClick(entity);
        } else {
            mRightAdapter.getData().clear();
            mRightAdapter.addData(entities);
            mRightAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void endItemOnClick(MultipleItemEntity entity) {
        final ProductPriceDelegate delegate = new ProductPriceDelegate();
        final Bundle bundle = new Bundle();
        bundle.putString("QueryPackage", QueryPackage);
        bundle.putString(MultipleFields.ID.name(), entity.getField(MultipleFields.ID).toString());
        bundle.putString(ProductRightItemFields.MAKE.name(), entity.getField(ProductRightItemFields.MAKE).toString());
        bundle.putString(ProductRightItemFields.MODEL.name(), entity.getField(ProductRightItemFields.MODEL).toString());
        bundle.putString(ProductRightItemFields.SUB_MODEL.name(), entity.getField(ProductRightItemFields.SUB_MODEL).toString());
        bundle.putString(ProductRightItemFields.SERIES.name(), entity.getField(ProductRightItemFields.SERIES).toString());
        bundle.putString(ProductRightItemFields.YEAR.name(), entity.getField(ProductRightItemFields.YEAR).toString());
        if (!IsInterior) {
            bundle.putString(ProductRightItemFields.LAST_NAME.name(), entity.getField(ProductRightItemFields.LAST_NAME).toString());
            bundle.putString(ProductRightItemFields.LAST_MODEL.name(), entity.getField(ProductRightItemFields.LAST_MODEL).toString());
        } else {
            bundle.putString(ProductRightItemFields.LAST_NAME.name(), "");
            bundle.putString(ProductRightItemFields.LAST_MODEL.name(), "");
        }
        delegate.setArguments(bundle);
        getSupportDelegate().start(delegate);
    }

    private MultipleItemEntity getLeftData(MultipleItemEntity entity) {
        return MultipleItemEntity.builder()
                .setField(MultipleFields.ID, entity.getField(MultipleFields.ID))
                .setField(MultipleFields.ITEM_TYPE, entity.getField(MultipleFields.ITEM_TYPE))
                .setField(ProductRightItemFields.MODEL, entity.getField(ProductRightItemFields.MODEL))
                .setField(ProductRightItemFields.YEAR, entity.getField(ProductRightItemFields.YEAR))
                .setField(ProductRightItemFields.SUB_MODEL, entity.getField(ProductRightItemFields.SUB_MODEL))
                .setField(ProductRightItemFields.SERIES, entity.getField(ProductRightItemFields.SERIES))
                .setField(ProductRightItemFields.MAKE, entity.getField(ProductRightItemFields.MAKE))
                .build();
    }

    @Override
    public void leftModelItemOnClick(MultipleItemEntity entity) {
        final ArrayList<MultipleItemEntity> entities = mConverter.convert();
        mRightAdapter.getData().clear();
        mRightAdapter.addData(entities);
        mRightAdapter.notifyDataSetChanged();
        mLeftAdapter.getData().clear();
        mLeftAdapter.notifyDataSetChanged();
    }

    @Override
    public void leftYearItemOnClick(MultipleItemEntity entity) {
        final ArrayList<MultipleItemEntity> entities = mConverter.getYears(entity);
        mRightAdapter.getData().clear();
        mRightAdapter.addData(entities);
        mRightAdapter.notifyDataSetChanged();
        int size = mLeftAdapter.getData().size();
        if (size == 2) {
            mLeftAdapter.getData().remove(1);
        } else if (size > 2) {
            for (int i = 0; i < size - 1; i++) {
                mLeftAdapter.getData().remove(1);
            }
        }
        mLeftAdapter.notifyDataSetChanged();
    }

    @Override
    public void leftSubModelItemOnClick(MultipleItemEntity entity) {
        final ArrayList<MultipleItemEntity> entities = mConverter.getSubModels(entity);
        mRightAdapter.getData().clear();
        mRightAdapter.addData(entities);
        mRightAdapter.notifyDataSetChanged();
        int size = mLeftAdapter.getData().size();
        if (size == 3) {
            mLeftAdapter.getData().remove(2);
        } else if (size > 3) {
            for (int i = 0; i < size - 2; i++) {
                mLeftAdapter.getData().remove(2);
            }
        }
        mLeftAdapter.notifyDataSetChanged();
    }

    @Override
    public void leftSeriesItemOnClick(MultipleItemEntity entity) {
        final ArrayList<MultipleItemEntity> entities = mConverter.getSeries(entity);
        mRightAdapter.getData().clear();
        mRightAdapter.addData(entities);
        mRightAdapter.notifyDataSetChanged();
        int size = mLeftAdapter.getData().size();
        if (size == 4) {
            mLeftAdapter.getData().remove(3);
        } else if (size > 4) {
            for (int i = 0; i < size - 3; i++) {
                mLeftAdapter.getData().remove(3);
            }
        }
        mLeftAdapter.notifyDataSetChanged();
    }

    @Override
    public void leftEndItemOnClick(MultipleItemEntity entity) {

    }
}
