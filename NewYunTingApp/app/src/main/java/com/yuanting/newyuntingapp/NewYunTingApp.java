package com.yuanting.newyuntingapp;

import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.yuanting.latte.ec.database.DatabaseManager;
import com.yuanting.latte.ec.icon.FontEcModule;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.net.interceptors.DebugInterceptor;

/**
 * Created on 2018/4/17 15:07
 * Created by 薛立民
 * TEL 13262933389
 */
public class NewYunTingApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(getApplicationContext()).withApiHost("https://d.carbros.cn:4433/ShuNaite/")
                .withIcons(new FontAwesomeModule())
                .withLoaderDelayed(500)
                .withWeChatAppID("微信AppKey").withWeChatAppSecret("微信AppSecret")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withJavascriptInterface("latte")
                .withIcons(new FontEcModule()).configure();
        DatabaseManager.getInstance().init(this);
        initStetho();
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }
}
