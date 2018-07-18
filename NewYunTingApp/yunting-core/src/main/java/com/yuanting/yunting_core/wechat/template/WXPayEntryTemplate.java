package com.yuanting.yunting_core.wechat.template;

import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.yuanting.yunting_core.wechat.BaseWXPayEntryActivity;

/**
 * Created on 2018/5/2 18:06
 * Created by 薛立民
 * TEL 13262933389
 */
public class WXPayEntryTemplate extends BaseWXPayEntryActivity {

    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
