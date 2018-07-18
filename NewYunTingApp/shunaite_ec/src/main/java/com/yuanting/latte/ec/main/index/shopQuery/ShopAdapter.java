package com.yuanting.latte.ec.main.index.shopQuery;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;
import com.yuanting.yunting_ec.R;

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
        addItemType(ShopItemType.Shop_ITEM, R.layout.item_shop);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatTextView textView = holder.getView(R.id.item_select_title);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ShopItemType.Shop_ITEM:
                final String text = entity.getField(MultipleFields.TEXT);
                textView.setText(text);
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
