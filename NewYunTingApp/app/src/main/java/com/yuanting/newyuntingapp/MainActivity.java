package com.yuanting.newyuntingapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

import com.yuanting.dpfppu.main.DpfppuBottomDelegate;
import com.yuanting.latte.ec.main.ShuNaiTeBottomDelegate;
import com.yuanting.n2erp.main.ERPBottomDelegate;
import com.yuanting.n2erp.sign.SignInDelegate;
import com.yuanting.nomisdun.main.NoMisDunIndexDelegate;
import com.yuanting.yunting_core.activites.ProxyActivity;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
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
                        new AlertDialog.Builder(this)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setCancelable(false)
                                .setMessage("账号已过期，请联系管理员续费")
                                .show();
                    }
                    return new ERPBottomDelegate();
                } else {
                    return new SignInDelegate();
                }
        }
        return null;
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
