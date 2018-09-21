package com.yuanting.n2erp.main.setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.n2erp.R;

/**
 * Created on 2018/9/19 16:22
 * Created by 薛立民
 * TEL 13262933389
 */
public class DialogEditView extends Dialog implements View.OnClickListener {
    private Callback callback;
    private AppCompatEditText editText;
    private AppCompatTextView textView;

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.dialog_btn_ok) {
            final String name = editText.getText().toString();
            if (name.isEmpty()) {
                editText.setError("请填写名称");
            } else {
                editText.setError(null);
                callback.callback(name);
            }
        } else if (id == R.id.dialog_btn_cancel) {
            dismiss();
        }
    }

    public interface Callback {
        void callback(String name);
    }

    protected DialogEditView(@NonNull Context context) {
        super(context);
    }

    protected DialogEditView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogEditView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        editText = findViewById(R.id.et_name);
        textView = findViewById(R.id.dialog_title);
        final AppCompatButton btnOk = findViewById(R.id.dialog_btn_ok);
        final AppCompatButton btnCancel = findViewById(R.id.dialog_btn_cancel);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    public void setTitle(String title) {
        textView.setText(title);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

}
