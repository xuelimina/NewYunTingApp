package com.yuanting.n2share;

/**
 * Created on 2018/9/17 11:02
 * Created by 薛立民
 * TEL 13262933389
 */
public interface MvpView {
    /**
     * 显示正在加载进度框
     */
    void showLoading();
    /**
     * 隐藏正在加载进度框
     */
    void hideLoading();
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void showData(String data);
    /**
     * 当数据请求失败后，调用此接口提示
     * @param msg 失败原因
     */
    void showFailureMessage(String msg);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage();
}
