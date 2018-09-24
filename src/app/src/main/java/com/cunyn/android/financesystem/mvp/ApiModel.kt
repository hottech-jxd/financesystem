package com.cunyn.android.financesystem.mvp

import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.http.RetrofitManager
import io.reactivex.Observable

class ApiModel  {
    private val apiService = RetrofitManager.getApiService()

    fun getLoginUIData(customerId: Long)
        :Observable<ApiResult<LoginUIBean>>{
        return apiService!!.getLoginUIData(customerId)
    }

    fun sendSmsCode(phone:String, safeCode:String?, customerId:Long)
            :Observable<ApiResult<String?>>{
        //val apiService = RetrofitManager.getApiService()
        return apiService!!.sendSmsCode(phone , safeCode , customerId)
    }

    fun login(phone: String, smsCode:String , validateCode:String? , customerId:Long)
    :Observable<ApiResult<UserBean>>{
        return apiService!!.Login(phone, smsCode , customerId)
    }

    fun getPictureCode():Observable<ApiResult<String>>{
        return apiService!!.getPictureValidateCode()
    }

    fun getIndexUIData(customerId:Long)
    :Observable<ApiResult<ArrayList<IndexUIBean>>>{
        return apiService!!.getIndexUIData(customerId)
    }

    fun getFaqData(customerId:Long)
    :Observable<ApiResult<FeedbackBean>>{
        return apiService!!.getFaqData(customerId)
    }

}