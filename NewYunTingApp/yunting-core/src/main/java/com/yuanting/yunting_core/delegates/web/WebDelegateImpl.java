package com.yuanting.yunting_core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yuanting.yunting_core.delegates.web.IPageLoadListener;
import com.yuanting.yunting_core.delegates.web.IWebViewInitializer;
import com.yuanting.yunting_core.delegates.web.WebDelegate;
import com.yuanting.yunting_core.delegates.web.WebViewInitializer;
import com.yuanting.yunting_core.delegates.web.chromeclient.WebChromeClientImpl;
import com.yuanting.yunting_core.delegates.web.client.WebViewClientImpl;
import com.yuanting.yunting_core.delegates.web.route.RouteKeys;
import com.yuanting.yunting_core.delegates.web.route.Router;


/**
 * Created on 2018/5/17 16:28
 * Created by 薛立民
 * TEL 13262933389
 */
public class WebDelegateImpl extends WebDelegate {
    private IPageLoadListener mIPageLoadListener = null;
    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }
    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }
    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getWebView() != null) {
            //用原生的方式模拟web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
