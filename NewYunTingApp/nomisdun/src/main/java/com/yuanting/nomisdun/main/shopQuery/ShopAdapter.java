package com.yuanting.nomisdun.main.shopQuery;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.nomisdun.R;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/6/25 14:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShopAdapter extends MultipleRecyclerAdapter {
    private ShopItemOnClick mItemOnClick;

    public void setItemOnClick(ShopItemOnClick itemOnClick) {
        this.mItemOnClick = itemOnClick;
    }

    public ShopAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShopItemType.Shop_ITEM, R.layout.item_nomisdun_shop);
        addItemType(ShopItemType.SHOP_ITEM_CONTENT, R.layout.item_nomisdun_shop_content);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ShopItemType.Shop_ITEM:
                final String text = entity.getField(MultipleFields.TEXT);
                final AppCompatTextView textView = holder.getView(R.id.item_select_title);
                textView.setText(text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.shopContent(entity);
                    }
                });
                break;
            case ShopItemType.SHOP_ITEM_CONTENT:
                final String shopName = entity.getField(MultipleFields.TEXT);
                final AppCompatTextView tvShopName = holder.getView(R.id.tv_shop_name);
                tvShopName.setText(shopName);
                final String province = entity.getField(ShopItemFields.PROVINCE);
                final AppCompatTextView tvProvince = holder.getView(R.id.tv_province);
                tvProvince.setText(province);
                final String city = entity.getField(ShopItemFields.CITY);
                final AppCompatTextView tvCity = holder.getView(R.id.tv_city);
                tvCity.setText(city);
                final String county = entity.getField(ShopItemFields.COUNTY);
                final AppCompatTextView tvCounty = holder.getView(R.id.tv_district);
                tvCounty.setText(county);
                final String address = entity.getField(ShopItemFields.ADDRESS);
                final AppCompatTextView tvAddress = holder.getView(R.id.tv_address);
                tvAddress.setText(address);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.shopContent(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
