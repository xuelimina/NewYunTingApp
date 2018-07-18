package com.yuanting.latte.ec.main.index.video;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created on 2018/7/17 15:44
 * Created by 薛立民
 * TEL 13262933389
 */
public class JZVideoVideoView extends JZVideoPlayerStandard {
    public JZVideoVideoView(Context context) {
        super(context);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
    }

    @Override
    public void changeStartButtonSize(int size) {
        super.changeStartButtonSize(size);
    }

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    @Override
    public void onStatePreparingChangingUrl(int urlMapIndex, long seekToInAdvance) {
        super.onStatePreparingChangingUrl(urlMapIndex, seekToInAdvance);
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void showWifiDialog(int action) {
        super.showWifiDialog(action);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
    }

    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
    }

    @Override
    public void setSystemTimeAndBattery() {
        super.setSystemTimeAndBattery();
    }

    @Override
    public void onCLickUiToggleToClear() {
        super.onCLickUiToggleToClear();
    }

    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
    }

    @Override
    public void setBufferProgress(int bufferProgress) {
        super.setBufferProgress(bufferProgress);
    }

    @Override
    public void resetProgressAndTime() {
        super.resetProgressAndTime();
    }

    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
    }

    @Override
    public void changeUiToPreparing() {
        super.changeUiToPreparing();
    }

    @Override
    public void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
    }

    @Override
    public void changeUiToPlayingClear() {
        super.changeUiToPlayingClear();
    }

    @Override
    public void changeUiToPauseShow() {
        super.changeUiToPauseShow();
    }

    @Override
    public void changeUiToPauseClear() {
        super.changeUiToPauseClear();
    }

    @Override
    public void changeUiToComplete() {
        super.changeUiToComplete();
    }

    @Override
    public void changeUiToError() {
        super.changeUiToError();
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
        super.setAllControlsVisiblity(topCon, bottomCon, startBtn, loadingPro, thumbImg, bottomPro, retryLayout);
    }

    @Override
    public void updateStartImage() {
        super.updateStartImage();
    }

    @Override
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    @Override
    public void showVolumeDialog(float deltaY, int volumePercent) {
        super.showVolumeDialog(deltaY, volumePercent);
    }

    @Override
    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
    }

    @Override
    public void showBrightnessDialog(int brightnessPercent) {
        super.showBrightnessDialog(brightnessPercent);
    }

    @Override
    public void dismissBrightnessDialog() {
        super.dismissBrightnessDialog();
    }

    @Override
    public Dialog createDialogWithView(View localView) {
        return super.createDialogWithView(localView);
    }

    @Override
    public void startDismissControlViewTimer() {
        super.startDismissControlViewTimer();
    }

    @Override
    public void cancelDismissControlViewTimer() {
        super.cancelDismissControlViewTimer();
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
    }

    @Override
    public void dissmissControlView() {
        super.dissmissControlView();
    }

    @Override
    public Object getCurrentUrl() {
        return super.getCurrentUrl();
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
    }

    @Override
    public void startVideo() {
        super.startVideo();
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
    }

    @Override
    public void setState(int state) {
        super.setState(state);
    }

    @Override
    public void setState(int state, int urlMapIndex, int seekToInAdvance) {
        super.setState(state, urlMapIndex, seekToInAdvance);
    }

    @Override
    public void onStatePrepared() {
        super.onStatePrepared();
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void release() {
        super.release();
    }

    @Override
    public void initTextureView() {
        super.initTextureView();
    }

    @Override
    public void addTextureView() {
        super.addTextureView();
    }

    @Override
    public void removeTextureView() {
        super.removeTextureView();
    }

    @Override
    public void clearFullscreenLayout() {
        super.clearFullscreenLayout();
    }

    @Override
    public void clearFloatScreen() {
        super.clearFloatScreen();
    }

    @Override
    public void onVideoSizeChanged() {
        super.onVideoSizeChanged();
    }

    @Override
    public void startProgressTimer() {
        super.startProgressTimer();
    }

    @Override
    public void cancelProgressTimer() {
        super.cancelProgressTimer();
    }

    @Override
    public long getCurrentPositionWhenPlaying() {
        return super.getCurrentPositionWhenPlaying();
    }

    @Override
    public long getDuration() {
        return super.getDuration();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        super.onProgressChanged(seekBar, progress, fromUser);
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
    }

    @Override
    public boolean isCurrentPlay() {
        return super.isCurrentPlay();
    }

    @Override
    public boolean isCurrentJZVD() {
        return super.isCurrentJZVD();
    }

    @Override
    public void playOnThisJzvd() {
        super.playOnThisJzvd();
    }

    @Override
    public void autoFullscreen(float x) {
        super.autoFullscreen(x);
    }

    @Override
    public void autoQuitFullscreen() {
        super.autoQuitFullscreen();
    }

    @Override
    public void onEvent(int type) {
        super.onEvent(type);
    }

    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
    }

    public JZVideoVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
