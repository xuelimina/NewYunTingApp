package com.yuanting.yunting_core.net.firupdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.IRequest;
import com.yuanting.yunting_core.net.callback.ISuccess;

/**
 * Created on 2018/7/27 13:05
 * Created by 薛立民
 * TEL 13262933389
 */
public class FirUpdateUtils {

    private FirUpdateUtils() {
    }

    private static class Holder {
        private static final FirUpdateUtils INSTANCE = new FirUpdateUtils();
    }

    public static FirUpdateUtils getInstance() {
        return Holder.INSTANCE;
    }

    public void updateDialog(final JSONObject updateJson) {
        if (updateJson != null) {
            final int updateVersion = updateJson.getInteger("version");
            final int localVersion = Latte.getConfiguration(ConfigKeys.APP_CODE);
            if (updateVersion > localVersion) {
                new AlertDialog.Builder((Activity) Latte.getConfiguration(ConfigKeys.ACTIVITY))
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDownloadDialog(updateJson.getJSONObject("binary").getInteger("fsize")
                                        , updateJson.getString("installUrl"));
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity) Latte.getConfiguration(ConfigKeys.ACTIVITY)).finish();
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .setMessage("有最新版本需要更新，请下载更新")
                        .show();
            }
        }
    }

    /**
     * 显示下载进度对话框
     */
    private void showDownloadDialog(int max, String installUrl) {
        final ProgressDialog progressDialog = new ProgressDialog((Activity) Latte.getConfiguration(ConfigKeys.ACTIVITY));
        progressDialog.setTitle("正在下载最新版本...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMax(max);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downApk(installUrl, progressDialog);
    }

    private void downApk(String url, final ProgressDialog progressDialog) {
        RestClient.builder().url(url)
                .dir("re").extension("apk")
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onRequestDowning(final int process) {
                        new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.incrementProgressBy(process);
                            }
                        }.run();

                    }

                    @Override
                    public void onRequestEnd() {
                        ((Activity) Latte.getConfiguration(ConfigKeys.ACTIVITY)).finish();
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
                progressDialog.dismiss();
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                progressDialog.dismiss();
            }
        }).build().download();

    }
}
