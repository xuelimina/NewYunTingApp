package com.yuanting.latte.ec.main.shunaiteProduct;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
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
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.ui.loader.LatteLoader;
import com.yuanting.yunting_ec.R;
import com.yuanting.yunting_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/27 16:38
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShuNaiteProduct extends LatteDelegate {
    @BindView(R2.id.product_image_view)
    AppCompatImageView mShuNaiteImageView;
    @BindView(R2.id.btn_n)
    AppCompatButton mBtnN = null;
    @BindView(R2.id.btn_t)
    AppCompatButton mBtnT = null;
    private String mProductImageUrl;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        LatteLoader.showLoading(getContext());
        mProductImageUrl = Latte.getConfiguration(ConfigKeys.API_HOST) + "Img/product1.png";
        mBtnN.setTextColor(getResources().getColor(android.R.color.white));
        mBtnN.setBackgroundResource(R.color.app_main_background);
        mBtnT.setTextColor(getResources().getColor(android.R.color.black));
        mBtnT.setBackgroundResource(R.drawable.product_button_bg);
        showProductImage(mProductImageUrl, mShuNaiteImageView);
    }

    @OnClick(R2.id.btn_n)
    void interiorBtnOnClick() {
        LatteLoader.showLoading(getContext());
        mProductImageUrl = Latte.getConfiguration(ConfigKeys.API_HOST) + "Img/product1.png";
        mBtnN.setTextColor(getResources().getColor(android.R.color.white));
        mBtnN.setBackgroundResource(R.color.app_main_background);
        mBtnT.setTextColor(getResources().getColor(android.R.color.black));
        mBtnT.setBackgroundResource(R.drawable.product_button_bg);
        showProductImage(mProductImageUrl, mShuNaiteImageView);
    }

    @OnClick(R2.id.btn_t)
    void externalBtnOnClick() {
        LatteLoader.showLoading(getContext());
        mProductImageUrl = Latte.getConfiguration(ConfigKeys.API_HOST) + "Img/product2.png";
        mBtnN.setTextColor(getResources().getColor(android.R.color.black));
        mBtnN.setBackgroundResource(R.drawable.product_button_bg);
        mBtnT.setTextColor(getResources().getColor(android.R.color.white));
        mBtnT.setBackgroundResource(R.color.app_main_background);
        showProductImage(mProductImageUrl, mShuNaiteImageView);
    }

    private void showProductImage(String url, final AppCompatImageView imageView) {
        RequestOptions RECYCLER_OPTIONS =
                new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .fitCenter()
                        .dontAnimate();
        Glide.with(getContext())
                .load(url)
                .apply(RECYCLER_OPTIONS)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                        int vwidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        int vheight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        imageView.measure(vwidth, vheight);
                        int mheight = imageView.getMeasuredHeight();
                        int mwidth = imageView.getMeasuredWidth();
                        fitImage(_mActivity, imageView, mwidth, mheight);
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

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shunaite_product;
    }
}
