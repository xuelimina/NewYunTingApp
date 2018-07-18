package com.yuanting.yunting_core.util.timer;

import java.util.TimerTask;

/**
 * Created on 2018/4/26 15:09
 * Created by 薛立民
 * TEL 13262933389
 */
public class BaserTimerTask extends TimerTask {
    private ITimerListener mITimerLIstener = null;

    public BaserTimerTask(ITimerListener timerListener) {
        this.mITimerLIstener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerLIstener != null) {
            mITimerLIstener.onTimer();
        }
    }
}
