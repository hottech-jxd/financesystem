package com.cunyn.android.financesystem.bean

enum class Enums {



}

enum class FragmentType(var code :Int ){
    Index(1),
    Login(2)
}


/**
 * 新颜SDK--> 对应的相关渠道；
 */
enum class XinYan_CHANNEL(var channelName :String) {
    FUNCTION_CARRIER("carrier"),//运营商
    FUNCTION_QQ("qq"),//QQ验证
    FUNCTION_ALIPAY("alipay"),//支付宝；
    FUNCTION_TAOBAO("taobao"),//淘宝
    FUNCTION_JINGDONG("jingdong"),//京东
    FUNCTION_CHSI("chsi"),//学信网
    FUNCTION_FUND("fund"),//公积金
    FUNCTION_DIDI("didi"),//滴滴
    FUNCTION_MAIL("email"),//邮箱
    FUNCTION_SECURITY("security"),//社保
    FUNCTION_ONLINE_BANK("bank"),//网银账单
    FUNCTION_TAOBAOPAY("taobaopay")//淘宝支付宝认证
    ;

}