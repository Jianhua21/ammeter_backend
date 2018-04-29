package com.kashuo.kcp.constant;

/**
 * Created by dell-pc on 2018/4/19.
 */
public class AppConstant {


    public static final String IOM_APPID ="appId";

    public static final String IOM_SECRET ="secret";

    public static final String IOM_PLATFORM_PORT ="platformPort";

    public static final String IOM_PLATFORM_IP ="platformIp";

    //系统参数typeId
    public static final String SYSTEM_PARAMS_TYPE_ID = "7";

    public static final String CALLBACK_URLS_TYPE_ID = "8";

    public static final String COMMAND_RESET_CALLBACK_URL = "reset_url";

    public static final String  COMMAND_METHOD_PARAM_NAME = "control_meter";

    public static final String  COMMAND_SERVICEID_PARAM_NAME = "elect_meter";

    public static final String COMMAND_RESET_KEY ="reset";

    public static final String COMMAND_RESTORE_KEY ="restore";

    public static final String COMMAND_RESTORE_CALLBACK_URL = "restore_url";

    public static final String COMMAND_SUBSCRIBE_CALLBACK_URL ="subscribe_url";

    public static final String COMMAND_COMMON_CALLBACK_URL ="command_callback_url";

    public static final String COMMAND_NB_KEEPALIVE_KEY ="nb_keep_alive";

    public class AmmeterBaseInformation {

        public static final String AMMETER_DEVICE_TYPE = "nb_elect_meter";

        public static final String AMMETER_DEVICE_MODEL = "NBIoTDevice";

        public static final String AMMETER_DEVICE_MANUFACTURER_ID ="001020";

        public static final String AMMETER_DEVICE_MANUFACTURER_NAME = "Shiding";

        public static final String AMMETER_DEVICE_PROTOCOL = "CoAP";
    }


}
