package com.grew.sw.cashlawn.model;



import java.io.Serializable;

public class DeviceInfoModel implements Serializable {
    private long create_time;//抓取时间	1618985088
    private int VideoExternal;//视频外部文件个数
    private String availableMemory;//手机内部存储可用大小 b
    private long elapsedRealtime;//从开机到现在的毫秒数(包括睡眠时间)
    private long memorySize;//手机内部存储总大小
    private String isUsingProxyPort;//是否使用代理 true false
    private String isUsingVPN;//是否使用VPN	false
    private long ram_total_size;//总内存大小	1475395584
    private String isUSBDebug;//是否开启 USB 调试	false
    private String availableDiskSize;//手机内部存储可用大小	383799296
    private String totalDiskSize;//sd_card 使用了的内存大小
    private String phone_brand;//手机品牌	samsung
    private String cur_wifi_mac;//最近WiFi的mac地址	e4:26:86:72:fc:b0
    private long imei2;//设备号2	358208088672438
    private long imei1;//设备号1	358208088672438
    private String phone_type;//指示设备电话类型的常量 NONE：0，GS：1，CDMA：2，SIP=3"language
    private String language;//系统语言	en
    private String locale_display_language;//English
    private String network_operator_name;//网络运营商名称	TELCEL
    private String locale_iso_3_country;//此地区的国家地区缩写	USA
    private String locale_iso_3_language;//语言环境的字母缩写	eng
    private String build_fingerprint;//指纹信息
    private String cur_wifi_ssid;//最近WiFi的WLAN网络名称
    private int DownloadFiles;//下载文件个数
    private int battery_status;//是否正在充电，UNKNOWN： 1，CHARGING：2，DISCHARGING：3
    private int is_usb_charge;//是否 usb 充电，0：否，1：是
    private int is_ac_charge;//是否交流充电，0：否，1：是
    private String batteryPercentage;//电池百分比	0.17
    private String battery_temper;//电池状态	32
    private String battery_health;//电池寿命	2
    private String time_zoneId;//时区id	Africa/Lagos
    private String kernel_version;//string	内核版本	3.18.19-11839938
    private String currentSystemTime;//1618985088
    private String AudioInternal;//音频内部文件个数
    private String nettype;//网络类型 unknown：0，GPRS：1，EDGE： 2，UMTS：3，CDMA: Either IS95A or IS95B：4，EVDO revision 0：5
    private String serial;//序列号	4200bb14ea4734db
    private String android_id;//安卓id
    private String kernel_architecture;//string	内核架构	armeabi-v7a
    private String build_id;//版本id	MMB29T
    private String ImagesInternal;//图片内部文件个数
    private String build_number;//string	版本号	MMB29T.G532MUBU1AQG4
    private String mac;// mac地址	F0:EE:10:0F:3C:98
    private String isRooted;//是否root	false
    private String board;//主板	MT6737T
    private String first_ip;//公网ip	189.243.251.162
    private int VideoInternal;//视频内部文件个数	0
    private int AudioExternal;//外部文件个数	2
    private long build_time;//int	版本日期
    private int wifiCount;//WiFi个数	3
    private String time_zone;//时区	CST
    private long release_date;//int	更新日期	1499779791000
    private String device_name;//设备名称	grandpplte
    private String ImagesExternal;//图片外部文件个数	5
    private String device_model;//型号	SM-G532M
    private String imei;//设备号	000000000000000
    private String sys_version;//系统版本	23
    private String screenResolution;//物理尺寸	540 * 960
    private String manufacturerName;//品牌	samsung
    private String security_patch_level;//安全补丁时间	2017-07-01

