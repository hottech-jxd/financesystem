import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface AuditionContract {
    interface Presenter:IPresenter{
        fun uploadContracts(customerId: Long, userId:Long )


        fun submit(RealName : String , //	用户真实姓名
                   IdCardNo	:	String, //	身份证号
                   UserMobile: String	, //手机号
                   BankNo:String )
    }

    interface View:IView<Presenter>{


        fun uploadContractsCallback(apiResult: ApiResult<Any?>)

        fun submitCallback(apiResult: ApiResult<Any?>)

    }
}