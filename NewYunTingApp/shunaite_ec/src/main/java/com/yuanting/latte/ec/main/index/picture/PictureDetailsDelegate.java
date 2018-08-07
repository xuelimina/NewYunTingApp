package com.yuanting.latte.ec.main.index.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/6 17:46
 * Created by 薛立民
 * TEL 13262933389
 */
public class PictureDetailsDelegate extends LatteDelegate {
    @BindView(R2.id.iv_picture_detail)
    AppCompatImageView imageView;
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            final String url = args.getString(MultipleFields.IMAGE_URL.name());
            final RequestOptions options = new RequestOptions().centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate();
            Glide.with(getActivity()).load(url).apply(options).into(imageView);
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_picture_detail;
    }
}
