package com.yuanting.nomisdun.main.cases;

import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created on 2018/7/23 16:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class PictureConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_1.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_2.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_3.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_4.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_5.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_6.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_7.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        ENTITIES.add(MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                .setField(MultipleFields.IMAGE_URL, "http://p8jpjjvcn.bkt.clouddn.com/picture_8.png")
                .setField(MultipleFields.SPAN_SIZE, 2).build());
        return ENTITIES;
    }
}
