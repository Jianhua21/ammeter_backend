package com.kashuo.kcp.constant;

/**
 * Created by dell-pc on 2018/4/23.
 */
public class IoTConstant {

    //
    public static final String IOT_NOTIFY_TYPE                      = "notifyType";
    //
    public static final String IOT_DEVICE_ID                        = "deviceId";


    //添加新设备
    public static final String IOT_NOTIFY_TYPE_DEVICE_ADDED         = "deviceAdded";
    //设备信息变化
    public static final String IOT_NOTIFY_TYPE_DEVICE_INFO_CHANGED  = "deviceInfoChanged";
    //设备数据变化
    public static final String IOT_NOTIFY_TYPE_DEVICE_DATA_CHANGED  = "deviceDataChanged";
    //设备删除
    public static final String IOT_NOTIFY_TYPE_DEVICE_DELETED       = "deviceDeleted";
    //设备事件
    public static final String IOT_NOTIFY_TYPE_DEVICE_EVENT         = "deviceEvent";
    //消息确认
    public static final String IOT_NOTIFY_TYPE_MESSAGE_CONFIRM      = "messageConfirm";
    //响应命令
    public static final String IOT_NOTIFY_TYPE_COMMAND_RSP          = "commandRsp";
    //设备信息
    public static final String IOT_NOTIFY_TYPE_SERVICE_INFO_CHANGED = "serviceInfoChanged";
    //规则事件
    public static final String IOT_NOTIFY_TYPE_RULE_EVENT           = "ruleEvent";
    //设备绑定激活
    public static final String IOT_NOTIFY_TYPE_BIND_DEVICE          = "bindDevice";
    //设备数据批量变化
    public static final String IOT_NOTIFY_TYPE_DEVICE_DATAS_CHANGED = "deviceDatasChanged";

}
