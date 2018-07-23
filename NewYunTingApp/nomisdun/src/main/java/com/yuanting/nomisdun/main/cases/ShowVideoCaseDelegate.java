package com.yuanting.nomisdun.main.cases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuanting.nomisdun.R;
import com.yuanting.nomisdun.R2;
import com.yuanting.yunting_core.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created on 2018/7/23 15:33
 * Created by 薛立民
 * TEL 13262933389
 */
public class ShowVideoCaseDelegate extends LatteDelegate{
    @BindView(R2.id.jzv_video)
    JZVideoPlayerStandard video;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        video.thumbImageView.setImageResource(R.drawable.nomisdun_video_1);
        video.fullscreenButton.setVisibility(View.GONE);
        String uri = "http://p8jpjjvcn.bkt.clouddn.com/nomisdun.mp4";
        video.setUp(uri, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "视频案例");
    }
    @OnClick(R2.id.about_back)
    void back() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        _mActivity.onBackPressed();
    }
    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_nomisdun_show_video;
    }
}
