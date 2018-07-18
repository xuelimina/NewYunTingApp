package com.yuanting.yunting_core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.yuanting.yunting_core.app.Latte;


/**
 * Created on 2018/4/25 14:04
 * Created by 薛立民
 * TEL 13262933389
 */
public class DimenUtil {
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
