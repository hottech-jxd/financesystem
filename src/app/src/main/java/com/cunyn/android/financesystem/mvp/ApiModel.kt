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

    fun sendSmsCode(phone:String, safeCode:String?, customerId:Long , safeKey:String?)
            :Observable<ApiResult<String?>>{
        return apiService!!.sendSmsCode(phone , safeCode ,  safeKey)
    }

    fun login(phone: String, smsCode:String , validateCode:String? , customerId:Long)
    :Observable<ApiResult<UserBean>>{
        return apiService!!.Login(phone, smsCode , customerId)
    }

    fun getPictureCode():Observable<ApiResult<VerifyCodeBean?>>{
        return apiService!!.getPictureValidateCode()
    }

    fun getIndexUIData(customerId:Long)
    :Observable<ApiResult<ArrayList<IndexUIBean>?>>{
        return apiService!!.getIndexUIData(customerId)
    }

    fun getFaqData(customerId:Long)
    :Observable<ApiResult<FeedbackBean?>>{
        return apiService!!.getFaqData(customerId)
    }

    fun apply( userId : Long , customerId: Long)
    :Observable<ApiResult<Any?>>{
        return apiService!!.userApply(userId, customerId)
    }

    fun uploadContracts(userMobile: String , customerId: Long, contracts:String?)
    :Observable<ApiResult<Any?>>{
        return apiService!!.uploadConstract(userMobile , contracts)
    }

    fun submitInfo(realName:String,
                   idCard:String ,
                   mobile:String,
                   bankNo:String ,
                   bankType :String ,
                   bankYear:String,
                   bankMonth:String,
                   bankSafeCode:String,
                   validateType:String)
    :Observable<ApiResult<Any?>>{
        return apiService!!.submitInfo(
                idCard ,
                bankNo ,
                realName ,
                mobile ,
                bankType,
                bankYear,
                bankMonth,
                bankSafeCode,
                validateType)
    }

    fun getApplyRecords(customerId: Long,userId: Long, page:Int,size:Int)
    :Observable<ApiResult<ArrayList<TradeRecordBean>?>>{
        return apiService!!.getTradeRecord(userId , page, size)
    }

    fun getRegisterContent(customerId: Long)
            :Observable<ApiResult<ProtocalBean?>>{
        return apiService!!.getRegisterContent()
    }

    fun createPreOrder(txType:String , customerId: Long)
        :Observable<ApiResult<PreOrderBean?>>{
        return apiService!!.createPreOrder(txType , customerId)
    }
}