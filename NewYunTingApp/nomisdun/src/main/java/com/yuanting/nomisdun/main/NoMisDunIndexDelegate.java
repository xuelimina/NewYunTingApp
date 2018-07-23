package com.yuanting.nomisdun.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.nomisdun.main.about.AboutDelegate;
import com.yuanting.nomisdun.main.cases.SelectCaseDelegate;
import com.yuanting.nomisdun.main.orderQuery.OrderQueryDelegate;
import com.yuanting.nomisdun.main.product.SelectProductDelegate;
import com.yuanting.nomisdun.main.shopQuery.ShopQueryDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.IRequest;
import com.yuanting.yunting_core.net.callback.ISuccess;

import butterknife.OnClick;

/**
 * Created on 2018/7/19 10:11
 * Created by 薛立民
 * TEL 13262933389
 */
public class NoMisDunIndexDelegate extends BottomItemDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        checkedUpdate();
    }

    public void checkedUpdate() {
        RestClient.builder().url("http://api.fir.im/apps/latest/5b520673ca87a831f4b4050f?")
                .params("api_token", "dc4f939e27daebe8ede80a1a12fbbee3")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("onSuccess", response);

                        final JSONObject object = JSON.parseObject(response);
                        showDownloadDialog(object.getJSONObject("binary").getInteger("fsize"));
                        downApk(object.getString("installUrl"));
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getContext(), "获取信息失败" + msg + code, Toast.LENGTH_LONG).show();
                Log.i("onError", "获取信息失败" + msg + code);
            }
        }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "获取信息失败", Toast.LENGTH_LONG).show();
                Log.i("onFailure", "获取信息失败");
            }
        }).build().get();
    }

    private ProgressDialog progressDialog;

    /**
     * 显示下载进度对话框
     */
    public void showDownloadDialog(int max) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("正在下载...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMax(max);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    private void downApk(String url) {
        RestClient.builder().url(url)
                .dir("re")
                .extension("apk")
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onRequestDowning(int process) {
                        progressDialog.incrementProgressBy(process);
                    }

                    @Override
                    public void onRequestEnd() {
                        progressDialog.dismiss();
                    }
                }).failure(new IFailure() {
            @Override
            public void onFailure() {
                progressDialog.dismiss();
            }
        }).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                Log.i("downresponse", response);
                progressDialog.dismiss();
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                progressDialog.dismiss();
            }
        }).build().download();

    }

    @OnClick(R2.id.btn_about)
    void onClickAbout() {
        getSupportDelegate().start(new AboutDelegate());
    }

    @OnClick(R2.id.btn_product)
    void onClickProduct() {
        getSupportDelegate().start(new SelectProductDelegate());
    }

    @OnClick(R2.id.btn_case)
    void onClickCase() {
        getSupportDelegate().start(new SelectCaseDelegate());
    }

    @OnClick(R2.id.btn_shop_query)
    void onClickShopQuery() {
        getSupportDelegate().start(new ShopQueryDelegate());
    }

    @OnClick(R2.id.btn_order_query)
    void onClickOrderQuery() {
        getSupportDelegate().start(new OrderQueryDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_index;
    }
}
