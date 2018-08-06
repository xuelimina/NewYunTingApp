package com.yuanting.yunting_core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.web.IPageLoadListener;
import com.yuanting.yunting_core.delegates.web.WebDelegate;
import com.yuanting.yunting_core.ui.loader.LatteLoader;


/**
 * Created on 2018/5/17 16:38
 * Created by 薛立民
 * TEL 13262933389
 */
public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener;
    private Handler handler = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        LatteLogger.d("shouldOverrideUrlLoading", url);
//        return Router.getInstance().handleWebUrl(DELEGATE, url);
//    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);
    }
}
