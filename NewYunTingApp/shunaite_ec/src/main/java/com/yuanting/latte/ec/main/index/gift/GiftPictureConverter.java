package com.yuanting.latte.ec.main.index.gift;

import android.content.Context;
import android.content.res.AssetManager;

import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.ItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created on 2018/8/6 16:28
 * Created by 薛立民
 * TEL 13262933389
 */
public class GiftPictureConverter extends DataConverter {
    private Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        try {
            final AssetManager assets = mContext.getAssets();
            final String[] paths = assets.list("giftPicture");
            for (String path : paths) {
                ENTITIES.add(MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.ITEM_PICTURE)
                        .setField(MultipleFields.IMAGE_URL, "file:///android_asset/giftPicture/" + path)
                        .setField(MultipleFields.SPAN_SIZE, 1).build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ENTITIES;
    }
}
