package com.yuanting.n2erp.main.index.stockOut;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.n2erp.main.index.stockIn.ProductItemOnClick;
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
public class StockOutDelegate extends LatteDelegate implements ProductItemOnClick {
    @BindView(R2.id.tv_select_product_name)
    AppCompatTextView mTvSelectProductName;
    @BindView(R2.id.tv_surplus_count)
    AppCompatTextView mTvSurplusCount;
    @BindView(R2.id.tv_surplus)
    AppCompatTextView mTvSurplus;
    @BindView(R2.id.tv_select_title)
    AppCompatTextView mTvSelectTitle;
    @BindView(R2.id.tv_unit_out)
    AppCompatTextView mTvUnit;
    @BindView(R2.id.tv_select_purpose)
    AppCompatTextView mSelectPurpose;
    @BindView(R2.id.et_select_product_name)
    AppCompatEditText mEtSelectProductName;
    @BindView(R2.id.tv_select_other_purpose)
    AppCompatEditText mEtSelectOtherPurpose;
    @BindView(R2.id.et_count)
    AppCompatEditText mEtCount;
    @BindView(R2.id.rv_select)
    RecyclerView mRvSelect;
    @BindView(R2.id.surplus_layout)
    LinearLayoutCompat mSurplusLayout;
    @BindView(R2.id.linear_layout_select)
    LinearLayoutCompat mSelectLayout;
    private StockOutProductAdapter mAdapter;
    private StockOutProductDataConverter mConverter = new StockOutProductDataConverter();
    private MultipleItemEntity mCurrentProductNameEntity;
    private MultipleItemEntity mCurrentPurposeEntity;
    private boolean isUnit = true;
    private String mSourceidStr = "-1";
    private String mUnitStr = "卷";
    private final ArrayList<MultipleItemEntity> PointEntities = new ArrayList<>();
    private String mNameStr;
    private String mNumberStr;
    private String mTextStr;
    private String mOtherinfoStr;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        GetInventoryAll();
        GetOutPoint();
        String s = "2sdfsf3e";
        String s1 = "2";
        String s2 = "2s";
        String s3 = "fsf";
//        Log.i("s1", ""+s.contentEquals());
//        Log.i("s2", ""+s.contentEquals(s2));
//        Log.i("s3", ""+s.contentEquals(s3));
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    private void GetInventoryAll() {
        RestClient.builder().url("GetInventoryAll?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetInventoryAll", JsonUtils.getDecodeJSONStr(response));
                        mConverter.setJsonData(JsonUtils.getDecodeJSONStr(response)).convert();
                        final ArrayList<MultipleItemEntity> entities = mConverter.getProductNameListData();
                        if (entities.size() > 0) {
                            productContent(entities.get(0));
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("onError", msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("onFailure", "onFailure");
                    }
                }).build().get();

    }

    private void GetOutPoint() {
        RestClient.builder().url("GetOutPoint?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetOutPoint", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray dataArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                    .build();
                            final JSONObject object = dataArray.getJSONObject(i);
                            final String id = object.getString("ID");
                            final String Name = object.getString("Name");
                            final String Phone = object.getString("Phone");
                            final String Address = object.getString("Address");
                            final String Owner = object.getString("Owner");
                            itemEntity.setField(MultipleFields.ID, id);
                            itemEntity.setField(MultipleFields.TEXT, Name);
                            itemEntity.setField(StockOutProductItemFields.NAME, Name);
                            itemEntity.setField(StockOutProductItemFields.PHONE, Phone);
                            itemEntity.setField(StockOutProductItemFields.ADDRESS, Address);
                            itemEntity.setField(StockOutProductItemFields.OWNER, Owner);
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_ITEM);
                            itemEntity.setField(StockOutProductItemFields.ITEM_TYPE, StockOutProductItemType.PRODUCT_POINT_ITEM);
                            PointEntities.add(itemEntity);
                        }
                        final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                .build();
                        itemEntity.setField(MultipleFields.TEXT, "无");
                        itemEntity.setField(StockOutProductItemFields.NAME, "无");
                        itemEntity.setField(StockOutProductItemFields.ITEM_TYPE, StockOutProductItemType.PRODUCT_POINT_ITEM);
                        itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_ITEM);
                        PointEntities.add(0, itemEntity);
                        if (PointEntities.size() > 0) {
                            productContent(PointEntities.get(0));
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("onError", msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("onFailure", "onFailure");
                    }
                }).build().get();

    }

    @OnClick({R2.id.layout_select_product_name, R2.id.surplus_layout, R2.id.select_unit_layout, R2.id.layout_select_purpose})
    void selectOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.layout_select_product_name) {
            mTvSelectTitle.setText("产品名称");
            reRecyclerData();
            mSelectLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.surplus_layout) {
            mTvSelectTitle.setText("余料选择");
            if (mCurrentProductNameEntity != null) {
                reSelectRecyclerData(isUnit, mCurrentProductNameEntity);
                mSelectLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), "请选择产品", Toast.LENGTH_SHORT).show();
                mSelectLayout.setVisibility(View.GONE);
            }
        } else if (isUnit && id == R.id.select_unit_layout) {
            mTvSelectTitle.setText("单位");
            isUnit = true;
            unitRecyclerData();
            mSelectLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.layout_select_purpose) {
            OutPointRecyclerData();
            mSelectLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R2.id.rv_select_cancel)
    void cancelBtnOnClick() {
        mSelectLayout.setVisibility(View.GONE);
    }

    @OnClick({R2.id.btn_select_product_name, R2.id.btn_out_bound})
    void btnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.btn_select_product_name) {
            final String name = mEtSelectProductName.getText().toString();
            if (name.isEmpty()) {
                mEtSelectProductName.setError("请填写筛选字段");
            } else {
                mEtSelectProductName.setError(null);
                selectProductNameRecyclerData(name);
            }
        } else if (id == R.id.btn_out_bound) {
            if (isCheck()) {
                if (mUnitStr.equals("卷")) {
                    mSourceidStr = "0";
                } else if (mTvSurplus.getText().equals("从整卷拆出")) {
                    mSourceidStr = "-1";
                } else {
                    mSourceidStr = mCurrentPurposeEntity.getField(MultipleFields.ID);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                OutBound(mNameStr, mSourceidStr, mUnitStr, mNumberStr, simpleDateFormat.format(date), mTextStr, mOtherinfoStr);
            }
        }
        mSelectLayout.setVisibility(View.GONE);
    }

    private boolean isCheck() {
        boolean isCheck = true;
        mNameStr = mTvSelectProductName.getText().toString();
        mOtherinfoStr = mEtSelectOtherPurpose.getText().toString();
        mNumberStr = mEtCount.getText().toString();
        mTextStr = mSelectPurpose.getText().toString();
        mUnitStr = mTvUnit.getText().toString();
        if (mNumberStr.isEmpty() || Double.valueOf(mNumberStr) < 0) {
            Toast.makeText(getContext(), "请重新输入出库数量", Toast.LENGTH_SHORT).show();
            isCheck = false;
        } else if (mNameStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择出库的产品", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }

    private void OutBound(String name, String sourceid, String unit, String number, String time, String text, String otherinfo) {
        RestClient.builder().url("OutBound?").loader(getContext())
                .params("name", name)
                .params("sourceid", sourceid)
                .params("unit", unit)
                .params("number", number)
                .params("userid", AccountManager.getID())
                .params("time", time)
                .params("text", text)
                .params("otherinfo", otherinfo)
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (response.equals("1")) {
                            Toast.makeText(getContext(), "出库成功", Toast.LENGTH_SHORT).show();
                            GetInventoryAll();
                            GetOutPoint();
                        } else
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        Log.i("OutBound", response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("onError", msg);
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "出库失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();

    }

    private void OutPointRecyclerData() {
        mRvSelect.removeAllViews();
        mAdapter = new StockOutProductAdapter(PointEntities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void selectProductNameRecyclerData(String name) {
        mRvSelect.removeAllViews();
        final ArrayList<MultipleItemEntity> entities = mConverter.getListByName(name);
        if (entities.size() > 0) {
            productContent(entities.get(0));
        } else {
            mTvSurplusCount.setText("");
            mTvSelectProductName.setText("");
        }
        mAdapter = new StockOutProductAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void unitRecyclerData() {
        mRvSelect.removeAllViews();
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            final MultipleItemEntity entity1 = MultipleItemEntity.builder().build();
            entity1.setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM);
            entity1.setField(StockOutProductItemFields.NAME, i == 0 ? "卷" : "米");
            entity1.setField(MultipleFields.TEXT, i == 0 ? "卷" : "米");
            entities.add(entity1);
        }
        mAdapter = new StockOutProductAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void reSelectRecyclerData(boolean isUnit, MultipleItemEntity entity) {
        mRvSelect.removeAllViews();
        final ArrayList<MultipleItemEntity> entities = mConverter.getListByEntity(entity, "米");
        if (isUnit) {
            entities.add(0, MultipleItemEntity.builder().setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                    .setField(MultipleFields.TEXT, "从整卷拆出")
                    .setField(StockOutProductItemFields.NAME, "从整卷拆出")
                    .setField(StockOutProductItemFields.ITEM_TYPE, StockOutProductItemType.PRODUCT_POINT_ITEM)
                    .setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_ITEM).build());
        }
        mAdapter = new StockOutProductAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void reRecyclerData() {
        mRvSelect.removeAllViews();
        final ArrayList<MultipleItemEntity> entities;
        final String name = mEtSelectProductName.getText().toString();
        if (name.isEmpty()) {
            entities = mConverter.getProductNameListData();
        } else {
            entities = mConverter.getListByName(name);
        }
        mAdapter = new StockOutProductAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_stock_out_erp;
    }

    @Override
    public void productContent(MultipleItemEntity entity) {
        final String typeItem = entity.getField(StockOutProductItemFields.ITEM_TYPE);
        final String Idx = entity.getField(StockOutProductItemFields.IDX);
        final String mName = entity.getField(StockOutProductItemFields.NAME);
        if (Idx == null) {
            switch (mName) {
                case "卷":
                    mSourceidStr = "0";
                    mSurplusLayout.setVisibility(View.INVISIBLE);
                    break;
                case "米":
                    mSourceidStr = "-1";
                    mSurplusLayout.setVisibility(View.VISIBLE);
                    break;
            }
            mTvSurplus.setText("从整卷拆出");
            mUnitStr = mName;
            mTvUnit.setText(mName);
        } else
            switch (Idx) {
                case StockOutProductItemType.PRODUCT_ITEM:
                    switch (typeItem) {
                        case StockOutProductItemType.PRODUCT_ITEM:
                            mCurrentProductNameEntity = entity;
                            final int size = mConverter.getListByEntity(entity, "卷").size();
                            mTvSurplusCount.setText("仓库中\"" + mName + "\"还剩" + size + "卷");
                            mTvSelectProductName.setText(mName);
                            isUnit = true;
                            mTvSurplus.setText("从整卷拆出");
                            break;
                        case StockOutProductItemType.PRODUCT_POINT_ITEM:
                            mSelectPurpose.setText(mName);
                            break;
                        case StockOutProductItemType.PRODUCT_UNIT_ITEM:
                            mCurrentProductNameEntity = entity;
                            mTvSelectProductName.setText(mName);
                            mTvSurplusCount.setText("仓库中没有该料的整卷.");
                            isUnit = false;
                            mTvUnit.setText("米");
                            mSurplusLayout.setVisibility(View.VISIBLE);
                            final String number = entity.getField(StockOutProductItemFields.NUMBER);
                            final String unit = entity.getField(StockOutProductItemFields.UNIT);
                            mTvSurplus.setText(number + unit);
                            mCurrentPurposeEntity = entity;
                            break;
                        default:
                            break;
                    }
                    break;
                case StockOutProductItemType.PRODUCT_UNIT_ITEM:
                    mCurrentPurposeEntity = entity;
                    mTvSurplus.setText(entity.getField(MultipleFields.TEXT).toString());
                    break;
            }
        mUnitStr = mTvUnit.getText().toString();
        mSelectLayout.setVisibility(View.GONE);

    }
}
