package com.yuanting.latte.ec.main;

import android.graphics.Color;

import com.yuanting.latte.ec.main.about.AboutDelegate;
import com.yuanting.latte.ec.main.contact.ContactUsDelegate;
import com.yuanting.latte.ec.main.index.IndexDelegate;
import com.yuanting.latte.ec.main.personal.PersonalDelegate;
import com.yuanting.yunting_core.delegates.bottom.BaseBottomDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomItemDelegate;
import com.yuanting.yunting_core.delegates.bottom.BottomTabBean;
import com.yuanting.yunting_core.delegates.bottom.ItemBuilder;
import com.yuanting.yunting_ec.R;

import java.util.LinkedHashMap;

/**
 * Created on 2018/5/3 16:31
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShuNaiTeBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{icon-index}","{icon-index-shadow}",  "首页"), new IndexDelegate());
        items.put(new BottomTabBean("{icon-contact-us}","{icon-contact-us-shadow}",  "联系我们"), new ContactUsDelegate());
        items.put(new BottomTabBean("{icon-about-us}","{icon-about-us-shadow}",  "关于我们"), new AboutDelegate());
        items.put(new BottomTabBean("{icon-vip}","{icon-vip-shadow}",  "VIP客户"), new PersonalDelegate());
        return builder.addItems(items).build();
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
        return R.layout.delegate_bottom;
    }


}
