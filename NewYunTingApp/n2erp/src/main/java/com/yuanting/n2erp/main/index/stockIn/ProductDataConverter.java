package com.yuanting.n2erp.main.index.stockIn;

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
    private ArrayList<MultipleItemEntity> CategoryList = new ArrayList<>();
    private ArrayList<MultipleItemEntity> BrandList = new ArrayList<>();
    private ArrayList<MultipleItemEntity> ModelList = new ArrayList<>();
    private ArrayList<MultipleItemEntity> ProductNameList = new ArrayList<>();

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        CategoryList.clear();
        BrandList.clear();
        ModelList.clear();
        ProductNameList.clear();
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(getJsonData());
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ProductItemType.PRODUCT_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String text;
            final String idx = object.getString("Idx");
            final String id = object.getString("ID");
            final String Parent = object.getString("Parent");
            text = object.getString("Name");
            itemEntity.setField(MultipleFields.ID, id);
            itemEntity.setField(ProductItemFields.NAME, object.getString("Name"));
            itemEntity.setField(ProductItemFields.INFOS, object.getString("Infos"));
            itemEntity.setField(ProductItemFields.PARENT, Parent);
            itemEntity.setField(ProductItemFields.IDX, idx);
            itemEntity.setField(ProductItemFields.OWNER, object.getString("Owner"));
            itemEntity.setField(MultipleFields.TEXT, text);
            switch (idx) {
                case ProductIdx.CATEGORY_IDX:
                    CategoryList.add(itemEntity);
                    entities.add(itemEntity);
                    break;
                case ProductIdx.BRAND_IDX:
                    BrandList.add(itemEntity);
                    break;
                case ProductIdx.MODEL_IDX:
                    ModelList.add(itemEntity);
                    break;
                case ProductIdx.PRODUCT_NAME_IDX:
                    ProductNameList.add(itemEntity);
                    break;
            }
        }
        return entities;
    }

    public ArrayList<MultipleItemEntity> getListData(MultipleItemEntity entity, String idx) {
        switch (idx) {
            case ProductIdx.CATEGORY_IDX:
                return CategoryList;
            case ProductIdx.BRAND_IDX:
                return getListData(entity, BrandList);
            case ProductIdx.MODEL_IDX:
                return getListData(entity, ModelList);
            case ProductIdx.PRODUCT_NAME_IDX:
                return getListData(entity, ProductNameList);
            default:
                return CategoryList;
        }
    }

    private ArrayList<MultipleItemEntity> getListData(MultipleItemEntity entity, ArrayList<MultipleItemEntity> entities) {
        final ArrayList<MultipleItemEntity> entities1 = new ArrayList<>();
        for (MultipleItemEntity entity1 : entities) {
            if (entity.getField(MultipleFields.ID).equals(entity1.getField(ProductItemFields.PARENT))) {
                entities1.add(entity1);
            }
        }
        return entities1;
    }

    public void reListData(String idx, String response) {
        switch (idx) {
            case ProductIdx.CATEGORY_IDX:
                CategoryList.clear();
                break;
            case ProductIdx.BRAND_IDX:
                BrandList.clear();
                break;
            case ProductIdx.MODEL_IDX:
                ModelList.clear();
                break;
            case ProductIdx.PRODUCT_NAME_IDX:
                ProductNameList.clear();
                break;
        }
        final JSONArray dataArray = JSON.parseArray(response);
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final MultipleItemEntity itemEntity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ProductItemType.PRODUCT_ITEM)
                    .build();
            final JSONObject object = dataArray.getJSONObject(i);
            final String text;
            final String Idx = object.getString("Idx");
            final String id = object.getString("ID");
            final String Parent = object.getString("Parent");
            text = object.getString("Name");
            itemEntity.setField(MultipleFields.ID, id);
            itemEntity.setField(ProductItemFields.NAME, object.getString("Name"));
            itemEntity.setField(ProductItemFields.INFOS, object.getString("Infos"));
            itemEntity.setField(ProductItemFields.PARENT, Parent);
            itemEntity.setField(ProductItemFields.IDX, Idx);
            itemEntity.setField(ProductItemFields.OWNER, object.getString("Owner"));
            itemEntity.setField(MultipleFields.TEXT, text);
            switch (Idx) {
                case ProductIdx.CATEGORY_IDX:
                    CategoryList.add(itemEntity);
                    break;
                case ProductIdx.BRAND_IDX:
                    BrandList.add(itemEntity);
                    break;
                case ProductIdx.MODEL_IDX:
                    ModelList.add(itemEntity);
                    break;
                case ProductIdx.PRODUCT_NAME_IDX:
                    ProductNameList.add(itemEntity);
                    break;
            }
        }
    }
}
