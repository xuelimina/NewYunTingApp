package com.yuanting.n2erp.main.index.stockData;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.dialog.CommomDialog;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.n2erp.main.index.stockIn.ProductItemOnClick;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.date.DateDialogUtil;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/29 11:21
 * Created by 薛立民
 * TEL 13262933389
 */
public class StockInDataDelegate extends LatteDelegate implements ProductItemOnClick {
    @BindView(R2.id.rv_stock_out_data)
    RecyclerView mRecyclerView;
    @BindView(R2.id.btn_data_details)
    AppCompatButton mBtnDataDetails;
    @BindView(R2.id.btn_data_statistics)
    AppCompatButton mBtnStatisticsDetails;
    @BindView(R2.id.layout_details_title)
    LinearLayoutCompat mDetailsTitleLayout;
    @BindView(R2.id.layout_statistics_title)
    LinearLayoutCompat mStatisticsTitleLayout;
    @BindView(R2.id.et_select_product_name)
    AppCompatEditText mEtSelectName;
    @BindView(R2.id.tv_date_start)
    AppCompatTextView mTvDateStart;
    @BindView(R2.id.tv_date_end)
    AppCompatTextView mTvDateEnd;
    private StockInDataConverter mConverter = new StockInDataConverter();
    private boolean isStatistics = false;
    private String mStartStr;
    private String mEndStr;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mBtnDataDetails.setBackgroundResource(R.color.app_main_background_erp);
        mBtnStatisticsDetails.setBackgroundColor(Color.WHITE);
        mBtnDataDetails.setTextColor(Color.WHITE);
        mBtnStatisticsDetails.setTextColor(Color.DKGRAY);
        isStatistics = false;
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        mTvDateEnd.setText(format.format(calendar.getTime()));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 30);
        mTvDateStart.setText(format.format(calendar.getTime()));
        mStartStr = mTvDateStart.getText().toString();
        mEndStr = mTvDateEnd.getText().toString();
        GetEntryGroupByTime("", mStartStr, mEndStr);
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @OnClick({R2.id.layout_date_start, R2.id.layout_date_end})
    void onDateClick(View view) {
        final int id = view.getId();
        if (id == R.id.layout_date_start) {
            new DateDialogUtil().setDateListener(new DateDialogUtil.IDateListener() {
                @Override
                public void onDateChange(String date) {
                    mTvDateStart.setText(date);
                }
            }).showDialog(getContext());
        } else if (id == R.id.layout_date_end) {
            new DateDialogUtil().setDateListener(new DateDialogUtil.IDateListener() {
                @Override
                public void onDateChange(String date) {
                    mTvDateEnd.setText(date);
                }
            }).showDialog(getContext());
        }
    }

    @OnClick({R2.id.btn_data_details, R2.id.btn_data_statistics, R2.id.btn_select_product_name,})
    void onClick(View view) {
        mStartStr = mTvDateStart.getText().toString();
        mEndStr = mTvDateEnd.getText().toString();
        String selectName = mEtSelectName.getText().toString();
        if (selectName.isEmpty()) {
            selectName = "";
        }
        final int id = view.getId();
        if (id == R.id.btn_data_details) {
            mBtnDataDetails.setBackgroundResource(R.color.app_main_background_erp);
            mBtnDataDetails.setTextColor(Color.WHITE);
            mBtnStatisticsDetails.setTextColor(Color.DKGRAY);
            mBtnStatisticsDetails.setBackgroundColor(Color.WHITE);
            mDetailsTitleLayout.setVisibility(View.VISIBLE);
            mStatisticsTitleLayout.setVisibility(View.GONE);
            isStatistics = false;
            GetEntryGroupByTime(selectName, mStartStr, mEndStr);
        } else if (id == R.id.btn_data_statistics) {
            mBtnStatisticsDetails.setBackgroundResource(R.color.app_main_background_erp);
            mBtnDataDetails.setBackgroundColor(Color.WHITE);
            mDetailsTitleLayout.setVisibility(View.GONE);
            mStatisticsTitleLayout.setVisibility(View.VISIBLE);
            mBtnDataDetails.setTextColor(Color.DKGRAY);
            mBtnStatisticsDetails.setTextColor(Color.WHITE);
            isStatistics = true;
            GetEntryGroup1ByTime(selectName, mStartStr, mEndStr);
        } else if (id == R.id.btn_select_product_name) {
            if (isStatistics) {
                GetEntryGroup1ByTime(selectName, mStartStr, mEndStr);
            } else {
                GetEntryGroupByTime(selectName, mStartStr, mEndStr);
            }
        }
    }

    private void GetEntryGroup1ByTime(final String name, String StartTime, String EndTime) {
        RestClient.builder().url("GetEntryGroup1ByTime?")
                .params("owner", AccountManager.getOwner())
                .params("from", StartTime)
                .params("to", EndTime)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", JsonUtils.getDecodeJSONStr(response));
                        final ArrayList<MultipleItemEntity> entities = mConverter.getStatisticsData(JsonUtils.getDecodeJSONStr(response));
                        if (name.isEmpty()) {
                            reDataList(entities);
                        } else {
                            if (entities.size() > 0) {
                                final ArrayList<MultipleItemEntity> entities1 = new ArrayList<>();
                                for (MultipleItemEntity entity : entities) {
                                    final String nameEntiy = entity.getField(StockDataItemFields.NAME);
                                    if (nameEntiy.contains(name)) {
                                        entities1.add(entity);
                                    }
                                }
                                reDataList(entities1);
                            } else {
                                reDataList(entities);
                            }
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        reDataList(new ArrayList<MultipleItemEntity>());
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "获取数据信息失败", Toast.LENGTH_SHORT).show();
                        reDataList(new ArrayList<MultipleItemEntity>());
                    }
                }).build().get();
    }

    private void GetEntrySelect1ByTime(String name, String StartTime, String EndTime, String Unit) {
        RestClient.builder().url("GetEntrySelect1ByTime?")
                .params("Owner", AccountManager.getOwner())
                .params("from", StartTime)
                .params("to", EndTime)
                .params("Name", name)
                .params("Unit", Unit)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", JsonUtils.getDecodeJSONStr(response));
                        final CommomDialog dialog = new CommomDialog(getContext(), R.style.dialog,
                                new StockInDataAdapter(mConverter.setJsonData(JsonUtils.getDecodeJSONStr(response)).convert()));
                        dialog.show();
                        dialog.setTitle("详情");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        reDataList(new ArrayList<MultipleItemEntity>());
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "获取数据信息失败", Toast.LENGTH_SHORT).show();
                        reDataList(new ArrayList<MultipleItemEntity>());
                    }
                }).build().get();
    }

    private void GetEntryGroupByTime(String name, String StartTime, String EndTime) {
        RestClient.builder().url("GetEntryInfomationByTime?")
                .params("owner", AccountManager.getOwner())
                .params("from", StartTime)
                .params("to", EndTime)
                .params("contansname", name)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", JsonUtils.getDecodeJSONStr(response));
                        reDataList(mConverter.setJsonData(JsonUtils.getDecodeJSONStr(response)).convert());
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        reDataList(new ArrayList<MultipleItemEntity>());
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "获取数据信息失败", Toast.LENGTH_SHORT).show();
                        reDataList(new ArrayList<MultipleItemEntity>());
                    }
                }).build().get();
    }

    private void reDataList(ArrayList<MultipleItemEntity> entities) {
        mRecyclerView.removeAllViews();
        final StockInDataAdapter mAdapter = new StockInDataAdapter(entities);
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_stock_in_data_erp;
    }

    @Override
    public void productContent(MultipleItemEntity entity) {
        final String name = entity.getField(StockDataItemFields.NAME);
        final String unit = entity.getField(StockDataItemFields.UNIT);
        GetEntrySelect1ByTime(name, mStartStr, mEndStr, unit);
    }
}
