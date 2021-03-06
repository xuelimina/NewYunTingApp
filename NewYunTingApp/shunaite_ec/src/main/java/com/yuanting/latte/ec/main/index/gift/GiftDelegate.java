package com.yuanting.latte.ec.main.index.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuanting.latte.ec.main.index.picture.PictureDetailsDelegate;
import com.yuanting.latte.ec.main.index.picture.PictureItemOnClick;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/3 13:59
 * Created by 薛立民
 * TEL 13262933389
 */
public class GiftDelegate extends LatteDelegate implements PictureItemOnClick {
    @BindView(R2.id.rv_gift_picture)
    RecyclerView recyclerView;
    private GiftAdapter mAdapter;
    private GiftPictureConverter mConverter;
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mConverter = new GiftPictureConverter();
        mConverter.setContext(getActivity());
        mAdapter = new GiftAdapter(mConverter.convert());
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_gift_picture;
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
