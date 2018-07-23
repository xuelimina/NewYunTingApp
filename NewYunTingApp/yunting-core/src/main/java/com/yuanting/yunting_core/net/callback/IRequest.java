package com.yuanting.yunting_core.net.callback;

/**
 * Created on 2018/4/25 11:10
 * Created by 薛立民
 * TEL 13262933389
 */
public interface IRequest {
    void onRequestStart();
    void onRequestDowning(int process);
    void onRequestEnd();
}
