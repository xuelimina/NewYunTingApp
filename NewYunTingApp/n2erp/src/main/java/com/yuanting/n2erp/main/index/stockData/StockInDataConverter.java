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
public class StockInDataConverter extends DataConverter {
    private ArrayList<MultipleItemEntity> Allentities = new ArrayList<>();

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        Allentities.clear();
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(getJsonData());
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, StockDataItemType.STOCK_OUT_DATA_DETAILS_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String id = object.getString("ID");
            final String Name = object.getString("Name");
            final String Unit = object.getString("Unit");
            final String Number = object.getString("Number");
            String OperationUserID = "";
            if (object.containsKey("OperatainUserID")) {
                OperationUserID = object.getString("OperatainUserID");
            } else if (object.containsKey("OperationUserID")) {
                OperationUserID = object.getString("OperationUserID");
            }
            String Time = "";
            if (object.containsKey("EntryTime")) {
                Time = object.getString("EntryTime");
            } else if (object.containsKey("Time")) {
                Time = object.getString("Time");
            }
            final String Info = object.getString("Info");
            if (object.containsKey("Owner")) {
                final String Owner = object.getString("Owner");
                itemEntity.setField(StockDataItemFields.OWNER, Owner);
            }
            if (object.containsKey("TypeID")) {
                final String TypeID = object.getString("TypeID");
                itemEntity.setField(StockDataItemFields.TYPE_ID, TypeID);
            }
            itemEntity.setField(MultipleFields.ID, id);
            itemEntity.setField(StockDataItemFields.NAME, Name);
            itemEntity.setField(StockDataItemFields.UNIT, Unit);
            itemEntity.setField(StockDataItemFields.NUMBER, Number);
            itemEntity.setField(StockDataItemFields.OPERATION_USER_ID, OperationUserID);
            itemEntity.setField(StockDataItemFields.TIME, Time);
            itemEntity.setField(StockDataItemFields.INFO, Info);
            entities.add(itemEntity);
            Allentities.add(itemEntity);
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
                    .setField(MultipleFields.ITEM_TYPE, StockDataItemType.STOCK_OUT_DATA_STATISTICS_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String Name = object.getString("名称");
            final String Number = object.getString("数量");
            final String Unit = object.getString("单位");
            itemEntity.setField(StockDataItemFields.NAME, Name);
            itemEntity.setField(StockDataItemFields.UNIT, Unit);
            itemEntity.setField(StockDataItemFields.NUMBER, Number);
            entities.add(itemEntity);
            Allentities.add(itemEntity);
        }
        return entities;
    }

    public ArrayList<MultipleItemEntity> getDataByName(String name) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (MultipleItemEntity entity : Allentities) {
            final String Name = entity.getField(StockDataItemFields.NAME);
            if (Name.contains(name)) {
                entities.add(entity);
            }
        }
        return entities;
    }
}
