package com.kashuo.kcp.api.entity.callback;

/**
 * Created by dell-pc on 2019/2/25.
 */
public class DeviceZxYunCallBack {

    //日志编号
    private String logId;
    //项目编号
    private String projectId;
    //标志位：0网关1控制器2设备（二次码）3设备（回路）
    private String flag;
    //网关名称
    private String gatewayName;
    //设备编号
    private String deviceId;
    //设备安装位置
    private String devicePosition;
    //设备回路号
    private String deviceControllerId;
    //设备逻辑地址
    private String deviceAddrId;
    //设备通道号
    private String deviceChannelId;
    //设备二次码
    private String deviceNumber;
    //设备类型编号
    private String deviceType;
    //设备详细状态（报警通知时不为null）
    private String deviceState;
    //设备状态分组（报警和上线离线通知时不为null）
    private String deviceGroupState;
    //发生的时间
    private String happenTime;
    //操作码（操作通知时不为null）
    private String optCode;
    //推送类型（1.报警状态通知2.操作通知 3.上线离线通知）
    private String pushType;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    public String getDeviceControllerId() {
        return deviceControllerId;
    }

    public void setDeviceControllerId(String deviceControllerId) {
        this.deviceControllerId = deviceControllerId;
    }

    public String getDeviceAddrId() {
        return deviceAddrId;
    }

    public void setDeviceAddrId(String deviceAddrId) {
        this.deviceAddrId = deviceAddrId;
    }

    public String getDeviceChannelId() {
        return deviceChannelId;
    }

    public void setDeviceChannelId(String deviceChannelId) {
        this.deviceChannelId = deviceChannelId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getDeviceGroupState() {
        return deviceGroupState;
    }

    public void setDeviceGroupState(String deviceGroupState) {
        this.deviceGroupState = deviceGroupState;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }
}
