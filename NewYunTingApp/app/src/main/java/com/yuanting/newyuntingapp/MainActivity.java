package com.yuanting.newyuntingapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.dpfppu.main.DpfppuBottomDelegate;
import com.yuanting.latte.ec.main.ShuNaiTeBottomDelegate;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.n2erp.main.ERPBottomDelegate;
import com.yuanting.n2erp.sign.SignInDelegate;
import com.yuanting.nomisdun.main.NoMisDunIndexDelegate;
import com.yuanting.yunting_core.activites.ProxyActivity;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.laucher.ILauncherListener;
import com.yuanting.yunting_core.ui.laucher.OnLauncherFinishTag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.jzvd.JZVideoPlayer;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity implements ILauncherListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        final String applicationId = getApplication().getPackageName();
        switch (applicationId) {
            case PackageType.SHUNAITE:
                return new ShuNaiTeBottomDelegate();
            case PackageType.NOMISDUN:
                return new NoMisDunIndexDelegate();
            case PackageType.DPFPPU:
                return new DpfppuBottomDelegate();
            case PackageType.ERP:
                if (AccountManager.isSignIn()) {
                    updateUserInfo();
                    return new ERPBottomDelegate();
                } else {
                    return new SignInDelegate();
                }
        }
        return null;
    }

    private void updateUserInfo() {
        RestClient.builder().url("Landing?")
                .params("username", AccountManager.getUserName())
                .params("pwd", AccountManager.getPassword())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (response.equals("1")) {
                            final JSONObject profileJson = JSON.parseArray(JsonUtils.getDecodeJSONStr(response)).getJSONObject(0);
                            final String UserID = profileJson.getString("UserID");
                            final String UserName = profileJson.getString("UserName");
                            final String RegistTime = profileJson.getString("RegistTime");
                            final String Password = profileJson.getString("Password");
                            final String Permissions = profileJson.getString("Permissions");
                            final String TextInfo = profileJson.getString("TextInfo");
                            final String UserInfos = profileJson.getString("UserInfos");
                            AccountManager.setID(UserID);
                            AccountManager.setUserName(UserName);
                            AccountManager.setRegistTime(RegistTime);
                            AccountManager.setPassword(Password);
                            AccountManager.setPermissions(Permissions);
                            AccountManager.setTextInfo(TextInfo);
                            AccountManager.setCreateTime(UserInfos);
                            if (UserInfos.contains("<Number>"))
                                AccountManager.setNumber(UserInfos.substring(UserInfos.indexOf("<Number>") + "<Number>".length(), UserInfos.indexOf("</Number>")));
                            if (UserInfos.contains("<Address>"))
                                AccountManager.setCompanyAddress(UserInfos.substring(UserInfos.indexOf("<Address>") + "<Address>".length(), UserInfos.indexOf("</Address>")));
                            if (UserInfos.contains("<Name>"))
                                AccountManager.setCompanyName(UserInfos.substring(UserInfos.indexOf("<Name>") + "<Name>".length(), UserInfos.indexOf("</Name>")));
                            if (UserInfos.contains("<Owner>"))
                                AccountManager.setOwner(UserInfos.substring(UserInfos.indexOf("<Owner>") + "<Owner>".length(), UserInfos.indexOf("</Owner>")));

                            if (UserInfos.contains("<Lev>")) {
                                String Lev = UserInfos.substring(UserInfos.indexOf("<Lev>") + "<Lev>".length(), UserInfos.indexOf("</Lev>"));
                                String newUserInfo = UserInfos.substring(UserInfos.indexOf("</Lev>") + "<Lev>".length(), UserInfos.length());
                                if (newUserInfo.contains("<Lev>")) {
                                    Lev = Lev + "," + newUserInfo.substring(newUserInfo.indexOf("<Lev>") + "<Lev>".length(), newUserInfo.indexOf("</Lev>"));
                                }
                                AccountManager.setLev(Lev);
                            }
                            if (UserInfos.contains("<FinnalDate>")) {
                                String fiannaDate = UserInfos.substring(UserInfos.indexOf("<FinnalDate>") + "<FinnalDate>".length(), UserInfos.indexOf("</FinnalDate>"));
                                AccountManager.setFinnalDate(fiannaDate);
                            }
                            if (UserInfos.contains("<Mony>"))
                                AccountManager.setMony(UserInfos.substring(UserInfos.indexOf("<Mony>") + "<Mony>".length(), UserInfos.indexOf("</Mony>")));
                            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// HH:mm:ss//获取当前时间
                            String fiannaDate = AccountManager.getFinnalDate();
                            if (fiannaDate.contains("/"))
                                fiannaDate = fiannaDate.replace("/", "-") + " 23:59:59";
                            Date date = null;
                            try {
                                date = simpleDateFormat.parse(fiannaDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Calendar calendar = Calendar.getInstance();
                            Calendar fiannaCalendar = Calendar.getInstance();
                            fiannaCalendar.setTime(date);
                            if (calendar.after(fiannaCalendar)) {
                                showAlertDialog(MainActivity.this,"账号已过期，请联系管理员续费");
                            }
                        }
                    }
                }).build().get();
    }

    private void showAlertDialog(Activity activity, String message) {
        new AlertDialog.Builder(activity)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                getSupportDelegate().startWithPop(new ShuNaiTeBottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
