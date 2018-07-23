package com.yuanting.nomisdun.main.cases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/7/23 15:07
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShowItemPictureDelegate extends LatteDelegate {
    @BindView(R2.id.image_picture_case)
    AppCompatImageView mPictureRy;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            RequestOptions RECYCLER_OPTIONS = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate();
            Glide.with(getContext())
                    .load(args.getString("IMAGE_URL"))
                    .apply(RECYCLER_OPTIONS)
                    .into(mPictureRy);
        }
    }

    @OnClick(R2.id.about_back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_show_picture_content;
    }

}
