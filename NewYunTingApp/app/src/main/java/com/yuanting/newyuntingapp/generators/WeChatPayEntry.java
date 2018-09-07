package com.yuanting.newyuntingapp.generators;


import com.yuanting.yunting.annotations.PayEntryGenerator;
import com.yuanting.yunting_core.wechat.template.WXPayEntryTemplate;

/**
 * Created on 2018/5/2 18:09
 * Created by 薛立民
 * TEL 13262933389
 */
@PayEntryGenerator(packageName = "com.yuanting.newyuntingapp", payEntryTemplate = WXPayEntryTemplate.class)
public interface WeChatPayEntry {
}