    @Override
    public String toString() {
        return "DeviceInfoModel{" +
                "create_time=" + create_time +
                ", VideoExternal=" + VideoExternal +
                ", availableMemory='" + availableMemory + '\'' +
                ", elapsedRealtime=" + elapsedRealtime +
                ", memorySize=" + memorySize +
                ", isUsingProxyPort='" + isUsingProxyPort + '\'' +
                ", isUsingVPN='" + isUsingVPN + '\'' +
                ", ram_total_size=" + ram_total_size +
                ", isUSBDebug='" + isUSBDebug + '\'' +
                ", availableDiskSize='" + availableDiskSize + '\'' +
                ", totalDiskSize='" + totalDiskSize + '\'' +
                ", phone_brand='" + phone_brand + '\'' +
                ", cur_wifi_mac='" + cur_wifi_mac + '\'' +
                ", imei2=" + imei2 +
                ", imei1=" + imei1 +
                ", phone_type='" + phone_type + '\'' +
                ", language='" + language + '\'' +
                ", locale_display_language='" + locale_display_language + '\'' +
                ", network_operator_name='" + network_operator_name + '\'' +
                ", locale_iso_3_country='" + locale_iso_3_country + '\'' +
                ", locale_iso_3_language='" + locale_iso_3_language + '\'' +
                ", build_fingerprint='" + build_fingerprint + '\'' +
                ", cur_wifi_ssid='" + cur_wifi_ssid + '\'' +
                ", DownloadFiles=" + DownloadFiles +
                ", battery_status=" + battery_status +
                ", is_usb_charge=" + is_usb_charge +
                ", is_ac_charge=" + is_ac_charge +
                ", batteryPercentage='" + batteryPercentage + '\'' +
                ", battery_temper='" + battery_temper + '\'' +
                ", battery_health='" + battery_health + '\'' +
                ", time_zoneId='" + time_zoneId + '\'' +
                ", kernel_version='" + kernel_version + '\'' +
                ", currentSystemTime='" + currentSystemTime + '\'' +
                ", AudioInternal='" + AudioInternal + '\'' +
                ", nettype='" + nettype + '\'' +
                ", serial='" + serial + '\'' +
                ", android_id='" + android_id + '\'' +
                ", kernel_architecture='" + kernel_architecture + '\'' +
                ", build_id='" + build_id + '\'' +
                ", ImagesInternal='" + ImagesInternal + '\'' +
                ", build_number='" + build_number + '\'' +
                ", mac='" + mac + '\'' +
                ", isRooted='" + isRooted + '\'' +
                ", board='" + board + '\'' +
                ", first_ip='" + first_ip + '\'' +
                ", VideoInternal=" + VideoInternal +
                ", AudioExternal=" + AudioExternal +
                ", build_time=" + build_time +
                ", wifiCount=" + wifiCount +
                ", time_zone='" + time_zone + '\'' +
                ", release_date=" + release_date +
                ", device_name='" + device_name + '\'' +
                ", ImagesExternal='" + ImagesExternal + '\'' +
                ", device_model='" + device_model + '\'' +
                ", imei='" + imei + '\'' +
                ", sys_version='" + sys_version + '\'' +
                ", screenResolution='" + screenResolution + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", security_patch_level='" + security_patch_level + '\'' +
                '}';
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getVideoExternal() {
        return VideoExternal;
    }

    public void setVideoExternal(int videoExternal) {
        VideoExternal = videoExternal;
    }

    public String getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(String availableMemory) {
        this.availableMemory = availableMemory;
    }

    public long getElapsedRealtime() {
        return elapsedRealtime;
    }

    public void setElapsedRealtime(long elapsedRealtime) {
        this.elapsedRealtime = elapsedRealtime;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public String getIsUsingProxyPort() {
        return isUsingProxyPort;
    }

    public void setIsUsingProxyPort(String isUsingProxyPort) {
        this.isUsingProxyPort = isUsingProxyPort;
    }

    public String getIsUsingVPN() {
        return isUsingVPN;
    }

    public void setIsUsingVPN(String isUsingVPN) {
        this.isUsingVPN = isUsingVPN;
    }

    public long getRam_total_size() {
        return ram_total_size;
    }

    public void setRam_total_size(long ram_total_size) {
        this.ram_total_size = ram_total_size;
    }

    public String getIsUSBDebug() {
        return isUSBDebug;
    }

    public void setIsUSBDebug(String isUSBDebug) {
        this.isUSBDebug = isUSBDebug;
    }

    public String getAvailableDiskSize() {
        return availableDiskSize;
    }

    public void setAvailableDiskSize(String availableDiskSize) {
        this.availableDiskSize = availableDiskSize;
    }

    public String getTotalDiskSize() {
        return totalDiskSize;
    }

    public void setTotalDiskSize(String totalDiskSize) {
        this.totalDiskSize = totalDiskSize;
    }

    public String getPhone_brand() {
        return phone_brand;
    }

    public void setPhone_brand(String phone_brand) {
        this.phone_brand = phone_brand;
    }

    public String getCur_wifi_mac() {
        return cur_wifi_mac;
    }

    public void setCur_wifi_mac(String cur_wifi_mac) {
        this.cur_wifi_mac = cur_wifi_mac;
    }

    public long getImei2() {
        return imei2;
    }

    public void setImei2(long imei2) {
        this.imei2 = imei2;
    }

    public long getImei1() {
        return imei1;
    }

    public void setImei1(long imei1) {
        this.imei1 = imei1;
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocale_display_language() {
        return locale_display_language;
    }

    public void setLocale_display_language(String locale_display_language) {
        this.locale_display_language = locale_display_language;
    }

    public String getNetwork_operator_name() {
        return network_operator_name;
    }

    public void setNetwork_operator_name(String network_operator_name) {
        this.network_operator_name = network_operator_name;
    }

    public String getLocale_iso_3_country() {
        return locale_iso_3_country;
    }

    public void setLocale_iso_3_country(String locale_iso_3_country) {
        this.locale_iso_3_country = locale_iso_3_country;
    }

    public String getLocale_iso_3_language() {
        return locale_iso_3_language;
    }

    public void setLocale_iso_3_language(String locale_iso_3_language) {
        this.locale_iso_3_language = locale_iso_3_language;
    }

    public String getBuild_fingerprint() {
        return build_fingerprint;
    }

    public void setBuild_fingerprint(String build_fingerprint) {
        this.build_fingerprint = build_fingerprint;
    }

    public String getCur_wifi_ssid() {
        return cur_wifi_ssid;
    }

    public void setCur_wifi_ssid(String cur_wifi_ssid) {
        this.cur_wifi_ssid = cur_wifi_ssid;
    }

    public int getDownloadFiles() {
        return DownloadFiles;
    }

    public void setDownloadFiles(int downloadFiles) {
        DownloadFiles = downloadFiles;
    }

    public int getBattery_status() {
        return battery_status;
    }

    public void setBattery_status(int battery_status) {
        this.battery_status = battery_status;
    }

    public int getIs_usb_charge() {
        return is_usb_charge;
    }

    public void setIs_usb_charge(int is_usb_charge) {
        this.is_usb_charge = is_usb_charge;
    }

    public int getIs_ac_charge() {
        return is_ac_charge;
    }

    public void setIs_ac_charge(int is_ac_charge) {
        this.is_ac_charge = is_ac_charge;
    }

    public String getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(String batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getBattery_temper() {
        return battery_temper;
    }

    public void setBattery_temper(String battery_temper) {
        this.battery_temper = battery_temper;
    }

    public String getBattery_health() {
        return battery_health;
    }

    public void setBattery_health(String battery_health) {
        this.battery_health = battery_health;
    }

    public String getTime_zoneId() {
        return time_zoneId;
    }

    public void setTime_zoneId(String time_zoneId) {
        this.time_zoneId = time_zoneId;
    }

    public String getKernel_version() {
        return kernel_version;
    }

    public void setKernel_version(String kernel_version) {
        this.kernel_version = kernel_version;
    }

    public String getCurrentSystemTime() {
        return currentSystemTime;
    }

    public void setCurrentSystemTime(String currentSystemTime) {
        this.currentSystemTime = currentSystemTime;
    }

    public String getAudioInternal() {
        return AudioInternal;
    }

    public void setAudioInternal(String audioInternal) {
        AudioInternal = audioInternal;
    }

    public String getNettype() {
        return nettype;
    }

    public void setNettype(String nettype) {
        this.nettype = nettype;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getKernel_architecture() {
        return kernel_architecture;
    }

    public void setKernel_architecture(String kernel_architecture) {
        this.kernel_architecture = kernel_architecture;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getImagesInternal() {
        return ImagesInternal;
    }

    public void setImagesInternal(String imagesInternal) {
        ImagesInternal = imagesInternal;
    }

    public String getBuild_number() {
        return build_number;
    }

    public void setBuild_number(String build_number) {
        this.build_number = build_number;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIsRooted() {
        return isRooted;
    }

    public void setIsRooted(String isRooted) {
        this.isRooted = isRooted;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getFirst_ip() {
        return first_ip;
    }

    public void setFirst_ip(String first_ip) {
        this.first_ip = first_ip;
    }

    public int getVideoInternal() {
        return VideoInternal;
    }

    public void setVideoInternal(int videoInternal) {
        VideoInternal = videoInternal;
    }

    public int getAudioExternal() {
        return AudioExternal;
    }

    public void setAudioExternal(int audioExternal) {
        AudioExternal = audioExternal;
    }

    public long getBuild_time() {
        return build_time;
    }

    public void setBuild_time(long build_time) {
        this.build_time = build_time;
    }

    public int getWifiCount() {
        return wifiCount;
    }

    public void setWifiCount(int wifiCount) {
        this.wifiCount = wifiCount;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public long getRelease_date() {
        return release_date;
    }

    public void setRelease_date(long release_date) {
        this.release_date = release_date;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getImagesExternal() {
        return ImagesExternal;
    }

    public void setImagesExternal(String imagesExternal) {
        ImagesExternal = imagesExternal;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSys_version() {
        return sys_version;
    }

    public void setSys_version(String sys_version) {
        this.sys_version = sys_version;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getSecurity_patch_level() {
        return security_patch_level;
    }

    public void setSecurity_patch_level(String security_patch_level) {
        this.security_patch_level = security_patch_level;
    }
}
