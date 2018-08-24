package com.yuanting.n2erp.jsonUtils;

/**
 * Created on 2018/8/23 20:28
 * Created by 薛立民
 * TEL 13262933389
 */
public class JsonUtils {
    /**
     * 去掉数据 \
     *
     * @param s
     * @return
     */
    public static String getDecodeJSONStr(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '\\':
                    break;
                default:
                    sb.append(c);
            }
        }
        int length = sb.length();
        return sb.substring(1, length - 1);
    }
}
