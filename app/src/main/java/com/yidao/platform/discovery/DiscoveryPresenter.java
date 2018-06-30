package com.yidao.platform.discovery;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.yidao.platform.R;

public class DiscoveryPresenter {

    private DiscoveryViewInterface view;

    DiscoveryPresenter(DiscoveryViewInterface fragment) {
        this.view = fragment;
    }

    public void openCamera() {
        view.openCamera();
    }

    public void setImage(Bitmap bitmap, ImageView imageView) {
        view.showImage(bitmap,imageView);
    }

    public void openAlbum() {
        view.openAlbum();
    }

    public void handleImage(Context context, Intent data, ImageView imageView) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果document类型Uri 通过docId处理
            String docId = DocumentsContract.getDocumentId(uri);
            if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (uri.getAuthority().equals("com.android.providers.download.documents")) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //content类型Uri 普通方式处理
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //file类型Uri 直接获取图片路径
            imagePath = uri.getPath();
        }
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            view.showImage(bitmap,imageView);
        }else {
            view.showToast(context.getString(R.string.failed_to_get_image));
        }
    }

    private String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
