package com.yidao.platform.discovery;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface DiscoveryViewInterface {
    void showImage(Bitmap bitmap, ImageView view);

    void openCamera();

    void openAlbum();

    void showToast(String s);
}
