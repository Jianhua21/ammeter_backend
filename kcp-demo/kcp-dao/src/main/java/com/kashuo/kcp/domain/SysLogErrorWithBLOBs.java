package com.kashuo.kcp.domain;

public class SysLogErrorWithBLOBs extends SysLogError {
    private String parameter;

    private String exception;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }
}