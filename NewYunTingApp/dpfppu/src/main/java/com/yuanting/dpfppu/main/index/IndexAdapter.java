package com.yuanting.dpfppu.main.index;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.yuanting.dpfppu.R;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/6/21 17:49
 * Created by 薛立民
 * TEL 13262933389
 */
public class IndexAdapter extends MultipleRecyclerAdapter {
    private IndexMenuClickListener menuClickListener = null;

    protected IndexAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.HORIZONTAL_MENU_LIST, R.layout.item_multiple_menu_dpf);
        addItemType(IndexItemType.INDEX_ITEM_EXTERNAL, R.layout.item_multiple_external_dpf);
        addItemType(IndexItemType.INDEX_ITEM_INSIDE, R.layout.item_multiple_inside_dpf);
        addItemType(IndexItemType.INDEX_ITEM_COLOR_MODIFICATION, R.layout.item_multiple_color_modification_dpf);
        addItemType(IndexItemType.INDEX_ITEM_SUNLIGHT, R.layout.item_multiple_sunlight_dpf);
    }

    public void setMenuClickListener(@NonNull IndexMenuClickListener listener) {
        this.menuClickListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ItemType.HORIZONTAL_MENU_LIST:
                final LinearLayoutCompat video = holder.getView(R.id.video_query_view);
                final LinearLayoutCompat shopQuery = holder.getView(R.id.shop_query_view);
                final LinearLayoutCompat orderQuery = holder.getView(R.id.order_query_view);
                video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.videoQueryStart();
                    }
                });
                shopQuery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.shopQueryStart();
                    }
                });
                orderQuery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.orderQueryStart();
                    }
                });
                break;
            case IndexItemType.INDEX_ITEM_EXTERNAL:
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.showExternal();
                    }
                });
                break;
            case IndexItemType.INDEX_ITEM_INSIDE:
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.showInside();
                    }
                });
                break;
            case IndexItemType.INDEX_ITEM_COLOR_MODIFICATION:
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.showColorModification();
                    }
                });
                break;
            case IndexItemType.INDEX_ITEM_SUNLIGHT:
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClickListener.showSunlight();
                    }
                });
                break;
            default:
                break;
        }
    }
}
