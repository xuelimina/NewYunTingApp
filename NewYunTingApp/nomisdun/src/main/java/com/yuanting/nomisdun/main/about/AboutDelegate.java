package com.yuanting.nomisdun.main.about;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.loader.LatteLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/7/19 13:07
 * Created by 薛立民
 * TEL 13262933389
 */
public class AboutDelegate extends LatteDelegate{
    @BindView(R2.id.company_image_view)
    AppCompatImageView mCompanyImageView;
    @OnClick(R2.id.about_back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LatteLoader.showLoading(getContext());
        final String companyImageUrl = Latte.getConfiguration(ConfigKeys.API_HOST) + "Img/company.png";
        RequestOptions RECYCLER_OPTIONS =
                new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .fitCenter()
                        .dontAnimate();
        Glide.with(getContext())
                .load(companyImageUrl)
                .apply(RECYCLER_OPTIONS)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mCompanyImageView.setImageDrawable(resource);
                        int vwidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        int vheight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mCompanyImageView.measure(vwidth, vheight);
                        int mheight = mCompanyImageView.getMeasuredHeight();
                        int mwidth = mCompanyImageView.getMeasuredWidth();
                        fitImage(_mActivity, mCompanyImageView, mwidth, mheight);
                        LatteLoader.stopLoading();
                    }
                });
    }

    /**
     * 根据图片大小按比例适配全屏
     *
     * @param imageView
     * @param picWidth
     * @param picHeight
     */

    private void fitImage(Activity activity, ImageView imageView, float picWidth, float picHeight) {
        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        float height = (float) width / picWidth * picHeight;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = (int) height;
        imageView.setLayoutParams(layoutParams);
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_about;
    }
}
