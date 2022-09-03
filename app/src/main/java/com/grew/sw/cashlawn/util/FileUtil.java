package com.grew.sw.cashlawn.util;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.grew.sw.cashlawn.App;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {

    public static ArrayList<File> getDownloadFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),MediaStore.Files.FileColumns.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    public static ArrayList<File> getVideoInternalFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Video.Media.INTERNAL_CONTENT_URI, MediaStore.Video.Media.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    public static ArrayList<File> getVideoExternalFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    public static ArrayList<File> getImagesInternalFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Images.Media.INTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    public static ArrayList<File> getImagesExternalFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    public static ArrayList<File> getAudioExternalFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    public static ArrayList<File> getAudioInternalFiles() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            fileArrayList.addAll(getFiles(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, MediaStore.Audio.Media.DATA));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

    /**
     * 获取指定目录内所有文件路径
     *
     * @param files
     * @param fileArrayList
     */
    private static void getAllFiles(File[] files, ArrayList<File> fileArrayList) {
        if (files == null) return;
        for (File file : files) {
            if (file.isFile()) {
                fileArrayList.add(file);
            } else if (file.isDirectory() && !file.getName().contains("thumbnails")) {
                getAllFiles(file.getAbsolutePath(), fileArrayList);
            }
        }
    }

    private static void getAllFiles(String dirPath, ArrayList<File> myFileList) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        if (files == null) return;
        for (File _file : files) {
            if (_file.isFile()) {
                myFileList.add(_file);
            } else if (_file.isDirectory() && !_file.getName().contains("thumbnails")) {
                getAllFiles(_file.getAbsolutePath(), myFileList);
            }
        }
    }

    private static ArrayList<File> getFiles(Uri volume, String columnName) {
        ArrayList<File> fileArrayList = new ArrayList<>();
        try {
            Cursor cursor = App.get().getContentResolver().query(volume, null, null, null, null);
            if (cursor != null) {
                int columnIndexOrThrow = cursor.getColumnIndexOrThrow(columnName);
                while (cursor.moveToNext()) {
                    String path = cursor.getString(columnIndexOrThrow);
                    int i = path.lastIndexOf(".");
                    if (i == -1) {
                        continue;
                    }
                    int i1 = path.lastIndexOf(File.separator);
                    if (i1 == -1) {
                        continue;
                    }
                    File file = new File(path);
                    fileArrayList.add(file);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileArrayList;
    }

}
