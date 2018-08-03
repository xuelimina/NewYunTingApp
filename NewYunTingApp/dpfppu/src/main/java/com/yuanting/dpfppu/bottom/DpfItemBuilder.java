package com.yuanting.dpfppu.bottom;

import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;

import java.util.LinkedHashMap;

/**
 * Created on 2018/5/3 14:16
 * Created by 薛立民
 * TEL 13262933389
 */
public final class DpfItemBuilder {
    private final LinkedHashMap<DpfBottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    static DpfItemBuilder builder() {
        return new DpfItemBuilder();
    }

    public final DpfItemBuilder addItem(DpfBottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final DpfItemBuilder addItems(LinkedHashMap<DpfBottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<DpfBottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
