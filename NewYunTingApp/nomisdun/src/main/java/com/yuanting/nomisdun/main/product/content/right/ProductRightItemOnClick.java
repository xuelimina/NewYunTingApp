package com.yuanting.nomisdun.main.product.content.right;

import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

/**
 * Created on 2018/6/25 18:36
 * Created by 薛立民
 * TEL 13262933389
 */
public interface ProductRightItemOnClick {
    void modelItemOnClick(MultipleItemEntity entity);

    void yearItemOnClick(MultipleItemEntity entity);

    void subModelItemOnClick(MultipleItemEntity entity);

    void seriesItemOnClick(MultipleItemEntity entity);

    void endItemOnClick(MultipleItemEntity entity);
}
