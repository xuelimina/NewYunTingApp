package com.yuanting.nomisdun.main.cases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/7/23 15:07
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShowPictureCaseDelegate extends LatteDelegate implements PictureItemOnClick {
    @BindView(R2.id.ry_picture_case)
    RecyclerView mPictureRy;
    private PictureAdapter mAdapter;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        mPictureRy.setLayoutManager(manager);
        mAdapter = new PictureAdapter(new PictureConverter().convert());
        mAdapter.setPictureItemOnClick(this);
        mPictureRy.setAdapter(mAdapter);
    }

    @OnClick(R2.id.about_back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_show_picture;
    }

    @Override
    public void pictureItemOnClick(MultipleItemEntity entity) {
        final ShowItemPictureDelegate delegate = new ShowItemPictureDelegate();
        final Bundle bundle = new Bundle();
        bundle.putString("IMAGE_URL", entity.getField(MultipleFields.IMAGE_URL).toString());
        delegate.setArguments(bundle);
        getSupportDelegate().start(delegate);
    }
}
