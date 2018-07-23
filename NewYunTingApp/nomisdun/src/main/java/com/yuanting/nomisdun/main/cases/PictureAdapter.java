package com.yuanting.nomisdun.main.cases;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yuanting.nomisdun.R;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/7/23 15:13
 * Created by 薛立民
 * TEL 13262933389
 */
public class PictureAdapter extends MultipleRecyclerAdapter {
    private PictureItemOnClick pictureItemOnClick;

    PictureAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.ITEM_PICTURE, R.layout.item_nomisdun_picture);
    }

    public void setPictureItemOnClick(PictureItemOnClick pictureItemOnClick) {
        this.pictureItemOnClick = pictureItemOnClick;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ItemType.ITEM_PICTURE:
                final AppCompatImageView itemPicture = holder.getView(R.id.image_view_picture);
                Glide.with(mContext)
                        .load(entity.getField(MultipleFields.IMAGE_URL))
                        .apply(RECYCLER_OPTIONS)
                        .into(itemPicture);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pictureItemOnClick.pictureItemOnClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
