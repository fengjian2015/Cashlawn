package com.grew.sw.cashlawn.util;

import android.app.Activity;
import android.database.Cursor;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.grew.sw.cashlawn.model.AlbumInfoModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageDataUtil implements LoaderManager.LoaderCallbacks<Cursor> {
    private AppCompatActivity appCompatActivity;
    private boolean isLoadFinish = false;
    private OnLoadFinishListener loadedListener;
    private ArrayList<AlbumInfoModel> albumInfoModels = new ArrayList<>();

    public void start(AppCompatActivity activity, OnLoadFinishListener loadedListener) {
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        this.loadedListener = loadedListener;
        appCompatActivity = activity;
        if (isLoadFinish && loadedListener != null) {
            loadedListener.loadFinish(albumInfoModels);
        }
        loaderManager.initLoader(0, null, this);//加载所有的图片
    }

    public void unLoadedListener(){
        loadedListener = null;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        long time = DateUtil.getServerTimestamp() / 1000 - 31536000;
        return new CursorLoader(appCompatActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null
                , MediaStore.Images.Media.DATE_ADDED + " > " + time, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && !data.isAfterLast()) {
            if (isLoadFinish) {
                return;
            }
            isLoadFinish = true;
            new Thread(() -> {
                albumInfoModels.clear();
                while (data.moveToNext()) {
                    //查询数据
                    String imageName = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    String imagePath = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    int imageWidth = data.getInt(data.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH));
                    int imageHeight = data.getInt(data.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT));
                    long imageAddTime = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));

                    AlbumInfoModel albumBean = new AlbumInfoModel();
                    albumBean.setName(imageName);
                    albumBean.setHeight(imageHeight + "");
                    albumBean.setWidth(imageWidth + "");
                    albumBean.setUpdateTime(DateUtil.getTimeFromLongYMDHMS(imageAddTime / 1000000000 > 100 ? imageAddTime : imageAddTime * 1000));
                    albumBean.setAddTime(albumBean.getUpdateTime());
                    ExifInterface exifInterface = null;
                    try {
                        exifInterface = new ExifInterface(imagePath);
                    } catch (IOException e) {
                    }
                    float[] latLong = new float[2];
                    if (exifInterface != null) {
                        exifInterface.getLatLong(latLong);
                        albumBean.setAuthor(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
                        albumBean.setModel( exifInterface.getAttribute(ExifInterface.TAG_MODEL));
                    }
                    albumBean.setLatitude(latLong[0]+"");
                    albumBean.setLongitude(latLong[1]+"");
                    if (TextUtils.isEmpty(albumBean.getAuthor())){
                        albumBean.setAuthor(Build.BRAND);
                    }
                    albumInfoModels.add(albumBean);
                }
                //回调接口，通知图片数据准备完成
                if (loadedListener != null) {
                    loadedListener.loadFinish(albumInfoModels);
                }
            }).start();

        } else if (data == null || data.isAfterLast()) {
            //回调接口，通知图片数据准备完成
            if (loadedListener != null) {
                loadedListener.loadFinish(albumInfoModels);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public interface OnLoadFinishListener {
        void loadFinish(ArrayList<AlbumInfoModel> albumInfoModels);
    }
}
