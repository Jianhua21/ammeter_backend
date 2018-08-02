package com.kashuo.kcp.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用常量
 */
@Configuration
@ConfigurationProperties(prefix = "app.constant", ignoreUnknownFields = true)
public class AppConstantProperties {

    private String defaultPassword;

    private String adminIds;

    private boolean isDebug;

    private boolean nbiot;

    public boolean isNbiot() {
        return nbiot;
    }

    public void setNbiot(boolean nbiot) {
        this.nbiot = nbiot;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(String adminIds) {
        this.adminIds = adminIds;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

}
