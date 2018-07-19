package com.yuanting.nomisdun.main.product;

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
public class ProductDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("CbList");
        final int size = dataArray.size();
        final int count;
        if (size % 4 == 0) {
            count = size / 4;
        } else {
            count = size / 4 + 1;
        }
        for (int i = 0; i < count; i++) {
            final ArrayList<MultipleItemEntity> itemEntities = new ArrayList<>();
            for (int j = i * 4; j < i * 4 + 4; j++) {
                if (j == size) {
                    break;
                }
                final JSONObject data = dataArray.getJSONObject(j);
                final int id = data.getInteger("Id");
                final String brand = data.getString("Brand");
                final String brandCn = data.getString("BrandCn");
                final String carImg = data.getString("CarImg");
                final String logoImg = data.getString("LogoImg");
                MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(ProductItemFields.ID, id)
                        .setField(ProductItemFields.BRAND, brand)
                        .setField(ProductItemFields.BRAND_CN, brandCn)
                        .setField(ProductItemFields.CARIMG, carImg)
                        .setField(ProductItemFields.LOGOIMG, logoImg)
                        .build();
                itemEntities.add(entity);
            }
            MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ProductItemType.PRODUCT_ITEM)
                    .setField(MultipleFields.SPAN_SIZE, 4)
                    .setField(ProductItemFields.ITEM_PRODUCTS, itemEntities)
                    .build();
            entities.add(itemEntity);
        }
        return entities;
    }
}
