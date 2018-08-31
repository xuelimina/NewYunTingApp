package com.yuanting.yunting_core.ui.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created on 2018/5/29 13:47
 * Created by 薛立民
 * TEL 13262933389
 */
public class DateDialogUtil {

    public interface IDateListener {

        void onDateChange(String date);
    }

    private IDateListener mDateListener = null;

    public DateDialogUtil setDateListener(IDateListener listener) {
        this.mDateListener = listener;
        return this;
    }

    public void showDialog(Context context) {
        final LinearLayout ll = new LinearLayout(context);
        final DatePicker picker = new DatePicker(context);
        final LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        picker.setLayoutParams(lp);
        final boolean[] is = {true};
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
        picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                is[0] = false;
                calendar.set(year, monthOfYear, dayOfMonth, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
                if (mDateListener != null) {
                    mDateListener.onDateChange(format.format(calendar.getTime()));
                }
            }
        });

        ll.addView(picker);

        new AlertDialog.Builder(context)
                .setTitle("选择日期")
                .setView(ll)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (is[0]) {
                            mDateListener.onDateChange(format.format(calendar.getTime()));
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
