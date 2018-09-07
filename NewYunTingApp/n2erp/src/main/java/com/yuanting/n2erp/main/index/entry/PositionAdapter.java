package com.yuanting.n2erp.main.index.entry;

import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.main.index.stockOut.StockOutProductItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/9/5 15:36
 * Created by 薛立民
 * TEL 13262933389
 */
public class PositionAdapter extends MultipleRecyclerAdapter {
    private PositionItemOnClick itemOnClick;

    protected PositionAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM, R.layout.item_position);
    }

    public void setItemOnClick(PositionItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatCheckedTextView textView = holder.getView(R.id.ctv_select_name);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case StockOutProductItemType.STOCK_OUT_PRODUCT_ITEM:
                final String text = entity.getField(MultipleFields.TEXT);
                textView.setText(text);
                textView.setChecked((Boolean) entity.getField(MultipleFields.TAG));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textView.isChecked()) {
                            textView.setChecked(false);
                            entity.setField(MultipleFields.TAG, false);
                        } else {
                            textView.setChecked(true);
                            entity.setField(MultipleFields.TAG, true);
                        }
                        itemOnClick.positionItemOnClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
