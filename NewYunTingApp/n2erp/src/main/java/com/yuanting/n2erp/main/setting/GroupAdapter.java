package com.yuanting.n2erp.main.setting;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yuanting.n2erp.R;
import com.yuanting.n2erp.main.index.entry.PositionItemOnClick;
import com.yuanting.yunting_core.ui.recycler.MultipleFields;
import com.yuanting.yunting_core.ui.recycler.MultipleItemEntity;
import com.yuanting.yunting_core.ui.recycler.MultipleRecyclerAdapter;
import com.yuanting.yunting_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created on 2018/9/5 15:36
 * Created by 薛立民
 * TEL 13262933389
 */
public class GroupAdapter extends MultipleRecyclerAdapter {
    private PositionItemOnClick itemOnClick;

    protected GroupAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(GroupItemType.GROUP_ITEM, R.layout.item_group);
        addItemType(GroupItemType.GROUP_DETAIL_ITEM, R.layout.item_group_detail);
    }

    public void setItemOnClick(PositionItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case GroupItemType.GROUP_ITEM:
                final AppCompatTextView group_name = holder.getView(R.id.tv_group_name);
                final AppCompatTextView group_member = holder.getView(R.id.tv_group_member);
                final AppCompatTextView leader_name = holder.getView(R.id.tv_group_leader_name);
                final AppCompatButton btn_manage = holder.getView(R.id.btn_manage);
                final String name = entity.getField(MultipleFields.NAME);
                final String body = entity.getField(GroupItemFields.BODYS);
                group_name.setText(name + "组");
                if (body.isEmpty()) {
                    group_member.setText("0 人");
                    leader_name.setText("");
                } else {
                    if (body.contains(",")) {
                        final String[] bodys = body.split(",");
                        group_member.setText(bodys.length + " 人");
                        leader_name.setText(bodys[0]);
                    } else {
                        group_member.setText(1 + " 人");
                        leader_name.setText(body);
                    }
                }
                btn_manage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOnClick.positionItemOnClick(entity);
                    }
                });
                break;
            case GroupItemType.GROUP_DETAIL_ITEM:
                final AppCompatTextView tv_group_detail_name = holder.getView(R.id.tv_group_detail_name);
                final AppCompatTextView tv_group_detail_post = holder.getView(R.id.tv_group_detail_post);
                final AppCompatButton btn_de_group_detail = holder.getView(R.id.btn_de_group_detail);
                final int position = getData().indexOf(entity);
                tv_group_detail_name.setText(entity.getField(MultipleFields.NAME).toString());
                if (position == 0) {
                    tv_group_detail_post.setText("组长");
                } else {
                    tv_group_detail_post.setText("施工人员");
                }
                btn_de_group_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOnClick.positionItemOnClick(entity);
                    }
                });
                break;
            default:
                break;
        }
    }
}
