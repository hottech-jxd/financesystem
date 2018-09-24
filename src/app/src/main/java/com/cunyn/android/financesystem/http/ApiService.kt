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
    @POST("getloginuidata")
    @FormUrlEncoded
    fun getLoginUIData(@Field("CustomerId") CustomerId:Long=0)
            : Observable<ApiResult<LoginUIBean>>

    /**
     * 短信发送接口
     * UserMobile       是          手机号
     * SafeCode         否          图文校验码，连续发送2次以上必传
    * CustomerId        是         商户号
     */
    @POST("sendcode")
    @FormUrlEncoded
    fun sendSmsCode(@Field("UserMobile") UserMobile: String
              , @Field("SafeCode") SafeCode: String?
              , @Field("CustomerId") CustomerId: Long): Observable<ApiResult<String?>>

    /**
     * 用户登录接口
     */
    @POST("login")
    @FormUrlEncoded
    fun Login(@Field("UserMobile") UserMobile:String,
              @Field("VerifyCode") VerifyCode:String,
              @Field("CustomerId") CustomerId:Long):Observable<ApiResult<UserBean>>

    /**
     * 图文验证码接口
     *
     */
    @GET("getpicturecode")
    fun getPictureValidateCode():Observable<ApiResult<String>>

    /**
     * APP首页广告接口
     *
     */
    @GET("getindexuidata")
    fun getIndexUIData(@Query("CustomerId") CustomerId:Long)
        :Observable<ApiResult<ArrayList<IndexUIBean>>>

    /**
     * 用户申请借款接口
     */
    @POST("")
    fun userApply(@Field("UserId") UserId:Long , @Field("CustomeId") CustomeId:Long)
    :Observable<ApiResult<Any>>

    /**
     * APP常见问题与投诉接口
     */
    @POST("getfaqdata")
    @FormUrlEncoded
    fun getFaqData(@Field("CustomerId")CustomerId:Long)
    :Observable<ApiResult<FeedbackBean>>

    /**
     * 交易记录接口
     */
    @POST("")
    @FormUrlEncoded
    fun getTradeRecord(@Field("UserId") UserId:Long , @Field("CustomerId") CustomerId:Long)
    :Observable<ApiResult<ArrayList<TradeRecordBean>>>

    /**
     * 通讯录接口
     * UserId:
     * CustomerId:
     * MailList:通讯录（序列化的json数据）
     */
    @POST("")
    @FormUrlEncoded
    fun uploadConstract(@Field("UserId") UserId:Long , @Field("CustomerId") CustomerId:Long,
                        @Field("MailList") MailList:String)
    :Observable<ApiResult<String>>

    /**
     * 数据爬虫接口
     * ReptileType:爬虫类型
     * CustomerId:
     * return: DataId: 爬虫任务订单号
     */
    @POST("")
    @FormUrlEncoded
    fun ReptileType(@Field("ReptileType") ReptileType:Int,@Field("CustomerId")CustomerId:Long)
    :Observable<ApiResult<Long>>
}