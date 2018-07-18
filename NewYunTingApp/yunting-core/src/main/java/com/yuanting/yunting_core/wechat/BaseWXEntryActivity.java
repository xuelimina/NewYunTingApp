package com.yuanting.yunting_core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

/**
 * Created on 2018/5/3 11:28
 * Created by 薛立民
 * TEL 13262933389
 */
public abstract class BaseWXEntryActivity extends BaseWXActivity {
    //用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {
        RestClient.builder().url(authUrl).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                final JSONObject authObj = JSON.parseObject(response);
                final String accessToken = authObj.getString("access_token");
                final String openId = authObj.getString("openid");
                final StringBuilder userInfoUrl = new StringBuilder();
                userInfoUrl
                        .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                        .append(accessToken)
                        .append("&openid=")
                        .append(openId)
                        .append("&lang=")
                        .append("zh_CN");
                getUserInfo(userInfoUrl.toString());
            }
        }).build().get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient.builder().url(userInfoUrl).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                onSignInSuccess(response);
            }
        }).failure(new IFailure() {
            @Override
            public void onFailure() {

            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

            }
        }).build().get();
    }
}
