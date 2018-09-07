package com.yuanting.n2erp.main.index.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2erp.R;
import com.yuanting.n2erp.R2;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/23 13:17
 * Created by 薛立民
 * TEL 13262933389
 */
public class FormDelegate extends LatteDelegate implements CalendarItemOnClick {
    @BindView(R2.id.tv_month)
    AppCompatTextView mTvMonth;
    @BindView(R2.id.tv_year)
    AppCompatTextView mTvYear;
    @BindView(R2.id.tv_month_matter)
    AppCompatTextView mTvMonthMatter;
    @BindView(R2.id.tv_day_matter)
    AppCompatTextView mTvDayMatter;
    @BindView(R2.id.rv_calendar)
    RecyclerView mRvCalendar;
    @BindView(R2.id.rv_day_matter)
    RecyclerView mRvDayMatter;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    private boolean isCurrentDay = true;
    /**
     * 当前年月日
     */
    private int mYear;
    private int mMonth;
    /**
     * 平年月天数数组
     */
    int commonYearMonthDay[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    /**
     * 闰年月天数数组
     */
    int leapYearMonthDay[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// HH:mm:ss//获取当前时间

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        /** 初始化当前系统的日期 */
        Calendar calendar = Calendar.getInstance();
        mCurrentYear = calendar.get(Calendar.YEAR);
        mCurrentMonth = calendar.get(Calendar.MONTH) + 1;
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.mYear = calendar.get(Calendar.YEAR);
        this.mMonth = calendar.get(Calendar.MONTH) + 1;
        mTvYear.setText(String.valueOf(mYear));
        mTvMonth.setText(mMonth + "月");
        isCurrentDay = true;
        GetConstructionByDate("" + mYear, "" + mMonth);
    }

    @OnClick({R2.id.icon_left, R2.id.icon_right})
    void iconOnClick(View view) {
        if (view.getId() == R.id.icon_left) {
            if (mMonth > 1) {
                mMonth = mMonth - 1;
            } else {
                mMonth = 12;
                mYear = mYear - 1;
            }

        } else if (view.getId() == R.id.icon_right) {
            if (mMonth < 12) {
                mMonth = mMonth + 1;
            } else {
                mMonth = 1;
                mYear = mYear + 1;
            }
        }
        mTvYear.setText(String.valueOf(mYear));
        mTvMonth.setText(mMonth + "月");
        isCurrentDay = mYear == mCurrentYear && mMonth == mCurrentMonth;
        GetConstructionByDate("" + mYear, "" + mMonth);
        mRvDayMatter.setVisibility(View.VISIBLE);
        mTvDayMatter.setVisibility(View.GONE);
        reRecyclerCalendarDataDetail(new ArrayList<MultipleItemEntity>());
    }

    private void GetConstructionByDate(final String year, final String month) {
        RestClient.builder().url("GetConstructionByDate?")
                .params("year", year).params("month", month).params("owner", AccountManager.getOwner())
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("GetConstructionByDate", JsonUtils.getDecodeJSONStr(response));
                        final JSONArray jsonArray = JSON.parseArray(JsonUtils.getDecodeJSONStr(response));
                        if (jsonArray == null || jsonArray.size() == 0) {
                            reRecyclerCalendarData(getCurrentMonthDay(mYear, mMonth, null));
                            mTvMonthMatter.setText("本月所有事件：0 件");
                        } else {
                            try {
                                final int size = jsonArray.size();
                                final HashMap<Integer, ArrayList<MultipleItemEntity>> dayData = new HashMap<>();
                                for (int i = 0; i < size; i++) {
                                    final JSONObject object = jsonArray.getJSONObject(i);
                                    final String ID = object.getString("ID");
                                    final String Position = object.getString("Position");
                                    final String Body = object.getString("Body");
                                    final String Color = object.getString("Color");
                                    final String Site = object.getString("Site");
                                    final String Material = object.getString("Material");
                                    final String Infos = object.getString("Infos");
                                    final String IsFinished = object.getString("IsFinished");
                                    final MultipleItemEntity entity = MultipleItemEntity.builder()
                                            .setField(MultipleFields.ITEM_TYPE, CalendarItemType.CALENDAR_DETAIL_ITEM)
                                            .setField(MultipleFields.ID, ID)
                                            .setField(CalendarItemFields.POSITION, Position)
                                            .setField(CalendarItemFields.BODY, Body)
                                            .setField(CalendarItemFields.COLOR, Color)
                                            .setField(CalendarItemFields.SITE, Site)
                                            .setField(CalendarItemFields.MATERIAL, Material)
                                            .setField(CalendarItemFields.INFOS, Infos)
                                            .setField(CalendarItemFields.IS_FINISHED, IsFinished)
                                            .build();
                                    String Time = object.getString("Time");
                                    if (Time.contains("T")) {
                                        Time = Time.replace("T", " ");
                                    }
                                    final Calendar calendar = Calendar.getInstance();
                                    final Date date = simpleDateFormat.parse(Time);
                                    calendar.setTime(date);
                                    final String time = month + "-" + calendar.get(Calendar.DAY_OF_MONTH)
                                            + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    entity.setField(CalendarItemFields.DAY, day);
                                    entity.setField(CalendarItemFields.TIME, time);
                                    if (dayData.containsKey(day)) {
                                        if (dayData.get(day).get(0).getField(CalendarItemFields.TIME).toString().compareTo(time) > 0) {
                                            dayData.get(day).add(0, entity);
                                        } else {
                                            dayData.get(day).add(1, entity);
                                        }

                                    } else {
                                        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
                                        entities.add(entity);
                                        dayData.put(day, entities);
                                    }
                                }
                                mTvMonthMatter.setText("本月所有事件：" + size + "件");
                                reRecyclerCalendarData(getCurrentMonthDay(mYear, mMonth, dayData));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        reRecyclerCalendarData(getCurrentMonthDay(mYear, mMonth, null));
                        mTvMonthMatter.setText("本月所有事件：0 件");
                        Log.i("error", msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.i("onFailure", "onFailure");
                        reRecyclerCalendarData(getCurrentMonthDay(mYear, mMonth, null));
                        mTvMonthMatter.setText("本月所有事件：0 件");
                    }
                }).build().get();

    }

    private void reRecyclerCalendarData(ArrayList<MultipleItemEntity> entities) {
        mRvCalendar.removeAllViews();
        final CalendarAdapter mCalendarAdapter = new CalendarAdapter(entities);
        mCalendarAdapter.setItemOnClick(this);
        mCalendarAdapter.setCurrentDay(mCurrentDay);
        mCalendarAdapter.setIsCurrentDay(isCurrentDay);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        mRvCalendar.setLayoutManager(manager);
        mRvCalendar.setAdapter(mCalendarAdapter);
        mCalendarAdapter.notifyDataSetChanged();
    }

    private void reRecyclerCalendarDataDetail(ArrayList<MultipleItemEntity> entities) {
        mRvDayMatter.removeAllViews();
        final CalendarAdapter mCalendarAdapter = new CalendarAdapter(entities);
        mCalendarAdapter.setItemOnClick(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDayMatter.setLayoutManager(manager);
        mRvDayMatter.setAdapter(mCalendarAdapter);
        mCalendarAdapter.notifyDataSetChanged();
    }

    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_form_erp;
    }

    /**
     * 通过年，月获取当前月的第一天为星期几 ，返回0是星期天，1是星期一，依次类推
     */
    private int getWeekDay(int year, int month) {
        int dayOfWeek;
        int goneYearDays = 0;
        int thisYearDays = 0;
//        boolean isLeapYear = false;//闰年
        int commonYearMonthDay[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int leapYearMonthDay[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 1900; i < year; i++) {// 从1900年开始算起，1900年1月1日为星期一
            if ((i % 4 == 0 && (i % 100 != 0)) || (i % 400 == 0)) {
                goneYearDays = goneYearDays + 366;
            } else {
                goneYearDays = goneYearDays + 365;
            }
        }
        if ((year % 4 == 0 && (year % 100 != 0)) || (year % 400 == 0)) {
//            isLeapYear = true;
            for (int i = 0; i < month - 1; i++) {
                thisYearDays = thisYearDays + leapYearMonthDay[i];
            }
        } else {
//            isLeapYear = false;
            for (int i = 0; i < month - 1; i++) {
                thisYearDays = thisYearDays + commonYearMonthDay[i];
            }
        }
        dayOfWeek = (goneYearDays + thisYearDays + 1) % 7;

        Log.d(this.getClass().getName(), "从1990到现在有" + (goneYearDays + thisYearDays + 1) + "天");
        Log.d(this.getClass().getName(), year + "年" + month + "月" + 1 + "日是星期" + dayOfWeek);
        return dayOfWeek;
    }

    /**
     * 通过年月，获取这个月一共有多少天
     */
    private int getDays(int year, int month) {
        int days = 0;

        if ((year % 4 == 0 && (year % 100 != 0)) || (year % 400 == 0)) {
            if (month > 0 && month <= 12) {
                days = leapYearMonthDay[month - 1];
            }
        } else {
            if (month > 0 && month <= 12) {
                days = commonYearMonthDay[month - 1];
            }
        }
        return days;
    }

    private ArrayList<MultipleItemEntity> getCurrentMonthDay(int year, int month, HashMap<Integer, ArrayList<MultipleItemEntity>> dayData) {
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final int days = getDays(year, month);
        final int weekDay = getWeekDay(year, month);
        for (int i = 1; i < (weekDay == 0 ? 7 : weekDay); i++) {
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, CalendarItemType.CALENDAR_ITEM)
                    .setField(MultipleFields.TEXT, "")
                    .setField(MultipleFields.SPAN_SIZE, 7).build();
            entities.add(entity);
        }
        for (int i = 1; i <= days; i++) {
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, CalendarItemType.CALENDAR_ITEM)
                    .setField(MultipleFields.TEXT, String.valueOf(i))
                    .setField(MultipleFields.TAG, i == mCurrentDay)
                    .setField(MultipleFields.SPAN_SIZE, 7).build();
            if (dayData != null && dayData.containsKey(i)) {
                entity.setField(CalendarItemFields.DAY_DATA, dayData.get(i));
            }
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public void calendarItemOnClick(String time, ArrayList<MultipleItemEntity> entities) {
        if (entities.size() > 0) {
            mRvDayMatter.setVisibility(View.VISIBLE);
            mTvDayMatter.setVisibility(View.GONE);
            reRecyclerCalendarDataDetail(entities);
        } else {
            mRvDayMatter.setVisibility(View.GONE);
            mTvDayMatter.setVisibility(View.VISIBLE);
            mTvDayMatter.setText(time + "没有要处理事件");
        }

    }
}
