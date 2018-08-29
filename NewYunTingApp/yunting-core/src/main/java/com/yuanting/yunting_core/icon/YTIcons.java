package com.yuanting.yunting_core.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created
 */

public enum YTIcons implements Icon {

    icon_scan('\ue502'),
    icon_index('\ue622'),
    icon_index_shadow('\ue60b'),
    icon_query('\ue621'),
    icon_query_shadow('\ue602'),
    icon_league('\ue6b2'),
    icon_league_shadow('\ue69a'),
    icon_contact_us('\ue618'),
    icon_contact_us_shadow('\ue559'),
    icon_vip('\ue60c'),
    icon_vip_shadow('\ue608'),
    icon_about_us('\ue607'),
    icon_about_us_shadow('\ue63f'),
    icon_setting('\ue677'),
    icon_setting_shadow('\ue74d'),
    icon_information('\ue501'),
    icon_information_shadow('\ue604'),
    icon_stock_in('\ue57f'),
    icon_stock_out('\ue620'),
    icon_stock_in_data('\ue61b'),
    icon_stock_out_data('\ue68c'),
    icon_stock_data('\ue679'),
    icon_form('\ue653'),
    icon_entry('\ue6a7'),
    icon_expect('\ue6c2'),
    icon_stock_data_data('\ue678'),
    icon_cooperation('\ue504'),
    icon_customer('\ue601'),
    icon_user_info('\ue640');
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
