package com.yuanting.n2erp.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2erp.jsonUtils.JsonUtils;
import com.yuanting.yunting_core.app.AccountManager;

/**
 * Created on 2018/5/2 10:20
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignHandler {

    public static void onSignUp(String response, ISignListener signListener) {
//        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
//        final long userId = profileJson.getLong("userId");
//        final String name = profileJson.getString("name");
//        final String avatar = profileJson.getString("avatar");
//        final String gender = profileJson.getString("gender");
//        final String address = profileJson.getString("address");
//        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
//        DatabaseManager.getInstance().getDao().insert(profile);
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }

    public static void onSignIn(String response, ISignListener signListener) {

        final JSONObject profileJson = JSON.parseArray(JsonUtils.getDecodeJSONStr(response)).getJSONObject(0);
        final String UserID = profileJson.getString("UserID");
        final String UserName = profileJson.getString("UserName");
        final String RegistTime = profileJson.getString("RegistTime");
        final String Password = profileJson.getString("Password");
        final String Permissions = profileJson.getString("Permissions");
        final String TextInfo = profileJson.getString("TextInfo");
        final String UserInfos = profileJson.getString("UserInfos");
        AccountManager.setSignState(true);
        AccountManager.setID(UserID);
        AccountManager.setUserName(UserName);
        AccountManager.setRegistTime(RegistTime);
        AccountManager.setPassword(Password);
        AccountManager.setPermissions(Permissions);
        AccountManager.setTextInfo(TextInfo);
        AccountManager.setCreateTime(UserInfos);
        AccountManager.setOwner(UserInfos.substring(UserInfos.indexOf("<Owner>") + "<Owner>".length(), UserInfos.indexOf("</Owner>")));
        AccountManager.setMony(UserInfos.substring(UserInfos.indexOf("<Mony>") + "<Mony>".length(), UserInfos.indexOf("</Mony>")));
        signListener.onSignInSuccess();
    }


}
