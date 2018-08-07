package com.yuanting.latte.ec.main.index.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/6 16:20
 * Created by 薛立民
 * TEL 13262933389
 */
public class PictureDelegate extends LatteDelegate implements PictureItemOnClick {
    @BindView(R2.id.rv_picture_v)
    RecyclerView mRVPicture;
    private PictureAdapter mAdapter;
    private PictureConverter mConverter;

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mConverter = new PictureConverter();
        mConverter.setContext(getActivity());
        mAdapter = new PictureAdapter(mConverter.convert());
        mAdapter.setItemOnClick(this);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRVPicture.setLayoutManager(manager);
        mRVPicture.setAdapter(mAdapter);

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_picture;
    }

    @Override
    public void showPictureDetail(MultipleItemEntity entity) {
        final Bundle bundle = new Bundle();
        final PictureDetailsDelegate detailsDelegate = new PictureDetailsDelegate();
        bundle.putString(MultipleFields.IMAGE_URL.name(), entity.getField(MultipleFields.IMAGE_URL).toString());
        detailsDelegate.setArguments(bundle);
        getSupportDelegate().start(detailsDelegate);
    }
}
