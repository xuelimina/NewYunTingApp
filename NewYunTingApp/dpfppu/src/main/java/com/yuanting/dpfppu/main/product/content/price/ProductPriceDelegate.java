package com.yuanting.dpfppu.main.product.content.price;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.R2;
import com.yuanting.dpfppu.main.product.content.right.ProductRightItemFields;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/6/26 12:51
 * Created by 薛立民
 * TEL 13262933389
 */
public class ProductPriceDelegate extends LatteDelegate implements ISuccess, IError, IFailure, ProductModelItemOnClick {
    @BindView(R2.id.product_model_recycler_view)
    RecyclerView mModelRecyclerView;
    @BindView(R2.id.price_text_view)
    AppCompatTextView priceTextView;
    private String id, make, model, subModel, series, year, lastName, lastModel, queryPackage;
    private ProductModelAdapter mAdapter;
    private ProductModelDataConverter mConverter;
    private boolean isComplete = false;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args != null) {
            id = args.getString(MultipleFields.ID.name());
            make = args.getString(ProductRightItemFields.MAKE.name());
            model = args.getString(ProductRightItemFields.MODEL.name());
            subModel = args.getString(ProductRightItemFields.SUB_MODEL.name());
            series = args.getString(ProductRightItemFields.SERIES.name());
            year = args.getString(ProductRightItemFields.YEAR.name());
            lastName = args.getString(ProductRightItemFields.LAST_NAME.name());
            lastModel = args.getString(ProductRightItemFields.LAST_MODEL.name());
            queryPackage = args.getString("QueryPackage");
            getProductModelContent(queryPackage, id, lastModel);
        }
    }

    private void getProductModelContent(String url, String id, String lastModel) {
        RestClient.builder()
                .url(url)
                .params("CarId", id)
                .params("productName", lastModel)
                .loader(getContext())
                .success(this)
                .failure(this)
                .build()
                .post();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_product_price_dpf;
    }

    @Override
    public void onError(int code, String msg) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSuccess(String response) {
//        Log.i("response", response);
        mConverter = new ProductModelDataConverter();
        mAdapter = new ProductModelAdapter(mConverter.setJsonData(response).convert());
        mAdapter.setItemOnClick(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mModelRecyclerView.setLayoutManager(manager);
        mModelRecyclerView.setAdapter(mAdapter);
    }
    @OnClick(R2.id.back)
    void back() {
        _mActivity.onBackPressed();
    }
    @Override
    public void onModelItemClick(MultipleItemEntity entity) {
        final int type = Integer.valueOf(entity.getField(ProductModelItemFields.TYPE).toString());
        final boolean currentSelected = entity.getField(ProductModelItemFields.IS_SELECTED);
        switch (type) {
            case 3:
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    MultipleItemEntity entity1 = mAdapter.getData().get(i);
                    entity1.setField(ProductModelItemFields.IS_SELECTED, false);
                }
                for (int j = 0; j < mAdapter.getData().size(); j++) {
                    MultipleItemEntity entity1 = mAdapter.getData().get(j);
                    if (!"2".equals(entity1.getField(ProductModelItemFields.TYPE)))
                        if (currentSelected) {
                            entity1.setField(ProductModelItemFields.IS_SELECTED, false);
                            entity.setField(ProductModelItemFields.IS_SELECTED, false);
                        } else {
                            entity1.setField(ProductModelItemFields.IS_SELECTED, true);
                            entity.setField(ProductModelItemFields.IS_SELECTED, true);
                        }
                }
                if (currentSelected) {
                    isComplete = false;
                    priceTextView.setText("￥:0.00");
                } else {
                    isComplete = true;
                }
                break;
            case 2:
                if (isComplete) {
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        MultipleItemEntity entity1 = mAdapter.getData().get(i);
                        entity1.setField(ProductModelItemFields.IS_SELECTED, false);
                    }
                }
                final ArrayList<MultipleItemEntity> partList = entity.getField(ProductModelItemFields.PARTLIST);
                for (int i = 0; i < partList.size(); i++) {
                    String bid = partList.get(i).getField(ProductModelPartItemFields.BID);
                    for (int j = 0; j < mAdapter.getData().size(); j++) {
                        MultipleItemEntity entity1 = mAdapter.getData().get(j);
                        if (bid.equals(entity1.getField(MultipleFields.ID))) {
                            if (currentSelected) {
                                entity1.setField(ProductModelItemFields.IS_SELECTED, false);
                            } else {
                                entity1.setField(ProductModelItemFields.IS_SELECTED, true);
                            }
                        }
                    }
                }
                if (currentSelected) {
                    entity.setField(ProductModelItemFields.IS_SELECTED, false);
                } else {
                    entity.setField(ProductModelItemFields.IS_SELECTED, true);
                }
                isComplete = false;
                break;
            case 1:
                String id = entity.getField(MultipleFields.ID);
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    MultipleItemEntity entity1 = mAdapter.getData().get(i);
                    boolean IS_SELECTED = entity1.getField(ProductModelItemFields.IS_SELECTED);
                    String type1 = entity1.getField(ProductModelItemFields.TYPE);
                    String entity1ID = entity1.getField(MultipleFields.ID);
                    if ("3".equals(type1) && IS_SELECTED) {
                        for (int j = 0; j < mAdapter.getData().size(); j++) {
                            mAdapter.getData().get(j).setField(ProductModelItemFields.IS_SELECTED, false);
                        }
                        entity.setField(ProductModelItemFields.IS_SELECTED, true);
                        break;
                    } else if ("2".equals(type1) && IS_SELECTED) {
                        final ArrayList<MultipleItemEntity> partEntitnes = entity1.getField(ProductModelItemFields.PARTLIST);
                        for (int j = 0; j < partEntitnes.size(); j++) {
                            MultipleItemEntity entity2 = partEntitnes.get(j);
                            String bid = entity2.getField(ProductModelPartItemFields.BID);
                            if (id.equals(bid)) {
                                entity1.setField(ProductModelItemFields.IS_SELECTED, false);
                                entity.setField(ProductModelItemFields.IS_SELECTED, false);
                                break;
                            }
                        }
                    } else if ("1".equals(type1) && entity1ID.equals(id)) {
                        if (currentSelected) {
                            entity.setField(ProductModelItemFields.IS_SELECTED, false);
                        } else {
                            entity.setField(ProductModelItemFields.IS_SELECTED, true);
                        }
                    }
                }
                break;
            default:
                break;
        }
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        priceTextView.setText("￥:" + (int)getPriceTotal());
    }

    private double getPriceTotal() {
        double total = 0;
        final ArrayList<String> type2entity = new ArrayList<>();
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            MultipleItemEntity entity = mAdapter.getData().get(i);
            boolean IS_SELECTED = entity.getField(ProductModelItemFields.IS_SELECTED);
            String type = entity.getField(ProductModelItemFields.TYPE);
            if ("2".equals(type) && IS_SELECTED) {
                final ArrayList<MultipleItemEntity> partEntitnes = entity.getField(ProductModelItemFields.PARTLIST);
                for (int j = 0; j < partEntitnes.size(); j++) {
                    type2entity.add(partEntitnes.get(j).getField(ProductModelPartItemFields.BID).toString());
                }
            }
        }
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            MultipleItemEntity entity = mAdapter.getData().get(i);
            String id = entity.getField(MultipleFields.ID);
            boolean IS_SELECTED = entity.getField(ProductModelItemFields.IS_SELECTED);
            String type = entity.getField(ProductModelItemFields.TYPE);
            if ("3".equals(type) && IS_SELECTED) {
                return Double.valueOf(entity.getField(ProductModelItemFields.PRICE).toString());
            } else if ("2".equals(type) && IS_SELECTED) {
                total += Double.valueOf(entity.getField(ProductModelItemFields.PRICE).toString());
            } else if ("1".equals(type) && IS_SELECTED) {
                if (!type2entity.contains(id)) {
                    total += Double.valueOf(entity.getField(ProductModelItemFields.PRICE).toString());
                }
            }
        }
        return total;
    }
}
