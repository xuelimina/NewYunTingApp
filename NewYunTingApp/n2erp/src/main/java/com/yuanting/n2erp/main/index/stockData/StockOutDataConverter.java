package com.yuanting.n2erp.main.index.stockData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.ui.recycler.DataConverter;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 2018/6/22 12:18
 * Created by 薛立民
 * TEL 13262933389
 */
public class StockOutDataConverter extends DataConverter {
    private ArrayList<MultipleItemEntity> Allentities = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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
            final String OperationUserID = object.getString("OperationUserID");
            String Time = object.getString("Time");
            final String TextInfo = object.getString("TextInfo");
            String Other = object.getString("Other");
            itemEntity.setField(MultipleFields.ID, id);
            itemEntity.setField(StockDataItemFields.NAME, Name);
            if (object.containsKey("SourceID")) {
                final String SourceID = object.getString("SourceID");
                itemEntity.setField(StockDataItemFields.SOURCE_ID, SourceID);
            }
            itemEntity.setField(StockDataItemFields.UNIT, Unit);
            itemEntity.setField(StockDataItemFields.NUMBER, Number);
            itemEntity.setField(StockDataItemFields.OPERATION_USER_ID, OperationUserID);
            if (Time.contains("T")) {
                Time = Time.replace("T", " ");
            }
            itemEntity.setField(StockDataItemFields.TIME, Time);
            itemEntity.setField(StockDataItemFields.TEXT_INFO, TextInfo);
            if (Other.contains("/")) {
                Other = Other.replace("/", "-");
            }
            itemEntity.setField(StockDataItemFields.OTHER, Other);
            if (object.containsKey("Owner")) {
                final String Owner = object.getString("Owner");
                itemEntity.setField(StockDataItemFields.OWNER, Owner);
            }
            if (object.containsKey("TypeID")) {
                final String TypeID = object.getString("TypeID");
                itemEntity.setField(StockDataItemFields.TYPE_ID, TypeID);
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

    public ArrayList<MultipleItemEntity> getDataByNameStartEnd(String name, String start, String end) {
        ArrayList<MultipleItemEntity> entities = null;
        if (name != null && !name.isEmpty()) {
            if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
                entities = new ArrayList<>();
                if (getDataByTime(start, end).size() > 0){
                    for (MultipleItemEntity entity : getDataByTime(start, end)) {
                        final String Name = entity.getField(StockDataItemFields.NAME);
                        if (Name.contains(name)) {
                            entities.add(entity);
                        }
                    }
                }
            }
            if (start == null || start.isEmpty() && end != null && !end.isEmpty()) {
                entities = getDataByStartTime(end, "End");
            }
            if (start != null && !start.isEmpty() && (end == null || end.isEmpty())) {
                entities = getDataByStartTime(start, "Start");
            }
            if (start == null || start.isEmpty() && end == null || end.isEmpty()) {
                entities = getDataByName(name);
            }
        } else {
            if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
                entities = getDataByTime(start, end);
            }
            if (start == null || start.isEmpty() && end != null && !end.isEmpty()) {
                entities = getDataByNameTime(name, end, "End");
            }
            if (start != null && !start.isEmpty() && (end == null || end.isEmpty())) {
                entities = getDataByNameTime(name, start, "Start");
            }
        }
        return entities;
    }

    private ArrayList<MultipleItemEntity> getDataByNameTime(String name, String times, String tga) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        try {
            final Date startDate = sdf.parse(times);
            for (MultipleItemEntity entity : Allentities) {
                final String time = entity.getField(StockDataItemFields.OTHER);
                final String Name = entity.getField(StockDataItemFields.NAME);
                if (time != null && !time.isEmpty()) {
                    final Date timeData = sdf.parse(time);
                    if (tga.equals("Start") && startDate.getTime() <= timeData.getTime() && Name.contains(name)) {
                        entities.add(entity);
                    } else if (!tga.equals("Start") && timeData.getTime() <= startDate.getTime() && Name.contains(name)) {
                        entities.add(entity);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entities;
    }

    private Date getDate(String timeStr) {
        Date date = new Date();
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            try {
                date = sdf.parse(timeStr);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return date;
    }

    private ArrayList<MultipleItemEntity> getDataByName(String name) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (MultipleItemEntity entity : Allentities) {
            final String Name = entity.getField(StockDataItemFields.NAME);
            if (Name.contains(name)) {
                entities.add(entity);
            }
        }
        return entities;
    }

    private ArrayList<MultipleItemEntity> getDataByTime(String start, String end) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        try {
            final Date startDate = sdf.parse(start);
            final Date endData = sdf.parse(end);
            for (MultipleItemEntity entity : Allentities) {
                final String time = entity.getField(StockDataItemFields.OTHER);
                if (time != null && !time.isEmpty()) {
                    final Date timeData = sdf.parse(time);
                    if (startDate.getTime() <= timeData.getTime() && timeData.getTime() <= endData.getTime()) {
                        entities.add(entity);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entities;
    }

    private ArrayList<MultipleItemEntity> getDataByStartTime(String start, String tge) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        try {
            final Date startDate = sdf.parse(start);
            for (MultipleItemEntity entity : Allentities) {
                final String time = entity.getField(StockDataItemFields.OTHER);
                if (time != null && !time.isEmpty()) {
                    final Date timeData = sdf.parse(time);
                    if (tge.equals("Start") && startDate.getTime() <= timeData.getTime()) {
                        entities.add(entity);
                    } else if (!tge.equals("Start") && timeData.getTime() <= startDate.getTime()) {
                        entities.add(entity);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
