package com.yuanting.n2share.sign;

/**
 * Created on 2018/9/26 10:34
 * Created by 薛立民
 * TEL 13262933389
 */
public interface ISignModel {
    /**
     * 数据请求成功
     * @param msg 请求到的数据错误信息
     */
    void onSuccessError(String msg);
    /**
     * 数据请求成功
     * @param data 请求到的数据
     */
    void onSuccessResult(String data);
    /**
     *  使用网络API接口请求方式时，虽然已经请求成功但是由
     *  于{@code msg}的原因无法正常返回数据。
     */
    void onFailure(String msg);
    /**
     * 请求数据失败，指在请求网络API接口请求方式时，出现无法联网、
     * 缺少权限，内存泄露等原因导致无法连接到请求数据源。
     */
    void onError(String msg);

}
