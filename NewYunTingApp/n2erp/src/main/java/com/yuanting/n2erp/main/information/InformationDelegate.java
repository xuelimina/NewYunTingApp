package com.yuanting.n2erp.main.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

import butterknife.BindView;

/**
 * Created on 2018/8/22 17:39
 * Created by 薛立民
 * TEL 13262933389
 */
public class InformationDelegate extends BottomItemDelegate {
    @BindView(R2.id.rv_system_news)
    RecyclerView mRvSystemNews;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        GetSystemNews();
    }

    private void GetSystemNews() {
        RestClient.builder().url("GetSystemNews")
                .loader(getContext())
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("error", msg);
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("response", "onFailure");
                    }
                }).build().get();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_information_erp;
    }
}
