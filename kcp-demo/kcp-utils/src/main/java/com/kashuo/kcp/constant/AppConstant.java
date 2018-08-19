package com.kashuo.kcp.constant;

/**
 * Created by dell-pc on 2018/4/19.
 */
public class AppConstant {


    public static final String REDIS_KEY_AUTH_IOM ="auth_IoM_token_key";

    public static final String REDIS_KEY_AUTH_IOM_WELLCOVER ="auth_IoM_token_key_well_cover";
    //移动平台
    public static final String REDIS_KEY_AUTH_IOT ="auth_IoT_token_key";

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

    public static final String COMMAND_SWTICH_ON_KEY = "switch_on";

    public static final String COMMAND_SWTICH_OFF_KEY = "switch_off";

    public static final String COMMAND_CDP_IP_KEY ="cdp_ip";

    public static final String COMMAND_APN_ADDRESS_KEY ="apn_address";

    public static final String COMMAND_RESTORE_CALLBACK_URL = "restore_url";

    public static final String COMMAND_SUBSCRIBE_CALLBACK_URL ="subscribe_url";

    public static final String COMMAND_COMMON_CALLBACK_URL ="command_callback_url";

    public static final String COMMAND_NB_KEEPALIVE_KEY ="nb_keep_alive";

    public static final String COMMAND_SAVE_CONFIG_KEY ="save_config";

    public static final String COMMAND_AMMETER_ADDRESS_KEY ="ammeter_address";

    public static final String COMMAND_AMMETER_POWER_KEY ="command_power";

    public static final String COMMAND_AMMETER_VOLTAGE_KEY ="command_voltage";

    public static final String COMMAND_AMMETER_CURRENT_KEY ="command_current";

    public static final String COMMAND_AMMETER_TEMPERATURE_KEY ="command_temperature";

    public static final String METER_RSSI_KEY = "RSSI";

    public static final String METER_RSRQ_KEY ="RSRQ";

    public static final String METER_CELLI_KEY ="CELLI";

    public static final String METER_RSRP_KEY ="RSRP";

    public class AmmeterBaseInformation {

        public static final String AMMETER_DEVICE_TYPE = "nb_manhole_meter";

        public static final String AMMETER_DEVICE_MODEL = "NBIoTDeviceManhole";

        public static final String AMMETER_DEVICE_MANUFACTURER_ID ="001020";

        public static final String AMMETER_DEVICE_MANUFACTURER_NAME = "Shiding";

        public static final String AMMETER_DEVICE_PROTOCOL = "CoAP";
    }


}
