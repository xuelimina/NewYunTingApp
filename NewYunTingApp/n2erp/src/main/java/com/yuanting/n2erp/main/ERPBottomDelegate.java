package com.yuanting.n2erp.main;

import android.graphics.Color;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.main.index.IndexDelegate;
import com.yuanting.n2erp.main.information.InformationDelegate;
import com.yuanting.n2erp.main.setting.SettingDelegate;
import com.yuanting.yunting_core.delegates.bottom.BaseBottomDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomTabBean;
import com.yuanting.yunting_core.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * Created on 2018/5/3 16:31
 * Created by 薛立民
 * TEL 13262933389
 */
public class ERPBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{icon-index}","{icon-index-shadow}",  "首页"), new IndexDelegate());
        items.put(new BottomTabBean("{icon-information}","{icon-information-shadow}",  "消息"), new InformationDelegate());
        items.put(new BottomTabBean("{icon-setting}","{icon-setting-shadow}",  "设置"), new SettingDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int selectTextColor() {
        return Color.parseColor("#555555");
    }

    @Override
    public int defaultTextColor() {
        return Color.parseColor("#d5d5d8");
    }

    @Override
    public int setBottomBarDelegateLayoutId() {
        return R.layout.delegate_bottom_erp;
    }


}
