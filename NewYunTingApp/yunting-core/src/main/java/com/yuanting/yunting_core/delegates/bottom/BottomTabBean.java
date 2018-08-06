package com.yuanting.yunting_core.delegates.bottom;

/**
 * Created on 2018/5/3 14:13
 * Created by 薛立民
 * TEL 13262933389
 */
public final class BottomTabBean {
    private final CharSequence ICON_SELECT;
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence iconSelect, CharSequence title) {
        this.ICON = icon;
        this.ICON_SELECT = iconSelect;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }
    public CharSequence getIconSelect() {
        return ICON_SELECT;
    }
    public CharSequence getTitle() {
        return TITLE;
    }
}
