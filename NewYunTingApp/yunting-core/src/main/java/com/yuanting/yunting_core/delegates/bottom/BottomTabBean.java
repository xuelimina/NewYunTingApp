package com.yuanting.yunting_core.delegates.bottom;

/**
 * Created on 2018/5/3 14:13
 * Created by 薛立民
 * TEL 13262933389
 */
public final class BottomTabBean {
    private final int ICON_SELECT_ID;
    private final int ICON_ID;
    private final CharSequence TITLE;

    public BottomTabBean(int iconId, int iconSelectId, CharSequence title) {
        this.ICON_ID = iconId;
        this.ICON_SELECT_ID = iconSelectId;
        this.TITLE = title;
    }

    public int getIconId() {
        return ICON_ID;
    }
    public int getIconSelectId() {
        return ICON_SELECT_ID;
    }
    public CharSequence getTitle() {
        return TITLE;
    }
}
