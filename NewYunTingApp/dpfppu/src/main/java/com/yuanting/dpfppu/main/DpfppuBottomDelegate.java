package com.yuanting.dpfppu.main;

import android.graphics.Color;

import com.yuanting.dpfppu.R;
import com.yuanting.dpfppu.main.index.IndexDelegate;
import com.yuanting.dpfppu.main.product.SelectProductDelegate;
import com.yuanting.yunting_core.delegates.bottom.BaseBottomDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomTabBean;
import com.yuanting.yunting_core.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * Created on 2018/7/27 18:40
 * Created by 薛立民
 * TEL 13262933389
 */
public class DpfppuBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{icon-index}","{icon-index-shadow}",  "首页"), new IndexDelegate());
        items.put(new BottomTabBean("{icon-query}", "{icon-query-shadow}", "报价查询"), new SelectProductDelegate());
        items.put(new BottomTabBean("{icon-league}", "{icon-league-shadow}","品牌加盟"), new IndexDelegate());
        return items;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int selectTextColor() {
        return Color.WHITE;
    }

    @Override
    public int defaultTextColor() {
        return Color.WHITE;
    }
    @Override
    public int setBottomBarDelegateLayoutId() {
        return R.layout.delegate_bottom_dpf;
//        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[ITEMS.size()]);
//        getSupportDelegate().loadMultipleRootFragment(R.layout.delegate_bottom_dpf, setIndexDelegate(), delegateArray);
    }
}
