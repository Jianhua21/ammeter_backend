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



    public class Command{
        //设备软重启
        public static final String DEVICE_COMMAND_STM001 ="STM001";
        //恢复出厂设置
        public static final String DEVICE_COMMAND_STM002 ="STM002";
        //保存系统配置
        public static final String DEVICE_COMMAND_STM003 ="STM003";
        //配置NB处理流程时间
        public static final String DEVICE_COMMAND_STM004 ="STM004";

        //获取模块信号值
        public static final String DEVICE_COMMAND_NBM001 ="NBM001";
        //设置CDP服务器IP
        public static final String DEVICE_COMMAND_NBM002 ="NBM002";
        //设置APN地址
        public static final String DEVICE_COMMAND_NBM003 ="NBM003";

        public static final String DEVICE_COMMAND_IEM001 ="00000000785634121a00123017010519";

        public static final String DEVICE_COMMAND_IEM002 ="IEM002";

        public static final String DEVICE_COMMAND_FEFEFEFE ="FEFEFEFE";

    }

}
