package com.yuanting.nomisdun.main.product.content.price;

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
public class ProductModelDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("PackageList");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final ArrayList<MultipleItemEntity> partEntities = new ArrayList<>();
            final JSONObject data = dataArray.getJSONObject(i);
            final String id = data.getString("Id");
            final String carId = data.getString("CarId");
            final String type = data.getString("Type");
            final String descrption = data.getString("Descrption");
            final String showImg = data.getString("ShowImg");
            final String setImg = data.getString("SetImg");
            final String price = data.getString("Price");
            final String name = data.getString("Name");
            final JSONArray partListData = data.getJSONArray("PartList");
            if (partListData != null) {
                final int size1 = partListData.size();
                for (int j = 0; j < size1; j++) {
                    final JSONObject partData = partListData.getJSONObject(j);
                    final String bId = partData.getString("BId");
                    final String aId = partData.getString("AId");
                    final String carId1 = partData.getString("CarId");
                    final String type1 = partData.getString("Type");
                    final String descrption1 = partData.getString("Descrption");
                    final String showImg1 = partData.getString("ShowImg");
                    final String setImg1 = partData.getString("SetImg");
                    final String price1 = partData.getString("Price");
                    final String name1 = partData.getString("Name");
                    MultipleItemEntity entity = MultipleItemEntity.builder()
                            .setField(ProductModelPartItemFields.BID, bId)
                            .setField(ProductModelPartItemFields.AID, aId)
                            .setField(ProductModelPartItemFields.CARID, carId1)
                            .setField(ProductModelPartItemFields.DESCRPTION, descrption1)
                            .setField(ProductModelPartItemFields.SHOWIMG, showImg1)
                            .setField(ProductModelPartItemFields.SETIMG, setImg1)
                            .setField(ProductModelPartItemFields.TYPE, type1)
                            .setField(ProductModelPartItemFields.PRICE, price1)
                            .setField(ProductModelPartItemFields.NAME, name1)
                            .build();
                    partEntities.add(entity);
                }
            }
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ID, id)
                    .setField(ProductModelItemFields.CARID, carId)
                    .setField(ProductModelItemFields.TYPE, type)
                    .setField(ProductModelItemFields.DESCRPTION, descrption)
                    .setField(ProductModelItemFields.SHOWIMG, showImg)
                    .setField(ProductModelItemFields.SETIMG, setImg)
                    .setField(ProductModelItemFields.PRICE, price)
                    .setField(ProductModelItemFields.NAME, name)
                    .setField(ProductModelItemFields.IS_SELECTED, false)
                    .setField(ProductModelItemFields.PARTLIST, partEntities)
                    .build();
            entity.setField(MultipleFields.ITEM_TYPE, ProductModelItemType.PRODUCT_MODEL_MODEL_ITEM);
            entities.add(entity);
        }
        return entities;
    }
}
