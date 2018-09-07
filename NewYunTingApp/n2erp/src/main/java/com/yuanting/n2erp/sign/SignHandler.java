package com.yuanting.n2erp.sign;

import android.util.Log;

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
        Log.i("response", "" + response);
        final String companyId = response.substring(response.indexOf("ID") + 3, response.indexOf(" 获得"));
        Log.i("id", "" + companyId);
        AccountManager.setSignState(false);
        AccountManager.setOwner(companyId);
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
        if (UserInfos.contains("<Number>"))
            AccountManager.setNumber(UserInfos.substring(UserInfos.indexOf("<Number>") + "<Number>".length(), UserInfos.indexOf("</Number>")));
        if (UserInfos.contains("<Address>"))
            AccountManager.setCompanyAddress(UserInfos.substring(UserInfos.indexOf("<Address>") + "<Address>".length(), UserInfos.indexOf("</Address>")));
        if (UserInfos.contains("<Name>"))
            AccountManager.setCompanyName(UserInfos.substring(UserInfos.indexOf("<Name>") + "<Name>".length(), UserInfos.indexOf("</Name>")));
        if (UserInfos.contains("<Owner>"))
            AccountManager.setOwner(UserInfos.substring(UserInfos.indexOf("<Owner>") + "<Owner>".length(), UserInfos.indexOf("</Owner>")));
        if (UserInfos.contains("<Lev>"))
            AccountManager.setLev(UserInfos.substring(UserInfos.indexOf("<Lev>") + "<Lev>".length(), UserInfos.indexOf("</Lev>")));
        if (UserInfos.contains("<FinnalDate>"))
            AccountManager.setFinnalDate(UserInfos.substring(UserInfos.indexOf("<FinnalDate>") + "<FinnalDate>".length(), UserInfos.indexOf("</FinnalDate>")));
        if (UserInfos.contains("<Mony>"))
            AccountManager.setLev(UserInfos.substring(UserInfos.indexOf("<Mony>") + "<Mony>".length(), UserInfos.indexOf("</Mony>")));
        signListener.onSignInSuccess();
    }


}
