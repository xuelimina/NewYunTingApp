package com.yuanting.yunting_core.delegates.web.event;

import com.yuanting.yunting_core.util.log.LatteLogger;

/**
 * Created on 2018/5/18 10:37
 * Created by 薛立民
 * TEL 13262933389
 */
public class UndefineEvent extends Event{
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefineEvent",params);
        return null;
    }
}
