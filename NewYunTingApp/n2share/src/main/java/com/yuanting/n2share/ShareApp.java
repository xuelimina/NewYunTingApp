package com.yuanting.n2share;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.icon.FontYTModule;

/**
 * Created on 2018/4/17 15:07
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShareApp extends MultiDexApplication {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

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
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontYTModule())
                .withLoaderDelayed(500)
                .withWeChatAppID("wxc82e786b61ed760e").withWeChatAppSecret("69ba1237fc0f06d62776671102f19392")
//                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withJavascriptInterface("latte")
                .configure();
//        DatabaseManager.getInstance().init(this);
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
