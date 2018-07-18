package com.yuanting.yunting_core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.delegates.web.WebDelegate;
import com.yuanting.yunting_core.delegates.web.WebDelegateImpl;

/**
 * Created on 2018/5/17 16:41
 * Created by 薛立民
 * TEL 13262933389
 */
public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router ROUTER = new Router();
    }

    public static Router getInstance() {
        return Holder.ROUTER;
    }

    public final boolean handleWebUrl(WebDelegate delegate, String url) {
        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }
        final LatteDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.getSupportDelegate().start(webDelegate);
        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null");
        }
    }

    private void loadLocalWebPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalWebPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate webView, String url) {
        loadPage(webView.getWebView(), url);
    }


    private void callPhone(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(url);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);

    }
}
