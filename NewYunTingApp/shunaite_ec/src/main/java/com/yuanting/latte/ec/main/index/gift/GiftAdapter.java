package com.yuanting.latte.ec.main.index.gift;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yuanting.latte.ec.main.index.picture.PictureItemOnClick;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;
import com.yuanting.yunting_ec.R;

import java.util.List;

/**
 * Created on 2018/8/7 11:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class GiftAdapter extends MultipleRecyclerAdapter {
    private PictureItemOnClick itemOnClick;
    protected GiftAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.ITEM_PICTURE, R.layout.item_multiple_item_picture);
    }
    public void setItemOnClick(PictureItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final View view = holder.itemView;
        switch (entity.getItemType()) {
            case ItemType.ITEM_PICTURE:
                final AppCompatImageView itemPicture = holder.getView(R.id.image_view_picture);
                Glide.with(mContext).load(entity.getField(MultipleFields.IMAGE_URL)).apply(RECYCLER_OPTIONS).into(itemPicture);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOnClick.showPictureDetail(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
