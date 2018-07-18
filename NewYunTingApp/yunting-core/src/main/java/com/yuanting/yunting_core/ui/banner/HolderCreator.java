package com.yuanting.yunting_core.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created on 2018/5/4 16:16
 * Created by 薛立民
 * TEL 13262933389
 */
public class HolderCreator implements CBViewHolderCreator<ImageHolder>{

    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
