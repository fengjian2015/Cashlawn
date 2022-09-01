package com.grew.sw.cashlawn.network;

import com.grew.sw.cashlawn.util.ComUtil;
import com.grew.sw.cashlawn.util.UserInfoUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ValueInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        if (requestBody!=null) {
            request = request.newBuilder()
                    .header("appVersion", ComUtil.getVersionName())
                    .header("devName", "android")
                    .header("session", UserInfoUtil.getSessionId())
                    .header("userId", UserInfoUtil.getUserId())
                    .build();
        }
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        return response;
    }
}
