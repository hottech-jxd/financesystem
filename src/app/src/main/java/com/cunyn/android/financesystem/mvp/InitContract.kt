import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.IndexUIBean
import com.cunyn.android.financesystem.bean.LoginUIBean
import com.cunyn.android.financesystem.bean.UserBean
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface InitContract {
    interface Presenter:IPresenter{
        fun init(customerId: Long)


    }

    interface View:IView<Presenter>{
        fun initCallback(apiResult: ApiResult<Any>)

    }
}