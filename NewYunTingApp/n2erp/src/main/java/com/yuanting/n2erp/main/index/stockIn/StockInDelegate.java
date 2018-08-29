package com.yuanting.n2erp.main.index.stockIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/23 13:08
 * Created by 薛立民
 * TEL 13262933389
 */
public class StockInDelegate extends LatteDelegate implements ProductItemOnClick {
    @BindView(R2.id.category_layout)
    RelativeLayout mCategoryLayout;
    @BindView(R2.id.brand_layout)
    RelativeLayout mBrandLayout;
    @BindView(R2.id.model_layout)
    RelativeLayout mModelLayout;
    @BindView(R2.id.product_name_layout)
    LinearLayoutCompat mProductNameLayout;
    @BindView(R2.id.et_category)
    AppCompatEditText mEtCategory;
    @BindView(R2.id.et_brand)
    AppCompatEditText mEtBrand;
    @BindView(R2.id.et_model)
    AppCompatEditText mEtModel;
    @BindView(R2.id.et_product_name)
    AppCompatEditText mEtProductName;
    @BindView(R2.id.et_standard)
    AppCompatEditText mEtStandard;
    @BindView(R2.id.et_count)
    AppCompatEditText mEtCount;
    @BindView(R2.id.et_info)
    AppCompatEditText mEtInfo;
    @BindView(R2.id.tv_select_category_name)
    AppCompatTextView mTvSelectCategory;
    @BindView(R2.id.tv_select_brand_name)
    AppCompatTextView mTvSelectBrand;
    @BindView(R2.id.tv_select_model_name)
    AppCompatTextView mTvSelectModel;
    @BindView(R2.id.tv_select_product_name)
    AppCompatTextView mTvSelectProductName;
    @BindView(R2.id.tv_unit)
    AppCompatTextView mTvSelectUnit;
    @BindView(R2.id.rv_select)
    RecyclerView mRvSelect;
    @BindView(R2.id.linear_layout_select)
    LinearLayoutCompat mSelectLayout;
    @BindView(R2.id.tv_select_title)
    AppCompatTextView mTvSelectTitle;
    private String parent = "";
    private String mCategoryNameStr = "";
    private String mBrandNameStr = "";
    private String mModelNameStr = "";
    private String mProductNameStr = "";
    private String mCountStr = "";
    private String mUnitStr = "";
    private String mInfoStr = "";
    private ProductDataConverter mConverter = new ProductDataConverter();
    private MultipleItemEntity mCurrentCategoryEntity;
    private MultipleItemEntity mCurrentBrandEntity;
    private MultipleItemEntity mCurrentModelEntity;
    private MultipleItemEntity mCurrentProductNameEntity;
    private ProductAdapter mAdapter;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        GetProductInfo("-1", "-1");
        reRecyclerData(null, ProductIdx.UNIT);
        mSelectLayout.setVisibility(View.GONE);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_stock_in_erp;
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick({R2.id.select_category_layout, R2.id.select_brand_layout, R2.id.select_model_layout, R2.id.select_product_name_layout, R2.id.select_unit_layout})
    void selectOnClick(View view) {
        mRvSelect.removeAllViews();
        final int id = view.getId();
        if (id == R.id.select_category_layout) {
            mTvSelectTitle.setText("品类");
            reRecyclerData(mCurrentCategoryEntity, ProductIdx.CATEGORY_IDX);
        } else if (id == R.id.select_brand_layout) {
            mTvSelectTitle.setText("品牌");
            reRecyclerData(mCurrentCategoryEntity, ProductIdx.BRAND_IDX);
        } else if (id == R.id.select_model_layout) {
            mTvSelectTitle.setText("型号");
            reRecyclerData(mCurrentBrandEntity, ProductIdx.MODEL_IDX);
        } else if (id == R.id.select_product_name_layout) {
            mTvSelectTitle.setText("名称");
            reRecyclerData(mCurrentModelEntity, ProductIdx.PRODUCT_NAME_IDX);
        } else if (id == R.id.select_unit_layout) {
            mTvSelectTitle.setText("单位");
            reRecyclerData(null, ProductIdx.UNIT);
        }
        mSelectLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R2.id.btn_add_category, R2.id.btn_add_brand, R2.id.btn_add_model, R2.id.btn_add_product_name})
    void addBtnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_add_category) {
            mCategoryLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_add_brand) {
            mBrandLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_add_model) {
            mModelLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_add_product_name) {
            mProductNameLayout.setVisibility(View.VISIBLE);
        }
        mSelectLayout.setVisibility(View.GONE);
    }

    @OnClick({R2.id.btn_add_category_cancel, R2.id.btn_add_brand_cancel, R2.id.btn_add_model_cancel, R2.id.btn_add_product_name_cancel, R2.id.rv_select_cancel})
    void cancelBtnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_add_category_cancel) {
            mCategoryLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_brand_cancel) {
            mBrandLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_model_cancel) {
            mModelLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_product_name_cancel) {
            mProductNameLayout.setVisibility(View.GONE);
        } else if (id == R.id.rv_select_cancel) {
            mSelectLayout.setVisibility(View.GONE);
        }
        mSelectLayout.setVisibility(View.GONE);
    }

    @OnClick({R2.id.btn_add_category_ok, R2.id.btn_add_brand_ok, R2.id.btn_add_model_ok, R2.id.btn_add_product_name_ok, R2.id.btn_entry_material})
    void okBtnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_add_category_ok) {
            final String categoryStr = mEtCategory.getText().toString();
            if (categoryStr.isEmpty()) {
                mEtCategory.setError("请填写添加的品类");
            } else {
                mEtCategory.setError(null);
                addMaterialMode(ProductIdx.CATEGORY_IDX, "0", "0", categoryStr);
            }
        } else if (id == R.id.btn_add_brand_ok) {
            final String brandStr = mEtBrand.getText().toString();
            final String selectCategoryStr = mTvSelectCategory.getText().toString();
            if (selectCategoryStr.isEmpty()) {
                Toast.makeText(getContext(), "请先选择品类", Toast.LENGTH_LONG).show();
                return;
            }
            if (brandStr.isEmpty()) {
                mEtBrand.setError("请填写添加的品牌");
            } else {
                mEtBrand.setError(null);
                parent = mCurrentCategoryEntity.getField(MultipleFields.ID);
                addMaterialMode(ProductIdx.BRAND_IDX, parent, "0", brandStr);
            }
        } else if (id == R.id.btn_add_model_ok) {
            final String modelStr = mEtModel.getText().toString();
            final String selectBrandStr = mTvSelectBrand.getText().toString();
            if (selectBrandStr.isEmpty()) {
                Toast.makeText(getContext(), "请先选择品牌", Toast.LENGTH_LONG).show();
                return;
            }
            if (modelStr.isEmpty()) {
                mEtModel.setError("请填写添加的型号");
            } else {
                mEtModel.setError(null);
                parent = mCurrentBrandEntity.getField(MultipleFields.ID);
                addMaterialMode(ProductIdx.MODEL_IDX, parent, "0", modelStr);
            }
        } else if (id == R.id.btn_add_product_name_ok) {
            final String productNameStr = mEtProductName.getText().toString();
            final String standardStr = mEtStandard.getText().toString();
            final String selectModelStr = mTvSelectModel.getText().toString();
            if (selectModelStr.isEmpty()) {
                Toast.makeText(getContext(), "请先选择型号", Toast.LENGTH_LONG).show();
                return;
            }
            boolean check = true;
            if (productNameStr.isEmpty()) {
                mEtProductName.setError("请填写添加的名称");
                check = false;
            } else {
                mEtProductName.setError(null);
            }
            if (standardStr.isEmpty()) {
                mEtStandard.setError("请填写添加的规格");
                check = false;
            } else {
                mEtStandard.setError(null);
            }
            if (check) {
                parent = mCurrentModelEntity.getField(MultipleFields.ID);
                addMaterialMode(ProductIdx.PRODUCT_NAME_IDX, parent, standardStr, productNameStr + "(" + standardStr + "米/卷)");
            }
        } else if (id == R.id.btn_entry_material) {
            if (checkEntryMaterial()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");// HH:mm:ss//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                entryMaterial(mCategoryNameStr + "/" + mBrandNameStr + "/" + mModelNameStr + "/" + mProductNameStr
                        , mUnitStr, simpleDateFormat.format(date), mCountStr, mInfoStr, mCurrentProductNameEntity.getField(MultipleFields.ID).toString());
            }
        }
        mSelectLayout.setVisibility(View.GONE);
    }

    private boolean checkEntryMaterial() {
        boolean isCheck = true;
        mCategoryNameStr = mTvSelectCategory.getText().toString();
        mBrandNameStr = mTvSelectBrand.getText().toString();
        mModelNameStr = mTvSelectModel.getText().toString();
        mProductNameStr = mTvSelectProductName.getText().toString();
        mCountStr = mEtCount.getText().toString();
        mUnitStr = mTvSelectUnit.getText().toString();
        mInfoStr = mEtInfo.getText().toString();
        if (mCategoryNameStr.isEmpty()) {
            mTvSelectCategory.setError("请选择品类");
            isCheck = false;
        } else {
            mTvSelectCategory.setError(null);
        }
        if (mBrandNameStr.isEmpty()) {
            mTvSelectBrand.setError("请选择品牌");
            isCheck = false;
        } else {
            mTvSelectBrand.setError(null);
        }
        if (mModelNameStr.isEmpty()) {
            mTvSelectModel.setError("请选择型号");
            isCheck = false;
        } else {
            mTvSelectModel.setError(null);
        }
        if (mProductNameStr.isEmpty()) {
            mTvSelectProductName.setError("请选择名称");
            isCheck = false;
        } else {
            mTvSelectProductName.setError(null);
        }
        if (mCountStr.isEmpty() || Double.valueOf(mCountStr) <= 0) {
            mEtCount.setError("请填写正确录入数量");
            isCheck = false;
        } else {
            mEtCount.setError(null);
        }
        return isCheck;

    }

    private void reRecyclerData(MultipleItemEntity entity, String idx) {
        mRvSelect.removeAllViews();
        if (idx.equals(ProductIdx.UNIT)) {
            final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                final MultipleItemEntity entity1 = MultipleItemEntity.builder().build();
                entity1.setField(MultipleFields.ITEM_TYPE, ProductItemType.PRODUCT_ITEM);
                entity1.setField(ProductItemFields.IDX, idx);
                entity1.setField(MultipleFields.TEXT, i == 0 ? "卷" : "米");
                entities.add(entity1);
            }
            mAdapter = new ProductAdapter(entities);
        } else {
            if (entity != null) {
                mAdapter = new ProductAdapter(mConverter.getListData(entity, idx) == null ? new ArrayList<MultipleItemEntity>() : mConverter.getListData(entity, idx));
            } else {
                mAdapter = new ProductAdapter(new ArrayList<MultipleItemEntity>());
            }
        }
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void addMaterialMode(final String idx, final String parent, String info, String productName) {
        RestClient.builder().url("AddMaterialMode?")
                .loader(getContext())
                .params("idx", idx)
                .params("name", productName)
                .params("info", info)
                .params("parent", parent)
                .params("owner", AccountManager.getOwner())
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("addMaterialMode", "onFailure");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("addMaterialMode", "onError" + msg);
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("res", response);
                        if (response.equals("1")) {
                            GetProductInfo(idx, parent);
                            switch (idx) {
                                case ProductIdx.CATEGORY_IDX:
                                    mCategoryLayout.setVisibility(View.GONE);
                                    break;
                                case ProductIdx.BRAND_IDX:
                                    mBrandLayout.setVisibility(View.GONE);
                                    break;
                                case ProductIdx.MODEL_IDX:
                                    mModelLayout.setVisibility(View.GONE);
                                    break;
                                case ProductIdx.PRODUCT_NAME_IDX:
                                    mProductNameLayout.setVisibility(View.GONE);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }).build().get();

    }

    private void entryMaterial(String name, String unit, String time, String number, String info, String typeId) {
        RestClient.builder().url("EntryMaterial?")
                .loader(getContext())
                .params("name", name)
                .params("unit", unit)
                .params("number", number)
                .params("time", time)
                .params("userid", AccountManager.getID())
                .params("owner", AccountManager.getOwner())
                .params("info", info)
                .params("typeId", typeId)
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "入库失败", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("ress", response);
                        Toast.makeText(getContext(), "入库成功", Toast.LENGTH_LONG).show();

                    }
                }).build().get();

    }

    private void GetProductInfo(final String idx, final String ParentID) {
        RestClient.builder().url("GetProductInfo?")
                .params("ParentID", ParentID)
                .loader(getContext())
                .params("Owner", AccountManager.getOwner())
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("onFailure", "onFailure");
                        Toast.makeText(getContext(), "初始化数据失败", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("onFailure", msg);
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("re", JsonUtils.getDecodeJSONStr(response));
                        switch (idx) {
                            case ProductIdx.CATEGORY_IDX:
                            case ProductIdx.BRAND_IDX:
                            case ProductIdx.MODEL_IDX:
                            case ProductIdx.PRODUCT_NAME_IDX:
                                mConverter.reListData(idx, JsonUtils.getDecodeJSONStr(response));
                                break;
                            default:
                                final ArrayList<MultipleItemEntity> entities = mConverter.setJsonData(JsonUtils.getDecodeJSONStr(response)).convert();
                                reViewData(ProductIdx.CATEGORY_IDX, entities == null ? null : entities.get(0));
                                break;
                        }
                    }
                }).build().get();
    }

    @Override
    public void productContent(MultipleItemEntity entity) {
        if (entity != null) {
            final String idx = entity.getField(ProductItemFields.IDX).toString();
            reViewData(idx, entity);
        }
    }

    private void reViewData(String idx, MultipleItemEntity entity) {
        mCategoryNameStr = "";
        mBrandNameStr = "";
        mModelNameStr = "";
        mProductNameStr = "";
        if (entity != null)
            switch (idx) {
                case ProductIdx.CATEGORY_IDX:
                    mCurrentCategoryEntity = null;
                    mCurrentBrandEntity = null;
                    mCurrentModelEntity = null;
                    mCurrentProductNameEntity = null;
                    mCurrentCategoryEntity = entity;
                    mCategoryNameStr = entity.getField(ProductItemFields.NAME).toString();
                    mCurrentBrandEntity = mConverter.getListData(entity, ProductIdx.BRAND_IDX).size() > 0
                            ? mConverter.getListData(entity, ProductIdx.BRAND_IDX).get(0) : null;
                    if (mCurrentBrandEntity != null) {
                        mCurrentModelEntity = mConverter.getListData(mCurrentBrandEntity, ProductIdx.MODEL_IDX).size() > 0
                                ? mConverter.getListData(mCurrentBrandEntity, ProductIdx.MODEL_IDX).get(0) : null;
                        mBrandNameStr = mCurrentBrandEntity.getField(ProductItemFields.NAME).toString();
                        if (mCurrentModelEntity != null) {
                            mModelNameStr = mCurrentModelEntity.getField(ProductItemFields.NAME).toString();
                            mCurrentProductNameEntity = mConverter.getListData(mCurrentModelEntity, ProductIdx.PRODUCT_NAME_IDX).size() > 0
                                    ? mConverter.getListData(mCurrentModelEntity, ProductIdx.PRODUCT_NAME_IDX).get(0) : null;
                            if (mCurrentProductNameEntity != null) {
                                mProductNameStr = mCurrentProductNameEntity.getField(ProductItemFields.NAME).toString();
                            }
                        }
                    }
                    break;
                case ProductIdx.BRAND_IDX:
                    mCurrentBrandEntity = null;
                    mCurrentModelEntity = null;
                    mCurrentProductNameEntity = null;
                    mCurrentBrandEntity = entity;
                    mCategoryNameStr = mCurrentCategoryEntity.getField(ProductItemFields.NAME).toString();
                    mBrandNameStr = entity.getField(ProductItemFields.NAME).toString();
                    mCurrentModelEntity = mConverter.getListData(entity, ProductIdx.MODEL_IDX).size() > 0
                            ? mConverter.getListData(entity, ProductIdx.MODEL_IDX).get(0) : null;
                    if (mCurrentModelEntity != null) {
                        mModelNameStr = mCurrentModelEntity.getField(ProductItemFields.NAME).toString();
                        mCurrentProductNameEntity = mConverter.getListData(mCurrentModelEntity, ProductIdx.PRODUCT_NAME_IDX).size() > 0
                                ? mConverter.getListData(mCurrentModelEntity, ProductIdx.PRODUCT_NAME_IDX).get(0) : null;
                        if (mCurrentProductNameEntity != null) {
                            mProductNameStr = mCurrentProductNameEntity.getField(ProductItemFields.NAME).toString();
                        }
                    }
                    break;
                case ProductIdx.MODEL_IDX:
                    mCurrentModelEntity = null;
                    mCurrentProductNameEntity = null;
                    mCurrentModelEntity = entity;
                    mCategoryNameStr = mCurrentCategoryEntity.getField(ProductItemFields.NAME).toString();
                    mBrandNameStr = mCurrentBrandEntity.getField(ProductItemFields.NAME).toString();
                    mModelNameStr = entity.getField(ProductItemFields.NAME).toString();
                    mCurrentProductNameEntity = mConverter.getListData(entity, ProductIdx.PRODUCT_NAME_IDX).size() > 0
                            ? mConverter.getListData(entity, ProductIdx.PRODUCT_NAME_IDX).get(0) : null;
                    if (mCurrentProductNameEntity != null) {
                        mProductNameStr = mCurrentProductNameEntity.getField(ProductItemFields.NAME).toString();
                    }
                    break;
                case ProductIdx.PRODUCT_NAME_IDX:
                    mCurrentProductNameEntity = null;
                    mCurrentProductNameEntity = entity;
                    mCategoryNameStr = mCurrentCategoryEntity.getField(ProductItemFields.NAME).toString();
                    mBrandNameStr = mCurrentBrandEntity.getField(ProductItemFields.NAME).toString();
                    mModelNameStr = mCurrentModelEntity.getField(ProductItemFields.NAME).toString();
                    mProductNameStr = entity.getField(ProductItemFields.NAME).toString();
                    break;
                case ProductIdx.UNIT:
                    mCategoryNameStr = mCurrentCategoryEntity.getField(ProductItemFields.NAME).toString();
                    mBrandNameStr = mCurrentBrandEntity.getField(ProductItemFields.NAME).toString();
                    mModelNameStr = mCurrentModelEntity.getField(ProductItemFields.NAME).toString();
                    mProductNameStr = mCurrentProductNameEntity.getField(ProductItemFields.NAME).toString();
                    mUnitStr = entity.getField(MultipleFields.TEXT).toString();
                    mTvSelectUnit.setText(mUnitStr);
                    if (mUnitStr.equals("卷")) {
                        mTvSelectUnit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else
                        mTvSelectUnit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                default:
                    break;
            }
        mTvSelectCategory.setText(mCategoryNameStr);
        mTvSelectBrand.setText(mBrandNameStr);
        mTvSelectModel.setText(mModelNameStr);
        mTvSelectProductName.setText(mProductNameStr);
        mSelectLayout.setVisibility(View.GONE);
    }
}
