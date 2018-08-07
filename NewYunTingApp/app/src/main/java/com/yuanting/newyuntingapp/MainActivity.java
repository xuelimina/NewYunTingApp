package com.yuanting.newyuntingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.yuanting.dpfppu.main.DpfppuBottomDelegate;
import com.yuanting.latte.ec.main.ShuNaiTeBottomDelegate;
import com.yuanting.latte.ec.sign.SignInDelegate;
import com.yuanting.nomisdun.main.NoMisDunIndexDelegate;
import com.yuanting.yunting_core.activites.ProxyActivity;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.laucher.ILauncherListener;
import com.yuanting.yunting_core.ui.laucher.OnLauncherFinishTag;

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
