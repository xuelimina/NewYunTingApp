package com.yuanting.newyuntingapp;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
        String server_url = "";
        String fir_appid = "";
        String fir_api_token = "";
        int appCode = 0;
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            server_url = appInfo.metaData.getString("server_url");
            fir_appid = appInfo.metaData.getString("fir_appid");
            fir_api_token = appInfo.metaData.getString("fir_api_token");
            appCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Latte.init(getApplicationContext()).withApiHost(server_url)
                .withFirAppID(fir_appid)
                .withFirApiToken(fir_api_token)
                .withAppCode(appCode)
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
