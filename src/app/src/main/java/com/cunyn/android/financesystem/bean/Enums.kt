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
enum class XinYan_CHANNEL(var channelId : Int , var channelName :String) {
    FUNCTION_CARRIER( 3 ,"carrier"),//运营商
    FUNCTION_QQ( 4,"qq"),//QQ验证
    FUNCTION_ALIPAY(5,"alipay"),//支付宝；
    FUNCTION_TAOBAO(0,"taobao"),//淘宝
    FUNCTION_JINGDONG(6,"jingdong"),//京东
    FUNCTION_CHSI(7,"chsi"),//学信网
    FUNCTION_FUND(8,"fund"),//公积金
    FUNCTION_DIDI(999999,"didi"),//滴滴
    FUNCTION_MAIL(1,"email"),//邮箱
    FUNCTION_SECURITY(14,"security"),//社保
    FUNCTION_ONLINE_BANK(2,"bank"),//网银账单
    FUNCTION_TAOBAOPAY(9999998,"taobaopay")//淘宝支付宝认证
    ;

}