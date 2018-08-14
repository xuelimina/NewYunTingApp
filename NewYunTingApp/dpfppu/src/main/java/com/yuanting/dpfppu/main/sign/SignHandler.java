package com.yuanting.dpfppu.main.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.app.AccountManager;

/**
 * Created on 2018/5/2 10:20
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignHandler {

    public static void onSignUp(String response ,ISignListener signListener) {
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
    public static void onSignIn(String response ,ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response);
        final String ID = profileJson.getString("ID");
        final String PartnerID = profileJson.getString("PartnerID");
        final String Account = profileJson.getString("Account");
        final String Password = profileJson.getString("Password");
        final String Area = profileJson.getString("Area");
        final boolean Status = profileJson.getBoolean("Status");
        final String CreateTime = profileJson.getString("CreateTime");
        AccountManager.setSignState(true);
        AccountManager.setID(ID);
        AccountManager.setPartnerID(PartnerID);
        AccountManager.setAccount(Account);
        AccountManager.setPassword(Password);
        AccountManager.setArea(Area);
        AccountManager.setStatus(Status);
        AccountManager.setCreateTime(CreateTime);
        signListener.onSignInSuccess();
    }
}
