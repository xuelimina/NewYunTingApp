package com.yuanting.latte.ec.main.index.orderQuery;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/7/13 10:17
 * Created by 薛立民
 * TEL 13262933389
 */
public class OrderDetailsDelegate extends LatteDelegate implements ISuccess, IError, IFailure {
    @BindView(R2.id.tv_area)
    AppCompatTextView mTVArea;
    @BindView(R2.id.tv_agency)
    AppCompatTextView mTVAgency;
    @BindView(R2.id.tv_product_name)
    AppCompatTextView mTVProductName;
    @BindView(R2.id.tv_product_number)
    AppCompatTextView mTVProductNumber;
    @BindView(R2.id.tv_gp)
    AppCompatTextView mTVGP;
    @BindView(R2.id.et_customer_name)
    AppCompatEditText mETCustomerName;
    @BindView(R2.id.et_customer_sex)
    AppCompatEditText mETCustomerSex;
    @BindView(R2.id.et_customer_contact)
    AppCompatEditText mETCustomerContact;
    @BindView(R2.id.et_car_model)
    AppCompatEditText mETCarModel;
    @BindView(R2.id.et_car_number)
    AppCompatEditText mETCarNumber;
    @BindView(R2.id.et_car_color)
    AppCompatEditText mETCarColor;
    @BindView(R2.id.et_vin)
    AppCompatEditText mETVIN;
    @BindView(R2.id.et_store_name)
    AppCompatEditText mETStoreName;
    @BindView(R2.id.et_store_contact)
    AppCompatEditText mETStoreContact;
    /**
     * 整车
     */
    @BindView(R2.id.ck_sale_part_whole_vehicle)
    AppCompatCheckedTextView mCKWholeVehicle;
    /**
     * 左车身套餐
     */
    @BindView(R2.id.ck_sale_part_left_only)
    AppCompatCheckedTextView mCKLeftOnly;
    /**
     * 右车身套餐
     */
    @BindView(R2.id.ck_sale_part_right_only)
    AppCompatCheckedTextView mCKRightOnly;
    /**
     * 前车身套餐
     */
    @BindView(R2.id.ck_sale_part_front_only)
    AppCompatCheckedTextView mCKFrontOnly;
    /**
     * 前杠
     */
    @BindView(R2.id.ck_sale_part_front_bumper_kit)
    AppCompatCheckedTextView mCKFrontBumperKit;
    /**
     * 后杠
     */
    @BindView(R2.id.ck_sale_part_rear_bumper_kit)
    AppCompatCheckedTextView mCKRearBumperKit;
    /**
     * 机盖
     */
    @BindView(R2.id.ck_sale_part_hood_kit)
    AppCompatCheckedTextView mCKHoodKit;
    /**
     * 右前叶子板
     */
    @BindView(R2.id.ck_sale_part_front_fender_kit_right_only)
    AppCompatCheckedTextView mCKFrontFenderKitRightOnly;
    /**
     * 左前叶子板
     */
    @BindView(R2.id.ck_sale_part_front_fender_kit_left_only)
    AppCompatCheckedTextView mCKFrontFenderKitLeftOnly;
    /**
     * 右后叶子板
     */
    @BindView(R2.id.ck_sale_part_rear_fender_kit_right_only)
    AppCompatCheckedTextView mCKRearFenderKitRightOnly;
    /**
     * 左后叶子板
     */
    @BindView(R2.id.ck_sale_part_rear_fender_kit_left_only)
    AppCompatCheckedTextView mCKRearFenderKitLeftOnly;
    /**
     * 左门组合
     */
    @BindView(R2.id.ck_sale_part_door_kit_left_only)
    AppCompatCheckedTextView mCKDoorKitLeftOnly;
    /**
     * 右门组合
     */
    @BindView(R2.id.ck_sale_part_door_kit_right_only)
    AppCompatCheckedTextView mCKDoorKitRightOnly;
    /**
     * 右侧裙
     */
    @BindView(R2.id.ck_sale_part_rocker_panel_kit_right_only)
    AppCompatCheckedTextView mCKRockerPanelKitRightOnly;
    /**
     * 左侧裙
     */
    @BindView(R2.id.ck_sale_part_rocker_panel_kit_left_only)
    AppCompatCheckedTextView mCKRockerPanelKitLeftOnly;
    /**
     * 车顶
     */
    @BindView(R2.id.ck_sale_part_roof_kit)
    AppCompatCheckedTextView mCKRoofKit;
    /**
     * 后盖组合
     */
    @BindView(R2.id.ck_sale_part_trunk_lid_kit)
    AppCompatCheckedTextView mCKTrunkLidKit;
    /**
     * 后视镜
     */
    @BindView(R2.id.ck_sale_part_mirror_kit)
    AppCompatCheckedTextView mCKMirrorKit;
    @BindView(R2.id.tv_exp)
    AppCompatTextView mTVExp;
    @BindView(R2.id.tv_sale_time)
    AppCompatTextView mTVSaleTime;
    //总金额
    @BindView(R2.id.et_price)
    AppCompatEditText mETPrice;
    //备注
    @BindView(R2.id.et_remarks)
    AppCompatEditText mETRemarks;
    @BindView(R2.id.btn_order_detail_sub)
    AppCompatButton mBtnSub;
    private String mStrArea;
    private String mStrAgency;
    private String mStrProductName;
    private String mStrProductNumber;
    private String mStrGP;
    private String mStrCustomerName;
    private String mStrCustomerSex;
    private String mStrCustomerContact;
    private String mStrCarModel;
    private String mStrCarNumber;
    private String mStrVIN;
    private String mStrCarColor;
    private String mStrSalePart;
    private String mStrStoreName;
    private String mStrStoreContact;
    private String mStrRemarks;
    private String mStrPrice;
    private String mStrEXP;
    private String mStrSaleTime;
    private String mWarrantyID;
    private ArrayList<AppCompatCheckedTextView> mAllChecked;
    private boolean mIsSale = false;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            if (args.getBoolean("IsScanner")) {
                mWarrantyID = args.getString("WarrantyID");
                setScanData(args.getString("response"));
            } else {
                setScanData(args.getString("response"));
            }
            setViewData();
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_details;
    }

    private void setScanData(String response) {
        final JSONObject object = JSON.parseObject(response);
        mIsSale = object.getBoolean("IsSale");
        mStrArea = object.getString("Area");
        mStrAgency = object.getString("Agency");
        mStrProductName = object.getString("ProductName");
        mStrGP = object.getString("GP");
        mStrProductNumber = object.getString("ProductNumber");
        mStrGP = object.getString("GP");
        JSONObject warrantyDetailObject = JSON.parseObject(object.getString("WarrantyDetail"));
        setStringData(warrantyDetailObject);
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    /**
     * 整车
     */
    @OnClick(R2.id.ck_sale_part_whole_vehicle)
    void wholevehicle() {
        mCKWholeVehicle.toggle();
    }

    /**
     * 左车身套餐
     */
    @OnClick(R2.id.ck_sale_part_left_only)
    void left_only() {
        mCKLeftOnly.toggle();
    }

    /**
     * 右车身套餐
     */
    @OnClick(R2.id.ck_sale_part_right_only)
    void right_only() {
        mCKRightOnly.toggle();
    }

    /**
     * 前车身套餐
     */
    @OnClick(R2.id.ck_sale_part_front_only)
    void front_only() {
        mCKFrontOnly.toggle();
    }

    /**
     * 前杠
     */
    @OnClick(R2.id.ck_sale_part_front_bumper_kit)
    void front_bumper_kit() {
        mCKFrontBumperKit.toggle();
    }

    /**
     * 后杠
     */
    @OnClick(R2.id.ck_sale_part_rear_bumper_kit)
    void rear_bumper_kit() {
        mCKRearBumperKit.toggle();
    }

    /**
     * 机盖
     */
    @OnClick(R2.id.ck_sale_part_hood_kit)
    void hood_kit() {
        mCKHoodKit.toggle();
    }

    @OnClick(R2.id.ck_sale_part_front_fender_kit_right_only)
    void front_fender_kit_right_only() {
        mCKFrontFenderKitRightOnly.toggle();
    }

    @OnClick(R2.id.ck_sale_part_front_fender_kit_left_only)
    void front_fender_kit_left_only() {
        mCKFrontFenderKitLeftOnly.toggle();
    }

    /**
     * 右后叶子板
     */
    @OnClick(R2.id.ck_sale_part_rear_fender_kit_right_only)
    void rear_fender_kit_right_only() {
        mCKRearFenderKitRightOnly.toggle();
    }

    /**
     * 左后叶子板
     */
    @OnClick(R2.id.ck_sale_part_rear_fender_kit_left_only)
    void rear_fender_kit_left_only() {
        mCKRearFenderKitLeftOnly.toggle();
    }

    /**
     * 左门组合
     */
    @OnClick(R2.id.ck_sale_part_door_kit_left_only)
    void door_kit_left_only() {
        mCKDoorKitLeftOnly.toggle();
    }

    /**
     * 右门组合
     */
    @OnClick(R2.id.ck_sale_part_door_kit_right_only)
    void door_kit_right_only() {
        mCKDoorKitRightOnly.toggle();
    }

    /**
     * 右侧裙
     */
    @OnClick(R2.id.ck_sale_part_rocker_panel_kit_right_only)
    void rocker_panel_kit_right_only() {
        mCKRockerPanelKitRightOnly.toggle();
    }

    /**
     * 左侧裙
     */
    @OnClick(R2.id.ck_sale_part_rocker_panel_kit_left_only)
    void rocker_panel_kit_left_only() {
        mCKRockerPanelKitLeftOnly.toggle();
    }

    /**
     * 车顶
     */
    @OnClick(R2.id.ck_sale_part_roof_kit)
    void roof_kit() {
        mCKRoofKit.toggle();
    }

    /**
     * 后盖组合
     */
    @OnClick(R2.id.ck_sale_part_trunk_lid_kit)
    void trunk_lid_kit() {
        mCKTrunkLidKit.toggle();
    }

    /**
     * 后视镜
     */
    @OnClick(R2.id.ck_sale_part_mirror_kit)
    void mirror_kit() {
        mCKMirrorKit.toggle();
    }

    /**
     * 质保时间
     */
    @OnClick(R2.id.tv_exp)
    void tv_exp() {
        if (!mIsSale) {
            showDialog(mTVExp);
        }
    }

    /**
     * 施工时间
     */
    @OnClick(R2.id.tv_sale_time)
    void tv_sale_time() {
        if (!mIsSale) {
            showDialog(mTVSaleTime);
        }
    }

    /**
     * 提交
     */
    @OnClick(R2.id.btn_order_detail_sub)
    void btn_order_detail_sub() {
        if (checkViewData()) {
            RestClient.builder()
                    .url("Car/AddWarrantyDetail/")
                    .params("WarrantyID", mWarrantyID)
                    .params("CustomerName", mStrCustomerName)
                    .params("CustomerSex", mStrCustomerSex)
                    .params("CustomerContact", mStrCustomerContact)
                    .params("CarModel", mStrCarModel)
                    .params("CarNumber", mStrCarNumber)
                    .params("CarColor", mStrCarColor)
                    .params("StoreName", mStrStoreName)
                    .params("StoreContact", mStrStoreContact)
                    .params("SalePart", mStrSalePart)
                    .params("EXP", mStrEXP)
                    .params("SaleTime", mStrSaleTime)
                    .params("VIN", mStrVIN)
                    .params("Price", mStrPrice)
                    .params("Remarks", mStrRemarks)
                    .loader(getContext())
                    .success(this)
                    .error(this)
                    .failure(this)
                    .build()
                    .post();
        } else {
            Toast.makeText(getContext(), "请填写正确信息", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkViewData() {
        boolean isCheck = true;
        mStrCustomerName = mETCustomerName.getText().toString();
        if (mStrCustomerName.isEmpty()) {
            mETCustomerName.setError("请填写您的姓名");
            isCheck = false;
        } else {
            mETCustomerName.setError(null);
        }
        mStrCustomerSex = mETCustomerSex.getText().toString();
        mStrCustomerContact = mETCustomerContact.getText().toString();
        if (mStrCustomerContact.isEmpty() || mStrCustomerContact.length() != 11) {
            mETCustomerContact.setError("错误的手机号");
            isCheck = false;
        } else {
            mETCustomerContact.setError(null);
        }
        mStrCarModel = mETCarModel.getText().toString();
        if (mStrCarModel.isEmpty()) {
            mETCarModel.setError("请填写车型");
            isCheck = false;
        } else {
            mETCarModel.setError(null);
        }
        mStrCarNumber = mETCarNumber.getText().toString();
        if (mStrCarNumber.isEmpty()) {
            mETCarNumber.setError("请填写车牌号");
            isCheck = false;
        } else {
            mETCarNumber.setError(null);
        }
        mStrCarColor = mETCarColor.getText().toString();
        if (mStrCarColor.isEmpty()) {
            mETCarColor.setError("请填写车身颜色");
            isCheck = false;
        } else {
            mETCarColor.setError(null);
        }
        mStrVIN = mETVIN.getText().toString();
        if (mStrVIN.isEmpty()) {
            mETVIN.setError("请填写车辆识别码");
            isCheck = false;
        } else {
            mETVIN.setError(null);
        }
        mStrStoreName = mETStoreName.getText().toString();
        if (mStrStoreName.isEmpty()) {
            mETStoreName.setError("请填写施工店名");
            isCheck = false;
        } else {
            mETStoreName.setError(null);
        }
        mStrStoreContact = mETStoreContact.getText().toString();
        if (mStrStoreContact.isEmpty() || mStrStoreContact.length() != 11) {
            mETStoreContact.setError("错误的联系方式");
            isCheck = false;
        } else {
            mETStoreContact.setError(null);
        }
        mStrSalePart = getCheck();
        if (mStrSalePart.isEmpty()) {
            Toast.makeText(getContext(), "请选择施工项目", Toast.LENGTH_LONG).show();
            isCheck = false;
        }
        mStrEXP = mTVExp.getText().toString();
        if (mStrEXP.isEmpty()) {
            mTVExp.setError("请选择质保时间");
            isCheck = false;
        } else {
            mTVExp.setError(null);
        }
        mStrSaleTime = mTVSaleTime.getText().toString();
        if (mStrSaleTime.isEmpty()) {
            mTVSaleTime.setError("请选择施工时间");
            isCheck = false;
        } else {
            mTVSaleTime.setError(null);
        }
        mStrPrice = mETPrice.getText().toString();
        if (mStrPrice.isEmpty()) {
            mETPrice.setError("请填写总金额");
            isCheck = false;
        } else {
            mETPrice.setError(null);
        }
        mStrRemarks = mETRemarks.getText().toString();
        return isCheck;
    }

    private void setSearchData(String response) {
        final JSONObject object = JSON.parseObject(response);
        final JSONObject queryWarrantyListObject = JSON.parseArray(object.getString("QueryWarrantyList")).getJSONObject(0);
        mIsSale = queryWarrantyListObject.getBoolean("IsSale");
        mStrArea = queryWarrantyListObject.getString("Area");
        mStrAgency = queryWarrantyListObject.getString("Agency");
        mStrProductName = queryWarrantyListObject.getString("ProductName");
        mStrGP = queryWarrantyListObject.getString("GP");
        mStrProductNumber = queryWarrantyListObject.getString("PN");
        setStringData(queryWarrantyListObject);
    }

    private void setStringData(JSONObject object) {
        mStrCustomerName = object.getString("CustomerName");
        mStrCustomerSex = object.getString("CustomerSex");
        mStrCustomerContact = object.getString("CustomerContact");
        mStrCarModel = object.getString("CarModel");
        mStrCarNumber = object.getString("CarNumber");
        mStrCarColor = object.getString("CarColor");
        mStrVIN = object.getString("VIN");
        mStrStoreName = object.getString("StoreName");
        mStrStoreContact = object.getString("StoreContact");
        mStrEXP = object.getString("EXP");
        mStrSaleTime = object.getString("SaleTime");
        mStrPrice = object.getString("Price");
        mStrRemarks = object.getString("Remarks");
        mStrSalePart = object.getString("SalePart");
        final String[] split = mStrSalePart.split(",");
        for (String sp : split) {
            setChecked(sp);
        }
    }

    private void setViewData() {
        mTVArea.setText(mStrArea);
        mTVAgency.setText(mStrAgency);
        mTVProductName.setText(mStrProductName);
        mTVProductNumber.setText(mStrProductNumber);
        mTVGP.setText(mStrGP);
        if (mIsSale) {
            mETCustomerName.setText(mStrCustomerName);
            mETCustomerSex.setText(mStrCustomerSex);
            mETCustomerContact.setText(mStrCustomerContact);
            mETCarModel.setText(mStrCarModel);
            mETCarNumber.setText(mStrCarNumber);
            mETCarColor.setText(mStrCarColor);
            mETVIN.setText(mStrVIN);
            mETStoreName.setText(mStrStoreName);
            mETStoreContact.setText(mStrStoreContact);
            mTVExp.setText(mStrEXP);
            mTVSaleTime.setText(mStrSaleTime);
            mETPrice.setText(mStrPrice);
            mETRemarks.setText(mStrRemarks);
        } else {
            mAllChecked = new ArrayList<>();
            mAllChecked.add(mCKWholeVehicle);
            mAllChecked.add(mCKLeftOnly);
            mAllChecked.add(mCKRightOnly);
            mAllChecked.add(mCKFrontOnly);
            mAllChecked.add(mCKFrontBumperKit);
            mAllChecked.add(mCKRearBumperKit);
            mAllChecked.add(mCKHoodKit);
            mAllChecked.add(mCKFrontFenderKitRightOnly);
            mAllChecked.add(mCKFrontFenderKitLeftOnly);
            mAllChecked.add(mCKRearFenderKitRightOnly);
            mAllChecked.add(mCKRearFenderKitLeftOnly);
            mAllChecked.add(mCKDoorKitLeftOnly);
            mAllChecked.add(mCKDoorKitRightOnly);
            mAllChecked.add(mCKRockerPanelKitRightOnly);
            mAllChecked.add(mCKRockerPanelKitLeftOnly);
            mAllChecked.add(mCKRoofKit);
            mAllChecked.add(mCKTrunkLidKit);
            mAllChecked.add(mCKMirrorKit);
        }
        setViewEnabled(!mIsSale);
    }

    /**
     * 设置是否可编辑
     *
     * @param isFocusable
     */
    private void setViewEnabled(boolean isFocusable) {
        mETCustomerName.setEnabled(isFocusable);
        mETCustomerSex.setEnabled(isFocusable);
        mETCustomerContact.setEnabled(isFocusable);
        mETCarModel.setEnabled(isFocusable);
        mETCarNumber.setEnabled(isFocusable);
        mETCarColor.setEnabled(isFocusable);
        mETVIN.setEnabled(isFocusable);
        mETStoreName.setEnabled(isFocusable);
        mETStoreContact.setEnabled(isFocusable);
        mETPrice.setEnabled(isFocusable);
        mETRemarks.setEnabled(isFocusable);
        mCKWholeVehicle.setEnabled(isFocusable);
        mCKLeftOnly.setEnabled(isFocusable);
        mCKRightOnly.setEnabled(isFocusable);
        mCKFrontOnly.setEnabled(isFocusable);
        mCKFrontBumperKit.setEnabled(isFocusable);
        mCKRearBumperKit.setEnabled(isFocusable);
        mCKHoodKit.setEnabled(isFocusable);
        mCKFrontFenderKitRightOnly.setEnabled(isFocusable);
        mCKFrontFenderKitLeftOnly.setEnabled(isFocusable);
        mCKRearFenderKitRightOnly.setEnabled(isFocusable);
        mCKRearFenderKitLeftOnly.setEnabled(isFocusable);
        mCKDoorKitLeftOnly.setEnabled(isFocusable);
        mCKDoorKitRightOnly.setEnabled(isFocusable);
        mCKRockerPanelKitRightOnly.setEnabled(isFocusable);
        mCKRockerPanelKitLeftOnly.setEnabled(isFocusable);
        mCKRoofKit.setEnabled(isFocusable);
        mCKTrunkLidKit.setEnabled(isFocusable);
        mCKMirrorKit.setEnabled(isFocusable);
        mTVExp.setEnabled(isFocusable);
        mTVSaleTime.setEnabled(isFocusable);
        mCKWholeVehicle.setFocusable(isFocusable);
        mCKLeftOnly.setFocusable(isFocusable);
        mCKRightOnly.setFocusable(isFocusable);
        mCKFrontOnly.setFocusable(isFocusable);
        mCKFrontBumperKit.setFocusable(isFocusable);
        mCKRearBumperKit.setFocusable(isFocusable);
        mCKHoodKit.setFocusable(isFocusable);
        mCKFrontFenderKitRightOnly.setFocusable(isFocusable);
        mCKFrontFenderKitLeftOnly.setFocusable(isFocusable);
        mCKRearFenderKitRightOnly.setFocusable(isFocusable);
        mCKRearFenderKitLeftOnly.setFocusable(isFocusable);
        mCKDoorKitLeftOnly.setFocusable(isFocusable);
        mCKDoorKitRightOnly.setFocusable(isFocusable);
        mCKRockerPanelKitRightOnly.setFocusable(isFocusable);
        mCKRockerPanelKitLeftOnly.setFocusable(isFocusable);
        mCKRoofKit.setFocusable(isFocusable);
        mCKTrunkLidKit.setFocusable(isFocusable);
        mCKMirrorKit.setFocusable(isFocusable);
        mBtnSub.setVisibility(isFocusable ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取所有用户选中的复选框
     *
     * @return
     */
    private String getCheck() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < mAllChecked.size(); i++) {
            if (mAllChecked.get(i).isChecked()) {
                if (i == mAllChecked.size() - 1)
                    s.append(mAllChecked.get(i).getText().toString());
                else
                    s.append(mAllChecked.get(i).getText().toString()).append(",");
            }
        }
        return s.toString();
    }

    /**
     * 弹出时间选择框
     *
     * @param tv
     */
    private void showDialog(final AppCompatTextView tv) {
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(getContext(),
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        String mon = monthOfYear < 9 ? "0" + monthOfYear + "" : monthOfYear + "";
                        String day = dayOfMonth < 9 ? "0" + dayOfMonth + "" : dayOfMonth + "";
                        tv.setText(year + "/" + mon + "/" + day);
                    }
                }
                // 设置初始日期
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setChecked(String s) {
        switch (s) {
            case "整车":
                mCKWholeVehicle.setChecked(true);
                break;
            case "左车身套餐":
                mCKLeftOnly.setChecked(true);
                break;
            case "右车身套餐":
                mCKRightOnly.setChecked(true);
                break;
            case "前车身套餐":
                mCKFrontOnly.setChecked(true);
                break;
            case "前杠":
                mCKFrontBumperKit.setChecked(true);
                break;
            case "后杠":
                mCKRearBumperKit.setChecked(true);
                break;
            case "机盖":
                mCKHoodKit.setChecked(true);
                break;
            case "右前叶子板":
                mCKFrontFenderKitRightOnly.setChecked(true);
                break;
            case "左前叶子板":
                mCKFrontFenderKitLeftOnly.setChecked(true);
                break;
            case "右后叶子板":
                mCKRearFenderKitRightOnly.setChecked(true);
                break;
            case "左后叶子板":
                mCKRearFenderKitLeftOnly.setChecked(true);
                break;
            case "左门组合":
                mCKDoorKitLeftOnly.setChecked(true);
                break;
            case "右门组合":
                mCKDoorKitRightOnly.setChecked(true);
                break;
            case "右侧裙":
                mCKRockerPanelKitRightOnly.setChecked(true);
                break;
            case "左侧裙":
                mCKRockerPanelKitLeftOnly.setChecked(true);
                break;
            case "车顶":
                mCKRoofKit.setChecked(true);
                break;
            case "后盖组合":
                mCKTrunkLidKit.setChecked(true);
                break;
            case "后视镜":
                mCKMirrorKit.setChecked(true);
                break;
            default:
                break;
        }

    }

    @Override
    public void onError(int code, String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String response) {
        _mActivity.onBackPressed();
        Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
    }
}
