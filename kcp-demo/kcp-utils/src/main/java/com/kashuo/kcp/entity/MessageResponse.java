package com.kashuo.kcp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dell-pc on 2019/2/14.
 */
public class MessageResponse {

    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("BizId")
    private String bizId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
}
