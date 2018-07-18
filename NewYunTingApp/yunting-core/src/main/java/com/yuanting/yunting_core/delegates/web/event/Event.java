package com.yuanting.yunting_core.delegates.web.event;

import android.content.Context;

import com.yuanting.yunting_core.delegates.LatteDelegate;

/**
 * Created on 2018/5/18 10:27
 * Created by 薛立民
 * TEL 13262933389
 */
public abstract class Event implements IEvent {
    private Context mContext = null;
    private String mAction = null;
    private LatteDelegate mDelegate = null;
    private String mUrl = null;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(LatteDelegate delegate) {
        this.mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
