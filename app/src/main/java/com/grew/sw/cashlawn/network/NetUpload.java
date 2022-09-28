package com.grew.sw.cashlawn.network;

import com.grew.sw.cashlawn.util.ConsUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class NetUpload {
    /**
     * Okhttp上传图片(流)
     */
    public static void okHttpUploadImage(File file,Callback callback) {
        // 创建 OkHttpClient
        OkHttpClient client = NetClient.getInstance().initUploadOkHttpClient();
        // 要上传的文件
        // 把文件封装进请求体
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody 上传文件专用的请求体
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型(必填)
                .addFormDataPart("file", file.getName(), fileBody)
                .addFormDataPart("type", "jpg")
                .build();
        Request request = new Request.Builder()
                .url(ConsUtil.BASE_URL+"/system/uploadimg")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(call,response);
            }

        });
    }

    public static String getResponseBody(Response response) {
        Charset UTF8 = Charset.forName("UTF-8");
        ResponseBody responseBody = (ResponseBody) response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        return buffer.clone().readString(charset);
    }
}
