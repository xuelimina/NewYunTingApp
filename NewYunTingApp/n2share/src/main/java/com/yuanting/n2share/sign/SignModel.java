package com.yuanting.n2share.sign;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanting.n2share.json.JsonResult;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;

/**
 * Created on 2018/9/26 10:34
 * Created by 薛立民
 * TEL 13262933389
 */
public class SignModel implements ISuccess, IError, IFailure {
    private final ISignModel iSignModel;
    private String TAG = getClass().getName();

    SignModel(ISignModel iSignModel) {
        this.iSignModel = iSignModel;
    }

    public void signIn(Context context, String name, String password) {
        RestClient.builder().url("Login/Login")
                .loader(context)
                .params("UserPhone", name).params("Password", password)
                .error(this)
                .failure(this)
                .success(this).build().post();

    }

    public void signUpSendRegisterMsg(Context context, String UserPhone) {
        RestClient.builder().url("Login/SendRegisterMsg")
                .loader(context)
                .params("UserPhone", UserPhone)
                .error(this)
                .failure(this)
                .success(this).build().post();

    }

    public void signUp(Context context, String UserPhone, String Code, String name, String password) {
        RestClient.builder().url("Login/Register")
                .loader(context)
                .params("UserPhone", UserPhone).params("Code", Code).params("Name", name).params("Password", password)
                .error(this)
                .failure(this)
                .success(this).build().post();

    }

    @Override
    public void onError(int code, String msg) {
        iSignModel.onError(msg);
    }

    @Override
    public void onFailure() {
        iSignModel.onFailure("");
    }

    @Override
    public void onSuccess(String response) {
        Log.i(TAG, response);
        final JSONObject object = JSON.parseObject(response);
        final String result = object.getString("Result");
        if (result.equals(JsonResult.SUCCESS)) {
            iSignModel.onSuccessResult(response);
        } else {
            iSignModel.onSuccessError(object.getString("Content"));
        }
    }
    public static void onSignUp(String response) {
        Log.i("response", "" + response);
        final String companyId = response.substring(response.indexOf("ID") + 3, response.indexOf(" 获得"));
        Log.i("id", "" + companyId);
        AccountManager.setSignState(false);
        AccountManager.setOwner(companyId);
    }

    public static void onSignIn(String response) {
        final JSONObject profileJson = JSON.parseArray(response).getJSONObject(0);
        final String UserID = profileJson.getString("UserID");
        final String UserName = profileJson.getString("UserName");
        final String RegistTime = profileJson.getString("RegistTime");
        final String Password = profileJson.getString("Password");
        final String Permissions = profileJson.getString("Permissions");
        final String TextInfo = profileJson.getString("TextInfo");
        final String UserInfos = profileJson.getString("UserInfos");
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

        if (UserInfos.contains("<Lev>")) {
            String Lev = UserInfos.substring(UserInfos.indexOf("<Lev>") + "<Lev>".length(), UserInfos.indexOf("</Lev>"));
            String newUserInfo = UserInfos.substring(UserInfos.indexOf("</Lev>") + "<Lev>".length(), UserInfos.length());
            if (newUserInfo.contains("<Lev>")) {
                Lev = Lev + "," + newUserInfo.substring(newUserInfo.indexOf("<Lev>") + "<Lev>".length(), newUserInfo.indexOf("</Lev>"));
            }
            AccountManager.setLev(Lev);
        }
        if (UserInfos.contains("<FinnalDate>")) {
            String fiannaDate = UserInfos.substring(UserInfos.indexOf("<FinnalDate>") + "<FinnalDate>".length(), UserInfos.indexOf("</FinnalDate>"));
            AccountManager.setFinnalDate(fiannaDate);
        }
        if (UserInfos.contains("<Mony>"))
            AccountManager.setMony(UserInfos.substring(UserInfos.indexOf("<Mony>") + "<Mony>".length(), UserInfos.indexOf("</Mony>")));
    }

}
