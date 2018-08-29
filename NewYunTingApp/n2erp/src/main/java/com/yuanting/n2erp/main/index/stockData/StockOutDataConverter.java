package com.yuanting.n2erp.main.index.stockData;

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
public class StockOutDataConverter extends DataConverter {
    private ArrayList<MultipleItemEntity> Allentities = new ArrayList<>();

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        Allentities.clear();
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(getJsonData());
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, StockOutDataItemType.STOCK_OUT_DATA_DETAILS_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String id = object.getString("ID");
            final String Name = object.getString("Name");
            final String Unit = object.getString("Unit");
            final String Number = object.getString("Number");
            final String OperationUserID = object.getString("OperationUserID");
            final String Time = object.getString("Time");
            final String TextInfo = object.getString("TextInfo");
            final String Other = object.getString("Other");
            itemEntity.setField(MultipleFields.ID, id);
            itemEntity.setField(StockOutDataItemFields.NAME, Name);
            if (object.containsKey("SourceID")) {
                final String SourceID = object.getString("SourceID");
                itemEntity.setField(StockOutDataItemFields.SOURCE_ID, SourceID);
            }
            itemEntity.setField(StockOutDataItemFields.UNIT, Unit);
            itemEntity.setField(StockOutDataItemFields.NUMBER, Number);
            itemEntity.setField(StockOutDataItemFields.OPERATION_USER_ID, OperationUserID);
            itemEntity.setField(StockOutDataItemFields.TIME, Time);
            itemEntity.setField(StockOutDataItemFields.TEXT_INFO, TextInfo);
            itemEntity.setField(StockOutDataItemFields.OTHER, Other);
            if (object.containsKey("Owner")) {
                final String Owner = object.getString("Owner");
                itemEntity.setField(StockOutDataItemFields.OWNER, Owner);
            }
            if (object.containsKey("TypeID")) {
                final String TypeID = object.getString("TypeID");
                itemEntity.setField(StockOutDataItemFields.TYPE_ID, TypeID);
            }
            Allentities.add(itemEntity);
            entities.add(itemEntity);
        }
        return entities;
    }

    public ArrayList<MultipleItemEntity> getStatisticsData(String jsonData) {
        Allentities.clear();
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(jsonData);
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, StockOutDataItemType.STOCK_OUT_DATA_STATISTICS_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String Name = object.getString("名称");
            final String Number = object.getString("数量");
            final String Unit = object.getString("单位");
            itemEntity.setField(StockOutDataItemFields.NAME, Name);
            itemEntity.setField(StockOutDataItemFields.UNIT, Unit);
            itemEntity.setField(StockOutDataItemFields.NUMBER, Number);
            entities.add(itemEntity);
            Allentities.add(itemEntity);
        }
        return entities;
    }
    public ArrayList<MultipleItemEntity> getDataByName(String name) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (MultipleItemEntity entity : Allentities) {
            final String Name = entity.getField(StockOutDataItemFields.NAME);
            if (Name.contains(name)) {
                entities.add(entity);
            }
        }
        return entities;
    }
}
