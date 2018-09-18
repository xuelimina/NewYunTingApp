package com.yuanting.n2share.generators;


import com.yuanting.yunting.annotations.PayEntryGenerator;
import com.yuanting.yunting_core.wechat.template.WXPayEntryTemplate;

/**
 * Created on 2018/5/2 18:09
 * Created by 薛立民
 * TEL 13262933389
 */
@PayEntryGenerator(packageName = "com.yuanting.n2share", payEntryTemplate = WXPayEntryTemplate.class)
public interface WeChatPayEntry {
}
