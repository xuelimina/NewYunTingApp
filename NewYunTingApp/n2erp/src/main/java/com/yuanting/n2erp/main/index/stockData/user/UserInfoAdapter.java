package com.yuanting.n2erp.main.index.stockData.user;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/9/11 16:00
 * Created by 薛立民
 * TEL 13262933389
 */
public class UserInfoAdapter extends MultipleRecyclerAdapter {
    private UserLevItemOnClick itemOnClick;

    public void setItemOnClick(UserLevItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    protected UserInfoAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(UserInfoItemType.USER_INFO_ITEM, R.layout.item_user_info_erp);
        addItemType(UserInfoItemType.USER_INFO_LEV_NAME_ITEM, R.layout.item_user_info_lever_name_erp);
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case UserInfoItemType.USER_INFO_ITEM:
                final AppCompatTextView userName = holder.getView(R.id.tv_user_name);
                final AppCompatTextView userPassword = holder.getView(R.id.tv_user_password);
                final AppCompatTextView leverName = holder.getView(R.id.tv_lever_name);
                final String name = entity.getField(UserInfoItemFields.USER_NAME);
                final String password = entity.getField(UserInfoItemFields.PASSWORD);
                final String levName = entity.getField(UserInfoItemFields.LEV);
                userName.setText(name);
                userPassword.setText(password);
                if (levName == null || levName.isEmpty()) {
                    leverName.setText("此账号数据格式错误，无权限等级");
                } else {
                    leverName.setText(levName);
                }

                break;
            case UserInfoItemType.USER_INFO_LEV_NAME_ITEM:
                final AppCompatTextView tvlevname = holder.getView(R.id.item_user_info_lever_name);
                final String levname = entity.getField(MultipleFields.NAME);
                tvlevname.setText(levname);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOnClick.userLevItemOnClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
