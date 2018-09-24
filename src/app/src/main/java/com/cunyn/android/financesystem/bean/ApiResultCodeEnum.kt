package com.cunyn.android.financesystem.bean

enum class ApiResultCodeEnum (var code :Int,var desc :String ){

    SUCCESS(1 , "请求成功"),
    TOKEN_ERROR(11,"交互会话已过期，请重新登录"),
    REQUEST_NOT_EXIST(2,"无法执行的请求或请求交互不存在"),
    PARAMETER_ERROR(3,"参数错误"),
    PARAMETER_TYPE_ERROR(4,"无法识别的参数类型"),


    SMS_LOGIN(20,"用户已登录，需使用短信验证码确认登录。"),

    SYSTEM_ERROR(0,"系统错误")
//    REQUEST_FAIL(301,"系统请求失败"),
//    MISS_PARAMETER(400,"缺少请求参数"),
//    SIGN_MISS(401,"签名未传"),
//    SING_ERROR(402,"签名错误"),
//    NO_INFO(403,"没有信息"),
//    SERVER_ERROR(500,"服务器错误"),
//    USER_NO_LOGIN(1000,"用户未登录"),
//    USER_FREEZE(1001,"用户已被冻结"),
//    USER_ILLEGAL(1002,"用户登录信息非法"),
    //TOKEN_ERROR(4003, "token失效"),
    //AUTH_ERROR(4123 ,"未完成认证,前往认证"),
    //EXIST_RECYCLE_ERROR(4502,"存在回收中的记录，请完成后再次申请!")
}