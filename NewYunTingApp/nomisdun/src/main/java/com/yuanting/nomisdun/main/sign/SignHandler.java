package com.yuanting.nomisdun.main.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.yunting_core.app.AccountManager;

/**
 * Created on 2018/5/2 10:20
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignHandler {

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
