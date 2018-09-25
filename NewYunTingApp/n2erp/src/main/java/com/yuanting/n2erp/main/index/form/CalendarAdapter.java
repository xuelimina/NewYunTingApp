package com.yuanting.n2erp.main.index.form;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/9/5 20:23
 * Created by 薛立民
 * TEL 13262933389
 */
public class CalendarAdapter extends MultipleRecyclerAdapter {
    private CalendarItemOnClick mItemOnClick;
    private CalendarDetailsItemOnClick mDetailsItemOnClick;
    private boolean isCurrenDay = false;
    private int currentDay;

    public void setIsCurrentDay(boolean currentDay) {
        isCurrenDay = currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    protected CalendarAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(CalendarItemType.CALENDAR_ITEM, R.layout.item_calendar);
        addItemType(CalendarItemType.CALENDAR_DETAIL_ITEM, R.layout.item_calendar_detail);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case CalendarItemType.CALENDAR_ITEM:
                final AppCompatTextView tvIsCalendarDay = holder.getView(R.id.tv_is_calendar_day);
                final AppCompatTextView tvCalendarDay = holder.getView(R.id.tv_calendar_day);
                final String text = entity.getField(MultipleFields.TEXT);
                final ArrayList<MultipleItemEntity> dayData = entity.getField(CalendarItemFields.DAY_DATA);
                if (dayData != null && dayData.size() > 0) {
                    tvCalendarDay.setBackgroundResource(R.color.app_main_background_erp);
                    tvCalendarDay.setTextColor(Color.WHITE);
                } else {
                    tvCalendarDay.setTextColor(Color.BLACK);
                }
                if (text.isEmpty()) {
                    tvCalendarDay.setText("");
                } else {
                    tvCalendarDay.setText(text);
                    if (isCurrenDay && currentDay == Integer.valueOf(text)) {
                        tvIsCalendarDay.setText("今天");
                        tvIsCalendarDay.setBackgroundColor(Color.YELLOW);
                        tvCalendarDay.setTextColor(Color.BLACK);
                        mItemOnClick.calendarItemOnClick(text + "号", (dayData != null && dayData.size() > 0) ? dayData : new ArrayList<MultipleItemEntity>());
                    }
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayData != null && dayData.size() > 0) {
                                mItemOnClick.calendarItemOnClick(text + "号", dayData);
                            } else {
                                mItemOnClick.calendarItemOnClick(text + "号", new ArrayList<MultipleItemEntity>());
                            }
                        }
                    });
                }
                break;
            case CalendarItemType.CALENDAR_DETAIL_ITEM:
                final AppCompatTextView tvTime = holder.getView(R.id.tv_time);
                final AppCompatTextView tvPosition = holder.getView(R.id.tv_position);
                final AppCompatTextView tvBody = holder.getView(R.id.tv_body);
                final AppCompatTextView tvSite = holder.getView(R.id.tv_site);
                final AppCompatTextView tvMaterial = holder.getView(R.id.tv_material);
                final AppCompatTextView tvColor = holder.getView(R.id.tv_color);
                final AppCompatTextView tvIsFinished = holder.getView(R.id.tv_is_finished);
                final String time = entity.getField(CalendarItemFields.TIME);
                final String position = entity.getField(CalendarItemFields.POSITION);
                final String body = entity.getField(CalendarItemFields.BODY);
                final String site = entity.getField(CalendarItemFields.SITE);
                final String material = entity.getField(CalendarItemFields.MATERIAL);
                final String color = entity.getField(CalendarItemFields.COLOR);
                final String is_finished = entity.getField(CalendarItemFields.IS_FINISHED);
                tvTime.setText("时间:" + time);
                tvPosition.setText("接车地点:" + position);
                tvBody.setText("负责小组:" + body);
                tvSite.setText("施工部位:" + site);
                tvMaterial.setText("使用产品:" + material);
                tvColor.setText("颜色:" + color);
                tvIsFinished.setText("状态:" + (is_finished.equals("0") ? "未完成" : "已完成"));
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mDetailsItemOnClick.calendarDetailsItemOnClick(entity);
                        return true;
                    }
                });
                break;
            default:
                break;
        }
    }

    public void setItemOnClick(CalendarItemOnClick itemOnClick, CalendarDetailsItemOnClick calendarDetailsItemOnClick) {
        mItemOnClick = itemOnClick;
        mDetailsItemOnClick = calendarDetailsItemOnClick;
    }
}
