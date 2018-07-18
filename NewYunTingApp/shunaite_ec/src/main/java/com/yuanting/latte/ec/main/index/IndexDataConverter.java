package com.yuanting.latte.ec.main.index;

import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_ec.R;

import java.util.ArrayList;

/**
 * Created on 2018/5/4 11:38
 * Created by 薛立民
 * TEL 13262933389
 */
public class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.add(banners());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.HORIZONTAL_MENU_LIST)
                .setField(MultipleFields.SPAN_SIZE, 4).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.VIDEO)
                .setField(MultipleFields.SPAN_SIZE, 4).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.PICTURE)
                .setField(MultipleFields.SPAN_SIZE, 4).build());
        return ENTITIES;
    }

    private MultipleItemEntity banners() {
        final ArrayList<Integer> bannerImages = new ArrayList<>();
        bannerImages.clear();
        bannerImages.add(R.mipmap.bannder1);
        bannerImages.add(R.mipmap.bannder2);
        bannerImages.add(R.mipmap.bannder3);
        bannerImages.add(R.mipmap.bannder4);
        bannerImages.add(R.mipmap.bannder5);
        return MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.BANNER)
                    .setField(MultipleFields.SPAN_SIZE, 4)
                    .setField(MultipleFields.BANNERS, bannerImages).build();
    }
}
