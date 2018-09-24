import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.LoginUIBean
import com.cunyn.android.financesystem.bean.UserBean
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface LoginContract {
    interface Presenter:IPresenter{
        fun getLoginUIData(customerId: Long)
        fun getPictureCode()
        fun sendCode( phone:String ,  safecode:String? , customerId:Long)
        fun login(phone:String , smsCode:String, validateCode :String? , customerId:Long)
    }

    interface View:IView<Presenter>{
        fun getLoginUIDataCallback(apiResult: ApiResult<LoginUIBean>)
        fun sendCodeCallback(apiResult: ApiResult<String?>)
        fun loginCallback(apiResult: ApiResult<UserBean>)
        fun getPictureCodecallback(apiResult: ApiResult<String>)
    }
}