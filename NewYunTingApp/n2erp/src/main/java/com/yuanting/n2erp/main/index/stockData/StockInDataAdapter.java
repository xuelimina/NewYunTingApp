package com.yuanting.n2erp.main.index.stockData;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.main.index.stockIn.ProductItemOnClick;
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
public class StockInDataAdapter extends MultipleRecyclerAdapter {
    private ProductItemOnClick mItemOnClick;

    public void setItemOnClick(ProductItemOnClick itemOnClick) {
        this.mItemOnClick = itemOnClick;
    }

    StockInDataAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(StockDataItemType.STOCK_OUT_DATA_DETAILS_ITEM, R.layout.item_stock_in_data_details_erp);
        addItemType(StockDataItemType.STOCK_OUT_DATA_STATISTICS_ITEM, R.layout.item_stock_data_statistics_erp);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case StockDataItemType.STOCK_OUT_DATA_DETAILS_ITEM:
                final AppCompatTextView tvId = holder.getView(R.id.tv_id_in);
                final AppCompatTextView tvName = holder.getView(R.id.tv_name_in);
                final AppCompatTextView tvNumber = holder.getView(R.id.tv_number_in);
                final AppCompatTextView tvUnit = holder.getView(R.id.tv_unit_in);
                final AppCompatTextView tvTime = holder.getView(R.id.tv_in_time_in);
                final AppCompatTextView tvUserId = holder.getView(R.id.tv_user_id_in);
                final AppCompatTextView tvTextInfo = holder.getView(R.id.tv_text_info_in);
                final String Id = entity.getField(MultipleFields.ID);
                final String Name = entity.getField(StockDataItemFields.NAME);
                final String Number = entity.getField(StockDataItemFields.NUMBER);
                final String Unit = entity.getField(StockDataItemFields.UNIT);
                final String Time = entity.getField(StockDataItemFields.TIME);
                final String UserId = entity.getField(StockDataItemFields.OPERATION_USER_ID);
                final String TextInfo = entity.getField(StockDataItemFields.INFO);
                tvId.setText(Id == null ? "" : Id);
                tvName.setText(Name == null ? "" : Name);
                tvNumber.setText(Number == null ? "" : Number);
                tvUnit.setText(Unit == null ? "" : Unit);
                tvTime.setText(Time == null ? "" : Time);
                tvUserId.setText(UserId == null ? "" : UserId);
                tvTextInfo.setText(TextInfo == null ? "" : TextInfo);
                break;
            case StockDataItemType.STOCK_OUT_DATA_STATISTICS_ITEM:
                final AppCompatTextView tvStatisticsName = holder.getView(R.id.tv_name);
                final AppCompatTextView tvStatisticsNumber = holder.getView(R.id.tv_number);
                final AppCompatTextView tvStatisticsUnit = holder.getView(R.id.tv_unit);
                tvStatisticsName.setText(entity.getField(StockDataItemFields.NAME).toString());
                tvStatisticsNumber.setText(entity.getField(StockDataItemFields.NUMBER).toString());
                tvStatisticsUnit.setText(entity.getField(StockDataItemFields.UNIT).toString());
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
