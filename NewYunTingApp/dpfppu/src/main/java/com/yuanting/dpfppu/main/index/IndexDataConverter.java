package com.yuanting.dpfppu.main.index;

import com.yuanting.dpfppu.R;
import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

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
        ENTITIES.add(MultipleItemEntity.builder().setField(MultipleFields.ITEM_TYPE, ItemType.HORIZONTAL_MENU_LIST).build());
        ENTITIES.add(MultipleItemEntity.builder().setField(MultipleFields.ITEM_TYPE, IndexItemType.INDEX_ITEM_EXTERNAL).build());
        ENTITIES.add(MultipleItemEntity.builder().setField(MultipleFields.ITEM_TYPE, IndexItemType.INDEX_ITEM_INSIDE).build());
        ENTITIES.add(MultipleItemEntity.builder().setField(MultipleFields.ITEM_TYPE, IndexItemType.INDEX_ITEM_COLOR_MODIFICATION).build());
        ENTITIES.add(MultipleItemEntity.builder().setField(MultipleFields.ITEM_TYPE, IndexItemType.INDEX_ITEM_SUNLIGHT).build());
        return ENTITIES;
    }

    private MultipleItemEntity banners() {
        final ArrayList<Integer> bannerImages = new ArrayList<>();
        bannerImages.clear();
        bannerImages.add(R.drawable.bannder_1);
        bannerImages.add(R.drawable.bannder_2);
        bannerImages.add(R.drawable.bannder_3);
        bannerImages.add(R.drawable.bannder_4);
        return MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.BANNER)
                .setField(MultipleFields.SPAN_SIZE, 4)
                .setField(MultipleFields.BANNERS, bannerImages).build();
    }
}
