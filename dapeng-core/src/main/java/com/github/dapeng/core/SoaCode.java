package com.github.dapeng.core;

/**
 * @author craneding
 * @date 15/9/10
 */
public enum SoaCode implements SoaBaseCodeInterface {
    // 客户端
    //系统出错了
    ClientUnKnown("Err-Core-400", "system error!"),
    //没有可用路由
    NoMatchedRouting("Err-Core-401", "No route available"),
    //没有对应的服务版本
    NoMatchedVersion("Err-Core-403", "No corresponding service version"),
    //无可用的服务实例
    NotFoundServer("Err-Core-404", "No service instance available\n"),
    //没有对应的方法
    NoMatchedMethod("Err-Core-405", "No corresponding method\n"),
    //连接失败
    NotConnected("Err-Core-406", "Connection failed"),
    //请求超时
    ReqTimeOut("Err-Core-407", "Request timed out"),
    //请求对象字段不允许为空
    ReqFieldNull("Err-Core-411", "Request object field is not allowed to be empty\n"),
    //响应对象字段不允许为空
    RespFieldNull("Err-Core-412", "Response object field is not allowed to be empty\n"),
    //响应通讯包解析出错
    RespDecodeError("Err-Core-413", "Respond to communication packet parsing error\n"),
    //响应通讯包未知异常
    RespDecodeUnknownError("Err-Core-414", "The communication package is unknown, and the API version may be inconsistent."),

    // 服务端
    //系统出错了
    ServerUnKnown("Err-Core-500", "System error!"),
    //数据上报出错
    HealthCheckError("Err-Core-501", "Data reporting error"),
    //没有对应的服务或者没有对应的服务版本
    NoMatchedService("Err-Core-504", "No corresponding service or no corresponding service version"),
    //没有对应的方法
    ServerNoMatchedMethod("Err-Core-505", "No corresponding method"),
    //请求超时
    ServerReqTimeOut("Err-Core-506", "Request timed out"),
    //请求过大
    ReqBufferOverFlow("Err-Core-510", "Request too large"),
    //请求对象字段不允许为空
    ServerReqFieldNull("Err-Core-511", "Request object field is not allowed to be empty"),
    //响应对象字段不允许为空
    ServerRespFieldNull("Err-Core-512", "Response object field is not allowed to be empty"),
    //请求通讯包解析出错
    ReqDecodeError("Err-Core-513", "Request communication packet parsing error"),
    //限流模块初始化失败
    ShmInitError("Err-Core-520", "Current limiting module initialization failed"),
    //客户端已被限流
    FreqLimited("Err-Core-521", "The client has been restricted"),
    //限流规则解析出错
    FreqConfigError("Err-Core-522", "Current limit rule parsing error"),
    //限流处理出错
    FreqControlError("Err-Core-523", "Current limit processing error"),
    //服务容器不在运行状态
    ContainerStatusError("Err-Core-524", "Service container is not running\n"),
    // 通用错误码
    StructFieldNull("Err-Core-600", "Structure field is not allowed to be empty");
    private String code;
    private String msg;

    SoaCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return code + ":" + msg;
    }
}
