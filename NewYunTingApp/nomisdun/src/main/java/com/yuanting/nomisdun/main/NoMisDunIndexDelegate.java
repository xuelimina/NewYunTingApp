package com.yuanting.nomisdun.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.nomisdun.main.about.AboutDelegate;
import com.yuanting.nomisdun.main.cases.SelectCaseDelegate;
import com.yuanting.nomisdun.main.orderQuery.OrderQueryDelegate;
import com.yuanting.nomisdun.main.product.SelectProductDelegate;
import com.yuanting.nomisdun.main.shopQuery.ShopQueryDelegate;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.net.firupdate.FirUpdateUtils;

import java.util.Arrays;

import butterknife.OnClick;

/**
 * Created on 2018/7/19 10:11
 * Created by 薛立民
 * TEL 13262933389
 */
public class NoMisDunIndexDelegate extends BottomItemDelegate {
    private FirUpdateUtils firUpdateUtils;
    private JSONObject updateJson;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        firUpdateUtils = FirUpdateUtils.getInstance();
        isUpdate();
    }

    public void isUpdate() {
        RestClient.builder().url("http://api.fir.im/apps/latest/" + Latte.getConfiguration(ConfigKeys.FIR_APP_ID) + "?")
                .params("api_token", Latte.getConfiguration(ConfigKeys.FIR_API_TOKEN))
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        updateJson = JSON.parseObject(response);
                        final int updateVersion = updateJson.getInteger("version");
                        final int localVersion = Latte.getConfiguration(ConfigKeys.APP_CODE);
                        if (updateVersion > localVersion) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                final boolean isRead = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == 0;
                                final boolean isWrite = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0;
                                if (!isRead && !isWrite) {
                                    readFileWithCheck();
                                } else {
                                    firUpdateUtils.updateDialog(updateJson);
                                }
                            } else {
                                firUpdateUtils.updateDialog(updateJson);
                            }
                        }
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
            }
        }).failure(new IFailure() {
            @Override
            public void onFailure() {
            }
        }).build().get();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("grantResults", Arrays.toString(grantResults));
        if (grantResults[0] == 0) {
            firUpdateUtils.updateDialog(updateJson);
        } else if (grantResults[0] == -1) {
            new AlertDialog.Builder(getActivity())
                    .setPositiveButton("去应用管理中设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            getActivity().finish();
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("有新版本需要更新需要存储权限，否则无法使用使用")
                    .show();
        }
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
