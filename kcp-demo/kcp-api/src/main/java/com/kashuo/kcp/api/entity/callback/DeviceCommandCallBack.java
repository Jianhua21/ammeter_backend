package com.kashuo.kcp.api.entity.callback;

/**
 * Created by dell-pc on 2018/4/29.
 */
public class DeviceCommandCallBack {

    private String deviceId;

    private String commandId;

    private CommandResult result;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public CommandResult getResult() {
        return result;
    }

    public void setResult(CommandResult result) {
        this.result = result;
    }

    public class CommandResult{
        private String resultCode;
        private String resultDetail;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultDetail() {
            return resultDetail;
        }

        public void setResultDetail(String resultDetail) {
            this.resultDetail = resultDetail;
        }
    }
}
