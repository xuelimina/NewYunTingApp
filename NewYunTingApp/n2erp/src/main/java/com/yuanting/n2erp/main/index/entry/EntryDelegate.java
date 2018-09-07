package com.yuanting.n2erp.main.index.entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.n2erp.main.index.stockIn.ProductItemOnClick;
import com.yuanting.n2erp.main.index.stockOut.StockOutProductAdapter;
import com.yuanting.n2erp.main.index.stockOut.StockOutProductItemFields;
import com.yuanting.n2erp.main.index.stockOut.StockOutProductItemType;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.date.DateDialogUtil;
import com.yuanting.yunting_core.ui.date.TimeDialogUtil;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/23 13:10
 * Created by 薛立民
 * TEL 13262933389
 */
public class EntryDelegate extends LatteDelegate implements ProductItemOnClick, PositionItemOnClick {
    @BindView(R2.id.tv_select_receive_cart_date)
    AppCompatTextView mTvReceiveCartDate;
    @BindView(R2.id.tv_select_receive_cart_time)
    AppCompatTextView mTvReceiveCartTime;
    @BindView(R2.id.tv_select_place_name)
    AppCompatTextView mTvPlaceName;
    @BindView(R2.id.tv_select_user_name)
    AppCompatTextView mTvUserName;
    @BindView(R2.id.tv_select_color_name)
    AppCompatTextView mTvColorName;
    @BindView(R2.id.tv_select_product)
    AppCompatTextView mTvProductName;
    @BindView(R2.id.rv_select)
    RecyclerView mRvSelect;
    @BindView(R2.id.rv_position)
    RecyclerView mRvPosition;
    @BindView(R2.id.linear_layout_select)
    LinearLayoutCompat mSelectLayout;
    @BindView(R2.id.tv_select_title)
    AppCompatTextView mTvSelectTitle;
    @BindView(R2.id.tv_select_position_name)
    AppCompatTextView mTvSelectPositionName;
    @BindView(R2.id.user_layout)
    LinearLayoutCompat mAddUserLayout;
    @BindView(R2.id.position_layout)
    RelativeLayout mAddPositionLayout;
    @BindView(R2.id.color_layout)
    RelativeLayout mAddColorLayout;
    @BindView(R2.id.et_user_name)
    AppCompatEditText mEtUserName;
    @BindView(R2.id.et_user_number)
    AppCompatEditText mEtUserNumber;
    @BindView(R2.id.et_position)
    AppCompatEditText mEtPosition;
    @BindView(R2.id.et_color)
    AppCompatEditText mEtColor;
    private final ArrayList<MultipleItemEntity> PointEntities = new ArrayList<>();
    private final ArrayList<MultipleItemEntity> mBodyEntities = new ArrayList<>();
    private final ArrayList<MultipleItemEntity> mPartEntities = new ArrayList<>();
    private final ArrayList<MultipleItemEntity> mMaterialEntities = new ArrayList<>();
    private final ArrayList<MultipleItemEntity> mColorsEntities = new ArrayList<>();
    private String mSelectPosition = "";

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        GetPartner();
        GetParts();
        GetBodys();
        GetColors();
        GetInventoryName();
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick(R2.id.btn_add_construction)
    void btnOnClickAddConstruction() {
        boolean isCheck = true;
        final String dateStr = mTvReceiveCartDate.getText().toString();
        final String timeStr = mTvReceiveCartTime.getText().toString();
        final String addressStr = mTvPlaceName.getText().toString();
        final String userStr = mTvUserName.getText().toString();
        final String positionStr = mTvSelectPositionName.getText().toString();
        final String materialStr = mTvProductName.getText().toString();
        final String colorStr = mTvColorName.getText().toString();
        if (dateStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择接车日期", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (timeStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择接车时间", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (addressStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择接车地点", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (userStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择负责人", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (positionStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择施工部位", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (materialStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择施工材料", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (colorStr.isEmpty()) {
            Toast.makeText(getContext(), "请选择颜色", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (isCheck) {
            AddConstruction(dateStr + " " + timeStr, addressStr, userStr, colorStr, positionStr, materialStr);
        }
    }


    @OnClick({R2.id.btn_add_user, R2.id.btn_add_position, R2.id.btn_add_color
            , R2.id.btn_add_user_cancel, R2.id.btn_add_position_cancel, R2.id.btn_add_color_cancel
            , R2.id.btn_add_user_ok, R2.id.btn_add_position_ok, R2.id.btn_add_color_ok
            , R2.id.rv_select_cancel})
    void btnOnClick(View view) {
        final int id = view.getId();
        if (id == R.id.rv_select_cancel) {
            mSelectLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_user) {
            mAddUserLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_add_position) {
            mAddPositionLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_add_color) {
            mAddColorLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_add_user_cancel) {
            mAddUserLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_position_cancel) {
            mAddPositionLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_color_cancel) {
            mAddColorLayout.setVisibility(View.GONE);
        } else if (id == R.id.btn_add_user_ok) {
            boolean isCheckUser = true;
            final String mUserNameStr = mEtUserName.getText().toString();
            final String mUserNumberStr = mEtUserNumber.getText().toString();
            if (mUserNameStr.isEmpty()) {
                isCheckUser = false;
                mEtUserName.setError("请填写负责人姓名");
            } else
                mEtUserName.setError(null);
            if (mUserNumberStr.isEmpty() || mUserNumberStr.length() < 11) {
                isCheckUser = false;
                mEtUserNumber.setError("请填写正确的联系方式");
            } else
                mEtUserNumber.setError(null);
            if (isCheckUser) {
                if (isCheckByName(mUserNameStr, mBodyEntities)) {
                    mAddUserLayout.setVisibility(View.GONE);
                    AddBody(mUserNameStr, mUserNumberStr);
                } else {
                    Toast.makeText(getContext(), "负责人已存在", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (id == R.id.btn_add_position_ok) {
            final String mPositionNameStr = mEtPosition.getText().toString();
            if (mPositionNameStr.isEmpty()) {
                mEtPosition.setError("请填写部位名称");
            } else {
                mEtPosition.setError(null);
                if (isCheckByName(mPositionNameStr, mPartEntities)) {
                    mAddPositionLayout.setVisibility(View.GONE);
                    AddParts(mPositionNameStr);
                } else {
                    Toast.makeText(getContext(), "部位名称已存在", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (id == R.id.btn_add_color_ok) {
            final String mColorNameStr = mEtColor.getText().toString();
            if (mColorNameStr.isEmpty()) {
                mEtColor.setError("请填写颜色");
            } else {
                mEtColor.setError(null);
                if (isCheckByName(mColorNameStr, mColorsEntities)) {
                    mAddColorLayout.setVisibility(View.GONE);
                    AddColor(mColorNameStr);
                } else {
                    Toast.makeText(getContext(), "颜色已存在", Toast.LENGTH_SHORT).show();
                }

            }
        }
        mSelectLayout.setVisibility(View.GONE);
    }

    @OnClick({R2.id.select_receive_cart_date_layout, R2.id.select_receive_cart_time_layout, R2.id.select_place_layout
            , R2.id.select_position_layout, R2.id.select_user_layout, R2.id.select_product_layout, R2.id.select_color_layout})
    void selectOnClick(View view) {
        mRvSelect.removeAllViews();
        final int id = view.getId();
        if (id == R.id.select_receive_cart_date_layout) {
            new DateDialogUtil().setDateListener(new DateDialogUtil.IDateListener() {
                @Override
                public void onDateChange(String date) {
                    mTvReceiveCartDate.setText(date);
                }
            }).showDialog(getContext());
        } else if (id == R.id.select_receive_cart_time_layout) {
            new TimeDialogUtil().setTimeListener(new TimeDialogUtil.ITimeListener() {
                @Override
                public void onTimeChange(String time) {
                    mTvReceiveCartTime.setText(time);
                }
            }).showDialog(getContext());
        } else if (id == R.id.select_place_layout) {
            mTvSelectTitle.setText("接车地点");
            reRecyclerData(PointEntities);
            mSelectLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.select_user_layout) {
            mTvSelectTitle.setText("负责人");
            reRecyclerData(mBodyEntities);
            mSelectLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.select_product_layout) {
            mTvSelectTitle.setText("施工材料");
            reRecyclerData(mMaterialEntities);
            mSelectLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.select_color_layout) {
            mTvSelectTitle.setText("颜色");
            reRecyclerData(mColorsEntities);
            mSelectLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.select_position_layout) {
            reRecyclerPositionData(mPartEntities);
            mSelectLayout.setVisibility(View.GONE);
            mRvPosition.setVisibility(View.VISIBLE);
        }

    }

    private boolean isCheckByName(String checkName, ArrayList<MultipleItemEntity> entities) {
        boolean isCheck = false;
        for (MultipleItemEntity entity : entities) {
            final String name = entity.getField(StockOutProductItemFields.NAME);
            if (checkName.equals(name)) {
                isCheck = true;
                break;
            }
        }
        return !isCheck;

    }

    private void AddConstruction(String tim, String address, String user, String color, String position, String material) {
        RestClient.builder().url("AddConstruction?")
                .params("posi", address)
                .params("manager", user).params("color", color).params("site", position).params("material", material)
                .params("tim", tim).params("infos", user).params("state", "0")
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("AddConstruction", response);
                        if (response.equals("1")) {
                            Toast.makeText(getContext(), "录入成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "添加施工颜色失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();
    }

    private void AddColor(String name) {
        RestClient.builder().url("AddColor?")
                .params("color", name)
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("AddColor", response);
                        if (response.equals("1")) {
                            GetColors();
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "添加施工颜色失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();

    }

    private void AddParts(final String name) {
        RestClient.builder().url("AddParts?")
                .params("pt", name)
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("AddParts", response);
                        if (response.equals("1")) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                    .build();
                            itemEntity.setField(MultipleFields.TEXT, name);
                            itemEntity.setField(MultipleFields.TAG, false);
                            itemEntity.setField(MultipleFields.SPAN_SIZE, 4);
                            itemEntity.setField(StockOutProductItemFields.NAME, name);
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.ENTRY_PARTS_ITEM);
                            mPartEntities.add(0, itemEntity);
                            reRecyclerPositionData(mPartEntities);
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "添加施工部位失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();

    }

    private void AddBody(String name, String phoneNumber) {
        RestClient.builder().url("AddBody?")
                .params("name", name)
                .params("phonenumber", phoneNumber)
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("addBody", response);
                        if (response.equals("1")) {
                            GetBodys();
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "添加负责人失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();

    }

    private void GetPartner() {
        PointEntities.clear();
        RestClient.builder().url("GetPartner?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetPartner", JsonUtils.getDecodeJSONStr(response));
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
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.ENTRY_PARTNER_ITEM);
                            PointEntities.add(itemEntity);
                        }
                        if (PointEntities.size() > 0) {
                            mTvPlaceName.setText(PointEntities.get(0).getField(StockOutProductItemFields.NAME).toString());
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

    private void GetBodys() {
        mBodyEntities.clear();
        RestClient.builder().url("GetBodys?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetBodys", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray dataArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                    .build();
                            final JSONObject object = dataArray.getJSONObject(i);
                            final String Name = object.getString("Name");
                            itemEntity.setField(MultipleFields.TEXT, Name);
                            itemEntity.setField(StockOutProductItemFields.NAME, Name);
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.ENTRY_BODY_ITEM);
                            mBodyEntities.add(itemEntity);
                        }
                        if (mBodyEntities.size() > 0)
                            mTvUserName.setText(mBodyEntities.get(0).getField(StockOutProductItemFields.NAME).toString());
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

    private void GetParts() {
        mPartEntities.clear();
        RestClient.builder().url("GetParts?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetParts", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray dataArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                    .build();
                            final JSONObject object = dataArray.getJSONObject(i);
                            final String id = object.getString("Id");
                            final String Name = object.getString("Name");
                            final String Owner = object.getString("Owner");
                            itemEntity.setField(MultipleFields.ID, id);
                            itemEntity.setField(MultipleFields.TEXT, Name);
                            itemEntity.setField(MultipleFields.SPAN_SIZE, 4);
                            itemEntity.setField(MultipleFields.TAG, false);
                            itemEntity.setField(StockOutProductItemFields.NAME, Name);
                            itemEntity.setField(StockOutProductItemFields.OWNER, Owner);
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.ENTRY_PARTS_ITEM);
                            mPartEntities.add(itemEntity);
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

    private void GetColors() {
        mColorsEntities.clear();
        RestClient.builder().url("GetColors?").loader(getContext())
                .params("owner", AccountManager.getOwner())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetColors", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray dataArray = JSON.parseArray(response);
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                    .build();
                            final String Name = dataArray.getString(i);
                            itemEntity.setField(MultipleFields.TEXT, Name);
                            itemEntity.setField(StockOutProductItemFields.NAME, Name);
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.ENTRY_COLOR_ITEM);
                            mColorsEntities.add(itemEntity);
                        }
                        if (mColorsEntities.size() > 0) {
                            mTvColorName.setText(mColorsEntities.get(0).getField(StockOutProductItemFields.NAME).toString());
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

    private void GetInventoryName() {
        mMaterialEntities.clear();
        RestClient.builder().url("GetInventoryName?")
                .params("owner", AccountManager.getOwner())
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetInventoryName", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray dataArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        final int size = dataArray.size();
                        for (int i = 0; i < size; i++) {
                            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                                    .build();
                            final JSONObject object = dataArray.getJSONObject(i);
                            final String Name = object.getString("Name");
                            itemEntity.setField(MultipleFields.TEXT, Name);
                            itemEntity.setField(StockOutProductItemFields.NAME, Name);
                            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.ENTRY_MATERIAL_ITEM);
                            mMaterialEntities.add(itemEntity);
                        }
                        if (mMaterialEntities.size() > 0) {
                            mTvProductName.setText(mMaterialEntities.get(0).getField(StockOutProductItemFields.NAME).toString());
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "获取数据信息失败", Toast.LENGTH_SHORT).show();
                    }
                }).build().get();
    }

    private void reRecyclerPositionData(ArrayList<MultipleItemEntity> entities) {
        mRvPosition.removeAllViews();
        final PositionAdapter mPositonAdapter = new PositionAdapter(entities);
        mPositonAdapter.setItemOnClick(this);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRvPosition.setLayoutManager(manager);
        mRvPosition.setAdapter(mPositonAdapter);
        mPositonAdapter.notifyDataSetChanged();
    }

    private void reRecyclerData(ArrayList<MultipleItemEntity> entities) {
        mRvSelect.removeAllViews();
        final StockOutProductAdapter mAdapter = new StockOutProductAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelect.setLayoutManager(layoutManager);
        mRvSelect.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_entry_erp;
    }

    @Override
    public void productContent(MultipleItemEntity entity) {
        final String idx = entity.getField(StockOutProductItemFields.IDX);
        final String name = entity.getField(StockOutProductItemFields.NAME);
        switch (idx) {
            case StockOutProductItemType.ENTRY_PARTNER_ITEM:
                mTvPlaceName.setText(name);
                break;
            case StockOutProductItemType.ENTRY_BODY_ITEM:
                mTvUserName.setText(name);
                break;
            case StockOutProductItemType.ENTRY_PARTS_ITEM:
                mTvSelectPositionName.setText(name);
                break;
            case StockOutProductItemType.ENTRY_MATERIAL_ITEM:
                mTvProductName.setText(name);
                break;
            case StockOutProductItemType.ENTRY_COLOR_ITEM:
                mTvColorName.setText(name);
                break;
        }
        mSelectLayout.setVisibility(View.GONE);
    }

    @Override
    public void positionItemOnClick(MultipleItemEntity entity) {
        final boolean tag = entity.getField(MultipleFields.TAG);
        final String name = entity.getField(StockOutProductItemFields.NAME);
        if (tag) {
            if (mSelectPosition.length() != 0) {
                mSelectPosition = mSelectPosition + "," + name;
            } else {
                mSelectPosition = name;
            }
        } else {
            if (mSelectPosition.equals(name)) {
                mSelectPosition = "";
            } else if (mSelectPosition.indexOf(name) == 0) {
                mSelectPosition = mSelectPosition.replace(name + ",", "");
            } else {
                mSelectPosition = mSelectPosition.replace("," + name, "");
            }

        }
        mTvSelectPositionName.setText(mSelectPosition);
        mRvPosition.setVisibility(View.GONE);
    }
}
