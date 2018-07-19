package com.yuanting.nomisdun.main.product.content.right;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.nomisdun.R;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/6/25 14:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductRightAdapter extends MultipleRecyclerAdapter {
    private ProductRightItemOnClick mItemOnClick;

    public void setItemOnClick(ProductRightItemOnClick itemOnClick) {
        this.mItemOnClick = itemOnClick;
    }

    public ProductRightAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_MODEL_ITEM, R.layout.item_nomisdun_product_content_right);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_YEAR_ITEM, R.layout.item_nomisdun_product_content_right);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_SUB_MODEL_ITEM, R.layout.item_nomisdun_product_content_right);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_SERIES_ITEM, R.layout.item_nomisdun_product_content_right);
        addItemType(ProductRightItemType.PRODUCT_RIGHT_END_ITEM, R.layout.item_nomisdun_product_content_right);
    }


    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatTextView textView = holder.getView(R.id.item_content_title);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ProductRightItemType.PRODUCT_RIGHT_MODEL_ITEM:
                final String model = entity.getField(ProductRightItemFields.MODEL);
                textView.setText(model);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.modelItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_YEAR_ITEM:
                final String year = entity.getField(ProductRightItemFields.YEAR);
                textView.setText(year);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.yearItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_SUB_MODEL_ITEM:
                final String subModel = entity.getField(ProductRightItemFields.SUB_MODEL);
                textView.setText(subModel);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.subModelItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_SERIES_ITEM:
                final String series = entity.getField(ProductRightItemFields.SERIES);
                textView.setText(series);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.seriesItemOnClick(entity);
                    }
                });
                break;
            case ProductRightItemType.PRODUCT_RIGHT_END_ITEM:
                final String lastName = entity.getField(ProductRightItemFields.LAST_NAME);
                textView.setText(lastName);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.endItemOnClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
