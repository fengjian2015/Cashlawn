package com.grew.sw.cashlawn.network;

import com.grew.sw.cashlawn.model.ImageResponse;
import com.grew.sw.cashlawn.model.UrlResponse;
import com.grew.sw.cashlawn.model.UserInfoResponse;
import com.grew.sw.cashlawn.model.VersionResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NewService {

    @POST("/account/sendVerifyCode")
    public Observable<UserInfoResponse> sendCode(@Body Map<String, String> map);

    @POST("/account/loginByPhoneVerifyCode")
    public Observable<UserInfoResponse> login(@Body Map<String, String> map);

    @POST("/account/logout")
    public Observable<UserInfoResponse> logout();

    @POST("/auth/uploadCocoLoanWardAuth")
    public Observable<UserInfoResponse> uploadCocoLoanWardAuth(@Body Map<String, String> map);

    @POST("/system/getNewVersion")
    public Observable<VersionResponse> requestVersion();

    @GET("/system/getProtocolUrl")
    public Observable<UrlResponse> getUrl();

    @Multipart
    @POST("/system/uploadimg")
    public Call<ImageResponse> uploadImg(@Part MultipartBody.Part part , @Part MultipartBody.Part type );
}
