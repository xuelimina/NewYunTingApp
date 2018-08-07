package com.yuanting.latte.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.yuanting.latte.ec.main.personal.list.ListBean;
import com.yuanting.latte.ec.sign.SignInDelegate;
import com.yuanting.yunting_core.app.AccountManager;
import com.yuanting.yunting_core.app.IUserChecker;
import com.yuanting.yunting_core.delegates.LatteDelegate;

/**
 */

public class PersonalClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public PersonalClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //TODO 检查用户是否已经登录
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onSignIn() {
                        DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                    }

                    @Override
                    public void onNoSignIn() {
                        DELEGATE.getParentDelegate().getSupportDelegate().start(new SignInDelegate());
                    }
                });

                break;
            case 2:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 3:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
