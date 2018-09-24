package com.cunyn.android.financesystem.bean

import com.xinyan.bigdata.bean.TitleConfig

object XinYanData {

    val NO = "NO"
    val YES = "YES"

    var titleConfig = TitleConfig()
    var tradeNo: String? = null
    var memberId: String? = null
    var type: String? = null
    var environment: String? = null

    //运营商
    var realname: String? = null//运营商
    var idcard: String? = null//运营商
    var phoneNum: String? = null//运营商
    var phoneServerCode: String? = null//运营商手机号服务密码
    var carrierCanInput = XinYanData.YES//yes运营商页面可以输入
    var carrierIDandNameShow = XinYanData.NO//是否显示用户名  默认不显示


    object UrlManager {
        val EN_TEST = "test"//测试；
        val EN_PRODUCT = "product"//生产；
        //测试预订单
        val TEST_PREORDER_URL = "https://test.xinyan.com/gateway-data/data/v2/preOrder"
        //生产预订单
        val PRODUCT_PREORDER_URL = "https://api.xinyan.com/gateway-data/data/v2/preOrder"
        val TEST_NOTIFY_URL = "http://test.xinyan.com/data/test/member/notify"
        val PRODUCT_NOTIFY_URL = "https://api.xinyan.com/data/test/member/notify"
        //获取结果url
        val BASE_TEST_RESULTURL = "https://test.xinyan.com/data"
        val BASE_PRODUCT_RESULTURL = "https://api.xinyan.com/data"

        val TAOBAO_RESULT_URL = "/taobao/v3/userdata/{order}"
        val JINGDONG_RESULT_URL = "/jingdong/v3/userdata/{order}"
        val ALIPAY_RESULT_URL = "/alipay/v3/data/{order}"
        val CARRIER_RESULT_URL = "/carrier/v2/mobile/{order}?mobile="
        val QQ_RESULT_URL = "/qq/v1/alldata/{order}"
        val FUND_RESULT_URL = "/fund/v2/result/{order}"
        val CHIS_RESULT_URL = "/chsi/v1/all/{order}"
        val DIDI_RESULT_URL = "/didi/v1/alldata/{order}"
        val MAIL_RESULT_URL = "/email/v3/bills/{orderNo}"
        val SECURITY_RESULT_URL = "/security/v1/info/{orderNo}"
        val BANK_RESULT_URL = "/bank/v2/cards/all/{orderNo}"
        val TAOBAOPAY_RESULT_URL = "/taobaopay/v1/info/{orderNo}"

    }
}