package com.yuanting.latte.ec.main.index.product.content.left;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.latte.ec.main.index.product.content.right.ProductRightItemFields;
import com.yuanting.latte.ec.main.index.product.content.right.ProductRightItemType;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;
import com.yuanting.yunting_ec.R;

import java.util.List;

/**
 * Created on 2018/6/25 14:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductLeftAdapter extends MultipleRecyclerAdapter {
    private ProductLeftItemOnClick mItemOnClick;

    public void setItemOnClick(ProductLeftItemOnClick itemOnClick) {
        this.mItemOnClick = itemOnClick;
    }

    public ProductLeftAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_MODEL_ITEM, R.layout.item_product_content_left);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_YEAR_ITEM, R.layout.item_product_content_left);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_SUB_MODEL_ITEM, R.layout.item_product_content_left);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_SERIES_ITEM, R.layout.item_product_content_left);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_END_ITEM, R.layout.item_product_content_left);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatTextView textView = holder.getView(R.id.item_select_left_content_title);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ProductRightItemType.PRODUCT_RIGHT_MODEL_ITEM:
                final String model = entity.getField(ProductRightItemFields.MODEL);
                textView.setText(model);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.leftModelItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_YEAR_ITEM:
                final String year = entity.getField(ProductRightItemFields.YEAR);
                textView.setText(year);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.leftYearItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_SUB_MODEL_ITEM:
                final String subModel = entity.getField(ProductRightItemFields.SUB_MODEL);
                textView.setText(subModel);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.leftSubModelItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_SERIES_ITEM:
                final String series = entity.getField(ProductRightItemFields.SERIES);
                textView.setText(series);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.leftSeriesItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_END_ITEM:
                final String lastName = entity.getField(ProductRightItemFields.LAST_NAME);
                textView.setText(lastName);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.leftEndItemOnClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
