package com.yuanting.yunting_core.ui.camera;

import android.net.Uri;

/**
 * Created on 2018/7/12 11:26
 * Created by 薛立民
 * TEL 13262933389
 */
public class CameraImageBean {

    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
