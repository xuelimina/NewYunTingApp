package com.yuanting.nomisdun.main.product.content.price;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yuanting.nomisdun.R;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/6/25 14:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductModelAdapter extends MultipleRecyclerAdapter {
    private final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate();
    private ProductModelItemOnClick mItemOnClick;

    public void setItemOnClick(ProductModelItemOnClick itemOnClick) {
        this.mItemOnClick = itemOnClick;
    }

    public ProductModelAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ProductModelItemType.PRODUCT_MODEL_MODEL_ITEM, R.layout.item_nomisdun_product_model);
    }


    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        final AppCompatTextView nameChView = holder.getView(R.id.product_model_name_ch);
        final AppCompatTextView priceView = holder.getView(R.id.product_model_price);
        final AppCompatTextView nameEhView = holder.getView(R.id.product_model_name_eh);
        final ImageView imageView = holder.getView(R.id.product_model_image_view);
        final View itemView = holder.itemView;
        switch (entity.getItemType()) {
            case ProductModelItemType.PRODUCT_MODEL_MODEL_ITEM:
                final String nameCh = entity.getField(ProductModelItemFields.NAME);
                final String price = entity.getField(ProductModelItemFields.PRICE);

                nameChView.setText(nameCh);
                nameEhView.setText(getEName(nameCh));
                priceView.setText("￥:" + price.substring(0,price.indexOf(".")));
                final boolean isSelected = entity.getField(ProductModelItemFields.IS_SELECTED);

                final String imageUrl;
                if (isSelected) {
                    imageUrl = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + entity.getField(ProductModelItemFields.SETIMG) + "/Nick2/";
                } else {
                    imageUrl = Latte.getConfiguration(ConfigKeys.API_HOST) + "Bss/QueryImg/" + entity.getField(ProductModelItemFields.SHOWIMG) + "/Nick2/";
                }
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into(imageView);

                final ArrayList<MultipleItemEntity> partentitns = entity.getField(ProductModelItemFields.PARTLIST);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClick.onModelItemClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 根据中文名匹配英文名
     *
     * @param name
     */
    private String getEName(String name) {
        String nameEh = "";
        switch (name) {
            case "整车":
                nameEh = "Body Kit (Whole vehicle)";
                break;
            case "左车身套装":
                nameEh = "Body Kit (Left only)";
                break;
            case "右车身套装":
                nameEh = "Body Kit (Right only)";
                break;
            case "前车身套装":
                nameEh = "Body Kit (Front only)";
                break;
            case "前杠":
                nameEh = "Front Bumper Kit";
                break;
            case "后杠":
                nameEh = "Rear Bumper Kit";
                break;
            case "机盖":
                nameEh = "Hood Kit";
                break;
            case "左前叶子板":
                nameEh = "Front Fender Kit (Left only)";
                break;
            case "右前叶子板":
                nameEh = "Front Fender Kit (Right only)";
                break;
            case "右后叶子板":
                nameEh = "Rear Fender Kit (Right only)";
                break;
            case "左后叶子板":
                nameEh = "Rear Fender Kit (Left only)";
                break;
            case "左门组合":
                nameEh = "Door Kit (Left only)";
                break;
            case "右门组合":
                nameEh = "Door Kit (Right only)";
                break;
            case "左侧裙":
                nameEh = "Rocker Panel Kit (Left only)";
                break;
            case "右侧裙":
                nameEh = "Rocker Panel Kit (Right only)";
                break;
            case "车顶":
                nameEh = "Roof Kit";
                break;
            case "后盖组合":
                nameEh = "Trunk Lid Kit";
                break;
            case "后视镜":
                nameEh = "Mirror Kit";
                break;

        }
        return nameEh;
    }
}
