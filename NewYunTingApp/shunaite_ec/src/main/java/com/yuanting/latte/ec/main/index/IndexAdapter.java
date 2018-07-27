package com.yuanting.latte.ec.main.index;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;
import com.yuanting.yunting_ec.R;

import java.io.IOException;
import java.util.ArrayList;
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
                identification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.orderQueryStart();
                    }
                });
                break;
            case ItemType.VIDEO:
                final JZVideoPlayerStandard video = holder.getView(R.id.image_video);
                video.thumbImageView.setImageResource(R.drawable.video_1);
                video.fullscreenButton.setVisibility(View.GONE);
                String uri = "http://p8jpjjvcn.bkt.clouddn.com/shunaite.mp4";
                video.setUp(uri, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "视频案例");
                break;
            case ItemType.PICTURE:
                final RecyclerView recyclerView = holder.getView(R.id.ry_picture);
                final PictureAdapter adapter = new PictureAdapter(new PictureConverter().convert());
                final GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                break;
            default:
                break;
        }
    }

    class PictureAdapter extends MultipleRecyclerAdapter {

        PictureAdapter(List<MultipleItemEntity> data) {
            super(data);
            addItemType(ItemType.ITEM_PICTURE, R.layout.item_multiple_item_picture);
        }

        @Override
        protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
            super.convert(holder, entity);
            switch (entity.getItemType()) {
                case ItemType.ITEM_PICTURE:
                    final AppCompatImageView itemPicture = holder.getView(R.id.image_view_picture);
                    Glide.with(mContext).load(entity.getField(MultipleFields.IMAGE_URL)).apply(RECYCLER_OPTIONS).into(itemPicture);
                    break;
                default:
                    break;
            }
        }
    }

    class PictureConverter extends DataConverter {
        @Override
        public ArrayList<MultipleItemEntity> convert() {
            try {
                final AssetManager assets = mContext.getAssets();
                final String[] paths = assets.list("picture");
                for (String path : paths) {
                    ENTITIES.add(MultipleItemEntity.builder()
                            .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                            .setField(MultipleFields.IMAGE_URL, "file:///android_asset/picture/" + path)
                            .setField(MultipleFields.SPAN_SIZE, 1).build());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ENTITIES;
        }
    }
}
