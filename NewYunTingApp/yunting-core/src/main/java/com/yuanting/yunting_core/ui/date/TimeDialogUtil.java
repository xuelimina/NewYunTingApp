package com.yuanting.yunting_core.ui.date;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created on 2018/5/29 13:47
 * Created by 薛立民
 * TEL 13262933389
 */
public class TimeDialogUtil {

    public interface ITimeListener {

        void onTimeChange(String time);
    }

    private ITimeListener mTimeListener = null;

    public TimeDialogUtil setTimeListener(ITimeListener listener) {
        this.mTimeListener = listener;
        return this;
    }

    public void showDialog(Context context) {
        final Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (mTimeListener != null) {
                    mTimeListener.onTimeChange("" + hourOfDay + ":" + minute);
                }
            }
        } , calendar.get(Calendar.HOUR_OF_DAY) , calendar.get(Calendar.MINUTE) , true).show();
    }

}
