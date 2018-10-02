package com.cunyn.android.financesystem.http

import android.renderscript.Allocation
import com.cunyn.android.financesystem.bean.*
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {

    /**
     * 登录页背景图及图标颜色接口
     * CustomerId :商户号
     */
    @POST("ColorAndIcon")
    @FormUrlEncoded
    fun getLoginUIData(@Field("CustomerId") CustomerId: Long = 0)
            : Observable<ApiResult<LoginUIBean>>

    /**
     * 短信发送接口
     * UserMobile       是          手机号
     * SafeCode         否          图文校验码，连续发送2次以上必传
     * CustomerId        是         商户号
     * SafeKey       是   图文校验码返回的key
     */
    @POST("SendSMSCode")
    @FormUrlEncoded
    fun sendSmsCode(@Field("UserMobile") UserMobile: String
                    , @Field("SafeCode") SafeCode: String?
                    , @Field("CustomerId") CustomerId: Long
                    , @Field("SafeKey") SafeKey:String?)
            : Observable<ApiResult<String?>>

    /**
     * 用户登录接口
     */
    @POST("Login")
    @FormUrlEncoded
    fun Login(@Field("UserMobile") UserMobile: String,
              @Field("VerifyCode") VerifyCode: String,
              @Field("CustomerId") CustomerId: Long): Observable<ApiResult<UserBean>>

    /**
     * 图文验证码接口
     *
     */
    @POST("CreateVerifyCode")
    fun getPictureValidateCode(): Observable<ApiResult<VerifyCodeBean?>>

    /**
     * APP首页广告接口
     *
     */
    @GET("APPADV")
    fun getIndexUIData(@Query("CustomerId") CustomerId: Long)
            : Observable<ApiResult<ArrayList<IndexUIBean>?>>

    /**
     * 用户申请借款接口
     */
    @POST("UserLoan")
    @FormUrlEncoded
    fun userApply(@Field("UserId") UserId: Long
                  , @Field("CustomeId") CustomeId: Long)
            : Observable<ApiResult<Any?>>

    /**
     * APP常见问题与投诉接口
     */
    @POST("QA")
    @FormUrlEncoded
    fun getFaqData(@Field("CustomerId") CustomerId: Long)
            : Observable<ApiResult<FeedbackBean?>>

    /**
     * 交易记录接口
     */
    @POST("GetShopList")
    @FormUrlEncoded
    fun getTradeRecord(@Field("UserId") UserId: Long
                       , @Field("CustomerId" ) CustomerId: Long
                        ,@Field("page") page:Int
                        ,@Field("size") size:Int)
            : Observable<ApiResult<ArrayList<TradeRecordBean>?>>

    /**
     * 通讯录接口
     * UserId:
     * CustomerId:
     * MailList:通讯录（序列化的json数据）
     */
    @POST("MailRequest")
    @FormUrlEncoded
    fun uploadConstract(@Field("UserMobile") UserMobile: String
                        , @Field("CustomerId") CustomerId: Long,
                        @Field("MailList") MailList: String?)
            : Observable<ApiResult<Any?>>

    /**
     * 数据爬虫接口
     * ReptileType:爬虫类型
     * taobao=>淘宝
    email=>邮箱信用卡账单
    bank=>网银数据
    carrier=>运营商数据
    qq=>qq数据
    alipay=>支付宝数据
    jingdong=>京东
    chsi=>学信网
    fund=>公积金
    insurance=>车险保单
    maimai=>脉脉
    zhixingcourt=>法院被执行人
    shixincourt=>法院失信人
    linkedin=>领英
    security=>社保
     * CustomerId:
     * return: DataId: 爬虫任务订单号
     */
    @POST("CreatePreOrder")
    @FormUrlEncoded
    fun createPreOrder(@Field("txnType") txnType: String
                    , @Field("CustomerId") CustomerId: Long)
            : Observable<ApiResult<PreOrderBean?>>

    /**
     * 四要素认证接口（对照接口文档调整）
     */
    @POST("AuthBankCard")
    @FormUrlEncoded
    fun submitInfo(
            @Field("RealName") RealName: String,
            @Field("IdCardNo") IdCardNo :String,
            @Field("UserMobile") UserMobile :String,
            @Field("BankNo") BankNo: String)
            : Observable<ApiResult<Any?>>

    /**
     * 获取注册协议接口
     */
    @POST("GetRegisterContent")
    fun getRegisterContent():Observable<ApiResult<ProtocalBean?>>


}