package com.yuanting.n2share;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.yuanting.n2share.delegate.sign.SignInDelegate;
import com.yuanting.yunting_core.activites.ProxyActivity;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity {

    //进度条
    ProgressDialog progressDialog;
    TextView text;
    MvpPresenter presenter;

    @Override
    public LatteDelegate setRootDelegate() {
        return new SignInDelegate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
//        setContentView(R.layout.activity_main);
//        text = (TextView)findViewById(R.id.text);
//        // 初始化进度条
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("正在加载数据");
//        //初始化Presenter
//        presenter = new MvpPresenter(this);
//    }
//    // button 点击事件调用方法
//    public void getData(View view){
//        presenter.getData("normal");
//    }
//    // button 点击事件调用方法
//    public void getDataForFailure(View view){
//        presenter.getData("failure");
//    }
//    // button 点击事件调用方法
//    public void getDataForError(View view){
//        presenter.getData("error");
//    }
//    @Override
//    public void showLoading() {
//        if (!progressDialog.isShowing()) {
//            progressDialog.show();
//        }
    }
//    @Override
//    public void hideLoading() {
//        if (progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
//    @Override
//    public void showData(String data) {
//        text.setText(data);
//    }
//    @Override
//    public void showFailureMessage(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        text.setText(msg);
//    }
//    @Override
//    public void showErrorMessage() {
//        Toast.makeText(this, "网络请求数据出现异常", Toast.LENGTH_SHORT).show();
//        text.setText("网络请求数据出现异常");
//    }
}
