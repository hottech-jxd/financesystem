package com.cunyn.android.financesystem.http

import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.LoginUIBean
import com.cunyn.android.financesystem.bean.UserBean
import com.cunyn.android.financesystem.bean.VerifyCodeBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService2 {

    /**
     * 登录页背景图及图标颜色接口
     * CustomerId :商户号
     */
    @Headers("Content-Type: application/json","Accept: application/json")
    @POST("ColorAndIcon")
    @FormUrlEncoded
    fun getLoginUIData(@Body requestBody:RequestBody )
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
    fun sendSmsCode(@Body requestBody: RequestBody  )
            : Observable<ApiResult<String?>>

    /**
     * 图文验证码接口
     *
     */
    @POST("CreateVerifyCode")
    fun getPictureValidateCode(): Observable<ApiResult<VerifyCodeBean?>>
    /**
     * 用户登录接口
     */
    @POST("Login")
    @FormUrlEncoded
    fun Login( @Body requestBody: RequestBody )
            : Observable<ApiResult<UserBean>>

}