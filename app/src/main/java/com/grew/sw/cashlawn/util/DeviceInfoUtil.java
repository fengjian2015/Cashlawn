package com.grew.sw.cashlawn.util;

import static android.content.ContentValues.TAG;
import static android.content.Context.SENSOR_SERVICE;

import android.annotation.SuppressLint;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.grew.sw.cashlawn.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class DeviceInfoUtil {

    public static String getDeviceName(){
        return Settings.Secure.getString(App.get().getContentResolver(), "bluetooth_name");
    }

    public static String getKernelVersion() {
        try {
            return System.getProperty("os.version");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取当前网络连接的类型
     *
     * @param context context
     * @return int
     */
    public static int getNetworkState( ){
        ConnectivityManager connManager = (ConnectivityManager) App.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null){
            return 0;
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        TelephonyManager telephonyManager =
                (TelephonyManager) App.get().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") int networkType = telephonyManager.getNetworkType();
        return networkType;
    }

    public static String getTimeZoneId(){
        TimeZone aDefault = TimeZone.getDefault();
        return aDefault.getID();
    }

    public static String getTimeZone(){
        TimeZone aDefault = TimeZone.getDefault();
        return aDefault.getDisplayName(false,TimeZone.SHORT);
    }


    public static String getOperatorName(){
        TelephonyManager systemService = (TelephonyManager) App.get().getSystemService(Context.TELEPHONY_SERVICE);
        return systemService.getSimOperatorName();
    }


    public static String getPhoneType(){
        TelephonyManager systemService = (TelephonyManager) App.get().getSystemService(Context.TELEPHONY_SERVICE);
        if (systemService==null) return "0";
        switch (systemService.getPhoneType()){
            case TelephonyManager.PHONE_TYPE_GSM:
                return "1";
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "3";
            case TelephonyManager.PHONE_TYPE_SIP:
                return "4";
            default:
                return "0";
        }
    }



    public static String isUsbDebug() {
        return Settings.Secure.getInt(App.get().getContentResolver(), Settings.Secure.ADB_ENABLED, 0
        ) > 0 ? "true" : "false";
    }


    public static String isVpn() {
        ConnectivityManager systemService = (ConnectivityManager) App.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = systemService.getNetworkInfo(ConnectivityManager.TYPE_VPN);
        if (networkInfo == null) return "false";
        return networkInfo.isConnected() ? "true" : "false";
    }


    public static String isProxy() {
        try {
            String proxyAddress;
            int proxyPort;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                proxyAddress = System.getProperty("http.proxyHost");
                String property = System.getProperty("http.proxyPort");
                if (TextUtils.isEmpty(property)) property = "-1";
                proxyPort = ComUtil.stringToInt(property);
            } else {
                proxyAddress = Proxy.getHost(App.get());
                proxyPort = Proxy.getPort(App.get());
            }
            return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1 ? "true" : "false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    public static String getWifiInfo() {
        WifiManager wifiManager = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getBSSID() == null ? "" : info.getBSSID();
    }


    @SuppressLint("MissingPermission")
    public static String getWifiMac() {
        WifiManager wifiManager = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getMacAddress() == null ? "" : info.getMacAddress();
    }

    @SuppressLint("MissingPermission")
    public static String getWifiName() {
        WifiManager wifiManager = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getSSID() == null ? "" : info.getSSID();
    }

    /**
     * wifi列表
     *
     * @return
     */
    public static List<String> getWifiList() {
        WifiManager wifiManager = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        List<String> wifiList = new ArrayList<>();
        if (scanWifiList != null && scanWifiList.size() > 0) {
            for (int i = 0; i < scanWifiList.size(); i++) {
                ScanResult scanResult = scanWifiList.get(i);
                if (!TextUtils.isEmpty(scanResult.SSID)) {
                    wifiList.add(scanResult.SSID);
                }
            }
            return wifiList;
        } else {
            Log.e(TAG, "非常遗憾搜索到wifi");
            return wifiList;
        }
    }

    /**
     * 获取IMEI
     */
    public static String getIMEI() {
        String IMEI;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            IMEI = DeviceUtils.getAndroidID();
        } else {
            IMEI = ((TelephonyManager) App.get().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (TextUtils.isEmpty(IMEI)) {
                IMEI = DeviceUtils.getAndroidID();
            }
        }
        return IMEI;
    }

    public static long getIMEI1() {
        String IMEI;
        try {
            IMEI =  ((TelephonyManager) App.get().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            return ComUtil.stringToLong(IMEI);
        } catch (Exception ignored) {

        }
        return 0L;
    }

    /**
     * 获取传感器数量
     *
     * @return
     */
    public static String getSensorCount() {
        SensorManager sm = (SensorManager) App.get().getSystemService(SENSOR_SERVICE);  //获取系统的传感器服务并创建实例
        List<Sensor> list = sm.getSensorList(Sensor.TYPE_ALL);  //获取传感器的集合
        if (list != null) {
            return list.size() + "";
        }
        return 0 + "";
    }

    /**
     * 获取剩余空间
     *
     * @return
     */
    public static String getAvailableSpace() {
        return queryWithStorageManager(App.get())[1];
    }

    /**
     * 获取总空间
     *
     * @return
     */
    public static String getTotalRam() {
        return queryWithStorageManager(App.get())[0];
    }


    /**
     * 获取内存大小和剩余空间
     *
     * @param context
     * @return
     */
    private static String[] queryWithStorageManager(Context context) {
        String[] size = new String[]{"0", "0"};
        //5.0 查外置存储
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        int version = Build.VERSION.SDK_INT;
        if (version < Build.VERSION_CODES.M) {//小于6.0
            try {
                Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList");
                StorageVolume[] volumeList = (StorageVolume[]) getVolumeList.invoke(storageManager);
                long totalSize = 0, availableSize = 0;
                if (volumeList != null) {
                    Method getPathFile = null;
                    for (StorageVolume volume : volumeList) {
                        if (getPathFile == null) {
                            getPathFile = volume.getClass().getDeclaredMethod("getPathFile");
                        }
                        File file = (File) getPathFile.invoke(volume);
                        totalSize += file.getTotalSpace();
                        availableSize += file.getUsableSpace();
                    }
                }
                size[0] = totalSize + "";
                size[1] = availableSize + "";
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");//6.0
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
                long total = 0L, used = 0L, systemSize = 0L;
                for (Object obj : getVolumeInfo) {

                    Field getType = obj.getClass().getField("type");
                    int type = getType.getInt(obj);
                    if (type == 1) {//TYPE_PRIVATE
                        long totalSize = 0L;
                        //获取内置内存总大小
                        if (version >= Build.VERSION_CODES.O) {//8.0
                            Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid");
                            String fsUuid = (String) getFsUuid.invoke(obj);
                            totalSize = getTotalSize(context, fsUuid);//8.0 以后使用
                        } else if (version >= Build.VERSION_CODES.N_MR1) {//7.1.1
                            Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize");//5.0 6.0 7.0没有
                            totalSize = (long) getPrimaryStorageSize.invoke(storageManager);
                        }
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);

                            if (totalSize == 0) {
                                totalSize = f.getTotalSpace();
                            }
                            systemSize = totalSize - f.getTotalSpace();
                            used += totalSize - f.getFreeSpace();
                            total += totalSize;
                        }
                    } else if (type == 0) {//TYPE_PUBLIC
                        //外置存储
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            used += f.getTotalSpace() - f.getFreeSpace();
                            total += f.getTotalSpace();
                        }
                    } else if (type == 2) {//TYPE_EMULATED

                    }
                }
                size[0] = (total + systemSize) + "";
                size[1] = (total - used) + "";
            } catch (SecurityException e) {
                Log.e(TAG, "缺少权限：permission.PACKAGE_USAGE_STATS");
            } catch (Exception e) {
                e.printStackTrace();
                size = queryWithStatFs(size);
            }
        }
        return size;
    }

    private static String[] queryWithStatFs(String[] strings) {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        //存储块
        long blockCount = statFs.getBlockCount();
        //块大小
        long blockSize = statFs.getBlockSize();
        //可用块数量
        long availableCount = statFs.getAvailableBlocks();
        //剩余块数量，注：这个包含保留块（including reserved blocks）即应用无法使用的空间
        strings[0] = (blockSize * blockCount) + "";
        strings[1] = (blockSize * availableCount) + "";
        return strings;
    }


    /**
     * API 26 android O
     * 获取总共容量大小，包括系统大小
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private static long getTotalSize(Context context, String fsUuid) {
        try {
            UUID id;
            if (fsUuid == null) {
                id = StorageManager.UUID_DEFAULT;
            } else {
                id = UUID.fromString(fsUuid);
            }
            StorageStatsManager stats = context.getSystemService(StorageStatsManager.class);
            return stats.getTotalBytes(id);
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * BASEBAND-VER
     * 基带版本
     * return String
     */
    public static String getBasebandVer() {
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            return (String) result;
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 屏幕分辨率
     *
     * @return
     */
    public static String getScreenResolution() {
        WindowManager windowManager = IActivityManager.getActivity().getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return height + " * " + width;
    }

    public static String getIPAddress() {
        WifiManager wifi_service = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
        WifiInfo wifiinfo = wifi_service.getConnectionInfo();
        String localIp = getLocalIPAddress();
        if (wifiinfo.getIpAddress() == 0) {
            //DhcpInfo中的ipAddress是一个int型的变量，通过Formatter将其转化为字符串IP地址
            return localIp;
        } else {
            return Formatter.formatIpAddress(dhcpInfo.ipAddress);
        }
    }

    private static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}