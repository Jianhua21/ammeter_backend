package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kashuo.common.base.domain.Page;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Results<T> {


    private int code;

    private String message;

    private T data;

    private Long total;

    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 返回成功 不带数据
     */
    public static Results success(String message) {
        return new Results(10000, message,"");
    }

    /**
     * 返回成功 带数据
     */
    public static Results success(String message, Object data) {
        return new Results(10000, message, data);
    }

    /**
     * 返回成功 带数据
     */
    public static Results success(String message, String sn) {
        return new Results(10000, message, "",sn);
    }
    /**
     * 直接返回数据
     */
    public static Results success(Object data) {
        return new Results(10000, "success", data);
    }

    /**
     * 直接返回数据
     */
    public static Results success(Object data,String sn) {
        return new Results(10000, "success", data,sn);
    }

    /**
     * 返回失败
     */
    public static Results error(String message) {
        return new Results(10001, message,"");
    }

    public static Results error(String message,String sn) {
        return new Results(10001, message,sn);
    }

    /**
     * 返回失败及状态码
     */
    public static Results error(int code, String message) {
        return new Results(code, message,"");
    }

    public Results(Object data) {
        this(10000, "", data);
    }

    public Results(int code, String message,String sn) {
        super();
        this.code = code;
        this.message = message;
        this.sn = sn;
    }

    @SuppressWarnings("unchecked")
    public Results(int code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = (T) data;
        if (data instanceof Page) {
            this.total = ((Page<?>) data).getTotal();
        }
    }
    public Results(int code, String message, Object data,String sn) {
        super();
        this.code = code;
        this.message = message;
        this.data = (T) data;
        if (data instanceof Page) {
            this.total = ((Page<?>) data).getTotal();
        }
        this.sn =sn;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }


}
