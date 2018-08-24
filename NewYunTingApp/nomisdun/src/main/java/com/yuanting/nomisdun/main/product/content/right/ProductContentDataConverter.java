package com.yuanting.nomisdun.main.product.content.right;

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
public class ProductContentDataConverter extends DataConverter {
    private HashMap<String, ArrayList<MultipleItemEntity>> modelMap = new HashMap<>();
    private HashMap<String, ArrayList<MultipleItemEntity>> yearsMap = new HashMap<>();
    private HashMap<String, ArrayList<MultipleItemEntity>> subModelMap = new HashMap<>();
    private HashMap<String, ArrayList<MultipleItemEntity>> seriesMap = new HashMap<>();

    public void setInterior(boolean interior) {
        IsInterior = interior;
    }

    private boolean IsInterior = false;

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        //Model Year SubModel Series ID
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("CList");
        final int size = dataArray.size();
        modelMap.clear();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String id = data.getString("ID");
            final String year = data.getString("Year");
            final String model = data.getString("Model");
            final String subModel = data.getString("SubModel");
            final String make = data.getString("Make");
            final String series = data.getString("Series");
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ID, id)
                    .setField(ProductRightItemFields.MODEL, model)
                    .setField(ProductRightItemFields.YEAR, year)
                    .setField(ProductRightItemFields.SUB_MODEL, subModel)
                    .setField(ProductRightItemFields.SERIES, series)
                    .setField(ProductRightItemFields.MAKE, make)
                    .setField(ProductRightItemFields.IS_LAST, false)
                    .build();
            if (modelMap.containsKey(model)) {
                modelMap.get(model).add(entity);
            } else {
                entity.setField(MultipleFields.ITEM_TYPE, ProductRightItemType.PRODUCT_RIGHT_MODEL_ITEM);
                ArrayList<MultipleItemEntity> entities1 = new ArrayList<>();
                entities1.add(entity);
                modelMap.put(model, entities1);
                entities.add(entity);
            }
        }
        return entities;
    }


    public ArrayList<MultipleItemEntity> getYears(MultipleItemEntity entity1) {
        yearsMap.clear();
        String model = entity1.getField(ProductRightItemFields.MODEL);
        final ArrayList<MultipleItemEntity> entities = modelMap.get(model);
        final ArrayList<MultipleItemEntity> yearsEntities = new ArrayList<>();
        if (entities != null && entities.size() > 0) {
            for (MultipleItemEntity entity : entities) {
                String year = entity.getField(ProductRightItemFields.YEAR);
                if (!year.isEmpty())
                    if (yearsMap.containsKey(year)) {
                        yearsMap.get(year).add(entity);
                    } else {
                        entity.setField(MultipleFields.ITEM_TYPE, ProductRightItemType.PRODUCT_RIGHT_YEAR_ITEM);
                        ArrayList<MultipleItemEntity> entities1 = new ArrayList<>();
                        entities1.add(entity);
                        yearsEntities.add(entity);
                        yearsMap.put(year, entities1);
                    }
            }
            entity1.setField(ProductRightItemFields.IS_LAST, false);
        } else {
            if (IsInterior) {
                entity1.setField(ProductRightItemFields.IS_LAST, true);
            } else {
                entity1.setField(ProductRightItemFields.IS_LAST, false);
            }
        }
        if (IsInterior) {
            return yearsEntities;
        }
        return yearsEntities.size() == 0 ? getEndList(entity1) : yearsEntities;
    }

    public ArrayList<MultipleItemEntity> getSubModels(MultipleItemEntity entity1) {
        subModelMap.clear();
        final ArrayList<MultipleItemEntity> entities = yearsMap.get(entity1.getField(ProductRightItemFields.YEAR).toString());
        final ArrayList<MultipleItemEntity> subModelEntities = new ArrayList<>();
        if (entities != null && entities.size() > 0) {
            for (MultipleItemEntity entity : entities) {
                String subModel = entity.getField(ProductRightItemFields.SUB_MODEL);
                if (!subModel.isEmpty()) {
                    if (subModelMap.containsKey(subModel)) {
                        subModelMap.get(subModel).add(entity);
                    } else {
                        entity.setField(MultipleFields.ITEM_TYPE, ProductRightItemType.PRODUCT_RIGHT_SUB_MODEL_ITEM);
                        ArrayList<MultipleItemEntity> entities1 = new ArrayList<>();
                        entities1.add(entity);
                        subModelEntities.add(entity);
                        subModelMap.put(subModel, entities1);
                    }
                }
            }
            entity1.setField(ProductRightItemFields.IS_LAST, false);
        } else {
            if (IsInterior) {
                entity1.setField(ProductRightItemFields.IS_LAST, true);
            } else {
                entity1.setField(ProductRightItemFields.IS_LAST, false);
            }
        }
        if (IsInterior) {
            return subModelEntities;
        } else {
            entity1.setField(ProductRightItemFields.IS_LAST, false);
        }
        return subModelEntities.size() == 0 ? getEndList(entity1) : subModelEntities;
    }

    public ArrayList<MultipleItemEntity> getSeries(MultipleItemEntity entity1) {
        seriesMap.clear();
        final ArrayList<MultipleItemEntity> entities = subModelMap.get(entity1.getField(ProductRightItemFields.SUB_MODEL).toString());
        final ArrayList<MultipleItemEntity> seriesEntities = new ArrayList<>();
        if (entities != null && entities.size() > 0) {
            for (MultipleItemEntity entity : entities) {
                String series = entity.getField(ProductRightItemFields.SERIES);
                if (!series.isEmpty())
                if (seriesMap.containsKey(series)) {
                    seriesMap.get(series).add(entity);
                } else {
                    entity.setField(MultipleFields.ITEM_TYPE, ProductRightItemType.PRODUCT_RIGHT_SERIES_ITEM);
                    ArrayList<MultipleItemEntity> entities1 = new ArrayList<>();
                    entities1.add(entity);
                    seriesEntities.add(entity);
                    seriesMap.put(series, entities1);
                }
            }
            entity1.setField(ProductRightItemFields.IS_LAST, false);
        } else {
            if (IsInterior) {
                entity1.setField(ProductRightItemFields.IS_LAST, true);
            } else {
                entity1.setField(ProductRightItemFields.IS_LAST, false);
            }
        }
        if (IsInterior) {
            return seriesEntities;
        }
        return seriesEntities.size() == 0 ? getEndList(entity1) : seriesEntities;
    }

    public ArrayList<MultipleItemEntity> getEndList(MultipleItemEntity entity1) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final HashMap<String, String> lastData = new HashMap<>();
        lastData.put("N100系列", "N100");
        lastData.put("N90系列", "N90");
        lastData.put("N75系列", "N75");
        for (String key : lastData.keySet()) {
            MultipleItemEntity entity = MultipleItemEntity.builder().build();
            entity.setField(MultipleFields.ID, entity1.getField(MultipleFields.ID).toString());
            entity.setField(ProductRightItemFields.MODEL, entity1.getField(ProductRightItemFields.MODEL).toString());
            entity.setField(ProductRightItemFields.YEAR, entity1.getField(ProductRightItemFields.YEAR).toString());
            entity.setField(ProductRightItemFields.SUB_MODEL, entity1.getField(ProductRightItemFields.SUB_MODEL).toString());
            entity.setField(ProductRightItemFields.SERIES, entity1.getField(ProductRightItemFields.SERIES).toString());
            entity.setField(ProductRightItemFields.MAKE, entity1.getField(ProductRightItemFields.MAKE).toString());
            entity.setField(MultipleFields.ITEM_TYPE, ProductRightItemType.PRODUCT_RIGHT_END_ITEM);
            entity.setField(ProductRightItemFields.LAST_NAME, key);
            entity.setField(ProductRightItemFields.LAST_MODEL, lastData.get(key));
            entities.add(entity);
        }
        return entities;
    }

}
