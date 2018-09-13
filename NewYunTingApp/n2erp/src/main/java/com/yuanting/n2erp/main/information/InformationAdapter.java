package com.yuanting.n2erp.main.information;

import android.support.v7.widget.AppCompatTextView;

import com.yuanting.n2erp.R;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/9/13 14:08
 * Created by 薛立民
 * TEL 13262933389
 */
public class InformationAdapter extends MultipleRecyclerAdapter {
    public static final int INFORMATION_TYPE = 51;

    protected InformationAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(INFORMATION_TYPE, R.layout.item_information);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case INFORMATION_TYPE:
                final AppCompatTextView tvName = holder.getView(R.id.tv_information_content);
                final AppCompatTextView tvTime = holder.getView(R.id.tv_information_time);
                final String name = entity.getField(MultipleFields.TITLE);
                final String time = entity.getField(MultipleFields.TEXT);
                tvName.setText(name);
                tvTime.setText(time);
                break;
            default:
                break;
        }
    }
}
