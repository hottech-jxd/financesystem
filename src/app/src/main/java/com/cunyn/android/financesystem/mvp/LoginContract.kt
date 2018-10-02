import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface LoginContract {
    interface Presenter:IPresenter{
        fun getLoginUIData(customerId: Long)
        fun getPictureCode()
        fun sendCode( phone:String ,  safecode:String? , customerId:Long , safeKey:String? )
        fun login(phone:String , smsCode:String, validateCode :String? , customerId:Long)
        fun  getRegisterContent()
    }

    interface View:IView<Presenter>{
        fun getLoginUIDataCallback(apiResult: ApiResult<LoginUIBean>)
        fun sendCodeCallback(apiResult: ApiResult<String?>)
        fun loginCallback(apiResult: ApiResult<UserBean>)
        fun getPictureCodecallback(apiResult: ApiResult<VerifyCodeBean?>)
        fun getRegisterContentCallback(apiResult: ApiResult<ProtocalBean?>)
    }
}