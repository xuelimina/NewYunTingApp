package com.yuanting.latte.ec.main.index;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;
import com.yuanting.yunting_ec.R;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created on 2018/6/21 17:49
 * Created by 薛立民
 * TEL 13262933389
 */
public class IndexAdapter extends MultipleRecyclerAdapter {
    private IndexMenuClickListener menuClickListener = null;

    protected IndexAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.HORIZONTAL_MENU_LIST, R.layout.item_multiple_menu);
        addItemType(ItemType.VIDEO, R.layout.item_multiple_video);
        addItemType(ItemType.PICTURE, R.layout.item_multiple_picture);
    }

    public void setMenuClickListener(@NonNull IndexMenuClickListener listener) {
        this.menuClickListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case ItemType.HORIZONTAL_MENU_LIST:
                final LinearLayoutCompat distributionInquiries = holder.getView(R.id.distribution_inquiries_view);
                final LinearLayoutCompat shunaiteProduct = holder.getView(R.id.shunaite_product_view);
                final LinearLayoutCompat productPrice = holder.getView(R.id.product_price_view);
                final LinearLayoutCompat onlineView = holder.getView(R.id.online_view);
                final LinearLayoutCompat gift = holder.getView(R.id.gift_view);
                final LinearLayoutCompat identification = holder.getView(R.id.identification_view);
                distributionInquiries.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.shopQueryStart();

                    }
                });
                shunaiteProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.shuNaiteProductStart();
                    }
                });
                productPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.productPriceStart();
                    }
                });
                gift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.giftStart();
                    }
                });
                onlineView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.onlineStar();
                    }
                });
                identification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.orderQueryStart();
                    }
                });
                break;
            case ItemType.VIDEO:
                final AppCompatImageView imageViewVideo2 = holder.getView(R.id.iv_video_2);
                final AppCompatImageView imageViewVideo3 = holder.getView(R.id.iv_video_3);
                final AppCompatImageView imageViewVideo4 = holder.getView(R.id.iv_video_4);
                final JZVideoPlayerStandard video = holder.getView(R.id.image_video);
                video.thumbImageView.setImageResource(R.drawable.video_1);
//                video.fullscreenButton.setVisibility(View.GONE);
                String uri = "http://p8jpjjvcn.bkt.clouddn.com/shunaite.mp4";
                video.setUp(uri, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "视频案例");
                imageViewVideo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        video.thumbImageView.setImageResource(R.drawable.video_2);
                    }
                });
                imageViewVideo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        video.thumbImageView.setImageResource(R.drawable.video_3);
                    }
                });
                imageViewVideo4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        video.thumbImageView.setImageResource(R.drawable.video_4);
                    }
                });
                break;
            case ItemType.PICTURE:
                final AppCompatImageView imageView = holder.getView(R.id.iv_picture);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.pictureQueryStart();
                    }
                });
                break;
            default:
                break;
        }
    }
}
