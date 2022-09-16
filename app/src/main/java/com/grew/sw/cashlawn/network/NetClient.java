package com.grew.sw.cashlawn.network;

import static com.grew.sw.cashlawn.util.ConsUtil.BASE_URL;

import com.grew.sw.cashlawn.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetClient {

    private static NetClient netClient;
    private static NewService newService,authService;


    public static NewService getNewService() {
        if (newService == null){
            newService = getInstance().initOkHttpClient(false);
        }
        return newService;
    }

    public static NewService getAuthService() {
        if (authService == null){
            authService = getInstance().initOkHttpClient(true);
        }
        return authService;
    }

    public static NetClient getInstance(){
        if (netClient == null){
            netClient = new NetClient();
        }
        return netClient;
    }

    public NewService initOkHttpClient(boolean isAuth){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(false);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60,TimeUnit.SECONDS);
        builder.writeTimeout(60,TimeUnit.SECONDS);
        builder.sslSocketFactory(getSSLSocketFactory());
        builder.addInterceptor(new ValueInterceptor());
        if (isAuth) {
            builder.addInterceptor(new GzipRequestInterceptor());
        }
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor("NetClientLog");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);
        }

        Retrofit build = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        return build.create(NewService.class);
    }

    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        // return null; 或者
                        return new X509Certificate[]{}; // 空实现
                    }
                }
        };
        return trustAllCerts;
    }

}
