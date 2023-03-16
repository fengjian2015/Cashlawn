package com.grew.sw.cashlawn.model;



import java.io.Serializable;
import java.util.List;

public class DeviceInfoModel implements Serializable {
    private long create_time;//抓取时间	1618985088
    private int VideoExternal;//视频外部文件个数
    private String phone_brand;//手机品牌	samsung
    private String cur_wifi_mac;//最近WiFi的mac地址	e4:26:86:72:fc:b0
    private long imei2;//设备号2	358208088672438
    private long imei1;//设备号1	358208088672438
    private String build_fingerprint;//指纹信息
    private String cur_wifi_ssid;//最近WiFi的WLAN网络名称
    private int DownloadFiles;//下载文件个数
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
    private String board;//主板	MT6737T
    private int VideoInternal;//视频内部文件个数	0
    private int AudioExternal;//外部文件个数	2
    private long build_time;//int	版本日期
    private String time_zone;//时区	CST
    private long release_date;//int	更新日期	1499779791000
    private String device_name;//设备名称	grandpplte
    private String ImagesExternal;//图片外部文件个数	5
    private String security_patch_level;//安全补丁时间	2017-07-01
    private List<String> wifilist;//WiFi名称
    private String sensorcount;//传感器数量

    private String wifi_state;//wifi强度,当前链接的wifi强度
    private String gaid;//wifi强度,当前链接的wifi强度 得到的值是一个0到-100的区间值，是一个int型数据，其中0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线。
    private int back_num;//退到后台次数
    private String open_time;//打开app时间yyyy-MM-dd HH:mm
    private int open_power;//打开app时的电量，比如 70% ，
    private int complete_apply_power;//提交申请时电量  ，比如70% ，

    private StorageData storage;
    private GeneralData general_data;
    private HardwareData hardware;
    private PublicIpData public_ip;
    private BatteryStatusData battery_status;
    private DeviceInfoData device_info;

    @Override
    public String toString() {
        return "DeviceInfoModel{" +
                "create_time=" + create_time +
                ", VideoExternal=" + VideoExternal +
                ", phone_brand='" + phone_brand + '\'' +
                ", cur_wifi_mac='" + cur_wifi_mac + '\'' +
                ", imei2=" + imei2 +
                ", imei1=" + imei1 +
                ", build_fingerprint='" + build_fingerprint + '\'' +
                ", cur_wifi_ssid='" + cur_wifi_ssid + '\'' +
                ", DownloadFiles=" + DownloadFiles +
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
                ", board='" + board + '\'' +
                ", VideoInternal=" + VideoInternal +
                ", AudioExternal=" + AudioExternal +
                ", build_time=" + build_time +
                ", time_zone='" + time_zone + '\'' +
                ", release_date=" + release_date +
                ", device_name='" + device_name + '\'' +
                ", ImagesExternal='" + ImagesExternal + '\'' +
                ", security_patch_level='" + security_patch_level + '\'' +
                ", wifilist=" + wifilist +
                ", sensorcount='" + sensorcount + '\'' +
                ", wifi_state='" + wifi_state + '\'' +
                ", gaid='" + gaid + '\'' +
                ", back_num='" + back_num + '\'' +
                ", open_time='" + open_time + '\'' +
                ", open_power='" + open_power + '\'' +
                ", complete_apply_power='" + complete_apply_power + '\'' +
                ", storage=" + storage +
                ", general_data=" + general_data +
                ", hardware=" + hardware +
                ", public_ip=" + public_ip +
                ", battery_status=" + battery_status +
                ", device_info=" + device_info +
                '}';
    }

    public String getWifi_state() {
        return wifi_state;
    }

    public void setWifi_state(String wifi_state) {
        this.wifi_state = wifi_state;
    }

    public String getGaid() {
        return gaid;
    }

    public void setGaid(String gaid) {
        this.gaid = gaid;
    }

    public int getBack_num() {
        return back_num;
    }

    public void setBack_num(int back_num) {
        this.back_num = back_num;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public int getOpen_power() {
        return open_power;
    }

    public void setOpen_power(int open_power) {
        this.open_power = open_power;
    }

    public int getComplete_apply_power() {
        return complete_apply_power;
    }

    public void setComplete_apply_power(int complete_apply_power) {
        this.complete_apply_power = complete_apply_power;
    }

    public List<String> getWifilist() {
        return wifilist;
    }

    public void setWifilist(List<String> wifilist) {
        this.wifilist = wifilist;
    }

    public String getSensorcount() {
        return sensorcount;
    }

    public void setSensorcount(String sensorcount) {
        this.sensorcount = sensorcount;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
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

    public String getSecurity_patch_level() {
        return security_patch_level;
    }

    public void setSecurity_patch_level(String security_patch_level) {
        this.security_patch_level = security_patch_level;
    }

    public StorageData getStorage() {
        return storage;
    }

    public void setStorage(StorageData storage) {
        this.storage = storage;
    }

    public GeneralData getGeneral_data() {
        return general_data;
    }

    public void setGeneral_data(GeneralData general_data) {
        this.general_data = general_data;
    }

    public HardwareData getHardware() {
        return hardware;
    }

    public void setHardware(HardwareData hardware) {
        this.hardware = hardware;
    }

    public PublicIpData getPublic_ip() {
        return public_ip;
    }

    public void setPublic_ip(PublicIpData public_ip) {
        this.public_ip = public_ip;
    }

    public BatteryStatusData getBattery_status() {
        return battery_status;
    }

    public void setBattery_status(BatteryStatusData battery_status) {
        this.battery_status = battery_status;
    }

    public DeviceInfoData getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfoData device_info) {
        this.device_info = device_info;
    }
}
