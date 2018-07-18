package com.yuanting.yunting_core.ui.camera;

import android.net.Uri;

import com.yuanting.yunting_core.delegates.PermissionCheckerDelegate;
import com.yuanting.yunting_core.util.file.FileUtil;

/**
 * Created on 2018/7/12 11:26
 * Created by 薛立民
 * TEL 13262933389
 */
public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
