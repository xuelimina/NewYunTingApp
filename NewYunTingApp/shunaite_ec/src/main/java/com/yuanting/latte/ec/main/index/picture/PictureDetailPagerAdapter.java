package com.yuanting.latte.ec.main.index.picture;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created on 2018/8/6 17:50
 * Created by 薛立民
 * TEL 13262933389
 */
public class PictureDetailPagerAdapter extends PagerAdapter{
    private ArrayList<MultipleItemEntity> data;

    public PictureDetailPagerAdapter(ArrayList<MultipleItemEntity> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
