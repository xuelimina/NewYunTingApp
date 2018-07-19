package com.yuanting.nomisdun.main.product;

import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yuanting.nomisdun.R;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/6/22 12:16
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductAdapter extends MultipleRecyclerAdapter {
    private ProductImageOnClick mProductImageOnClick = null;

    protected ProductAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ProductItemType.PRODUCT_ITEM, R.layout.item_nomisdun_product);
    }

    public void setProductImageOnClick(ProductImageOnClick onClick) {
        this.mProductImageOnClick = onClick;
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case ProductItemType.PRODUCT_ITEM:
                final AppCompatImageView imageView1 = holder.getView(R.id.image_item_product_1);
                final AppCompatImageView imageView2 = holder.getView(R.id.image_item_product_2);
                final AppCompatImageView imageView3 = holder.getView(R.id.image_item_product_3);
                final AppCompatImageView imageView4 = holder.getView(R.id.image_item_product_4);
                final ArrayList<MultipleItemEntity> entities = entity.getField(ProductItemFields.ITEM_PRODUCTS);
                final int size = entities.size();
                for (int i = 0; i < size; i++) {
                    switch (i) {
                        case 0:
                            final String imageUrl1 = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + entities.get(0).getField(ProductItemFields.LOGOIMG) + "/Nick2/";
                            Glide.with(mContext)
                                    .load(imageUrl1)
                                    .apply(RECYCLER_OPTIONS)
                                    .into(imageView1);
                            imageView1.setBackgroundColor(Color.DKGRAY);
                            break;
                        case 1:
                            final String imageUrl2 = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + entities.get(1).getField(ProductItemFields.LOGOIMG) + "/Nick2/";
                            Glide.with(mContext)
                                    .load(imageUrl2)
                                    .apply(RECYCLER_OPTIONS)
                                    .into(imageView2);
                            imageView2.setBackgroundColor(Color.DKGRAY);
                            break;
                        case 2:
                            final String imageUrl3 = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + entities.get(2).getField(ProductItemFields.LOGOIMG) + "/Nick2/";
                            Glide.with(mContext)
                                    .load(imageUrl3)
                                    .apply(RECYCLER_OPTIONS)
                                    .into(imageView3);
                            imageView3.setBackgroundColor(Color.DKGRAY);
                            break;
                        case 3:
                            final String imageUrl4 = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + entities.get(3).getField(ProductItemFields.LOGOIMG) + "/Nick2/";
                            Glide.with(mContext)
                                    .load(imageUrl4)
                                    .apply(RECYCLER_OPTIONS)
                                    .into(imageView4);
                            imageView4.setBackgroundColor(Color.DKGRAY);
                            break;
                        default:
                            break;
                    }

                }
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entities.size() >= 1) {
                            mProductImageOnClick.startProductContent(entities.get(0));
                        }

                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entities.size() >= 2) {
                            mProductImageOnClick.startProductContent(entities.get(1));
                        }
                    }
                });
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entities.size() >= 3) {
                            mProductImageOnClick.startProductContent(entities.get(2));
                        }
                    }
                });
                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entities.size() >= 4) {
                            mProductImageOnClick.startProductContent(entities.get(3));
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
