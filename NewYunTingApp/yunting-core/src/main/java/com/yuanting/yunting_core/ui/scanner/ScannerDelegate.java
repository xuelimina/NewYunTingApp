package com.yuanting.yunting_core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.yunting_core.R;
import com.yuanting.yunting_core.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.util.callback.CallbackManager;
import com.yuanting.yunting_core.util.callback.CallbackType;
import com.yuanting.yunting_core.util.callback.IGlobalCallback;

import butterknife.BindView;
import butterknife.OnClick;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created on 2018/7/12 15:02
 * Created by 薛立民
 * TEL 13262933389
 */
public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {
    @BindView(R2.id.scan_view)
    ScanView mScanView;

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_scanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView != null) {
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked") final IGlobalCallback<String> callback = CallbackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
