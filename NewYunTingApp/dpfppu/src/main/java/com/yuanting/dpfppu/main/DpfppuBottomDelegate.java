package com.yuanting.dpfppu.main;

import com.yuanting.dpfppu.bottom.BaseBottomDpfDelegate;
import com.yuanting.dpfppu.bottom.DpfBottomTabBean;
import com.yuanting.dpfppu.bottom.DpfItemBuilder;
import com.yuanting.dpfppu.main.index.IndexDelegate;
import com.yuanting.dpfppu.main.product.SelectProductDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import java.util.LinkedHashMap;

/**
 * Created on 2018/7/27 18:40
 * Created by 薛立民
 * TEL 13262933389
 */
public class DpfppuBottomDelegate extends BaseBottomDpfDelegate {
    @Override
    public LinkedHashMap<DpfBottomTabBean, BottomItemDelegate> setItems(DpfItemBuilder builder) {
        final LinkedHashMap<DpfBottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new DpfBottomTabBean("{icon-index}","{icon-index-shadow}",  "首页"), new IndexDelegate());
        items.put(new DpfBottomTabBean("{icon-query}", "{icon-query-shadow}", "报价查询"), new SelectProductDelegate());
        items.put(new DpfBottomTabBean("{icon-league}", "{icon-league-shadow}","品牌加盟"), new IndexDelegate());
        return items;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }
}
