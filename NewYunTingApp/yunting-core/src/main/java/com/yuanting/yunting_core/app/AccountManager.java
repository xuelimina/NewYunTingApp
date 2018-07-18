package com.yuanting.yunting_core.app;


import com.yuanting.yunting_core.util.storage.LattePreference;

/**
 * Created on 2018/5/2 11:41
 * Created by 薛立民
 * TEL 13262933389
 */
public class AccountManager {
    private enum SignTag {
        SIGN_TAG,
        ID,
        PARTNER_ID,
        ACCOUNT,
        PASSWORD,
        AREA,
        STATUS,
        CREATE_TIME
    }

    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    public static void setID(String id) {
        LattePreference.addCustomAppProfile(SignTag.ID.name(), id);
    }

    public static void setPartnerID(String partnerID) {
        LattePreference.addCustomAppProfile(SignTag.PARTNER_ID.name(), partnerID);
    }

    public static void setAccount(String account) {
        LattePreference.addCustomAppProfile(SignTag.ACCOUNT.name(), account);
    }

    public static void setPassword(String password) {
        LattePreference.addCustomAppProfile(SignTag.PASSWORD.name(), password);
    }

    public static void setArea(String area) {
        LattePreference.addCustomAppProfile(SignTag.AREA.name(), area);
    }

    public static void setStatus(boolean status) {
        LattePreference.setAppFlag(SignTag.STATUS.name(), status);
    }

    public static void setCreateTime(String createTime) {
        LattePreference.addCustomAppProfile(SignTag.CREATE_TIME.name(), createTime);
    }

    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static String getID() {
        return LattePreference.getCustomAppProfile(SignTag.ID.name());
    }

    public static String getPartnerID() {
        return LattePreference.getCustomAppProfile(SignTag.PARTNER_ID.name());
    }

    public static String getAccount() {
        return LattePreference.getCustomAppProfile(SignTag.ACCOUNT.name());
    }

    public static String getPassword() {
        return LattePreference.getCustomAppProfile(SignTag.PASSWORD.name());
    }

    public static String getArea() {
        return LattePreference.getCustomAppProfile(SignTag.AREA.name());
    }

    public  boolean getStatus() {
        return LattePreference.getAppFlag(SignTag.STATUS.name());
    }

    public static String getCreateTime() {
        return LattePreference.getCustomAppProfile(SignTag.CREATE_TIME.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNoSignIn();
        }
    }
}
