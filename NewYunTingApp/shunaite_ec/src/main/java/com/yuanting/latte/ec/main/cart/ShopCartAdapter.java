package com.yuanting.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;
import com.yuanting.yunting_ec.R;

import java.util.List;

/**
 * Created on 2018/5/22 14:39
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShopCartAdapter extends MultipleRecyclerAdapter {
    private boolean mIsSelectedAll = false;
    private ICartItemListener mCartItemListener = null;
    private double mTotalPrice = 0.00;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice + total;
        }
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemListener(ICartItemListener listener) {
        this.mCartItemListener = listener;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.mTotalPrice = totalPrice;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值

                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                //取出所以控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);
                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);
                //根据数据状态显示左侧勾勾
                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                if (isSelected) {
                    entity.setField(ShopCartItemFields.REMOVE_POSITION, holder.getAdapterPosition());
                    iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                } else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加左侧勾勾点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            iconIsSelected.setTextColor
                                    (ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);
                        }
                    }
                });
                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int countNum = Integer.parseInt(tvCount.getText().toString());
                        countNum--;
                        if (countNum > 0) {
                            tvCount.setText(String.valueOf(countNum));
                            entity.setField(ShopCartItemFields.COUNT, countNum);
                        } else {
                            remove(entity);
                            notifyDataSetChanged();
                        }
                        if (mCartItemListener != null) {
                            final double  itemTotal = countNum * price;
                            mTotalPrice = mTotalPrice - price;
                            mCartItemListener.onItemClick(itemTotal);
                        }
//                            RestClient.builder()
//                                    .url("shop_cart_count.json")
//                                    .loader(mContext)
//                                    .params("count", currentCount)
//                                    .success(new ISuccess() {
//                                        @Override
//                                        public void onSuccess(String response) {
//
//                                        }
//                                    })
//                                    .build()
//                                    .post();

                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int countNum = Integer.parseInt(tvCount.getText().toString());
                        countNum++;
                        tvCount.setText(String.valueOf(countNum));
                        if (mCartItemListener != null) {
                            mTotalPrice = mTotalPrice + price;
                            final double itemTotal = countNum * price;
                            entity.setField(ShopCartItemFields.COUNT, countNum);
                            mCartItemListener.onItemClick(itemTotal);
                        }
//                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
//                        RestClient.builder()
//                                .url("shop_cart_count.php")
//                                .loader(mContext)
//                                .params("count", currentCount)
//                                .success(new ISuccess() {
//                                    @Override
//                                    public void onSuccess(String response) {
//                                        int countNum = Integer.parseInt(tvCount.getText().toString());
//                                        countNum++;
//                                        tvCount.setText(String.valueOf(countNum));
//                                        if (mCartItemListener != null) {
//                                            mTotalPrice = mTotalPrice + price;
//                                            final double itemTotal = countNum * price;
//                                            mCartItemListener.onItemClick(itemTotal);
//                                        }
//                                    }
//                                })
//                                .build()
//                                .post();
                    }
                });
                break;
        }
    }

    public void remove(MultipleItemEntity entity) {
        mData.remove(entity);
    }
}
