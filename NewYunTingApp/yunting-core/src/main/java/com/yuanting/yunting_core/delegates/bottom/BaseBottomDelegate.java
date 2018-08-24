package com.yuanting.yunting_core.delegates.bottom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joanzapata.iconify.widget.IconTextView;
import com.yuanting.yunting_core.R;
import com.yuanting.yunting_core.R2;
import com.yuanting.yunting_core.app.ConfigKeys;
import com.yuanting.yunting_core.app.Latte;
import com.yuanting.yunting_core.delegates.LatteDelegate;
import com.yuanting.yunting_core.net.RestClient;
import com.yuanting.yunting_core.net.callback.IError;
import com.yuanting.yunting_core.net.callback.IFailure;
import com.yuanting.yunting_core.net.callback.ISuccess;
import com.yuanting.yunting_core.net.firupdate.FirUpdateUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created on 2018/5/3 13:53
 * Created by 薛立民
 * TEL 13262933389
 */
public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    protected final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    protected final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    @Override
    public Object setLayout() {
        return setBottomBarDelegateLayoutId();
    }

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    public abstract int selectTextColor();

    public abstract int defaultTextColor();

    public abstract int setBottomBarDelegateLayoutId();

    private FirUpdateUtils firUpdateUtils;
    private JSONObject updateJson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
        firUpdateUtils = FirUpdateUtils.getInstance();
        isUpdate();
    }

    public void isUpdate() {
        RestClient.builder().url("http://api.fir.im/apps/latest/" + Latte.getConfiguration(ConfigKeys.FIR_APP_ID) + "?")
                .params("api_token", Latte.getConfiguration(ConfigKeys.FIR_API_TOKEN))
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        updateJson = JSON.parseObject(response);
                        final int updateVersion = updateJson.getInteger("version");
                        final int localVersion = Latte.getConfiguration(ConfigKeys.APP_CODE);
                        if (updateVersion > localVersion) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                final boolean isRead = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == 0;
                                final boolean isWrite = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0;
                                if (!isRead && !isWrite) {
                                    readFileWithCheck();
                                } else {
                                    firUpdateUtils.updateDialog(updateJson);
                                }
                            } else {
                                firUpdateUtils.updateDialog(updateJson);
                            }
                        }
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
            }
        }).failure(new IFailure() {
            @Override
            public void onFailure() {
            }
        }).build().get();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0) {
            firUpdateUtils.updateDialog(updateJson);
        } else if (grantResults[0] == -1) {
            new AlertDialog.Builder(getActivity())
                    .setPositiveButton("去应用管理中设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            getActivity().finish();
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("有新版本需要更新需要存储权限，否则无法使用使用")
                    .show();
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemIcon.setTextColor(defaultTextColor());
            itemIcon.setBackgroundColor(Color.TRANSPARENT);
            itemTitle.setText(bean.getTitle());
            itemTitle.setTextColor(defaultTextColor());
            if (i == mIndexDelegate) {
                itemIcon.setText(bean.getIconSelect());
                itemIcon.setTextColor(selectTextColor());
                itemTitle.setTextColor(selectTextColor());
            }
        }
        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final BottomTabBean bean = TAB_BEANS.get(i);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemIcon.setText(bean.getIcon());
            itemIcon.setTextColor(defaultTextColor());
            itemTitle.setTextColor(defaultTextColor());
            itemIcon.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        final BottomTabBean bean = TAB_BEANS.get(tag);
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemIcon.setText(bean.getIconSelect());
        itemIcon.setTextColor(selectTextColor());
        itemTitle.setTextColor(selectTextColor());
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }
}
