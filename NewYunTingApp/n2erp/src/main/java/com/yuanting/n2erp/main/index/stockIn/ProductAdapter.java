package com.yuanting.n2erp.main.index.stockIn;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/6/25 14:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductAdapter extends MultipleRecyclerAdapter {
    private ProductItemOnClick mItemOnClick;

    public void setItemOnClick(ProductItemOnClick itemOnClick) {
        this.mItemOnClick = itemOnClick;
    }

    public ProductAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ProductItemType.PRODUCT_ITEM, R.layout.item_product_erp);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatTextView textView = holder.getView(R.id.item_select_product_title);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ProductItemType.PRODUCT_ITEM:
                final String text = entity.getField(MultipleFields.TEXT);
                textView.setText(text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.productContent(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
