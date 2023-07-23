package com.grew.sw.cashlawn.util;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
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

import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.model.AlbumInfoModel;

import java.io.InputStream;
import java.util.ArrayList;

public class ImageDataUtil implements LoaderManager.LoaderCallbacks<Cursor> {
    private AppCompatActivity appCompatActivity;
    private boolean isLoadFinish = false;
    private boolean isLoading = false;
    private OnLoadFinishListener loadedListener;
    private ArrayList<AlbumInfoModel> albumInfoModels = new ArrayList<>();

    public synchronized void start(AppCompatActivity activity, OnLoadFinishListener loadedListener) {
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        this.loadedListener = loadedListener;
        appCompatActivity = activity;
        LogUtils.d("ImageDataUtil:isLoading"+isLoading+"  isLoadFinish:"+isLoadFinish);
        synchronized(albumInfoModels){
            if (!isLoading && isLoadFinish && loadedListener != null) {
                loadedListener.loadFinish(albumInfoModels);
            }
            if (!isLoading) {
                loaderManager.initLoader(0, null, this);//加载所有的图片
            }
        }
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
        try {
            if (data != null && !data.isAfterLast()) {
                if (isLoadFinish || isLoading) {
                    return;
                }
                isLoading = true;
                new Thread(() -> {
                    albumInfoModels.clear();
                    while (!data.isClosed() && data.moveToNext()) {
                        //查询数据
                        String imageName = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                        long imageAddTime = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                        String imagePath = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        int imageWidth = data.getInt(data.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH));
                        int imageHeight = data.getInt(data.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT));
                        String mimeType = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));

                        AlbumInfoModel albumBean = new AlbumInfoModel();
                        albumBean.setName(imageName);
                        long take_time = (imageAddTime / 1000000000) > 100 ? imageAddTime : imageAddTime * 1000;
                        albumBean.setTake_time(take_time);
                        albumBean.setWidth(imageWidth+"");
                        albumBean.setHeight(imageHeight+"");
                        albumBean.setUpdateTime(DateUtil.getTimeFromLongYMDHMS(take_time));
                        ExifInterface exifInterface;
                        if (mimeType!=null && (mimeType.toLowerCase().contains("jpeg")  || mimeType.toLowerCase().contains("jpg"))) {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    long id = data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                                    //通过id构造Uri
                                    Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//                            //构造输入流
                                    InputStream inputStream = App.get().getContentResolver().openInputStream(uri);
                                    exifInterface = new ExifInterface(inputStream);
                                    inputStream.close();
                                } else {
                                    exifInterface = new ExifInterface(imagePath);
                                }
                                float[] latLong = new float[2];
                                exifInterface.getLatLong(latLong);
                                String attribute = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
                                String model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);

                                albumBean.setAuthor(TextUtils.isEmpty(attribute) ? Build.BRAND : attribute);
                                albumBean.setLatitude(latLong[0] + "");
                                albumBean.setLongitude(latLong[1] + "");
                                albumBean.setModel(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        albumInfoModels.add(albumBean);
                    }
                    //回调接口，通知图片数据准备完成
                    if (loadedListener != null) {
                        loadedListener.loadFinish(albumInfoModels);
                    }
                    isLoading = false;
                    isLoadFinish = true;
                }).start();

            } else if (data == null || data.isAfterLast()) {
                //回调接口，通知图片数据准备完成
                if (loadedListener != null) {
                    loadedListener.loadFinish(albumInfoModels);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public interface OnLoadFinishListener {
        void loadFinish(ArrayList<AlbumInfoModel> albumInfoModels);
    }
}
