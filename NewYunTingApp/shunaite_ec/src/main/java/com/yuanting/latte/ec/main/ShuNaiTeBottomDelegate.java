package com.yuanting.latte.ec.main;

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
        items.put(new BottomTabBean(R.drawable.index,R.drawable.index_select,"首页"), new IndexDelegate());
        items.put(new BottomTabBean(R.drawable.service,R.drawable.service_select,"联系我们"), new ContactUsDelegate());
        items.put(new BottomTabBean(R.drawable.vip,R.drawable.vip_select,"VIP客户"), new PersonalDelegate());
        items.put(new BottomTabBean(R.drawable.about,R.drawable.about_select,"关于我们"), new AboutDelegate());
//        items.put(new BottomTabBean(R.drawable.server_select), new SortDelegate());
//        items.put(new BottomTabBean(R.drawable.vip_select), new DiscoverDelegate());
//        items.put(new BottomTabBean(R.drawable.about_select), new ShopCartDelegate());
//        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

//    @Override
//    public int setClickedColor() {
//        return Color.parseColor("#ffff8800");
//    }
}
