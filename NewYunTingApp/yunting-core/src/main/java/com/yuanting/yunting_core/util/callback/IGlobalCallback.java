package com.yuanting.yunting_core.util.callback;

import android.support.annotation.Nullable;

/**
 * Created on 2018/5/29 13:26
 * Created by 薛立民
 * TEL 13262933389
 */
public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
