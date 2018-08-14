package com.yuanting.dpfppu.main.index.shopQuery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created on 2018/6/22 12:18
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShopDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(getJsonData());
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .build();
            if (dataArray.getString(i).contains("Address")) {
                JSONObject object = dataArray.getJSONObject(i);
                itemEntity.setField(MultipleFields.TEXT, object.getString("Name"));
                itemEntity.setField(MultipleFields.ITEM_TYPE, ShopItemType.SHOP_ITEM_CONTENT);
                itemEntity.setField(MultipleFields.ID, object.getString("ID"));
                itemEntity.setField(ShopItemFields.NAME, object.getString("Name"));
                itemEntity.setField(ShopItemFields.ADDRESS, object.getString("Address"));
                itemEntity.setField(ShopItemFields.PROVINCE, object.getString("Province"));
                itemEntity.setField(ShopItemFields.CITY, object.getString("City"));
                itemEntity.setField(ShopItemFields.COUNTY, object.getString("County"));
                itemEntity.setField(ShopItemFields.CONTACT, object.getString("Contact"));
                itemEntity.setField(ShopItemFields.CONTACT_NAME, object.getString("ContactName"));
                itemEntity.setField(ShopItemFields.STATUS, object.getString("Status"));
                itemEntity.setField(ShopItemFields.CREATE_TIME, object.getString("CreateTime"));
            } else {
                itemEntity.setField(MultipleFields.TEXT, dataArray.getString(i));
                itemEntity.setField(MultipleFields.ITEM_TYPE, ShopItemType.Shop_ITEM);
            }
            entities.add(itemEntity);
        }
        return entities;
    }
}
