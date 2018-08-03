package com.yuanting.yunting_core.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created
 */

public enum YTIcons implements Icon {

    icon_scan('\ue502'),
    icon_vip_shadow('\ue608') ,
    icon_index('\ue622'),
    icon_index_shadow('\ue60b'),
    icon_vip('\ue60c'),
    icon_league('\ue6b2'),
    icon_league_shadow('\ue69a'),
    icon_vip_2('\ue66c'),
    icon_query('\ue621'),
    icon_query_shadow('\ue602'),
    icon_contact_us('\ue60a'),
    icon_about_us('\ue625');

    private char character;

    YTIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
