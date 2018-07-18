package com.yuanting.yunting_core.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.yuanting.yunting_core.R;
import com.yuanting.yunting_core.ui.laucher.LauncherHolderCreator;

import java.util.ArrayList;

/**
 * Created on 2018/5/4 16:15
 * Created by 薛立民
 * TEL 13262933389
 */
public class BannerCreator {
    public static void setDefaultString(ConvenientBanner<String> convenientBanne, ArrayList<String> banners
            , OnItemClickListener clickListener) {
        convenientBanne.setPages(new HolderCreator(), banners)//轮播图数据
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})//轮播图底部的点点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//轮播图点点的位置
                .setOnItemClickListener(clickListener)//设置点击事件
                .setPageTransformer(new DefaultTransformer())//轮播图样式
                .startTurning(3000)//shijian
                .setCanLoop(true);//是否继续轮播
    }
    public static void setDefaultInt(ConvenientBanner<Integer> convenientBanne, ArrayList<Integer> banners
            , OnItemClickListener clickListener) {
        convenientBanne.setPages(new LauncherHolderCreator(), banners)//轮播图数据
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})//轮播图底部的点点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//轮播图点点的位置
                .setOnItemClickListener(clickListener)//设置点击事件
                .setPageTransformer(new DefaultTransformer())//轮播图样式
                .startTurning(3000)//shijian
                .setCanLoop(true);//是否继续轮播
    }
}
