package com.kashuo.kcp.constant;

/**
 * Created by dell-pc on 2018/4/16.
 */
public enum  ExceptionConstant {

    ERROR_CODE_100208(403,"100208","appId或secret错误"),
    ERROR_CODE_1005(403,"1005","app_key or access_token is invalid"),
    ERROR_CODE_100022(400,"100022","输入参数无效"),
    ERROR_CODE_100006(401,"100006","refreshToken无效"),
    ERROR_CODE_100002(401,"100002","错误的token信息"),
    ERROR_CODE_100025(401,"100025","获取不到appId鉴权信息"),
    ERROR_CODE_100203(200,"100203","应用不存在"),
    ERROR_CODE_100217(200,"100217","应用未被授权"),
    ERROR_CODE_100007(400,"100007","参数不合法"),
    ERROR_CODE_100412(200,"100412","当前应用下设备数量达到上限"),
    ERROR_CODE_100003(400,"100003","验证码无效"),
    ERROR_CODE_100416(200,"100416","设备已经绑定"),
    ERROR_CODE_100001(500,"100001","服务内部处理错误"),
    ERROR_CODE_103026(200,"103026","License不存在"),
    ERROR_CODE_103028(200,"103028","License无资源"),
    ERROR_CODE_103027(200,"103027","License的销售项不存在"),
    ERROR_CODE_100418(200,"100418","设备数据不存在"),
    ERROR_CODE_100023(500,"100023","数据库中数据异常"),
    ERROR_CODE_102203(400,"102203","命令名称无效"),
    ERROR_CODE_100431(200,"100431","服务类型不存在"),
    ERROR_CODE_100428(200,"100428","设备不在线"),
    ERROR_CODE_100432(200,"100432","设备已被muted"),
    ERROR_CODE_100403(200,"100403","设备不存在"),
    ERROR_CODE_100610(200,"100610","设备未激活"),
    ERROR_CODE_100611(200,"100611","设备在线"),
    ERROR_CODE_100426(200,"100426","nodeId重复"),
    ERROR_CODE_100019(400,"100019","非法请求，request为空"),
    ERROR_CODE_103008(400,"103008","加密iv值不存在"),
    ERROR_CODE_103009(400,"103009","加密key不存在"),
    ERROR_CODE_103007(400,"103007","加密flag不存在"),
    ERROR_CODE_103004(200,"103004","新的carInfo, simNumber或者carModel不能为null"),
    ERROR_CODE_103001(200,"103001","simNumber已经存在"),
    ERROR_CODE_103002(200,"103002","plateNumber已经存在"),
    ERROR_CODE_103010(200,"103010","vin已经存在"),
    ERROR_CODE_200001(200,"200001","资源不存在")
    ;

    private Integer statusCode;
    private String errorCode;
    private String desc;

    ExceptionConstant(Integer statusCode,String errorCode,String desc){
        this.statusCode=statusCode;
        this.errorCode=errorCode;
        this.desc=desc;
    }
    public static  String getExceptionConstant(Integer statusCode,String errorCode){
        for(ExceptionConstant cnum:ExceptionConstant.values()){
            if(cnum.statusCode.intValue() == statusCode.intValue() &&
                    cnum.errorCode.equals(errorCode)){
                return  cnum.desc;
            }
        }
        return "错误代码不存在!";
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
