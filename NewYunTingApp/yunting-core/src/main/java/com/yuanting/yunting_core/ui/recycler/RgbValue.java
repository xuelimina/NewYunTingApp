package com.yuanting.yunting_core.ui.recycler;


import com.google.auto.value.AutoValue;

/**
 * Created on 2018/5/7 15:28
 * Created by 薛立民
 * TEL 13262933389
 */
@AutoValue
public abstract class RgbValue {
    public abstract int red();

    public abstract int greed();

    public abstract int bule();

    public static RgbValue create(int red, int green, int bule) {
        return new AutoValue_RgbValue(red, green, bule);
    }
}
