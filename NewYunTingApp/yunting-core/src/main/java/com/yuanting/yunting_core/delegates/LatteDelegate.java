package com.yuanting.yunting_core.delegates;

/**
 * Created on 2018/4/24 11:27
 * Created by 薛立民
 * TEL 13262933389
 */
@SuppressWarnings("unchecked")
public abstract class LatteDelegate extends PermissionCheckerDelegate {
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
