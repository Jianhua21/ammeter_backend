package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by dell-pc on 2018/4/22.
 */
@ApiModel("订阅")
public class SubscribeCondition {

    @ApiModelProperty("通知类型，如：\n" +
            "1、deviceAdded（添加新设备）\n" +
            "2、deviceInfoChanged（设备信息变化）\n" +
            "3、deviceDataChanged（设备数据变化）\n" +
            "4、deviceDeleted（删除设备）\n" +
            "5、deviceEvent（设备事件）\n" +
            "6、messageConfirm（消息确认）\n" +
            "7、commandRsp（响应命令）\n" +
            "8、serviceInfoChanged（设备信息）\n" +
            "9、ruleEvent（规则事件）\n" +
            "10、bindDevice（设备绑定激活）\n" +
            "11、deviceDatasChanged（设备数据批量变化）\n")
    @NotNull
    private String notifyType;
    @ApiModelProperty("消息唯一码")
    private String sn;

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
