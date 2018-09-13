package com.yuanting.n2erp.main.index.stockOut;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 2018/6/22 12:18
 * Created by 薛立民
 * TEL 13262933389
 */
public class StockOutProductDataConverter extends DataConverter {
    private ArrayList<MultipleItemEntity> Allentities = new ArrayList<>();

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        Allentities.clear();
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(getJsonData());
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String id = object.getString("ID");
            final String Name = object.getString("Name");
            final String Number = object.getString("Number");
            final String Unit = object.getString("Unit");
            final String EntryTime = object.getString("EntryTime");
            final String OperationUserID = object.getString("OperationUserID");
            final String Owner = object.getString("Owner");
            final String Info = object.getString("Info");
            final String TypeID = object.getString("TypeID");
            itemEntity.setField(MultipleFields.ID, id);
            itemEntity.setField(MultipleFields.TEXT, Name);
            itemEntity.setField(StockOutProductItemFields.NAME, Name);
            itemEntity.setField(StockOutProductItemFields.NUMBER, Number);
            itemEntity.setField(StockOutProductItemFields.UNIT, Unit);
            itemEntity.setField(StockOutProductItemFields.ENTRY_TIME, EntryTime);
            itemEntity.setField(StockOutProductItemFields.OPERATION_USER_ID, OperationUserID);
            itemEntity.setField(StockOutProductItemFields.OWNER, Owner);
            itemEntity.setField(StockOutProductItemFields.INFO, Info);
            itemEntity.setField(StockOutProductItemFields.TYPE_ID, TypeID);
            itemEntity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_ITEM);
            if (Unit.equals("卷")) {
                itemEntity.setField(StockOutProductItemFields.ITEM_TYPE, StockOutProductItemType.PRODUCT_ITEM);
            } else if (Unit.equals("米")) {
                itemEntity.setField(StockOutProductItemFields.ITEM_TYPE, StockOutProductItemType.PRODUCT_UNIT_ITEM);
            }
            Allentities.add(itemEntity);
        }
        return entities;
    }

    public ArrayList<MultipleItemEntity> getListByName(String name) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (MultipleItemEntity entity1 : getProductNameListData()) {
            final String Name = entity1.getField(StockOutProductItemFields.NAME);
            if (Name.contains(name))
                entities.add(entity1);
        }
        return entities;
    }

    public ArrayList<MultipleItemEntity> getListByEntity(MultipleItemEntity entity, String unit) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final String mName = entity.getField(StockOutProductItemFields.NAME);
        for (MultipleItemEntity entity1 : Allentities) {
            final String Unit = entity1.getField(StockOutProductItemFields.UNIT);
            final String Name = entity1.getField(StockOutProductItemFields.NAME);
            if (!Unit.isEmpty() && !Name.isEmpty())
                if (unit.equals(Unit) && mName.equals(Name)) {
                    final String number = entity1.getField(StockOutProductItemFields.NUMBER);
                    entity1.setField(MultipleFields.TEXT, number + Unit);
                    entity1.setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_UNIT_ITEM);
                    entities.add(entity1);
                }
        }
        return entities;
    }

    public ArrayList<MultipleItemEntity> getProductNameListData() {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final HashMap<String, ArrayList<MultipleItemEntity>> EntityHashMap = new HashMap<>();
        for (MultipleItemEntity entity : Allentities) {
            final String NAME = entity.getField(StockOutProductItemFields.NAME);
            if (EntityHashMap.containsKey(NAME)) {
                EntityHashMap.get(NAME).add(entity);
            } else {
                final ArrayList<MultipleItemEntity> list = new ArrayList<>();
                list.add(entity);
                EntityHashMap.put(NAME, list);
            }
        }
        final ArrayList<String> NameArrayList = new ArrayList<>();
        for (String key : EntityHashMap.keySet()) {
            int i = 0;
            final int size = EntityHashMap.get(key).size();
            for (MultipleItemEntity entity : EntityHashMap.get(key)) {
                final String name = entity.getField(StockOutProductItemFields.NAME);
                final String unit = entity.getField(StockOutProductItemFields.UNIT);
                if (unit.equals("卷")) {
                    if (!NameArrayList.contains(name)) {
                        NameArrayList.add(name);
                        entity.setField(MultipleFields.TEXT, name);
                        entity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_ITEM);
                        entities.add(entity);
                        break;
                    }
                } else if (unit.equals("米")) {
                    i++;
                    if (i == size) {
                        if (!NameArrayList.contains(name)) {
                            NameArrayList.add(name);
                            entity.setField(MultipleFields.TEXT, name);
                            entity.setField(StockOutProductItemFields.IDX, StockOutProductItemType.PRODUCT_ITEM);
                            entities.add(entity);
                        }
                    }
                }
            }
        }
        return entities;
    }

}
